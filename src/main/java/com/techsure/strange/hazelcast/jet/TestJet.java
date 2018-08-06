package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IList;
import com.hazelcast.core.IMapEvent;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.WindowDefinition;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/7/10 15:45
 */
public class TestJet {
	public static void main(String[] args) throws InterruptedException {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig("inputMap")
				.setEnabled(true)
				.setCapacity(0)
				.setTimeToLiveSeconds(10);

		JetInstance jet = Jet.newJetInstance(cfg);
		IList<TestVo> test = jet.getList("test");

		Integer count = 0;
		Integer num = 0;
		for(int i = 0; i< 100; i++){
			TestVo testVo = new TestVo();
			testVo.setName("z" + count);
			testVo.setSex(count % 2== 0?0:1);
			testVo.setAge(18 + count + num);
			testVo.setGenerateDate(new Date(Long.sum(System.currentTimeMillis() ,Long.valueOf(i * 2000))));
			test.add(testVo);
			count++;
			num++;
			if(count == 10){
				count = 0;
			}
		}

		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<TestVo>list("test"))
				.filter(e -> e.getSex() != 0)
				.addTimestamps(e -> e.getGenerateDate().getTime(),30000)
				.window(WindowDefinition.tumbling(60000))
				.groupingKey(e -> e.getName())
				.aggregate(AggregateOperations.counting())
				//.drainTo(Sinks.list("outList"));
				//.drainTo(Sinks.files("G:\\test.txt"));
				.drainTo(Sinks.<Map.Entry<String, Long>, String, Long>mapWithUpdating(
						"outMap", Map.Entry::getKey,
						(oldV, item) -> (item.getValue()))
				);

		jet.newJob(p).join();

		while(true){
			TimeUnit.SECONDS.sleep(3L);
		}

	}


	/*public static void main(String[] args){
		JetInstance jet = Jet.newJetInstance();
		try {
			List<String> test = jet.getList("test");
			Pipeline p  = Pipeline.create();
			if(test == null){
				p.drawFrom(Sources.list("test")).drainTo(Sinks.list("outList"));
				test = jet.getList("test");
			}
			test.add("test1");
			test.add("test2");
			jet.newJob(p)
		}
	}*/
}

class TestVo implements Serializable{
	private String name;
	private Integer sex;
	private Integer age;
	private Date generateDate;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}

}

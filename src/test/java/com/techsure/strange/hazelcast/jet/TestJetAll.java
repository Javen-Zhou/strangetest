package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IMap;
import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Job;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.*;
import com.hazelcast.jet.stream.DistributedStream;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.aggregate.AggregateOperations.counting;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_OLDEST;

/**
 * @author zhoujian
 * @Date 2018/7/12 15:50
 */
public class TestJetAll {
	private static final String MAP_NAME = "map";
	private static final String SINK_LIST = "list";
	private static final String LIST_NAME = "inList";








	@Test
	public void testA() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.list(LIST_NAME))
				.drainTo(Sinks.list(SINK_LIST));

		List list = jet.getList(LIST_NAME);
		list.add("test1");
		list.add("test2");

		jet.newJob(p).join();

		while(true){
			TimeUnit.SECONDS.sleep(1L);
		}
	}

	@Test
	public void testB() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		List outList = jet.getList(SINK_LIST);
		while(true){
			outList.forEach(System.out::println);
			TimeUnit.SECONDS.sleep(2L);
		}
	}

	@Test
	public void test1() throws Exception {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);

		try {
			jet.newJob(buildPipeline());
			IMapJet<Integer, Integer> map = jet.getMap(MAP_NAME);
			for (int i = 0; i < 500; i++) {
				map.set(i, i);
			}

			DistributedStream.fromMap(map)
					//.filter(e -> e.getValue() < 0)
					.forEach(System.out::println);

			TimeUnit.SECONDS.sleep(10);
			List<Integer> list = jet.getList(SINK_LIST);

			System.out.println("list.size()=" + jet.getList(SINK_LIST).size());
		} finally {
			jet.shutdown();
		}
	}

	@Test
	public void test2() throws Exception {
		System.setProperty("hazelcast.logging.type", "log4j");
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Jet.newJetInstance(jetConfig);
		try {
			Pipeline p = Pipeline.create();
			p.drawFrom(Sources.<Integer, Integer>mapJournal(MAP_NAME, START_FROM_OLDEST))
					.map(Map.Entry::getValue)
					.drainTo(Sinks.list(SINK_LIST));

			jet.newJob(p);

			IMapJet<Integer, Integer> map = jet.getMap(MAP_NAME);
			for (int i = 0; i < 1000; i++) {
				map.set(i, i);
			}
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Read " + jet.getList(SINK_LIST).size() + " entries from map journal.");
		} finally {
			Jet.shutdownAll();
		}
	}

	@Test
	public void testTumblingWindow() {
		JetConfig cfg = getJetConfig();
		JetInstance jet = Jet.newJetInstance(cfg);
		Jet.newJetInstance(cfg);

		try {
			jet.newJob(buildWindowPipeline());
			IMapJet<Integer, TestVo> map = jet.getMap(MAP_NAME);
			map.put(0,new TestVo("张三","男",18,new Date(Long.sum(System.currentTimeMillis(), 2000L))));
			map.put(1,new TestVo("李四","男",19,new Date(Long.sum(System.currentTimeMillis(), 4000L))));
			map.put(2,new TestVo("王五","男",22,new Date(Long.sum(System.currentTimeMillis(), 6000L))));
			map.put(3,new TestVo("赵六","男",21,new Date(Long.sum(System.currentTimeMillis(), 8000L))));


			TimeUnit.SECONDS.sleep(10L);

			/*List list = jet.getList(SINK_LIST);
			System.out.println(list.size());*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			jet.shutdown();
		}
	}

	private static Pipeline buildWindowPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<TestVo, Integer, TestVo>mapJournal(MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_OLDEST))
				.addTimestamps(e -> e.getGenerateDate().getTime(), 10000)
				.window(WindowDefinition.tumbling(30000))
				//.groupingKey(e -> e.getName())
				.aggregate(AggregateOperations.averagingDouble(e -> Long.valueOf(e.getAge())))
				//.drainTo(Sinks.list(SINK_LIST));
		//.drainTo(Sinks.files("G:\\test"));
		.drainTo(Sinks.logger());
		return p;
	}


	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<Integer, Integer>mapJournal(MAP_NAME, JournalInitialPosition.START_FROM_OLDEST))
				//.map(Map.Entry::getValue)
				.drainTo(Sinks.list(SINK_LIST));

		return p;
	}

	private static JetConfig getJetConfig() {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig(MAP_NAME)
				.setEnabled(true)
				.setCapacity(1000)
				.setTimeToLiveSeconds(20)
		;
		return cfg;
	}
}

class TestVo implements Serializable {
	private String name;
	private String sex;
	private Integer age;
	private Date generateDate;

	public TestVo(){}

	public TestVo(String name, String sex, Integer age, Date generateDate) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.generateDate = generateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
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

package com.techsure.strange.hazelcast.jet;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/7/10 18:10
 */
public class TestJet3 {
	public static void main(String[] args) throws InterruptedException {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.list("test"))
				.drainTo(Sinks.list("outList"));

		JetInstance jet = Jet.newJetInstance();
		List<String> test = jet.getList("test");
		test.clear();
		test.add("test3");
		test.add("test4");

		jet.newJob(p).join();
		while(true){
			TimeUnit.SECONDS.sleep(3L);
			System.out.println("connecting....");
		}
	}
}

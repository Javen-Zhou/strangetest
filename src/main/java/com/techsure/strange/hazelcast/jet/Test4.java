package com.techsure.strange.hazelcast.jet;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.pipeline.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/7/11 10:58
 */
public class Test4 {
	public static void main(String[] args){
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.map("test"))
				.drainTo(Sinks.remoteList("outMap",new ClientConfig()));

		JetInstance jet = Jet.newJetInstance();
		try{
			Map<String,String> test = jet.getMap("test");
			test.put("test1","test1");
			test.put("test2","test2");

			jet.newJob(p).join();

			List outMap = jet.getList("outMap");
			outMap.forEach(System.out::println);
		}finally {
			Jet.shutdownAll();
		}
	}
}

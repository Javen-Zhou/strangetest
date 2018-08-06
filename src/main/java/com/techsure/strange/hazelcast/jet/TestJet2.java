package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IMap;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.datamodel.TimestampedEntry;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/7/10 16:04
 */
public class TestJet2 {
	public static void main(String[] args){

		JetInstance jet = Jet.newJetInstance();

		try{
			while(true) {
				System.out.println("read start");
				IMap<String, Long> map = jet.getMap("outMap");
				for(String key:map.keySet()){
					System.out.println(key + " = " + map.get(key));
				}

				/*
				List<TimestampedEntry> outList = jet.getList("outList");

				List<TestVo> testVoList;
				for (TimestampedEntry e:outList) {
					System.out.println(e.toString());

				}
				*/
				System.out.println("read end");
				TimeUnit.SECONDS.sleep(30L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			Jet.shutdownAll();
		}
	}
}

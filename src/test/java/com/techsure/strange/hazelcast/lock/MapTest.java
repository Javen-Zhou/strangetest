package com.techsure.strange.hazelcast.lock;

import com.hazelcast.core.IMap;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/9/2 星期日 13:56
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void testMap() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		IMap<String,Long> map = jet.getMap("Map");
		map.set("oneHourJob",2L);
	}

	@Test
	public void testMapB(){
		JetInstance jet = Jet.newJetInstance();
		IMap<String,Long> map = jet.getMap("Map");
		map.set("oneHourJob",1L);
		while(true){
			if(map.get("oneHourJob").equals(2L)){
				logger.info(String.valueOf( map.get("oneHourJob")));
				break;
			}
		}
	}
}

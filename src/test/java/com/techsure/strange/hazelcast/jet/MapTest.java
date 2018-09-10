package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 15:27
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void instanceA() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		while (true) {
			TimeUnit.SECONDS.sleep(2L);
		}
	}

	@Test
	public void test() throws InterruptedException {
		instanceB(20000);
	}

	public void instanceB(Integer num) throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		IMap<Integer, Integer> map = jet.getMap("Map");
		IList list = jet.getList("List");
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			map.set(i, i);
		}
		logger.info("set consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			map.setAsync(i, i);
		}
		logger.info("setAsync consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			map.put(i, i);
		}
		logger.info("put consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			map.putAsync(i, i);
		}
		logger.info("put Async consume time:{}", System.currentTimeMillis() - begin);
	}
}

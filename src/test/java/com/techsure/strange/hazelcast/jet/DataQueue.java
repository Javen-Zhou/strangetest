package com.techsure.strange.hazelcast.jet;

import com.techsure.strange.hazelcast.assist.RollupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/30 星期四 20:34
 */
public class DataQueue extends AbstractDataQueue  implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(DataQueue.class);
	private static BlockingQueue queue;

	public static void put(RollupVo rollupVo) {
		if (queue == null) {
			queue = new LinkedBlockingQueue();
		}
		try {
			TimeUnit.MILLISECONDS.sleep(5L);

			queue.put(rollupVo);
			logger.info(String.valueOf(queue.size()));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static RollupVo take() throws InterruptedException {
		if (queue == null) {
			queue = new LinkedBlockingQueue();
		}
		RollupVo rollupVo = (RollupVo) queue.take();
		return rollupVo;
	}
}

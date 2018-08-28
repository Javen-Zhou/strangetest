package com.techsure.strange.chronicle;

import net.openhft.chronicle.core.Maths;
import net.openhft.chronicle.core.time.TimeProvider;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.RollCycle;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/27 星期一 20:11
 */
public class StoreQueue {
	private static final Logger logger = LoggerFactory.getLogger(StoreQueue.class);

	@Test
	public void intPut() throws InterruptedException {
		String path = "queue";
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(SelfRollCyle.FIVE_MINUTE).build();
		ExcerptAppender appender = queue.acquireAppender();
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			appender.writeText("str" + i + ":" + System.currentTimeMillis());
			//TimeUnit.SECONDS.sleep(5L);
		}
		logger.info("consume:{}", System.currentTimeMillis() - begin);
	}

	@Test
	public void outPut() throws InterruptedException {
		String path = "queue";
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).build();
		ExcerptTailer tailer = queue.createTailer();
		String text;
		Long begin = System.currentTimeMillis();
		Integer count = 0;
		while ((text = tailer.readText()) != null) {
			if (count == 0) {
				logger.info(String.valueOf(tailer.index()));
			}
			count++;
		}
		logger.info("consume:{},count:{}", System.currentTimeMillis() - begin, count);

		/*ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
		threadPool.scheduleAtFixedRate(() -> {
			String text;
			while ((text = tailer.readText()) != null)
				logger.info(text);
			logger.info(String.valueOf(tailer.cycle()));
		}, 0, 60, TimeUnit.SECONDS);

		while (true) {
			TimeUnit.SECONDS.sleep(10L);
		}*/
	}
}

enum SelfRollCyle implements RollCycle {
	FIVE_MINUTE("yyyyMMdd-HHmm", 5 * 60 * 1000, 2 << 10, 16),
	ONE_HOUR("yyyyMMdd-HH", 60 * 60 * 1000, 4 << 10, 16),
	EIGHT_HOUR("yyyyMMdd-HH", 8 * 60 * 60 * 1000, 4 << 10, 16);


	final String format;
	final int length;
	final int cycleShift;
	final int indexCount;
	final int indexSpacing;
	final long sequenceMask;

	SelfRollCyle(String format, int length, int indexCount, int indexSpacing) {
		this.format = format;
		this.length = length;
		this.indexCount = Maths.nextPower2(indexCount, 8);
		this.indexSpacing = Maths.nextPower2(indexSpacing, 1);
		cycleShift = Math.max(32, Maths.intLog2(indexCount) * 2 + Maths.intLog2(indexSpacing));
		sequenceMask = (1L << cycleShift) - 1;
	}

	@Override
	public String format() {
		return this.format;
	}

	@Override
	public int length() {
		return this.length;
	}

	@Override
	public int defaultIndexCount() {
		return indexCount;
	}

	@Override
	public int defaultIndexSpacing() {
		return indexSpacing;
	}

	@Override
	public int current(@NotNull TimeProvider time, long epoch) {
		return (int) ((time.currentTimeMillis() - epoch) / length());
	}

	@Override
	public long toIndex(int cycle, long sequenceNumber) {
		return ((long) cycle << cycleShift) + (sequenceNumber & sequenceMask);
	}

	@Override
	public long toSequenceNumber(long index) {
		return index & sequenceMask;
	}

	@Override
	public int toCycle(long index) {
		return Maths.toUInt31(index >> cycleShift);
	}
}

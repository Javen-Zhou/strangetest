package com.techsure.strange.chronicle;

import net.openhft.chronicle.core.Maths;
import net.openhft.chronicle.core.time.TimeProvider;
import net.openhft.chronicle.queue.RollCycle;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhoujian
 * @Date 2018/8/28 星期二 11:35
 */
public enum RollCycleEnum implements RollCycle {
	/**
	 * 五分钟一个文件
	 */
	FIVE_MINUTE("yyyyMMdd-HHmm", 5 * 60 * 1000, 2 << 10, 16),
	/**
	 * 八小时一个文件
	 */
	EIGHT_HOUR("yyyyMMdd-HH", 8 * 60 * 60 * 1000, 4 << 10, 16);


	final String format;
	final int length;
	final int cycleShift;
	final int indexCount;
	final int indexSpacing;
	final long sequenceMask;

	RollCycleEnum(String format, int length, int indexCount, int indexSpacing) {
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

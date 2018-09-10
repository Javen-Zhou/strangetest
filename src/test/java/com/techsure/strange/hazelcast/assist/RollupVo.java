package com.techsure.strange.hazelcast.assist;


import java.io.Serializable;

/**
 * @author zhoujian
 * @Date 2018/8/26 星期日 17:14
 */
public class RollupVo<T extends Number & Comparable<T>> extends CommonVo implements Serializable{

	private Integer num;
	private T max;
	private T min;
	private T sum;
	private T average;
	private T p95;
	private T p5;


	@Override
	public Object getValue() {
		return p95;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public T getMax() {
		return max;
	}

	public void setMax(T max) {
		this.max = max;
	}

	public T getMin() {
		return min;
	}

	public void setMin(T min) {
		this.min = min;
	}

	public T getSum() {
		return sum;
	}

	public void setSum(T sum) {
		this.sum = sum;
	}

	public T getAverage() {
		return average;
	}

	public void setAverage(T average) {
		this.average = average;
	}

	public T getP95() {
		return p95;
	}

	public void setP95(T p95) {
		this.p95 = p95;
	}

	public T getP5() {
		return p5;
	}

	public void setP5(T p5) {
		this.p5 = p5;
	}


	public boolean isNull() {
		if (dataType == null) {
			return true;
		}
		if (DataTypeEnum.DOUBLE.getType().equals(dataType) || DataTypeEnum.LONG.getType().equals(dataType)) {
			if (this.metricId != null
					&& this.average != null
					&& this.max != null
					&& this.min != null
					&& this.p5 != null
					&& this.p95 != null
					&& this.sum != null
					&& this.ttl != 0
					&& this.table != null
					&& this.time != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "RollupVo{" +
				"num=" + num +
				", max=" + max +
				", min=" + min +
				", average=" + average +
				", sum=" + sum +
				", p95=" + p95 +
				", p5=" + p5 +
				", metricId='" + metricId + '\'' +
				", table='" + table + '\'' +
				", time=" + time +
				'}';
	}
}

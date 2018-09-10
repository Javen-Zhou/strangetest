package com.techsure.strange.chronicle;

/**
 * @author zhoujian
 * @Date 2018/8/28 星期二 16:19
 */
public enum TopicEnum {
	/**
	 * 一个rollUp的topic
	 */
	ROLLUP("rollup"),
	/**
	 * 指标id
	 */
	METRIC_ID("metricId"),
	/**
	 * 键空间
	 */
	TENANT("tenant"),
	/**
	 * 表
	 */
	TABLE("table"),
	/**
	 * 值
	 */
	VALUE("value"),
	/**
	 * 生存时间
	 */
	TTL("ttl"),
	/**
	 * 数据类型,double,long,string
	 */
	DATA_TYPE("dataType"),
	/**
	 * 产生时间
	 */
	TIME("time"),
	/**
	 * 最大值
	 */
	MAX("max"),
	/**
	 * 最小值
	 */
	MIN("min"),
	/**
	 * 总和
	 */
	SUM("sum"),
	/**
	 * 数量
	 */
	NUM("num"),
	/**
	 * 平均值
	 */
	AVERAGE("average"),
	/**
	 * p5值
	 */
	P5("p5"),
	/**
	 * p95值
	 */
	P95("p95");

	private String topic;

	TopicEnum(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}
}

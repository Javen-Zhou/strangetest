package com.techsure.strange.hazelcast.assist;

import java.io.Serializable;

/**
 * @author zhoujian
 * @Date 2018/8/26 星期日 17:23
 */
public class CommonVo implements Serializable{
	/**
	 * cassandra键空间
	 */
	protected String tenant;
	/**
	 * 表名
	 */
	protected String table;
	/**
	 * 指标id
	 */
	protected String metricId;
	/**
	 * 数据类型
	 */
	protected String dataType;
	/**
	 * 产生时间
	 */
	protected Long time;
	/**
	 * 生存时间
	 */
	protected Integer ttl;
	/**
	 * 值
	 */
	protected Object value;


	protected String target;


	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getMetricId() {
		return metricId;
	}

	public void setMetricId(String metricId) {
		this.metricId = metricId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}



	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}

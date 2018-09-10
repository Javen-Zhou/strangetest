package com.techsure.strange.netty;

/**
 * @author zhoujian
 * @Date 2018/8/26 星期日 17:29
 */
public enum RollUpTypeEnum {
	/**
	 * 五分钟
	 */
	FIVE_MINUTE("fiveMinute"),
	/**
	 * 一小时
	 */
	ONE_HOUR("oneHour"),
	/**
	 * 八小时
	 */
	EIGHT_HOUR("eightHour"),
	/**
	 * 一天
	 */
	ONE_DAY("oneDay");

	private String type;

	RollUpTypeEnum(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

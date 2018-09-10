package com.techsure.strange.hazelcast.assist;

/**
 * @author zhoujian
 * @Date 2018/8/26 星期日 17:19
 */
public enum DataTypeEnum {
	/**
	 * double类型
	 */
	DOUBLE("Double"),
	/**
	 * Long类型
	 */
	LONG("Long"),
	/**
	 * String类型
	 */
	STRING("String");

	private String type;

	DataTypeEnum(String type) {
		this.type = type;
	}

	public java.lang.String getType() {
		return type;
	}

	public static DataTypeEnum from(String value) {
		DataTypeEnum[] arr = DataTypeEnum.values();
		for (DataTypeEnum s : arr) {
			if (value != null && value.equalsIgnoreCase(s.type)) {
				return s;
			}
		}
		throw new IllegalArgumentException();
	}
}

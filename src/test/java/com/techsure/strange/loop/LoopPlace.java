package com.techsure.strange.loop;

import java.io.Serializable;

/**
 * @author zhoujian
 * @Date 2018/8/23 19:28
 */
public class LoopPlace implements Serializable {
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 在集群中的loop值
	 */
	private Integer place;

	private Boolean isReady;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public Boolean isReady() {
		return isReady;
	}

	public void setReady(Boolean ready) {
		isReady = ready;
	}
}

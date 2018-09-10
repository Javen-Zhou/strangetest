package com.techsure.strange.netty;

import com.techsure.strange.hazelcast.assist.RollupVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/8/27 星期一 16:16
 */
public class TransportVo implements Serializable {
	private static final long serialVersionUID = -9172774392245257999L;

	private List<RollupVo> rollupVoList;

	private RollUpTypeEnum type;

	public TransportVo(){

	}

	public TransportVo(RollUpTypeEnum type, List<RollupVo> rollupVoList) {
		this.type = type;
		this.rollupVoList = rollupVoList;
	}

	public List<RollupVo> getRollupVoList() {
		return rollupVoList;
	}

	public TransportVo setRollupVoList(List<RollupVo> rollupVoList) {
		this.rollupVoList = rollupVoList;
		return this;
	}

	public RollUpTypeEnum getType() {
		return type;
	}

	public void setType(RollUpTypeEnum type) {
		this.type = type;
	}
}

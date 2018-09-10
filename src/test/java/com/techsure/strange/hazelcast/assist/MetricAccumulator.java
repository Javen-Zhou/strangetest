package com.techsure.strange.hazelcast.assist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/8/29 星期三 11:53
 */
public class MetricAccumulator implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(MetricAccumulator.class);
	private List<Object> valueList;

	private String dataType;
	private String table;
	private String tenant;
	private String target;
	private String metricId;
	private Long time;
	private boolean isInit = false;

	public MetricAccumulator() {
		valueList = new ArrayList<>();
	}

	/**
	 * 接收数据
	 */
	public <T extends CommonVo> MetricAccumulator accumulate(T t) {
		if (!isInit) {
			dataType = t.getDataType();
			table = t.getTable();
			tenant = t.getTenant();
			target = t.getTarget();
			metricId = t.getMetricId();
			isInit = true;
		}
		time = t.getTime();
		if (DataTypeEnum.DOUBLE.getType().equals(dataType)) {
			if (t.getValue() instanceof Double) {
				valueList.add(new BigDecimal((Double) t.getValue()));
			} else {
				valueList.add(t.getValue());
			}
		} else {
			valueList.add(t.getValue());
		}
		return this;
	}

	/**
	 * 时间窗口结束，计算归档数据并返回
	 *
	 * @return 归档数据
	 */
	public RollupVo finish() {

		RollupVo rollupVo = new RollupVo();
		rollupVo.setDataType(dataType);
		rollupVo.setTable(table);
		rollupVo.setTarget(target);
		rollupVo.setTenant(tenant);
		rollupVo.setMetricId(metricId);
		rollupVo.setTime(time);
		try {
			if (!valueList.isEmpty()) {
				CalculateUtils.compute(rollupVo, valueList);
			}
		} catch (Exception e) {
			logger.error("error data:metricId={},dataType={},value={}", metricId, dataType, valueList);
			logger.error(e.getMessage(), e);
		}
		return rollupVo;
	}
}
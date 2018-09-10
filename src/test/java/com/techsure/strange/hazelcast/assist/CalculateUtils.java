package com.techsure.strange.hazelcast.assist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/7/19 11:33
 */
public class CalculateUtils {
	private static final Logger logger = LoggerFactory.getLogger(CalculateUtils.class);

	@SuppressWarnings("unchecked")
	public static void compute(RollupVo rollupVo, List list) {
		DataTypeEnum dataType = DataTypeEnum.from(rollupVo.getDataType());
		switch (dataType) {
			case DOUBLE:
				calculateDouble(rollupVo, list);
				break;
			case LONG:
				calculateLong(rollupVo, list);
				break;
			default:
				break;
		}
	}

	private static void calculateDouble(RollupVo<Double> rollupVo, List<BigDecimal> list) {
		try {
			BigDecimal sum = new BigDecimal(0);
			list.sort(BigDecimal::compareTo);
			Map<Integer, BigDecimal> map = new HashMap<>(10);
			for (int i = 0; i < list.size(); i++) {
				map.put(i, list.get(i));
				sum = sum.add(list.get(i));
			}
			rollupVo.setSum(sum.doubleValue());
			rollupVo.setAverage(sum.divide(new BigDecimal(list.size()), 2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
			rollupVo.setMax(map.get(list.size() - 1).doubleValue());
			rollupVo.setMin(map.get(0).doubleValue());
			rollupVo.setNum(list.size());
			int index95 = list.size() * 95 / 100 - 1;
			//下标从0开始，所以要减1
			if (index95 < 0) {
				index95 = 0;
			}
			int index5 = list.size() * 5 / 100;
			rollupVo.setP5(map.get(index5).doubleValue());
			rollupVo.setP95(map.get(index95).doubleValue());
		} catch (Exception e) {
			logger.error("error data:metricId={},dataType={},value={}", rollupVo.getMetricId(), rollupVo.getDataType(), list);
			logger.error(e.getMessage(), e);
		}
	}

	private static void calculateLong(RollupVo<Long> rollupVo, List<Long> list) {
		try {

			Long sum = 0L;
			list.sort((o1, o2) -> o1.equals(o2) ? 0 : (o1 < o2 ? -1 : 1));
			Map<Integer, Long> map = new HashMap<>(12);
			for (int i = 0; i < list.size(); i++) {
				map.put(i, list.get(i));
				sum += list.get(i);
			}
			rollupVo.setSum(sum);
			rollupVo.setSum(sum / list.size());
			rollupVo.setMax(map.get(list.size() - 1));
			rollupVo.setMin(map.get(0));

			int index95 = list.size() * 95 / 100 - 1;
			//下标从0开始，所以要减1
			if (index95 < 0) {
				index95 = 0;
			}
			int index5 = list.size() * 5 / 100;
			rollupVo.setP5(map.get(index5));
			rollupVo.setP95(map.get(index95));
		} catch (Exception e) {
			logger.error("error data:metricId={},dataType={},value={}", rollupVo.getMetricId(), rollupVo.getDataType(), list);
			logger.error(e.getMessage(), e);
		}
	}

}

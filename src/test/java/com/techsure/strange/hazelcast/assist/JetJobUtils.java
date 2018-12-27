package com.techsure.strange.hazelcast.assist;

import com.hazelcast.jet.aggregate.AggregateOperation;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.function.DistributedBiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/29 星期三 12:03
 */
public class JetJobUtils {
	private static final Logger logger = LoggerFactory.getLogger(JetJobUtils.class);

	/**
	 * 个性化计算处理metric
	 */
	public static <T extends CommonVo> AggregateOperation1<T, MetricAccumulator, RollupVo> calculateMetric() {
		return AggregateOperation
				.withCreate(MetricAccumulator::new)
				.andAccumulate((DistributedBiConsumer<MetricAccumulator, T>) MetricAccumulator::accumulate)
				.andExportFinish(MetricAccumulator::export);
	}

}

package com.techsure.strange.basetype;

import com.techsure.strange.hazelcast.assist.CalculateUtils;
import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.MetricAccumulator;
import com.techsure.strange.hazelcast.assist.RollupVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zhoujian
 * @Date 2018/9/5 星期三 10:05
 */
public class StreamTest {
	private static final Logger logger = LoggerFactory.getLogger(StreamTest.class);

	@Test
	public void testStream() throws InterruptedException {
		List<RollupVo> rollupVoList = new ArrayList<>();
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 12; j++) {
				RollupVo rollupVo = new RollupVo();
				rollupVo.setTime((long) j * 1000);
				rollupVo.setMetricId(String.valueOf(i));
				rollupVo.setP95(new Double(i + j));
				rollupVo.setTenant("monitor");
				rollupVo.setTable("asm_double");
				rollupVo.setDataType(DataTypeEnum.DOUBLE.getType());
				rollupVoList.add(rollupVo);
			}
		}
		/*(Supplier<List<T>>) ArrayList::new, List::add,
				(left, right) -> { left.addAll(right); return left;*/
		rollupVoList.parallelStream().collect(
				Collectors.groupingBy(
						f -> Arrays.asList(f.getMetricId(), f.getTenant(), f.getTable(), f.getDataType(), f.getTtl()),
						new MetricCollector<RollupVo, Number>((list, item) -> {
							if (DataTypeEnum.DOUBLE.getType().equals(item.getDataType())) {
								if (item.getValue() instanceof Double) {
									list.add(new BigDecimal((Double) item.getValue()));
								} else {
									list.add((Number) item.getValue());
								}
							} else {
								list.add((Number) item.getValue());
							}
						}))
		).entrySet().stream().map(
				e -> {
					RollupVo rollupVo = new RollupVo();
					rollupVo.setMetricId((String) e.getKey().get(0));
					rollupVo.setTenant((String) e.getKey().get(1));
					rollupVo.setTable((String) e.getKey().get(2));
					rollupVo.setDataType((String) e.getKey().get(3));
					rollupVo.setTtl((Integer) e.getKey().get(4));
					CalculateUtils.compute(rollupVo, e.getValue());
					logger.info(String.valueOf(rollupVo));
					return rollupVo;
				}
		);

		logger.info("consume:{}", System.currentTimeMillis() - begin);
	}
}

class MetricCollector<T, A> implements Collector<T, List<A>, List<A>> {
	BiConsumer<List<A>, T> accumulator;

	public MetricCollector(BiConsumer<List<A>, T> accumulator) {
		this.accumulator = accumulator;
	}

	@Override
	public Supplier<List<A>> supplier() {
		return ArrayList::new;
	}

	@Override
	public BiConsumer<List<A>, T> accumulator() {
		return accumulator;
	}

	@Override
	public BinaryOperator<List<A>> combiner() {
		return (left, right) -> {
			left.addAll(right);
			return left;
		};
	}

	@Override
	public Function<List<A>, List<A>> finisher() {
		return Function.identity();
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
	}
}

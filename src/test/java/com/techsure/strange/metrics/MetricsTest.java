package com.techsure.strange.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/17 18:59
 */
public class MetricsTest {
	private static MetricRegistry metricRegistry = new MetricRegistry();

	/**
	 * 在控制台上打印输出
	 */
	private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).build();

	@Test
	public void testGauge() throws InterruptedException {
		reporter.start(3, TimeUnit.SECONDS);
		LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<Integer>();

		Gauge<Integer> gauge = queue::size;
		metricRegistry.register("test",gauge);

		//测试JMX
		JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
		jmxReporter.start();

		for(int i=0;i<100;i++){
			queue.put(Integer.valueOf(i));
		}
	}
}

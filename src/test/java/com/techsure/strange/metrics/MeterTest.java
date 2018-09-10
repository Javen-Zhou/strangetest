package com.techsure.strange.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/9/2 星期日 15:34
 */
public class MeterTest {
	private static final Logger logger = LoggerFactory.getLogger(MeterTest.class);

	@Test
	public void instanceA() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		MetricRegistry metrics = new MetricRegistry();
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
		Meter requests = metrics.meter("queue.put");
		reporter.start(3, TimeUnit.SECONDS);
		for(int i=0;i<100;i++){
			requests.mark();
		}
		while(true){
			TimeUnit.SECONDS.sleep(1L);
		}
	}

	@Test
	public void instanceB() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		MetricRegistry metrics = new MetricRegistry();
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();
		Meter requests = metrics.meter("queue.put");
		reporter.start(5, TimeUnit.SECONDS);
		while(true){
			TimeUnit.SECONDS.sleep(1L);
		}
	}
}

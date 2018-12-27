package com.techsure.strange.hazelcast.jet;

import com.hazelcast.config.Config;
import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.datamodel.TimestampedEntry;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sink;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.WindowDefinition;
import com.techsure.strange.hazelcast.assist.AttributeVo;
import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.JetJobUtils;
import com.techsure.strange.hazelcast.assist.RollupVo;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_CURRENT;
import static com.hazelcast.jet.pipeline.SinkBuilder.sinkBuilder;

/**
 * @author zhoujian
 * @Date 2018/12/4 18:03
 */
public class CapacityTest {
	private static final Logger logger = LoggerFactory.getLogger(CapacityTest.class);

	private static final String FIVE_MINUTE_MAP_NAME = "five-minute-map-name";
	private static final String FIVE_MINUTE_JOB_NAME = "five-minute-job-name";
	private static BlockingQueue queue = new LinkedBlockingDeque();

	@Before
	public void before() throws IOException {
		File file = new File("E:\\Tmp\\jet");
		file.deleteOnExit();

		if(!file.exists()){
			file.mkdirs();
		}
	}

	@Test
	public void testCapacity() throws InterruptedException {

		JetInstance jetInstance = Jet.newJetInstance(getJetConfig());
		jetInstance.newJob(buildPipeline(), getJobConfig());

		IMapJet<String, AttributeVo> map = jetInstance.getMap(FIVE_MINUTE_MAP_NAME);

		TimeUnit.SECONDS.sleep(5L);
		Long ctime = System.currentTimeMillis();
			for (int i = 0; i < 200; i++) {
				for(int j=0;j<5;j++){
					AttributeVo attributeVo = new AttributeVo();
					attributeVo.setDataType(DataTypeEnum.DOUBLE.getType());
					attributeVo.setTime(ctime + i*j*1000);
					attributeVo.setValue(BigDecimal.valueOf((long)j));
					attributeVo.setMetricId(String.valueOf(i));
					attributeVo.setTable("asm");
					attributeVo.setTenant("monitor");
					attributeVo.setTarget("test");
					map.set(attributeVo.getMetricId(),attributeVo);
				}
			}
		logger.info("set all complete");
		while (true){
				if(System.currentTimeMillis() % TimeUnit.MINUTES.toMillis(1) == 0){
					break;
				}
		}
		logger.info("all complete");
		TimeUnit.SECONDS.sleep(5L);
		for (int i = 0; i < 200; i++) {
				AttributeVo attributeVo = new AttributeVo();
				attributeVo.setDataType(DataTypeEnum.DOUBLE.getType());
				attributeVo.setTime(ctime + i * 1000);
				attributeVo.setValue(BigDecimal.valueOf((long)1));
				attributeVo.setMetricId(String.valueOf(i));
				attributeVo.setTable("asm");
				attributeVo.setTenant("monitor");
				attributeVo.setTarget("test");
				map.set(attributeVo.getMetricId(),attributeVo);
		}
		logger.info("complete");
		while(true){
			TimeUnit.SECONDS.sleep(3L);
		}
	}

	private JetConfig getJetConfig() {
		JetConfig cfg = new JetConfig();

		Config hzConfig = new Config();
		//添加五分钟归档作业的配置
		hzConfig.getMapEventJournalConfig(FIVE_MINUTE_MAP_NAME)
				.setEnabled(true)
				.setCapacity(500000)
				.setTimeToLiveSeconds(0);
		cfg.setHazelcastConfig(hzConfig);
		return cfg;
	}

	private  JobConfig getJobConfig() {
		JobConfig jobConfig = new JobConfig();
		//设置作业名称
		jobConfig.setName(FIVE_MINUTE_JOB_NAME);
	/*	//启用Jet容错
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		//设置快照拍摄间隔
		jobConfig.setSnapshotIntervalMillis(TimeUnit.MINUTES.toMillis(1));
		//启用裂脑保护
		jobConfig.setSplitBrainProtection(true);*/
		return jobConfig;
	}

	private  Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<AttributeVo, String, AttributeVo>mapJournal(FIVE_MINUTE_MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_CURRENT))
				//每个数据的时间戳，用以划分时间窗口
				.addTimestamps()
				//定义时间窗口的大小
				.window(WindowDefinition.tumbling(TimeUnit.MINUTES.toMillis(1)))
				.groupingKey(AttributeVo::getMetricId)
				.aggregate(JetJobUtils.calculateMetric())
				.drainTo(createCustomSink());
		return p;
	}

	private static Sink<TimestampedEntry<String, RollupVo>> createCustomSink(){
		Sink<TimestampedEntry<String, RollupVo>> sink = sinkBuilder(
				"fiveMinuteJob-sink", x -> LoggerFactory.getLogger(CapacityTest.class))
				.receiveFn((Logger logger,TimestampedEntry<String, RollupVo> item) -> {logger.debug(item.getValue().toString());})
				.build();
		return sink;
	}
}

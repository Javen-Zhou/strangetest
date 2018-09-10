package com.techsure.strange.hazelcast.list;

import com.hazelcast.core.IList;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/9/4 星期二 16:24
 */
public class DestroyTest {
	private static final Logger logger = LoggerFactory.getLogger(DestroyTest.class);
	@Test
	public void instanceA(){
		JetInstance jetA = Jet.newJetInstance();
		IList list = jetA.getList("list");

		JetInstance jetB = Jet.newJetInstance();
		IList listB = jetB.getList("list");

		list.destroy();
		listB.destroy();

		logger.info(String.valueOf(list));
	}

	public void instanceB(){

	}
}

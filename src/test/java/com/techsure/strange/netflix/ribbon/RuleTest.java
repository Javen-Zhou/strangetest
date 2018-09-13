package com.techsure.strange.netflix.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zhoujian
 * @Date 2018/9/12 星期三 15:22
 */
public class RuleTest {
	private static final Logger logger = LoggerFactory.getLogger(RuleTest.class);

	@Test
	public void testRound() throws IOException {
		ConfigurationManager.getConfigInstance()
				.setProperty("MyRibbonClient.ribbon.listOfServers", "localhost:8080,localhost:8082");
		//ConfigurationManager.loadCascadedPropertiesFromResources("ribbon-client");
		ILoadBalancer loadBalancer = ClientFactory.getNamedLoadBalancer("MyRibbonClient");
		IRule chooseRule = new RoundRobinRule();
		chooseRule.setLoadBalancer(loadBalancer);
		for (int i = 0; i < 10; i++) {
			Server server = chooseRule.choose(null);
			logger.info("request:{}", server.getHostPort());
		}
	}

	@Test
	public void testAvailability() {
		ConfigurationManager.getConfigInstance()
				.setProperty("MyRibbonClient.ribbon.listOfServers", "localhost:8080,localhost:8082");
		ILoadBalancer loadBalancer = ClientFactory.getNamedLoadBalancer("MyRibbonClient");
		IRule chooseRule = new AvailabilityFilteringRule();
		chooseRule.setLoadBalancer(loadBalancer);
		for (int i = 0; i < 10; i++) {
			Server server = chooseRule.choose(null);
			logger.info("request:{}", server.getHostPort());
		}
	}


}

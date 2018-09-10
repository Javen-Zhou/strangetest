package com.techsure.strange.cassandra.session;

import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.*;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujian
 * @Date 2018/8/27 星期一 0:07
 */
public class CassandraSelfSession {
	private static final Logger logger = LoggerFactory.getLogger(CassandraSelfSession.class);

	private Map<String, Session> connPool = new ConcurrentHashMap<>();
	private Cluster cluster;
	private ConsistencyLevel consistencyLevel = ConsistencyLevel.ONE;

	public void initCassandra() {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions
				.setConnectionsPerHost(HostDistance.LOCAL, 4, 10)
				.setConnectionsPerHost(HostDistance.REMOTE, 4, 10);

		SocketOptions socketOptions = new SocketOptions();
		socketOptions
				.setConnectTimeoutMillis(60000)
				.setKeepAlive(true)
				.setReadTimeoutMillis(60000);
		ExponentialReconnectionPolicy reconnectionPolicy = new ExponentialReconnectionPolicy(100, 1000);
		this.cluster = Cluster
				.builder()
				.addContactPoints("192.168.148.128")
				.withSocketOptions(socketOptions)
				.withPoolingOptions(poolingOptions)
				.withProtocolVersion(ProtocolVersion.NEWEST_SUPPORTED)
				.withReconnectionPolicy(reconnectionPolicy)
				.build();
	}

	synchronized public void createNewKeySpace(String cql) {
		Session session = null;
		try {
			session = this.cluster.newSession();
			session.execute(cql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private Session getSession(String keySpace) {
		Session temp = connPool.get(keySpace);
		if (temp == null) {
			try {
				temp = cluster.connect(keySpace);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			connPool.put(keySpace, temp);
		}
		return temp;
	}

	public Session getSession2(String keySpace){
		return cluster.connect(keySpace);
	}

	public synchronized ResultSet execute(String tenant, String cql) {
		Session session = getSession(tenant);
		ResultSet result = null;
		try {
			if (session != null) {
				result = session.execute(
						new SimpleStatement(cql)
								.setConsistencyLevel(consistencyLevel));
			}
		} catch (WriteFailureException | ReadFailureException | ReadTimeoutException | WriteTimeoutException | NoHostAvailableException e) {
			logger.error(e.getMessage(), e);
			consistencyLevel = ConsistencyLevel.ANY;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public ResultSetFuture executeAsync(String tenant, String cql) {
		Session session = getSession(tenant);
		ResultSetFuture result = null;
		try {
			if (session != null) {
				result = session.executeAsync(
						new SimpleStatement(cql)
								.setConsistencyLevel(consistencyLevel));
			}
		} catch (WriteFailureException | ReadFailureException | ReadTimeoutException | WriteTimeoutException | NoHostAvailableException e) {
			logger.error(e.getMessage(), e);
			consistencyLevel = ConsistencyLevel.ANY;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}

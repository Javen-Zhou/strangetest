package com.techsure.strange.cassandra.session;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cassandra.config.CassandraCqlSessionFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;

/**
 * @author zhoujian
 * @Date 2018/9/3 星期一 15:24
 */
public class CassandraTest {
	private static final Logger logger = LoggerFactory.getLogger(CassandraTest.class);


	@Test
	public void testBatch() {
		CassandraSelfSession selfSession = new CassandraSelfSession();
		selfSession.initCassandra();

		Session session1 = selfSession.getSession2("monitor");
		Session session12 = selfSession.getSession2("monitor");
		/*Session session1 = selfSession.getSession2("monitor");
		Session session2 = selfSession.getSession2("monitor");
*/

		/*StringBuilder batchCql = new StringBuilder("BEGIN BATCH ");
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			batchCql.append("insert into asm_double (metric_id,time,value) VALUES('")
					.append(i)
					.append("',")
					.append(System.currentTimeMillis())
					.append(",")
					.append(new Double(i))
					.append(")");

			if (batchCql.length() > 50000) {
				batchCql.append("APPLY BATCH;");
				session1.execute("monitor", batchCql.toString());
				batchCql.delete(12, batchCql.length());
			}
		}
		batchCql.append("APPLY BATCH;");
		session1.execute("monitor", batchCql.toString());
		batchCql.delete(12, batchCql.length());
		logger.info("batch consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 20000; i < 30000; i++) {
			batchCql.append("insert into asm_double (metric_id,time,value) VALUES('")
					.append(i)
					.append("',")
					.append(System.currentTimeMillis())
					.append(",")
					.append(new Double(i))
					.append(")");

			if (batchCql.length() > 100000) {
				batchCql.append("APPLY BATCH;");
				selfSession.execute("monitor", batchCql.toString());
				batchCql.delete(12, batchCql.length());
			}
		}
		batchCql.append("APPLY BATCH;");
		selfSession.execute("monitor", batchCql.toString());
		batchCql.delete(12, batchCql.length());
		logger.info("200000 batch consume:{}", System.currentTimeMillis() - begin);
*/

		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			begin = System.currentTimeMillis();
			String cql = "insert into asm_double(metric_id,time,value)VALUES('" + i + "'," + System.currentTimeMillis() + "," + new Double(i) + ")";
			session1.execute(
					new SimpleStatement(cql)
							.setConsistencyLevel(ConsistencyLevel.ONE));
		}
		logger.info("single consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 10000; i < 20000; i++) {
			String cql = "insert into asm_double(metric_id,time,value)VALUES('" + i + "'," + System.currentTimeMillis() + "," + new Double(i) + ")";
			session12.execute(
					new SimpleStatement(cql)
							.setConsistencyLevel(ConsistencyLevel.ONE));
		}
		logger.info("single consume:{}", System.currentTimeMillis() - begin);
	}
}

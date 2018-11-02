package com.techsure.strange.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author zhoujian
 * @Date 2018/10/26 16:05
 */
public class HtmlEmailTest {
	public static final Logger logger = LoggerFactory.getLogger(HtmlEmailTest.class);

	@Test
	public void testSendHtmlEmail() throws EmailException, IOException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.exmail.qq.com");
		email.setSmtpPort(25);
		email.setCharset("UTF-8");
		email.setAuthentication("test@techsure.com.cn", "Techsure2501");
		email.setFrom("test@techsure.com.cn", "事件平台");
		email.addTo("zhoujian@techsure.com.cn");
		email.setSubject("Re:测试标题");
		email.setHtmlMsg("<div>测试邮件内容</div>");
		email.embed(new File("E:\\Tmp\\application.properties"));
		File file = new File("E:\\Tmp\\application.properties");
		logger.info(file.getAbsolutePath());
		logger.info(file.getCanonicalPath());
		logger.info(file.getParent());
		logger.info(file.getParentFile().getPath());
		logger.info(email.toString());
		email.send();
	}


	@Test
	public void testSystemEnv(){
		logger.info(System.getenv("TECHSURE_HOME"));
		logger.info(System.getenv("JAVA_HOME"));
	}
}

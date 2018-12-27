package com.techsure.strange.exercise;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/11/20 20:00
 */
public class PatternTest {
	private static final Logger logger = LoggerFactory.getLogger(PatternTest.class);

	@Test
	public void testPattern() {
		String text = "(1).建立mount点，mount点名称由需求者提供，通用格式例如：#{i:mountpoint}\n" +
				"(2).执行mount操作，命令格式例如：\n" +
				"# mount -t nfs -o rw,soft,intr,timeo=600,retry=3 #{i:ip}:#{i:volume} #{i:mountpoint}\n" +
				"#df –h  检测文件系统中是否有#{i:ip}: #{i:volume} 字样\n" +
				"(3).测试NAS读写权限，以为mount点#{i:mountpoint}为例：\n" +
				"  # cd #{i:mountpoint};mkdir test (创建一个test目录)\n" +
				"  # rmdir test (删除test目录)\n" +
				"  如果以上测试正常完成，则读写正常，否则需要找存储工程师确认权限情况。\n" +
				"(4).将挂载命令写入/etc/rc.local，以便OS重启能自动挂载NAS\n" +
				"echo “ mount -t nfs -o rw,soft,intr,timeo=600,retry=3  #{i:ip}:#{i:volume}   #{i:mountpoint}” >>/etc/rc.local\n" +
				"(5).以上操作在每台需要mount此NAS文件系统的主机上重复操作一次\n" +
				"(6).分配一个新IP：#{o:ip}\n";

		Map<String, String> map = new HashMap<>();
		map.put("ip", "172.26.10.11");
		map.put("volume", "/CBS_KBS");
		map.put("mountpoint", "/nfsc/CBS_KBS");

		StringBuffer sb = new StringBuffer();

		Pattern pi = Pattern.compile("#\\{i:(.+?)\\}");
		Pattern po = Pattern.compile("#\\{o:(.+?)\\}");

		Matcher m = pi.matcher(text);
		while (m.find()) {
			m.appendReplacement(sb, map.get(m.group(1)));
		}
		m.appendTail(sb);

		StringBuffer sb2 = new StringBuffer();
		m = po.matcher(sb.toString());

		String oInput = "<input type=”text” name=\"\">";
		Pattern poInput = Pattern.compile("name=\"(.*)\"");
		Matcher poMatch = poInput.matcher(oInput);

		while (m.find()) {
			String tmp = m.group(1);
			m.appendReplacement(sb2, poMatch.replaceAll("name=\"" + map.get(tmp) + "\""));
		}
		m.appendTail(sb2);
		logger.info(sb2.toString());
	}
}

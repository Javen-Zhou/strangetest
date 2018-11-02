package com.techsure.strange.simple;

import org.junit.Test;

import java.io.*;

/**
 * @author zhoujian
 * @Date 2018/10/22 17:03
 */
public class StringWriterTest {

	@Test
	public void generateHtml() throws IOException {
		File file = new File("E:\\Tmp\\test.html");
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.write(
				"<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">\n");
		out.write("<head>\n");
		out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></meta>\n");
		out.write("<style type=\"text/css\">\n");
		out.write("html {font-family: \"PingFang SC\", \"Helvetica Neue\", \"思源黑体\", \"Microsoft YaHei\", \"微软雅黑\", Helvetica;line-height: 1.42857143; color: #666666;font-size: 14px;}\n");
		out.write(
				"table{width: 100%; max-width: 100%; margin-bottom: 10px; margin-top: 0;border-collapse:collapse;border-spacing:0;border-top:1px solid #ddd;}\n");
		out.write("th,td{padding: 8px; line-height: 1.42857143;  vertical-align: top; border-top: 1px solid #dddddd;}\n");
		out.write("th{text-align: left;color: #999999;}\n");
		out.write(".table-condensed th,.table-condensed td{padding: 5px;}\n");
		out.write("div.well {  min-height: 20px; padding: 19px; line-height: 1.8; border-radius: 4px;  background: #fffdf2; border: 1px solid #ffd821;box-shadow: 0 0 5px 0 rgba(0,0,0,0.10); border-radius: 5px;}\n");
		out.write(".text-primary { color: #336eff;}\n");
		out.write("</style>\n");
		out.write("</head>\n");
		out.write("<body>\n");

		out.write("<div style=\"width:100%;text-align:center;font-size:22px;font-weight:bold\">" + "这是标题" + "</div>");



		out.write("\n</body>\n</html>");

		out.flush();
		out.close();

	}
}

package com.techsure.strange.basetype;


import java.io.*;

/**
 * @author zhoujian
 * @Date 2018/11/19 16:04
 */
public class AutoCloseTest {

	public void testAutoClose(){
		try(InputStream is = new FileInputStream(new File("test"));
			InputStream os = new FileInputStream(new File("test2"))){

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

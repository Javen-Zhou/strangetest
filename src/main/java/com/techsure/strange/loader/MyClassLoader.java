package com.techsure.strange.loader;

/**
 * @author zhoujian
 * @Date 2019/9/4 11:54
 */
public class MyClassLoader extends Thread {
	/*@Override
	public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
		Class c = findLoadedClass(name);
		if(c == null){
			try{
				if(getParent() != null){
					c = getParent().loadClass(name,false);
				}else{
					c =  findBootStrapClassOrNull(name);
				}
			}catch (ClassNotFoundException e){
				System.err.println(e.getCause().getMessage());
			}
			if(c == null){
				c = findClass(name);
			}
		}
		if(resolve){
			resloveClass(c);
		}
		return c;
	}*/
}

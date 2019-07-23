package com.techsure.strange.basetype;

import org.jetbrains.annotations.Contract;

import java.util.Objects;


/**
 * @author zhoujian
 * @Date 2019/7/11 12:06
 */
public  interface InterfaceTest<K,V> {
	void test1();
	@Contract(mutates="this")
	default boolean replace(K key, V oldValue, V newValue) {
		Object curValue = get(key);
		if (!Objects.equals(curValue, oldValue) ||
				(curValue == null && !containsKey(key))) {
			return false;
		}
		put(key, newValue);
		return true;
	}

	void put(K key, V newValue);

	boolean containsKey(K key);

	Object get(K key);


}

interface InterfaceTest2<K,V> extends InterfaceTest<K,V>{

}

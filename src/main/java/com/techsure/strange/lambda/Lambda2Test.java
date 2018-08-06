package com.techsure.strange.lambda;

import java.util.function.Function;

/**
 * @author zhoujian
 * @Date 2018/7/16 17:27
 */
public class Lambda2Test {

	public static void main(String[] args){
		Crei2<Integer, String> crei2 = new Crei2Impl<>(2,"s1");
		System.out.println(String.valueOf(crei2.getName()));
		System.out.println(crei2.getName2());

	}

}

final class Util{
	public static <K,V> Function<Crei2<K,V>,V> putEvent(){
		return new Function<Crei2<K, V>, V>() {
			@Override
			public V apply(Crei2<K, V> kvCrei2) {
				return kvCrei2.getName();
			}
		};
	}
}

interface Crei2<K ,V>{
	V getName();

	K getName2();
}

class Crei2Impl<K,V> implements Crei2{

	private K key;
	private V value;

	public Crei2Impl(){
		super();
	}

	public Crei2Impl(K key,V value){
		this.key = key;
		this.value = value;
	}


	@Override
	public K getName() {
		return key;
	}

	@Override
	public V getName2() {
		return value;
	}
}

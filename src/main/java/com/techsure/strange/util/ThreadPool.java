package com.techsure.strange.util;

import java.util.concurrent.*;

/**
 * @author zhoujian
 * @Date 2018/7/6 19:13
 */
public class ThreadPool {
	private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(10,10,0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingDeque<>(30),r -> new Thread(r,"Thread-Pool"));

	public static void execute(Runnable runnable){
		THREAD_POOL.execute(runnable);
	}

	public static <V> Future<V> execute(Callable<V> callable){
		return THREAD_POOL.submit(callable);
	}
}

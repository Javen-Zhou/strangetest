package com.techsure.strange.threadpool;

import jnr.ffi.annotations.In;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhoujian
 * @Date 2018/8/23 10:04
 */
public class ThreadPoolTest {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);

	private BlockingQueue queue = new LinkedBlockingDeque(5);

	/**
	 * ExecutorService是实现线程池的重要接口，实现这个接口的子类有ThreadPoolExecutor(普通线程池)和ScheduleTreadPoolExecutor
	 */
	ThreadPoolExecutor threadPool1 = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, queue,
			r -> {
				Thread thread = new Thread(r);
				thread.setDaemon(false);
				thread.setName("Test-Pool1-" + thread.getName());

				return thread;
			},
			new ThreadPoolExecutor.DiscardOldestPolicy());

	//定时任务的线程池
	ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(20, r -> {
		Thread thread = new Thread(r);
		thread.setDaemon(false);
		thread.setName("Test-Pool2-" + thread.getName());
		return thread;
	}, new ThreadPoolExecutor.DiscardOldestPolicy());

	@Test
	public void testCallBack() throws ExecutionException, InterruptedException {
		List<Future<Integer>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Future<Integer> f = threadPool1.submit(new SearchCallBack(i));
			list.add(f);
		}
		int count = 0;
		while (list.size() > 0) {
			List<Future<Integer>> tmpList = new ArrayList<>();
			for (Future<Integer> future : list) {
				if (future.isDone()) {
					count += future.get();
				}else{
					tmpList.add(future);
				}
			}
			list = tmpList;
		}

		logger.info(String.valueOf(count));
	}

	@Test
	public void testThreadPool1() throws InterruptedException {
		for (int i = 0; i < 25; i++) {
			final Integer num = i;
			threadPool1.execute(() -> {
				logger.info(String.valueOf(num));
				try {
					TimeUnit.SECONDS.sleep(300L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			System.out.println("线程池中活跃的线程数： " + threadPool1.getPoolSize());
			if (queue.size() > 0) {
				System.out.println("----------------队列中阻塞的线程数" + queue.size());
			}
		}

		while (true) {
			TimeUnit.SECONDS.sleep(2L);
		}
	}
}

class SearchCallBack implements Callable<Integer> {

	private Integer value;

	public SearchCallBack(Integer value) {
		this.value = value;
	}

	@Override
	public Integer call() throws Exception {
		TimeUnit.SECONDS.sleep(2);
		System.out.println(value);
		return value;
	}
}

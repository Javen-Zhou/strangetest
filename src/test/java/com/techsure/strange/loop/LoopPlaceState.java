package com.techsure.strange.loop;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/23 19:25
 */
public class LoopPlaceState {
	private static final Logger logger = LoggerFactory.getLogger(LoopPlaceState.class);

	private static final String LOOP_STATE_LOGGER_FORMAT = " MetricServer cluster, which address is {}. And the place in loop is {}";

	private static final String LOCAL_POINT_ADDED_LOGGER_FORMAT = "\n\nLocal point is added to" + LOOP_STATE_LOGGER_FORMAT;
	private static final String REMOTE_POINT_ADDED_LOGGER_FORMAT = "\n\nRemote point is added to" + LOOP_STATE_LOGGER_FORMAT;
	private static final String REMOTE_POINT_QUIT_LOGGER_FORMAT = "\n\nRemote point is quit from" + LOOP_STATE_LOGGER_FORMAT;
	private static final String LOOP_MAP_NAME = "loopMap";
	private static final String LOOP_LOCK_NAME = "loopLock";
	private static final Integer LOOP_LENGTH = 1024;
	private static final Integer SERVER_PORT = 8080;

	private static IMap<String, LoopPlace> loopPlaceMap;
	private static ILock lock;
	private static List<String> lastServerList = new ArrayList<>();
	private static HazelcastInstance instance;

	/**
	 * 初始化节点
	 */
	public static void init(HazelcastInstance instance) throws InterruptedException {
		if(instance == null){
			LoopPlaceState.instance = instance;
		}
		if (loopPlaceMap == null) {
			loopPlaceMap = instance.getMap(LOOP_MAP_NAME);
		}

		if (lock == null) {
			lock = instance.getLock(LOOP_LOCK_NAME);
		}

		if (loopPlaceMap == null || lock == null) {
			logger.error("init hazelcast failed!please check hazelcast config!");
			throw new RuntimeException();
		}

		String localAddress = instance.getCluster().getLocalMember().getAddress().getHost() + ":" + SERVER_PORT;
		LoopPlace localLoopPlace = loopPlaceMap.get(localAddress);
		if (localLoopPlace == null) {
			lock.lock();
			localLoopPlace = computeLocalLoopPlace(localAddress);
			loopPlaceMap.set(localAddress, localLoopPlace);
			logger.info(LOCAL_POINT_ADDED_LOGGER_FORMAT, localAddress, localLoopPlace.getPlace());
			lock.unlock();
		}

		startClusterCheck();

		StringBuilder metricServerClusterMessage = new StringBuilder("\n\nMetricServer Cluster {\n");
		for (LoopPlace loopPlace : loopPlaceMap.values()) {
			metricServerClusterMessage
					.append("point : ")
					.append(loopPlace.getAddress())
					.append(", place in the loop is : ")
					.append(loopPlace.getPlace())
					.append(".\n");
		}
		metricServerClusterMessage.append("}\n\nMetricServer cluster is started!\n");
		logger.info(metricServerClusterMessage.toString());

		while(true){
			TimeUnit.SECONDS.sleep(2L);
		}
	}

	/**
	 * 定时执行,检查集群节点加入或退出
	 */
	private static void startClusterCheck() {
		ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
		scheduleThreadPool.scheduleAtFixedRate(LoopPlaceState::computeClusterState, 0, 10, TimeUnit.SECONDS);
	}

	/**
	 * 计算节点加入或退出的方法
	 */
	private static void computeClusterState() {
		List<String> currentServerList = getServerList();
		//检测已退出的节点,并移除
		Boolean isQuit = true;
		for (String key : loopPlaceMap.keySet()) {
			for (String address : currentServerList) {
				if (address.equals(key)) {
					isQuit = false;
					break;
				}
			}
			if (isQuit) {
				logger.info(REMOTE_POINT_QUIT_LOGGER_FORMAT, key, loopPlaceMap.get(key));
				loopPlaceMap.remove(key);
				isQuit = true;
			}
		}


		//检测新加入的节点,日志输出
		if (lastServerList != null) {
			for (String address : currentServerList) {
				if (!lastServerList.contains(address)) {
					logger.info(REMOTE_POINT_ADDED_LOGGER_FORMAT, address, loopPlaceMap.get(address));
				}
			}
		}
		lastServerList = currentServerList;
	}

	/**
	 * 获取最新的集群节点地址列表
	 *
	 * @return 节点地址列表
	 */
	private static List<String> getServerList() {
		Set<Member> members = instance.getCluster().getMembers();
		String ip;
		String address;
		List<String> serverList = new ArrayList<>();
		for (Member member : members) {
			ip = member.getAddress().getHost();
			//member.getAddress().getPort()获取的是hazelcast端口,而不是web端口，因此使用本地端口,因此要保证节点web端口一致
			address = ip + ":" + SERVER_PORT;
			serverList.add(address);
		}

		for (String serverAddress : serverList) {
			//有节点加入,并且loopPlaceMap还未更新,递归运算
			if (!loopPlaceMap.containsKey(serverAddress)) {
				while (true) {
					if (!lock.isLocked()) {
						getServerList();
						break;
					}
				}
				break;
			}
		}
		return serverList;
	}

	/**
	 * 节点加入集群时计算本节点对应的loopPlace
	 *
	 * @param localAddress 当前节点地址
	 * @return 当前节点地址的对应loopPlace
	 */
	private static LoopPlace computeLocalLoopPlace(String localAddress) {
		List<LoopPlace> loopPlaceList = getLoopList();
		Integer size = loopPlaceList.size();
		Integer place;
		if (size == 0 || loopPlaceList.get(0).getPlace() != 0) {
			place = 0;
		} else {
			//散列算法,取相邻两值相差最大的两个值的中间数
			Integer maxDelta = 0;
			Integer delta;
			Integer cursor = loopPlaceList.get(0).getPlace();
			loopPlaceList.sort((o1, o2) -> {
				if (o1.getPlace() == o2.getPlace()) {
					return 0;
				}
				return o1.getPlace() < o2.getPlace() ? -1 : 1;
			});
			for (int i = 0; i < size; i++) {
				Integer n = loopPlaceList.get(i).getPlace();
				Integer m = i + 1 == size ? LOOP_LENGTH : loopPlaceList.get(i + 1).getPlace();
				delta = m - n;
				if (delta > maxDelta) {
					maxDelta = delta;
					cursor = m;
				}
			}

			place = cursor - maxDelta / 2;
		}

		LoopPlace loopPlace = new LoopPlace();
		loopPlace.setAddress(localAddress);
		loopPlace.setPlace(place);
		return loopPlace;
	}

	/**
	 * 将LoopPlaceMap转换成List
	 *
	 * @return loopList
	 */
	public static List<LoopPlace> getLoopList() {
		return new ArrayList<>(loopPlaceMap.values());
	}

}

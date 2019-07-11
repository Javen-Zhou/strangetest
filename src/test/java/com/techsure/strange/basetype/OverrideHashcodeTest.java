package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zhoujian
 * @Date 2019/7/9 10:19
 */
public class OverrideHashcodeTest {
	private static final Logger logger = LoggerFactory.getLogger(OverrideHashcodeTest.class);

	@Test
	public void testMapKey() {
		Map<UserVo, String> map = new HashMap<>();
		UserVo userVo = new UserVo(1L, "test", 123, "address");
		UserVo userVo1 = new UserVo(1L, "test2", 123, "address");

		map.put(userVo, "s1");
		map.put(userVo1, "s2");

		for (Map.Entry<UserVo, String> entry : map.entrySet()) {
			logger.info(entry.getValue());
		}
	}

	@Test
	public void testListEq() {
		List<UserVo> userVoList1 = new ArrayList<>();
		List<UserVo> uservoList2 = new ArrayList<>();
		UserVo userVo = new UserVo(1L, "test", 123, "address");
		userVoList1.add(userVo);

		UserVo userVo2 = new UserVo(1L, "test", 123, "address");
		uservoList2.add(userVo2);

		Map<List<UserVo>, String> map = new HashMap<>();
		map.put(userVoList1, "test1");
		map.put(uservoList2, "test2");
		map.forEach((k, v) -> logger.info(v));
	}

	@Test
	public void testRemoveSame() {
		UserVo userVo1 = new UserVo(1L, "test1", 123, "address1");
		UserVo userVo2 = new UserVo(2L, "test2", 123, "address2");
		UserVo userVo3 = new UserVo(1L, "test1", 123, "address3");
		Set<UserVo> set = new HashSet<>(3);
		set.add(userVo1);
		set.add(userVo2);
		set.add(userVo3);

		logger.info("set size:{}", set.size());
		for (UserVo userVo : set) {
			logger.info(userVo.toString());
		}
	}
}

class UserVo {
	private Long id;
	private String userName;
	private Integer userPhone;
	private String address;

	public UserVo(Long id, String userName, Integer userPhone, String address) {
		this.id = id;
		this.userName = userName;
		this.userPhone = userPhone;
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserVo{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", userPhone=" + userPhone +
				", address='" + address + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserVo userVo = (UserVo) o;
		return Objects.equals(getId(), userVo.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(Integer userPhone) {
		this.userPhone = userPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

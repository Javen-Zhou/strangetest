package com.techsure.strange.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2019/2/24 17:02
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void testConvert() {
		List<DataVo> dataList = new ArrayList<>();
		dataList.add(new DataVo().setAttrTypeName("cpu").setAttrName("usage").setObjName("core1").setValue(0));
		dataList.add(new DataVo().setAttrTypeName("cpu").setAttrName("size").setObjName("core2").setValue(1));

		Map<String, Map<String, DataVo>> map = convertDataListToMap(dataList);

		for (Map.Entry<String, Map<String, DataVo>> objMapEntry : map.entrySet()) {
			for (Map.Entry<String, DataVo> data : objMapEntry.getValue().entrySet()) {
				data.getValue().setValue(4);
			}
		}

		for(DataVo dataVo:dataList){
			logger.info(dataVo.toString());
		}
	}

	private Map<String, Map<String, DataVo>> convertDataListToMap(List<DataVo> dataList) {
		Map<String, Map<String, DataVo>> map = new HashMap<>();
		Map<String, DataVo> objMap;
		for (DataVo dataVo : dataList) {
			objMap = map.get(dataVo.getAttrTypeName() + "." + dataVo.getAttrName());
			if (objMap == null) {
				objMap = new HashMap<>();
			}
			objMap.put(dataVo.getObjName(), dataVo);
			objMap.put(dataVo.getObjName(), dataVo);
			map.put(dataVo.getAttrTypeName() + "." + dataVo.getAttrName(), objMap);
		}
		return map;
	}
}

class DataVo {
	private String attrTypeName;
	private String attrName;
	private String objName;
	private Integer value;

	public String getAttrTypeName() {
		return attrTypeName;
	}

	public DataVo setAttrTypeName(String attrTypeName) {
		this.attrTypeName = attrTypeName;
		return this;
	}

	public String getAttrName() {
		return attrName;
	}

	public DataVo setAttrName(String attrName) {
		this.attrName = attrName;
		return this;

	}

	public String getObjName() {
		return objName;
	}

	public DataVo setObjName(String objName) {
		this.objName = objName;
		return this;

	}

	public Integer getValue() {
		return value;
	}

	public DataVo setValue(Integer value) {
		this.value = value;
		return this;

	}

	@Override
	public String toString() {
		return "DataVo{" +
				"attrTypeName='" + attrTypeName + '\'' +
				", attrName='" + attrName + '\'' +
				", objName='" + objName + '\'' +
				", value=" + value +
				'}';
	}
}

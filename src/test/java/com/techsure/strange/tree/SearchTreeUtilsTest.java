package com.techsure.strange.tree;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/6/6 15:36
 */
public class SearchTreeUtilsTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchTreeUtilsTest.class);

	@Test
	public void testCreate() {
		int[] arrays = {2, 3, 1, 4, 5};
		TreeRoot treeRoot = new TreeRoot();
		for (int value : arrays) {
			TreeUtils.createSearchTree(treeRoot, value);
		}

		logger.info("先序遍历--------------------");
		TreeUtils.preTraverseBTree(treeRoot.getTreeRoot());
		logger.info("后序遍历--------------------");
		TreeUtils.sufTraverseBTree(treeRoot.getTreeRoot());
		logger.info("中序遍历--------------------");
		TreeUtils.inTraverseBTree(treeRoot.getTreeRoot());


		logger.info(String.valueOf(TreeUtils.getHeight(treeRoot.getTreeRoot())));
	}
}

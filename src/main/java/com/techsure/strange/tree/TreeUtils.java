package com.techsure.strange.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/6/6 10:40
 */
public class TreeUtils {
	private static final Logger logger = LoggerFactory.getLogger(TreeUtils.class);

	/**
	 * 先序遍历
	 *
	 * @param rootTreeNode
	 */
	public static void preTraverseBTree(TreeNode rootTreeNode) {
		if (rootTreeNode != null) {
			logger.info(String.valueOf(rootTreeNode.getValue()));
			preTraverseBTree(rootTreeNode.getLeftTreeNode());
			preTraverseBTree(rootTreeNode.getRightTreeNode());
		}
	}

	/**
	 * 中序遍历
	 *
	 * @param rootTreeNode
	 */
	public static void inTraverseBTree(TreeNode rootTreeNode) {
		if (rootTreeNode != null) {
			inTraverseBTree(rootTreeNode.getLeftTreeNode());
			logger.info(String.valueOf(rootTreeNode.getValue()));
			inTraverseBTree(rootTreeNode.getRightTreeNode());
		}
	}

	/**
	 * 后序遍历
	 *
	 * @param rootTreeNode
	 */
	public static void sufTraverseBTree(TreeNode rootTreeNode) {
		if (rootTreeNode != null) {
			sufTraverseBTree(rootTreeNode.getLeftTreeNode());
			sufTraverseBTree(rootTreeNode.getRightTreeNode());
			logger.info(String.valueOf(rootTreeNode.getValue()));
		}
	}

	public static void createSearchTree(TreeRoot treeRoot, int value) {
		if (treeRoot.getTreeRoot() == null) {
			TreeNode treeNode = new TreeNode(value);
			treeRoot.setTreeRoot(treeNode);
			return;
		}

		TreeNode tempRoot = treeRoot.getTreeRoot();
		while (tempRoot != null) {
			if (value > tempRoot.getValue()) {
				if (tempRoot.getRightTreeNode() == null) {
					tempRoot.setRightTreeNode(new TreeNode(value));
					return;
				}
				tempRoot = tempRoot.getRightTreeNode();
			} else {
				if (tempRoot.getLeftTreeNode() == null) {
					tempRoot.setLeftTreeNode(new TreeNode(value));
					return;
				}
				tempRoot = tempRoot.getLeftTreeNode();
			}
		}
	}

	public static int getHeight(TreeNode treeNode) {
		if (treeNode == null) {
			return 0;
		}

		int left = getHeight(treeNode.getLeftTreeNode());
		int right = getHeight(treeNode.getRightTreeNode());

		int max = left;
		if (right > max) {
			max = right;
		}
		return max + 1;
	}

	public static int getMax(TreeNode treeNode) {
		if (treeNode == null) {
			return -1;
		}
		int left = getMax(treeNode.getLeftTreeNode());
		int right = getMax(treeNode.getRightTreeNode());
		int currentValue = treeNode.getValue();
		int max = left > right ? left : right;
		max = max > currentValue ? max : currentValue;
		return max;

	}
}

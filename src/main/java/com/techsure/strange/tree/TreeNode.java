package com.techsure.strange.tree;

/**
 * @author zhoujian
 * @Date 2019/6/6 10:39
 */
public class TreeNode {
	private TreeNode leftTreeNode;
	private TreeNode rightTreeNode;
	private int value;

	public TreeNode() {
	}

	public TreeNode(int value) {
		this.value = value;
	}

	public TreeNode getLeftTreeNode() {
		return leftTreeNode;
	}

	public void setLeftTreeNode(TreeNode leftTreeNode) {
		this.leftTreeNode = leftTreeNode;
	}

	public TreeNode getRightTreeNode() {
		return rightTreeNode;
	}

	public void setRightTreeNode(TreeNode rightTreeNode) {
		this.rightTreeNode = rightTreeNode;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

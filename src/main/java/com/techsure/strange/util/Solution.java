package com.techsure.strange.util;

/**
 * @author zhoujian
 * @Date 2019/6/6 17:52
 */
/**
  Definition for a binary tree node.*/
 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
public class Solution {
 	int total = 0;
	public TreeNode bstToGst(TreeNode root) {
		if(root != null){
			bstToGst(root.right);
			total = root.val + total;
			root.val = total;
			bstToGst(root.left);
		}
		return root;
	}



}

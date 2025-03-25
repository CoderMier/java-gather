package com.qdm.datastruct.tree;


/**
 * 表示链表中的一个节点。
 * 每个节点包含一个整数数据和一个指向下一个节点的引用。
 * 该类主要用于构建和操作单链表。
 */
public class TreeNode {
    private String data;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}

package com.qdm.datastruct.tree;

public class TreeForeach {


    public static void main(String[] args) {
        //前序：FCADBEHGM
        TreeNode treeNode = TreeUtil.initTree();
        System.out.print("前序遍历:");
        preForeach(treeNode);
        System.out.println();
        System.out.print("中序遍历:");
        midForeach(treeNode);
        System.out.println();
        System.out.print("后序遍历:");
        afterForeach(treeNode);
    }




    /**
     * 前序遍历二叉树，并打印每个节点的数据。
     * 遍历顺序为：根节点 -> 左子树 -> 右子树。
     *
     * @param root 二叉树的根节点，若为null则函数不执行任何操作
     */
    public static void preForeach(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.printf(root.getData());
        preForeach(root.getLeft());
        preForeach(root.getRight());
    }


    /**
     * 中序遍历二叉树，并打印每个节点的数据。
     * 遍历顺序为： 左子树 -> 根节点 -> 右子树。
     */
    public static void midForeach(TreeNode root) {
        if (root == null) {
            return;
        }
        midForeach(root.getLeft());
        System.out.printf(root.getData());
        midForeach(root.getRight());
    }


    /**
     * 后序遍历二叉树，并打印每个节点的数据。
     * 遍历顺序为： 左子树 -> 右子树  -> 根节点。
     */
    public static void afterForeach(TreeNode root) {
        if (root == null) {
            return;
        }
        afterForeach(root.getLeft());
        afterForeach(root.getRight());
        System.out.printf(root.getData());
    }








}

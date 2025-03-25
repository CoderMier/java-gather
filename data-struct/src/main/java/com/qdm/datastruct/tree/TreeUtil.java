package com.qdm.datastruct.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    /**
     * 初始化一棵二叉树
     * <p>
     * 此方法构建了一棵固定的二叉树结构，用于演示或测试二叉树相关的算法和操作
     * 树的节点包含字母，代表树中的不同位置节点，通过设置左右子节点来构建树的结构
     *
     * @return 树的根节点，通过它可以在树中进行遍历或其他操作
     * 前序：FCADBEHGM
     *          F
     *        /   \
     *       C     E
     *      / \   / \
     *     A  D  H  G
     *       /     /
     *      B     M
     *
     *
     */
    public static TreeNode initTree() {
        // 创建节点，用字母标记
        TreeNode F = new TreeNode("F");
        TreeNode C = new TreeNode("C");
        TreeNode A = new TreeNode("A");
        TreeNode D = new TreeNode("D");
        TreeNode B = new TreeNode("B");
        TreeNode E = new TreeNode("E");
        TreeNode H = new TreeNode("H");
        TreeNode G = new TreeNode("G");
        TreeNode M = new TreeNode("M");

        // 构建树的结构
        F.setLeft(C);
        F.setRight(E);
        C.setLeft(A);
        C.setRight(D);
        D.setLeft(B);
        E.setLeft(H);
        E.setRight(G);
        G.setLeft(M);
        // 返回根节点
        return F;
    }
}
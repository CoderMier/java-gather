package com.qdm.datastruct.dfs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DfsSolution {



    /**
     * 求给定数字的全排
     */
    @Test
    public  void listNums() {
        int[] nums = {1,2,3};
        boolean[] visited = new boolean[nums.length];
        listNumsDfs(nums, 0, visited, new Stack<>());
    }



    @Test
    public void rightIps() {
        String ipStr = "19216801";
        rightIpsDfs(ipStr, 0, 1, new Stack<>());
    }


    private void rightIpsDfs(String ips, int index, int level, Stack<String> res) {
//        System.out.println("res1 = " + res + ", index=" + index +", level=" + level);
        if (level == 5 || index  >= ips.length()) {
            if (level == 5 && index  == ips.length()) {
                System.out.println("res = " + res);
            }
            return;
        }


        for (int i = 1; i < 4; i++) {
            if (index + i > ips.length()) {
                break;
            }
            String n = ips.substring(index, index + i);
            if (Integer.valueOf(n) < 255 &&  (n.equals("0")  || !n.startsWith("0"))) {
                res.push(n);
                rightIpsDfs(ips, index+i, level+1, res);
                res.pop();
            }
        }

    }




    /**
     * 深度优先搜索方法，用于生成给定数字的全排列
     * @param nums    给定的数字数组
     * @param level   当前递归的层级
     * @param visited 布尔数组，记录数字是否已被访问
     * @param res     当前排列结果栈
     */
    private void listNumsDfs(int[] nums, int level, boolean[] visited, Stack<Integer> res) {
        // 如果当前层级等于数字数组的长度，说明已经生成了一个完整的排列，打印结果
        if (nums.length == level) {
            System.out.println(res);
        }
        // 遍历数字数组中的每个数字
        for (int i = 0; i < nums.length; i++) {
            // 如果当前数字未被访问过
            if (visited[i] == false) {
                // 将当前数字压入结果栈
                res.push(nums[i]);
                // 标记当前数字已被访问
                visited[i] = true;
                // 打印当前状态：入栈操作，当前层级，当前数字索引，访问状态数组，结果栈
                System.out.println("++++入栈：level=" + level + ", i = " + i + ", visited=" + Arrays.toString(visited) + ", res=" + res);

                // 递归调用listNumsDfs方法，继续生成下一个层级的排列
                listNumsDfs(nums, level + 1, visited, res);
                // 回溯：将当前数字标记为未访问
                visited[i] = false;
                // 将当前数字从结果栈中弹出
                res.pop();
                // 打印当前状态：出栈操作，当前层级，当前数字索引，访问状态数组，结果栈
                System.out.println("---出栈：level=" + level + ", i = " + i + ", visited=" + Arrays.toString(visited) + ", res=" + res);
            }
        }
    }








}

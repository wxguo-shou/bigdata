package com.atguigu;

import java.util.Scanner;

/**
 * @author name 婉然从物
 * @create 2023-10-28 9:06
 */
public class Test02 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        String s = sc.next();
        System.out.println(i);

        System.out.println(s);
    }
}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        int array[] = new int[2];
        for (int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    array[0] = i;
                    array[1] = j;
                }
            }
        }
        return array;
    }
}
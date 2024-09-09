package com.atguigu;

/**
 * @author name 婉然从物
 * @create 2023-10-24 17:26
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(fact(5));
        System.out.println(fact1(5));

    }

    public static int fact(int n){
        if (n >= 2){
            n = n * fact(n - 1);
        }
        return n;
    }

    public static int fact1(int n){
        if (n == 0) return 1;
        return fact1(n - 1) * n;
    }
}

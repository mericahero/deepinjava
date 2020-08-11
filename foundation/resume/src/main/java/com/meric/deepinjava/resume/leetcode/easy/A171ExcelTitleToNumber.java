package com.meric.deepinjava.resume.leetcode.easy;

public class A171ExcelTitleToNumber {
    public int titleToNumber(String s) {
        int ans = 0;
        for(int i=0;i<s.length();i++) {
            int num = s.charAt(i) - 'A' + 1;
            ans = ans * 26 + num;
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "AB";
        System.out.println(new A171ExcelTitleToNumber().titleToNumber(s));
    }
}

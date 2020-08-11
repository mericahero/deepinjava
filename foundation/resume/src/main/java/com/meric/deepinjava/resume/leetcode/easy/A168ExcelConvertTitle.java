package com.meric.deepinjava.resume.leetcode.easy;

public class A168ExcelConvertTitle {
    public String convertToTitle(int n) {
        StringBuilder result = new StringBuilder();
        while(n>0){
            n--;
            result =result.insert (0,(char)('A'+n%26) );
            n=n/26;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        int n=27;
        System.out.println(new A168ExcelConvertTitle().convertToTitle(n));
    }

}

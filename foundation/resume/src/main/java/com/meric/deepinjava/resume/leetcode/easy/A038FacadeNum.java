package com.meric.deepinjava.resume.leetcode.easy;

public class A038FacadeNum {
    public String countAndSay(int n) {
        if(n==1){
            return "1";
        }
        String temp = countAndSay(n-1);
        StringBuilder sb=new StringBuilder();
        int slow=0;
        int fast = 0;
        int l=temp.length();
        while(slow<l){
            char slowChar = temp.charAt(slow);
            while(fast<l && temp.charAt(fast)==slowChar){
                fast++;
            }
            sb.append(fast-slow).append(slowChar);
            slow=fast;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new A038FacadeNum().countAndSay(6));
    }
}

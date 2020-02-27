package com.meric.deepinjava.resume.leetcode.easy;

import java.util.HashSet;
import java.util.Set;

public class A003lengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        int result =0,i=0,j=0;
        int n = s.length();
        HashSet<Character> set = new HashSet<>(n);
        while(i<n && j<n){
            if(set.add(s.charAt(j))){
                j++;
                if(j-i>result){
                    result=j-i;
                }
            }else{
                set.remove(s.charAt(i++));
            }
        }
        return result;
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int result = 0,left=0;
        int[] index = new int[128];
        for(int right=0;right<n;right++){
            int charAtRight = s.charAt(right);
            int charAtLeft = s.charAt(left);
            left=Math.max(index[charAtRight],left);
            result = Math.max(result,right-left+1);
            index[charAtRight]=left+1;
        }
        return result;
    }


    public static void main(String[] args) {
        String str = "abcabcbb";
        int result = new A003lengthOfLongestSubstring().lengthOfLongestSubstring2(str);
    }
}

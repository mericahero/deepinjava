package com.meric.deepinjava.resume.leetcode.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 实现 strStr() 函数。
 *
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 *
 * 示例 1:
 *
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 * 示例 2:
 *
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/implement-strstr
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class A028strStr {
    public int strStr(String haystack, String needle) {
        if(haystack==null || haystack.length()==0 || needle==null){
            return 0;
        }
        for(int i=0;i<haystack.length();++i){
            int j=0;
            for(;j<needle.length();++j){
                if(haystack.charAt(i+j)!=needle.charAt(j)){
                    break;
                }
            }
            if(j==needle.length()){
                return i;
            }
        }
        return -1;
    }



    static A028strStr tester ;

    @BeforeAll
    public static void beforeAll(){
        tester= new A028strStr();
    }

    @Test
    public void testIn(){
        Assertions.assertEquals(2,tester.strStr("hello","ll"));
    }

    @Test
    public void testNotIn(){
        Assertions.assertEquals(-1,tester.strStr("hello","abc"));
    }

}

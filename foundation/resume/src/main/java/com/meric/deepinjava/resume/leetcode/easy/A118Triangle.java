package com.meric.deepinjava.resume.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class A118Triangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>(numRows);
        List<Integer> lastAry = new ArrayList<>();
        lastAry.add(1);
        result.add(lastAry);
        for(int i=2;i<=numRows;++i){
            List<Integer> newAry = new ArrayList<>(i);
            for(int j=0;j<i;++j){
                if(j==0){
                    newAry.add(lastAry.get(j));
                }else if(j==i-1){
                    newAry.add(lastAry.get(i-2));
                }else{
                    newAry.add(lastAry.get(j-1) +lastAry.get(j) );
                }
            }
            result.add(newAry);
            lastAry=newAry;
        }
        return result;
    }



    public static void main(String[] args) {
        int n = 6;
        List<List<Integer>> result = new A118Triangle().generate(n);

        System.out.println(result);
    }
}

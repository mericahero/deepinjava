package com.meric.deepinjava.resume.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class A001twoSum {
    public int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; ++i) {
            if(nums[i]>target) continue;
            for(int j=i;j<nums.length;++j){
                if(nums[j]>target) continue;
                if(nums[j]==target-nums[i]){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public int[] twoSum2(int[] nums,int target){
        int length = nums.length;
        Map<Integer,Integer> map = new HashMap<>(length);
        for(int i=0;i<length;++i){
            map.put(nums[i],i);
        }
        for(int i=0;i<length;++i){
            if(map.containsKey(target-nums[i])){
                int j =map.get(target-nums[i]);
                if(i==j) continue;
                return new int[]{i,j};
            }
        }
        return null;
    }

    public int[] twoSum3(int[] nums,int target){
        int mask = 0x7FF;
        int[] result = new int[0x800];
        for (int i = 0; i < nums.length; i++) {
            int c = (target - nums[i]) & mask;
            if (result[c] != 0) {
                return new int[]{i,result[c] - 1};
            }
            result[nums[i] & mask] = i+1;
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{230,863,916,585,981,404,316,785,88,12,70,435,384,778,887,755,740,337,86,92,325,422,815,650,920,125,277,336,221,847,168,23,677,61,400,136,874,363,394,199,863,997,794,587,124,321,212,957,764,173,314,422,927,783,930,282,306,506,44,926,691,568,68,730,933,737,531,180,414,751,28,546,60,371,493,370,527,387,43,541,13,457,328,227,652,365,430,803,59,858,538,427,583,368,375,173,809,896,370,789};
        int target = 542;
        int[] result = new A001twoSum().twoSum3(nums,target);
    }

}

package com.meric.deepinjava.resume.leetcode.easy;

public class A034SearchRange {
    public int[] searchRange(int[] nums, int target) {
        if(nums==null || nums.length==0){
            return new int[]{-1,-1};
        }
        int left = 0;
        int right=nums.length;
        int mid = (left+right)/2;
        if(nums[left]>target || nums[right]<target){
            return new int[]{-1,-1};
        }

        int[] result = new int[]{-1,-1};
        while(left<right){
            if(nums[mid]<target){
                left = mid;
                mid=(left+right)/2;
                continue;;
            }
            if (nums[mid]>target) {
                right =mid;
                mid = (left+right)/2;
                continue;
            }
            if(nums[left]<target){
                left++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] n = new int[]{5,7,7,8,8,10};
        System.out.println(new A034SearchRange().searchRange(n,8));
    }
}

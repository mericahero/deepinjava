package com.meric.deepinjava.resume.leetcode.easy;

public class A033SearchRotate {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        if(
                nums.length==0 ||
                (nums[left]>target && nums[right]<target)
        ){
            return -1;
        }
        while(left<=right){
            if(nums[left]==target){
                return left;
            }
            if(nums[right]==target){
                return right;
            }


            if(nums[left]<target){
                left++;
                continue;
            }
            if(nums[right]>target){
                right--;
                continue;
            }
            if(nums[left]>nums[right]){
                left++;
            }else{
                right--;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        int[] n = new int[]{4,5,6,8,0,1,2};
        System.out.println(new A033SearchRotate().search(n,7));
    }
}

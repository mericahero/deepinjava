package com.meric.deepinjava.resume.leetcode.easy;

public class A153FindMin {
    public int findMin(int[] nums) {
        if(nums==null || nums.length==0){
            return 0;
        }
        int left = 0;
        int right = nums.length-1;
        while(left < right){
            if(nums[left]>=nums[right]){
                left++;
            }else{
                break;
            }
        }
        return nums[left];

    }

    public static void main(String[] args) {
        int[] a = new int[]{3,4,5,1,2};
        System.out.println(new A153FindMin().findMin(a));
    }
}

package com.meric.deepinjava.resume.leetcode.easy;

public class A088MergeTwoSorted {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p = m-1;
        int q = n-1;
        int r = p+q-1;
        while ( p>=0 && q>=0 ){
            nums1[r--]= (nums1[p]<nums2[q]) ? nums2[p--]:nums1[q--];
        }
        System.arraycopy(nums2,0,nums1,0,q+1);
    }

    public static void main(String[] args) {
        int[] n = new int[]{1,2,3,3,5,6,0,0,0,0};
        int[] m =new int[]{6,7,8,9};

        new A088MergeTwoSorted().merge(n,6,m,4);

        System.out.println(n);
    }


}

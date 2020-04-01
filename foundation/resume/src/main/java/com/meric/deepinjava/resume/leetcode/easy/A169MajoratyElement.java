package com.meric.deepinjava.resume.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

public class A169MajoratyElement {
    public int majorityElement(int[] nums) {
        if(nums.length==1){
            return nums[0];
        }
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;++i){
            int numi = nums[i];
            if(map.containsKey(numi)){
                map.put(numi,map.get(numi)+1);
                if(map.get(numi)>nums.length/2){
                    return numi;
                }
            }else{
                map.put(numi,1);
            }
        }
        return -1;
    }

    public int majorityElement2(int[] nums) {
        int max = nums[0];
        int min = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>max){
                max=nums[i];
            }
            if(nums[i]<min){
                min=nums[i];
            }
        }
        int diff = max-min;
        int[] temp = new int[++diff];
        for(int i=0;i<nums.length;i++){
            int numi = nums[i];
            int idx = numi-min;
            temp[idx]=temp[idx]+1;
            if(temp[idx]>nums.length/2){
                return idx+min;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = new int[]{2,2,1,1,1,2,2};
        a = new int[]{2147483647};
        a = new int[]{-1,};
        System.out.println(new A169MajoratyElement().majorityElement2(a));
    }

}

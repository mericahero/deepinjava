package com.meric.deepinjava.resume.siddontang;

public class Array001RemoveElement {
    public int removeDuplicates(int[] ary){
        if(ary==null || ary.length==0){
            return 0;
        }
        int j=0;
        for(int i=1;i<ary.length;++i){
            if(ary[j]==ary[i]) {
                continue;
            }
            ary[++j]=ary[i];
        }
        return j+1;
    }

    public int removeDuplicates2(int[] ary){
        if(ary==null || ary.length==0){
            return 0;
        }
        int j=0;
        for(int i=1;i<ary.length;++i){

        }
    }

    public static void main(String[] args) {
        int[] origin = new int[]{1,1,2,2,2,3,3,3,3};
        int result1 = new Array001RemoveElement().removeDuplicates(origin);
        System.out.println(result1);
    }

}

package com.meric.deepinjava.resume.siddontang;

/**
 * https://siddontang.gitbooks.io/leetcode-solution/array/sum.html
 */
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
        int acc = 1;
        for(int i=1;i<ary.length;++i){
            if(ary[j]==ary[i]){
                acc++;
                if(acc<=2){
                    ary[++j]=ary[i];
                }
            }else{
                ary[++j]=ary[i];
                acc=1;
            }
        }
        return j+1;
    }

    public static void main(String[] args) {
        int[] origin = new int[]{1,1,2,2,2,3,3,3,3};
        int result1 = new Array001RemoveElement().removeDuplicates(origin);
        origin = new int[]{1,1,2,2,2,3,3,3,3};
        int result2 = new Array001RemoveElement().removeDuplicates2(origin);
        System.out.println(result1);
        System.out.println(result2);
    }

}

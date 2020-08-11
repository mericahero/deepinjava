package com.meric.deepinjava.resume.fact;

public class AtoiSolution {
    /**
     * 把字符串转换成整型数
     * @param source 源字符串
     * @return 返回 int 值，此值由将输入字符作为数字解析而生成。 如果该输入无法转换为该类型的值，则返回值为 Integer.MIN_VALUE
     * @example
     *      "1234" -> 1234
     *      "1a234" -> Integer.MIN_VALUE
     *      "-1234" -> -1234
     *      "  1234" -> 1234
     *      "1234  " -> 1234
     *      "12 34" -> Integer.MIN_VALUE
     *      " 2147483647 " -> 2147483647
     *      " 2147483648 " -> Integer.MIN_VALUE
     *      超过int最大值 -> Integer.MIN_VALUE
     */
    public int atoi(String source){
        // i表示遍历顺序, result 表示无符号的结果， symbol 表示符号位
        int i=0,result=0,symbol=1;
        char currentChar;
        int length = source.length();
        // 循环字符串，排除前面的空格部分
        while( source.charAt(i) ==' ' ){
            i++;
        }
        // 取出符号位
        if((currentChar= source.charAt(i))=='+' || currentChar=='-'){
            if(currentChar=='-'){
                symbol=-1;
            }
            i++;
        }

        boolean middleSpace = false;

        // 遍历后面的字符串
        for(;i<length;i++){
            currentChar=source.charAt(i);
            if(currentChar==' '){
                middleSpace = true;
                continue;
            }else{
                // 中间有空格
                if(middleSpace) return Integer.MIN_VALUE;
            }
            // 主体部分不是数字
            if(currentChar<'0'|| currentChar>'9') return Integer.MIN_VALUE;
            int nowCharInt = currentChar-'0';
            // 超出Integer最大了
            if(result>Integer.MAX_VALUE/10) return Integer.MIN_VALUE;
            // Integer.MAX_VALUE%10 -> 0x7ffffffff%10 = 7 避免了一次除法
            if(result==Integer.MAX_VALUE/10 && nowCharInt>7) return Integer.MIN_VALUE;
            result=result*10+ nowCharInt;
        }
        return symbol * result;
    }

    public static void main(String[] args) {
        System.out.println(new AtoiSolution().atoi(""));
        System.out.println(new AtoiSolution().atoi("1234"));
        System.out.println(new AtoiSolution().atoi("-1234"));
        System.out.println(new AtoiSolution().atoi("a-1234"));
        System.out.println(new AtoiSolution().atoi(" -12a34"));
        System.out.println(new AtoiSolution().atoi(" 2147483647 "));
        System.out.println(new AtoiSolution().atoi(" 2147483648"));
    }
}
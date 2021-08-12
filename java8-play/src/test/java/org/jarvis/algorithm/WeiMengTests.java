package org.jarvis.algorithm;

/**
 * @author tennyson
 * @date 2021/5/18-20:29
 */
public class WeiMengTests {
    /**
     * 排序算法
     * 1、数字一共有多位(有限位)通过点区分，前方是1号位，2号位……
     * 2、进行比较，如果1号位不同，数大者数组大，如果相同则比较下一位
     * 3、最终输出结果 两数谁更大
     */
    public static void compare(String var1, String var2) {
        String[] result = new String[2];
        String[] var1Array = var1.split("\\.");
        String[] var2Array = var2.split("\\.");
        int n = Math.min(var1Array.length, var2Array.length);
        for (int i = 0; i < n; i++) {
            Integer var1i = Integer.parseInt(var1Array[i]);
            Integer var2i = Integer.parseInt(var2Array[i]);
            if (var1i > var2i) {
                result[0] = var1;
                result[1] = var2;
                break;
            }
            if (var1i < var2i) {
                result[0] = var2;
                result[1] = var1;
                break;
            }
        }
        if (result[0] == null && var1Array.length > var2Array.length) {
            result[0] = var1;
            result[1] = var2;
        }
        if (result[0] == null && var1Array.length < var2Array.length) {
            result[0] = var2;
            result[1] = var1;
        }
        System.out.println(result[0] + ">" + result[1]);
    }

    public static void main(String[] strings) {
        compare("10.2.3.36.65.89", "10.2.3");
    }
}

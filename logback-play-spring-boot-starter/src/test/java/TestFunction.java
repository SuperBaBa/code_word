import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author tennyson
 * @date 2021/5/13-23:08
 */
public class TestFunction {
    public static void main(String[] args) throws IOException {
        incompleteStr();
    }

    /**
     * 写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。如果小数点后数值大于等于5,向上取整；小于5，则向下取整。
     * <p>
     * 输入描述:
     * 输入一个正浮点数值
     * <p>
     * 输出描述:
     * 输出该数值的近似整数值
     *
     * @throws IOException
     */
    public static void approximateValue() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = bufferedReader.readLine();
//        Scanner scanner=new Scanner(System.in);
//        String str=scanner.next();
        int index = str.indexOf(".");
        int a = Integer.parseInt(str.substring(0, index));
        int b = Integer.parseInt(str.substring(index + 1, index + 2));
        if (b >= 5) {
            System.out.println(a + 1);
        } else {
            System.out.println(a);
        }
    }

    /**
     * 数据表记录包含表索引和数值（int范围的正整数），请对表索引相同的记录进行合并，即将相同索引的数值进行求和运算，输出按照key值升序进行输出。
     */
    public void mergeKeyValue() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int times = Integer.parseInt(bf.readLine());
        int i = 0;
        Map<Integer, Integer> result = new HashMap<>();
        while (i < times) {
            i++;
            String kv = bf.readLine();
            String[] keyAndValue = kv.split(" ");
            int keyInteger = Integer.parseInt(keyAndValue[0]);
            int valueInteger = Integer.parseInt(keyAndValue[1]);
            if (result.containsKey(keyInteger)) {
                result.get(keyInteger);
//                result.put(keyInteger, )
            }
        }
    }

    /**
     * 计算字符串最后一个单词的长度，单词以空格隔开，字符串长度小于5000。
     *
     * @throws IOException
     */
    @Test
    public void calculateString() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = bf.readLine();
        String[] inputStrArray = inputStr.split(" ");
        System.out.println(inputStrArray[inputStrArray.length - 1].length());

    }

    /**
     * 写出一个程序，接受一个由字母、数字和空格组成的字符串，和一个字母，然后输出输入字符串中该字母的出现次数。不区分大小写，字符串长度小于500。
     */
    public static void wordAppearTimes() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = bf.readLine();
        String inputLetter = bf.readLine();
        int times = 0;
        for (int i = 0; i < inputStr.length(); i++) {
            if (inputLetter.equalsIgnoreCase(String.valueOf(inputStr.charAt(i)))) {
                times++;
            }
        }
        System.out.println(times);
    }

    /**
     * 明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000），
     * 对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。
     * 然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。
     * 请你协助明明完成“去重”与“排序”的工作(同一个测试用例里可能会有多组数据(用于不同的调查)，希望大家能正确处理)
     * <p>
     * 输入描述:
     * 注意：输入可能有多组数据(用于不同的调查)。每组数据都包括多行，第一行先输入随机整数的个数N，接下来的N行再输入相应个数的整数。具体格式请看下面的"示例"。
     * <p>
     * 输出描述:
     * 返回多行，处理后的结果
     */
    public static void uniqueAndSortStudentNum() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            boolean[] markBoolArray = new boolean[10001];
            for (int i = 0; i < n; i++) {
                int num = sc.nextInt();
                markBoolArray[num] = true;
            }
            for (int i = 0; i < 1001; i++) {
                if (markBoolArray[i]) {
                    System.out.println(i);
                }
            }
        }
    }

    /**
     * 连续输入字符串，请按长度为8拆分每个字符串后输出到新的字符串数组；
     * 长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
     * <p>
     * 输入
     * abc
     * 123456789
     * <p>
     * 输出
     * abc00000
     * 12345678
     * 90000000
     *
     * @throws IOException
     */
    public static void incompleteStr() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String inputStr;
        while ((inputStr = bf.readLine()) != null) {
            StringBuilder strBuilder = new StringBuilder(inputStr);
            int len = inputStr.trim().length();
            System.out.println(len & (8 - 1));
            System.out.println(len % 8);
            if (len % 8 != 0) {
                for (int i = 0; i < 8 - len % 8; i++) {
                    strBuilder.append(0);
                }
            }
            int start = 0;
            while (strBuilder.length() != start) {
                System.out.println(strBuilder.substring(start, start + 8));
                start += 8;
            }
        }
    }
}

package org.jarvis.consumer.java8.algorithm;

import java.util.*;

/**
 * DFA算法，确定有限状态自动机
 * 首先有一个有限状态的集合，其次进行状态转移
 *
 * @author marcus
 * @date 2020/10/22-16:00
 */
public class DFAAlgorithm {
    private static HashMap<String, String> words;


    public static void initTrie(Set<String> sensitiveWords) {
        words = new HashMap(sensitiveWords.size());
        Map temp;
        Map<String, String> temp2;
        //遍历传入的敏感词集合，构建字典树
        for (String word : sensitiveWords) {
            temp = words;
            //将每个词转为字符数组，每个字符都是一个状态，构建有限状态集合
            for (char character : word.toCharArray()) {
                //先查看字典树内是否存在这个状态
                Object var1 = temp.get(character);
                if (var1 != null) {
                    //如果存在，则指向下一个节点
                    temp = (Map) var1;
                } else {
                    //如果不存在则进行创建节点
                    temp2 = new HashMap();
                    temp2.put("isEnd", "0");
                    //放置该字符，并标记其状态
                    temp.put(character, temp2);
                    //指向下一个节点
                    temp = temp2;
                }
                if (word.charAt(word.length() - 1) == character) {
                    temp.put("isEnd", "1");
                }
            }
        }
        System.out.println(words);
    }

    public boolean contains(String text, int matchType) {
        int i = 0;
        while (i == text.length() - 1) {
            text.substring(i);
        }
        return false;
    }

    public static void main(String[] args) {
        initTrie(new HashSet<>(Arrays.asList("搓搓手", "扣扣脚", "搓大腿")));
//        initSensitiveWordMap(new HashSet<>(Arrays.asList("搓搓手", "扣扣脚", "搓大腿")));
    }

    public int checkSensitiveWord(String text, int beginIndex, int matchType) {
        return 0;
    }

}

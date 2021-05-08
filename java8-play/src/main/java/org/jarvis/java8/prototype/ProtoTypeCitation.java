package org.jarvis.java8.prototype;

/**
 * author:Lovel  date:2020/5/18
 */
public class ProtoTypeCitation {
    public static void main(String[] args) {
        Citation citation = new Citation("小明", "渣男", "大一");
        System.out.println(citation.toString());
        try {
            Citation citation1 = citation.clone();
            citation1.setName("小军");
            citation1.setName("大四");
            System.out.println(citation1.toString());
            System.out.println(citation==citation1);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

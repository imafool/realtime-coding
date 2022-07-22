package com.coco;

import java.util.ArrayList;

public class TestArrayList {
    public static void main(String[] args) {
        //测试移除ArrayList集合中的元素，观察是否是移除第一次出现的指定元素
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("20000");
        list.add("3");
        list.add("4");
        list.add("20000");
        list.add("5");
        System.out.println("集合元素：" + list);
        System.out.println("-------------移除值为20000的元素");
        list.remove("20000");
        System.out.println("移除后的集合：" + list);
    }
}

package com.coco.io._io.iostream._homework;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 编写一个dog.properties：
 * name=tom
 * age=5
 * color=red
 * 编写Dog类，创建一个dog对象，读取dog.properties，用相应的内容完成属性的初始化，并输出
 */
public class $03_DogDemo {
    public static void main(String[] args) throws IOException {
        String path = "D:\\imafool\\ijava\\java-io\\src\\main\\java\\com\\coco\\iostream\\_homework\\dog.properties";

        Dog dog = new Dog();

        Properties properties = new Properties();
        properties.load(new FileReader(path));
        String name = (String) properties.get("name");
        int age = Integer.parseInt((String)properties.get("age"));
        String color = (String) properties.get("color");

        dog.setName(name);
        dog.setAge(age);
        dog.setColor(color);

        System.out.println(dog);
    }
}

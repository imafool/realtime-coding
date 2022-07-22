package com.coco.io._io.iostream.inputstream;

import com.coco.io._io.iostream.outputstream.Duck;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 提供反序列化功能，恢复数据的类型和值[需要与序列化的顺序一致]
 */
public class _ObjectInputStream {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\obj.data";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        objectInput();
    }

    private static void objectInput() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readInt());
        System.out.println(ois.readLong());
        System.out.println(ois.readFloat());
        System.out.println(ois.readDouble());
        Object obj = ois.readObject();
        System.out.println(obj.getClass());
        Duck duck = (Duck) obj;
        System.out.println(duck);
        System.out.println("Display Completed!");
    }
}
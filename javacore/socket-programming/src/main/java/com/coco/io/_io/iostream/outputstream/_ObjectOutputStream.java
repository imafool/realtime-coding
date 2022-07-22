package com.coco.io._io.iostream.outputstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 提供序列化功能，序列化多个类型和相应的值
 */
public class _ObjectOutputStream {
    private static final String PATH = "C:\\Users\\Administrator\\Desktop\\javaiotest\\buffer\\obj.data";

    public static void main(String[] args) throws IOException {
        objectOut();
    }

    private static void objectOut() throws IOException {
        FileOutputStream os = new FileOutputStream(PATH);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeBoolean(Boolean.TRUE);
        oos.writeChar('H');
        oos.writeInt(127);
        oos.writeLong(100L);
        oos.writeFloat(1.0F);
        oos.writeDouble(1000.0D);
        oos.writeObject(new Duck(1, "yellow"));
        oos.close();
        System.out.println("Object Restore Completed!");
    }
}
package com.coco.io._io.iostream._homework;

import java.io.File;
import java.io.IOException;

/**
 * 判断e盘下是否有文件夹mytemp，如果没有就创建mytemp
 * 在上述创建的目录下，创建文件hello.txt
 * 如果hello.txt已经存在，提示该文件已经存在不要再重复创建了
 */
public class $01_5min {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\javaiotest\\hehe";
        File dir = new File(path);
        if (!dir.exists()){
            if (dir.mkdir()){
                System.out.println("Dir Create Success!");
            }
        }else{
            System.out.println("Dir Already Exist!");
        }

        File file = new File(path, "hello.txt");
        if (!file.exists()){
            if (file.createNewFile()){
                System.out.println("File Create Success!");
            }
        }else{
            System.out.println("File Already Exist!");
        }
    }
}

package com.coco.io._io.iostream._homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 使用BufferedReader读取一个文本文件，为每行加上行号，再连同内容一并输出到屏幕上
 */
public class $02_Text_Handle {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\javaiotest\\reader\\demo.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int ln = 0;
        while((line = br.readLine()) != null){
            System.out.println(++ln + " " + line);
        }
        br.close();
    }
}

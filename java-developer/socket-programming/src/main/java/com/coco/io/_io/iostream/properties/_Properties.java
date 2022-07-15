package com.coco.io._io.iostream.properties;

import java.io.*;
import java.util.Properties;

/**
 * Properties extends Hashtable extends Dictionary extends Object
 */
public class _Properties {
    private static final String PATH = "D:\\imafool\\ijava\\java-io\\src\\main\\java\\com\\coco\\iostream\\properties\\demo.properties";
    private static final String PATH2 = "D:\\imafool\\ijava\\java-io\\src\\main\\java\\com\\coco\\iostream\\properties\\newProperties.properties";

    public static void main(String[] args) throws IOException, InterruptedException {
        // troHandle();
        // proHandler();
        setProper();
    }

    private static void setProper() throws InterruptedException, IOException {
        Properties properties = new Properties();
        properties.setProperty("KEY", "VALUE");
        properties.store(new FileWriter(PATH2), "");
        Thread.sleep(500);
        properties.load(new FileReader(PATH2));
        properties.list(System.out);
    }

    private static void proHandler() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(PATH));
        properties.list(System.out);
        String name = properties.getProperty("name");
        String hi = properties.getProperty("hi");
        String local = properties.getProperty("local");
        System.out.println(name + " . " + hi + " . " + local);
    }

    private static void troHandle() throws IOException {
        BufferedReader bis = new BufferedReader(new FileReader(PATH));
        String line;
        while ((line = bis.readLine()) != null){
            System.out.print(line + " || ");
            String[] split = line.split("=");
            System.out.println(split[0] + " : " + split[1]);
        }
    }
}

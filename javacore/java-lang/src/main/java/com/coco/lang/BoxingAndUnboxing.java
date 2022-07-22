package com.coco.lang;

@SuppressWarnings("all")
public class BoxingAndUnboxing {
    public static void main(String[] args) {
        Integer integer1 = 3;
        Integer integer2 = 3;
        if (integer1 == integer2)
            System.out.println("integer1 == integer2");//true，需要进行自动装箱时，如果数字在-128至127之间时，会直接使用缓存中的对象，而不是重新创建一个对象，可以直接使用a==b判断a和b的值是否相等，还包括true、flse和 '\u0000'至 '\u007f'之间的字符
        else
            System.out.println("integer1 != integer2");

        Integer integer3 = 300;
        Integer integer4 = 300;
        if (integer3 == integer4)
            System.out.println("integer3 == integer4");
        else
            System.out.println("integer3 != integer4"); //false
    }
}

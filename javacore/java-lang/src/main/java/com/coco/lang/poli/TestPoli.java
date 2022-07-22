package com.coco.lang.poli;

public class TestPoli {
    public static void main(String[] args) {
        Wine[] wines = new Wine[2];

        wines[0] = new JNC(); //向上转型，丢失特有方法，对公共方法执行重写的，未重写，执行父类方法
        wines[1] = new JGJ();

        for(int i = 0 ; i < 2 ; i++){
            System.out.println(wines[i].toString() + "--" + wines[i].drink());
        }
    }
}
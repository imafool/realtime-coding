package com.coco.lang.poli;

public class JGJ extends Wine{
    public JGJ(){
        setName("JGJ");
    }

    public String drink(){
        return "喝的是 " + getName();
    }

    public String toString(){
        return "Wine : " + getName();
    }
}
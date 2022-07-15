package com.coco.lang.poli;

public class JNC extends Wine{
    public JNC(){
        setName("JNC");
    }

    public String drink(){
        return "喝的是 " + getName();
    }

    public String toString(){
        return "Wine : " + getName();
    }
}
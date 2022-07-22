package com.coco.lang.extend;

import com.coco.lang.assemb.funnycase.Husband;

/**
 * Wife 隐藏年龄
 */
public class Wife {
    private String name;
    private int age;
    private String sex;
    private com.coco.lang.assemb.funnycase.Husband husband;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHusband(com.coco.lang.assemb.funnycase.Husband husband) {
        this.husband = husband;
    }

    public Husband getHusband() {
        return husband;
    }

}
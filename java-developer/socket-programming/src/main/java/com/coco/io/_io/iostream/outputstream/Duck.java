package com.coco.io._io.iostream.outputstream;

import java.io.Serializable;

public class Duck implements Serializable {
    private int size;

    private String color;

    public Duck(int size, String color) {
        this.size = size;
        this.color = color;
    }

    public Duck() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Duck{" +
                "size=" + size +
                ", color='" + color + '\'' +
                '}';
    }
}

package com.liner.preferencemanagerdemo;

public class MyObject {
    private String name;

    public MyObject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "name='" + name + '\'' +
                '}';
    }
}

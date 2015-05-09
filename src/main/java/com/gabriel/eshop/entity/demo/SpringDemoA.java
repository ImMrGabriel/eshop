package com.gabriel.eshop.entity.demo;

import java.util.Map;

public class SpringDemoA {
    private int[] intArray;
    private Map<String, SpringDemoB> map;

    public SpringDemoA() {
    }

    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public void setMap(Map<String, SpringDemoB> map) {
        this.map = map;
    }
}

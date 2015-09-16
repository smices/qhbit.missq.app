package com.imissq.model;

public class MisqDataBean extends BaseBean {
    private int water;
    private int oil;
    private int elastic;
    private int smooth;
    private int age;

    private int base;
    private float baseWeight;

    public int getAge() {
        return age;
    }
    public void setAge(int age) { this.age = age; }

    public int getOil() { return oil; }
    public void setOil(int oil) { this.oil = oil; }

    public int getBase() { return base; }
    public void setBase(int base) {
        this.base = base;
    }

    public float getBaseWeight() { return baseWeight; }
    public void setBaseWeight(float baseWeight) {
        this.baseWeight = baseWeight;
    }

    public int getSmooth() {
        return smooth;
    }
    public void setSmooth(int smooth) {
        this.smooth = smooth;
    }

    public int getWater() {
        return water;
    }
    public void setWater(int water) {
        this.water = water;
    }

    public int getElastic() {
        return elastic;
    }
    public void setElastic(int elastic) {
        this.elastic = elastic;
    }
}

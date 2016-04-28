package com.example.guiyuan.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by huqiang on 2016/4/12 10:23.
 */
public class Detail extends DataSupport {
    private int num;
    private String single_count;
    private String weight;

    public Detail() {
    }

    public Detail(int num, String single_count, String weight) {
        this.num = num;
        this.single_count = single_count;
        this.weight = weight;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSingle_count() {
        return single_count;
    }

    public void setSingle_count(String single_count) {
        this.single_count = single_count;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id='" + num + '\'' +
                ", single_count='" + single_count + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}

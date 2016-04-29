package com.example.guiyuan.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by huqiang on 2016/4/12 10:23.
 */
public class Detail extends DataSupport {
    private String rfidcode;
    private String num;
    private String single_count;
    private String weight;

    public Detail() {
    }

    public Detail(String rfidcode, String num, String single_count, String weight) {
        this.rfidcode = rfidcode;
        this.num = num;
        this.single_count = single_count;
        this.weight = weight;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
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

    public String getRfidcode() {
        return rfidcode;
    }

    public void setRfidcode(String rfidcode) {
        this.rfidcode = rfidcode;
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

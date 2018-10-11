package com.cheng.tonglepai.data;

import java.io.Serializable;

/**
 * Created by cheng on 2018/7/10.
 */

public class DeviceListData implements Serializable {
    private String device_model;
    private String img;
    private String device_name;
    private String id;
    private int showNO;
    private double price_purchase;

    public int getShowNO() {
        return showNO;
    }

    public void setShowNO(int showNO) {
        this.showNO = showNO;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice_purchase() {
        return price_purchase;
    }

    public void setPrice_purchase(double price_purchase) {
        this.price_purchase = price_purchase;
    }
}

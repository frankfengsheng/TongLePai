package com.cheng.tonglepai.data;

import java.io.Serializable;

/**
 * Created by cheng on 2018/7/23.
 */

public class InvestorDeviceListData implements Serializable{
    private String id;
    private String name;
    private String details;
    private String device_list;
    private String time;
    private String thismonth;
    private String yesterday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDevice_list() {
        return device_list;
    }

    public void setDevice_list(String device_list) {
        this.device_list = device_list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThismonth() {
        return thismonth;
    }

    public void setThismonth(String thismonth) {
        this.thismonth = thismonth;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }
}

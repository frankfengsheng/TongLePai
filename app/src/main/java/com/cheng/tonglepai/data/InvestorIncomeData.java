package com.cheng.tonglepai.data;

/**
 * Created by cheng on 2018/7/24.
 */

public class InvestorIncomeData {
    private String last_month;
    private String yesterday;
    private String today;
    private String this_month;
    private String id;
    private String store_name;
    private String name;
    private String details;
    private String status;
    private String updated;
    private String nums;
    private String total;

    public String getLast_month() {
        return last_month;
    }

    public void setLast_month(String last_month) {
        this.last_month = last_month;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getThis_month() {
        return this_month;
    }

    public void setThis_month(String this_month) {
        this.this_month = this_month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvestorIncomeData{" +
                "last_month='" + last_month + '\'' +
                ", yesterday='" + yesterday + '\'' +
                ", today='" + today + '\'' +
                ", this_month='" + this_month + '\'' +
                ", id='" + id + '\'' +
                ", store_name='" + store_name + '\'' +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                ", updated='" + updated + '\'' +
                ", nums='" + nums + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}

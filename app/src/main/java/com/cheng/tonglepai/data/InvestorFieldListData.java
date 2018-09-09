package com.cheng.tonglepai.data;

/**
 * Created by cheng on 2018/7/16.
 */

public class InvestorFieldListData {
    private String id;
    private String name;
    private String details;
    private String expected_revenue;
    private String nums;
    private String migrate_num;
    private String status_data;

    public String getStatus_data() {
        return status_data;
    }

    public void setStatus_data(String status_data) {
        this.status_data = status_data;
    }

    public String getMigrate_num() {
        return migrate_num;
    }

    public void setMigrate_num(String migrate_num) {
        this.migrate_num = migrate_num;
    }

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

    public String getExpected_revenue() {
        return expected_revenue;
    }

    public void setExpected_revenue(String expected_revenue) {
        this.expected_revenue = expected_revenue;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}

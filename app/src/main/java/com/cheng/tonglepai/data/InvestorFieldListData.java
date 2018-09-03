package com.cheng.tonglepai.data;

/**
 * Created by cheng on 2018/7/16.
 */

public class InvestorFieldListData {
    private String id;
    private String name;
    private String details;
    private String expected_revenue;
    private int nums;

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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}

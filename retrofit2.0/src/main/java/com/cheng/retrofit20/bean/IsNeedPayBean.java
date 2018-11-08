package com.cheng.retrofit20.bean;

import java.util.List;

/**
 * Created by feng on 2018/11/7 0007.
 * 场地方是否需要缴币借口回调
 */

public class IsNeedPayBean {


    /**
     * data : []
     * price_pay : 32
     * status : 1
     * msg : 成功
     * remind : 0
     */

    private int price_pay;
    private int status;
    private String msg;
    private int remind;
    private List<?> data;

    public int getPrice_pay() {
        return price_pay;
    }

    public void setPrice_pay(int price_pay) {
        this.price_pay = price_pay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}

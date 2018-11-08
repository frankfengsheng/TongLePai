package com.cheng.retrofit20.bean;

/**
 * Created by Administrator on 2018/11/1 0001.
 * 微信提现
 */

public class WechatWithDrawBean {

    /**
     * data :
     * msg : 微信提现申请成功
     * status : 1
     */

    private String data;
    private String msg;
    private int status;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

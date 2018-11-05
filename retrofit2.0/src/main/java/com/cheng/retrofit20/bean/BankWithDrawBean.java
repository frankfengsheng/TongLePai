package com.cheng.retrofit20.bean;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class BankWithDrawBean extends BaseHttpResult{


    /**
     * data :
     * msg : 提现申请成功
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

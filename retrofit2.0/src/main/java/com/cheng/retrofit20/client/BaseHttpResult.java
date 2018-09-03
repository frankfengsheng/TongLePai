package com.cheng.retrofit20.client;

import java.io.Serializable;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class BaseHttpResult implements Serializable {
    private int status;
    private String msg;

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

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "code=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}

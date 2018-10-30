package com.cheng.retrofit20.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29 0029.
 */

public class SingalDetectionBean {


    /**
     * dataerror : []
     * msg : 正常
     * status : 61
     */

    private String msg;
    private int status;
    private List<?> dataerror;

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

    public List<?> getDataerror() {
        return dataerror;
    }

    public void setDataerror(List<?> dataerror) {
        this.dataerror = dataerror;
    }
}

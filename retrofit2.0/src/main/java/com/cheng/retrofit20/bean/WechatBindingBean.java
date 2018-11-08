package com.cheng.retrofit20.bean;

/**
 * Created by Administrator on 2018/10/31 0031.
 * 微信绑定成功回调
 */

public class WechatBindingBean {


    /**
     * dataerror :
     * msg : 微信绑定成功:梁朝伟
     * status : 71
     */

    private String dataerror;
    private String msg;
    private int status;

    public String getDataerror() {
        return dataerror;
    }

    public void setDataerror(String dataerror) {
        this.dataerror = dataerror;
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

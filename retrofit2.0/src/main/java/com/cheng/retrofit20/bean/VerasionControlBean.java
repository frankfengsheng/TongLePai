package com.cheng.retrofit20.bean;

/**
 * Created by Administrator on 2018/11/7 0007.
 * 判断是否更新版本
 */

public class VerasionControlBean {

    /**
     * content : 童乐派 2.8 华丽升级！\n · 更新版本功能点提示窗口添加\n · 场地设备信号查询展示\n · 微信授权绑定、微信提现到零钱\n为商户贴心打造新版APP，功能使用更方便。
     * data : {"android_address":"http://www.tonglepai.cn/app/tonglepai.apk","androidis_update":"1","android_number":"2.8"}
     * status : 19
     * msg : 请下载最新版本童乐派
     */

    private String content;
    private DataBean data;
    private int status;
    private String msg;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * android_address : http://www.tonglepai.cn/app/tonglepai.apk
         * androidis_update : 1
         * android_number : 2.8
         */

        private String android_address;
        private String androidis_update;
        private String android_number;

        public String getAndroid_address() {
            return android_address;
        }

        public void setAndroid_address(String android_address) {
            this.android_address = android_address;
        }

        public String getAndroidis_update() {
            return androidis_update;
        }

        public void setAndroidis_update(String androidis_update) {
            this.androidis_update = androidis_update;
        }

        public String getAndroid_number() {
            return android_number;
        }

        public void setAndroid_number(String android_number) {
            this.android_number = android_number;
        }
    }
}

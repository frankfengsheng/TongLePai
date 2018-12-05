package com.cheng.retrofit20.bean;

import java.util.List;

public class PartnerSiteDeviceIncomeListBean {

    /**
     * data : [{"id":"16","device_code":"30014844","store_name":"南杉网络","details":"河南郑州金水区原盛国际","device_name":"星际小章鱼","sm_price":"2.30","tb_price":"0.00","price":"2.30"},{"id":"17","device_code":"30014841","store_name":"南杉网络","details":"河南郑州金水区原盛国际","device_name":"小猪佩奇","sm_price":"1.00","tb_price":"0.00","price":"1.00"}]
     * msg : 成功
     * status : 1
     */

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 16
         * device_code : 30014844
         * store_name : 南杉网络
         * details : 河南郑州金水区原盛国际
         * device_name : 星际小章鱼
         * sm_price : 2.30
         * tb_price : 0.00
         * price : 2.30
         */

        private String id;
        private String device_code;
        private String store_name;
        private String details;
        private String device_name;
        private String sm_price;
        private String tb_price;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDevice_code() {
            return device_code;
        }

        public void setDevice_code(String device_code) {
            this.device_code = device_code;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
            this.tb_price = tb_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

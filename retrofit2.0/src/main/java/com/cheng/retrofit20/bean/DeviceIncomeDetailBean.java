package com.cheng.retrofit20.bean;

import java.util.List;

public class DeviceIncomeDetailBean {


    /**
     * data : [{"updated":"1541056346","pay_type":"1","price":"0.50"},{"updated":"1541048504","pay_type":"1","price":"0.50"},{"updated":"1540549733","pay_type":"1","price":"0.50"},{"updated":"1540432446","pay_type":"1","price":"0.50"},{"updated":"1540352731","pay_type":"1","price":"0.50"},{"updated":"1535711429","pay_type":"1","price":"1.00"},{"updated":"1535525943","pay_type":"1","price":"1.00"},{"updated":"1535440012","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"},{"updated":"1535252100","pay_type":"1","price":"1.00"}]
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
         * updated : 1541056346
         * pay_type : 1
         * price : 0.50
         */

        private String updated;
        private String pay_type;
        private String price;

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

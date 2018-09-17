package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/9/17.
 */

public class PostMoneyRecordResult extends BaseHttpResult {

    /**
     * data : [{"pay_type":"1","updated":"1537173662","price":"1.00"},{"pay_type":"0","updated":"1537173620","price":"2.00"},{"pay_type":"0","updated":"1537173587","price":"1.00"},{"pay_type":"0","updated":"1537173405","price":"1.00"},{"pay_type":"1","updated":"1537173332","price":"2.00"}]
     * pay_data : {"wj_price":0,"yj_price":7,"z_price":7}
     */

    private PayDataBean pay_data;
    private List<DataBean> data;

    public PayDataBean getPay_data() {
        return pay_data;
    }

    public void setPay_data(PayDataBean pay_data) {
        this.pay_data = pay_data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PayDataBean {
        /**
         * wj_price : 0
         * yj_price : 7
         * z_price : 7
         */

        private String wj_price;
        private String yj_price;
        private String z_price;

        public String getWj_price() {
            return wj_price;
        }

        public void setWj_price(String wj_price) {
            this.wj_price = wj_price;
        }

        public String getYj_price() {
            return yj_price;
        }

        public void setYj_price(String yj_price) {
            this.yj_price = yj_price;
        }

        public String getZ_price() {
            return z_price;
        }

        public void setZ_price(String z_price) {
            this.z_price = z_price;
        }
    }

    public static class DataBean {
        /**
         * pay_type : 1
         * updated : 1537173662
         * price : 1.00
         */

        private String pay_type;
        private String updated;
        private String price;

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

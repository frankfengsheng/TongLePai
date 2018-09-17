package com.cheng.tonglepai.data;

import java.util.List;

/**
 * Created by cheng on 2018/9/17.
 */

public class PostMoneyRecordData {
    private PriceDataBean price_data;
    private List<DataBean> data;

    public PriceDataBean getPrice_data() {
        return price_data;
    }

    public void setPrice_data(PriceDataBean price_data) {
        this.price_data = price_data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PriceDataBean {
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
        private String pay_type;
        private String price;
        private String updated;

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

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }
    }
}

package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/9/17.
 */

public class DeviceBillResult extends BaseHttpResult {

    /**
     * data : [{"updated":"1537170463","pay_source":"1","price":"0.30"},{"updated":"1537170294","pay_source":"0","price":"0.30"},{"updated":"1537170157","pay_source":"0","price":"0.30"}]
     * price_data : {"z_price":0.9}
     */

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
        /**
         * z_price : 0.9
         */

        private String z_price;

        public String getZ_price() {
            return z_price;
        }

        public void setZ_price(String z_price) {
            this.z_price = z_price;
        }
    }

    public static class DataBean {
        /**
         * updated : 1537170463
         * pay_source : 1
         * price : 0.30
         */

        private String updated;
        private String pay_source;
        private String price;

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPay_source() {
            return pay_source;
        }

        public void setPay_source(String pay_source) {
            this.pay_source = pay_source;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

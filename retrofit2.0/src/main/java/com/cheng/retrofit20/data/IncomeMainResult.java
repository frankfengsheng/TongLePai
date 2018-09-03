package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/8/8.
 */

public class IncomeMainResult extends BaseHttpResult {

    /**
     * data : {"total":0,"invest_price":0,"partner_price":0,"place_price":0,"times":1533696088}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 0
         * invest_price : 0
         * partner_price : 0
         * place_price : 0
         * times : 1533696088
         */

        private String total;
        private String invest_price;
        private String partner_price;
        private String place_price;
        private String times;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getInvest_price() {
            return invest_price;
        }

        public void setInvest_price(String invest_price) {
            this.invest_price = invest_price;
        }

        public String getPartner_price() {
            return partner_price;
        }

        public void setPartner_price(String partner_price) {
            this.partner_price = partner_price;
        }

        public String getPlace_price() {
            return place_price;
        }

        public void setPlace_price(String place_price) {
            this.place_price = place_price;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}

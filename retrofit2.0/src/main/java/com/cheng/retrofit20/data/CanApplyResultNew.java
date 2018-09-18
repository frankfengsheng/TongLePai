package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/9/18.
 */

public class CanApplyResultNew extends BaseHttpResult {

    /**
     * data : {"price_pay":"0.00","bank":"中国华夏银行","z_price":100,"price":"100.00","bank_account":"127457477447744745"}
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
         * price_pay : 0.00
         * bank : 中国华夏银行
         * z_price : 100
         * price : 100.00
         * bank_account : 127457477447744745
         */

        private String price_pay;
        private String bank;
        private String z_price;
        private String price;
        private String bank_account;

        public String getPrice_pay() {
            return price_pay;
        }

        public void setPrice_pay(String price_pay) {
            this.price_pay = price_pay;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getZ_price() {
            return z_price;
        }

        public void setZ_price(String z_price) {
            this.z_price = z_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }
    }
}

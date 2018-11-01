package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/7/25.
 */

public class CanApplyResult extends BaseHttpResult {


    /**
     * data : {"openid":"oQZPy1KPk91E5-M7nmrve9Z4xe4Y","wx_nickname":"梁朝伟","price":"3.20","bank":"","bank_account":"","price_pay":"8.00"}
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
         * openid : oQZPy1KPk91E5-M7nmrve9Z4xe4Y
         * wx_nickname : 梁朝伟
         * price : 3.20
         * bank :
         * bank_account :
         * price_pay : 8.00
         */

        private String openid;
        private String wx_nickname;
        private String price;
        private String bank;
        private String bank_account;
        private String price_pay;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getWx_nickname() {
            return wx_nickname;
        }

        public void setWx_nickname(String wx_nickname) {
            this.wx_nickname = wx_nickname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getPrice_pay() {
            return price_pay;
        }

        public void setPrice_pay(String price_pay) {
            this.price_pay = price_pay;
        }
    }
}

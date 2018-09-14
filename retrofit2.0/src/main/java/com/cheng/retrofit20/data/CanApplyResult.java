package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/25.
 */

public class CanApplyResult extends BaseHttpResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bank_account : 1777777777778
         * bank : 谭谭银行
         * price : 0.00
         */

        private String bank_account;
        private String bank;
        private String price;
        private String price_pay;
        private String z_price;

        public String getPrice_pay() {
            return price_pay;
        }

        public void setPrice_pay(String price_pay) {
            this.price_pay = price_pay;
        }

        public String getZ_price() {
            return z_price;
        }

        public void setZ_price(String z_price) {
            this.z_price = z_price;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

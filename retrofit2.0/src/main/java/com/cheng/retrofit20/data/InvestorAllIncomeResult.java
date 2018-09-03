package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/24.
 */

public class InvestorAllIncomeResult extends BaseHttpResult {

    /**
     * data : [{"id":"119","store_name":"sdf","details":"sdf","device_list":15,"tj":[{"days":"1","date":"2018-01-01","price":"0"},{"days":"2","date":"2018-01-02","price":"0"},{"days":"3","date":"2018-01-03","price":"0"},{"days":"4","date":"2018-01-04","price":"0"},{"days":"5","date":"2018-01-05","price":"0"},{"days":"6","date":"2018-01-06","price":"0"},{"days":"7","date":"2018-01-07","price":"0"},{"days":"8","date":"2018-01-08","price":"0"},{"days":"9","date":"2018-01-09","price":"0"},{"days":"10","date":"2018-01-10","price":"0"},{"days":"11","date":"2018-01-11","price":"0"},{"days":"12","date":"2018-01-12","price":"0"},{"days":"13","date":"2018-01-13","price":"0"},{"days":"14","date":"2018-01-14","price":"0"},{"days":"15","date":"2018-01-15","price":"0"},{"days":"16","date":"2018-01-16","price":"0"},{"days":"17","date":"2018-01-17","price":"0"},{"days":"18","date":"2018-01-18","price":"0"},{"days":"19","date":"2018-01-19","price":"0"},{"days":"20","date":"2018-01-20","price":"0"},{"days":"21","date":"2018-01-21","price":"0"},{"days":"22","date":"2018-01-22","price":"0"},{"days":"23","date":"2018-01-23","price":"0"},{"days":"24","date":"2018-01-24","price":"0"},{"days":"25","date":"2018-01-25","price":"0"},{"days":"26","date":"2018-01-26","price":"0"},{"days":"27","date":"2018-01-27","price":"0"},{"days":"28","date":"2018-01-28","price":"0"},{"days":"29","date":"2018-01-29","price":"0"},{"days":"30","date":"2018-01-30","price":"0"},{"days":"31","date":"2018-01-31","price":"0"}],"money":0},{"id":"120","store_name":"erer","details":"re","device_list":12,"tj":[{"days":"1","date":"2018-01-01","price":"0"},{"days":"2","date":"2018-01-02","price":"0"},{"days":"3","date":"2018-01-03","price":"0"},{"days":"4","date":"2018-01-04","price":"0"},{"days":"5","date":"2018-01-05","price":"0"},{"days":"6","date":"2018-01-06","price":"0"},{"days":"7","date":"2018-01-07","price":"0"},{"days":"8","date":"2018-01-08","price":"0"},{"days":"9","date":"2018-01-09","price":"0"},{"days":"10","date":"2018-01-10","price":"0"},{"days":"11","date":"2018-01-11","price":"0"},{"days":"12","date":"2018-01-12","price":"0"},{"days":"13","date":"2018-01-13","price":"0"},{"days":"14","date":"2018-01-14","price":"0"},{"days":"15","date":"2018-01-15","price":"0"},{"days":"16","date":"2018-01-16","price":"0"},{"days":"17","date":"2018-01-17","price":"0"},{"days":"18","date":"2018-01-18","price":"0"},{"days":"19","date":"2018-01-19","price":"0"},{"days":"20","date":"2018-01-20","price":"0"},{"days":"21","date":"2018-01-21","price":"0"},{"days":"22","date":"2018-01-22","price":"0"},{"days":"23","date":"2018-01-23","price":"0"},{"days":"24","date":"2018-01-24","price":"0"},{"days":"25","date":"2018-01-25","price":"0"},{"days":"26","date":"2018-01-26","price":"0"},{"days":"27","date":"2018-01-27","price":"0"},{"days":"28","date":"2018-01-28","price":"0"},{"days":"29","date":"2018-01-29","price":"0"},{"days":"30","date":"2018-01-30","price":"0"},{"days":"31","date":"2018-01-31","price":"0"}],"money":0}]
     * rmb : 0
     */

    private String rmb;
    private List<DataBean> data;

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 119
         * store_name : sdf
         * details : sdf
         * device_list : 15
         * tj : [{"days":"1","date":"2018-01-01","price":"0"},{"days":"2","date":"2018-01-02","price":"0"},{"days":"3","date":"2018-01-03","price":"0"},{"days":"4","date":"2018-01-04","price":"0"},{"days":"5","date":"2018-01-05","price":"0"},{"days":"6","date":"2018-01-06","price":"0"},{"days":"7","date":"2018-01-07","price":"0"},{"days":"8","date":"2018-01-08","price":"0"},{"days":"9","date":"2018-01-09","price":"0"},{"days":"10","date":"2018-01-10","price":"0"},{"days":"11","date":"2018-01-11","price":"0"},{"days":"12","date":"2018-01-12","price":"0"},{"days":"13","date":"2018-01-13","price":"0"},{"days":"14","date":"2018-01-14","price":"0"},{"days":"15","date":"2018-01-15","price":"0"},{"days":"16","date":"2018-01-16","price":"0"},{"days":"17","date":"2018-01-17","price":"0"},{"days":"18","date":"2018-01-18","price":"0"},{"days":"19","date":"2018-01-19","price":"0"},{"days":"20","date":"2018-01-20","price":"0"},{"days":"21","date":"2018-01-21","price":"0"},{"days":"22","date":"2018-01-22","price":"0"},{"days":"23","date":"2018-01-23","price":"0"},{"days":"24","date":"2018-01-24","price":"0"},{"days":"25","date":"2018-01-25","price":"0"},{"days":"26","date":"2018-01-26","price":"0"},{"days":"27","date":"2018-01-27","price":"0"},{"days":"28","date":"2018-01-28","price":"0"},{"days":"29","date":"2018-01-29","price":"0"},{"days":"30","date":"2018-01-30","price":"0"},{"days":"31","date":"2018-01-31","price":"0"}]
         * money : 0
         */

        private String id;
        private String store_name;
        private String details;
        private String device_list;
        private String money;
        private List<TjBean> tj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDevice_list() {
            return device_list;
        }

        public void setDevice_list(String device_list) {
            this.device_list = device_list;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<TjBean> getTj() {
            return tj;
        }

        public void setTj(List<TjBean> tj) {
            this.tj = tj;
        }

        public static class TjBean {
            /**
             * days : 1
             * date : 2018-01-01
             * price : 0
             */

            private String days;
            private String date;
            private String price;

            public String getDays() {
                return days;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}

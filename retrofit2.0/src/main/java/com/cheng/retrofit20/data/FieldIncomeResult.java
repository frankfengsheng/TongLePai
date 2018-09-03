package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/10.
 */

public class FieldIncomeResult extends BaseHttpResult{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 0
         * id : 267
         * this_month : 0
         * details : 北京北京市东城区ing嘻嘻嘻
         * updated : 1533197579
         * status : 5
         * nums : 2
         * invest_status : 1
         * name : 体育场
         * yesterday : 0
         * today : 0
         * last_month : 0
         * store_name : 娜娜
         */

        private String total;
        private String id;
        private String this_month;
        private String details;
        private String updated;
        private String status;
        private String nums;
        private String invest_status;
        private String name;
        private String yesterday;
        private String today;
        private String last_month;
        private String store_name;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThis_month() {
            return this_month;
        }

        public void setThis_month(String this_month) {
            this.this_month = this_month;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getInvest_status() {
            return invest_status;
        }

        public void setInvest_status(String invest_status) {
            this.invest_status = invest_status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYesterday() {
            return yesterday;
        }

        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getLast_month() {
            return last_month;
        }

        public void setLast_month(String last_month) {
            this.last_month = last_month;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }
}

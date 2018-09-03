package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/23.
 */

public class InvestorAddviceResult extends BaseHttpResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 101
         * name : 超市
         * details : 1
         * device_list : 3
         * time : 1532145469
         * thismonth : 300
         * yesterday : 0
         */

        private String id;
        private String name;
        private String details;
        private String device_list;
        private String time;
        private String thismonth;
        private String yesterday;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDevice_list() {
            return device_list;
        }

        public void setDevice_list(String device_list) {
            this.device_list = device_list;
        }

        public String getThismonth() {
            return thismonth;
        }

        public void setThismonth(String thismonth) {
            this.thismonth = thismonth;
        }

        public String getYesterday() {
            return yesterday;
        }

        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }
    }
}

package com.cheng.retrofit20.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class DevicesDetailsBean {
    /**
     * data : {"total":"597.30","sm_price":1670,"tb_price":332}
     * yesterday_data : {"sm_price":0,"tb_price":1,"price":"0.30"}
     * today_data : {"sm_price":0,"tb_price":0,"price":"0.00"}
     * thismonth_data : {"sm_price":110,"tb_price":10,"price":"36.00"}
     * zx_data : [{"2018-11-01":1,"price":6},{"2018-11-02":2,"price":4.8},{"2018-11-03":3,"price":7.2},{"2018-11-04":4,"price":3.6},{"2018-11-05":5,"price":0},{"2018-11-06":6,"price":0},{"2018-11-07":7,"price":0},{"2018-11-08":8,"price":5.1},{"2018-11-09":9,"price":4.2},{"2018-11-10":10,"price":0.3},{"2018-11-11":11,"price":4.5},{"2018-11-12":12,"price":0.3},{"2018-11-13":13,"price":0},{"2018-11-14":14,"price":0},{"2018-11-15":15,"price":0},{"2018-11-16":16,"price":0},{"2018-11-17":17,"price":0},{"2018-11-18":18,"price":0},{"2018-11-19":19,"price":0},{"2018-11-20":20,"price":0},{"2018-11-21":21,"price":0},{"2018-11-22":22,"price":0},{"2018-11-23":23,"price":0},{"2018-11-24":24,"price":0},{"2018-11-25":25,"price":0},{"2018-11-26":26,"price":0},{"2018-11-27":27,"price":0},{"2018-11-28":28,"price":0},{"2018-11-29":29,"price":0},{"2018-11-30":30,"price":0}]
     * msg : 成功
     * status : 1
     */

    private DataBean data;
    private YesterdayDataBean yesterday_data;
    private TodayDataBean today_data;
    private ThismonthDataBean thismonth_data;
    private String msg;
    private int status;
    private List<ZxDataBean> zx_data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public YesterdayDataBean getYesterday_data() {
        return yesterday_data;
    }

    public void setYesterday_data(YesterdayDataBean yesterday_data) {
        this.yesterday_data = yesterday_data;
    }

    public TodayDataBean getToday_data() {
        return today_data;
    }

    public void setToday_data(TodayDataBean today_data) {
        this.today_data = today_data;
    }

    public ThismonthDataBean getThismonth_data() {
        return thismonth_data;
    }

    public void setThismonth_data(ThismonthDataBean thismonth_data) {
        this.thismonth_data = thismonth_data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ZxDataBean> getZx_data() {
        return zx_data;
    }

    public void setZx_data(List<ZxDataBean> zx_data) {
        this.zx_data = zx_data;
    }

    public static class DataBean {
        /**
         * total : 597.30
         * sm_price : 1670
         * tb_price : 332
         */

        private String total;
        private int sm_price;
        private int tb_price;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getSm_price() {
            return sm_price;
        }

        public void setSm_price(int sm_price) {
            this.sm_price = sm_price;
        }

        public int getTb_price() {
            return tb_price;
        }

        public void setTb_price(int tb_price) {
            this.tb_price = tb_price;
        }
    }

    public static class YesterdayDataBean {
        /**
         * sm_price : 0
         * tb_price : 1
         * price : 0.30
         */

        private int sm_price;
        private int tb_price;
        private String price;

        public int getSm_price() {
            return sm_price;
        }

        public void setSm_price(int sm_price) {
            this.sm_price = sm_price;
        }

        public int getTb_price() {
            return tb_price;
        }

        public void setTb_price(int tb_price) {
            this.tb_price = tb_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class TodayDataBean {
        /**
         * sm_price : 0
         * tb_price : 0
         * price : 0.00
         */

        private int sm_price;
        private int tb_price;
        private String price;

        public int getSm_price() {
            return sm_price;
        }

        public void setSm_price(int sm_price) {
            this.sm_price = sm_price;
        }

        public int getTb_price() {
            return tb_price;
        }

        public void setTb_price(int tb_price) {
            this.tb_price = tb_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class ThismonthDataBean {
        /**
         * sm_price : 110
         * tb_price : 10
         * price : 36.00
         */

        private int sm_price;
        private int tb_price;
        private String price;

        public int getSm_price() {
            return sm_price;
        }

        public void setSm_price(int sm_price) {
            this.sm_price = sm_price;
        }

        public int getTb_price() {
            return tb_price;
        }

        public void setTb_price(int tb_price) {
            this.tb_price = tb_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class ZxDataBean {
        private double price;
        private Map map;


        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    /* *//**
     * data : {"total":"0.00","this_month":"0.00","today":"0.00","yesterday":"0.00","last_month":"0.00"}
     * zx_data : [{"2018-11-01":1,"price":0},{"2018-11-02":2,"price":0},{"2018-11-03":3,"price":0},{"2018-11-04":4,"price":0},{"2018-11-05":5,"price":0},{"2018-11-06":6,"price":0},{"2018-11-07":7,"price":0},{"2018-11-08":8,"price":0},{"2018-11-09":9,"price":0},{"2018-11-10":10,"price":0},{"2018-11-11":11,"price":0},{"2018-11-12":12,"price":0},{"2018-11-13":13,"price":0},{"2018-11-14":14,"price":0},{"2018-11-15":15,"price":0},{"2018-11-16":16,"price":0},{"2018-11-17":17,"price":0},{"2018-11-18":18,"price":0},{"2018-11-19":19,"price":0},{"2018-11-20":20,"price":0},{"2018-11-21":21,"price":0},{"2018-11-22":22,"price":0},{"2018-11-23":23,"price":0},{"2018-11-24":24,"price":0},{"2018-11-25":25,"price":0},{"2018-11-26":26,"price":0},{"2018-11-27":27,"price":0},{"2018-11-28":28,"price":0},{"2018-11-29":29,"price":0},{"2018-11-30":30,"price":0}]
     * msg : 无数据
     * status : 55
     *//*

    private DataBean data;
    private String msg;
    private int status;
    private List<ZxDataBean> zx_data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ZxDataBean> getZx_data() {
        return zx_data;
    }

    public void setZx_data(List<ZxDataBean> zx_data) {
        this.zx_data = zx_data;
    }

    public static class DataBean {
        *//**
         * total : 0.00
         * this_month : 0.00
         * today : 0.00
         * yesterday : 0.00
         * last_month : 0.00
         *//*

        private String total;
        private String this_month;
        private String today;
        private String yesterday;
        private String last_month;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getThis_month() {
            return this_month;
        }

        public void setThis_month(String this_month) {
            this.this_month = this_month;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getYesterday() {
            return yesterday;
        }

        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }

        public String getLast_month() {
            return last_month;
        }

        public void setLast_month(String last_month) {
            this.last_month = last_month;
        }
    }

    public static class ZxDataBean {
        *//**
         * 2018-11-01 : 1
         * price : 0
         * 2018-11-02 : 2
         * 2018-11-03 : 3
         * 2018-11-04 : 4
         * 2018-11-05 : 5
         * 2018-11-06 : 6
         * 2018-11-07 : 7
         * 2018-11-08 : 8
         * 2018-11-09 : 9
         * 2018-11-10 : 10
         * 2018-11-11 : 11
         * 2018-11-12 : 12
         * 2018-11-13 : 13
         * 2018-11-14 : 14
         * 2018-11-15 : 15
         * 2018-11-16 : 16
         * 2018-11-17 : 17
         * 2018-11-18 : 18
         * 2018-11-19 : 19
         * 2018-11-20 : 20
         * 2018-11-21 : 21
         * 2018-11-22 : 22
         * 2018-11-23 : 23
         * 2018-11-24 : 24
         * 2018-11-25 : 25
         * 2018-11-26 : 26
         * 2018-11-27 : 27
         * 2018-11-28 : 28
         * 2018-11-29 : 29
         * 2018-11-30 : 30
         *//*

       private double price;
       private Map map;


        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }


    }*/


}

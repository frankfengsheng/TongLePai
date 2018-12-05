package com.cheng.retrofit20.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class PartnerStaticIncomeBean {


    /**
     * data : {"total":"0.00","sm_price":"0.00","tb_price":"0.00"}
     * data_m : {"low_device_nums":0,"device_nums":0,"cd_nums":0}
     * yesterday_data : {"sm_price":"0.00","tb_price":"0.00","price":"0.00"}
     * today_data : {"sm_price":"0.00","tb_price":"0.00","price":"0.00"}
     * thismonth_data : {"sm_price":"0.00","tb_price":"0.00","price":"0.00"}
     * zx_data : [{"2018-12-01":1,"price":0},{"2018-12-02":2,"price":0},{"2018-12-03":3,"price":0},{"2018-12-04":4,"price":0},{"2018-12-05":5,"price":0},{"2018-12-06":6,"price":0},{"2018-12-07":7,"price":0},{"2018-12-08":8,"price":0},{"2018-12-09":9,"price":0},{"2018-12-10":10,"price":0},{"2018-12-11":11,"price":0},{"2018-12-12":12,"price":0},{"2018-12-13":13,"price":0},{"2018-12-14":14,"price":0},{"2018-12-15":15,"price":0},{"2018-12-16":16,"price":0},{"2018-12-17":17,"price":0},{"2018-12-18":18,"price":0},{"2018-12-19":19,"price":0},{"2018-12-20":20,"price":0},{"2018-12-21":21,"price":0},{"2018-12-22":22,"price":0},{"2018-12-23":23,"price":0},{"2018-12-24":24,"price":0},{"2018-12-25":25,"price":0},{"2018-12-26":26,"price":0},{"2018-12-27":27,"price":0},{"2018-12-28":28,"price":0},{"2018-12-29":29,"price":0},{"2018-12-30":30,"price":0},{"2018-12-31":31,"price":0}]
     * msg : 无数据
     * status : 55
     */

    private DataBean data;
    private DataMBean data_m;
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

    public DataMBean getData_m() {
        return data_m;
    }

    public void setData_m(DataMBean data_m) {
        this.data_m = data_m;
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
         * total : 0.00
         * sm_price : 0.00
         * tb_price : 0.00
         */

        private String total;
        private String sm_price;
        private String tb_price;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
            this.tb_price = tb_price;
        }
    }

    public static class DataMBean {
        /**
         * low_device_nums : 0
         * device_nums : 0
         * cd_nums : 0
         */

        private int low_device_nums;
        private int device_nums;
        private int cd_nums;

        public int getLow_device_nums() {
            return low_device_nums;
        }

        public void setLow_device_nums(int low_device_nums) {
            this.low_device_nums = low_device_nums;
        }

        public int getDevice_nums() {
            return device_nums;
        }

        public void setDevice_nums(int device_nums) {
            this.device_nums = device_nums;
        }

        public int getCd_nums() {
            return cd_nums;
        }

        public void setCd_nums(int cd_nums) {
            this.cd_nums = cd_nums;
        }
    }

    public static class YesterdayDataBean {
        /**
         * sm_price : 0.00
         * tb_price : 0.00
         * price : 0.00
         */

        private String sm_price;
        private String tb_price;
        private String price;

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
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
         * sm_price : 0.00
         * tb_price : 0.00
         * price : 0.00
         */

        private String sm_price;
        private String tb_price;
        private String price;

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
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
         * sm_price : 0.00
         * tb_price : 0.00
         * price : 0.00
         */

        private String sm_price;
        private String tb_price;
        private String price;

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
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

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }
    }
}

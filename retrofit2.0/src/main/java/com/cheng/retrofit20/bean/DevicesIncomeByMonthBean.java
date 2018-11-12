package com.cheng.retrofit20.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * 根据月份获取折线图数据
 */
public class DevicesIncomeByMonthBean {


    /**
     * data : []
     * zx_data : [{"2018-10-01":1,"price":0},{"2018-10-02":2,"price":0},{"2018-10-03":3,"price":0},{"2018-10-04":4,"price":0},{"2018-10-05":5,"price":0},{"2018-10-06":6,"price":0},{"2018-10-07":7,"price":0},{"2018-10-08":8,"price":0},{"2018-10-09":9,"price":0},{"2018-10-10":10,"price":0},{"2018-10-11":11,"price":0},{"2018-10-12":12,"price":0},{"2018-10-13":13,"price":0},{"2018-10-14":14,"price":0},{"2018-10-15":15,"price":0},{"2018-10-16":16,"price":0},{"2018-10-17":17,"price":0},{"2018-10-18":18,"price":0},{"2018-10-19":19,"price":0},{"2018-10-20":20,"price":0},{"2018-10-21":21,"price":0},{"2018-10-22":22,"price":0},{"2018-10-23":23,"price":0},{"2018-10-24":24,"price":0},{"2018-10-25":25,"price":0},{"2018-10-26":26,"price":0},{"2018-10-27":27,"price":0},{"2018-10-28":28,"price":0},{"2018-10-29":29,"price":0},{"2018-10-30":30,"price":0},{"2018-10-31":31,"price":0}]
     * msg : 无数据
     * status : 55
     */

    private String msg;
    private int status;
    private List<?> data;
    private List<ZxDataBean> zx_data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public List<ZxDataBean> getZx_data() {
        return zx_data;
    }

    public void setZx_data(List<ZxDataBean> zx_data) {
        this.zx_data = zx_data;
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
}

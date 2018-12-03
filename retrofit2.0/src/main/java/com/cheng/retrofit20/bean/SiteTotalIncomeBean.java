package com.cheng.retrofit20.bean;

import java.util.List;
import java.util.Map;

public class SiteTotalIncomeBean {


    /**
     * zx_data : [{"2018-12-01":1,"price":0},{"2018-12-02":2,"price":0},{"2018-12-03":3,"price":0},{"2018-12-04":4,"price":0},{"2018-12-05":5,"price":0},{"2018-12-06":6,"price":0},{"2018-12-07":7,"price":0},{"2018-12-08":8,"price":0},{"2018-12-09":9,"price":0},{"2018-12-10":10,"price":0},{"2018-12-11":11,"price":0},{"2018-12-12":12,"price":0},{"2018-12-13":13,"price":0},{"2018-12-14":14,"price":0},{"2018-12-15":15,"price":0},{"2018-12-16":16,"price":0},{"2018-12-17":17,"price":0},{"2018-12-18":18,"price":0},{"2018-12-19":19,"price":0},{"2018-12-20":20,"price":0},{"2018-12-21":21,"price":0},{"2018-12-22":22,"price":0},{"2018-12-23":23,"price":0},{"2018-12-24":24,"price":0},{"2018-12-25":25,"price":0},{"2018-12-26":26,"price":0},{"2018-12-27":27,"price":0},{"2018-12-28":28,"price":0},{"2018-12-29":29,"price":0},{"2018-12-30":30,"price":0},{"2018-12-31":31,"price":0}]
     * msg : 无数据
     * status : 55
     */

    private String msg;
    private int status;
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

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }
    }
}

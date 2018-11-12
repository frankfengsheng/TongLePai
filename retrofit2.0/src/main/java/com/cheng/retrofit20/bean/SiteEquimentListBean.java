package com.cheng.retrofit20.bean;

import java.io.Serializable;
import java.util.List;

public class SiteEquimentListBean {

    /**
     * data : [{"device_id":"128","device_name":"QQ鲸","device_code":"30014885","created":"1540362656","lastday":"0.60","thismonth":"7.80","status":"1"},{"device_id":"129","device_name":"皮卡丘2","device_code":"30014882","created":"1540362657","lastday":"0.00","thismonth":"0.90","status":"1"},{"device_id":"130","device_name":"熊熊乐园","device_code":"30014888","created":"1540362657","lastday":"0.00","thismonth":"9.30","status":"1"},{"device_id":"131","device_name":"星际小章鱼","device_code":"30014886","created":"1540362657","lastday":"0.00","thismonth":"11.40","status":"1"}]
     * msg : 成功
     * status : 1
     */

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * device_id : 128
         * device_name : QQ鲸
         * device_code : 30014885
         * created : 1540362656
         * lastday : 0.60
         * thismonth : 7.80
         * status : 1
         */

        private String device_id;
        private String device_name;
        private String device_code;
        private String created;
        private String lastday;
        private String thismonth;
        private String status;

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getDevice_code() {
            return device_code;
        }

        public void setDevice_code(String device_code) {
            this.device_code = device_code;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLastday() {
            return lastday;
        }

        public void setLastday(String lastday) {
            this.lastday = lastday;
        }

        public String getThismonth() {
            return thismonth;
        }

        public void setThismonth(String thismonth) {
            this.thismonth = thismonth;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

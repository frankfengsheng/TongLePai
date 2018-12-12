package com.cheng.retrofit20.bean;

import java.util.List;

public class DeviceIncomeDetailBean {


    /**
     * data : [{"pay_source":"1","updated":"1544358564","price":"0.10"},{"pay_source":"1","updated":"1544354612","price":"0.10"},{"pay_source":"1","updated":"1544348030","price":"0.10"},{"pay_source":"1","updated":"1544340933","price":"0.10"},{"pay_source":"1","updated":"1544266868","price":"0.10"},{"pay_source":"1","updated":"1544259048","price":"0.10"},{"pay_source":"1","updated":"1544258789","price":"0.10"},{"pay_source":"1","updated":"1544255014","price":"0.10"},{"pay_source":"0","updated":"1544250069","price":"0.10"},{"pay_source":"1","updated":"1544247955","price":"0.10"},{"pay_source":"1","updated":"1544235873","price":"0.10"},{"pay_source":"1","updated":"1544182582","price":"0.10"},{"pay_source":"1","updated":"1544158170","price":"0.10"},{"pay_source":"0","updated":"1544097635","price":"0.10"},{"pay_source":"0","updated":"1544087733","price":"0.10"},{"pay_source":"0","updated":"1544087136","price":"0.10"},{"pay_source":"1","updated":"1544074339","price":"0.10"},{"pay_source":"0","updated":"1544067455","price":"0.10"},{"pay_source":"0","updated":"1544066160","price":"0.10"},{"pay_source":"0","updated":"1544064949","price":"0.10"}]
     * status : 1
     * msg : 成功
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pay_source : 1
         * updated : 1544358564
         * price : 0.10
         */
        private String pay_source;
        private String updated;
        private String price;

        public String getPay_source() {
            return pay_source;
        }

        public void setPay_source(String pay_source) {
            this.pay_source = pay_source;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

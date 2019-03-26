package com.cheng.retrofit20.bean;

import com.google.gson.annotations.SerializedName;

public class WechatPayResultBean {

    /**
     * data : {"appid":null,"noncestr":"d2saohf9r8ooj0om0n2hn7o9awv7ckjm","package":"Sign=WXPay","partnerid":null,"prepayid":"wx2112343703665116509872ee0383498818","timestamp":1553141202,"sign":"144621E0E98A97876819E50D482ECAC5"}
     * msg : 调用成功
     * status : 1
     */

    private DataBean data;
    private String msg;
    private int status;

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

    public static class DataBean {
        /**
         * appid : null
         * noncestr : d2saohf9r8ooj0om0n2hn7o9awv7ckjm
         * package : Sign=WXPay
         * partnerid : null
         * prepayid : wx2112343703665116509872ee0383498818
         * timestamp : 1553141202
         * sign : 144621E0E98A97876819E50D482ECAC5
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}

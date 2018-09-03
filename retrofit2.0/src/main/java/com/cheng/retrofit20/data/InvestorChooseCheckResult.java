package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/18.
 */

public class InvestorChooseCheckResult extends BaseHttpResult {


    /**
     * data : [{"code":"yyc_001","nums":"1","img":"http://www.tonglepai.cn/Uploads/device/20180620/max_5b29fd4a39c43.jpg","device_name":"摇摇车"},{"code":"zwwj_001","nums":"2","img":"http://www.tonglepai.cn/Uploads/device/20180620/max_5b2a03a0ca861.jpg","device_name":"抓娃娃机"},{"code":"amy_001","nums":"1","img":"http://www.tonglepai.cn/Uploads/device/20180627/max_5b32fbbfad86f.jpg","device_name":"按摩椅"}]
     * betting_fee : null
     */
    private String betting_fee;
    private String id;
    private String total;
    private String shipping_fee;
    private List<DataBean> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getBetting_fee() {
        return betting_fee;
    }

    public void setBetting_fee(String betting_fee) {
        this.betting_fee = betting_fee;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : yyc_001
         * nums : 1
         * img : http://www.tonglepai.cn/Uploads/device/20180620/max_5b29fd4a39c43.jpg
         * device_name : 摇摇车
         */

        private String code;
        private int nums;
        private String img;
        private String device_name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }
    }
}

package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/9/10.
 */

public class RefereeListResult extends BaseHttpResult {

    /**
     * data : [{"price":"500.00","city":"上海","created":"1536382731","name":"许智勇","tel":"15900482662","level":"0"},{"price":"500.00","city":"上海","created":"1536382731","name":"老宋","tel":"13601817760","level":"1"},{"price":"700.00","city":"上海","created":"1536382731","name":"王建","tel":"18937197679","level":"3"}]
     * price_data : {"ls_price":"1.21","nums":3,"z_price":1700}
     */

    private PriceDataBean price_data;
    private List<DataBean> data;

    public PriceDataBean getPrice_data() {
        return price_data;
    }

    public void setPrice_data(PriceDataBean price_data) {
        this.price_data = price_data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PriceDataBean {
        /**
         * ls_price : 1.21
         * nums : 3
         * z_price : 1700
         */

        private String ls_price;
        private String nums;
        private String z_price;
        private String info_nums;
        private String xq_price;

        public String getXq_price() {
            return xq_price;
        }

        public void setXq_price(String xq_price) {
            this.xq_price = xq_price;
        }

        public String getInfo_nums() {
            return info_nums;
        }

        public void setInfo_nums(String info_nums) {
            this.info_nums = info_nums;
        }

        public String getLs_price() {
            return ls_price;
        }

        public void setLs_price(String ls_price) {
            this.ls_price = ls_price;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getZ_price() {
            return z_price;
        }

        public void setZ_price(String z_price) {
            this.z_price = z_price;
        }
    }

    public static class DataBean {
        /**
         * price : 500.00
         * city : 上海
         * created : 1536382731
         * name : 许智勇
         * tel : 15900482662
         * level : 0
         */

        private String price;
        private String city;
        private String created;
        private String name;
        private String tel;
        private String level;
        private String id;
        private String device_nums;
        private String details;
        private String store_name;

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDevice_nums() {
            return device_nums;
        }

        public void setDevice_nums(String device_nums) {
            this.device_nums = device_nums;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}

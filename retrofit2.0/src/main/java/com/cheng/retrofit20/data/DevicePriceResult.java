package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/9/17.
 */

public class DevicePriceResult extends BaseHttpResult {

    /**
     * data : [{"store_device_id":"68","device_name":"打地鼠机","price":0.9},{"store_device_id":"69","device_name":"打地鼠机","price":0},{"store_device_id":"70","device_name":"射水枪","price":0.9}]
     * info_data : {"id":"47","store_name":"2018917test2store","details":"北京北京市朝阳区好再来2超市","device_nums":3,"device_list_status":"1","z_price":1.8}
     */

    private InfoDataBean info_data;
    private List<DataBean> data;

    public InfoDataBean getInfo_data() {
        return info_data;
    }

    public void setInfo_data(InfoDataBean info_data) {
        this.info_data = info_data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class InfoDataBean {
        /**
         * id : 47
         * store_name : 2018917test2store
         * details : 北京北京市朝阳区好再来2超市
         * device_nums : 3
         * device_list_status : 1
         * z_price : 1.8
         */

        private String id;
        private String store_name;
        private String details;
        private String device_nums;
        private String device_list_status;
        private String z_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDevice_nums() {
            return device_nums;
        }

        public void setDevice_nums(String device_nums) {
            this.device_nums = device_nums;
        }

        public String getDevice_list_status() {
            return device_list_status;
        }

        public void setDevice_list_status(String device_list_status) {
            this.device_list_status = device_list_status;
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
         * store_device_id : 68
         * device_name : 打地鼠机
         * price : 0.9
         */

        private String store_device_id;
        private String device_name;
        private String price;

        public String getStore_device_id() {
            return store_device_id;
        }

        public void setStore_device_id(String store_device_id) {
            this.store_device_id = store_device_id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

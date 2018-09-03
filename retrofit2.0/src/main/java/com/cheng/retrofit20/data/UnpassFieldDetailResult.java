package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/23.
 */

public class UnpassFieldDetailResult extends BaseHttpResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 151
         * store_business_id : 222
         * store_name : 222
         * store_area_all : 22.00
         * store_area_able : 22.00
         * customer_flow : 222
         * expected_revenue : 222
         * province : 11
         * city : 1101
         * distinct : 110101
         * details : 2222
         * mobile : 17602192840
         * telphone : 17602192840
         * store_interior_1 : http://www.tonglepai.cn/Uploads/store_info/20180720/infotlp1532068716960982.jpeg
         * store_interior_2 :
         * store_interior_3 :
         * store_interior_4 :
         * store_exterior_1 : http://www.tonglepai.cn/Uploads/store_info/20180720/infotlp1532068716960621.jpeg
         * store_exterior_2 :
         * store_exterior_3 :
         * store_exterior_4 :
         * device_list : [{"amy_001":"2","yyc_001":"3","zwwj_001":"3"}]
         */

        private String id;
        private String store_business_id;
        private String store_name;
        private String store_area_all;
        private String store_area_able;
        private String customer_flow;
        private String expected_revenue;
        private String province;
        private String city;
        private String distinct;
        private String details;
        private String mobile;
        private String area_temp;
        private String telphone;
        private String store_interior_1;
        private String store_interior_2;
        private String store_interior_3;
        private String store_interior_4;
        private String store_exterior_1;
        private String store_exterior_2;
        private String store_exterior_3;
        private String store_exterior_4;
        private List<DeviceListBean> device_list;

        public String getArea_temp() {
            return area_temp;
        }

        public void setArea_temp(String area_temp) {
            this.area_temp = area_temp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStore_business_id() {
            return store_business_id;
        }

        public void setStore_business_id(String store_business_id) {
            this.store_business_id = store_business_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_area_all() {
            return store_area_all;
        }

        public void setStore_area_all(String store_area_all) {
            this.store_area_all = store_area_all;
        }

        public String getStore_area_able() {
            return store_area_able;
        }

        public void setStore_area_able(String store_area_able) {
            this.store_area_able = store_area_able;
        }

        public String getCustomer_flow() {
            return customer_flow;
        }

        public void setCustomer_flow(String customer_flow) {
            this.customer_flow = customer_flow;
        }

        public String getExpected_revenue() {
            return expected_revenue;
        }

        public void setExpected_revenue(String expected_revenue) {
            this.expected_revenue = expected_revenue;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistinct() {
            return distinct;
        }

        public void setDistinct(String distinct) {
            this.distinct = distinct;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getStore_interior_1() {
            return store_interior_1;
        }

        public void setStore_interior_1(String store_interior_1) {
            this.store_interior_1 = store_interior_1;
        }

        public String getStore_interior_2() {
            return store_interior_2;
        }

        public void setStore_interior_2(String store_interior_2) {
            this.store_interior_2 = store_interior_2;
        }

        public String getStore_interior_3() {
            return store_interior_3;
        }

        public void setStore_interior_3(String store_interior_3) {
            this.store_interior_3 = store_interior_3;
        }

        public String getStore_interior_4() {
            return store_interior_4;
        }

        public void setStore_interior_4(String store_interior_4) {
            this.store_interior_4 = store_interior_4;
        }

        public String getStore_exterior_1() {
            return store_exterior_1;
        }

        public void setStore_exterior_1(String store_exterior_1) {
            this.store_exterior_1 = store_exterior_1;
        }

        public String getStore_exterior_2() {
            return store_exterior_2;
        }

        public void setStore_exterior_2(String store_exterior_2) {
            this.store_exterior_2 = store_exterior_2;
        }

        public String getStore_exterior_3() {
            return store_exterior_3;
        }

        public void setStore_exterior_3(String store_exterior_3) {
            this.store_exterior_3 = store_exterior_3;
        }

        public String getStore_exterior_4() {
            return store_exterior_4;
        }

        public void setStore_exterior_4(String store_exterior_4) {
            this.store_exterior_4 = store_exterior_4;
        }

        public List<DeviceListBean> getDevice_list() {
            return device_list;
        }

        public void setDevice_list(List<DeviceListBean> device_list) {
            this.device_list = device_list;
        }

        public static class DeviceListBean {

        }
    }
}

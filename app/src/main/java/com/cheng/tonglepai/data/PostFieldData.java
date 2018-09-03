package com.cheng.tonglepai.data;

import java.util.List;

/**
 * Created by cheng on 2018/7/9.
 */

public class PostFieldData {

    /**
     * city : 1101
     * created : 2018-07-09 13:48:53
     * customer_flow : sdf
     * details : sdf
     * distinct : 110101
     * expected_revenue : df
     * mobile : sdf
     * province : 11
     * store_area_able : sdf
     * store_area_all : sdf
     * store_business_id : 1
     * store_name : dsf
     * telphone : sdf
     * tlp : [{"yyc_001":"1"},{"zwwj_001":"1"},{"amy_001":"1"}]
     * userid : 1
     */

    private String city;
    private String customer_flow;
    private String details;
    private String distinct;
    private String expected_revenue;
    private String mobile;
    private String province;
    private String store_area_able;
    private String store_area_all;
    private String store_business_id; //
    private String store_name; //
    private String telphone;
    private String userid; //
    private String area_temp; //
    private String store_interior_1;
    private String store_interior_2;
    private String store_interior_3;
    private String store_interior_4;

    private String store_exterior_1;
    private String store_exterior_2;
    private String store_exterior_3;
    private String store_exterior_4;
    private List<TlpBean> tlp;

    public String getArea_temp() {
        return area_temp;
    }

    public void setArea_temp(String area_temp) {
        this.area_temp = area_temp;
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

    public String getStore_interior_2() {
        return store_interior_2;
    }

    public void setStore_interior_2(String store_interior_2) {
        this.store_interior_2 = store_interior_2;
    }

    public String getStore_interior_1() {
        return store_interior_1;
    }

    public void setStore_interior_1(String store_interior_1) {
        this.store_interior_1 = store_interior_1;
    }

    public String getStore_exterior_1() {
        return store_exterior_1;
    }

    public void setStore_exterior_1(String store_exterior_1) {
        this.store_exterior_1 = store_exterior_1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getCustomer_flow() {
        return customer_flow;
    }

    public void setCustomer_flow(String customer_flow) {
        this.customer_flow = customer_flow;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getExpected_revenue() {
        return expected_revenue;
    }

    public void setExpected_revenue(String expected_revenue) {
        this.expected_revenue = expected_revenue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStore_area_able() {
        return store_area_able;
    }

    public void setStore_area_able(String store_area_able) {
        this.store_area_able = store_area_able;
    }

    public String getStore_area_all() {
        return store_area_all;
    }

    public void setStore_area_all(String store_area_all) {
        this.store_area_all = store_area_all;
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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<TlpBean> getTlp() {
        return tlp;
    }

    public void setTlp(List<TlpBean> tlp) {
        this.tlp = tlp;
    }

    public static class TlpBean {
        /**
         * yyc_001 : 1
         * zwwj_001 : 1
         * amy_001 : 1
         */

        private String yyc_001;
        private String zwwj_001;
        private String amy_001;

        public String getYyc_001() {
            return yyc_001;
        }

        public void setYyc_001(String yyc_001) {
            this.yyc_001 = yyc_001;
        }

        public String getZwwj_001() {
            return zwwj_001;
        }

        public void setZwwj_001(String zwwj_001) {
            this.zwwj_001 = zwwj_001;
        }

        public String getAmy_001() {
            return amy_001;
        }

        public void setAmy_001(String amy_001) {
            this.amy_001 = amy_001;
        }
    }
}

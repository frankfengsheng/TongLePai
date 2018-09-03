package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/5/21.
 */

public class TestData extends BaseHttpResult {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 3
         * image : http://localhost/zbj/3
         * name : 格力空调
         * is_free_shipping : 0
         * income : 450
         * price : 5000.00
         * sales : 0
         * benefit : 50
         * is_poster : 1
         */

        private String id;
        private String image;
        private String name;
        private int is_free_shipping;
        private int income;
        private String price;
        private String sales;
        private String benefit;
        private int is_poster;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_free_shipping() {
            return is_free_shipping;
        }

        public void setIs_free_shipping(int is_free_shipping) {
            this.is_free_shipping = is_free_shipping;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getBenefit() {
            return benefit;
        }

        public void setBenefit(String benefit) {
            this.benefit = benefit;
        }

        public int getIs_poster() {
            return is_poster;
        }

        public void setIs_poster(int is_poster) {
            this.is_poster = is_poster;
        }
    }
}

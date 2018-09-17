package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/7/26.
 */

public class InvestorUserInfoResult extends BaseHttpResult {

    /**
     * data : {"nickename":"未设置","tel":"17602192840","price":"0.00","img":"http://www.tonglepai.cn/Uploads/108.png","my_device_nums":0,"yt_device_nums":0,"zy_device_nums":"0","z_shouyi":0,"today":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nickename : 未设置
         * tel : 17602192840
         * price : 0.00
         * img : http://www.tonglepai.cn/Uploads/108.png
         * my_device_nums : 0
         * yt_device_nums : 0
         * zy_device_nums : 0
         * z_shouyi : 0
         * today : 0
         */

        private String nickename;
        private String tel;
        private String price;
        private String img;
        private String my_device_nums;
        private String yt_device_nums;
        private String zy_device_nums;
        private String z_shouyi;
        private String today;
        private String level;
        private String sm_shouyi;
        private String tb_shouyi;

        public String getSm_shouyi() {
            return sm_shouyi;
        }

        public void setSm_shouyi(String sm_shouyi) {
            this.sm_shouyi = sm_shouyi;
        }

        public String getTb_shouyi() {
            return tb_shouyi;
        }

        public void setTb_shouyi(String tb_shouyi) {
            this.tb_shouyi = tb_shouyi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getNickename() {
            return nickename;
        }

        public void setNickename(String nickename) {
            this.nickename = nickename;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMy_device_nums() {
            return my_device_nums;
        }

        public void setMy_device_nums(String my_device_nums) {
            this.my_device_nums = my_device_nums;
        }

        public String getYt_device_nums() {
            return yt_device_nums;
        }

        public void setYt_device_nums(String yt_device_nums) {
            this.yt_device_nums = yt_device_nums;
        }

        public String getZy_device_nums() {
            return zy_device_nums;
        }

        public void setZy_device_nums(String zy_device_nums) {
            this.zy_device_nums = zy_device_nums;
        }

        public String getZ_shouyi() {
            return z_shouyi;
        }

        public void setZ_shouyi(String z_shouyi) {
            this.z_shouyi = z_shouyi;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }
    }
}

package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/7/2.
 */

public class UserInfoResult extends BaseHttpResult{


    /**
     * data : {"nickname":null,"tel":"18788874195","count":"0","count1":"0","num":0,"ct":"0","total":0,"price":0}
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
         * nickname : null
         * tel : 18788874195
         * count : 0
         * count1 : 0
         * num : 0
         * ct : 0
         * total : 0
         * price : 0
         */

        private String nickename;
        private String tel;
        private String img;
        private String cd_nums;
        private String yt_nums;
        private String zg_nums;
        private String z_shouyi;
        private String today;
        private String price;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getCd_nums() {
            return cd_nums;
        }

        public void setCd_nums(String cd_nums) {
            this.cd_nums = cd_nums;
        }

        public String getYt_nums() {
            return yt_nums;
        }

        public void setYt_nums(String yt_nums) {
            this.yt_nums = yt_nums;
        }

        public String getZg_nums() {
            return zg_nums;
        }

        public void setZg_nums(String zg_nums) {
            this.zg_nums = zg_nums;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

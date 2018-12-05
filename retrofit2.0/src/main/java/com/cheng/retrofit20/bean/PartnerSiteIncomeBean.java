package com.cheng.retrofit20.bean;

import java.util.List;

public class PartnerSiteIncomeBean {


    /**
     * data : [{"info_id":"32","store_name":"二十一世纪社区超市","details":"河南郑州金水区花园北路二十一世纪社区","sm_price":"211.80","tb_price":"110.90","price":"322.70","device_nums":2},{"info_id":"36","store_name":"二十一世纪社区超市","details":"河南郑州金水区花园北路二十一世纪社区","sm_price":"156.90","tb_price":"21.50","price":"178.40","device_nums":1},{"info_id":"35","store_name":"二十一世纪玩具店","details":"河南郑州金水区花园北路二十一世纪社区","sm_price":"79.00","tb_price":"43.50","price":"122.50","device_nums":2},{"info_id":"56","store_name":"21世纪社区玩具店","details":"河南郑州市辖区花园北路二十一世纪社区","sm_price":"15.60","tb_price":"22.60","price":"38.20","device_nums":1},{"info_id":"16","store_name":"南杉网络","details":"河南郑州金水区原盛国际","sm_price":"3.30","tb_price":"0.00","price":"3.30","device_nums":2},{"info_id":"177","store_name":"乐豆童装","details":"河南郑州市辖区21世纪社区北门","sm_price":"1.40","tb_price":"0.90","price":"2.30","device_nums":1},{"info_id":"174","store_name":"21世纪社区超市","details":"河南郑州市辖区花园路21世纪社区西门","sm_price":"0.00","tb_price":"0.80","price":"0.80","device_nums":1}]
     * msg : 成功
     * status : 1
     */

    private String msg;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * info_id : 32
         * store_name : 二十一世纪社区超市
         * details : 河南郑州金水区花园北路二十一世纪社区
         * sm_price : 211.80
         * tb_price : 110.90
         * price : 322.70
         * device_nums : 2
         */

        private String info_id;
        private String store_name;
        private String details;
        private String sm_price;
        private String tb_price;
        private String price;
        private int device_nums;

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
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

        public String getSm_price() {
            return sm_price;
        }

        public void setSm_price(String sm_price) {
            this.sm_price = sm_price;
        }

        public String getTb_price() {
            return tb_price;
        }

        public void setTb_price(String tb_price) {
            this.tb_price = tb_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getDevice_nums() {
            return device_nums;
        }

        public void setDevice_nums(int device_nums) {
            this.device_nums = device_nums;
        }
    }
}

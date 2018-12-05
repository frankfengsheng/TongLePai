package com.cheng.retrofit20.bean;

import java.util.List;

public class PartnerDeviceIncomeListBean {


    /**
     * data : [{"id":"41","device_code":"30014298","store_name":"二十一世纪社区超市","details":"河南郑州金水区花园北路二十一世纪社区","device_name":"星际小章鱼","sm_price":"149.00","tb_price":"55.50","price":"204.50"},{"id":"48","device_code":"30014269","store_name":"二十一世纪社区超市","details":"河南郑州金水区花园北路二十一世纪社区","device_name":"射水枪","sm_price":"156.90","tb_price":"21.50","price":"178.40"},{"id":"40","device_code":"30014297","store_name":"二十一世纪社区超市","details":"河南郑州金水区花园北路二十一世纪社区","device_name":"小猪佩奇","sm_price":"62.80","tb_price":"55.40","price":"118.20"},{"id":"46","device_code":"30014834","store_name":"二十一世纪玩具店","details":"河南郑州金水区花园北路二十一世纪社区","device_name":"星际小章鱼","sm_price":"56.50","tb_price":"32.90","price":"89.40"},{"id":"52","device_code":"0","store_name":"鑫顺地产","details":"河南郑州金水区花园北路二十一世纪社区西门","device_name":"星际小章鱼","sm_price":"39.10","tb_price":"20.50","price":"59.60"},{"id":"84","device_code":"0","store_name":"二十一世纪社区超市","details":"河南郑州市辖区花园北路二十一世纪社区","device_name":"儿童枪机","sm_price":"24.20","tb_price":"17.10","price":"41.30"},{"id":"83","device_code":"30014854","store_name":"21世纪社区玩具店","details":"河南郑州市辖区花园北路二十一世纪社区","device_name":"熊熊乐园","sm_price":"15.60","tb_price":"22.60","price":"38.20"},{"id":"47","device_code":"30014266","store_name":"二十一世纪玩具店","details":"河南郑州金水区花园北路二十一世纪社区","device_name":"老爷车","sm_price":"22.50","tb_price":"10.60","price":"33.10"},{"id":"51","device_code":"0","store_name":"鑫顺地产","details":"河南郑州金水区花园北路二十一世纪社区西门","device_name":"捕鱼机","sm_price":"11.40","tb_price":"0.00","price":"11.40"},{"id":"99","device_code":"0","store_name":"DIY玩具店","details":"河南郑州市辖区郑州市二七区兴华南街百姓路交叉口","device_name":"星际小章鱼","sm_price":"2.30","tb_price":"4.00","price":"6.30"}]
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
         * id : 41
         * device_code : 30014298
         * store_name : 二十一世纪社区超市
         * details : 河南郑州金水区花园北路二十一世纪社区
         * device_name : 星际小章鱼
         * sm_price : 149.00
         * tb_price : 55.50
         * price : 204.50
         */

        private String id;
        private String device_code;
        private String store_name;
        private String details;
        private String device_name;
        private String sm_price;
        private String tb_price;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDevice_code() {
            return device_code;
        }

        public void setDevice_code(String device_code) {
            this.device_code = device_code;
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

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
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
    }
}

package com.cheng.retrofit20.data;

import java.util.List;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class EquimentDetailResult {


    /**
     * data : [{"img":"http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc602ef67.jpg"},{"img":"http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc14f328c.png"},{"img":"http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc27dad3e.png"},{"img":"http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc6fb86e3.jpg"},{"img":"http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc7df02b8.png"}]
     * titel : 这是标题
     * content : aaaaaaaaaaaaaaaaaaaaaaasss1132
     * msg : 成功
     * status : 1
     */

    private String titel;
    private String content;
    private String msg;
    private int status;
    private List<DataBean> data;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
         * img : http://app.tonglepai.cn/Uploads/device/20180928/max_5badfc602ef67.jpg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

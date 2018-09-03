package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

import java.util.List;

/**
 * Created by cheng on 2018/7/30.
 */

public class CheckokRecordsResult extends BaseHttpResult {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 197
         * name : 电影院
         * details : 北京北京市东城区kejijingcheng
         * device_list : 3
         * records : [{"created":"1532766369","status":"1"}]
         */

        private String id;
        private String name;
        private String details;
        private int device_list;
        private List<RecordsBean> records;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getDevice_list() {
            return device_list;
        }

        public void setDevice_list(int device_list) {
            this.device_list = device_list;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * created : 1532766369
             * status : 1
             */

            private String created;
            private String status;

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}

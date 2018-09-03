package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/7/16.
 */

public class ChooseTypeResult extends BaseHttpResult {


    /**
     * data : {"id":"425"}
     * token : BWMDOVVnVTZRLw10XTIAOAI8B3NUJAYgBDRbIAoyVmcDJg02AnEEZ1JkCztSJQE7UGYCYVJ1AG4JL1ppVjNSPQUgA2BVOlVoUTENJV0zAH0CPAdmVGsGcQQlWz4KK1ZnA3cNZAJmBH9SbAt8
     */

    private DataBean data;
    private String token;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * id : 425
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

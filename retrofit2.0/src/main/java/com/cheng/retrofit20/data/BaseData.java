package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/6/21.
 */

public class BaseData extends BaseHttpResult {

    /**
     * data :
     * status : 0
     */

    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}

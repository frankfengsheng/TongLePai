package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/6/27.
 */

public class SmsLoginResult extends BaseHttpResult {

    /**
     * data :
     * tel : 17602192840
     */

    private String data;
    private String tel;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

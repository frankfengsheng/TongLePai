package com.cheng.retrofit20.data;

import com.cheng.retrofit20.client.BaseHttpResult;

/**
 * Created by cheng on 2018/7/27.
 */

public class AlipayResult extends BaseHttpResult {

    /**
     * data : alipay_sdk=alipay-sdk-php-20180705&app_id=2018061460358430&biz_content=%7B%22body%22%3A%22aa%22%2C%22subject%22%3A%22test%22%2C%22out_trade_no%22%3A%221807271024849975%22%2C%22timeout_express%22%3A%2210m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.tonglepai.cn%2Fapi%2FInvestor%2Fnotifyurl&sign_type=RSA2&timestamp=2018-07-27+13%3A31%3A29&version=1.0&sign=HZtxP7b0VgWZGKk62TBMXI%2B8vthZ9%2BXOUDwOoAHLZp7SSOTbMUr6oPH9bDOS%2BqhrvZ6QPdY5l5aRADv4IQ4cG77ypjPxUEJCXmrf7RbVmbXJnNL2HM%2FlYLupsmOvLtpAavFur9cuz3%2FyR2phnwZGyMeHBH2bucOJZo8IMJCC8mBfgiC1sQnLIxJAJSCkYIeKJNwqb7Q2fbCAIozET3b%2B5r0Chx8ntuVh8F8S4UeOSedS30BVXdDzYpTelocKVZDnZK4pQWHBtPygB7STeTGsckBgFpfY%2Fwi1c5mayWfcaaNQdqt%2FP07mrrypTcTlOSxUSNomVvSZq2t6GzPj0aHKrA%3D%3D
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

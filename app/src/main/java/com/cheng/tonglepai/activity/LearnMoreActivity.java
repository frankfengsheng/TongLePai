package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/8/7.
 */

public class LearnMoreActivity extends TitleActivity {
    public static final String TYPE = "type";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_learn_more);
        MyApplication.getInstance().addActivity(this);
        if (getIntent().getIntExtra(TYPE, 0) == 4)
            setMidTitle("合作服务协议");
        else
            setMidTitle("了解权益");
        initView();
    }

    private void initView() {

        webView = (WebView) findViewById(R.id.webview);
        if (getIntent().getIntExtra(TYPE, 0) == 1) {
            webView.loadUrl("http://m.zhengbajing.com/t-l-p/investor/investor.html");

        } else if (getIntent().getIntExtra(TYPE, 0) == 2) {
            webView.loadUrl("http://m.zhengbajing.com/t-l-p/investor/partner.html");
        } else if (getIntent().getIntExtra(TYPE, 0) == 3) {
            webView.loadUrl("http://m.zhengbajing.com/t-l-p/investor/city.html");
        } else if (getIntent().getIntExtra(TYPE, 0) == 4) {
            webView.loadUrl("http://m.zhengbajing.com/t-l-p/investor/agreement.html");
        }
    }
}

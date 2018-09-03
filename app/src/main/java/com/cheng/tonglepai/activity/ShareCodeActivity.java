package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.ShareCodeData;
import com.cheng.tonglepai.net.ShareCodeRequest;

/**
 * Created by cheng on 2018/8/1.
 */

public class ShareCodeActivity extends TitleActivity {
    private TextView tvShareCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_share_code);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("童乐派");
        initView();
        initData();

    }

    private void initView() {
        tvShareCode = (TextView) findViewById(R.id.tv_share_code);
        tvShareCode.setTextIsSelectable(true);
    }

    private void initData() {
        ShareCodeRequest mRequest = new ShareCodeRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<ShareCodeData>() {
            @Override
            public void onSuccess(ShareCodeData data) {
                tvShareCode.setText(data.getShareCode());
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(ShareCodeActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        mRequest.requestShareCode();
    }
}

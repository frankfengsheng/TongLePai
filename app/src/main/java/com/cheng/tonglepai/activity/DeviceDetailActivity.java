package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.FieldIncomeData;
import com.cheng.tonglepai.data.InvestorIncomeData;
import com.cheng.tonglepai.net.FieldIncomeRequest;
import com.cheng.tonglepai.net.FieldMoveInRequest;
import com.cheng.tonglepai.net.InvestorIncomeRequest;
import com.cheng.tonglepai.tool.MyReturnDialog;
import com.cheng.tonglepai.tool.MyToast;
import com.cheng.tonglepai.tool.TimeUtil;

/**
 * Created by cheng on 2018/7/10.
 */

public class DeviceDetailActivity extends TitleActivity {

   

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_income);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("查看");

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }
}

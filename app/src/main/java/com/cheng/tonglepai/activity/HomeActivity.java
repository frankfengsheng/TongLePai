package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/5/31.
 */

public class HomeActivity extends TitleActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState, R.layout.activity_home_page);
        MyApplication.getInstance().addActivity(this);
    }
}

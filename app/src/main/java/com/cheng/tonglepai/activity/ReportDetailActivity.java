package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/9/10.
 */

public class ReportDetailActivity extends TitleActivity {
    private TextView btnFillin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_report_guize);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("童乐派");
        initView();
    }

    private void initView() {
        btnFillin = (TextView) findViewById(R.id.tv_to_input);

        btnFillin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportDetailActivity.this,ReportFillInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

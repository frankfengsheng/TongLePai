package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/7/26.
 */

public class PostFieldSuccessActivity extends TitleActivity {
    private Button btnToFieldList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_post_field_success);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地列表");
        initView();


    }

    private void initView() {
        btnToFieldList = (Button) findViewById(R.id.btn_to_field);
        btnToFieldList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostFieldSuccessActivity.this,HasPostFieldFieldActivity.class);
                startActivity(intent);
            }
        });
    }

}

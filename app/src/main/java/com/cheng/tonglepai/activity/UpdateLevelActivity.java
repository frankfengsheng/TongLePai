package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.OnMultiClickListener;

/**
 * Created by cheng on 2018/8/2.
 */


public class UpdateLevelActivity extends TitleActivity {
    private Button btnUpdate;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        public void run() {
            btnUpdate.setEnabled(true);
            btnUpdate.setBackgroundColor(Color.parseColor("#45a7fe"));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_marker_update_level);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("会员等级");
        initView();

    }

    private void initView() {
        btnUpdate = (Button) findViewById(R.id.btn_to_update_level);

        btnUpdate.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                btnUpdate.setEnabled(false);
                btnUpdate.setBackgroundColor(Color.parseColor("#4F4F4F"));
                mHandler.postDelayed(mRunnable, 1000*60*60);

                Intent intent = new Intent(UpdateLevelActivity.this,PublicResultActivity.class);
                intent.putExtra(PublicResultActivity.TYPE,3);
                startActivity(intent);
            }
        });
    }
}

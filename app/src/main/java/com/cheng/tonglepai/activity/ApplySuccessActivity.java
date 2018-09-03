package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/6/8.
 */

public class ApplySuccessActivity extends TitleActivity {
    private Button btnToHome;
    public static final String IS_SUCCESS = "is.success";
    public static final String TYPE = "type";
    private boolean isSuccsee;
    private TextView text1,text2,text3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_wait_apply);
        MyApplication.getInstance().addActivity(this);
        isSuccsee = getIntent().getBooleanExtra(IS_SUCCESS,false);
        setMidTitle("申请提现");
        addRightView();
        initView();
    }

    private void initView() {
        btnToHome = (Button) findViewById(R.id.btn_back_home);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        if(isSuccsee){
            btnToHome.setText("返回首页");
            text1.setText("提现申请成功");
            text2.setText("1-3个工作日到账");
            text3.setVisibility(View.GONE);
        }else{
            btnToHome.setText("重新提现");
            text1.setText("提现失败");
            text2.setText("可能是网络或其他原因导致提现失败");
            text3.setVisibility(View.VISIBLE);
        }
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSuccsee){
                    //返回主页
                    Intent intent = new Intent(ApplySuccessActivity.this,UserCenterActivity.class);
                    intent.putExtra(UserCenterActivity.USER_TYPE,getIntent().getIntExtra(TYPE,0));
                    startActivity(intent);
                }else {
                    //重新提现
                    finish();
                }
            }
        });
    }

    private void addRightView() {

        TextView rightView = (TextView) View.inflate(this, R.layout.view_title_right_text, null);
        rightView.setText("查看明细");
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ApplySuccessActivity.this, ApplyDetailActivity.class);
                intent.putExtra("type",getIntent().getIntExtra(TYPE,0));
                startActivity(intent);
            }
        });
        setRightView(rightView);

    }
}

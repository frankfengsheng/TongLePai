package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.net.LoginoutRequest;

/**
 * Created by cheng on 2018/7/5.
 */

public class PersonSettingActivity extends TitleActivity implements View.OnClickListener {
    public static final String NICKNAME = "nick.name";
    public static final String USERPHOTO = "user.photo";
    private Button btnLogOut;
    private RelativeLayout llBindPhone;
    private TextView tvOldPhone,tvUserName;
    private int userType;
    private ImageView ivUserPhoto;
    private String tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_person_setting);
        MyApplication.getInstance().addActivity(this);
        tel = HttpConfig.newInstance(this).getUserTel();
        setMidTitle("个人设置");
        initView();
    }

    private void initView() {
        userType = getIntent().getIntExtra("type", 0);
        btnLogOut = (Button) findViewById(R.id.btn_log_out);
        llBindPhone = (RelativeLayout) findViewById(R.id.rl_bind_phone);
        tvOldPhone = (TextView) findViewById(R.id.tv_old_phone);
        tvUserName = (TextView) findViewById(R.id.tv_user_nickname);
        String telphone = tel.substring(0, 3) + "****" + tel.substring(7, 11);
        tvOldPhone.setText(telphone);
        ivUserPhoto = (ImageView) findViewById(R.id.iv_user_photo);
        llBindPhone.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        MyBitmapUtil myBitmapUtil = new MyBitmapUtil(PersonSettingActivity.this, getIntent().getStringExtra(USERPHOTO));
        myBitmapUtil.display(getIntent().getStringExtra(USERPHOTO), ivUserPhoto);

        tvUserName.setText(getIntent().getStringExtra(NICKNAME));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_out:
                logout();
                break;
        }

        switch (v.getId()) {
            case R.id.rl_bind_phone:
                Intent intent = new Intent(PersonSettingActivity.this, SmsLoginActivity.class);
                intent.putExtra(SmsLoginActivity.TYPE, userType);
                startActivity(intent);
                break;
        }
    }

    private void logout() {
        LoginoutRequest mRequest = new LoginoutRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                Intent intent = new Intent(PersonSettingActivity.this, LoginActivity.class);
                HttpConfig.newInstance(PersonSettingActivity.this).setUserTel("");
                HttpConfig.newInstance(PersonSettingActivity.this).setAccessToken("");
                HttpConfig.newInstance(PersonSettingActivity.this).setUserid("");
                HttpConfig.newInstance(PersonSettingActivity.this).setUserPwd("");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(PersonSettingActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        mRequest.requestLogout(tel);
    }
}

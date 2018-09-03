package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.TestData;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.FieldSmsRequest;
import com.cheng.tonglepai.net.FieldUpdateTelRequest;
import com.cheng.tonglepai.net.IncestorUpdateTelRequest;
import com.cheng.tonglepai.net.InvestorSendSmsRequest;
import com.cheng.tonglepai.net.LoginoutRequest;
import com.cheng.tonglepai.net.MarkerSendSmsRequest;
import com.cheng.tonglepai.net.MarkerUpdateTelRequest;
import com.cheng.tonglepai.tool.AccountValidatorUtil;
import com.cheng.tonglepai.tool.VerifyTimerUtil;

/**
 * Created by cheng on 2018/6/27.
 */

public class SmsLoginActivity extends TitleActivity {
    private TextView tvOldPhone;
    private EditText etPhone, etSms;
    private Button btnToSave, tvSendSms;
    private int userType;
    private VerifyTimerUtil timerUtil;
    public static final String TYPE = "type";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_sms_login);
        setMidTitle("绑定手机");
        MyApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        tvOldPhone = (TextView) findViewById(R.id.et_phone_old);
        tvOldPhone.setText(HttpConfig.newInstance(this).getUserTel());
        userType = getIntent().getIntExtra(TYPE, 0);
        etPhone = (EditText) findViewById(R.id.et_phone_no);
        tvSendSms = (Button) findViewById(R.id.tv_get_verify_code);
        btnToSave = (Button) findViewById(R.id.btn_to_save);
        etSms = (EditText) findViewById(R.id.et_sms);
        timerUtil = new VerifyTimerUtil(this, tvSendSms);
        tvSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    Toast.makeText(SmsLoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!AccountValidatorUtil.isMobile(etPhone.getText().toString().trim())) {
                    Toast.makeText(SmsLoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPhone.getText().toString().trim().equals(HttpConfig.newInstance(SmsLoginActivity.this).getUserTel())) {
                    Toast.makeText(SmsLoginActivity.this, "与原绑定手机号码相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userType == 1) {
                    InvestorSendSmsRequest mRequest = new InvestorSendSmsRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<TestData>() {
                        @Override
                        public void onSuccess(TestData data) {
                            Toast.makeText(SmsLoginActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
                            timerUtil.verifyCodeComeDown();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestInvestorSendSms(etPhone.getText().toString().trim());
                } else if (userType == 2) {
                    MarkerSendSmsRequest mRequest = new MarkerSendSmsRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            Toast.makeText(SmsLoginActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
                            timerUtil.verifyCodeComeDown();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestInvestorSendSms(etPhone.getText().toString().trim());
                } else if (userType == 3) {
                    FieldSmsRequest mRequest = new FieldSmsRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            Toast.makeText(SmsLoginActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
                            timerUtil.verifyCodeComeDown();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestFeedBack(etPhone.getText().toString().trim());
                }
            }
        });

        btnToSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    Toast.makeText(SmsLoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(etSms.getText().toString().trim())) {
                    Toast.makeText(SmsLoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userType == 1) {
                    IncestorUpdateTelRequest mRequest = new IncestorUpdateTelRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            logout();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestIncestorUpdateTel(etPhone.getText().toString().trim(), etSms.getText().toString().trim());
                } else if (userType == 2) {
                    MarkerUpdateTelRequest mRequest = new MarkerUpdateTelRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            logout();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestMarkerUpdateTel(etPhone.getText().toString().trim(), etSms.getText().toString().trim());
                }else if (userType == 3) {
                    FieldUpdateTelRequest mRequest = new FieldUpdateTelRequest(SmsLoginActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            logout();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(SmsLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestFieldUpdateTel(etPhone.getText().toString().trim(), etSms.getText().toString().trim());
                }

            }
        });
    }

    private void logout() {
        LoginoutRequest mRequest = new LoginoutRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                Intent intent = new Intent(SmsLoginActivity.this, LoginActivity.class);
                HttpConfig.newInstance(SmsLoginActivity.this).setUserTel("");
                HttpConfig.newInstance(SmsLoginActivity.this).setAccessToken("");
                HttpConfig.newInstance(SmsLoginActivity.this).setUserid("");
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(SmsLoginActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        mRequest.requestLogout(HttpConfig.newInstance(SmsLoginActivity.this).getUserTel());
    }
}

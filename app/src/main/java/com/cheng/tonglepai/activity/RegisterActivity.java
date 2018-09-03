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
import com.cheng.retrofit20.data.TestData;
import com.cheng.tonglepai.MainActivity;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.SmsLoginData;
import com.cheng.tonglepai.net.RegisterRequest;
import com.cheng.tonglepai.net.TestRequest;
import com.cheng.tonglepai.tool.AccountValidatorUtil;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.VerifyTimerUtil;

/**
 * Created by cheng on 2018/8/7.
 */

public class RegisterActivity extends TitleActivity {
    private LoadingDialog loadingDialog;
    private Button btnLogin;
    private EditText etPhoneNo, etName;
    private EditText etVerifyCode;
    private EditText etShareCode, etPsw;
    private Button btnGetCode;
    private VerifyTimerUtil timerUtil;
    private TextView tvToMore,tvToLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_register);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("注册");

        initView();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("请等待...");
        loadingDialog.setCancelable(false);
        btnLogin = (Button) findViewById(R.id.btn_to_login);

        etName = (EditText) findViewById(R.id.et_name);
        etPhoneNo = (EditText) findViewById(R.id.et_phone_number);
        etPsw = (EditText) findViewById(R.id.et_psw);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        etShareCode = (EditText) findViewById(R.id.et_share_code);
        tvToMore = (TextView) findViewById(R.id.tv_to_more);
        tvToLogin = (TextView) findViewById(R.id.tv_to_login);
        tvToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LearnMoreActivity.class);
                intent.putExtra(LearnMoreActivity.TYPE, 4);
                startActivity(intent);
            }
        });
        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);finish();
            }
        });
        btnGetCode = (Button) findViewById(R.id.tv_get_verify_code);

        timerUtil = new VerifyTimerUtil(this, btnGetCode);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNo.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!AccountValidatorUtil.isMobile(etPhoneNo.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingDialog.show();
                TestRequest mRequest = new TestRequest(RegisterActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<TestData>() {
                    @Override
                    public void onSuccess(TestData data) {
                        Toast.makeText(RegisterActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        timerUtil.verifyCodeComeDown();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });
                mRequest.requestTest(etPhoneNo.getText().toString().trim());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNo.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etVerifyCode.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etPsw.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingDialog.show();
                RegisterRequest mRequest = new RegisterRequest(RegisterActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<SmsLoginData>() {
                    @Override
                    public void onSuccess(SmsLoginData data) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.USER_PHONE, data.getTel());
                        loadingDialog.dismiss();
                        startActivity(intent);
                        LoginActivity.loginActivity.finish();
                        finish();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(RegisterActivity.this, "验证码错误，登录失败", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });
                mRequest.requestRegister(etName.getText().toString().trim(), etPhoneNo.getText().toString().trim(), etVerifyCode.getText().toString().trim(), etShareCode.getText().toString().trim(),etPsw.getText().toString().trim());

            }
        });
    }
}

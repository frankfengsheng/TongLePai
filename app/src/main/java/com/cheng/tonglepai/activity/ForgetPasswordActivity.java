package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.TestData;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.ModifyPwdRequest;
import com.cheng.tonglepai.net.TestRequest;
import com.cheng.tonglepai.tool.AccountValidatorUtil;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.VerifyTimerUtil;

/**
 * Created by cheng on 2018/8/11.
 */

public class ForgetPasswordActivity extends TitleActivity {
    private Button btnGetCode;
    private EditText etPhoneNo, etVerifyCode, etPsw, etPswAgain;
    private VerifyTimerUtil timerUtil;
    private LoadingDialog loadingDialog;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_forget_password);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("忘记密码");

        initView();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("请等待...");
        loadingDialog.setCancelable(false);
        etPhoneNo = (EditText) findViewById(R.id.et_phone_number);
        btnGetCode = (Button) findViewById(R.id.tv_get_verify_code);
        btnLogin = (Button) findViewById(R.id.btn_to_login);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        etPsw = (EditText) findViewById(R.id.et_psw);
        etPswAgain = (EditText) findViewById(R.id.et_psw_again);

        timerUtil = new VerifyTimerUtil(this, btnGetCode);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNo.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!AccountValidatorUtil.isMobile(etPhoneNo.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingDialog.show();
                TestRequest mRequest = new TestRequest(ForgetPasswordActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<TestData>() {
                    @Override
                    public void onSuccess(TestData data) {
                        Toast.makeText(ForgetPasswordActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        timerUtil.verifyCodeComeDown();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(ForgetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgetPasswordActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(etVerifyCode.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etPsw.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etPswAgain.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!etPsw.getText().toString().trim().equals(etPswAgain.getText().toString().trim())){
                    Toast.makeText(ForgetPasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingDialog.show();
                ModifyPwdRequest mRequest = new ModifyPwdRequest(ForgetPasswordActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                    @Override
                    public void onSuccess(BaseHttpResult data) {
                        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                        loadingDialog.dismiss();
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(ForgetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });
                mRequest.requestLogin(etPhoneNo.getText().toString().trim(),etVerifyCode.getText().toString().trim(),etPsw.getText().toString().trim(),etPswAgain.getText().toString().trim());

            }
        });
    }


}

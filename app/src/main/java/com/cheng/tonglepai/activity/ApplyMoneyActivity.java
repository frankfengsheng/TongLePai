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
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.ApplyMoneyRequest;
import com.cheng.tonglepai.net.FieldApplyMoneyRequest;
import com.cheng.tonglepai.net.InvestorApplyMoneyRequest;

/**
 * Created by cheng on 2018/6/8.
 */

public class ApplyMoneyActivity extends TitleActivity {
    private TextView canApplyMoney;
    private EditText etApplyMoney;
    private Button btnToApply;
    public static final String CAN_APPLY_MONEY = "apply.money";
    public static final String BANK_ACCOUNT = "bank.account";
    public static final String BANK_NAME = "bank.name";
    public static final String USER_TYPE = "user.type";
    private int userType = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_apply_money);
        MyApplication.getInstance().addActivity(this);
        userType = getIntent().getIntExtra(USER_TYPE, 0);
        setMidTitle("提现");
        addRightView();
        initView();
    }

    private void initView() {
        canApplyMoney = (TextView) findViewById(R.id.tv_can_apply);
        canApplyMoney.setText("￥" + getIntent().getStringExtra(CAN_APPLY_MONEY));
        etApplyMoney = (EditText) findViewById(R.id.et_apply_money);
        btnToApply = (Button) findViewById(R.id.btn_to_apply);
        btnToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etApplyMoney.getText().toString().trim())) {
                    Toast.makeText(ApplyMoneyActivity.this, "请输入申请提现金额", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Integer.parseInt(etApplyMoney.getText().toString().trim()) < 1) {
                    Toast.makeText(ApplyMoneyActivity.this, "单笔提现不小于1元", Toast.LENGTH_LONG).show();
                    return;
                }
                if (userType == 2) {
                    ApplyMoneyRequest mRequest = new ApplyMoneyRequest(ApplyMoneyActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);
                            Toast.makeText(ApplyMoneyActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });

//                    mRequest.requestApplyMoney(etApplyMoney.getText().toString().trim());
                } else if (userType == 1) {
                    InvestorApplyMoneyRequest mRequest = new InvestorApplyMoneyRequest(ApplyMoneyActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            //马上提现
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(ApplyMoneyActivity.this, msg, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);
                        }
                    });

//                    mRequest.requestInvestorApplyMoney(etApplyMoney.getText().toString().trim());
                }else if (userType == 3) {
                    FieldApplyMoneyRequest mRequest = new FieldApplyMoneyRequest(ApplyMoneyActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            //马上提现
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(ApplyMoneyActivity.this, msg, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ApplyMoneyActivity.this, ApplySuccessActivity.class);
                            intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                            intent.putExtra(ApplySuccessActivity.TYPE, userType);
                            startActivity(intent);
                        }
                    });

//                    mRequest.requestFieldApplyMoney(etApplyMoney.getText().toString().trim());
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
                intent.setClass(ApplyMoneyActivity.this, ApplyDetailActivity.class);
                intent.putExtra("type", userType);
                startActivity(intent);
            }
        });
        setRightView(rightView);

    }
}

package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
 * Created by cheng on 2018/9/9.
 */

public class ApplyMoneyActivityNew extends TitleActivity {
    public static final String CAN_APPLY_MONEY = "apply.money";
    public static final String BANK_ACCOUNT = "bank.account";
    public static final String BANK_NAME = "bank.name";
    public static final String USER_TYPE = "user.type";
    private int userType = 0;
    private TextView bankName, bankAccount, tvCanApplyMoney, tvRealMoney;
    private Button btnToApply;
    private EditText etApplyMoney;
    private String bankNameShow, bankShow;
    private TextView shouxu_money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_apply_money_new);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("提现");
        userType = getIntent().getIntExtra(USER_TYPE, 0);
        addRightView();
        initView();
    }

    private void initView() {
        bankName = (TextView) findViewById(R.id.bank_name);
        bankAccount = (TextView) findViewById(R.id.bank_account);
        tvCanApplyMoney = (TextView) findViewById(R.id.tv_can_apply_money);
        tvRealMoney = (TextView) findViewById(R.id.tv_real_money);

        bankNameShow = getIntent().getStringExtra(BANK_NAME);
        bankShow = getIntent().getStringExtra(BANK_ACCOUNT);
        bankName.setText(bankNameShow);
        bankAccount.setText(bankShow);
        tvCanApplyMoney.setText(getIntent().getStringExtra(CAN_APPLY_MONEY));

        shouxu_money = (TextView) findViewById(R.id.shouxu_money);
        etApplyMoney = (EditText) findViewById(R.id.et_apply_money);
        etApplyMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (Double.parseDouble(s.toString()) * 0.006 < 1) {
                        shouxu_money.setText("1");
                        tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - 1));
                    } else {
                        if (String.valueOf(Double.parseDouble(s.toString()) * 0.006).length() > 4) {
                            shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4));
                            tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4))));
                        } else {
                            shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006));
                            tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(s.toString()) * 0.006));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });

        btnToApply = (Button) findViewById(R.id.btn_to_apply);
        btnToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toApply();
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
                intent.setClass(ApplyMoneyActivityNew.this, ApplyDetailActivity.class);
                intent.putExtra("type", userType);
                startActivity(intent);
            }
        });
        setRightView(rightView);
    }

    private void toApply() {
        if (TextUtils.isEmpty(etApplyMoney.getText().toString().trim())) {
            Toast.makeText(ApplyMoneyActivityNew.this, "请输入申请提现金额", Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.parseInt(etApplyMoney.getText().toString().trim()) < 1) {
            Toast.makeText(ApplyMoneyActivityNew.this, "单笔提现不小于10元", Toast.LENGTH_LONG).show();
            return;
        }
        if (userType == 2) {
            ApplyMoneyRequest mRequest = new ApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }

                @Override
                public void onFailed(String msg, int code) {
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                }
            });

            mRequest.requestApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        } else if (userType == 1) {
            InvestorApplyMoneyRequest mRequest = new InvestorApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    //马上提现
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);

                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }
            });

            mRequest.requestInvestorApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        } else if (userType == 3) {
            FieldApplyMoneyRequest mRequest = new FieldApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    //马上提现
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);

                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }
            });

            mRequest.requestFieldApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        }


    }
}

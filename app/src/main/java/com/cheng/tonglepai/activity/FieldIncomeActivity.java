package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.FieldIncomeData;
import com.cheng.tonglepai.data.InvestorIncomeData;
import com.cheng.tonglepai.net.FieldIncomeRequest;
import com.cheng.tonglepai.net.FieldMoveInRequest;
import com.cheng.tonglepai.net.InvestorIncomeRequest;
import com.cheng.tonglepai.tool.TimeUtil;

/**
 * Created by cheng on 2018/7/10.
 */

public class FieldIncomeActivity extends TitleActivity {

    public static final String FIELD_ID = "field.id";
    //    public static final String DEVICE_ID = "device.id";
    public static final String TYPE = "type";
    private TextView tvShopName, tvShopAddress, tvFieldTime, tvDeviceNo, tvAllIncome, tvReturnDevice;
    private TextView tvDayIncome, tvMonthIncome, tvYesterdayIncome, tvLastmonthIncome;
    private Button btnReturnDevice;
    private String nums = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_income);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("查看");

        initView();
        initData();
    }

    private void initView() {
        tvShopName = (TextView) findViewById(R.id.tv_field_shop_name);
        tvShopAddress = (TextView) findViewById(R.id.tv_field_address);
        tvFieldTime = (TextView) findViewById(R.id.tv_field_time);
        tvDeviceNo = (TextView) findViewById(R.id.tv_device_no);
        tvDayIncome = (TextView) findViewById(R.id.tv_today_income);
        tvMonthIncome = (TextView) findViewById(R.id.tv_month_income);
        tvYesterdayIncome = (TextView) findViewById(R.id.tv_yesterday_income);
        tvLastmonthIncome = (TextView) findViewById(R.id.tv_last_month_income);
        tvAllIncome = (TextView) findViewById(R.id.tv_all_money);
        tvReturnDevice = (TextView) findViewById(R.id.tv_return_device);
        btnReturnDevice = (Button) findViewById(R.id.btn_return_device);
        if (1 == getIntent().getIntExtra(TYPE, 0)) {
            btnReturnDevice.setVisibility(View.VISIBLE);
            tvReturnDevice.setVisibility(View.VISIBLE);
        } else if (2 == getIntent().getIntExtra(TYPE, 0)) {
            btnReturnDevice.setVisibility(View.GONE);
            tvReturnDevice.setVisibility(View.GONE);
        }
        btnReturnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FieldMoveInRequest mRequest = new FieldMoveInRequest(FieldIncomeActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                    @Override
                    public void onSuccess(BaseHttpResult data) {
                        Intent intent = new Intent(FieldIncomeActivity.this, PublicResultActivity.class);
                        intent.putExtra(PublicResultActivity.TYPE, 1);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(FieldIncomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                mRequest.requestFieldMoveIn(getIntent().getStringExtra(FIELD_ID),nums);
            }
        });
    }

    private void initData() {
        if (1 == getIntent().getIntExtra(TYPE, 0)) {
            InvestorIncomeRequest mRequest = new InvestorIncomeRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorIncomeData>() {
                @Override
                public void onSuccess(InvestorIncomeData data) {
                    Log.i("---------", data.toString() + "");
                    tvShopName.setText(data.getStore_name());
                    tvShopAddress.setText(data.getDetails());
                    tvFieldTime.setText("起投时间：" + TimeUtil.alltimes(data.getUpdated()));
                    nums = data.getNums();
                    tvDeviceNo.setText(data.getNums() + "台");
                    tvDayIncome.setText("￥  " + data.getToday());
                    tvMonthIncome.setText("￥  " + data.getThis_month());
                    tvYesterdayIncome.setText("￥  " + data.getYesterday());
                    tvLastmonthIncome.setText("￥  " + data.getLast_month());
                    tvAllIncome.setText("￥ " + data.getTotal());
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(FieldIncomeActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            });
            mRequest.requestInvestorIncome(getIntent().getStringExtra(FIELD_ID));
        } else if (2 == getIntent().getIntExtra(TYPE, 0)) {
            FieldIncomeRequest mRequest = new FieldIncomeRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<FieldIncomeData>() {
                @Override
                public void onSuccess(FieldIncomeData data) {
                    tvShopName.setText(data.getStore_name());
                    tvShopAddress.setText(data.getDetails());
                    tvFieldTime.setText("起投时间：" + TimeUtil.alltimes(data.getUpdated()));
                    tvDeviceNo.setText(data.getNums() + "台");
                    tvDayIncome.setText("￥  " + data.getToday());
                    tvMonthIncome.setText("￥  " + data.getThis_month());
                    tvYesterdayIncome.setText("￥  " + data.getYesterday());
                    tvLastmonthIncome.setText("￥  " + data.getLast_month());
                    tvAllIncome.setText("￥ " + data.getTotal());
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(FieldIncomeActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            });

            mRequest.requestFieldIncome(getIntent().getStringExtra(FIELD_ID));
        }
    }
}

package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.PayReturnInfoData;
import com.cheng.tonglepai.net.MarkerPayReturnInfoRequest;
import com.cheng.tonglepai.net.PayReturnInfoRequest;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cheng on 2018/7/27.
 */

public class PaySuccessActivity extends TitleActivity {
    public static final String PAY_ORDER_NO = "pay.order.no";
    public static final String PAY_ID = "pay.id";
    public static final String IS_FEIGHT = "is.feight";
    private TextView tvMoney, tvType, tvTel, tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_pay_success);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("订单支付");
        if (getIntent().getBooleanExtra(IS_FEIGHT, false))
            addRightView();
        initView();
        initData();
    }

    private void initView() {
        tvMoney = (TextView) findViewById(R.id.tv_order_money);
        tvType = (TextView) findViewById(R.id.tv_order_type);
        tvTel = (TextView) findViewById(R.id.tv_order_tel);
        tvTime = (TextView) findViewById(R.id.tv_order_time);

    }

    private void addRightView() {

        TextView rightView = (TextView) View.inflate(this, R.layout.view_title_right_text, null);
        rightView.setText("运费明细");
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaySuccessActivity.this, FreightListActivity.class);
                startActivity(intent);
            }
        });
        setRightView(rightView);

    }

    private void initData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (getIntent().getBooleanExtra(IS_FEIGHT, false)) {
                    MarkerPayReturnInfoRequest mRequest = new MarkerPayReturnInfoRequest(PaySuccessActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<PayReturnInfoData>() {
                        @Override
                        public void onSuccess(PayReturnInfoData data) {
                            tvMoney.setText(data.getPrice());
                            tvType.setText(data.getType());
                            tvTel.setText(data.getTel());
                            tvTime.setText(TimeUtil.alltimes(data.getTimes()));
                            PayOnlineActivity.payOnlineActivity.finish();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(PaySuccessActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestMarkerPayReturnInfo(getIntent().getStringExtra(PAY_ORDER_NO), getIntent().getStringExtra(PAY_ID));
                } else {
                    PayReturnInfoRequest mRequest = new PayReturnInfoRequest(PaySuccessActivity.this);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<PayReturnInfoData>() {
                        @Override
                        public void onSuccess(final PayReturnInfoData data) {
                            tvMoney.setText(data.getPrice());
                            tvType.setText(data.getType());
                            tvTel.setText(data.getTel());
                            tvTime.setText(TimeUtil.alltimes(data.getTimes()));

                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                public void run() {
                                    Intent intent = new Intent(PaySuccessActivity.this, PayDeviceSuccessaActivity.class);
                                    intent.putExtra(PayDeviceSuccessaActivity.DEVICE_NO, data.getNums() + "");
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    PayOnlineActivity.payOnlineActivity.finish();
                                    FiledDetailActivity.filedDetailActivity.finish();
                                }
                            };
                            timer.schedule(task, 2500);

                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(PaySuccessActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequest.requestAllDevice(getIntent().getStringExtra(PAY_ORDER_NO), getIntent().getStringExtra(PAY_ID));
                }
            }
        };
        timer.schedule(task, 2000);


    }
}

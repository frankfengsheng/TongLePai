package com.cheng.tonglepai.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.AlipayRequest;
import com.cheng.tonglepai.net.MarkerAlipayRequest;
import com.cheng.tonglepai.pay.AuthResult;
import com.cheng.tonglepai.pay.PayResult;
import com.cheng.tonglepai.tool.AdvancedCountdownTimer;
import com.cheng.tonglepai.tool.AlipayUtil;

import java.util.Map;

/**
 * Created by cheng on 2018/7/25.
 */

public class PayOnlineActivity extends TitleActivity {
    public static final String PAY_MONEY = "pay.money";
    public static final String PAY_ORDER_NO = "pay.order.no";
    public static final String PAY_ID = "pay.id";
    public static final String IS_FEIGHT = "is.freight";
    private TextView tvPayMoney, tvPayNo,tvPayName;
    private Button btnToPay;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    public static PayOnlineActivity payOnlineActivity;

    private long minute = 0;//分
    private long second = 0;//秒
    private long time = 0;//总时间
    private TextView tvLastTime;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayOnlineActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayOnlineActivity.this, PaySuccessActivity.class);
                        intent.putExtra(PaySuccessActivity.PAY_ORDER_NO, getIntent().getStringExtra(PAY_ORDER_NO));
                        intent.putExtra(PaySuccessActivity.PAY_ID, getIntent().getStringExtra(PAY_ID));
                        intent.putExtra(PaySuccessActivity.IS_FEIGHT, getIntent().getBooleanExtra(IS_FEIGHT, false));
                        startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayOnlineActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayOnlineActivity.this, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayOnlineActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private AlipayResult alipayResult;
    private MyCount count;
    private boolean isFreight;
    private LinearLayout llFeightContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_wechat_pay);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("支付");
        initView();
    }

    private void initView() {
        isFreight = getIntent().getBooleanExtra(IS_FEIGHT, false);
        tvLastTime = (TextView) findViewById(R.id.tv_last_time);
        tvPayMoney = (TextView) findViewById(R.id.tv_pay_money);
        btnToPay = (Button) findViewById(R.id.btn_sure_to_pay);
        tvPayNo = (TextView) findViewById(R.id.tv_order_no);
        tvPayName = (TextView) findViewById(R.id.tv_pay_name);
        llFeightContent = (LinearLayout) findViewById(R.id.ll_freight_content);
        btnToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipayGo();
            }
        });
        payOnlineActivity = this;

        if (isFreight) {
            tvPayMoney.setVisibility(View.GONE);
            tvPayNo.setVisibility(View.GONE);
            llFeightContent.setVisibility(View.VISIBLE);
            tvLastTime.setText("￥" + getIntent().getStringExtra(PAY_MONEY)+"元");
            tvPayName.setText("您需要支付");
        } else {
            tvPayMoney.setVisibility(View.VISIBLE);
            tvPayNo.setVisibility(View.VISIBLE);
            llFeightContent.setVisibility(View.GONE);
            tvPayName.setText("剩余支付时间");
            tvPayMoney.setText("￥" + getIntent().getStringExtra(PAY_MONEY));
            tvPayNo.setText("订单号：" + getIntent().getStringExtra(PAY_ORDER_NO));
            tvPayName.setText("您需要支付");

            minute = Long.parseLong("05");
            second = Long.parseLong("00");
            time = (minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
            count = new MyCount(time, 1000);//延时时间为1s
            tvLastTime.setText("00：00");
            count.start();
        }
    }

    private void alipayGo() {
        boolean canPay = AlipayUtil.canPay(this);
        if (!canPay) {
            Toast.makeText(PayOnlineActivity.this, "未安装相关支付宝组件", Toast.LENGTH_LONG).show();
            return;
        }
        if (getIntent().getBooleanExtra(IS_FEIGHT, false)) {
            MarkerAlipayRequest mRequest = new MarkerAlipayRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<AlipayResult>() {
                @Override
                public void onSuccess(AlipayResult data) {
                    alipayResult = data;
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PayOnlineActivity.this);
                            Map<String, String> result = alipay.payV2(alipayResult.getData(), true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }

                @Override
                public void onFailed(String msg, int code) {

                }
            });
            mRequest.requestMarkerAlipay(getIntent().getStringExtra(PAY_ID), getIntent().getStringExtra(PAY_ORDER_NO), getIntent().getStringExtra(PAY_MONEY));
        } else {
            AlipayRequest mRequest = new AlipayRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<AlipayResult>() {
                @Override
                public void onSuccess(AlipayResult data) {
                    alipayResult = data;
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PayOnlineActivity.this);
                            Map<String, String> result = alipay.payV2(alipayResult.getData(), true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }

                @Override
                public void onFailed(String msg, int code) {

                }
            });
            mRequest.requestAlipay(getIntent().getStringExtra(PAY_ID), getIntent().getStringExtra(PAY_ORDER_NO), getIntent().getStringExtra(PAY_MONEY));

        }
    }

    class MyCount extends AdvancedCountdownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {  //这两个参数在AdvancedCountdownTimer.java中均有(在“构造函数”中).
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btnToPay.setClickable(false);
            btnToPay.setBackgroundColor(Color.parseColor("#888888"));
        }

        //更新剩余时间
        String a = null;

        @Override
        public void onTick(long millisUntilFinished, int percent) {
            long myminute = ((millisUntilFinished / 1000)) / 60;
            long mysecond = millisUntilFinished / 1000 - myminute * 60;
            if (mysecond < 10) {
                a = "0" + mysecond;
                tvLastTime.setText("0" + myminute + ":" + a);
            } else {

                tvLastTime.setText("0" + myminute + ":" + mysecond);
            }
        }

    }
}

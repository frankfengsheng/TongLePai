package com.cheng.tonglepai.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.CanApplyData;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.net.FieldAlipayRequest;
import com.cheng.tonglepai.net.FieldCanApplyRequest;
import com.cheng.tonglepai.net.PricePayRequest;
import com.cheng.tonglepai.pay.AuthResult;
import com.cheng.tonglepai.pay.PayResult;
import com.cheng.tonglepai.tool.AlipayUtil;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.Map;

/**
 * Created by cheng on 2018/9/17.
 */

public class ToPostMoneyActivity extends TitleActivity {

    private String pricePay = "";
    private String zPrice = "";
    private TextView tvNeedPay, tvLastMoney;
    private CheckBox ivChooseOne, ivChooseTwo;
    private Button btnToPost;
    private EditText etToPost;
    private LoadingDialog loadingDialog;
    private AlipayResult alipayResult;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
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
                        Toast.makeText(ToPostMoneyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        etToPost.setText("");
                        etToPost.setHint("输入上缴金额");
                        if(loadingDialog!=null) loadingDialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    initData();
                                }
                            }).start();


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ToPostMoneyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ToPostMoneyActivity.this, "授权成功" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(ToPostMoneyActivity.this, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_post_money);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("上缴");
        initView();
        initData();
    }

    private void initData() {
        new MyIncomeModle(this).canapplayCallback(new MyIncomeModle.CanApplyCallback() {
            @Override
            public void bindSucess(CanApplyResult bindingBean) {

                zPrice=bindingBean.getData().getPrice();
                pricePay=bindingBean.getData().getPrice_pay();
                tvNeedPay.setText("￥" + pricePay);
                tvLastMoney.setText("￥" + zPrice);
            }
        });

    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);
        tvNeedPay = (TextView) findViewById(R.id.tv_need_post);
        tvLastMoney = (TextView) findViewById(R.id.tv_last_post);
        ivChooseOne = (CheckBox) findViewById(R.id.iv_choose_one);
        ivChooseTwo = (CheckBox) findViewById(R.id.iv_choose_two);

        ivChooseOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivChooseOne.setChecked(true);
                    ivChooseTwo.setChecked(false);
                } else {
                    ivChooseOne.setChecked(false);
                    ivChooseTwo.setChecked(true);
                }
            }
        });

        ivChooseTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivChooseOne.setChecked(false);
                    ivChooseTwo.setChecked(true);
                } else {
                    ivChooseOne.setChecked(true);
                    ivChooseTwo.setChecked(false);
                }
            }
        });

        btnToPost = (Button) findViewById(R.id.btn_post_money);
        btnToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivChooseOne.isChecked()) {
                    postMoney();
                } else if (ivChooseTwo.isChecked()) {

                    postAliMoney();
                }
            }
        });
        etToPost = (EditText) findViewById(R.id.et_post_money);
        etToPost.setKeyListener(new DigitsKeyListener(false, true));
        etToPost.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(".")) {

                    if (s.length() - 1 - s.toString().indexOf(".") >= 0) {
                        etToPost.setText(s.toString().subSequence(0, s.toString().indexOf(".")));
                        etToPost.setSelection(s.toString().subSequence(0, s.toString().indexOf(".")).length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    etToPost.setText("0");
                    etToPost.setSelection(1);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etToPost.setText(s.subSequence(0, 1));
                        etToPost.setSelection(1);
                        return;
                    }
                }
            }
        });
    }

    private void postAliMoney() {
        if (TextUtils.isEmpty(etToPost.getText().toString().trim())) {
            Toast.makeText(this, "请输入上缴金额", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean canPay = AlipayUtil.canPay(this);
        if (!canPay) {
            Toast.makeText(this, "未安装相关支付宝组件", Toast.LENGTH_LONG).show();
            return;
        }
        FieldAlipayRequest mRequest = new FieldAlipayRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<AlipayResult>() {
            @Override
            public void onSuccess(AlipayResult data) {
                alipayResult = data;
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(ToPostMoneyActivity.this);
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
        mRequest.requestFieldAlipay(etToPost.getText().toString().trim());
    }

    private void postMoney() {
        if (TextUtils.isEmpty(etToPost.getText().toString().trim())) {
            Toast.makeText(this, "请输入上缴金额", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        PricePayRequest mRequest = new PricePayRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                loadingDialog.dismiss();
                etToPost.setText("");
                etToPost.setHint("输入上缴金额");
                initData();
                Toast.makeText(ToPostMoneyActivity.this, "上缴成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(ToPostMoneyActivity.this, msg, Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();

            }
        });
        mRequest.requestApplyMoney(etToPost.getText().toString().trim());
    }
}

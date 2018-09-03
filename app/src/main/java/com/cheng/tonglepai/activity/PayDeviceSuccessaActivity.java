package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/7/30.
 */

public class PayDeviceSuccessaActivity extends TitleActivity {
    public static final String DEVICE_NO = "device.no";
    private Button btnToStatus;
    private TextView tvOrderNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_pay_device_success);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("订单支付");
        initView();
    }

    private void initView() {
        btnToStatus = (Button) findViewById(R.id.btn_order_status);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_no);

        tvOrderNo.setText("已经成功购买"+getIntent().getStringExtra(DEVICE_NO)+"台设备");

        btnToStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayDeviceSuccessaActivity.this,OrderStatusDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.OrderDetailAdapter;
import com.cheng.tonglepai.data.CheckokRecordsData;
import com.cheng.tonglepai.net.CheckRecordsRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

/**
 * Created by cheng on 2018/7/30.
 */

public class OrderStatusActivity extends TitleActivity {
    public static final String ORDER_NO = "order.no";
    private TextView tvShopName,tvShopAddress,tvShopNo;
    private LoadingDialog loadingDialog;
    private ListView lvDetail;
    private OrderDetailAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_order_detail);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("订单状态");

        initView();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        tvShopName = (TextView) findViewById(R.id.shop_name);
        tvShopAddress = (TextView) findViewById(R.id.tv_shop_address);
        tvShopNo = (TextView) findViewById(R.id.tv_shop_no);
        lvDetail = (ListView) findViewById(R.id.lv_order_status);
        mAdapter = new OrderDetailAdapter(this);
        lvDetail.setAdapter(mAdapter);
    }

    private void initData() {
        loadingDialog.show();
        CheckRecordsRequest mRequest = new CheckRecordsRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<CheckokRecordsData>() {
            @Override
            public void onSuccess(CheckokRecordsData data) {
                tvShopName.setText(data.getName());
                tvShopAddress.setText(data.getDetails());
                tvShopNo.setText(data.getDevice_list()+"台");
                mAdapter.setData(data.getRecords());
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                loadingDialog.dismiss();
                Toast.makeText(OrderStatusActivity.this,"没有数据",Toast.LENGTH_LONG);
            }
        });
        mRequest.requestCheckRecords(getIntent().getStringExtra(ORDER_NO));
    }
}

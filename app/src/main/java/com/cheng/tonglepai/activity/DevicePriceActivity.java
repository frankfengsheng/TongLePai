package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DevicePriceAdapter;
import com.cheng.tonglepai.data.DevicePriceData;
import com.cheng.tonglepai.net.FieldDevicePriceRequest;
import com.cheng.tonglepai.net.InvestorDevicePriceRequest;
import com.cheng.tonglepai.net.MarkerDevicePriceRequest;

/**
 * Created by cheng on 2018/9/17.
 */

public class DevicePriceActivity extends TitleActivity {

    public static final String ID = "store.id";
    public static final String YEAR = "store.year";
    public static final String MONTH = "store.month";
    private int userType;
    private String year, month, id;
    private TextView tvAddress,tvStoreName,tvDeviceNum,tvPriceData,tvPrice;
    private ListView lvDevice;
    private DevicePriceAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_device_price);
        MyApplication.getInstance().addActivity(this);
        year = getIntent().getStringExtra(YEAR);
        month = getIntent().getStringExtra(MONTH);
        id = getIntent().getStringExtra(ID);
        userType = HttpConfig.newInstance(this).getUserType();
        setMidTitle("场地设备");
        initView();
        initData();
    }

    private void initView() {
        tvAddress = (TextView) findViewById(R.id.device_price_address);
        tvStoreName = (TextView) findViewById(R.id.device_price_field_name);
        tvDeviceNum = (TextView) findViewById(R.id.device_price_field_num);
        tvPriceData = (TextView) findViewById(R.id.time_price_data);
        tvPrice = (TextView) findViewById(R.id.all_device_price);
        lvDevice = (ListView) findViewById(R.id.lv_device_price);
        mAdapter = new DevicePriceAdapter(this);
        lvDevice.setAdapter(mAdapter);
    }

    private void initData() {
        if (userType == 1) {
            InvestorDevicePriceRequest mRequest = new InvestorDevicePriceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DevicePriceData>() {
                @Override
                public void onSuccess(DevicePriceData data) {
                    tvAddress.setText(data.getInfo_data().getDetails());
                    tvStoreName.setText(data.getInfo_data().getStore_name());
                    tvPriceData.setText(year+"-"+month);
                    tvDeviceNum.setText("设备："+data.getInfo_data().getDevice_nums()+"台");
                    tvPrice.setText("累计收益：￥"+data.getInfo_data().getZ_price());
                    mAdapter.setData(data.getData(),data.getInfo_data().getDetails(),data.getInfo_data().getStore_name(),year,month,id);
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(DevicePriceActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
            mRequest.requestInvestorDevicePrice(id,month,year);
        } else if (userType == 2) {
            MarkerDevicePriceRequest mRequest = new MarkerDevicePriceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DevicePriceData>() {
                @Override
                public void onSuccess(DevicePriceData data) {
                    tvAddress.setText(data.getInfo_data().getDetails());
                    tvStoreName.setText(data.getInfo_data().getStore_name());
                    tvPriceData.setText(year+"-"+month);
                    tvDeviceNum.setText("设备："+data.getInfo_data().getDevice_nums()+"台");
                    tvPrice.setText("累计收益：￥"+data.getInfo_data().getZ_price());
                    mAdapter.setData(data.getData(),data.getInfo_data().getDetails(),data.getInfo_data().getStore_name(),year,month,id);
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(DevicePriceActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
            mRequest.requestMarkerDevicePrice(id,month,year);
        } else if (userType == 3) {
            FieldDevicePriceRequest mRequest = new FieldDevicePriceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DevicePriceData>() {
                @Override
                public void onSuccess(DevicePriceData data) {
                    tvAddress.setText(data.getInfo_data().getDetails());
                    tvStoreName.setText(data.getInfo_data().getStore_name());
                    tvPriceData.setText(year+"-"+month);
                    tvDeviceNum.setText("设备："+data.getInfo_data().getDevice_nums()+"台");
                    tvPrice.setText("累计收益：￥"+data.getInfo_data().getZ_price());
                    mAdapter.setData(data.getData(),data.getInfo_data().getDetails(),data.getInfo_data().getStore_name(),year,month,id);
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(DevicePriceActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
            mRequest.requestFieldDevicePrice(id,month,year);
        }
    }
}

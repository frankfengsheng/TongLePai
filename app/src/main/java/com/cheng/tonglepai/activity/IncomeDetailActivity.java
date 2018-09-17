package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.IncomeDetailAdapter;
import com.cheng.tonglepai.data.CheckBillData;
import com.cheng.tonglepai.data.DeviceBillData;
import com.cheng.tonglepai.data.InvestorAllIncomeData;
import com.cheng.tonglepai.net.FieldDeviceBillRequest;
import com.cheng.tonglepai.net.InvestorDeviceBillRequest;
import com.cheng.tonglepai.net.MarkerDeviceBillRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/8/2.
 */

public class IncomeDetailActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String INCOME_DATA = "income.data";
    public static final String INCOME_ADDRESS = "income.address";
    public static final String INCOME_STORE_NAME = "store.name";
    public static final String DEVICE_NAME = "device.name";
    public static final String DEVICE_ID = "device.id";
    public static final String ID = "store.id";
    public static final String YEAR = "store.year";
    public static final String MONTH = "store.month";
    private ListView lvIncome;
    private IncomeDetailAdapter mAdapter;
    private List<InvestorAllIncomeData.DataBean.TjBean> mData = new ArrayList<>();
    private List<InvestorAllIncomeData.DataBean.TjBean> mDataList = new ArrayList<>();
    private TextView tvName;
    private String year, month, id;
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private boolean isLoad;
    private boolean isFirst;
    private LoadingDialog loadingDialog;
    private boolean needLoad;
    private TextView tvAddress, tvStoreName, tvDeviceNum, tvPriceData, tvPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_income_detail);
        MyApplication.getInstance().addActivity(this);
        year = getIntent().getStringExtra(YEAR);
        month = getIntent().getStringExtra(MONTH);
        id = getIntent().getStringExtra(ID);
        setMidTitle("收益明细");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_income_detail);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        tvAddress = (TextView) findViewById(R.id.device_price_address);
        tvStoreName = (TextView) findViewById(R.id.device_price_field_name);
        tvDeviceNum = (TextView) findViewById(R.id.device_price_field_num);
        tvPriceData = (TextView) findViewById(R.id.time_price_data);
        tvPrice = (TextView) findViewById(R.id.all_device_price);
        tvAddress.setText(getIntent().getStringExtra(INCOME_ADDRESS));
        tvStoreName.setText(getIntent().getStringExtra(INCOME_STORE_NAME));
        tvDeviceNum.setText(getIntent().getStringExtra(DEVICE_NAME));
        tvPriceData.setText(getIntent().getStringExtra(YEAR) + "-" + getIntent().getStringExtra(MONTH));

        lvIncome = (ListView) findViewById(R.id.lv_income_detail);
        mAdapter = new IncomeDetailAdapter(this);
        lvIncome.setAdapter(mAdapter);
//        mData = (List<InvestorAllIncomeData.DataBean.TjBean>) getIntent().getSerializableExtra(INCOME_DATA);
//        for (int i = 0; i < mData.size(); i++) {
//            if(0.0 != Double.parseDouble(mData.get(i).getPrice())){
//               mDataList.add(mData.get(i));
//            }
//        }
    }

    private void initData() {
        loadingDialog.show();
        if (HttpConfig.newInstance(this).getUserType() == 1) {
            InvestorDeviceBillRequest mRequest = new InvestorDeviceBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DeviceBillData>() {
                @Override
                public void onSuccess(DeviceBillData data) {
                    tvPrice.setText(data.getPrice_data().getZ_price());
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data.getData());
                    if (data.getData().size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailed(String msg, int code) {
                    loadingDialog.dismiss();
                    Toast.makeText(IncomeDetailActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    if (isFirst)
                        mAdapter.clearData();
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                }
            });
            mRequest.requestFieldDevicePrice(id, month, year, page + "", getIntent().getStringExtra(DEVICE_ID));
        }

        if (HttpConfig.newInstance(this).getUserType() == 2) {
            MarkerDeviceBillRequest mRequest = new MarkerDeviceBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DeviceBillData>() {
                @Override
                public void onSuccess(DeviceBillData data) {
                    tvPrice.setText(data.getPrice_data().getZ_price());
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data.getData());
                    if (data.getData().size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailed(String msg, int code) {
                    loadingDialog.dismiss();
                    Toast.makeText(IncomeDetailActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    if (isFirst)
                        mAdapter.clearData();
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                }
            });
            mRequest.requestFieldDevicePrice(id, month, year, page + "", getIntent().getStringExtra(DEVICE_ID));
        }

        if (HttpConfig.newInstance(this).getUserType() == 3) {
            FieldDeviceBillRequest mRequest = new FieldDeviceBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<DeviceBillData>() {
                @Override
                public void onSuccess(DeviceBillData data) {
                    tvPrice.setText(data.getPrice_data().getZ_price());
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data.getData());
                    if (data.getData().size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                }

                @Override
                public void onFailed(String msg, int code) {
                    loadingDialog.dismiss();
                    Toast.makeText(IncomeDetailActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    if (isFirst)
                        mAdapter.clearData();
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                }
            });
            mRequest.requestFieldDevicePrice(id, month, year, page + "", getIntent().getStringExtra(DEVICE_ID));
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            initData();
            return true;
        } else {
            return false;
        }
    }
}

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
import com.cheng.tonglepai.data.InvestorAllIncomeData;
import com.cheng.tonglepai.net.FieldCheckBillRequest;
import com.cheng.tonglepai.net.InvestorCheckBillRequest;
import com.cheng.tonglepai.net.MarkerCheckBillRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/8/2.
 */

public class


IncomeDetailActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String INCOME_DATA = "income.data";
    public static final String INCOME_ADDRESS = "income.address";
    public static final String INCOME_STORE_NAME = "store.name";
    public static final String ID = "store.id";
    public static final String YEAR = "store.year";
    public static final String MONTH = "store.month";
    private TextView tvAddress;
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
        tvAddress = (TextView) findViewById(R.id.tv_income_address);
        tvName = (TextView) findViewById(R.id.tv_income_name);
        lvIncome = (ListView) findViewById(R.id.lv_income_detail);
        mAdapter = new IncomeDetailAdapter(this);
        lvIncome.setAdapter(mAdapter);
//        mData = (List<InvestorAllIncomeData.DataBean.TjBean>) getIntent().getSerializableExtra(INCOME_DATA);
//        for (int i = 0; i < mData.size(); i++) {
//            if(0.0 != Double.parseDouble(mData.get(i).getPrice())){
//               mDataList.add(mData.get(i));
//            }
//        }
        tvAddress.setText(getIntent().getStringExtra(INCOME_ADDRESS));
        tvName.setText(getIntent().getStringExtra(INCOME_STORE_NAME));
    }

    private void initData() {
        loadingDialog.show();
        if (HttpConfig.newInstance(this).getUserType() == 1) {
            InvestorCheckBillRequest mRequest = new InvestorCheckBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<CheckBillData>>() {
                @Override
                public void onSuccess(List<CheckBillData> data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data);
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data);
                    if (data.size() < 10)
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
            mRequest.requestInvestorCheckBill(id, page + "", month, year);
        }

        if (HttpConfig.newInstance(this).getUserType() == 2) {
            MarkerCheckBillRequest mRequest = new MarkerCheckBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<CheckBillData>>() {
                @Override
                public void onSuccess(List<CheckBillData> data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data);
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data);
                    if (data.size() < 10)
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
            mRequest.requestMarkerCheckBill(id, page + "", month, year);
        }

        if (HttpConfig.newInstance(this).getUserType() == 3) {
            FieldCheckBillRequest mRequest = new FieldCheckBillRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<CheckBillData>>() {
                @Override
                public void onSuccess(List<CheckBillData> data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data);
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.setData(data);
                    if (data.size() < 10)
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
            mRequest.requestFieldCheckBill(id, page + "", month, year);
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

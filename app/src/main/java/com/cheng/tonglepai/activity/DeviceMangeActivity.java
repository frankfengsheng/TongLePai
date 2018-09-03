package com.cheng.tonglepai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ExceptionDeviceAdapter;
import com.cheng.tonglepai.data.ExceptionDeviceData;
import com.cheng.tonglepai.net.AllDeviceRequest;
import com.cheng.tonglepai.net.AllFieldDeviceRequest;
import com.cheng.tonglepai.net.ExceptionDeviceRequest;
import com.cheng.tonglepai.net.FieldExceptionDeviceRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.SearchDevicePopwindow;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 合伙人的设备管理
 */

public class DeviceMangeActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, SearchDevicePopwindow.SearchDevicePopListener {
    private RadioButton rbAll, rbIncome, rbSearch;
    private BGARefreshLayout mRefreshLayout;
    private RadioButton hasPutinDevice, exceptionDevice;
    private LinearLayout llRbType;
    private ExceptionDeviceAdapter mAdapter;
    private ListView rvDeviceManage;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private int page = 1;
    private String isByIncome = "0";
    private SearchDevicePopwindow popwindow;
    private boolean isRefreshException;
    private String searchContent = "";
    private int type;
    private LoadingDialog loadingDialog;
    private boolean showDialog = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_partner_equipments);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("设备管理");
        initView();
        initRefreshLayout();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        type = getIntent().getIntExtra("type", 0);
        llRbType = (LinearLayout) findViewById(R.id.ll_rb_type);
        rbAll = (RadioButton) findViewById(R.id.rb_all);
        rbIncome = (RadioButton) findViewById(R.id.rb_income);
        rbSearch = (RadioButton) findViewById(R.id.rb_search);
        hasPutinDevice = (RadioButton) findViewById(R.id.has_putin_device);
        exceptionDevice = (RadioButton) findViewById(R.id.excption_device);
        popwindow = new SearchDevicePopwindow(this, getWindow(), false);
        popwindow.setChooseProductPopListener(this);
        rbAll.setOnClickListener(this);
        rbIncome.setOnClickListener(this);
        rbSearch.setOnClickListener(this);
        hasPutinDevice.setOnClickListener(this);
        exceptionDevice.setOnClickListener(this);

        rvDeviceManage = (ListView) findViewById(R.id.rv_device_manage);
        mAdapter = new ExceptionDeviceAdapter(this);
        rvDeviceManage.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_device_manage);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initData() {
        if (showDialog)
            loadingDialog.show();


        if (type == 2) {
            AllDeviceRequest mRequest = new AllDeviceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ExceptionDeviceData>>() {
                @Override
                public void onSuccess(List<ExceptionDeviceData> data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        mAdapter.setLoadData(data);
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
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    Toast.makeText(DeviceMangeActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                    if (isFirst)
                        mAdapter.clearData();
                }
            });

            mRequest.requestAllDevice(HttpConfig.newInstance(this).getUserid(), page + "", isByIncome, searchContent);
        } else if (type == 3) {
            AllFieldDeviceRequest mRequest = new AllFieldDeviceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ExceptionDeviceData>>() {
                @Override
                public void onSuccess(List<ExceptionDeviceData> data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.size() < 10)
                            needLoad = false;
                        else
                            needLoad = true;
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        mAdapter.setLoadData(data);
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
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    Toast.makeText(DeviceMangeActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                    if (isFirst)
                        mAdapter.clearData();
                }
            });

            mRequest.requestAllFieldDevice(HttpConfig.newInstance(this).getUserid(), page + "", isByIncome, searchContent);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_all:
                searchContent = "";
                initBtn();
                isByIncome = "0";
                rbAll.setTextColor(Color.parseColor("#ffffff"));
                rbAll.setBackgroundColor(Color.parseColor("#8CC8FE"));
                page = 1;
                showDialog = true;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                initData();
                break;
            case R.id.rb_income:
                searchContent = "";
                initBtn();
                isByIncome = "1";
                rbIncome.setTextColor(Color.parseColor("#ffffff"));
                rbIncome.setBackgroundColor(Color.parseColor("#8CC8FE"));
                page = 1;
                showDialog = true;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                initData();
                break;
            case R.id.rb_search:
                initBtn();
                popwindow.showAsDropDown(llRbType);
                rbSearch.setTextColor(Color.parseColor("#ffffff"));
                rbSearch.setBackgroundColor(Color.parseColor("#8CC8FE"));
                break;
            case R.id.has_putin_device:
                searchContent = "";
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                isRefreshException = false;
                llRbType.setVisibility(View.VISIBLE);
                exceptionDevice.setTextColor(Color.parseColor("#45a7fe"));
                exceptionDevice.setBackgroundColor(Color.parseColor("#ffffff"));
                hasPutinDevice.setTextColor(Color.parseColor("#ffffff"));
                hasPutinDevice.setBackgroundColor(Color.parseColor("#45a7fe"));
                showDialog = true;
                initData();
                break;

            case R.id.excption_device:
                llRbType.setVisibility(View.GONE);
                isRefreshException = true;
                showDialog = true;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                page = 1;
                hasPutinDevice.setTextColor(Color.parseColor("#45a7fe"));
                hasPutinDevice.setBackgroundColor(Color.parseColor("#ffffff"));
                exceptionDevice.setTextColor(Color.parseColor("#ffffff"));
                exceptionDevice.setBackgroundColor(Color.parseColor("#45a7fe"));
                initExceptionData();
                break;
            default:
                break;
        }
    }

    private void initExceptionData() {
        if (showDialog)
            loadingDialog.show();

        if (type == 2) {
            ExceptionDeviceRequest mRequest = new ExceptionDeviceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ExceptionDeviceData>>() {
                @Override
                public void onSuccess(List<ExceptionDeviceData> data) {
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
                    Toast.makeText(DeviceMangeActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    mAdapter.clearData();
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                }
            });

            mRequest.requestExceptionDevice(HttpConfig.newInstance(DeviceMangeActivity.this).getUserid(), page + "");
        } else if (type == 3) {
            FieldExceptionDeviceRequest mRequest = new FieldExceptionDeviceRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ExceptionDeviceData>>() {
                @Override
                public void onSuccess(List<ExceptionDeviceData> data) {
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
                    Toast.makeText(DeviceMangeActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    mAdapter.clearData();
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                }
            });

            mRequest.requestFieldExceptionDevice(HttpConfig.newInstance(DeviceMangeActivity.this).getUserid(), page + "");
        }
    }

    private void initBtn() {
        rbAll.setChecked(false);
        rbIncome.setChecked(false);
        rbSearch.setChecked(false);
        rbAll.setTextColor(Color.parseColor("#686868"));
        rbIncome.setTextColor(Color.parseColor("#686868"));
        rbSearch.setTextColor(Color.parseColor("#686868"));
        rbAll.setBackgroundColor(Color.parseColor("#ffffff"));
        rbIncome.setBackgroundColor(Color.parseColor("#ffffff"));
        rbSearch.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showDialog = false;
        isLoad = false;
        isFirst = true;
        page = 1;
        if (isRefreshException) {
            initExceptionData();
        } else {
            initData();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        showDialog = true;
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            if (isRefreshException) {
                initExceptionData();
            } else {
                initData();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void toSearch(String ids) {
        searchContent = ids;
        page = 1;
        isByIncome = "0";
        showDialog = true;
        isFirst = true;
        needLoad = false;
        isLoad = false;
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isByIncome = "0";
        showDialog = true;
        isFirst = true;
        needLoad = false;
        isLoad = false;
        page = 1;
        initData();
    }
}

package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.BindDeviceAdapter;
import com.cheng.tonglepai.data.BindDeviceData;
import com.cheng.tonglepai.net.BindDeviceRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/8/14.
 */

public class BindDeviceActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private BindDeviceAdapter mAdapter;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_bind_device);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("设备列表");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_bind_device);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvDetail = (ListView) findViewById(R.id.lv_bind_device);
        mAdapter = new BindDeviceAdapter(this);
        lvDetail.setAdapter(mAdapter);

    }

    private void initData() {
        loadingDialog.show();
        BindDeviceRequest mRequest = new BindDeviceRequest(this);

        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<BindDeviceData>>() {
            @Override
            public void onSuccess(List<BindDeviceData> data) {
                int a = 1;
                if (data.size() != 0) {
                    if (data.size() == 1) {
                        needLoad = false;
                        mAdapter.setData(data);
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                    } else {
                        for (int i = 1; i < data.size(); i++) {
                            if (!data.get(i).getInfo_id().equals(data.get(i - 1).getInfo_id())) {
                                a++;
                            }
                        }

                        if (isLoad) {
                            isLoad = false;
                            if (a < 5)
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
                        if (a < 5)
                            needLoad = false;
                        else
                            needLoad = true;
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(BindDeviceActivity.this, msg, Toast.LENGTH_LONG).show();
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
            }
        });

        mRequest.requestBindDevice(page + "");
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}

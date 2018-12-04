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

public class DeviceIncomeDetail extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
        setMidTitle("我的设备");
        initRefreshLayout();
        initView();

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

    private void initData(){

    }

}

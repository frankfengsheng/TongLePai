package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.OrderStatusAdapter;
import com.cheng.tonglepai.data.CheckokInfoData;
import com.cheng.tonglepai.net.OrderStatusListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/6/7.
 */

public class OrderStatusDetailActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView lvOrderList;
    private OrderStatusAdapter mAdapter;
    private int page = 1;
    private boolean needLoad,isLoad;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_order_status);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("订单状态");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initData() {
        loadingDialog.show();
        OrderStatusListRequest mRequest = new OrderStatusListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<CheckokInfoData>>() {
            @Override
            public void onSuccess(List<CheckokInfoData> data) {
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
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                Toast.makeText(OrderStatusDetailActivity.this,"没有数据",Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
            }
        });
        mRequest.requestOrderStatusList(page+"");
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvOrderList = (ListView) findViewById(R.id.lv_order_list);
        mAdapter = new OrderStatusAdapter(this);
        lvOrderList.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_order_status);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        needLoad = true;
        isLoad = false;
        initData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (needLoad) {
            page++;
            isLoad = true;
            initData();
            return true;
        } else {
            mRefreshLayout.endRefreshing();
            mRefreshLayout.endLoadingMore();
            return false;
        }
    }
}

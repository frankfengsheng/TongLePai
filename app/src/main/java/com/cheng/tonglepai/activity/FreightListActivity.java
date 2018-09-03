package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.FreightListDetailAdapter;
import com.cheng.tonglepai.data.PartnerdesData;
import com.cheng.tonglepai.net.PartnerdesRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/7/12.
 */

public class FreightListActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private FreightListDetailAdapter mAdapter;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private LoadingDialog loadingDialog;
    private LinearLayout llFreghtHead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_apply_detail);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("运费明细");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        llFreghtHead = (LinearLayout) findViewById(R.id.ll_head_freight);
        llFreghtHead.setVisibility(View.GONE);
        lvDetail = (ListView) findViewById(R.id.lv_apply_detail);
        mAdapter = new FreightListDetailAdapter(this);
        lvDetail.setAdapter(mAdapter);
    }

    private void initData() {
        loadingDialog.show();
        PartnerdesRequest mRequest = new PartnerdesRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<PartnerdesData>>() {
            @Override
            public void onSuccess(List<PartnerdesData> data) {
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
                Toast.makeText(FreightListActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                if (isFirst)
                    mAdapter.clearData();
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }
        });

        mRequest.requestPartnerdes(HttpConfig.newInstance(this).getUserid(), page + "");

    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_apply_detail);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));


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

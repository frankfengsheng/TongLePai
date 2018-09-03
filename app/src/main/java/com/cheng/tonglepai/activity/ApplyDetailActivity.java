package com.cheng.tonglepai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ApplyDetailAdapter;
import com.cheng.tonglepai.data.ApplyDetailUseData;
import com.cheng.tonglepai.net.AllApplyDetailRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/6/8.
 */

public class ApplyDetailActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private ApplyDetailAdapter mAdapter;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private int userType;
    private LoadingDialog loadingDialog;
    private String where = "1";
    private RadioButton rbAll, rbIng, rbFinish, rbFair;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_apply_detail);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("提款明细");
        userType = getIntent().getIntExtra("type", 0);
        initRefreshLayout();
        initView();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvDetail = (ListView) findViewById(R.id.lv_apply_detail);
        mAdapter = new ApplyDetailAdapter(this);
        lvDetail.setAdapter(mAdapter);

        rbAll = (RadioButton) findViewById(R.id.rb_all);
        rbIng = (RadioButton) findViewById(R.id.rb_ing);
        rbFair = (RadioButton) findViewById(R.id.rb_fair);
        rbFinish = (RadioButton) findViewById(R.id.rb_finish);

        rbAll.setOnClickListener(this);
        rbIng.setOnClickListener(this);
        rbFair.setOnClickListener(this);
        rbFinish.setOnClickListener(this);
    }

    private void initData() {
        loadingDialog.show();
        AllApplyDetailRequest mRequest = new AllApplyDetailRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ApplyDetailUseData>>() {
            @Override
            public void onSuccess(List<ApplyDetailUseData> data) {
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
                Toast.makeText(ApplyDetailActivity.this, "没有更多数据", Toast.LENGTH_LONG);
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }
        });
        mRequest.requestInvestorApplyDetail(page+"", where);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_all:
                initBtn();
                rbAll.setChecked(true);
                rbAll.setTextColor(Color.parseColor("#ffffff"));
                rbAll.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                where = "1";
                mAdapter.clearData();
                initData();
                break;
            case R.id.rb_ing:
                initBtn();
                rbIng.setChecked(true);
                rbIng.setTextColor(Color.parseColor("#ffffff"));
                rbIng.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                where = "2";
                mAdapter.clearData();
                initData();
                break;
            case R.id.rb_fair:
                initBtn();
                rbFair.setChecked(true);
                rbFair.setTextColor(Color.parseColor("#ffffff"));
                rbFair.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                where = "3";
                mAdapter.clearData();
                initData();
                break;
            case R.id.rb_finish:
                initBtn();
                rbFinish.setChecked(true);
                rbFinish.setTextColor(Color.parseColor("#ffffff"));
                rbFinish.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                where = "4";
                mAdapter.clearData();
                initData();
                break;
            default:
                break;
        }
    }

    private void initBtn() {
        rbAll.setChecked(false);
        rbFair.setChecked(false);
        rbFinish.setChecked(false);
        rbIng.setChecked(false);
        rbFair.setTextColor(Color.parseColor("#686868"));
        rbAll.setTextColor(Color.parseColor("#686868"));
        rbFinish.setTextColor(Color.parseColor("#686868"));
        rbIng.setTextColor(Color.parseColor("#686868"));
        rbAll.setBackgroundColor(Color.parseColor("#ffffff"));
        rbIng.setBackgroundColor(Color.parseColor("#ffffff"));
        rbFinish.setBackgroundColor(Color.parseColor("#ffffff"));
        rbFair.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}

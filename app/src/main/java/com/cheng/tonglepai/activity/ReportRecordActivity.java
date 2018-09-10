package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ReportRecordAdapter;
import com.cheng.tonglepai.data.ReportRecordData;
import com.cheng.tonglepai.net.ReportRecordRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/9/9.
 */

public class ReportRecordActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private LoadingDialog loadingDialog;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private ReportRecordAdapter mAdapter;
    private Button btnToAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_report_record);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("报备记录");
        initRefreshLayout();
        initView();
        initData();
    }


    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvDetail = (ListView) findViewById(R.id.lv_report_record);
        mAdapter = new ReportRecordAdapter(this);
        lvDetail.setAdapter(mAdapter);

        btnToAdd = (Button) findViewById(R.id.btn_to_add);
        btnToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportRecordActivity.this,ReportDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        loadingDialog.show();
        ReportRecordRequest mRequest = new ReportRecordRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<ReportRecordData>>() {
            @Override
            public void onSuccess(List<ReportRecordData> data) {
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
                Toast.makeText(ReportRecordActivity.this, "没有更多数据", Toast.LENGTH_LONG);
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }
        });
        mRequest.requestReportRecord(page+"");
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_report_record);
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
    protected void onResume() {
        super.onResume();
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }
}

package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cheng.retrofit20.bean.DeviceIncomeDetailBean;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceIncomeListAdapter;
import com.cheng.tonglepai.model.IncomeModle;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/8/14.
 */

public class PartnerDeviceIncomeListActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private DeviceIncomeListAdapter  mAdapter;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad=false;
    private LoadingDialog loadingDialog;
    private String deviceId;
    private String startTime;
    private String endTime;
    private List<DeviceIncomeDetailBean.DataBean> mList=new ArrayList<>();
    private TextView tv_empty;

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
        loadingDialog.setMessage("加载数据中...");
        deviceId=getIntent().getStringExtra(DeviceIncomeDetailActivity.DEVICEID);
        startTime=getIntent().getStringExtra(DeviceIncomeDetailActivity.STARTTIME);
        endTime=getIntent().getStringExtra(DeviceIncomeDetailActivity.EndTime);

        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvDetail = (ListView) findViewById(R.id.lv_bind_device);
        mAdapter = new DeviceIncomeListAdapter(this,mList);

        tv_empty= (TextView) findViewById(R.id.tv_empty);
        tv_empty.setText("设备暂无流水");
        lvDetail.setAdapter(mAdapter);
        initData();
    }
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        mList.clear();
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



    private void initData(){
        loadingDialog.show();
        new IncomeModle(this).SiteGetDeviceIncomeDetails(deviceId, startTime, endTime, page+"", new IncomeModle.DeviceIncomeDetailSuccessCallback() {
            @Override
            public void Sucess(DeviceIncomeDetailBean bean) {
                if(isLoad){
                    mRefreshLayout.endLoadingMore();
                }else {
                    mRefreshLayout.endRefreshing();
                }
                loadingDialog.dismiss();
                if(bean!=null&&bean.getData()!=null&&bean.getData().size()>0){
                    mList.addAll(bean.getData());
                    mAdapter.notifyDataSetChanged();
                    if(bean.getData().size()>=20){
                        needLoad=true;
                    }else {
                        needLoad=false;
                    }
                }
                if(mList.size()<=0){
                    mRefreshLayout.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.VISIBLE);
                }else {
                    mRefreshLayout.setVisibility(View.VISIBLE);
                    tv_empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void Faile() {
                if(isLoad){
                    mRefreshLayout.endLoadingMore();
                }else {
                    mRefreshLayout.endRefreshing();
                }
                loadingDialog.dismiss();
            }
        });
    }
}
package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.InvestorDeviceAdapter;
import com.cheng.tonglepai.adapter.SiteDeviceListAdapter;
import com.cheng.tonglepai.data.InvestorDeviceListData;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.net.FieldMoveInRequest;
import com.cheng.tonglepai.net.InvestorDeviceListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.MyReturnDialog;
import com.cheng.tonglepai.tool.MyToast;
import com.cheng.tonglepai.tool.SearchDevicePopwindow;
import com.cheng.tonglepai.tool.TimeUtil;
import com.cheng.tonglepai.tool.ToastUtil;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 合伙人的设备管理
 */

public class SiteDeviceListActivity extends TitleActivity  {
    private LoadingDialog loadingDialog;
    private RecyclerView recyclerView;
    private TextView tv_shopName,tv_DeviceCount,tv_yesterDayIncome,tv_MonthIncome,tv_startTime,tv_weizhi;
    InvestorDeviceListData investorDeviceListData;
    private TextView tv_move;
    private MyReturnDialog progressDialog;
    private int nums=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_site_device_list);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地设备");
        initView();
        initRefreshLayout();
        initData();
    }

    private void initView() {

        investorDeviceListData= (InvestorDeviceListData) getIntent().getSerializableExtra("bean");
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);
        recyclerView= (RecyclerView) findViewById(R.id.ry_devices_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
       tv_shopName = (TextView) findViewById(R.id.tv_shop_name);
        tv_DeviceCount = (TextView) findViewById(R.id.tv_device_count);
        tv_startTime = (TextView) findViewById(R.id.tv_start_time);
        tv_yesterDayIncome = (TextView)findViewById(R.id.tv_yesterday_income);
        tv_MonthIncome = (TextView) findViewById(R.id.tv_month_income);
        tv_weizhi = (TextView) findViewById(R.id.tv_weizhi);
        tv_move= (TextView) findViewById(R.id.tv_device_move);
        tv_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nums==0){
                    ToastUtil.showToast(SiteDeviceListActivity.this,"该场地下暂无设备需要迁移");
                }else {
                    progressDialog = MyToast.showReturnDialog(SiteDeviceListActivity.this,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    FieldMoveInRequest mRequest = new FieldMoveInRequest(SiteDeviceListActivity.this);
                                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                                        @Override
                                        public void onSuccess(BaseHttpResult data) {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(SiteDeviceListActivity.this, PublicResultActivity.class);
                                            intent.putExtra(PublicResultActivity.TYPE, 1);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailed(String msg, int code) {
                                            progressDialog.dismiss();
                                            Toast.makeText(SiteDeviceListActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    mRequest.requestFieldMoveIn(investorDeviceListData.getId(), nums + "");
                                }
                            });
                }
            }
        });
    }

    private void initRefreshLayout() {
        if(investorDeviceListData!=null){
            if(!TextUtils.isEmpty(investorDeviceListData.getName()))tv_shopName.setText(investorDeviceListData.getName());
            if(!TextUtils.isEmpty(investorDeviceListData.getDevice_list()))tv_DeviceCount.setText("设备："+investorDeviceListData.getDevice_list());
            if(!TextUtils.isEmpty(investorDeviceListData.getYesterday()))tv_yesterDayIncome.setText("昨日收益：￥"+investorDeviceListData.getYesterday());
            if(!TextUtils.isEmpty(investorDeviceListData.getThismonth()))tv_MonthIncome.setText("本月累计：￥"+investorDeviceListData.getThismonth());
            if(!TextUtils.isEmpty(investorDeviceListData.getDetails()))tv_weizhi.setText(investorDeviceListData.getDetails());
            if(!TextUtils.isEmpty(investorDeviceListData.getTime()))tv_startTime.setText("运行时间："+ TimeUtil.stampToDate(investorDeviceListData.getTime()));
            if(!TextUtils.isEmpty(investorDeviceListData.getId()))initData();
        }
    }

    private void initData() {
        loadingDialog.show();
        new MyIncomeModle(getApplicationContext()).GetDeviceList(investorDeviceListData.getId(), new MyIncomeModle.GetDeviceListCallback() {
            @Override
            public void Sucess(final SiteEquimentListBean bean) {
                loadingDialog.cancel();
                if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                    nums=bean.getData().size();
                    SiteDeviceListAdapter adapter = new SiteDeviceListAdapter(SiteDeviceListActivity.this, bean.getData());
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickLis(new SiteDeviceListAdapter.OnItemclickListenner() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent=new Intent(SiteDeviceListActivity.this,DeviceDetailActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("siteBean",investorDeviceListData);
                            bundle.putSerializable("deviceBean",bean.getData().get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    });
                }else {
                    nums=0;
                }
            }
            @Override
            public void Faile() {

            }
        });
    }



}

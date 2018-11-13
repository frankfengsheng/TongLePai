package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.InvestorDeviceAdapter;
import com.cheng.tonglepai.data.InvestorDeviceListData;
import com.cheng.tonglepai.net.InvestorDeviceListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.SearchDevicePopwindow;
import com.cheng.tonglepai.tool.ToastUtil;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 合伙人的设备管理
 */

public class InvestorDeviceMangeActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, SearchDevicePopwindow.SearchDevicePopListener {
    private LinearLayout rbAll, rbIncome;
    private BGARefreshLayout mRefreshLayout;
    private RadioButton hasPutinDevice, exceptionDevice;
    private LinearLayout llRbType;
    private InvestorDeviceAdapter mAdapter;
    private ListView rvDeviceManage;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private int page = 1;
    private String isByIncome = "0";
    private SearchDevicePopwindow popwindow;
    private String searchContent = "";
    private int type;
    private LinearLayout llChooseType;
    private LoadingDialog loadingDialog;
    private EditText edt_serach;
    private ImageView iv_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_partner_equipments);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地列表");
        initView();
        initRefreshLayout();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);
        type = getIntent().getIntExtra("type", type);
        llChooseType = (LinearLayout) findViewById(R.id.ll_choose_device);
        llRbType = (LinearLayout) findViewById(R.id.ll_rb_type);
        rbAll = (LinearLayout) findViewById(R.id.rb_all);
        rbIncome = (LinearLayout) findViewById(R.id.rb_income);
        hasPutinDevice = (RadioButton) findViewById(R.id.has_putin_device);
        exceptionDevice = (RadioButton) findViewById(R.id.excption_device);
        edt_serach= (EditText) findViewById(R.id.edt_serach_equiment);
        edt_serach.setHint("请输入地址");
        edt_serach.setOnKeyListener(onKeyListener);
        iv_search= (ImageView) findViewById(R.id.iv_search_equipment);

        popwindow = new SearchDevicePopwindow(this, getWindow(), true);
        popwindow.setChooseProductPopListener(this);
        rbAll.setOnClickListener(this);
        rbIncome.setOnClickListener(this);

        hasPutinDevice.setOnClickListener(this);
        exceptionDevice.setOnClickListener(this);
        iv_search.setOnClickListener(this);

        rvDeviceManage = (ListView) findViewById(R.id.rv_device_manage);
        mAdapter = new InvestorDeviceAdapter(this);
        rvDeviceManage.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_device_manage);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initData() {
        loadingDialog.show();
        InvestorDeviceListRequest mRequest = new InvestorDeviceListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<InvestorDeviceListData>>() {
            @Override
            public void onSuccess(List<InvestorDeviceListData> data) {
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
                if (isFirst)
                    mAdapter.clearData();
                Toast.makeText(InvestorDeviceMangeActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();

            }
        });

        mRequest.requestInvestorDeviceList(page + "", isByIncome, searchContent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_all:
                searchContent = "";
               // initBtn();
                isByIncome = "0";
                /*rbAll.setTextColor(Color.parseColor("#ffffff"));
                rbAll.setBackgroundColor(Color.parseColor("#8CC8FE"));*/
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                initData();
                break;
            case R.id.rb_income:
                searchContent = "";
                //initBtn();
                isByIncome = "1";
              /*  rbIncome.setTextColor(Color.parseColor("#ffffff"));
                rbIncome.setBackgroundColor(Color.parseColor("#8CC8FE"));*/
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                initData();
                break;
            case R.id.rb_search:
                //initBtn();
                popwindow.showAsDropDown(llRbType);
               /* rbSearch.setTextColor(Color.parseColor("#ffffff"));
                rbSearch.setBackgroundColor(Color.parseColor("#8CC8FE"));*/
                break;
            case R.id.iv_search_equipment:
                String content=edt_serach.getText().toString();
                if(!TextUtils.isEmpty(content)){
                    toSearch(content);
                }else {
                    ToastUtil.showToast(InvestorDeviceMangeActivity.this,"搜索条件不能为空");
                }

                break;
            default:
                break;
        }
    }
    View.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                String serch_content=edt_serach.getText().toString();
                if(TextUtils.isEmpty(serch_content)){
                    ToastUtil.showToast(InvestorDeviceMangeActivity.this,"通讯编号不能为空");
                }else {
                    toSearch(serch_content);
                }
            }
            return false;
        }
    };

   /* private void initBtn() {
        rbAll.setChecked(false);
        rbIncome.setChecked(false);
        rbSearch.setChecked(false);
        rbAll.setTextColor(Color.parseColor("#686868"));
        rbIncome.setTextColor(Color.parseColor("#686868"));
        rbSearch.setTextColor(Color.parseColor("#686868"));
        rbAll.setBackgroundColor(Color.parseColor("#ffffff"));
        rbIncome.setBackgroundColor(Color.parseColor("#ffffff"));
        rbSearch.setBackgroundColor(Color.parseColor("#ffffff"));
    }*/

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
    public void toSearch(String ids) {
        searchContent = ids;
        isFirst = true;
        needLoad = false;
        isLoad = false;
        page = 1;
        initData();
    }
}

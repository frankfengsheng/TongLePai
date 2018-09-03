package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.HasPostFieldAdapter;
import com.cheng.tonglepai.data.HasPostFieldData;
import com.cheng.tonglepai.net.HasPostFieldRequest;
import com.cheng.tonglepai.net.NeedMoneyRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/6/28.
 */

public class HasPostFieldFieldActivity extends TitleActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    //合伙人已报备场地
    private RadioButton rbAll, rbYunMoney, rbExamineDetail;
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private HasPostFieldAdapter mAdapter;
    private ListView lvHasPostField;
    private String str = "all";
    private LoadingDialog loadingDialog;
    public static final String ISFREIGHT = "is.freight";
    private boolean isFreight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_has_post_field);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("已报备场地");
        isFreight = getIntent().getBooleanExtra(ISFREIGHT, false);
        initRefreshLayout();
        initView();
        initData();
        initRightView();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_field_layout);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        mAdapter = new HasPostFieldAdapter(this);
        lvHasPostField = (ListView) findViewById(R.id.lv_has_post_field);
        lvHasPostField.setAdapter(mAdapter);
        rbAll = (RadioButton) findViewById(R.id.rb_filed_all);
        rbYunMoney = (RadioButton) findViewById(R.id.rb_filed_yun);
        rbExamineDetail = (RadioButton) findViewById(R.id.rb_field_result);

        rbAll.setOnClickListener(this);
        rbYunMoney.setOnClickListener(this);
        rbExamineDetail.setOnClickListener(this);

        if (isFreight) {
            initBtn();
            rbYunMoney.setChecked(true);
            rbYunMoney.setTextColor(Color.parseColor("#ffffff"));
            rbYunMoney.setBackgroundColor(Color.parseColor("#45a7fe"));
            page = 1;
            isFirst = true;
            needLoad = false;
            isLoad = false;
            str = "yun";
        }
    }

    private void initData() {
        if (TextUtils.isEmpty(str)) {
            return;
        } else {

            if ("all".equals(str)) {
                loadingDialog.show();
                HasPostFieldRequest mRequest = new HasPostFieldRequest(this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<List<HasPostFieldData>>() {
                    @Override
                    public void onSuccess(List<HasPostFieldData> data) {
                        Log.i("------", needLoad + "needLoad");
                        Log.i("------", isLoad + "isLoad");
                        if (isLoad) {
                            isLoad = false;
                            if (data.size() < 10)
                                needLoad = false;
                            else
                                needLoad = true;
                            mAdapter.setLoadData(data, false);
                            mRefreshLayout.endRefreshing();
                            mRefreshLayout.endLoadingMore();
                            loadingDialog.dismiss();
                            return;
                        }
                        mAdapter.setData(data, false);
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
                        Toast.makeText(HasPostFieldFieldActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                        if (isFirst)
                            mAdapter.clearData();
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                    }
                });

                mRequest.requestField(HttpConfig.newInstance(HasPostFieldFieldActivity.this).getUserid(), page + "");
            } else if ("yun".equals(str)) {
                loadingDialog.show();
                NeedMoneyRequest mRequest = new NeedMoneyRequest(this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<List<HasPostFieldData>>() {
                    @Override
                    public void onSuccess(List<HasPostFieldData> data) {
                        if (isLoad) {
                            isLoad = false;
                            if (data.size() < 10)
                                needLoad = false;
                            else
                                needLoad = true;
                            mAdapter.setLoadData(data, false);
                            mRefreshLayout.endRefreshing();
                            mRefreshLayout.endLoadingMore();
                            loadingDialog.dismiss();
                            return;
                        }
                        mAdapter.setData(data, false);
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
                        Toast.makeText(HasPostFieldFieldActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                        if (isFirst)
                            mAdapter.clearData();
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                    }
                });

                mRequest.requestField(HttpConfig.newInstance(HasPostFieldFieldActivity.this).getUserid(), page + "");
            } else if ("result".equals(str)) {
                loadingDialog.show();
                HasPostFieldRequest mRequest = new HasPostFieldRequest(this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<List<HasPostFieldData>>() {
                    @Override
                    public void onSuccess(List<HasPostFieldData> data) {
                        if (isLoad) {
                            isLoad = false;
                            if (data.size() < 10)
                                needLoad = false;
                            else
                                needLoad = true;
                            mAdapter.setLoadData(data, true);
                            mRefreshLayout.endRefreshing();
                            mRefreshLayout.endLoadingMore();
                            loadingDialog.dismiss();
                            return;
                        }
                        mAdapter.setData(data, true);
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
                        Toast.makeText(HasPostFieldFieldActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                        if (isFirst)
                            mAdapter.clearData();
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                    }
                });

                mRequest.requestField(HttpConfig.newInstance(HasPostFieldFieldActivity.this).getUserid(), page + "");
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_filed_all:
                initBtn();
                rbAll.setChecked(true);
                rbAll.setTextColor(Color.parseColor("#ffffff"));
                rbAll.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                str = "all";
                initData();
                break;
            case R.id.rb_filed_yun:
                initBtn();
                rbYunMoney.setChecked(true);
                rbYunMoney.setTextColor(Color.parseColor("#ffffff"));
                rbYunMoney.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                str = "yun";
                initData();
                break;
            case R.id.rb_field_result:
                initBtn();
                rbExamineDetail.setChecked(true);
                rbExamineDetail.setTextColor(Color.parseColor("#ffffff"));
                rbExamineDetail.setBackgroundColor(Color.parseColor("#45a7fe"));
                page = 1;
                isFirst = true;
                needLoad = false;
                isLoad = false;
                str = "result";
                initData();
                break;
            default:
                break;
        }
    }

    private void initBtn() {
        rbAll.setChecked(false);
        rbYunMoney.setChecked(false);
        rbExamineDetail.setChecked(false);
        rbAll.setTextColor(Color.parseColor("#686868"));
        rbYunMoney.setTextColor(Color.parseColor("#686868"));
        rbExamineDetail.setTextColor(Color.parseColor("#686868"));
        rbAll.setBackgroundColor(Color.parseColor("#ffffff"));
        rbYunMoney.setBackgroundColor(Color.parseColor("#ffffff"));
        rbExamineDetail.setBackgroundColor(Color.parseColor("#ffffff"));
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
        Log.i("------", needLoad + "needLoad");
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

    private void initRightView() {
        TextView mRightTv = (TextView) View.inflate(this, R.layout.view_title_right_text, null);
        setRightView(mRightTv);
        mRightTv.setText("运费明细");
        mRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HasPostFieldFieldActivity.this, FreightListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBtn();
        rbAll.setChecked(true);
        rbAll.setTextColor(Color.parseColor("#ffffff"));
        rbAll.setBackgroundColor(Color.parseColor("#45a7fe"));
        if (getIntent().getBooleanExtra(ISFREIGHT, false)) {
            str = "yun";
        } else {
            str = "all";
        }
        page = 1;
        isFirst = true;
        needLoad = false;
        isLoad = false;

        initData();
    }
}

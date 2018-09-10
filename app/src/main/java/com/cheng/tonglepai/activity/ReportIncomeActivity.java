package com.cheng.tonglepai.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ReportIncomeAdapter;
import com.cheng.tonglepai.data.RefereeListData;
import com.cheng.tonglepai.net.RefereeListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/9/10.
 */

public class ReportIncomeActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, ReportIncomeAdapter.IReportIncomeListener {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private String screen = "1";
    private ListView lvDetail;
    private LoadingDialog loadingDialog;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private ReportIncomeAdapter mAdapter;
    private TextView tvScreenOne, tvScreenTwo, tvOneIncome, tvTwoIncome, tvRecommendNums;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_report_income);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("推荐收益");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initData() {
        loadingDialog.show();
        RefereeListRequest mRequest = new RefereeListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<RefereeListData>() {
            @Override
            public void onSuccess(RefereeListData data) {
                tvOneIncome.setText("￥" + data.getPrice_data().getZ_price());
                tvTwoIncome.setText("￥" + data.getPrice_data().getLs_price());
                tvRecommendNums.setText(data.getPrice_data().getNums());
                if (isLoad) {
                    isLoad = false;
                    if (data.getData().size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data.getData(),screen,false);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.clearData();
                mAdapter.setData(data.getData(),screen,false);
                if (data.getData().size() < 10)
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
                mAdapter.clearData();
                Toast.makeText(ReportIncomeActivity.this, "没有更多数据", Toast.LENGTH_LONG);
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }
        });
        mRequest.requestRefereeList(page + "", screen + "");
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        lvDetail = (ListView) findViewById(R.id.lv_recommend_list);
        View headView = getLayoutInflater().inflate(R.layout.view_report_head, null);
        lvDetail.addHeaderView(headView);
        mAdapter = new ReportIncomeAdapter(this);
        mAdapter.setOnReportIncomeListener(this);
        lvDetail.setAdapter(mAdapter);

        tvOneIncome = (TextView) headView.findViewById(R.id.tv_one_income);
        tvTwoIncome = (TextView) headView.findViewById(R.id.tv_two_income);
        tvRecommendNums = (TextView) headView.findViewById(R.id.tv_recommend_nums);
        tvScreenOne = (TextView) headView.findViewById(R.id.tv_screen_one);
        tvScreenTwo = (TextView) headView.findViewById(R.id.tv_screen_two);

        tvScreenOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvScreenOne.setBackgroundResource(R.drawable.blue_report_shape);
                tvScreenTwo.setBackgroundResource(R.drawable.blue_report_no_shape);
                tvScreenOne.setTextColor(Color.parseColor("#ffffff"));
                tvScreenTwo.setTextColor(Color.parseColor("#888888"));
                isLoad = false;
                isFirst = true;
                page = 1;
                screen = "1";
                initData();
            }
        });

        tvScreenTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvScreenTwo.setBackgroundResource(R.drawable.blue_report_shape);
                tvScreenOne.setBackgroundResource(R.drawable.blue_report_no_shape);
                tvScreenOne.setTextColor(Color.parseColor("#888888"));
                tvScreenTwo.setTextColor(Color.parseColor("#ffffff"));
                isLoad = false;
                isFirst = true;
                page = 1;
                screen = "2";
                initData();
            }
        });

    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_recommend_list);
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

    @Override
    public void onPhone(String tel) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(ReportIncomeActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(ReportIncomeActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(ReportIncomeActivity.this, "请授权！", Toast.LENGTH_LONG).show();

                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(ReportIncomeActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            // 已经获得授权，可以打电话
            CallPhone(tel);
        }
    }

    private void CallPhone(String tel) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        startActivity(intent);
    }
}

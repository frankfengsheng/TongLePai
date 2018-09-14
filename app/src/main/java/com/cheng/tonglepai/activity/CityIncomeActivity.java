package com.cheng.tonglepai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ReportIncomeAdapter;
import com.cheng.tonglepai.data.RefereeListData;
import com.cheng.tonglepai.net.RefereeCityListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/9/10.
 */

public class CityIncomeActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
    private TextView tv_nums_content;
    private TextView tv_no_content;
    private View headView;
    private LinearLayout llIncomeList;
    private LinearLayout llHeadView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_report_income);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("辖区收益");
        initRefreshLayout();
        initView();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        llIncomeList = (LinearLayout) findViewById(R.id.ll_income_list);
        lvDetail = (ListView) findViewById(R.id.lv_recommend_list);
        tv_no_content = (TextView) findViewById(R.id.tv_no_content);
        headView = getLayoutInflater().inflate(R.layout.view_report_head, null);
        lvDetail.addHeaderView(headView);
        mAdapter = new ReportIncomeAdapter(this);
        lvDetail.setAdapter(mAdapter);

        tv_nums_content = (TextView) headView.findViewById(R.id.tv_nums_content);
        tvOneIncome = (TextView) headView.findViewById(R.id.tv_one_income);
        tvTwoIncome = (TextView) headView.findViewById(R.id.tv_two_income);
        tvRecommendNums = (TextView) headView.findViewById(R.id.tv_recommend_nums);
        tvScreenOne = (TextView) headView.findViewById(R.id.tv_screen_one);
        tvScreenTwo = (TextView) headView.findViewById(R.id.tv_screen_two);
        llHeadView = (LinearLayout) headView.findViewById(R.id.ll_head_view);

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
                mAdapter.clearData();
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
                mAdapter.clearData();
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

    private void initData() {
        loadingDialog.show();
        RefereeCityListRequest mRequest = new RefereeCityListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<RefereeListData>() {
            @Override
            public void onSuccess(RefereeListData data) {
                headView.setVisibility(View.VISIBLE);
                llHeadView.setVisibility(View.VISIBLE);
                llIncomeList.setVisibility(View.VISIBLE);
                tv_no_content.setVisibility(View.GONE);
                tvOneIncome.setText("￥" + data.getPrice_data().getXq_price());
                tvTwoIncome.setText("￥" + data.getPrice_data().getLs_price());
                if(screen.equals("1")) {
                    tv_nums_content.setText("推荐人数:");
                    tvRecommendNums.setText(data.getPrice_data().getNums());
                }else if(screen.equals("2")){
                    tv_nums_content.setText("场地数:");
                    tvRecommendNums.setText(data.getPrice_data().getInfo_nums());
                }
                if (isLoad) {
                    isLoad = false;
                    if (data.getData().size() < 5)
                        needLoad = false;
                    else
                        needLoad = true;

                    mAdapter.setLoadData(data.getData(), screen,true);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.clearData();
                mAdapter.setData(data.getData(), screen,true);
                if (data.getData().size() < 5)
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
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();

                if(code == 36){
                    headView.setVisibility(View.GONE);
                    llHeadView.setVisibility(View.GONE);
                    tv_no_content.setVisibility(View.VISIBLE);
                    llIncomeList.setVisibility(View.GONE);
                }else{
                    Toast.makeText(CityIncomeActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequest.requestRefereeList(page + "", screen + "");
    }
}

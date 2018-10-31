package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ProfitDetailAdapter;
import com.cheng.tonglepai.data.CanApplyData;
import com.cheng.tonglepai.data.InvestorAllIncomeData;
import com.cheng.tonglepai.data.PostIncomeData;
import com.cheng.tonglepai.net.AllIncomeRequest;
import com.cheng.tonglepai.net.CanApplyRequest;
import com.cheng.tonglepai.net.FieldAllIncomeRequest;
import com.cheng.tonglepai.net.FieldCanApplyRequest;
import com.cheng.tonglepai.net.InvestorAllIncomeRequest;
import com.cheng.tonglepai.net.MarkerCanApplyRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.google.gson.Gson;

import java.util.Calendar;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * Created by cheng on 2018/7/24.
 */

public class SeeProfitActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private int userType;
    private int page = 1;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private TextView tvCanApplyMoney;
    private TextView tvToApply;
    private String canApplyMoney = "", bankAccount = "", bankName = "", pricePay = "", zPrice = "";
    private ProfitDetailAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private ListView lvAllIncome;
    private LoadingDialog loadingDialog;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12;
    private String month = "1";
    private TextView tvMonthShow;
    private String openid;
    private String wx_nicknam;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_see_profit);
        MyApplication.getInstance().addActivity(this);
        userType = getIntent().getIntExtra("type", 0);
        setMidTitle("我的收益");
        initRefreshLayout();
        initView();
        initHeadData();
        initData();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_all_income);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    private void initHeadData() {
        if (userType == 1) {
            CanApplyRequest mRequest = new CanApplyRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<CanApplyData>() {
                @Override
                public void onSuccess(CanApplyData data) {

                    if (Double.parseDouble(data.getPrice()) == 0) {
                        canApplyMoney = "0";
                    } else
                    canApplyMoney = Double.parseDouble(data.getPrice()) + "";
                    tvCanApplyMoney.setText(canApplyMoney + "元");
                    bankAccount = data.getBank_account();
                    bankName = data.getBank();
                    openid=data.getOpenid();
                    wx_nicknam=data.getWx_nickname();
                }

                @Override
                public void onFailed(String msg, int code) {


                    Toast.makeText(SeeProfitActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            });
            mRequest.requestCanApply();
        } else if (userType == 2) {
            MarkerCanApplyRequest mRequest = new MarkerCanApplyRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<CanApplyData>() {
                @Override
                public void onSuccess(CanApplyData data) {
                    if (Double.parseDouble(data.getPrice()) == 0) {
                        canApplyMoney = "0";
                    } else
                        canApplyMoney = Double.parseDouble(data.getPrice()) + "";
                    tvCanApplyMoney.setText(canApplyMoney + "元");
                    bankAccount = data.getBank_account();
                    bankName = data.getBank();
                    openid=data.getOpenid();
                    wx_nicknam=data.getWx_nickname();
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(SeeProfitActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            });
            mRequest.requestCanApply();
        } else if (userType == 3) {
            FieldCanApplyRequest mRequest = new FieldCanApplyRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<CanApplyData>() {
                @Override
                public void onSuccess(CanApplyData data) {
                    if (Double.parseDouble(data.getPrice()) == 0) {
                        canApplyMoney = "0";
                    } else
                    canApplyMoney = Double.parseDouble(data.getPrice()) + "";
                    tvCanApplyMoney.setText(canApplyMoney + "元");
                    bankAccount = data.getBank_account();
                    bankName = data.getBank();
                    pricePay = data.getPrice_pay();
                    zPrice = data.getZ_price();
                    openid=data.getOpenid();
                    wx_nicknam=data.getWx_nickname();
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(SeeProfitActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            });
            mRequest.requestFieldCanApply();
        }
    }

    private void initView() {
        Calendar c = Calendar.getInstance();
        month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份

        tvMonthShow = (TextView) findViewById(R.id.tv_month_show);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);
        rb6 = (RadioButton) findViewById(R.id.rb6);
        rb7 = (RadioButton) findViewById(R.id.rb7);
        rb8 = (RadioButton) findViewById(R.id.rb8);
        rb9 = (RadioButton) findViewById(R.id.rb9);
        rb10 = (RadioButton) findViewById(R.id.rb10);
        rb11 = (RadioButton) findViewById(R.id.rb11);
        rb12 = (RadioButton) findViewById(R.id.rb12);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
        rb6.setOnClickListener(this);
        rb7.setOnClickListener(this);
        rb8.setOnClickListener(this);
        rb9.setOnClickListener(this);
        rb10.setOnClickListener(this);
        rb11.setOnClickListener(this);
        rb12.setOnClickListener(this);
        rb1.setBackgroundColor(Color.parseColor("#45a7fe"));
        rb1.setTextColor(Color.parseColor("#ffffff"));

        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        tvCanApplyMoney = (TextView) findViewById(R.id.tv_can_apply_money);
        tvToApply = (TextView) findViewById(R.id.tv_to_apply);
        lvAllIncome = (ListView) findViewById(R.id.lv_all_income);
        mAdapter = new ProfitDetailAdapter(this);
        lvAllIncome.setAdapter(mAdapter);
        tvToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (TextUtils.isEmpty(canApplyMoney) || Double.parseDouble(canApplyMoney) == 0.00) {
//                    Toast.makeText(SeeProfitActivity.this, "没有提现金额", Toast.LENGTH_LONG).show();
//                    return;
//                }
                Intent intent = new Intent(SeeProfitActivity.this, ApplyMoneyActivityNew.class);
                intent.putExtra(ApplyMoneyActivityNew.CAN_APPLY_MONEY, canApplyMoney);
                intent.putExtra(ApplyMoneyActivityNew.BANK_ACCOUNT, bankAccount);
                intent.putExtra(ApplyMoneyActivityNew.BANK_NAME, bankName);
                intent.putExtra(ApplyMoneyActivityNew.USER_TYPE, userType);
                intent.putExtra(ApplyMoneyActivityNew.INCOME_ALL, zPrice);
                intent.putExtra(ApplyMoneyActivityNew.NEED_PAY, pricePay);
                intent.putExtra(ApplyMoneyActivityNew.OPEN_ID,openid);
                intent.putExtra(ApplyMoneyActivityNew.WX_NICKNAME,wx_nicknam);
                startActivity(intent);
            }
        });

        if (month.equals("1")) {
            initRadionButton();
            rb1.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb1.setTextColor(Color.parseColor("#ffffff"));
            rb1.setVisibility(View.VISIBLE);
            page = 1;
            tvMonthShow.setText("1月收益");
        } else if (month.equals("2")) {
            initRadionButton();
            rb2.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb2.setTextColor(Color.parseColor("#ffffff"));
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            page = 1;
            tvMonthShow.setText("2月收益");
        } else if (month.equals("3")) {
            initRadionButton();
            rb3.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb3.setTextColor(Color.parseColor("#ffffff"));
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            page = 1;
            tvMonthShow.setText("3月收益");
        } else if (month.equals("4")) {
            initRadionButton();
            rb4.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb4.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            tvMonthShow.setText("4月收益");
        } else if (month.equals("5")) {
            initRadionButton();
            rb5.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb5.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            tvMonthShow.setText("5月收益");
        } else if (month.equals("6")) {
            initRadionButton();
            rb6.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb6.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            tvMonthShow.setText("6月收益");
        } else if (month.equals("7")) {
            initRadionButton();
            rb7.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb7.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            tvMonthShow.setText("7月收益");
        } else if (month.equals("8")) {
            initRadionButton();
            rb8.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb8.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            rb8.setVisibility(View.VISIBLE);
            tvMonthShow.setText("8月收益");
        } else if (month.equals("9")) {
            initRadionButton();
            rb9.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb9.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            rb8.setVisibility(View.VISIBLE);
            rb9.setVisibility(View.VISIBLE);
            tvMonthShow.setText("9月收益");
        } else if (month.equals("10")) {
            initRadionButton();
            rb10.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb10.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            rb8.setVisibility(View.VISIBLE);
            rb9.setVisibility(View.VISIBLE);
            rb10.setVisibility(View.VISIBLE);
            tvMonthShow.setText("10月收益");
        } else if (month.equals("11")) {
            initRadionButton();
            rb11.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb11.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            rb8.setVisibility(View.VISIBLE);
            rb9.setVisibility(View.VISIBLE);
            rb10.setVisibility(View.VISIBLE);
            rb11.setVisibility(View.VISIBLE);
            tvMonthShow.setText("11月收益");
        } else if (month.equals("12")) {
            initRadionButton();
            rb12.setBackgroundColor(Color.parseColor("#45a7fe"));
            rb12.setTextColor(Color.parseColor("#ffffff"));
            page = 1;
            rb1.setVisibility(View.VISIBLE);
            rb2.setVisibility(View.VISIBLE);
            rb3.setVisibility(View.VISIBLE);
            rb4.setVisibility(View.VISIBLE);
            rb5.setVisibility(View.VISIBLE);
            rb6.setVisibility(View.VISIBLE);
            rb7.setVisibility(View.VISIBLE);
            rb8.setVisibility(View.VISIBLE);
            rb9.setVisibility(View.VISIBLE);
            rb10.setVisibility(View.VISIBLE);
            rb11.setVisibility(View.VISIBLE);
            rb12.setVisibility(View.VISIBLE);
            tvMonthShow.setText("12月收益");
        }
    }

    private void initData() {
        loadingDialog.show();


        PostIncomeData mData = new PostIncomeData();
        mData.setMonth(month);
        mData.setPage(page + "");
        mData.setYear("2018");
        mData.setUserid(HttpConfig.newInstance(this).getUserid());
        String gson = new Gson().toJson(mData);

        if (userType == 2) {
            AllIncomeRequest mRequest = new AllIncomeRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorAllIncomeData>() {
                @Override
                public void onSuccess(InvestorAllIncomeData data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 2)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.clearData();
                    mAdapter.setData(data.getData(), "2018", month);
                    if (data.getData().size() < 2)
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
                    Toast.makeText(SeeProfitActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                }
            });

            mRequest.requestAllDevice(gson);
        } else if (userType == 1) {
            InvestorAllIncomeRequest mRequest = new InvestorAllIncomeRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorAllIncomeData>() {
                @Override
                public void onSuccess(InvestorAllIncomeData data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 2)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.clearData();
                    mAdapter.setData(data.getData(), "2018", month);
                    if (data.getData().size() < 2)
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
                    Toast.makeText(SeeProfitActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                }
            });

            mRequest.requestAllDevice(gson);
        } else if (userType == 3) {
            FieldAllIncomeRequest mRequest = new FieldAllIncomeRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorAllIncomeData>() {
                @Override
                public void onSuccess(InvestorAllIncomeData data) {
                    if (isLoad) {
                        isLoad = false;
                        if (data.getData().size() < 2)
                            needLoad = false;
                        else
                            needLoad = true;
                        mAdapter.setLoadData(data.getData());
                        mRefreshLayout.endRefreshing();
                        mRefreshLayout.endLoadingMore();
                        loadingDialog.dismiss();
                        return;
                    }
                    mAdapter.clearData();
                    mAdapter.setData(data.getData(), "2018", month);
                    if (data.getData().size() < 2)
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
                    Toast.makeText(SeeProfitActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                }
            });

            mRequest.requestFieldAllDevice(gson);
        }
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
            case R.id.rb1:
                initRadionButton();
                rb1.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb1.setTextColor(Color.parseColor("#ffffff"));
                month = "1";
                page = 1;
                tvMonthShow.setText("1月收益");
                initData();
                break;
            case R.id.rb2:
                initRadionButton();
                rb2.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb2.setTextColor(Color.parseColor("#ffffff"));
                month = "2";
                page = 1;
                tvMonthShow.setText("2月收益");
                initData();
                break;
            case R.id.rb3:
                initRadionButton();
                rb3.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb3.setTextColor(Color.parseColor("#ffffff"));
                month = "3";
                page = 1;
                tvMonthShow.setText("3月收益");
                initData();
                break;
            case R.id.rb4:
                initRadionButton();
                rb4.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb4.setTextColor(Color.parseColor("#ffffff"));
                month = "4";
                page = 1;
                tvMonthShow.setText("4月收益");
                initData();
                break;
            case R.id.rb5:
                initRadionButton();
                rb5.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb5.setTextColor(Color.parseColor("#ffffff"));
                month = "5";
                page = 1;
                tvMonthShow.setText("5月收益");
                initData();
                break;
            case R.id.rb6:
                initRadionButton();
                rb6.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb6.setTextColor(Color.parseColor("#ffffff"));
                month = "6";
                page = 1;
                tvMonthShow.setText("6月收益");
                initData();
                break;
            case R.id.rb7:
                initRadionButton();
                rb7.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb7.setTextColor(Color.parseColor("#ffffff"));
                month = "7";
                page = 1;
                tvMonthShow.setText("7月收益");
                initData();
                break;
            case R.id.rb8:
                initRadionButton();
                rb8.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb8.setTextColor(Color.parseColor("#ffffff"));
                month = "8";
                page = 1;
                tvMonthShow.setText("8月收益");
                initData();
                break;
            case R.id.rb9:
                initRadionButton();
                rb9.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb9.setTextColor(Color.parseColor("#ffffff"));
                month = "9";
                page = 1;
                tvMonthShow.setText("9月收益");
                initData();
                break;
            case R.id.rb10:
                initRadionButton();
                rb10.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb10.setTextColor(Color.parseColor("#ffffff"));
                month = "10";
                page = 1;
                tvMonthShow.setText("10月收益");
                initData();
                break;
            case R.id.rb11:
                initRadionButton();
                rb11.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb11.setTextColor(Color.parseColor("#ffffff"));
                month = "11";
                page = 1;
                tvMonthShow.setText("11月收益");
                initData();
                break;
            case R.id.rb12:
                initRadionButton();
                rb12.setBackgroundColor(Color.parseColor("#45a7fe"));
                rb12.setTextColor(Color.parseColor("#ffffff"));
                month = "12";
                page = 1;
                tvMonthShow.setText("12月收益");
                initData();
                break;
            default:
                break;
        }
    }

    private void initRadionButton() {
        rb1.setBackgroundColor(Color.parseColor("#ffffff"));
        rb2.setBackgroundColor(Color.parseColor("#ffffff"));
        rb3.setBackgroundColor(Color.parseColor("#ffffff"));
        rb4.setBackgroundColor(Color.parseColor("#ffffff"));
        rb5.setBackgroundColor(Color.parseColor("#ffffff"));
        rb6.setBackgroundColor(Color.parseColor("#ffffff"));
        rb7.setBackgroundColor(Color.parseColor("#ffffff"));
        rb8.setBackgroundColor(Color.parseColor("#ffffff"));
        rb9.setBackgroundColor(Color.parseColor("#ffffff"));
        rb10.setBackgroundColor(Color.parseColor("#ffffff"));
        rb11.setBackgroundColor(Color.parseColor("#ffffff"));
        rb12.setBackgroundColor(Color.parseColor("#ffffff"));
        rb1.setTextColor(Color.parseColor("#45a7fe"));
        rb2.setTextColor(Color.parseColor("#45a7fe"));
        rb3.setTextColor(Color.parseColor("#45a7fe"));
        rb4.setTextColor(Color.parseColor("#45a7fe"));
        rb5.setTextColor(Color.parseColor("#45a7fe"));
        rb6.setTextColor(Color.parseColor("#45a7fe"));
        rb7.setTextColor(Color.parseColor("#45a7fe"));
        rb8.setTextColor(Color.parseColor("#45a7fe"));
        rb9.setTextColor(Color.parseColor("#45a7fe"));
        rb10.setTextColor(Color.parseColor("#45a7fe"));
        rb11.setTextColor(Color.parseColor("#45a7fe"));
        rb12.setTextColor(Color.parseColor("#45a7fe"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHeadData();
        goFirstState();
        initData();
    }

    private void goFirstState() {
        isLoad = false;
        isFirst = true;
        page = 1;
    }
}

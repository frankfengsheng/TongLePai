package com.cheng.tonglepai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cheng.retrofit20.bean.DevicesDetailsBean;
import com.cheng.retrofit20.bean.DevicesIncomeByMonthBean;
import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.InvestorDeviceListData;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.TimeUtil;
import com.cheng.tonglepai.view.ChartView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/7/10.
 */

public class DeviceDetailActivity extends TitleActivity implements View.OnClickListener{

    private TextView tv_deviceName,tv_deviceState,tv_deviceCode,tv_starTime;
    private TextView tv_dayIncome;
    private TextView tv_yesterdayIncome;
    private TextView tv_monthIncome;
    private TextView tv_lastMonthIncome;
    private TextView tv_totalIncome;
    private TextView tv_weizhi;
    private TextView tv_date;
    private LinearLayout ly_date;
    InvestorDeviceListData investorDeviceListData;
    SiteEquimentListBean.DataBean dataBean;
    ChartView chartView;
    private TimePickerView pvTime;
    LinearLayout ly_chart;
    LoadingDialog loadingDialog=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_device_details);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("查看");
        initView();
        initData();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载数据中...");

        investorDeviceListData= (InvestorDeviceListData) getIntent().getSerializableExtra("siteBean");
        dataBean= (SiteEquimentListBean.DataBean) getIntent().getSerializableExtra("deviceBean");
        tv_weizhi= (TextView) findViewById(R.id.tv_device_weizhi);
        tv_totalIncome= (TextView) findViewById(R.id.tv_all_money);
        tv_deviceName= (TextView) findViewById(R.id.tv_device_name);
        tv_deviceState= (TextView) findViewById(R.id.tv_device_state);
        tv_deviceCode= (TextView) findViewById(R.id.tv_device_code);
        tv_starTime= (TextView) findViewById(R.id.tv_device_start_time);
        tv_dayIncome= (TextView) findViewById(R.id.tv_today_income);
        tv_yesterdayIncome= (TextView) findViewById(R.id.tv_yesterday_income);
        tv_monthIncome= (TextView) findViewById(R.id.tv_this_month_income);
        tv_lastMonthIncome= (TextView) findViewById(R.id.tv_last_month_income);
        tv_yesterdayIncome= (TextView) findViewById(R.id.tv_yesterday_income);
        tv_date= (TextView) findViewById(R.id.tv_date);
        ly_date= (LinearLayout) findViewById(R.id.ly_date);
        chartView= (ChartView) findViewById(R.id.chartview);
        ly_chart= (LinearLayout) findViewById(R.id.ly_chart);
        ly_date.setOnClickListener(this);
        //初始化时间选择控件
        initTimePicker();
        refreshUI();
    }

    private void initData() {
        if(dataBean!=null)
        new MyIncomeModle(this).GetDeviceDetails(dataBean.getDevice_id(), new MyIncomeModle.GetDeviceDetaisCallback() {
            @Override
            public void Sucess(DevicesDetailsBean bean) {
                if(bean!=null&&bean.getData()!=null){
                    if(!TextUtils.isEmpty(bean.getData().getLast_month()))tv_lastMonthIncome.setText("￥"+bean.getData().getLast_month());
                    if(!TextUtils.isEmpty(bean.getData().getThis_month()))tv_monthIncome.setText("￥"+bean.getData().getThis_month());
                    if(!TextUtils.isEmpty(bean.getData().getToday()))tv_dayIncome.setText("￥"+bean.getData().getToday());
                    if(!TextUtils.isEmpty(bean.getData().getYesterday()))tv_yesterdayIncome.setText("￥"+bean.getData().getYesterday());
                    if(!TextUtils.isEmpty(bean.getData().getTotal()))tv_totalIncome.setText("￥"+bean.getData().getTotal());
                    //x轴坐标对应的数据
                    List<String> xValue = new ArrayList<>();
                    //y轴坐标对应的数据
                    List<Integer> yValue = new ArrayList<>();
                    //折线对应的数据
                    Map<String, Double> value = new HashMap<>();
                    for (int i = 0; i < bean.getZx_data().size(); i++) {
                        xValue.add((i + 1) + "号");
                        value.put((i + 1) + "号", bean.getZx_data().get(i).getPrice());
                    }

                    double maxIndex = 0;//定义最大值为该数组的第一个数
                    double minIndex = 0;//定义最小值为该数组的第一个数

                    for (int i = 0; i < bean.getZx_data().size(); i++) {
                        if (maxIndex < bean.getZx_data().get(i).getPrice()) {
                            maxIndex = bean.getZx_data().get(i).getPrice();
                        }

                        if (minIndex > bean.getZx_data().get(i).getPrice()) {
                            minIndex = bean.getZx_data().get(i).getPrice();
                        }

                    }
                    int a = (int)Math.ceil(18.6 / 6);
                    for (int i = 0; i  < 7; i++) {

                        if (a == 0) {
                            yValue.add(i * 500);
                        } else
                            yValue.add(i * a);
                    }
                   chartView.setValue(value, xValue, yValue);

                }
            }

            @Override
            public void Faile() {

            }
        });
    }

    private void refreshUI(){
        if(investorDeviceListData!=null&&!TextUtils.isEmpty(investorDeviceListData.getDetails()))tv_weizhi.setText(investorDeviceListData.getDetails());
        if(dataBean!=null&&!TextUtils.isEmpty(dataBean.getDevice_name()))tv_deviceName.setText(dataBean.getDevice_name());
        if(dataBean!=null&&!TextUtils.isEmpty(dataBean.getDevice_code()))tv_deviceCode.setText("通讯编码："+dataBean.getDevice_code());
        if(dataBean!=null&&!TextUtils.isEmpty(dataBean.getCreated()))tv_starTime.setText("运行时间："+TimeUtil.stampToDate(dataBean.getCreated()));
        if(dataBean!=null&&dataBean.getStatus().equals("1")){
            tv_deviceState.setText("运营");
        }else {
            tv_deviceState.setText("停止运营");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");// HH:mm:ss
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());
        tv_date.setText(simpleDateFormat.format(date));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_date:
                chartView.setVisibility(View.GONE);
                pvTime.show(tv_date, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
              /*  new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                    }
                }).build();*/
                break;
        }
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v,String year,String month) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                TextView btn = (TextView) v;
                String data=getTime(date);
                btn.setText(data);
                getIncomeByMonth(data.substring(0,data.indexOf("年")),data.substring(data.indexOf("年")+1,data.indexOf("月")));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                                chartView.setVisibility(View.VISIBLE);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                                chartView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setTextColorCenter(Color.RED)
                .setRangDate(null, selectedDate)
                .setDecorView(ly_chart)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(true);//系统返回键不屏蔽
    }

    /**
     * 根据月份获取设备收益
     * @param
     * @return
     */
    private void getIncomeByMonth(String year,String month){
        loadingDialog.show();
        new MyIncomeModle(this).GetIncomeByMonth(dataBean.getDevice_id(),year,month, new MyIncomeModle.GetIncomeByMonthCallback() {
            @Override
            public void Sucess(DevicesIncomeByMonthBean bean) {
                loadingDialog.dismiss();
                if(bean!=null&&bean.getZx_data()!=null&&bean.getZx_data().size()>0) {
                    //x轴坐标对应的数据
                    List<String> xValue = new ArrayList<>();
                    //y轴坐标对应的数据
                    List<Integer> yValue = new ArrayList<>();
                    //折线对应的数据
                    Map<String, Double> value = new HashMap<>();
                    for (int i = 0; i < bean.getZx_data().size(); i++) {
                        xValue.add((i + 1) + "号");
                        value.put((i + 1) + "号", bean.getZx_data().get(i).getPrice());
                    }

                    double maxIndex = 0;//定义最大值为该数组的第一个数
                    double minIndex = 0;//定义最小值为该数组的第一个数

                    for (int i = 0; i < bean.getZx_data().size(); i++) {
                        if (maxIndex < bean.getZx_data().get(i).getPrice()) {
                            maxIndex = bean.getZx_data().get(i).getPrice();
                        }

                        if (minIndex > bean.getZx_data().get(i).getPrice()) {
                            minIndex = bean.getZx_data().get(i).getPrice();
                        }

                    }
                    int a = (int) Math.ceil(18.6 / 6);
                    for (int i = 0; i < 7; i++) {

                        if (a == 0) {
                            yValue.add(i * 500);
                        } else
                            yValue.add(i * a);
                    }
                    chartView.setValue(value, xValue, yValue);
                }
            }

            @Override
            public void Faile() {
                loadingDialog.dismiss();
            }
        });
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }
}

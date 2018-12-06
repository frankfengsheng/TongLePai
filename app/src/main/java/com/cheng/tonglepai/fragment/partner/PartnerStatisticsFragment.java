
package com.cheng.tonglepai.fragment.partner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cheng.retrofit20.bean.PartnerStaticIncomeBean;
import com.cheng.retrofit20.bean.SiteIncomeBean;
import com.cheng.retrofit20.bean.SiteTotalIncomeBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.model.IncomeModle;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.view.ChartView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * Created by User on 2016/6/28.冯
*/

public class PartnerStatisticsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View contentView;
    private TextView tv_totalIncome;
    private TextView tv_saomaIncome;
    private TextView tv_toubiIncome;
    private TextView tv_deviceCount;
    private TextView tv_toubiCount;
    private TextView tv_daijiaoCount;
    private TextView tv_todaySaoma,tv_todayToubi,tv_todayIncome;
    private TextView tv_yestodaySaoma,tv_yestodayToubi,tv_yestodayIncome;
    private TextView tv_monthSaoma,tv_monthToubi,tv_monthIncome;
    private TextView tv_date;
    private LinearLayout ly_date;
    private LinearLayout ly_xiaqu_income;
    private TextView tv_xiaquIncome;
    private TextView tv_lineXiaqu;

    ChartView chartView;
    private TimePickerView pvTime;
    private LinearLayout ly_bottom;
    LoadingDialog loadingDialog=null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=inflater.inflate(R.layout.site_statistics_fragment, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_view();

    }
    private void init_view()
    {
        loadingDialog = DialogUtil.createLoaddingDialog(getActivity());
        loadingDialog.setMessage("加载数据中...");
        tv_totalIncome= (TextView) contentView.findViewById(R.id.tv_totalIncom);
        tv_toubiIncome= (TextView) contentView.findViewById(R.id.tv_toubi_income);
        tv_saomaIncome= (TextView) contentView.findViewById(R.id.tv_saoma_income);
        tv_deviceCount= (TextView) contentView.findViewById(R.id.tv_device_count);
        tv_toubiCount= (TextView) contentView.findViewById(R.id.tv_toubi_count);
        tv_daijiaoCount= (TextView) contentView.findViewById(R.id.tv_daijiao_count);
        tv_todaySaoma= (TextView) contentView.findViewById(R.id.tv_today_saoma_count);
        tv_todayToubi= (TextView) contentView.findViewById(R.id.tv_today_toubi_count);
        tv_todayIncome= (TextView) contentView.findViewById(R.id.tv_today_income);
        tv_yestodaySaoma= (TextView) contentView.findViewById(R.id.tv_yestoday_saoma_count);
        tv_yestodayToubi= (TextView) contentView.findViewById(R.id.tv_yestoday_toubi_count);
        tv_yestodayIncome= (TextView) contentView.findViewById(R.id.tv_yestoday_daijiao_income);
        tv_monthSaoma= (TextView) contentView.findViewById(R.id.tv_month_saoma_count);
        tv_monthIncome= (TextView) contentView.findViewById(R.id.tv_month_income);
        tv_monthToubi= (TextView) contentView.findViewById(R.id.tv_month_toubi_count);
        tv_date= (TextView) contentView.findViewById(R.id.tv_date);
        ly_date= (LinearLayout) contentView.findViewById(R.id.ly_date);
        chartView= (ChartView) contentView.findViewById(R.id.chartview);
        ly_bottom= (LinearLayout) contentView.findViewById(R.id.ly_bottom);
        ly_xiaqu_income= (LinearLayout) contentView.findViewById(R.id.ly_xiaqu_income);
        tv_xiaquIncome= (TextView) contentView.findViewById(R.id.tv_xiaqu_income);
        tv_lineXiaqu= (TextView) contentView.findViewById(R.id.tv_line_xiaqu);

        ly_xiaqu_income.setVisibility(View.VISIBLE);
        tv_lineXiaqu.setVisibility(View.VISIBLE);

        ly_date.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");// HH:mm:ss
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());
        tv_date.setText(simpleDateFormat.format(date));
        initTimePicker();
        getIncome();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_date:
                chartView.setVisibility(View.GONE);
                pvTime.show(tv_date, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view

                break;
        }
    }

    /**
     * 根据月份获取设备收益
     * @param
     * @return
     */
    private void getIncome(){
        loadingDialog.show();
       new IncomeModle(getActivity()).GetPartnerStatisticIncomeInfo(new IncomeModle.PartnerStatisticIncomeSucess() {
           @Override
           public void Sucess(PartnerStaticIncomeBean bean) {
               loadingDialog.dismiss();
               if(bean!=null){
                   if(bean.getData()!=null&&!TextUtils.isEmpty(bean.getData().getTotal()))tv_totalIncome.setText("￥"+bean.getData().getTotal());
                   if(bean.getData()!=null&&!TextUtils.isEmpty(bean.getData().getSm_price()))tv_saomaIncome.setText("￥"+bean.getData().getSm_price());
                   if(bean.getData()!=null&&!TextUtils.isEmpty(bean.getData().getTb_price()))tv_toubiIncome.setText("￥"+bean.getData().getTb_price());
                   if(bean.getData()!=null&&!TextUtils.isEmpty(bean.getData().getJurisdiction_price()))tv_xiaquIncome.setText("￥"+bean.getData().getJurisdiction_price());
                   if(bean.getData_m()!=null)tv_toubiCount.setText(""+bean.getData_m().getDevice_nums());
                   if(bean.getData_m()!=null)tv_deviceCount.setText(""+bean.getData_m().getCd_nums());
                   if(bean.getData_m()!=null)tv_daijiaoCount.setText(""+bean.getData_m().getLow_device_nums());
                   if(bean.getToday_data()!=null)tv_todaySaoma.setText(""+bean.getToday_data().getSm_price());
                   if(bean.getToday_data()!=null)tv_todayToubi.setText(""+bean.getToday_data().getTb_price());
                   if(bean.getToday_data()!=null&&!TextUtils.isEmpty(bean.getToday_data().getPrice()))tv_todayIncome.setText(""+bean.getToday_data().getPrice());
                   if(bean.getYesterday_data()!=null)tv_yestodaySaoma.setText(""+bean.getYesterday_data().getSm_price());
                   if(bean.getYesterday_data()!=null)tv_yestodayToubi.setText(""+bean.getYesterday_data().getTb_price());
                   if(bean.getYesterday_data()!=null&&!TextUtils.isEmpty(bean.getYesterday_data().getPrice()))tv_yestodayIncome.setText(""+bean.getYesterday_data().getPrice());
                   if(bean.getThismonth_data()!=null)tv_monthSaoma.setText(""+bean.getThismonth_data().getSm_price());
                   if(bean.getThismonth_data()!=null)tv_monthToubi.setText(""+bean.getThismonth_data().getTb_price());
                   if(bean.getThismonth_data()!=null&&!TextUtils.isEmpty(bean.getThismonth_data().getPrice()))tv_monthIncome.setText(""+bean.getThismonth_data().getPrice());
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
                       int a = (int) Math.ceil(maxIndex*2 / 6);
                       for (int i = 0; i < 7; i++) {

                           if (a == 0) {
                               yValue.add(i * 500);
                           } else
                               yValue.add(i * a);
                       }
                       chartView.setValue(value, xValue, yValue);
                   }
               }
           }

           @Override
           public void Faile() {
                loadingDialog.dismiss();
           }
       });
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), 28);
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v, String year, String month) {
                //选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                TextView btn = (TextView) v;
                String data=getTime(date);
                btn.setText(data);
                getIncomeByMonth(data.substring(0,data.indexOf("年")),data.substring(data.indexOf("年")+1,data.indexOf("月")));
            }
        }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
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
                .setDecorView(ly_bottom)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(true);//系统返回键不屏蔽
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }

    /**
     * 根据月份获取设备收益
     * @param
     * @return
     */
    private void getIncomeByMonth(String year,String month){
        loadingDialog.show();
        new IncomeModle(getActivity()).SiteGetTotalIncomeByMonth(year,month, new IncomeModle.PartnerZhexianIncomeSucess() {
            @Override
            public void Sucess(SiteTotalIncomeBean bean) {
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
                    int a = (int) Math.ceil(maxIndex*2/ 6);
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
}

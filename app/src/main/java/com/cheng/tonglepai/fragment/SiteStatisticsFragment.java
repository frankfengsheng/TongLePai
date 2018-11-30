
package com.cheng.tonglepai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.view.ChartView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/*
 * Created by User on 2016/6/28.冯
*/

public class SiteStatisticsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
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
    ChartView chartView;
    private TimePickerView pvTime;
    private LinearLayout ly_bottom;
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
        initTimePicker();
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
            }
        }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

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
}

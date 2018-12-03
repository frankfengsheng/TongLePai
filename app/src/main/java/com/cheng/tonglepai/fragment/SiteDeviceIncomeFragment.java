
package com.cheng.tonglepai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class SiteDeviceIncomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View contentView;
    private TextView tv_today;
    private TextView tv_yestoday;
    private TextView tv_week;
    private TextView tv_month;
    private LinearLayout ly_startDate;
    private LinearLayout ly_endDate;
    private TextView tv_startDate;
    private TextView tv_endDate;
    SimpleDateFormat simpleDateFormat;
    RecyclerView recyclerView;
    public static SiteDeviceIncomeFragment newInstance() {

        Bundle args = new Bundle();
        SiteDeviceIncomeFragment fragment = new SiteDeviceIncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
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

        contentView = inflater.inflate(R.layout.site_device_income_fragment, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_view();

    }

    private void init_view() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        tv_today = (TextView) contentView.findViewById(R.id.tv_today);
        tv_week = (TextView) contentView.findViewById(R.id.tv_this_week);
        tv_yestoday = (TextView) contentView.findViewById(R.id.tv_yesterday);
        tv_month = (TextView) contentView.findViewById(R.id.tv_this_month);
        ly_startDate = (LinearLayout) contentView.findViewById(R.id.ly_start_date);
        ly_endDate = (LinearLayout) contentView.findViewById(R.id.ly_end_date);
        tv_startDate= (TextView) contentView.findViewById(R.id.tv_start_date);
        tv_endDate= (TextView) contentView.findViewById(R.id.tv_end_date);
        recyclerView= (RecyclerView) contentView.findViewById(R.id.myincome_fragment_ry);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        ly_startDate.setOnClickListener(this);
        ly_endDate.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        tv_yestoday.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_today:
                init_title();
                tv_today.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(getTodayDate());
                tv_endDate.setText(getTodayDate());
                break;
            case R.id.tv_yesterday:
                init_title();
                tv_yestoday.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(getYesterdayTime());
                tv_endDate.setText(getTodayDate());
                break;
            case R.id.tv_this_month:
                init_title();
                tv_month.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(getMonthsTime());
                tv_endDate.setText(getTodayDate());
                break;
            case R.id.tv_this_week:
                init_title();
                tv_week.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(getWeekTime());
                tv_endDate.setText(getTodayDate());
                break;
        }
    }

    private void init_title() {
        tv_week.setTextColor(getResources().getColor(R.color.text_black));
        tv_today.setTextColor(getResources().getColor(R.color.text_black));
        tv_yestoday.setTextColor(getResources().getColor(R.color.text_black));
        tv_month.setTextColor(getResources().getColor(R.color.text_black));
    }

    /**
     * 获取今日日期
     */
    private String getTodayDate() {

        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    /**
     * 获取昨日日期
     */
    private  String getYesterdayTime(){
        Calendar  calendar =Calendar. getInstance();
        calendar.add( Calendar. DATE, -1); //向前走一天
        Date date= calendar.getTime();
        return simpleDateFormat.format(date);
    }
    /**
     * 获取本周第一天日期
     */
    private  String getWeekTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);
        Date date= cal.getTime();
        return simpleDateFormat.format(date);
    }
    /**
     * 获取本月第一天日期
     */
    private  String getMonthsTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date= cal.getTime();
        return simpleDateFormat.format(date);
    }
}

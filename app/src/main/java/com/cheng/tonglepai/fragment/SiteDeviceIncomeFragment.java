
package com.cheng.tonglepai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.SiteFileIncomeListBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.FragmentSiteDeviceListAdapter;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.tool.DateSelectUtil;
import com.cheng.tonglepai.tool.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    private LinearLayout ly_bottom;
    SimpleDateFormat simpleDateFormat;
    RecyclerView recyclerView;
    FragmentSiteDeviceListAdapter adapter;
    private List<SiteFileIncomeListBean.DataBean> mList=new ArrayList<>();
    private int page=1;

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
        ly_bottom= (LinearLayout) contentView.findViewById(R.id.ly_bottom);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FragmentSiteDeviceListAdapter(getActivity(),mList);
        adapter.setOnItemClickLis(new FragmentSiteDeviceListAdapter.OnItemclickListenner() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        ly_startDate.setOnClickListener(this);
        ly_endDate.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        tv_yestoday.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        getDeviceIncome(tv_startDate.getText().toString(),tv_endDate.getText().toString(),page+"");

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
            case R.id.ly_start_date:
                ly_bottom.setVisibility(View.VISIBLE);
                DateSelectUtil.getInstance().selectDate(getActivity(),ly_bottom,tv_startDate,2,new DateSelectUtil.selectDateCallBack() {
                    @Override
                    public void dateselect(String date) {
                        ly_bottom.setVisibility(View.GONE);
                        if(judgeDate(date,tv_endDate.getText().toString())){
                            ToastUtil.showToast(getActivity(),"开始时间不得晚于结束时间");
                        }else {
                            tv_startDate.setText(date);
                        }
                    }
                });
                break;
            case R.id.ly_end_date:
                ly_bottom.setVisibility(View.VISIBLE);
                DateSelectUtil.getInstance().selectDate(getActivity(),ly_bottom,tv_endDate,2,new DateSelectUtil.selectDateCallBack() {
                    @Override
                    public void dateselect(String date) {
                        ly_bottom.setVisibility(View.GONE);
                        if(judgeDate(tv_startDate.getText().toString(),date)){
                            ToastUtil.showToast(getActivity(),"开始时间不得晚于结束时间");
                        }else {
                            tv_endDate.setText(date);
                        }
                    }
                });
                break;
        }
    }

    private void init_title() {
        tv_week.setTextColor(getResources().getColor(R.color.text_black3));
        tv_today.setTextColor(getResources().getColor(R.color.text_black3));
        tv_yestoday.setTextColor(getResources().getColor(R.color.text_black3));
        tv_month.setTextColor(getResources().getColor(R.color.text_black3));
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
    /**
     * 判断开始时间是否大于结束时间
     */
    private boolean judgeDate(String startDate,String endDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start_date=dateFormat.parse(startDate);
            Date end_date=dateFormat.parse(endDate);
            if(start_date.getTime()>end_date.getTime()){
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取设备收益详情
     */
    private void getDeviceIncome(String start_tiem,String end_time,String page){
        new MyIncomeModle(getActivity()).SiteGetFileIncome(start_tiem, end_time, page, new MyIncomeModle.SiteFielIncomeSucessCallback() {
            @Override
            public void Sucess(SiteFileIncomeListBean bean) {
                if(bean!=null&&bean.getData()!=null&&bean.getData().size()>0){
                    mList.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void Faile() {

            }
        });
    }
}

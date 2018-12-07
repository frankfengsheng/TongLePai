
package com.cheng.tonglepai.fragment.partner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cheng.retrofit20.bean.PartnerDeviceIncomeListBean;
import com.cheng.retrofit20.bean.PartnerSiteIncomeBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.DeviceIncomeDetailActivity;
import com.cheng.tonglepai.activity.PartnerDeviceIncomeListActivity;
import com.cheng.tonglepai.activity.PartnerSiteIncomeActivity;
import com.cheng.tonglepai.adapter.PartenerDeviceIncomeAdapter;
import com.cheng.tonglepai.adapter.PartnerSiteIncomeAdpter;
import com.cheng.tonglepai.model.IncomeModle;
import com.cheng.tonglepai.tool.DateSelectUtil;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/*
 * Created by User on 2016/6/28.冯
 */

public class PartnerDeviceIncomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener,BGARefreshLayout.BGARefreshLayoutDelegate {
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
    PartenerDeviceIncomeAdapter adapter;

    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvDetail;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad=false;
    private List<PartnerDeviceIncomeListBean.DataBean> mList=new ArrayList<>();
    private TextView tv_empty;
    private LoadingDialog loadingDialog;

    public static PartnerDeviceIncomeFragment newInstance() {

        Bundle args = new Bundle();
        PartnerDeviceIncomeFragment fragment = new PartnerDeviceIncomeFragment();
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
        initRefreshLayout();

    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) contentView.findViewById(R.id.bga_bind_device);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
    }
    private void init_view() {
        loadingDialog = DialogUtil.createLoaddingDialog(getActivity());
        loadingDialog.setMessage("加载数据中...");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        tv_today = (TextView) contentView.findViewById(R.id.tv_today);
        tv_week = (TextView) contentView.findViewById(R.id.tv_this_week);
        tv_yestoday = (TextView) contentView.findViewById(R.id.tv_yesterday);
        tv_month = (TextView) contentView.findViewById(R.id.tv_this_month);
        ly_startDate = (LinearLayout) contentView.findViewById(R.id.ly_start_date);
        ly_endDate = (LinearLayout) contentView.findViewById(R.id.ly_end_date);
        tv_startDate= (TextView) contentView.findViewById(R.id.tv_start_date);
        tv_endDate= (TextView) contentView.findViewById(R.id.tv_end_date);
        //recyclerView= (RecyclerView) contentView.findViewById(R.id.myincome_fragment_ry);
        ly_bottom= (LinearLayout) contentView.findViewById(R.id.ly_bottom);
        lvDetail = (ListView) contentView.findViewById(R.id.lv_bind_device);
        tv_empty= (TextView) contentView.findViewById(R.id.tv_empty);
        tv_startDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
        tv_endDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), PartnerDeviceIncomeListActivity.class);
                intent.putExtra(DeviceIncomeDetailActivity.DEVICEID,mList.get(position).getId());
                intent.putExtra(DeviceIncomeDetailActivity.STARTTIME,tv_startDate.getText().toString());
                intent.putExtra(DeviceIncomeDetailActivity.EndTime,tv_endDate.getText().toString());
                startActivity(intent);
            }
        });
        adapter=new PartenerDeviceIncomeAdapter(getActivity(),mList);
        lvDetail.setAdapter(adapter);

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
                tv_startDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
                tv_endDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
                loadData();
                break;
            case R.id.tv_yesterday:
                init_title();
                tv_yestoday.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(DateSelectUtil.getInstance().getYesterdayTime(simpleDateFormat));
                tv_endDate.setText(DateSelectUtil.getInstance().getYesterdayTime(simpleDateFormat));
                loadData();
                break;
            case R.id.tv_this_month:
                init_title();
                tv_month.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(DateSelectUtil.getInstance().getMonthsTime(simpleDateFormat));
                tv_endDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
                loadData();
                break;
            case R.id.tv_this_week:
                init_title();
                tv_week.setTextColor(getResources().getColor(R.color.tab_blue));
                tv_startDate.setText(DateSelectUtil.getInstance().getWeekTime(simpleDateFormat));
                tv_endDate.setText(DateSelectUtil.getInstance().getTodayDate(simpleDateFormat));
                loadData();
                break;
            case R.id.ly_start_date:
                ly_bottom.setVisibility(View.VISIBLE);
                DateSelectUtil.getInstance().selectDate(getActivity(),ly_bottom,tv_startDate,2,new DateSelectUtil.selectDateCallBack() {
                    @Override
                    public void dateselect(String date) {
                        ly_bottom.setVisibility(View.GONE);
                        if(DateSelectUtil.getInstance().judgeDate(date,tv_endDate.getText().toString())){
                            ToastUtil.showToast(getActivity(),"开始时间不得晚于结束时间");
                        }else {
                            tv_startDate.setText(date);
                            mList.clear();
                            loadData();
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
                        if(DateSelectUtil.getInstance().judgeDate(tv_startDate.getText().toString(),date)){
                            ToastUtil.showToast(getActivity(),"开始时间不得晚于结束时间");
                        }else {
                            tv_endDate.setText(date);
                           loadData();
                        }
                    }
                });
                break;
        }
    }

    /**
     * 选择时间变化，加载数据
     */
    private void loadData(){
        loadingDialog.show();
        mList.clear();
        page=1;
        getDeviceIncome(tv_startDate.getText().toString(),tv_endDate.getText().toString(),page+"");
    }

    private void init_title() {
        tv_week.setTextColor(getResources().getColor(R.color.text_black3));
        tv_today.setTextColor(getResources().getColor(R.color.text_black3));
        tv_yestoday.setTextColor(getResources().getColor(R.color.text_black3));
        tv_month.setTextColor(getResources().getColor(R.color.text_black3));
    }

    /**
     * 获取设备收益详情
     */
    private void getDeviceIncome(String start_tiem,String end_time,String page){
        new IncomeModle(getActivity()).PartnerGetDeviceIncomeList(start_tiem, end_time, page, new IncomeModle.PartenerDeviceIncomeSuccessCallback() {
            @Override
            public void Sucess(PartnerDeviceIncomeListBean bean) {
                loadingDialog.dismiss();
                if(bean!=null&&bean.getData()!=null&&bean.getData().size()>0){
                    mList.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                    if(bean.getData().size()>=10){
                        needLoad=true;
                    }else {
                        needLoad=false;
                    }
                }
                //判断获取数据是否为空
                if(mList.size()<=0){
                    tv_empty.setVisibility(View.VISIBLE);
                }else {
                    tv_empty.setVisibility(View.GONE);
                }
                if(isLoad){
                    mRefreshLayout.endLoadingMore();
                }else {
                    mRefreshLayout.endRefreshing();
                }
            }

            @Override
            public void Faile() {
                loadingDialog.dismiss();
                if(isLoad){
                    mRefreshLayout.endLoadingMore();
                }else {
                    mRefreshLayout.endRefreshing();
                }
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        mList.clear();
        getDeviceIncome(tv_startDate.getText().toString(),tv_endDate.getText().toString(),page+"");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            getDeviceIncome(tv_startDate.getText().toString(),tv_endDate.getText().toString(),page+"");
            return true;
        } else {
            return false;
        }
    }
}

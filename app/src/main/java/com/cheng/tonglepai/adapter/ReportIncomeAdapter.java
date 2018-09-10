package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.RefereeListData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/9/10.
 */

public class ReportIncomeAdapter extends BaseAdapter {
    private Context context;
    private List<RefereeListData.DataBean> mData = new ArrayList<>();
    private String screen;
    private boolean isCity;

    public ReportIncomeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RefereeListData.DataBean> data, String screen,boolean isCity) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;
            this.isCity = isCity;
            this.screen = screen;
        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<RefereeListData.DataBean> data, String screen,boolean isCity) {
        this.screen = screen;
        this.isCity = isCity;
        if (null == data || data.size() == 0 || mData == null)
            return;
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.view_item_report_income, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_contact);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvNums = (TextView) convertView.findViewById(R.id.tv_nums);
            holder.cityAddress = (TextView) convertView.findViewById(R.id.city_address);
            holder.cityNum = (TextView) convertView.findViewById(R.id.city_num);
            holder.cityMoney = (TextView) convertView.findViewById(R.id.tv_money_city);
            holder.ivPhone = (ImageView) convertView.findViewById(R.id.iv_contact);
            holder.llMoneyDate = (LinearLayout) convertView.findViewById(R.id.ll_money_date);
            holder.llCity = (RelativeLayout) convertView.findViewById(R.id.ll_city);
            holder.llNotCity = (LinearLayout) convertView.findViewById(R.id.ll_not_city);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final RefereeListData.DataBean data = mData.get(position);
        if(isCity){
            holder.ivPhone.setVisibility(View.GONE);
        }else{
            holder.ivPhone.setVisibility(View.VISIBLE);
        }
        if (screen.equals("1")) {
            holder.llNotCity.setVisibility(View.VISIBLE);
            holder.llCity.setVisibility(View.GONE);
            holder.tvName.setVisibility(View.VISIBLE);
            holder.ivPhone.setVisibility(View.VISIBLE);
            holder.tvNums.setVisibility(View.GONE);
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvMoney.setVisibility(View.VISIBLE);

            holder.tvName.setText(data.getCity() + "    " + data.getName());
            if (data.getLevel().equals("0")) {
                holder.tvType.setText("银牌合伙人");
            }
            if (data.getLevel().equals("1")) {
                holder.tvType.setText("金牌合伙人");
            }
            if (data.getLevel().equals("2")) {
                holder.tvType.setText("区县合伙人");
            }
            if (data.getLevel().equals("3")) {
                holder.tvType.setText("城市合伙人");
            }

            holder.tvDate.setText(TimeUtil.alltimes(data.getCreated()));
            holder.tvMoney.setText("+￥" + data.getPrice());
            holder.ivPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onPhone(data.getTel());
                    }
                }
            });
        }else if(screen.equals("2")){
            if(isCity){
                holder.llCity.setVisibility(View.VISIBLE);
                holder.llNotCity.setVisibility(View.GONE);
                holder.cityAddress.setText(data.getDetails());
                holder.cityNum.setText(data.getDevice_nums()+"台");
                holder.cityMoney.setText("+￥" + data.getPrice());
            }else {
                holder.llNotCity.setVisibility(View.VISIBLE);
                holder.llCity.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.ivPhone.setVisibility(View.GONE);
                holder.tvNums.setVisibility(View.VISIBLE);
                holder.tvDate.setVisibility(View.GONE);
                holder.tvMoney.setVisibility(View.VISIBLE);
                holder.tvType.setText("    " + data.getName());
                holder.tvNums.setText(data.getDevice_nums() + "台");
                holder.tvMoney.setText("+￥" + data.getPrice());
            }
        }

        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvName, tvType, tvMoney, tvDate,tvNums;
        private TextView cityAddress, cityNum, cityMoney;
        private ImageView ivPhone;
        private LinearLayout llMoneyDate,llNotCity;
        private RelativeLayout llCity;
    }

    private IReportIncomeListener mListener;

    public void setOnReportIncomeListener(IReportIncomeListener listener) {
        this.mListener = listener;
    }

    public interface IReportIncomeListener {
        void onPhone(String tel);
    }

}

package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.PartnerdesData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class FreightListDetailAdapter extends BaseAdapter {
    private Context context;
    private List<PartnerdesData> mData = new ArrayList<>();

    public FreightListDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PartnerdesData> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<PartnerdesData> data) {
        if (null == data || data.size() == 0 || mData == null)
            return;
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
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
            convertView = View.inflate(context, R.layout.view_freight_detail, null);
            holder = new ViewHolder();
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvMonth = (TextView) convertView.findViewById(R.id.tv_month_show);
            holder.tvMonthDay = (TextView) convertView.findViewById(R.id.tv_month_day);
            holder.tvWeekDay = (TextView) convertView.findViewById(R.id.tv_week_day);
            holder.rlShowDetail = (RelativeLayout) convertView.findViewById(R.id.ll_item_apply);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.rlShowDetail.setVisibility(View.VISIBLE);
            holder.tvMonth.setText(mData.get(position).getMonth());
        } else {
            if (mData.size() > 1) {
                if (mData.get(position).getMonth().equals(mData.get(position - 1).getMonth())) {
                    holder.rlShowDetail.setVisibility(View.GONE);
                } else {
                    holder.rlShowDetail.setVisibility(View.VISIBLE);
                    holder.tvMonth.setText(mData.get(position).getMonth());
                }
            }
        }
        holder.tvPrice.setText("+" + mData.get(position).getPrice());
        holder.tvDate.setText(mData.get(position).getStore_name()); //商家名字
        holder.tvWeekDay.setText(mData.get(position).getWeek());
        if (!TextUtils.isEmpty(mData.get(position).getUpdated())) {
            holder.tvMonthDay.setText(TimeUtil.alltimes(mData.get(position).getUpdated()));
        }
        return convertView;
    }


    class ViewHolder {
        private TextView tvPrice, tvDate, tvMonth, tvMonthDay, tvWeekDay;
        private RelativeLayout rlShowDetail;
    }
}

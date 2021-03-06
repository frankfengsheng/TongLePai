package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.DeviceBillData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class IncomeDetailAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceBillData.DataBean> mData = new ArrayList<>();

    public IncomeDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DeviceBillData.DataBean> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<DeviceBillData.DataBean> data) {
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
            convertView = View.inflate(context, R.layout.view_item_income_detail, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_income_date);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_income_money);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_income_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeviceBillData.DataBean dataBean = mData.get(position);
        holder.tvPrice.setText(dataBean.getPrice());
        holder.tvDate.setText(dataBean.getUpdated());
        holder.tvType.setText(dataBean.getPay_source());
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvPrice, tvDate,tvType;
    }
}

package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.FieldIncomeActivity;
import com.cheng.tonglepai.data.InvestorDeviceListData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class InvestorDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<InvestorDeviceListData> mData = new ArrayList<>();

    public InvestorDeviceAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvestorDeviceListData> data) {
        if (null != data && data.size() > 0) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public void setLoadData(List<InvestorDeviceListData> data) {
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
            convertView = View.inflate(context, R.layout.view_item_device_invetor, null);
            holder = new ViewHolder();
            holder.tv_shopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_DeviceCount = (TextView) convertView.findViewById(R.id.tv_device_count);
            holder.tv_startTime = (TextView) convertView.findViewById(R.id.tv_start_time);
            holder.tv_yesterDayIncome = (TextView) convertView.findViewById(R.id.tv_yesterday_income);
            holder.tv_MonthIncome = (TextView) convertView.findViewById(R.id.tv_month_income);
            holder.tv_weizhi = (TextView) convertView.findViewById(R.id.tv_weizhi);
            holder.llDeviceList= (LinearLayout) convertView.findViewById(R.id.ly_device_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InvestorDeviceListData data = mData.get(position);
        holder.tv_startTime.setText("运行时间: " + data.getTime());
        holder.tv_shopName.setText(data.getName());
        holder.tv_DeviceCount.setText("设备："+data.getDevice_list());
        holder.tv_yesterDayIncome.setText("昨日收益："+data.getYesterday());
        holder.tv_MonthIncome.setText("本月累计："+data.getYesterday());
        holder.tv_weizhi.setText(data.getDetails());

        holder.llDeviceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FieldIncomeActivity.class);
                intent.putExtra(FieldIncomeActivity.FIELD_ID, data.getId());
                intent.putExtra(FieldIncomeActivity.TYPE, 1);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tv_shopName,tv_DeviceCount,tv_yesterDayIncome,tv_MonthIncome,tv_startTime,tv_weizhi;
        private LinearLayout llDeviceList;
    }
}

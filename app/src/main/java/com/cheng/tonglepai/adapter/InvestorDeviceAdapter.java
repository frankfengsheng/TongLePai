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
            holder.tvDeviceNum = (TextView) convertView.findViewById(R.id.tv_device_num);
            holder.tvDeviceTime = (TextView) convertView.findViewById(R.id.tv_device_time);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tvShopAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
            holder.tvIncomeYesterday = (TextView) convertView.findViewById(R.id.tv_yesterday_income);
            holder.tvMonthIncome = (TextView) convertView.findViewById(R.id.tv_month_income);
            holder.llDeviceList = (LinearLayout) convertView.findViewById(R.id.ll_investor_device);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InvestorDeviceListData data = mData.get(position);
        holder.tvDeviceTime.setText("起投时间 " + TimeUtil.times(data.getTime().replace("年",".").replace("月","")));
        holder.tvShopName.setText(data.getName());
        holder.tvShopAddress.setText(data.getDetails());
        holder.tvDeviceNum.setText(data.getDevice_list()+"台");
        holder.tvIncomeYesterday.setText(data.getYesterday());
        holder.tvMonthIncome.setText(data.getThismonth());

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
        private TextView tvDeviceNum, tvDeviceTime, tvShopName, tvShopAddress, tvIncomeYesterday, tvMonthIncome;
        private LinearLayout llDeviceList;
    }
}

package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.IncomeDetailActivity;
import com.cheng.tonglepai.data.DevicePriceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/9/10.
 */

public class DevicePriceAdapter extends BaseAdapter {
    private Context context;
    private List<DevicePriceData.DataBean> mData = new ArrayList<>();
    private String details,storeName,year,month,id;

    public DevicePriceAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DevicePriceData.DataBean> data,String details,String storeName,String year,String month,String id) {
        mData.clear();
        this.details = details;
        this.year = year;
        this.month = month;
        this.id = id;
        this.storeName = storeName;
        if (null != data && data.size() > 0) {
            this.mData = data;
        }
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
            convertView = View.inflate(context, R.layout.view_item_device_price, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_price_name);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_price_num);
            holder.rlItem = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DevicePriceData.DataBean data = mData.get(position);
        holder.tvName.setText(data.getDevice_name());
        holder.tvMoney.setText("￥"+data.getPrice());
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IncomeDetailActivity.class);
                intent.putExtra(IncomeDetailActivity.DEVICE_ID,data.getStore_device_id());
                intent.putExtra(IncomeDetailActivity.ID,id);
                intent.putExtra(IncomeDetailActivity.INCOME_ADDRESS,details);
                intent.putExtra(IncomeDetailActivity.INCOME_STORE_NAME,storeName);
                intent.putExtra(IncomeDetailActivity.DEVICE_NAME,data.getDevice_name());
                intent.putExtra(IncomeDetailActivity.YEAR,year);
                intent.putExtra(IncomeDetailActivity.MONTH,month);
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
        private TextView tvName, tvMoney;
        private RelativeLayout rlItem;
    }

}

package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.DeviceIncomeDetailBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.QRCodingActivity;
import com.cheng.tonglepai.data.BindDeviceData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class DeviceIncomeListAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceIncomeDetailBean.DataBean> mData = new ArrayList<>();

    public DeviceIncomeListAdapter(Context context,List<DeviceIncomeDetailBean.DataBean> mList) {
        this.context = context;
        this.mData=mList;
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
            convertView = View.inflate(context, R.layout.item_device_income_list, null);
            holder = new ViewHolder();
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_device_income_time);
            holder.tv_income= (TextView) convertView.findViewById(R.id.tv_device_income);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(position).getUpdated()))holder.tv_time.setText( TimeUtil.allTimeNew(mData.get(position).getUpdated()));
        //0扫码 1投币
        if(!TextUtils.isEmpty(mData.get(position).getPay_source())&&mData.get(position).getPay_source().equals("0")){
            if(!TextUtils.isEmpty(mData.get(position).getPrice())) holder.tv_income.setText("扫码+"+mData.get(position).getPrice());
        }else{
            if(!TextUtils.isEmpty(mData.get(position).getPrice())) holder.tv_income.setText("投币+"+mData.get(position).getPrice());
        }

        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
       private TextView tv_time;
       private TextView tv_income;
    }
}

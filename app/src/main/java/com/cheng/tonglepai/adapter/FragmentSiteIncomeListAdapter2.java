package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.DeviceIncomeDetailBean;
import com.cheng.retrofit20.bean.SiteFileIncomeListBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class FragmentSiteIncomeListAdapter2 extends BaseAdapter {
    private Context context;
    private List<SiteFileIncomeListBean.DataBean> mData = new ArrayList<>();

    public FragmentSiteIncomeListAdapter2(Context context, List<SiteFileIncomeListBean.DataBean> mList) {
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
            convertView = View.inflate(context, R.layout.view_item_file_income, null);
            holder=new ViewHolder();
            holder.tv_siteName= (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_detail= (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tv_deviceName= (TextView) convertView.findViewById(R.id.tv_device_count);
            holder.tv_deviceCode= (TextView) convertView.findViewById(R.id.tv_device_code);
            holder.tv_toubiIncome= (TextView) convertView.findViewById(R.id.tv_toubi_income);
            holder.tv_saomaIncome= (TextView) convertView.findViewById(R.id.tv_saoma_income);
            holder.tv_totalIncome= (TextView) convertView.findViewById(R.id.tv_daijiao_count);
            holder.tv_weizhi= (TextView) convertView.findViewById(R.id.tv_weizhi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(mData.get(position).getStore_name()))  holder.tv_siteName.setText(mData.get(position).getStore_name());
        if(!TextUtils.isEmpty(mData.get(position).getDevice_name()))  holder.tv_deviceName.setText(mData.get(position).getDevice_name());
        if(!TextUtils.isEmpty(mData.get(position).getDevice_code()))  holder.tv_deviceCode.setText("通讯码："+mData.get(position).getDevice_code());
        if(!TextUtils.isEmpty(mData.get(position).getTb_price()))  holder.tv_toubiIncome.setText("￥"+mData.get(position).getTb_price());
        if(!TextUtils.isEmpty(mData.get(position).getSm_price()))  holder.tv_saomaIncome.setText("￥"+mData.get(position).getSm_price());
        if(!TextUtils.isEmpty(mData.get(position).getPrice()))     holder.tv_totalIncome.setText("￥"+mData.get(position).getPrice());
        if(!TextUtils.isEmpty(mData.get(position).getDetails()))  holder.tv_weizhi.setText(mData.get(position).getDetails());

        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tv_siteName;
        private TextView tv_detail;
        private TextView tv_deviceName;
        private TextView tv_deviceCode;
        private TextView tv_toubiIncome;
        private TextView tv_saomaIncome;
        private TextView tv_totalIncome;
        private TextView tv_weizhi;
        private LinearLayout ly_item;
    }
}

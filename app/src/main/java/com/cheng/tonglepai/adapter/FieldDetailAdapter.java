package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.InvestorChooseCheckData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class FieldDetailAdapter extends BaseAdapter {
    private Context context;
    private List<InvestorChooseCheckData.DataBean> mData = new ArrayList<>();
    ViewHolder holder = null;

    public FieldDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvestorChooseCheckData.DataBean> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<InvestorChooseCheckData.DataBean> data) {
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

        if (null == convertView) {
            convertView = View.inflate(context, R.layout.view_field_list, null);
            holder = new ViewHolder();
            holder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
            holder.deviceNum = (TextView) convertView.findViewById(R.id.device_num);
            holder.ivDevicePic = (ImageView) convertView.findViewById(R.id.device_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InvestorChooseCheckData.DataBean dataBean = mData.get(position);
        holder.deviceName.setText(dataBean.getDevice_name());
        holder.deviceNum.setText(dataBean.getNums() + "台");

        MyBitmapUtil myBitmapUtil = new MyBitmapUtil(context,dataBean.getImg());
        myBitmapUtil.display(dataBean.getImg(),holder.ivDevicePic);
        return convertView;
    }


    class ViewHolder {
        private TextView deviceName, deviceNum;
        private ImageView ivDevicePic;
    }

}

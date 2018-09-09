package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.ReportRecordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ReportRecordAdapter extends BaseAdapter {
    private Context context;
    private List<ReportRecordData> mData = new ArrayList<>();

    public ReportRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReportRecordData> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<ReportRecordData> data) {
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
            convertView = View.inflate(context, R.layout.view_apply_detail, null);
            holder = new ViewHolder();
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_month_show);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
//            holder.tvMonth = (TextView) convertView.findViewById(R.id.tv_month_show);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.apply_status);
//            holder.rlShowDetail = (RelativeLayout) convertView.findViewById(R.id.ll_item_apply);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvPrice, tvDate, tvMonth, tvStatus;
        private RelativeLayout rlShowDetail;
    }
}

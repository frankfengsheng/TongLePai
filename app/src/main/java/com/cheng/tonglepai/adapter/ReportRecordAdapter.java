package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.ReportRecordData;
import com.cheng.tonglepai.tool.TimeUtil;

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
            convertView = View.inflate(context, R.layout.view_report_record, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_report_name);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_report_phone);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_report_time);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_report_address);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_report_status);
            holder.tvRemark = (TextView) convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReportRecordData data = mData.get(position);
        holder.tvName.setText(data.getName());
        holder.tvPhone.setText(data.getTel());
        holder.tvTime.setText(TimeUtil.alltimes(data.getUpdated()));
        holder.tvAddress.setText(data.getCity());
        if (data.getStatus().equals("0")) {
            holder.tvStatus.setText("待加入");
            holder.tvStatus.setTextColor(Color.parseColor("#ffffff"));
            holder.tvStatus.setBackgroundResource(R.drawable.orange_field_shape);
        }
        if (data.getStatus().equals("1")) {
            holder.tvStatus.setText("成功加入");
            holder.tvStatus.setTextColor(Color.parseColor("#ffffff"));
            holder.tvStatus.setBackgroundResource(R.drawable.green_field_shape);
        }
        if (data.getStatus().equals("2")) {
            holder.tvStatus.setText("报备过期");
            holder.tvStatus.setTextColor(Color.parseColor("#ffffff"));
            holder.tvStatus.setBackgroundResource(R.drawable.red_field_shape_new);
        }
        holder.tvRemark.setText("备注：" + data.getRemarks());


        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvName, tvPhone, tvTime, tvAddress, tvStatus, tvRemark;
    }
}

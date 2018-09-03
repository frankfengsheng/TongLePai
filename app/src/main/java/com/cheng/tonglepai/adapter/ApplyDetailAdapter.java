package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.ApplyDetailUseData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ApplyDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ApplyDetailUseData> mData = new ArrayList<>();

    public ApplyDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ApplyDetailUseData> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<ApplyDetailUseData> data) {
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
//        if (position == 0) {
//            holder.rlShowDetail.setVisibility(View.VISIBLE);
//            holder.tvMonth.setText(mData.get(position).getMonth());
//        } else {
//            if (mData.size() > 1) {
//                if (mData.get(position).getMonth().equals(mData.get(position - 1).getMonth())) {
//                    holder.rlShowDetail.setVisibility(View.GONE);
//                } else {
//                    holder.rlShowDetail.setVisibility(View.VISIBLE);
//                    holder.tvMonth.setText(mData.get(position).getMonth());
//                }
//            }
//        }
        holder.tvPrice.setText("-" + mData.get(position).getPrice());
        holder.tvDate.setText(TimeUtil.allTime(mData.get(position).getUpdated()));
        if (mData.get(position).getStatus().equals("0")) {
            holder.tvStatus.setText("申请中");
        } else if (mData.get(position).getStatus().equals("1")) {
            holder.tvStatus.setText("已结算");
        } else if (mData.get(position).getStatus().equals("2")) {
            holder.tvStatus.setText("失败");
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

package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.PostMoneyRecordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/9/10.
 */

public class PostMoneyRecordAdapter extends BaseAdapter {
    private Context context;
    private List<PostMoneyRecordData.DataBean> mData = new ArrayList<>();

    public PostMoneyRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PostMoneyRecordData.DataBean> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;
        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<PostMoneyRecordData.DataBean> data) {
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
            convertView = View.inflate(context, R.layout.view_item_postmoney_record, null);
            holder = new ViewHolder();
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_record_time);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_record_type);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_record_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PostMoneyRecordData.DataBean data = mData.get(position);
        holder.tvTime.setText(data.getUpdated());
        if ("1".equals(data.getPay_type()))
            holder.tvType.setText("转账上缴");
        else if ("0".equals(data.getPay_type()))
            holder.tvType.setText("余额上缴");
        holder.tvMoney.setText("￥" + data.getPrice());
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvTime, tvType, tvMoney;
    }

}

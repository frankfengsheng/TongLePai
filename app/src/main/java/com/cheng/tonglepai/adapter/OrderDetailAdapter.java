package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.CheckokRecordsData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class OrderDetailAdapter extends BaseAdapter {
    private Context context;
    private List<CheckokRecordsData.RecordsBean> mData = new ArrayList<>();

    public OrderDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CheckokRecordsData.RecordsBean> data) {
        mData.clear();
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
            convertView = View.inflate(context, R.layout.view_item_order_status, null);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_status_date);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_status_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CheckokRecordsData.RecordsBean dataBean = mData.get(position);
        holder.tvDate.setText(TimeUtil.alltimes(dataBean.getCreated()));
        String statusName = "";
        if("1".equals(dataBean.getStatus())){
            statusName = "提交订单";
        }
        if("2".equals(dataBean.getStatus())){
            statusName = "配件组装";
        }
        if("3".equals(dataBean.getStatus())){
            statusName = "已出仓";
        }
        if("4".equals(dataBean.getStatus())){
            statusName = "发货";
        }
        if("5".equals(dataBean.getStatus())){
            statusName = "运输中";
        }
        if("6".equals(dataBean.getStatus())){
            statusName = "已签收";
        }
        if("7".equals(dataBean.getStatus())){
            statusName = "安装调试";
        }
        if("8".equals(dataBean.getStatus())){
            statusName = "运营";
        }
        holder.tvName.setText(statusName);
        return convertView;
    }

    class ViewHolder {
        private TextView tvDate, tvName;
    }
}

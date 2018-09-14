package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.OrderStatusActivity;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.CheckokInfoData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.List;

/**
 * Created by cheng on 2018/6/7.
 */

public class OrderStatusAdapter extends BaseAdapter {
    private Context context;
    private List<CheckokInfoData> mDataList;

    public OrderStatusAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CheckokInfoData> list) {
        this.mDataList = list;
        notifyDataSetChanged();
    }

    public void setLoadData(List<CheckokInfoData> data) {
        if (null == data || data.size() == 0 || mDataList == null)
            return;
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final CheckokInfoData data = mDataList.get(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
            holder = new ViewHolder();
            holder.btnToSee = (TextView) convertView.findViewById(R.id.tv_to_see_detail);
            holder.orderId = (TextView) convertView.findViewById(R.id.tv_order_id_show);
            holder.orderTime = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.orderNum = (TextView) convertView.findViewById(R.id.tv_conut);
            holder.orderPrice = (TextView) convertView.findViewById(R.id.all_money);
            holder.orderOnePrice = (TextView) convertView.findViewById(R.id.one_price);
            holder.orderYunPrice = (TextView) convertView.findViewById(R.id.other_money);
            holder.ivOrderPhoto = (ImageView) convertView.findViewById(R.id.iv_order_photo);
            holder.orderNo = (TextView) convertView.findViewById(R.id.tv_all_no);
            holder.llOrderStatus = (LinearLayout) convertView.findViewById(R.id.ll_item_order_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.orderId.setText(data.getOrder_number());
        holder.orderTime.setText(TimeUtil.allTime(data.getUpdated()));
        if (!TextUtils.isEmpty(data.getImg())) {
            MyBitmapUtil myBitmapUtil = new MyBitmapUtil(context, data.getImg());
            myBitmapUtil.display(data.getOrder_number(), holder.ivOrderPhoto);
        }
        holder.orderNo.setText("共"+data.getDevice_list()+"件");
        holder.orderNum.setText("x"+data.getDevice_list());
        holder.orderOnePrice.setText("￥"+data.getBetting_fee());
        holder.orderPrice.setText(data.getPrice());
        holder.orderYunPrice.setText("含运费￥("+data.getShipping_fee()+")");

        holder.llOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrderStatusActivity.class);
                intent.putExtra(OrderStatusActivity.ORDER_NO,data.getOrder_number());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private TextView orderId, orderNum, orderPrice, orderOnePrice, orderYunPrice, btnToSee, orderTime,orderNo;
        private ImageView ivOrderPhoto;
        private LinearLayout llOrderStatus;
    }

}
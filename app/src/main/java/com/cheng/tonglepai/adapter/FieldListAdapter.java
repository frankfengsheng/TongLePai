package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.FieldIncomeActivity;
import com.cheng.tonglepai.data.FieldListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class FieldListAdapter extends BaseAdapter {
    private Context context;
    private List<FieldListData> mData = new ArrayList<>();

    public FieldListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FieldListData> data) {
        if (null != data && data.size() > 0) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public void setLoadData(List<FieldListData> data) {
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
            convertView = View.inflate(context, R.layout.view_item_field_list, null);
            holder = new ViewHolder();
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tvShopAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
            holder.tvToSeeIncome = (TextView) convertView.findViewById(R.id.btn_to_see_income);
            holder.tvNeedNum = (TextView) convertView.findViewById(R.id.tv_post_num);
            holder.tvPostNum = (TextView) convertView.findViewById(R.id.tv_need_num);
            holder.tvTransfer = (TextView) convertView.findViewById(R.id.btn_to_transfer);
            holder.llFieldList = (LinearLayout) convertView.findViewById(R.id.item_field_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FieldListData data = mData.get(position);

        holder.tvToSeeIncome.setVisibility(View.GONE);
        holder.tvTransfer.setVisibility(View.GONE);
        holder.tvShopName.setText(data.getStore_name()+"");
        holder.tvShopAddress.setText(data.getDetails()+"");
        if(data.getNums().equals("0")){
            holder.tvNeedNum.setText("未投");
            holder.tvNeedNum.setTextColor(Color.parseColor("#FF1932"));
        }else{
            holder.tvNeedNum.setText("已投");
            holder.tvNeedNum.setTextColor(Color.parseColor("#33CC66"));
        }
        holder.tvPostNum.setText(data.getStart_nums()+"台");

        holder.llFieldList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FieldIncomeActivity.class);
                intent.putExtra(FieldIncomeActivity.FIELD_ID,data.getId());
                intent.putExtra("type",2);
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
        private TextView tvShopName, tvShopAddress, tvToSeeIncome,tvNeedNum,tvPostNum,tvTransfer;
        private LinearLayout llFieldList;
    }
}

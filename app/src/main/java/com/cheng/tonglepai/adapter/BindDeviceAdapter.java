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
import com.cheng.tonglepai.activity.QRCodingActivity;
import com.cheng.tonglepai.data.BindDeviceData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class BindDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<BindDeviceData> mData = new ArrayList<>();

    public BindDeviceAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BindDeviceData> data) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;

        }
        notifyDataSetChanged();
    }

    public void

    setLoadData(List<BindDeviceData> data) {
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
            convertView = View.inflate(context, R.layout.view_bind_device, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_detail_address);
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_device_id);
            holder.tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
            holder.tvData = (TextView) convertView.findViewById(R.id.tv_device_time);
            holder.tvDeviceCode = (TextView) convertView.findViewById(R.id.tv_device_code);
            holder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_item_bind);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BindDeviceData data = mData.get(position);
        if (position == 0) {
            holder.llItem.setVisibility(View.VISIBLE);
        } else {
            if (mData.size() > 1) {
                if (mData.get(position).getInfo_id().equals(mData.get(position - 1).getInfo_id())) {
                    holder.llItem.setVisibility(View.GONE);
                } else {
                    holder.llItem.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.tvAddress.setText(data.getDetails());
        holder.tvData.setText(TimeUtil.alltimes(data.getCreated()));
        holder.tvId.setText(data.getDevice_id());
        holder.tvDeviceName.setText(data.getDevice_name());
        holder.tvName.setText(data.getStore_name());
        if (data.getQrcode().equals("0")) {
            holder.tvDeviceCode.setText("绑定  >");
            holder.tvDeviceCode.setTextColor(Color.parseColor("#45a7fe"));
        } else {
            holder.tvDeviceCode.setText("已绑定");
            holder.tvDeviceCode.setTextColor(Color.parseColor("#888888"));
        }
        holder.tvDeviceCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQrcode().equals("0")) {
                    Intent intent = new Intent(context, QRCodingActivity.class);
                    intent.putExtra("device.id", data.getDevice_id());
                    intent.putExtra("info.id", data.getInfo_id());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvName, tvAddress, tvId, tvDeviceName, tvData, tvDeviceCode;
        private LinearLayout llItem;
    }
}

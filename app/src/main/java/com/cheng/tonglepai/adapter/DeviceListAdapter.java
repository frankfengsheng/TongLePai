package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.DeviceListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class DeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceListData> mData = new ArrayList<>();

    public DeviceListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DeviceListData> data) {
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
            convertView = View.inflate(context, R.layout.view_item_post_device, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_device);
            holder.tvNoCount = (TextView) convertView.findViewById(R.id.tv_num_content);
            holder.ivDevice = (ImageView) convertView.findViewById(R.id.iv_device);
            holder.ivNoReduce = (ImageView) convertView.findViewById(R.id.iv_number_reduce);
            holder.ivNoAdd = (ImageView) convertView.findViewById(R.id.iv_number_add);
            holder.tvPrice= (TextView) convertView.findViewById(R.id.tv_equipment_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DeviceListData data = mData.get(position);
        if (!TextUtils.isEmpty(data.getImg())) {
            MyBitmapUtil myBitmapUtil = new MyBitmapUtil(context, data.getImg());
            myBitmapUtil.display(data.getImg(), holder.ivDevice);
        }
        holder.tvName.setText(data.getDevice_name());
        holder.tvPrice.setText("￥ "+data.getPrice_purchase());
        final int allNo = data.getShowNO();
        holder.tvNoCount.setText(data.getShowNO() + "");
        holder.ivNoReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getShowNO() == 0) {
                    Toast.makeText(context, "已经到了最小值", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    data.setShowNO(allNo - 1);
                    deviceListListener.reduceNo(position);
                    notifyDataSetChanged();
                }
            }
        });
        holder.ivNoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setShowNO(allNo + 1);
                deviceListListener.addNo(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private DeviceListListener deviceListListener;

    public void setOnIPostPackageNoListener(DeviceListListener deviceListListener) {
        this.deviceListListener = deviceListListener;
    }

    public interface DeviceListListener {
        void reduceNo(int position);

        void addNo(int position);
    }

    class ViewHolder {
        private TextView tvName, tvNoCount,tvPrice;
        private ImageView ivDevice, ivNoReduce, ivNoAdd;
    }
}

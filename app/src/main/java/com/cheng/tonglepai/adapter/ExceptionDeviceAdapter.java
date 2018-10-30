package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.DeviceExceptionPostActivity;
import com.cheng.tonglepai.data.ExceptionDeviceData;
import com.cheng.tonglepai.net.FieldRepairRequest;
import com.cheng.tonglepai.net.NeedRepairRequest;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ExceptionDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<ExceptionDeviceData> mData = new ArrayList<>();
    Btnclick btnclick;
    public ExceptionDeviceAdapter(Context context) {
        this.context = context;
    }
    public void setBtnclick(Btnclick btnclick){
        this.btnclick=btnclick;
    }
    public List<ExceptionDeviceData> getData(){
        return mData;
    }

    public void setData(List<ExceptionDeviceData> data) {
        if (null != data && data.size() > 0) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public void setLoadData(List<ExceptionDeviceData> data) {
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
            convertView = View.inflate(context, R.layout.view_item_device_manage, null);
            holder = new ViewHolder();
            holder.tvDeviceNo = (TextView) convertView.findViewById(R.id.tv_device_id);
            holder.tvDeviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
            holder.tvDeviceTime = (TextView) convertView.findViewById(R.id.tv_device_time);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tvShopAddress = (TextView) convertView.findViewById(R.id.tv_device_address);
            holder.btnState = (TextView) convertView.findViewById(R.id.btn_type);
            holder.btn_detection= (Button) convertView.findViewById(R.id.btn_detection);
            holder.btn_repairs= (Button) convertView.findViewById(R.id.btn_repairs);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final ExceptionDeviceData data = mData.get(position);
        holder.tvDeviceName.setText(data.getDevice_name());
        holder.tvDeviceNo.setText(data.getDevice_code());
        if (!TextUtils.isEmpty(data.getCreated()))
            holder.tvDeviceTime.setText(TimeUtil.alltimes(data.getCreated()));
        holder.tvShopName.setText(data.getStore_name());
        holder.tvDeviceName.setText(data.getDevice_name());
        holder.tvShopAddress.setText(data.getDetails());
        if(data.getStatus()==1){
            holder.btnState.setVisibility(View.VISIBLE);
        }else{
            holder.btnState.setVisibility(View.GONE);
        }
        holder.btn_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnclick!=null)btnclick.detection(position);
            }
        });
        holder.btn_repairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HttpConfig.newInstance(context).getUserType() == 2) {
                    NeedRepairRequest mRequest = new NeedRepairRequest(context);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult mdata) {
                            Intent intent = new Intent(context, DeviceExceptionPostActivity.class);
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_CODE, data.getDevice_code());
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_NAME, data.getDevice_name());
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_ID, data.getId());
                            intent.putExtra(DeviceExceptionPostActivity.DETAILS, data.getDetails());
                            context.startActivity(intent);
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        }
                    });

                    mRequest.requestNeedRepair("", data.getDevice_code());
                } else if (HttpConfig.newInstance(context).getUserType() == 3) {
                    FieldRepairRequest mRequest = new FieldRepairRequest(context);
                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult mdata) {
                            Intent intent = new Intent(context, DeviceExceptionPostActivity.class);
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_CODE, data.getDevice_code());
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_NAME, data.getDevice_name());
                            intent.putExtra(DeviceExceptionPostActivity.DEVICE_ID, data.getId());
                            intent.putExtra(DeviceExceptionPostActivity.DETAILS, data.getDetails());
                            context.startActivity(intent);
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        }
                    });

                    mRequest.requestFieldNeedRepair(data.getDevice_code());
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
        private TextView tvDeviceNo, tvDeviceName, tvDeviceTime, tvShopName, tvShopAddress;
        private TextView btnState;
        private Button btn_detection,btn_repairs;
    }

    public interface Btnclick{
        void detection(int position);
        void repairs(int position);
    }
}

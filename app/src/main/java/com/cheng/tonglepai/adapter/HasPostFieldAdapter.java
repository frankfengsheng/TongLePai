package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.PayOnlineActivity;
import com.cheng.tonglepai.activity.PostFieldResultActivity;
import com.cheng.tonglepai.data.HasPostFieldData;
import com.cheng.tonglepai.data.OrderNoData;
import com.cheng.tonglepai.net.FreigthOrderNoRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class HasPostFieldAdapter extends BaseAdapter {
    private Context context;
    private List<HasPostFieldData> mData = new ArrayList<>();
    private boolean checked;

    public HasPostFieldAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HasPostFieldData> data, boolean isChecked) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;
            this.checked = isChecked;
        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<HasPostFieldData> data, boolean isChecked) {
        if (null == data || data.size() == 0 || mData == null)
            return;
        mData.addAll(data);
        this.checked = isChecked;
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
            convertView = View.inflate(context, R.layout.view_item_has_post_field, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_field_name);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_field_address);
            holder.btnState = (Button) convertView.findViewById(R.id.btn_state);
            holder.rlListField = (RelativeLayout) convertView.findViewById(R.id.rl_field_list);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mData.get(position).getStore_name() + "");
        holder.tvAddress.setText(mData.get(position).getDetails() + "");
        if (!TextUtils.isEmpty(mData.get(position).getStatus())) {
            if (mData.get(position).getStatus().equals("0")) {
                holder.btnState.setText("审核中");
                holder.btnState.setTextColor(Color.parseColor("#ffffff"));
                holder.btnState.setBackgroundResource(R.drawable.feiqi_field_shape);
            } else if (mData.get(position).getStatus().equals("1")) {
                if (checked){
                    holder.rlListField.setVisibility(View.GONE);
                }else {
                    holder.rlListField.setVisibility(View.VISIBLE);
                    holder.btnState.setText("缴纳运费");
                    holder.btnState.setTextColor(Color.parseColor("#ffffff"));
                    holder.btnState.setBackgroundResource(R.drawable.blue_soild_shape);
                }
            } else if (mData.get(position).getStatus().equals("2")) {
                holder.btnState.setText("审核失败");
                holder.btnState.setTextColor(Color.parseColor("#ffffff"));
                holder.btnState.setBackgroundResource(R.drawable.red_field_shape);
            } else if (mData.get(position).getStatus().equals("3")) {
                holder.btnState.setText("场地废弃");
                holder.btnState.setTextColor(Color.parseColor("#ffffff"));
                holder.btnState.setBackgroundResource(R.drawable.ing_field_shape);
            } else if (mData.get(position).getStatus().equals("4")) {
                holder.btnState.setText("缴费完成");
                holder.btnState.setTextColor(Color.parseColor("#ffffff"));
                holder.btnState.setBackgroundResource(R.drawable.yunfei_field_shape);
            } else if (mData.get(position).getStatus().equals("5")) {
                holder.btnState.setText("正式上线");
                holder.btnState.setTextColor(Color.parseColor("#45a7fe"));
                holder.btnState.setBackgroundResource(R.drawable.blue_field_shape);
            }else if (mData.get(position).getStatus().equals("6")) {
                holder.btnState.setText("设备转移");
                holder.btnState.setTextColor(Color.parseColor("#45a7fe"));
                holder.btnState.setBackgroundResource(R.drawable.blue_field_shape);
            }
            holder.btnState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(position).getStatus().equals("2")) {
                        Intent intent = new Intent(context, PostFieldResultActivity.class);
                        intent.putExtra(PostFieldResultActivity.STORE_INFO_ID, mData.get(position).getId());
                        context.startActivity(intent);
                    } else if (mData.get(position).getStatus().equals("1")) {
                        FreigthOrderNoRequest mRequest = new FreigthOrderNoRequest(context);
                        mRequest.setListener(new BaseHttpRequest.IRequestListener<OrderNoData>() {
                            @Override
                            public void onSuccess(OrderNoData data) {
                                Intent intent = new Intent(context, PayOnlineActivity.class);
                                intent.putExtra(PayOnlineActivity.PAY_MONEY, data.getTotal());
                                intent.putExtra(PayOnlineActivity.PAY_ORDER_NO, data.getOrder_number());
                                intent.putExtra(PayOnlineActivity.PAY_ID, data.getId());
                                intent.putExtra(PayOnlineActivity.IS_FEIGHT, true);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailed(String msg, int code) {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        });
                        mRequest.requestFreigtOrderNo(mData.get(position).getId());
                    }
                }
            });
        }
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvName, tvAddress;
        private Button btnState;
        private RelativeLayout rlListField;
    }
}

package com.cheng.tonglepai.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.FiledDetailActivity;
import com.cheng.tonglepai.activity.PublicResultActivity;
import com.cheng.tonglepai.data.InvestorFieldListData;
import com.cheng.tonglepai.net.FieldTransferRequest;
import com.cheng.tonglepai.tool.MyChooseToastDialog;
import com.cheng.tonglepai.tool.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class InverstorFieldListAdapter extends BaseAdapter {
    private Context context;
    private List<InvestorFieldListData> mData = new ArrayList<>();
    private MyChooseToastDialog progressDialog;

    public InverstorFieldListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvestorFieldListData> data) {
        if (null != data && data.size() > 0) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public void setLoadData(List<InvestorFieldListData> data) {
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
            holder.tvNeedNum = (TextView) convertView.findViewById(R.id.tv_need_num);
            holder.tvPostNum = (TextView) convertView.findViewById(R.id.tv_post_num);
            holder.tvFieldType = (TextView) convertView.findViewById(R.id.tv_device_type);
            holder.tvToTransfer = (TextView) convertView.findViewById(R.id.btn_to_transfer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InvestorFieldListData data = mData.get(position);

        holder.tvShopName.setText(data.getName() + "");
        holder.tvShopAddress.setText(data.getDetails() + "");
        holder.tvNeedNum.setText(data.getNums() + "台");
        holder.tvPostNum.setText(data.getExpected_revenue() + "");
        holder.tvToSeeIncome.setVisibility(View.VISIBLE);
        holder.tvToSeeIncome.setText("投放");
        holder.tvToSeeIncome.setBackground(context.getResources().getDrawable(R.drawable.blue_new_field_shape));
        if (data.getStatus_data().equals("0")) {
            holder.tvToTransfer.setClickable(false);
            holder.tvToTransfer.setEnabled(false);
            holder.tvToTransfer.setVisibility(View.VISIBLE);
            holder.tvToTransfer.setText("迁入");
            holder.tvToTransfer.setBackground(context.getResources().getDrawable(R.drawable.gray_new_field_shape));
        } else {
            if (Integer.parseInt(data.getMigrate_num()) < Integer.parseInt(data.getNums())) {
                holder.tvToTransfer.setClickable(false);
                holder.tvToTransfer.setEnabled(false);
                holder.tvToTransfer.setVisibility(View.VISIBLE);
                holder.tvToTransfer.setText("迁入");
                holder.tvToTransfer.setBackground(context.getResources().getDrawable(R.drawable.gray_new_field_shape));
            } else {
                holder.tvToTransfer.setVisibility(View.VISIBLE);
                holder.tvToTransfer.setClickable(true);
                holder.tvToTransfer.setEnabled(true);
                holder.tvToTransfer.setBackground(context.getResources().getDrawable(R.drawable.yellow_field_shape));
                holder.tvToTransfer.setText("迁入");
            }
        }
        holder.tvFieldType.setText("预计收益/日");
        holder.tvToSeeIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FiledDetailActivity.class);
                intent.putExtra(FiledDetailActivity.FIELD_ID, data.getId());
                intent.putExtra(FiledDetailActivity.SHOP_NAME, data.getName());
                intent.putExtra(FiledDetailActivity.SHOP_ADDRESS, data.getDetails());
                context.startActivity(intent);
            }
        });

        holder.tvToTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = MyToast.showChooseDialog((Activity) context, "您确定将设备迁入到新的场地？", "",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                FieldTransferRequest mRequest = new FieldTransferRequest(context);
                                mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                                    @Override
                                    public void onSuccess(BaseHttpResult data) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(context, PublicResultActivity.class);
                                        intent.putExtra(PublicResultActivity.TYPE, 2);
                                        context.startActivity(intent);
                                    }

                                    @Override
                                    public void onFailed(String msg, int code) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mRequest.requestFieldTransfery(data.getId(), data.getNums(), data.getMigrate_num());
                            }
                        });

            }
        });
        return convertView;
    }


    class ViewHolder {
        private TextView tvShopName, tvShopAddress, tvToSeeIncome, tvNeedNum, tvPostNum, tvFieldType, tvToTransfer;

    }
}

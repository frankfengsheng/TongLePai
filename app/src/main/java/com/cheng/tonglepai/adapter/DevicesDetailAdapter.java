package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.DevicePriceActivity;
import com.cheng.tonglepai.data.InvestorAllIncomeData;
import com.cheng.tonglepai.view.ChartView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/6/28.
 */

public class DevicesDetailAdapter extends BaseAdapter {
    private Context context;
    private List<InvestorAllIncomeData.DataBean> mData = new ArrayList<>();
    private String year, month;

    public DevicesDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<InvestorAllIncomeData.DataBean> data, String year, String month) {
        mData.clear();
        if (null != data && data.size() > 0) {
            this.mData = data;
            this.year = year;
            this.month = month;
        }
        notifyDataSetChanged();
    }

    public void setLoadData(List<InvestorAllIncomeData.DataBean> data) {
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
            convertView = View.inflate(context, R.layout.view_profit_detail, null);
            holder = new ViewHolder();
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_all_money);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_detail_address);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_all_device);
            holder.tvIncomeDetail = (TextView) convertView.findViewById(R.id.tv_income_detail);
            holder.chartView = (ChartView) convertView.findViewById(R.id.chartview);
            holder.rlIncomeHead = (RelativeLayout) convertView.findViewById(R.id.rl_income_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InvestorAllIncomeData.DataBean dataBean = mData.get(position);
        holder.tvAddress.setText("地址：" + dataBean.getDetails());
        if (Double.parseDouble(dataBean.getMoney()) == 0) {
            holder.tvPrice.setText("总计： 0元");
        } else
            holder.tvPrice.setText("总计： " + dataBean.getMoney() + "元");

        holder.tvNum.setText(dataBean.getStore_name() + "   " + dataBean.getDevice_list() + "台");
        //x轴坐标对应的数据
        List<String> xValue = new ArrayList<>();
        //y轴坐标对应的数据
        List<Integer> yValue = new ArrayList<>();
        //折线对应的数据
        Map<String, Double> value = new HashMap<>();
        for (int i = 0; i < dataBean.getTj().size(); i++) {
            xValue.add((i + 1) + "号");
            value.put((i + 1) + "号", Double.parseDouble(dataBean.getTj().get(i).getPrice()));
        }

        double maxIndex = 0;//定义最大值为该数组的第一个数
        double minIndex = 0;//定义最小值为该数组的第一个数

        for (int i = 0; i < dataBean.getTj().size(); i++) {
            if (maxIndex < Double.parseDouble(dataBean.getTj().get(i).getPrice())) {
                maxIndex = Double.parseDouble(dataBean.getTj().get(i).getPrice());
            }

            if (minIndex > Double.parseDouble(dataBean.getTj().get(i).getPrice())) {
                minIndex = Double.parseDouble(dataBean.getTj().get(i).getPrice());
            }

        }
        int a = (int)Math.ceil(18.6 / 6);
        for (int i = 0; i  < 7; i++) {

            if (a == 0) {
                yValue.add(i * 500);
            } else
                yValue.add(i * a);
        }
        holder.chartView.setValue(value, xValue, yValue);

        holder.tvIncomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DevicePriceActivity.class);
                intent.putExtra(DevicePriceActivity.ID, (Serializable) dataBean.getId());
                intent.putExtra(DevicePriceActivity.YEAR, year);
                intent.putExtra(DevicePriceActivity.MONTH, month);
                context.startActivity(intent);
            }
        });
        holder.rlIncomeHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DevicePriceActivity.class);
                intent.putExtra(DevicePriceActivity.ID, (Serializable) dataBean.getId());
                intent.putExtra(DevicePriceActivity.YEAR, year);
                intent.putExtra(DevicePriceActivity.MONTH, month);
                context.startActivity(intent);
            }
        });

//        holder.tvIncomeDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, IncomeDetailActivity.class);
//                intent.putExtra(IncomeDetailActivity.INCOME_DATA, (Serializable) dataBean.getTj());
//                intent.putExtra(IncomeDetailActivity.ID, (Serializable) dataBean.getId());
//                intent.putExtra(IncomeDetailActivity.INCOME_ADDRESS, dataBean.getDetails());
//                intent.putExtra(IncomeDetailActivity.INCOME_STORE_NAME, dataBean.getStore_name());
//                intent.putExtra(IncomeDetailActivity.YEAR, year);
//                intent.putExtra(IncomeDetailActivity.MONTH, month);
//                context.startActivity(intent);
//            }
//        });
//        holder.rlIncomeHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, IncomeDetailActivity.class);
//                intent.putExtra(IncomeDetailActivity.INCOME_DATA, (Serializable) dataBean.getTj());
//                intent.putExtra(IncomeDetailActivity.INCOME_ADDRESS, dataBean.getDetails());
//                intent.putExtra(IncomeDetailActivity.ID, (Serializable) dataBean.getId());
//                intent.putExtra(IncomeDetailActivity.INCOME_STORE_NAME, dataBean.getStore_name());
//                intent.putExtra(IncomeDetailActivity.YEAR, year);
//                intent.putExtra(IncomeDetailActivity.MONTH, month);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView tvPrice, tvNum, tvAddress, tvIncomeDetail;
        private ChartView chartView;
        private RelativeLayout rlIncomeHead;
    }
}

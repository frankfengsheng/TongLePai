package com.cheng.tonglepai.adapter.partner;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.SiteFileIncomeListBean;
import com.cheng.tonglepai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class PartnerSiteListAdapter extends BaseAdapter {
    private Context context;
    private List<SiteFileIncomeListBean.DataBean> mData = new ArrayList<>();

    public PartnerSiteListAdapter(Context context, List<SiteFileIncomeListBean.DataBean> mList) {
        this.context = context;
        this.mData=mList;
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
            convertView = View.inflate(context, R.layout.view_item_file_income, null);
            holder=new ViewHolder();
            holder.tv_siteName= (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_deviceYitou= (TextView) convertView.findViewById(R.id.tv_device_count);
            holder.tv_deviceKetou= (TextView) convertView.findViewById(R.id.tv_device_code);
            holder.tv_weizhi= (TextView) convertView.findViewById(R.id.tv_weizhi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tv_siteName;
        private TextView tv_deviceYitou;
        private TextView tv_deviceKetou;
        private TextView tv_weizhi;
        private LinearLayout ly_item;
    }
}

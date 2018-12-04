package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.retrofit20.bean.SiteFileIncomeListBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.List;


public class FragmentSiteDeviceListAdapter extends RecyclerView.Adapter<FragmentSiteDeviceListAdapter.MyHolder> {

    Context context;
    List<SiteFileIncomeListBean.DataBean> list;
    OnItemclickListenner itemclickListenner;

    public FragmentSiteDeviceListAdapter(Context context, List<SiteFileIncomeListBean.DataBean> list){
            this.context=context;
            this.list=list;
    }
    @Override
    public FragmentSiteDeviceListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_item_file_income,null);
        MyHolder myHolder=new MyHolder(view);


        return myHolder;
    }

    public void setOnItemClickLis(OnItemclickListenner itemClickLis){
        this.itemclickListenner=itemClickLis;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
      if(!TextUtils.isEmpty(list.get(position).getStore_name()))  holder.tv_siteName.setText(list.get(position).getStore_name());
        if(!TextUtils.isEmpty(list.get(position).getDevice_name()))  holder.tv_deviceName.setText(list.get(position).getDevice_name());
        if(!TextUtils.isEmpty(list.get(position).getDevice_code()))  holder.tv_deviceCode.setText("通讯码："+list.get(position).getDevice_code());
        if(!TextUtils.isEmpty(list.get(position).getTb_price()))  holder.tv_toubiIncome.setText("￥"+list.get(position).getTb_price());
        if(!TextUtils.isEmpty(list.get(position).getSm_price()))  holder.tv_saomaIncome.setText("￥"+list.get(position).getSm_price());
        if(!TextUtils.isEmpty(list.get(position).getPrice()))     holder.tv_totalIncome.setText("￥"+list.get(position).getPrice());
        if(!TextUtils.isEmpty(list.get(position).getDetails()))  holder.tv_weizhi.setText(list.get(position).getDetails());
        holder.ly_item.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(itemclickListenner!=null)itemclickListenner.onItemClick(position);
          }
      });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class   MyHolder extends RecyclerView.ViewHolder{
        private TextView tv_siteName;
        private TextView tv_detail;
        private TextView tv_deviceName;
        private TextView tv_deviceCode;
        private TextView tv_toubiIncome;
        private TextView tv_saomaIncome;
        private TextView tv_totalIncome;
        private TextView tv_weizhi;
        private LinearLayout ly_item;
        public MyHolder(View itemView) {
            super(itemView);
           tv_siteName= (TextView) itemView.findViewById(R.id.tv_shop_name);
            tv_detail= (TextView) itemView.findViewById(R.id.tv_detail);
            tv_deviceName= (TextView) itemView.findViewById(R.id.tv_device_count);
            tv_deviceCode= (TextView) itemView.findViewById(R.id.tv_device_code);
            tv_toubiIncome= (TextView) itemView.findViewById(R.id.tv_toubi_income);
            tv_saomaIncome= (TextView) itemView.findViewById(R.id.tv_saoma_income);
            tv_totalIncome= (TextView) itemView.findViewById(R.id.tv_daijiao_count);
            tv_weizhi= (TextView) itemView.findViewById(R.id.tv_weizhi);
            ly_item= (LinearLayout) itemView.findViewById(R.id.ly_device_item);


        }

    }
   public static interface OnItemclickListenner{
        void onItemClick(int position);

   }
}

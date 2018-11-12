package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.List;


public class SiteDeviceListAdapter extends RecyclerView.Adapter<SiteDeviceListAdapter.MyHolder> {

    Context context;
    List<SiteEquimentListBean.DataBean> list;
    OnItemclickListenner itemclickListenner;

    public SiteDeviceListAdapter(Context context, List<SiteEquimentListBean.DataBean> list){
            this.context=context;
            this.list=list;
    }
    @Override
    public SiteDeviceListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_item_site_device_list,null);
        MyHolder myHolder=new MyHolder(view);


        return myHolder;
    }

    public void setOnItemClickLis(OnItemclickListenner itemClickLis){
        this.itemclickListenner=itemClickLis;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
      holder.tv_shopName.setText(list.get(position).getDevice_name());
      holder.tv_DeviceCount.setText("通讯编号："+list.get(position).getDevice_code());
      holder.tv_yesterDayIncome.setText("昨日收益：￥"+list.get(position).getLastday());
      holder.tv_MonthIncome.setText("本月收益：￥"+list.get(position).getThismonth());
      holder.tv_startTime.setText("运行时间："+ TimeUtil.stampToDate(list.get(position).getCreated()));
      holder.ly_deviceitem.setOnClickListener(new View.OnClickListener() {
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
        private TextView tv_shopName,tv_DeviceCount,tv_yesterDayIncome,tv_MonthIncome,tv_startTime;
        private LinearLayout ly_deviceitem;
        public MyHolder(View itemView) {
            super(itemView);
            tv_shopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tv_DeviceCount = (TextView) itemView.findViewById(R.id.tv_device_count);
            tv_startTime = (TextView) itemView.findViewById(R.id.tv_start_time);
           tv_yesterDayIncome = (TextView) itemView.findViewById(R.id.tv_yesterday_income);
           tv_MonthIncome = (TextView) itemView.findViewById(R.id.tv_month_income);
           ly_deviceitem= (LinearLayout) itemView.findViewById(R.id.ly_device_item);

        }

    }
   public static interface OnItemclickListenner{
        void onItemClick( int position);

   }
}

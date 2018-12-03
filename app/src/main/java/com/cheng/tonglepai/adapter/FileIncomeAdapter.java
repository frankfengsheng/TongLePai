package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.List;


public class FileIncomeAdapter extends RecyclerView.Adapter<FileIncomeAdapter.MyHolder> {

    Context context;
    List<SiteEquimentListBean.DataBean> list;
    OnItemclickListenner itemclickListenner;

    public FileIncomeAdapter(Context context, List<SiteEquimentListBean.DataBean> list){
            this.context=context;
            this.list=list;
    }
    @Override
    public FileIncomeAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_item_file_income,null);
        MyHolder myHolder=new MyHolder(view);


        return myHolder;
    }

    public void setOnItemClickLis(OnItemclickListenner itemClickLis){
        this.itemclickListenner=itemClickLis;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class   MyHolder extends RecyclerView.ViewHolder{
        private TextView tv_siteName,tv_Detail,tv_toubiCount,tv_saomaCount,tv_income,tv_weizhi;

        public MyHolder(View itemView) {
            super(itemView);
            tv_income= (TextView) itemView.findViewById(R.id.tv_daijiao_count);
            tv_siteName= (TextView) itemView.findViewById(R.id.tv_shop_name);
            tv_Detail= (TextView) itemView.findViewById(R.id.tv_detail);
            tv_toubiCount= (TextView) itemView.findViewById(R.id.tv_toubi_income);
            tv_saomaCount= (TextView) itemView.findViewById(R.id.tv_saoma_income);
            tv_weizhi= (TextView) itemView.findViewById(R.id.tv_weizhi);
        }

    }
   public static interface OnItemclickListenner{
        void onItemClick(int position);

   }
}

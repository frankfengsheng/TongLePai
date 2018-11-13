package com.cheng.tonglepai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.ImageLoaderTool;
import com.cheng.tonglepai.tool.TlpBitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2016/6/29.
 */
public class SelectPictureAdapter extends BaseAdapter{


    private Context context;
    private List<String> list = new ArrayList<String>();
    private int maxCount;
    public SelectPictureAdapter(Context context, List<String> list,int maxCount) {
        this.context = context;
        this.list = list;
        this.maxCount=maxCount;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size()>=maxCount?maxCount:list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_picture, null);
            holder = new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.iv_img);
            holder.iv_delete= (ImageView) convertView.findViewById(R.id.imgDelete);
            holder.layout_delete= (LinearLayout) convertView.findViewById(R.id.layout_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position != list.size()) {

                Bitmap bitmapOneOld = BitmapFactory.decodeFile(list.get(position));
                //Bitmap bitmapOne = TlpBitmapFactory.getInstance().compressScale(bitmapOneOld);
                holder.imageView.setImageBitmap(bitmapOneOld);
                holder.layout_delete.setVisibility(View.VISIBLE);

        }else{
            holder.imageView.setImageResource(R.drawable.imo_notice_addimg);
            holder.layout_delete.setVisibility(View.GONE);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    list.remove(position);
                    notifyDataSetChanged();

            }
        });

        return convertView;
    }


    class ViewHolder {
        private ImageView imageView,iv_delete;
        private LinearLayout layout_delete;

    }
}

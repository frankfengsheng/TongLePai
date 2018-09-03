package com.cheng.tonglepai.tool;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/7/3.
 */

public class SearchDevicePopwindow extends PopupWindow {
    private View mMenuView;
    private Context context;
    private Window window;
    private EditText etSearch;
    private ImageView ivSearch;
    private boolean isInvestor;

    public SearchDevicePopwindow(final Context context, Window window, final boolean isInvestor) {
        super(context);
        this.context = context;
        this.window = window;
        this.isInvestor = isInvestor;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_device_search, null);

        etSearch = (EditText) mMenuView.findViewById(R.id.et_search);
        if (isInvestor)
            etSearch.setHint("请输入地址");
        else
            etSearch.setHint("请输入设备编号/地址");
        ivSearch = (ImageView) mMenuView.findViewById(R.id.iv_search);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                    if (isInvestor)
                        Toast.makeText(context, "请输入地址", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "请输入设备编号/地址", Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != searchDevicePopListener) {
                    searchDevicePopListener.toSearch(etSearch.getText().toString().trim());
                    dismiss();
                }
            }
        });
        this.setBackgroundDrawable(new BitmapDrawable());

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation_AppCompat_Dialog);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable();
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(1f);


    }

    private SearchDevicePopListener searchDevicePopListener;

    public void setChooseProductPopListener(SearchDevicePopListener searchDevicePopListener) {
        this.searchDevicePopListener = searchDevicePopListener;
    }

    public interface SearchDevicePopListener {
        void toSearch(String ids);
    }
}

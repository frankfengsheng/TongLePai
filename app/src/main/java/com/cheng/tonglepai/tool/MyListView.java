package com.cheng.tonglepai.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by cheng on 2018/7/31.
 */

public class MyListView extends ListView {


    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

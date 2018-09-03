package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.AlipayResult;

/**
 * Created by cheng on 2018/6/28.
 */

public class AlipayBinding implements IUiDataBinding<AlipayResult,AlipayResult> {
    private AlipayResult mResult;
    private Context mContext;

    public AlipayBinding(AlipayResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public AlipayResult getUiData() {
        return getData();
    }

    private AlipayResult getData() {
        AlipayResult uiList = new AlipayResult();
        uiList.setData(mResult.getData());
        return uiList;
    }
}

package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CanApplyResult;

/**
 * Created by cheng on 2018/7/25.
 */

public class CanApplyBinding implements IUiDataBinding<CanApplyResult,CanApplyResult> {
    private CanApplyResult mResult;
    private Context mContext;

    public CanApplyBinding(CanApplyResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public CanApplyResult getUiData() {
        return getData();
    }

    private CanApplyResult getData() {
        CanApplyResult uiList = new CanApplyResult();
        uiList.setData(mResult.getData());
        return uiList;
    }
}

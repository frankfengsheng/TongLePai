package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.ChooseTypeResult;
import com.cheng.tonglepai.data.ChooseTypeData;

/**
 * Created by cheng on 2018/6/28.
 */

public class ChooseTypeBinding implements IUiDataBinding<ChooseTypeData,ChooseTypeResult> {
    private ChooseTypeResult mResult;
    private Context mContext;

    public ChooseTypeBinding(ChooseTypeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public ChooseTypeData getUiData() {
        return getData();
    }

    private ChooseTypeData getData() {
        ChooseTypeData mData = new ChooseTypeData();
        mData.setToken(mResult.getToken());
        mData.setId(mResult.getData().getId());
        return mData;
    }
}

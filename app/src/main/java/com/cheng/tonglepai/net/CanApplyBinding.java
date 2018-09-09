package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.tonglepai.data.CanApplyData;

/**
 * Created by cheng on 2018/7/25.
 */

public class CanApplyBinding implements IUiDataBinding<CanApplyData,CanApplyResult> {
    private CanApplyResult mResult;
    private Context mContext;

    public CanApplyBinding(CanApplyResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public CanApplyData getUiData() {
        return getData();
    }

    private CanApplyData getData() {
        CanApplyData data = new CanApplyData();
        data.setBank(mResult.getData().get(0).getBank());
        data.setBank_account(mResult.getData().get(0).getBank_account());
        data.setPrice(mResult.getData().get(0).getPrice());
        return data;
    }
}

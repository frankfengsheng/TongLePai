package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CanApplyResultNew;
import com.cheng.tonglepai.data.CanApplyData;

/**
 * Created by cheng on 2018/7/25.
 */

public class CanApplyNewBinding implements IUiDataBinding<CanApplyData,CanApplyResultNew> {
    private CanApplyResultNew mResult;
    private Context mContext;

    public CanApplyNewBinding(CanApplyResultNew result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public CanApplyData getUiData() {
        return getData();
    }

    private CanApplyData getData() {
        CanApplyData data = new CanApplyData();
        data.setBank(mResult.getData().getBank());
        data.setBank_account(mResult.getData().getBank_account());
        data.setPrice(mResult.getData().getPrice());
        data.setZ_price(mResult.getData().getZ_price());
        data.setPrice_pay(mResult.getData().getPrice_pay());
        data.setOpenid(mResult.getData().getOpenid());
        data.setWx_nickname(mResult.getData().getWx_nickname());
        return data;
    }
}

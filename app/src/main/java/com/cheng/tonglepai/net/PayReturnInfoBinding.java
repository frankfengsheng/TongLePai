package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.PayReturnInfoResult;
import com.cheng.tonglepai.data.PayReturnInfoData;

/**
 * Created by cheng on 2018/6/28.
 */

public class PayReturnInfoBinding implements IUiDataBinding<PayReturnInfoData,PayReturnInfoResult> {
    private PayReturnInfoResult mResult;
    private Context mContext;

    public PayReturnInfoBinding(PayReturnInfoResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public PayReturnInfoData getUiData() {
        return getData();
    }

    private PayReturnInfoData getData() {
        PayReturnInfoData data = new PayReturnInfoData();
        data.setTimes(mResult.getData().getTimes());
        data.setNums(mResult.getData().getNums());
        data.setPrice(mResult.getData().getPrice());
        data.setTel(mResult.getData().getTel());
        data.setType(mResult.getData().getType());
        return data;
    }
}

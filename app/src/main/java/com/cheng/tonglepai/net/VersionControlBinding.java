package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.VersionControlResult;
import com.cheng.tonglepai.data.VersionControlData;


/**
 * Created by cheng on 2018/5/21.
 */

public class VersionControlBinding implements IUiDataBinding<VersionControlData,VersionControlResult> {
    private VersionControlResult mResult;
    private Context mContext;

    public VersionControlBinding(VersionControlResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public VersionControlData getUiData() {
        VersionControlData data = new VersionControlData();
        data.setAndroid_address(mResult.getData().getAndroid_address());
        data.setAndroid_number(mResult.getData().getAndroid_number());
        data.setIos_address(mResult.getData().getIos_address());
        data.setIos_number(mResult.getData().getIos_number());
        data.setAndroidis_update(mResult.getData().getAndroidis_update());
        return data;
    }
}

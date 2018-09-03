package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.ShareCodeResult;
import com.cheng.tonglepai.data.ShareCodeData;

/**
 * Created by cheng on 2018/6/28.
 */

public class ShareCodeBinding implements IUiDataBinding<ShareCodeData,ShareCodeResult> {
    private ShareCodeResult mResult;
    private Context mContext;

    public ShareCodeBinding(ShareCodeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public ShareCodeData getUiData() {
        return getData();
    }

    private ShareCodeData getData() {
        ShareCodeData uiList = new ShareCodeData();
        uiList.setShareCode(mResult.getData());
        return uiList;
    }
}

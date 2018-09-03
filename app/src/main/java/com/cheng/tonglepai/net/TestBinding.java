package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.TestData;


/**
 * Created by cheng on 2018/5/21.
 */

public class TestBinding implements IUiDataBinding<TestData,TestData> {
    private TestData mResult;
    private Context mContext;

    public TestBinding(TestData result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public TestData getUiData() {
        TestData data = new TestData();
        data.setStatus(mResult.getStatus());
        data.setMsg(mResult.getMsg());
        return data;
    }
}

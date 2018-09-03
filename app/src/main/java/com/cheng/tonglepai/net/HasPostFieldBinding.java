package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.HsaPostFieldResult;
import com.cheng.tonglepai.data.HasPostFieldData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class HasPostFieldBinding implements IUiDataBinding<List<HasPostFieldData>,HsaPostFieldResult> {
    private HsaPostFieldResult mResult;
    private Context mContext;

    public HasPostFieldBinding(HsaPostFieldResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<HasPostFieldData> getUiData() {
        return getData();
    }

    private List<HasPostFieldData> getData() {
        List<HasPostFieldData> uiList = new ArrayList<>();
        List<HsaPostFieldResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            HsaPostFieldResult.DataBean httpItem = httpList.get(i);
            HasPostFieldData uiItem = new HasPostFieldData();
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setStatus(httpItem.getStatus());
            uiItem.setStore_name(httpItem.getStore_name());
            uiItem.setId(httpItem.getId());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

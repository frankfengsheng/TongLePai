package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.AllDeviceResult;
import com.cheng.tonglepai.data.ExceptionDeviceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class AllDeviceBinding implements IUiDataBinding<List<ExceptionDeviceData>,AllDeviceResult> {
    private AllDeviceResult mResult;
    private Context mContext;

    public AllDeviceBinding(AllDeviceResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<ExceptionDeviceData> getUiData() {
        return getData();
    }

    private List<ExceptionDeviceData> getData() {
        List<ExceptionDeviceData> uiList = new ArrayList<>();
        List<AllDeviceResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            AllDeviceResult.DataBean httpItem = httpList.get(i);
            ExceptionDeviceData uiItem = new ExceptionDeviceData();
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setId(httpItem.getId());
//            uiItem.setStatus(httpItem.getStatus());
            uiItem.setDevice_name(httpItem.getDevice_name());
            uiItem.setCreated(httpItem.getTimes());
            uiItem.setDevice_code(httpItem.getDevice_code());
            uiItem.setStore_name(httpItem.getStore_name());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

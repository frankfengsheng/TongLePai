package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.BindDeviceListResult;
import com.cheng.tonglepai.data.BindDeviceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class BindDeviceBinding implements IUiDataBinding<List<BindDeviceData>,BindDeviceListResult> {
    private BindDeviceListResult mResult;
    private Context mContext;

    public BindDeviceBinding(BindDeviceListResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<BindDeviceData> getUiData() {
        return getData();
    }

    private List<BindDeviceData> getData() {
        List<BindDeviceData> uiList = new ArrayList<>();
        List<BindDeviceListResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            BindDeviceListResult.DataBean httpItem = httpList.get(i);
            BindDeviceData uiItem = new BindDeviceData();
            uiItem.setCreated(httpItem.getCreated());
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setDevice_code(httpItem.getDevice_code());
            uiItem.setStore_name(httpItem.getStore_name());
            uiItem.setQrcode(httpItem.getQrcode());
            uiItem.setInfo_id(httpItem.getInfo_id());
            uiItem.setDevice_name(httpItem.getDevice_name());
            uiItem.setDevice_id(httpItem.getDevice_id());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

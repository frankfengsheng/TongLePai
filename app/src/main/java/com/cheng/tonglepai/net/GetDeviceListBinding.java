package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.DeviceListResult;
import com.cheng.tonglepai.data.DeviceListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class GetDeviceListBinding implements IUiDataBinding<List<DeviceListData>,DeviceListResult> {
    private DeviceListResult mResult;
    private Context mContext;

    public GetDeviceListBinding(DeviceListResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<DeviceListData> getUiData() {
        return getData();
    }

    private List<DeviceListData> getData() {
        List<DeviceListData> uiList = new ArrayList<>();
        List<DeviceListResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            DeviceListResult.DataBean httpItem = httpList.get(i);
            DeviceListData uiItem = new DeviceListData();
            uiItem.setDevice_model(httpItem.getDevice_model());
            uiItem.setDevice_name(httpItem.getDevice_name());
            uiItem.setId(httpItem.getId());
            uiItem.setImg(httpItem.getImg());
            uiItem.setPrice_purchase(httpItem.getPrice_purchase());
            uiItem.setRecommend(httpItem.getRecommend());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

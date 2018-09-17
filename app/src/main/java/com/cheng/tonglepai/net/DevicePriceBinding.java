package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.DevicePriceResult;
import com.cheng.tonglepai.data.DevicePriceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class DevicePriceBinding implements IUiDataBinding<DevicePriceData, DevicePriceResult> {
    private DevicePriceResult mResult;
    private Context mContext;

    public DevicePriceBinding(DevicePriceResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public DevicePriceData getUiData() {
        return getData();
    }

    private DevicePriceData getData() {
        DevicePriceData uiList = new DevicePriceData();
        List<DevicePriceData.DataBean> dataList = new ArrayList<>();
        List<DevicePriceResult.DataBean> httpList = mResult.getData();
//        if (httpList == null || httpList.size() == 0) {
//            return null;
//        }
        for (int i = 0; i < httpList.size(); i++) {
            DevicePriceResult.DataBean httpItem = httpList.get(i);
            DevicePriceData.DataBean uiItem = new DevicePriceData.DataBean();
            uiItem.setDevice_name(httpItem.getDevice_name());
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setStore_device_id(httpItem.getStore_device_id());
            dataList.add(uiItem);
        }
        uiList.setData(dataList);
        DevicePriceData.InfoDataBean priceDataBean = new DevicePriceData.InfoDataBean();
        priceDataBean.setZ_price(mResult.getInfo_data().getZ_price());
        priceDataBean.setDetails(mResult.getInfo_data().getDetails());
        priceDataBean.setDevice_nums(mResult.getInfo_data().getDevice_nums());
        priceDataBean.setId(mResult.getInfo_data().getId());
        priceDataBean.setStore_name(mResult.getInfo_data().getStore_name());
        priceDataBean.setDevice_list_status(mResult.getInfo_data().getDevice_list_status());
        uiList.setInfo_data(priceDataBean);
        return uiList;
    }
}

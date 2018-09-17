package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.DeviceBillResult;
import com.cheng.tonglepai.data.DeviceBillData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class DeviceBillBinding implements IUiDataBinding<DeviceBillData, DeviceBillResult> {
    private DeviceBillResult mResult;
    private Context mContext;

    public DeviceBillBinding(DeviceBillResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public DeviceBillData getUiData() {
        return getData();
    }

    private DeviceBillData getData() {
        DeviceBillData uiList = new DeviceBillData();
        List<DeviceBillData.DataBean> dataList = new ArrayList<>();
        List<DeviceBillResult.DataBean> httpList = mResult.getData();
//        if (httpList == null || httpList.size() == 0) {
//            return null;
//        }
        for (int i = 0; i < httpList.size(); i++) {
            DeviceBillResult.DataBean httpItem = httpList.get(i);
            DeviceBillData.DataBean uiItem = new DeviceBillData.DataBean();
            if("1".equals(httpItem.getPay_source())){
                uiItem.setPay_source("投币");
            }else if("0".equals(httpItem.getPay_source())){
                uiItem.setPay_source("扫码");
            }
            uiItem.setPrice("￥"+httpItem.getPrice());
            uiItem.setUpdated(TimeUtil.allTimeNew(httpItem.getUpdated()));
            dataList.add(uiItem);
        }
        uiList.setData(dataList);
        DeviceBillData.PriceDataBean priceDataBean = new DeviceBillData.PriceDataBean();
        priceDataBean.setZ_price(mResult.getPrice_data().getZ_price());
        uiList.setPrice_data(priceDataBean);
        return uiList;
    }
}

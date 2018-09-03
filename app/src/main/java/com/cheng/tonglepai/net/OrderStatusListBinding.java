package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CheckokInfoResult;
import com.cheng.tonglepai.data.CheckokInfoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class OrderStatusListBinding implements IUiDataBinding<List<CheckokInfoData>,CheckokInfoResult> {
    private CheckokInfoResult mResult;
    private Context mContext;

    public OrderStatusListBinding(CheckokInfoResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<CheckokInfoData> getUiData() {
        return getData();
    }

    private List<CheckokInfoData> getData() {
        List<CheckokInfoData> uiList = new ArrayList<>();
        List<CheckokInfoResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            CheckokInfoResult.DataBean httpItem = httpList.get(i);
            CheckokInfoData uiItem = new CheckokInfoData();
            uiItem.setBetting_fee(httpItem.getBetting_fee());
            uiItem.setId(httpItem.getId());
            uiItem.setDevice_list(httpItem.getDevice_list());
            uiItem.setImg(httpItem.getImg());
            uiItem.setOrder_number(httpItem.getOrder_number());
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setUpdated(httpItem.getUpdated());
            uiItem.setShipping_fee(httpItem.getShipping_fee());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

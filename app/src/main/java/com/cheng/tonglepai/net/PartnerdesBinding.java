package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.PartenerdesResult;
import com.cheng.tonglepai.data.PartnerdesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class PartnerdesBinding implements IUiDataBinding<List<PartnerdesData>,PartenerdesResult> {
    private PartenerdesResult mResult;
    private Context mContext;

    public PartnerdesBinding(PartenerdesResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<PartnerdesData> getUiData() {
        return getData();
    }

    private List<PartnerdesData> getData() {
        List<PartnerdesData> uiList = new ArrayList<>();
        List<PartenerdesResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            PartenerdesResult.DataBean httpItem = httpList.get(i);
            PartnerdesData uiItem = new PartnerdesData();
            uiItem.setMonth(httpItem.getMonth());
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setStore_name(httpItem.getStore_name());
            uiItem.setWeek(httpItem.getWeek());
            uiItem.setUpdated(httpItem.getUpdated());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

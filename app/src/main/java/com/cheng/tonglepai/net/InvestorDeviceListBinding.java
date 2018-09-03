package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorAddviceResult;
import com.cheng.tonglepai.data.InvestorDeviceListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class InvestorDeviceListBinding implements IUiDataBinding<List<InvestorDeviceListData>, InvestorAddviceResult> {
    private InvestorAddviceResult mResult;
    private Context mContext;

    public InvestorDeviceListBinding(InvestorAddviceResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<InvestorDeviceListData> getUiData() {
        return getData();
    }

    private List<InvestorDeviceListData> getData() {
        List<InvestorDeviceListData> uiList = new ArrayList<>();
        List<InvestorAddviceResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            InvestorAddviceResult.DataBean httpItem = httpList.get(i);
            InvestorDeviceListData uiItem = new InvestorDeviceListData();
            uiItem.setId(httpItem.getId());
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setThismonth(httpItem.getThismonth());
            uiItem.setName(httpItem.getName());
            uiItem.setYesterday(httpItem.getYesterday());
            uiItem.setDevice_list(httpItem.getDevice_list());
            uiItem.setTime(httpItem.getTime());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

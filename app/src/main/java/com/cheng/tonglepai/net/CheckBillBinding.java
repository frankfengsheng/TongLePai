package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CheckinfoBillResult;
import com.cheng.tonglepai.data.CheckBillData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class CheckBillBinding implements IUiDataBinding<List<CheckBillData>, CheckinfoBillResult> {
    private CheckinfoBillResult mResult;
    private Context mContext;

    public CheckBillBinding(CheckinfoBillResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<CheckBillData> getUiData() {
        return getData();
    }

    private List<CheckBillData> getData() {
        List<CheckBillData> uiList = new ArrayList<>();
        List<CheckinfoBillResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            CheckinfoBillResult.DataBean httpItem = httpList.get(i);
            CheckBillData uiItem = new CheckBillData();
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setUpdated(TimeUtil.allTimeNew(httpItem.getUpdated()));
            uiList.add(uiItem);
        }
        return uiList;
    }
}

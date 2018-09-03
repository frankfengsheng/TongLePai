package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.FieldIncomeResult;
import com.cheng.tonglepai.data.FieldIncomeData;

/**
 * Created by cheng on 2018/6/28.
 */

public class FieldIncomeBinding implements IUiDataBinding<FieldIncomeData, FieldIncomeResult> {
    private FieldIncomeResult mResult;
    private Context mContext;

    public FieldIncomeBinding(FieldIncomeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public FieldIncomeData getUiData() {
        return getData();
    }

    private FieldIncomeData getData() {
        FieldIncomeData uiList = new FieldIncomeData();
        if (mResult == null) {
            return null;
        }
        FieldIncomeResult.DataBean dataBean = mResult.getData().get(0);
        uiList.setDetails(dataBean.getDetails());
        uiList.setId(dataBean.getId());
        uiList.setInvest_status(dataBean.getInvest_status());
        uiList.setLast_month(dataBean.getLast_month());
        uiList.setName(dataBean.getName());
        uiList.setNums(dataBean.getNums());
        uiList.setStatus(dataBean.getStatus());
        uiList.setStore_name(dataBean.getStore_name());
        uiList.setThis_month(dataBean.getThis_month());
        uiList.setToday(dataBean.getToday());
        uiList.setTotal(dataBean.getTotal());
        uiList.setUpdated(dataBean.getUpdated());
        uiList.setYesterday(dataBean.getYesterday());

        return uiList;
    }
}

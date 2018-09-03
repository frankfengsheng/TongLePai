package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorIncomeResult;
import com.cheng.tonglepai.data.InvestorIncomeData;

/**
 * Created by cheng on 2018/6/28.
 */

public class InvestorIncomeBinding implements IUiDataBinding<InvestorIncomeData, InvestorIncomeResult> {
    private InvestorIncomeResult mResult;
    private Context mContext;

    public InvestorIncomeBinding(InvestorIncomeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public InvestorIncomeData getUiData() {
        return getData();
    }

    private InvestorIncomeData getData() {
        InvestorIncomeData uiList = new InvestorIncomeData();

        if (null == mResult && null == mResult.getData()) {
            return null;
        }
        InvestorIncomeResult.DataBean dataBean = mResult.getData().get(0);
        uiList.setYesterday(dataBean.getYesterday());
        uiList.setName(dataBean.getName());
        uiList.setDetails(dataBean.getDetails());
        uiList.setLast_month(dataBean.getLast_month());
        uiList.setThis_month(dataBean.getThis_month());
        uiList.setToday(dataBean.getToday());
        uiList.setTotal(dataBean.getTotal());
        uiList.setStatus(dataBean.getStatus());
        uiList.setNums(dataBean.getNums());
        uiList.setUpdated(dataBean.getUpdated());
        uiList.setStore_name(dataBean.getStore_name());
        uiList.setId(dataBean.getId());
        return uiList;
    }
}

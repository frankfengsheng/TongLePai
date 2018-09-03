package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.IncomeMainResult;
import com.cheng.tonglepai.data.IncomeMainData;

/**
 * Created by cheng on 2018/6/28.
 */

public class IncomeMainBinding implements IUiDataBinding<IncomeMainData,IncomeMainResult> {
    private IncomeMainResult mResult;
    private Context mContext;

    public IncomeMainBinding(IncomeMainResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public IncomeMainData getUiData() {
        return getData();
    }

    private IncomeMainData getData() {
        IncomeMainData uiList = new IncomeMainData();
        uiList.setTimes(mResult.getData().getTimes());

        if(Double.parseDouble(mResult.getData().getTotal()) == 0){
            uiList.setTotal("0");
        }else{
            uiList.setTotal(mResult.getData().getTotal());
        }
        if(Double.parseDouble(mResult.getData().getPlace_price()) == 0){
            uiList.setPlace_price("0");
        }else{
            uiList.setPlace_price(mResult.getData().getPlace_price());
        }
        if(Double.parseDouble(mResult.getData().getInvest_price()) == 0){
            uiList.setInvest_price("0");
        }else{
            uiList.setInvest_price(mResult.getData().getInvest_price());
        }
        if(Double.parseDouble(mResult.getData().getPartner_price()) == 0){
            uiList.setPartner_price("0");
        }else{
            uiList.setPartner_price(mResult.getData().getPartner_price());
        }
        return uiList;
    }
}

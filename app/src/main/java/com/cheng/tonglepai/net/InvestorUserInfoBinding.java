package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorUserInfoResult;
import com.cheng.tonglepai.data.InvestorUserInfoData;


/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorUserInfoBinding implements IUiDataBinding<InvestorUserInfoData,InvestorUserInfoResult> {
    private InvestorUserInfoResult mResult;
    private Context mContext;

    public InvestorUserInfoBinding(InvestorUserInfoResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public InvestorUserInfoData getUiData() {
        InvestorUserInfoData data = new InvestorUserInfoData();
        data.setImg(mResult.getData().getImg());
        data.setMy_device_nums(mResult.getData().getMy_device_nums());
        data.setYt_device_nums(mResult.getData().getYt_device_nums());
        data.setNickename(mResult.getData().getNickename());
        data.setZ_shouyi(mResult.getData().getZ_shouyi());
        data.setTel(mResult.getData().getTel());
        data.setZy_device_nums(mResult.getData().getZy_device_nums());
        data.setLevel(mResult.getData().getLevel());

        if(Double.parseDouble(mResult.getData().getZ_shouyi()) == 0){
            data.setZ_shouyi("0");
        }else{
            data.setZ_shouyi(mResult.getData().getZ_shouyi());
        }
        if(Double.parseDouble(mResult.getData().getPrice()) == 0){
            data.setPrice("0");
        }else{
            data.setPrice(mResult.getData().getPrice());
        }
        if(Double.parseDouble(mResult.getData().getToday()) == 0){
            data.setToday("0");
        }else{
            data.setToday(mResult.getData().getToday());
        }
        return data;
    }
}

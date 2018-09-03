package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.UserInfoResult;
import com.cheng.tonglepai.data.UserInfoData;


/**
 * Created by cheng on 2018/5/21.
 */

public class UserInfoBinding implements IUiDataBinding<UserInfoData,UserInfoResult> {
    private UserInfoResult mResult;
    private Context mContext;

    public UserInfoBinding(UserInfoResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public UserInfoData getUiData() {
        UserInfoData data = new UserInfoData();
        data.setCount(mResult.getData().getYt_nums());
        data.setCount1(mResult.getData().getZg_nums());
        data.setCt(mResult.getData().getCd_nums());
        data.setImg(mResult.getData().getImg());
        data.setNickname(mResult.getData().getNickename());
        data.setTel(mResult.getData().getTel());
        data.setLevel(mResult.getData().getLevel());

        if(Double.parseDouble(mResult.getData().getZ_shouyi()) == 0){
            data.setTotal("0");
        }else{
            data.setTotal(mResult.getData().getZ_shouyi());
        }
        if(Double.parseDouble(mResult.getData().getPrice()) == 0){
            data.setPrice("0");
        }else{
            data.setPrice(mResult.getData().getPrice());
        }
        if(Double.parseDouble(mResult.getData().getToday()) == 0){
            data.setNum("0");
        }else{
            data.setNum(mResult.getData().getToday());
        }
        return data;
    }
}

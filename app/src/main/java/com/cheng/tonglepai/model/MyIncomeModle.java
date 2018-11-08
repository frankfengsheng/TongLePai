package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.IsNeedPayBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/11/1 0001.
 */

public class MyIncomeModle {
    Context context;
    public MyIncomeModle(Context context){
        this.context=context;
    }


    /**
     * 提现到银行
     * @param
     * @return
     */
    public void canapplayCallback( final CanApplyCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<CanApplyResult> call=loginInfoPost.getFieldCanApply(map);
        call.enqueue(new Callback<CanApplyResult>() {
            @Override
            public void onResponse(Call<CanApplyResult> call,final Response<CanApplyResult> response) {
                CanApplyResult  bindingBean=response.body();
                callBack.bindSucess(bindingBean);

            }
            @Override
            public void onFailure(Call<CanApplyResult> call, Throwable t) {

            }
        });

    }

    /**
     * 获取我的收益
     * @param
     * @return
     */
    public void MarkcanapplayCallback( final CanApplyCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<CanApplyResult> call=loginInfoPost.getMarkerCanApply(map);
        call.enqueue(new Callback<CanApplyResult>() {
            @Override
            public void onResponse(Call<CanApplyResult> call,final Response<CanApplyResult> response) {
                CanApplyResult  bindingBean=response.body();
                callBack.bindSucess(bindingBean);

            }
            @Override
            public void onFailure(Call<CanApplyResult> call, Throwable t) {

            }
        });

    }

    /**
     * 获取我的收益
     * @param
     * @return
     */
    public void InvestorCanapplayCallback( final CanApplyCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<CanApplyResult> call=loginInfoPost.getInvestorCanApply(map);
        call.enqueue(new Callback<CanApplyResult>() {
            @Override
            public void onResponse(Call<CanApplyResult> call,final Response<CanApplyResult> response) {
                CanApplyResult  bindingBean=response.body();
                callBack.bindSucess(bindingBean);

            }
            @Override
            public void onFailure(Call<CanApplyResult> call, Throwable t) {

            }
        });

    }

    /**
     * 场地方判断是否需要缴币
     * @param
     * @return
     */
    public void IsPayMoney( final IsNeedPayMoneyCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        Call<IsNeedPayBean> call=loginInfoPost.IsNeedPayMoney(map);
        call.enqueue(new Callback<IsNeedPayBean>() {
            @Override
            public void onResponse(Call<IsNeedPayBean> call,final Response<IsNeedPayBean> response) {
                IsNeedPayBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<IsNeedPayBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 获取收益回调
     */
    public interface  CanApplyCallback{
        void bindSucess(CanApplyResult bindingBean);
    }

    /**
     * 获取收益回调
     */
    public interface  IsNeedPayMoneyCallback{
        void Sucess(IsNeedPayBean bean);
        void Faile();
    }
}

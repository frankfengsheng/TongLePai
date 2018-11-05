package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.bean.WechatWithDrawBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.InvestorApplyMoneyCmd;
import com.cheng.retrofit20.http.UserInfoCmd;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/10/31 0031.
 */

public class BindingModel {
    Context context;
    public BindingModel(Context context){
        this.context=context;
    }

    /**
     * 绑定微信
     * @param
     * @return
     */
    public void bindingWechat(String openid, String nickName, final BindSuccessCallBack callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("openid",openid);
        map.put("wx_nickname",nickName);
        Call<WechatBindingBean> call=loginInfoPost.bindingWechat(map);
        call.enqueue(new Callback<WechatBindingBean>() {
            @Override
            public void onResponse(Call<WechatBindingBean> call,final Response<WechatBindingBean> response) {
               WechatBindingBean  bindingBean=response.body();
                callBack.bindSucess(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatBindingBean> call, Throwable t) {
            }
        });

    }

    /**
     * 绑定微信
     * @param
     * @return
     */
    public void ChangeBindingWechat(String openid, String nickName, final BindSuccessCallBack callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("openid",openid);
        map.put("wx_nickname",nickName);
        Call<WechatBindingBean> call=loginInfoPost.ChangeBindingWechat(map);
        call.enqueue(new Callback<WechatBindingBean>() {
            @Override
            public void onResponse(Call<WechatBindingBean> call,final Response<WechatBindingBean> response) {
                WechatBindingBean  bindingBean=response.body();
                callBack.bindSucess(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatBindingBean> call, Throwable t) {

            }
        });

    }

    /**
     * 微信提现
     */
    public void WechatWithDraw(String money, String realMoney, final WechatWithDraw callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("money",money);
        map.put("price_real",realMoney);
        Call<WechatWithDrawBean> call=loginInfoPost.WechatWithDraw(map);
        call.enqueue(new Callback<WechatWithDrawBean>() {
            @Override
            public void onResponse(Call<WechatWithDrawBean> call,final Response<WechatWithDrawBean> response) {
                WechatWithDrawBean  bindingBean=response.body();
                callBack.withDraw(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatWithDrawBean> call, Throwable t) {

            }
        });

    }

    /**
     * 合伙人银行卡提现
     */
    public void HehuoBankWithDraw(String money, String bank, String bankAccount, String realMoney, final WechatWithDraw callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put(InvestorApplyMoneyCmd.K_MONEY, money);
        map.put(InvestorApplyMoneyCmd.K_BANK, bank);
        map.put(InvestorApplyMoneyCmd.K_BANK_ACCOUNT, bankAccount);
        map.put(InvestorApplyMoneyCmd.K_PRICE_REAL, realMoney);

        Call<WechatWithDrawBean> call=loginInfoPost.HehuorenWithDraw(map);
        call.enqueue(new Callback<WechatWithDrawBean>() {
            @Override
            public void onResponse(Call<WechatWithDrawBean> call,final Response<WechatWithDrawBean> response) {
                WechatWithDrawBean  bindingBean=response.body();
                callBack.withDraw(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatWithDrawBean> call, Throwable t) {

            }
        });

    }
    /**
     * 场地方银行卡提现
     */
    public void ChangdiBankWithDraw(String money, String bank, String bankAccount, String realMoney, final WechatWithDraw callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put(InvestorApplyMoneyCmd.K_MONEY, money);
        map.put(InvestorApplyMoneyCmd.K_BANK, bank);
        map.put(InvestorApplyMoneyCmd.K_BANK_ACCOUNT, bankAccount);
        map.put(InvestorApplyMoneyCmd.K_PRICE_REAL, realMoney);
        Call<WechatWithDrawBean> call=loginInfoPost.ChangdirenWithDraw(map);
        call.enqueue(new Callback<WechatWithDrawBean>() {
            @Override
            public void onResponse(Call<WechatWithDrawBean> call,final Response<WechatWithDrawBean> response) {
                WechatWithDrawBean  bindingBean=response.body();
                callBack.withDraw(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatWithDrawBean> call, Throwable t) {

            }
        });

    }
    /**
     * 投资方银行卡提现
     */
    public void TouziBankWithDraw(String money, String bank, String bankAccount, String realMoney, final WechatWithDraw callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put(InvestorApplyMoneyCmd.K_MONEY, money);
        map.put(InvestorApplyMoneyCmd.K_BANK, bank);
        map.put(InvestorApplyMoneyCmd.K_BANK_ACCOUNT, bankAccount);
        map.put(InvestorApplyMoneyCmd.K_PRICE_REAL, realMoney);
        Call<WechatWithDrawBean> call=loginInfoPost.TouziRenWithDraw(map);
        call.enqueue(new Callback<WechatWithDrawBean>() {
            @Override
            public void onResponse(Call<WechatWithDrawBean> call,final Response<WechatWithDrawBean> response) {
                WechatWithDrawBean  bindingBean=response.body();
                callBack.withDraw(bindingBean);

            }
            @Override
            public void onFailure(Call<WechatWithDrawBean> call, Throwable t) {

            }
        });

    }
    /**
     * 微信绑定结果回调
     */
    public interface  BindSuccessCallBack{
        void bindSucess(WechatBindingBean bindingBean);
        void binFailed();
    }
    /**
     * 微信提现结果回调
     */
    public interface  WechatWithDraw{
        void withDraw(WechatWithDrawBean bindingBean);
        void onFaile();
    }

}

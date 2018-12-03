package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.DevicesDetailsBean;
import com.cheng.retrofit20.bean.DevicesIncomeByMonthBean;
import com.cheng.retrofit20.bean.IsNeedPayBean;
import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.retrofit20.bean.SiteIncomeBean;
import com.cheng.retrofit20.bean.SiteTotalIncomeBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;
import com.cheng.tonglepai.tool.ToastUtil;

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
                if(bindingBean!=null) {
                    callBack.bindSucess(bindingBean);
                }else {
                    ToastUtil.showToast(context,"抱歉，数据获取失败");
                }

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
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
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
     * 设备方根据场地获取设备列表
     * @param
     * @return
     */
    public void GetDeviceList(String id, final GetDeviceListCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("store_info_id", id);
        Call<SiteEquimentListBean> call=loginInfoPost.GetSiteDevicesList(map);
        call.enqueue(new Callback<SiteEquimentListBean>() {
            @Override
            public void onResponse(Call<SiteEquimentListBean> call,final Response<SiteEquimentListBean> response) {
                SiteEquimentListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<SiteEquimentListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 设备方根据场地获取设备详情
     * @param
     * @return
     */
    public void GetDeviceDetails(String id, final GetDeviceDetaisCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("device_id", id);
        Call<DevicesDetailsBean> call=loginInfoPost.GetSiteDevicesDetails(map);
        call.enqueue(new Callback<DevicesDetailsBean>() {
            @Override
            public void onResponse(Call<DevicesDetailsBean> call,final Response<DevicesDetailsBean> response) {
                DevicesDetailsBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<DevicesDetailsBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 设备方根据月份获取设备收益折线图数据
     * @param
     * @return
     */
    public void GetIncomeByMonth(String id,String year,String month, final GetIncomeByMonthCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("device_id", id);
        map.put("year",year);
        map.put("month",month);
        Call<DevicesIncomeByMonthBean> call=loginInfoPost.GetMonthDevicesDetails(map);
        call.enqueue(new Callback<DevicesIncomeByMonthBean>() {
            @Override
            public void onResponse(Call<DevicesIncomeByMonthBean> call,final Response<DevicesIncomeByMonthBean> response) {
                DevicesIncomeByMonthBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<DevicesIncomeByMonthBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }


    /**
     * 场地方获取我的收益
     * @param
     * @return
     */
    public void SiteIncome(final SiteIncomeSucessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<SiteIncomeBean> call=loginInfoPost.GetSiteIncome(map);
        call.enqueue(new Callback<SiteIncomeBean>() {
            @Override
            public void onResponse(Call<SiteIncomeBean> call,final Response<SiteIncomeBean> response) {
                SiteIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<SiteIncomeBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 设备方根据月份获取设备收益折线图数据
     * @param
     * @return
     */
    public void SiteGetTotalIncomeByMonth(String year,String month, final SiteTotalIncomeSucessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("year",year);
        map.put("month",month);
        Call<SiteTotalIncomeBean> call=loginInfoPost.GetSiteIncomeByMonth(map);
        call.enqueue(new Callback<SiteTotalIncomeBean>() {
            @Override
            public void onResponse(Call<SiteTotalIncomeBean> call,final Response<SiteTotalIncomeBean> response) {
                SiteTotalIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<SiteTotalIncomeBean> call, Throwable t) {
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
     * 场地方判断是否需要缴币
     */
    public interface  IsNeedPayMoneyCallback{
        void Sucess(IsNeedPayBean bean);
        void Faile();
    }

    /**
     * 场地方判断是否需要缴币
     */
    public interface  GetDeviceListCallback{
        void Sucess(SiteEquimentListBean bean);
        void Faile();
    }

    /**
     * 场地方设备详情
     */
    public interface  GetDeviceDetaisCallback{
        void Sucess(DevicesDetailsBean bean);
        void Faile();
    }

    /**
     * 场地方获取设备收益根据月份
     */
    public interface  GetIncomeByMonthCallback{
        void Sucess(DevicesIncomeByMonthBean bean);
        void Faile();
    }

    /**
     * 场地方总收益
     */
    public interface SiteIncomeSucessCallback{
        void Sucess(SiteIncomeBean bean);
        void Faile();
    }
    /**
     * 场地方总收益根据月份
     */
    public interface SiteTotalIncomeSucessCallback{
        void Sucess(SiteTotalIncomeBean bean);
        void Faile();
    }
}

package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.DeviceIncomeDetailBean;
import com.cheng.retrofit20.bean.DevicesDetailsBean;
import com.cheng.retrofit20.bean.DevicesIncomeByMonthBean;
import com.cheng.retrofit20.bean.IsNeedPayBean;
import com.cheng.retrofit20.bean.PartnerDeviceIncomeListBean;
import com.cheng.retrofit20.bean.PartnerSiteDeviceIncomeListBean;
import com.cheng.retrofit20.bean.PartnerSiteIncomeBean;
import com.cheng.retrofit20.bean.PartnerStaticIncomeBean;
import com.cheng.retrofit20.bean.SiteEquimentListBean;
import com.cheng.retrofit20.bean.SiteFileIncomeListBean;
import com.cheng.retrofit20.bean.SiteIncomeBean;
import com.cheng.retrofit20.bean.SiteTotalIncomeBean;
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

public class IncomeModle {
    Context context;
    public IncomeModle(Context context){
        this.context=context;
    }

    /**
     * 合伙人获取综合统计收益
     * @param
     * @return
     */
    public void GetPartnerStatisticIncomeInfo(final PartnerStatisticIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<PartnerStaticIncomeBean> call=loginInfoPost.GetPartnerStatisticIncome(map);
        call.enqueue(new Callback<PartnerStaticIncomeBean>() {
            @Override
            public void onResponse(Call<PartnerStaticIncomeBean> call,final Response<PartnerStaticIncomeBean> response) {
                PartnerStaticIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerStaticIncomeBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 合伙人根据月份获取设备收益折线图数据
     * @param
     * @return
     */
    public void SiteGetTotalIncomeByMonth(String year,String month, final PartnerZhexianIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("year",year);
        map.put("month",month);
        Call<SiteTotalIncomeBean> call=loginInfoPost.GetPartnerZhexianIncome(map);
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
     * 合伙人获取场地设备收益
     * @param
     * @return
     */
    public void PartnerSiteIncomeInfo(String startime,String endtime,String page, final PartnerSiteIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerSiteIncomeBean> call=loginInfoPost.GetPartnerSiteIncomeInfo(map);
        call.enqueue(new Callback<PartnerSiteIncomeBean>() {
            @Override
            public void onResponse(Call<PartnerSiteIncomeBean> call,final Response<PartnerSiteIncomeBean> response) {
                PartnerSiteIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerSiteIncomeBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 合伙人点击场地，查看场地设备
     * @param
     * @return
     */
    public void PartnerSiteDeviceListIncomeInfo(String info_id,String startime,String endtime,String page, final PartnerSiteDeviceListIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("info_id",info_id);
        map.put("page",page);
        Call<PartnerSiteDeviceIncomeListBean> call=loginInfoPost.GetPartnerSiteDeviceListIncomeInfo(map);
        call.enqueue(new Callback<PartnerSiteDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerSiteDeviceIncomeListBean> call,final Response<PartnerSiteDeviceIncomeListBean> response) {
                PartnerSiteDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerSiteDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 合伙人查看设备流水activity
     * @param
     * @return
     */
    public void SiteGetDeviceIncomeDetails(String id,String startime,String endtime,String page, final DeviceIncomeDetailSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        map.put("id",id);
        Call<DeviceIncomeDetailBean> call=loginInfoPost.PartnerGetDeviceIncomeListInfo(map);
        call.enqueue(new Callback<DeviceIncomeDetailBean>() {
            @Override
            public void onResponse(Call<DeviceIncomeDetailBean> call,final Response<DeviceIncomeDetailBean> response) {
                DeviceIncomeDetailBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<DeviceIncomeDetailBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 合伙人查看设备收益frgment
     * @param
     * @return
     */
    public void PartnerGetDeviceIncomeList(String startime,String endtime,String page, final PartenerDeviceIncomeSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerDeviceIncomeListBean> call=loginInfoPost.PartnerGetDeviceIncomeInfo(map);
        call.enqueue(new Callback<PartnerDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerDeviceIncomeListBean> call,final Response<PartnerDeviceIncomeListBean> response) {
                PartnerDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }


    //以下是投资人的接口



    /**
     * 投资人获取综合统计收益
     * @param
     * @return
     */
    public void TZGetStatisticIncome(final PartnerStatisticIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        Call<PartnerStaticIncomeBean> call=loginInfoPost.TongzirenZongheTongji(map);
        call.enqueue(new Callback<PartnerStaticIncomeBean>() {
            @Override
            public void onResponse(Call<PartnerStaticIncomeBean> call,final Response<PartnerStaticIncomeBean> response) {
                PartnerStaticIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerStaticIncomeBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 投资人根据月份获取设备收益折线图数据
     * @param
     * @return
     */
    public void TzZhexiantu(String year,String month, final PartnerZhexianIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("year",year);
        map.put("month",month);
        Call<SiteTotalIncomeBean> call=loginInfoPost.TouzirenZhexiantu(map);
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
     * 合投资人获取场地收益列表
     * @param
     * @return
     */
    public void TzChangdiList(String startime,String endtime,String page, final PartnerSiteIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerSiteIncomeBean> call=loginInfoPost.TouzirenChangdiShouyiFrgment(map);
        call.enqueue(new Callback<PartnerSiteIncomeBean>() {
            @Override
            public void onResponse(Call<PartnerSiteIncomeBean> call,final Response<PartnerSiteIncomeBean> response) {
                PartnerSiteIncomeBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);
            }
            @Override
            public void onFailure(Call<PartnerSiteIncomeBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 投资人点击场地，查看场地设备
     * @param
     * @return
     */
    public void TzChangdiNeiDevice(String info_id,String startime,String endtime,String page, final PartnerSiteDeviceListIncomeSucess callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("info_id",info_id);
        map.put("page",page);
        Call<PartnerSiteDeviceIncomeListBean> call=loginInfoPost.TZChangdiNeiDeviceIncomeList(map);
        call.enqueue(new Callback<PartnerSiteDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerSiteDeviceIncomeListBean> call,final Response<PartnerSiteDeviceIncomeListBean> response) {
                PartnerSiteDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerSiteDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 投资人查看设备流水activity
     * @param
     * @return
     */
    public void TZDeviceLiushuiInfo(String id,String startime,String endtime,String page, final DeviceIncomeDetailSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        map.put("id",id);
        Call<DeviceIncomeDetailBean> call=loginInfoPost.TZShebeiLiushui(map);
        call.enqueue(new Callback<DeviceIncomeDetailBean>() {
            @Override
            public void onResponse(Call<DeviceIncomeDetailBean> call,final Response<DeviceIncomeDetailBean> response) {
                DeviceIncomeDetailBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<DeviceIncomeDetailBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 投资人查看设备收益frgment
     * @param
     * @return
     */
    public void TzDeviceIncomeFragment(String startime,String endtime,String page, final PartenerDeviceIncomeSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerDeviceIncomeListBean> call=loginInfoPost.TZShebeiShouyiFragment(map);
        call.enqueue(new Callback<PartnerDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerDeviceIncomeListBean> call,final Response<PartnerDeviceIncomeListBean> response) {
                PartnerDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 场地方低设备收益frgment
     * @param
     * @return
     */
    public void CDdishouyiIncomeFragment(String startime,String endtime,String page, final PartenerDeviceIncomeSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerDeviceIncomeListBean> call=loginInfoPost.CDDiShouyiFragment(map);
        call.enqueue(new Callback<PartnerDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerDeviceIncomeListBean> call,final Response<PartnerDeviceIncomeListBean> response) {
                PartnerDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }


    /**
     * 投资低设备收益frgment
     * @param
     * @return
     */
    public void InvestordishouyiIncomeFragment(String startime,String endtime,String page, final PartenerDeviceIncomeSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerDeviceIncomeListBean> call=loginInfoPost.InvestorDiShouyiFragment(map);
        call.enqueue(new Callback<PartnerDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerDeviceIncomeListBean> call,final Response<PartnerDeviceIncomeListBean> response) {
                PartnerDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }

    /**
     * 合伙人低设备收益frgment
     * @param
     * @return
     */
    public void PartnerdishouyiIncomeFragment(String startime,String endtime,String page, final PartenerDeviceIncomeSuccessCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(context).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(context).getAccessToken());
        map.put("start_time",startime);
        map.put("end_time",endtime);
        map.put("page",page);
        Call<PartnerDeviceIncomeListBean> call=loginInfoPost.PartnerDiShouyiFragment(map);
        call.enqueue(new Callback<PartnerDeviceIncomeListBean>() {
            @Override
            public void onResponse(Call<PartnerDeviceIncomeListBean> call,final Response<PartnerDeviceIncomeListBean> response) {
                PartnerDeviceIncomeListBean  bindingBean=response.body();
                callBack.Sucess(bindingBean);

            }
            @Override
            public void onFailure(Call<PartnerDeviceIncomeListBean> call, Throwable t) {
                callBack.Faile();
            }
        });
    }
    /**
     * 合伙人统计收益
     */
    public interface PartnerStatisticIncomeSucess{
        void Sucess(PartnerStaticIncomeBean bean);
        void Faile();
    }

    /**
     * 合伙人收益折线图
     */
    public interface PartnerZhexianIncomeSucess{
        void Sucess(SiteTotalIncomeBean bean);
        void Faile();
    }

    /**
     * 合伙人场地收益列表
     */
    public interface PartnerSiteIncomeSucess{
        void Sucess(PartnerSiteIncomeBean bean);
        void Faile();
    }
    /**
     * 合伙人场地设备收益列表
     */
    public interface PartnerSiteDeviceListIncomeSucess{
        void Sucess(PartnerSiteDeviceIncomeListBean bean);
        void Faile();
    }

    /**
     * 合伙人设备收益流水
     */
    public interface DeviceIncomeDetailSuccessCallback{
        void Sucess(DeviceIncomeDetailBean bean);
        void Faile();
    }

    /**
     * 合伙人设备收益
     */
    public interface PartenerDeviceIncomeSuccessCallback{
        void Sucess(PartnerDeviceIncomeListBean bean);
        void Faile();
    }
}

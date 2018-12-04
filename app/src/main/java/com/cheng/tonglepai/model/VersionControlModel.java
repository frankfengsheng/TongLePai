package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.CheckVersionBean;
import com.cheng.retrofit20.bean.VerasionControlBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.bean.WechatWithDrawBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/11/7 0007.
 */

public class VersionControlModel {
    Context context;
    public VersionControlModel(Context context){
        this.context=context;
    }

    /**
     * 判断是否需要更新
     * @param
     * @return
     */
    public void versionControlModle(String version, final VersionControlCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put("android_v", version);
        Call<VerasionControlBean> call=loginInfoPost.getVersionControl(map);
        call.enqueue(new Callback<VerasionControlBean>() {
            @Override
            public void onResponse(Call<VerasionControlBean> call,final Response<VerasionControlBean> response) {
                VerasionControlBean  bindingBean=response.body();
                callBack.callBackSuccess(bindingBean);

            }
            @Override
            public void onFailure(Call<VerasionControlBean> call, Throwable t) {
            }
        });

    }

    /**
     * 将用户当前版本记录到后台
     * @param
     * @return
     */
    public void checkVersion(String version,String telphone, final CheckVersionCallback callBack){

        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put("number", version);
        map.put("tel",telphone);
        map.put("type",1+"");
        Call<CheckVersionBean> call=loginInfoPost.checkVersion(map);
        call.enqueue(new Callback<CheckVersionBean>() {
            @Override
            public void onResponse(Call<CheckVersionBean> call,final Response<CheckVersionBean> response) {
                CheckVersionBean  bindingBean=response.body();
                callBack.callBackSuccess(bindingBean);

            }
            @Override
            public void onFailure(Call<CheckVersionBean> call, Throwable t) {
            }
        });

    }
    /**
     * 微信提现结果回调
     */
    public interface  VersionControlCallback{
        void callBackSuccess(VerasionControlBean bindingBean);
        void onFaile();
    }

    /**
     * 微信提现结果回调
     */
    public interface  CheckVersionCallback{
        void callBackSuccess(CheckVersionBean bindingBean);
        void onFaile();
    }
}

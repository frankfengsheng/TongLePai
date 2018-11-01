package com.cheng.tonglepai.model;

import android.content.Context;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
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

   public interface  BindSuccessCallBack{
        void bindSucess(WechatBindingBean bindingBean);
    }
}

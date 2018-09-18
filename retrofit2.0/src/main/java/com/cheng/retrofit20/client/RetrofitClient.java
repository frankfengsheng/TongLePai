package com.cheng.retrofit20.client;

import android.content.Context;

import com.cheng.retrofit20.converter.JsonConverterFactory;

import retrofit2.Retrofit;

/**
 * Created by wumengmeng on 2016/8/9/0009.
 */
public class RetrofitClient {
    private static Retrofit retrofit;

    private static void createRetrofitClient(Context context) {

        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OkHttpFactory.getOkHttpClient(context))

                        //baseUrl
                .baseUrl("http://app.tonglepai.cn/")

                        //gson转化器
                .addConverterFactory(JsonConverterFactory.create())

                .build();
    }

    public static Retrofit getRetrofit(Context context) {
        if (null == retrofit){
            synchronized (RetrofitClient.class){
                if (null == retrofit){
                    createRetrofitClient(context);
                }
            }
        }
        return retrofit;
    }
}

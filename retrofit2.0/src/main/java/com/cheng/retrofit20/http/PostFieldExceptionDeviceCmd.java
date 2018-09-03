package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by wumengmeng on 2016/9/22/0022.
 */
public class PostFieldExceptionDeviceCmd extends BaseHttpCmd {
    public static final String K_CONTACT = "contact";
    public static final String K_TEL = "tel";
    public static final String K_TYPE = "type";
    public static final String K_CONTENT = "content";
    public static final String K_LITPIC_1 = "litpic_1";
    public static final String K_LITPIC_2 = "litpic_2";
    public static final String K_LITPIC_3 = "litpic_3";
    public static final String K_LITPIC_4 = "litpic_4";
    public static final String K_STORE_INFO_ID = "store_info_id";
    public static final String K_DEVICE_CODE = "device_code";
    public static final String K_DEVICE_NAME = "device_name";
    public static final String K_DETAILS = "details";
    public static final String K_USERID = "userid";
    public static final String K_TOKEN = "token";
    private Context context;

    public PostFieldExceptionDeviceCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getPostFieldExceptionDevice(getParams().getParentParams());
    }
}

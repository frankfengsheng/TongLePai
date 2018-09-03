package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.PostExceptionDeviceCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class PostExceptionDeviceRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public PostExceptionDeviceRequest(Context context) {
        this.mContext = context;
    }


    public void requestPostExceptionDevice(String contact, String tel, String type, String content,
                                           String litpic_1, String litpic_2, String litpic_3, String litpic_4, String store_info_id,
                                           String device_code, String device_name, String details) {
        HttpCommand httpCmd = newHttpCommand(contact, tel, type, content, litpic_1, litpic_2, litpic_3, litpic_4, store_info_id, device_code, device_name, details);
        httpCmd.execute();
    }

    private RequestParams getParams(String contact, String tel, String type, String content,
                                    String litpic_1, String litpic_2, String litpic_3, String litpic_4, String store_info_id,
                                    String device_code, String device_name, String details) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(PostExceptionDeviceCmd.K_CONTACT, contact);
        parameters.putParams(PostExceptionDeviceCmd.K_TEL, tel);
        parameters.putParams(PostExceptionDeviceCmd.K_TYPE, type);
        parameters.putParams(PostExceptionDeviceCmd.K_CONTENT, content);
        parameters.putParams(PostExceptionDeviceCmd.K_LITPIC_1, litpic_1);
        parameters.putParams(PostExceptionDeviceCmd.K_LITPIC_2, litpic_2);
        parameters.putParams(PostExceptionDeviceCmd.K_LITPIC_3, litpic_3);
        parameters.putParams(PostExceptionDeviceCmd.K_LITPIC_4, litpic_4);
        parameters.putParams(PostExceptionDeviceCmd.K_STORE_INFO_ID, store_info_id);
        parameters.putParams(PostExceptionDeviceCmd.K_DEVICE_CODE, device_code);
        parameters.putParams(PostExceptionDeviceCmd.K_DEVICE_NAME, device_name);
        parameters.putParams(PostExceptionDeviceCmd.K_DETAILS, details);
        parameters.putParams(PostExceptionDeviceCmd.K_USERID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(PostExceptionDeviceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String contact, String tel, String type, String content,
                                       String litpic_1, String litpic_2, String litpic_3, String litpic_4, String store_info_id,
                                       String device_code, String device_name, String details) {
        HttpCommand httpCmd = new PostExceptionDeviceCmd(mContext, getParams(contact, tel, type, content, litpic_1, litpic_2, litpic_3, litpic_4, store_info_id, device_code, device_name, details));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                if (null != mListener) {
                    mListener.onFailed(msg, code);
                }
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        return httpCmd;
    }
}

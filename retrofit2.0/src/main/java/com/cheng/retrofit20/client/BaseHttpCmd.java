package com.cheng.retrofit20.client;

import android.content.Context;

import com.cheng.retrofit20.ApiService;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class BaseHttpCmd extends HttpCommand {
    protected Context context;
    protected ApiService apiService;
    protected RequestParams params;

    public BaseHttpCmd(Context context, RequestParams params) {
        this.context = context;
        this.params = params;
    }


    @Override
    public void execute() {
        retrofit = getRetrofit(context);
        getCall().enqueue(mCallback);
    }

    public RequestParams getParams() {
        if (params == null) {
            params = new RequestParams();
        }
//        String sessionId = HttpConfig.newInstance(context).getAccessToken();
//        String version = HttpConfig.newInstance(context).getVersion();
//        params.add(KEY_PARAM_SESSION_ID, sessionId);
//        params.add(KEY_VERSION, version);
//        params.add(KEY_PLATFORM, "Android");
//        params.add(KEY_IDENTIFICATION,HttpConfig.newInstance(context).getDeviceId());
//        params.add(KEY_IDFA,HttpConfig.newInstance(context).getDeviceId());
//        if (!params.getParentParams().containsKey(KEY_JDATA)) {
        configureExtraParams(params);
//        }
//        else {
//            HttpLog.resultJson(params.getParentParams().get(KEY_JDATA));
//        }
        return params;
    }

    protected void configureExtraParams(RequestParams params) {
        if (params.getChildParams().size() > 0) {
//            StringBuilder paramsStr = new StringBuilder();
            for (Map.Entry<String, Object> p : params.getChildParams().entrySet()) {
//                if (p.getValue() instanceof JSONArray) {
//                    paramsStr.append("\"" + p.getKey() + "\"").append(":").append(p.getValue().toString()).append(",");
//                }
//                else if (p.getValue() instanceof JSONObject) {
//                    paramsStr.append("\"" + p.getKey() + "\"").append(":").append(p.getValue().toString()).append(",");
//                }
//                else {
//                    paramsStr.append("\"" + p.getKey() + "\"").append(":").append("\"" + p.getValue() + "\"").append(",");
//                }

                params.add(p.getKey(), p.getValue().toString());
            }
//            String finalParams = "{" + paramsStr.deleteCharAt(paramsStr.length() - 1).toString() + "}";
//            HttpLog.resultJson(finalParams);
//            params.add(KEY_JDATA, finalParams);
        } else {
//            params.add(KEY_JDATA, "{}");
        }
    }

    public ApiService getApiService() {
        apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    protected Call<?> getCall() {
        return null;
    }

    @Override
    public void cancel() {
        if (getCall() != null) {
            getCall().cancel();
        }
    }
}

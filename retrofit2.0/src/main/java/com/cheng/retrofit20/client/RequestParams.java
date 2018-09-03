package com.cheng.retrofit20.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class RequestParams {

    private Map<String,Object> childParams = new HashMap<>();
    private Map<String ,String> parentParams = new HashMap<>();
    private JSONObject jsonObject= new JSONObject();;

    public void putJson(String key, Object value){
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getJson(){
        return jsonObject.toString();
    }

    public void putParams(String key, Object value){
        childParams.put(key, value);
    }

    public void add(String key, String value){
        parentParams.put(key, value);
    }

    public Map<String, Object> getChildParams() {
        return childParams;
    }

    public Map<String, String> getParentParams() {
        return parentParams;
    }
}

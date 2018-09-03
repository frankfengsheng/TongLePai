package com.cheng.retrofit20.client;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class BaseHttpRequest<T> {
    public IRequestListener<T> mListener;

    public void setListener(IRequestListener listener) {
        this.mListener = listener;
    }

    public interface IRequestListener<T> {
        void onSuccess(T data);
        void onFailed(String msg, int code);
    }
}

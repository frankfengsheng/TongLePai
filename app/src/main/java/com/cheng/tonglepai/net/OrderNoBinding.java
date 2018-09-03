package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.OrderNoResult;
import com.cheng.tonglepai.data.OrderNoData;

/**
 * Created by cheng on 2018/6/28.
 */

public class OrderNoBinding implements IUiDataBinding<OrderNoData, OrderNoResult> {
    private OrderNoResult mResult;
    private Context mContext;

    public OrderNoBinding(OrderNoResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public OrderNoData getUiData() {
        return getData();
    }

    private OrderNoData getData() {
        OrderNoData data = new OrderNoData();

        if (null == mResult || null == mResult.getData()) {
            return null;
        }
        data.setId(mResult.getData().getId());
        data.setOrder_number(mResult.getData().getOrder_number());
        data.setTimes(mResult.getData().getTimes());
        data.setTotal(mResult.getData().getTotal());
        return data;
    }
}

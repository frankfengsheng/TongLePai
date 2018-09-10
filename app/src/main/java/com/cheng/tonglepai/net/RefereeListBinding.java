package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.RefereeListResult;
import com.cheng.tonglepai.data.RefereeListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class RefereeListBinding implements IUiDataBinding<RefereeListData,RefereeListResult> {
    private RefereeListResult mResult;
    private Context mContext;

    public RefereeListBinding(RefereeListResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public RefereeListData getUiData() {
        return getData();
    }

    private RefereeListData getData() {
        RefereeListData uiList = new RefereeListData();
        List<RefereeListData.DataBean> dataList = new ArrayList<>();
        List<RefereeListResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            RefereeListResult.DataBean httpItem = httpList.get(i);
            RefereeListData.DataBean uiItem = new RefereeListData.DataBean();
            uiItem.setCreated(httpItem.getCreated());
            uiItem.setTel(httpItem.getTel());
            uiItem.setName(httpItem.getName());
            uiItem.setCity(httpItem.getCity());
            uiItem.setDevice_nums(httpItem.getDevice_nums());
            uiItem.setId(httpItem.getId());
            uiItem.setLevel(httpItem.getLevel());
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setStore_name(httpItem.getStore_name());
            dataList.add(uiItem);
        }
        uiList.setData(dataList);
        RefereeListData.PriceDataBean priceDataBean = new RefereeListData.PriceDataBean();
        priceDataBean.setLs_price(mResult.getPrice_data().getLs_price());
        priceDataBean.setNums(mResult.getPrice_data().getNums());
        priceDataBean.setZ_price(mResult.getPrice_data().getZ_price());
        priceDataBean.setInfo_nums(mResult.getPrice_data().getInfo_nums());
        priceDataBean.setXq_price(mResult.getPrice_data().getXq_price());
        uiList.setPrice_data(priceDataBean);
        return uiList;
    }
}

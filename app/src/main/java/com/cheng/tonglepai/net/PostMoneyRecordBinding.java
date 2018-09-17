package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.PostMoneyRecordResult;
import com.cheng.tonglepai.data.PostMoneyRecordData;
import com.cheng.tonglepai.tool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class PostMoneyRecordBinding implements IUiDataBinding<PostMoneyRecordData, PostMoneyRecordResult> {
    private PostMoneyRecordResult mResult;
    private Context mContext;

    public PostMoneyRecordBinding(PostMoneyRecordResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public PostMoneyRecordData getUiData() {
        return getData();
    }

    private PostMoneyRecordData getData() {
        PostMoneyRecordData uiList = new PostMoneyRecordData();
        List<PostMoneyRecordData.DataBean> dataList = new ArrayList<>();
        List<PostMoneyRecordResult.DataBean> httpList = mResult.getData();
//        if (httpList == null || httpList.size() == 0) {
//            return null;
//        }
        for (int i = 0; i < httpList.size(); i++) {
            PostMoneyRecordResult.DataBean httpItem = httpList.get(i);
            PostMoneyRecordData.DataBean uiItem = new PostMoneyRecordData.DataBean();
            uiItem.setPay_type(httpItem.getPay_type());
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setUpdated(TimeUtil.allTimeNew(httpItem.getUpdated()));
            dataList.add(uiItem);
        }
        uiList.setData(dataList);
        PostMoneyRecordData.PriceDataBean priceDataBean = new PostMoneyRecordData.PriceDataBean();
        priceDataBean.setZ_price(mResult.getPay_data().getZ_price());
        priceDataBean.setWj_price(mResult.getPay_data().getWj_price());
        priceDataBean.setYj_price(mResult.getPay_data().getYj_price());
        uiList.setPrice_data(priceDataBean);
        return uiList;
    }
}

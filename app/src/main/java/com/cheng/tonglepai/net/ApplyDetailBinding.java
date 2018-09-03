package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.ApplyDetailData;
import com.cheng.tonglepai.data.ApplyDetailUseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ApplyDetailBinding implements IUiDataBinding<List<ApplyDetailUseData>,ApplyDetailData> {
    private ApplyDetailData mResult;
    private Context mContext;

    public ApplyDetailBinding(ApplyDetailData result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<ApplyDetailUseData> getUiData() {
        return getData();
    }

    private List<ApplyDetailUseData> getData() {
        List<ApplyDetailUseData> uiList = new ArrayList<>();
        List<ApplyDetailData.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            ApplyDetailData.DataBean httpItem = httpList.get(i);
            ApplyDetailUseData uiItem = new ApplyDetailUseData();
            uiItem.setPrice(httpItem.getPrice());
            uiItem.setUpdated(httpItem.getUpdated());
            uiItem.setMonth(httpItem.getMonth());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.ReportRecordResult;
import com.cheng.tonglepai.data.ReportRecordData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ReportRecordBinding implements IUiDataBinding<List<ReportRecordData>,ReportRecordResult> {
    private ReportRecordResult mResult;
    private Context mContext;

    public ReportRecordBinding(ReportRecordResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<ReportRecordData> getUiData() {
        return getData();
    }

    private List<ReportRecordData> getData() {
        List<ReportRecordData> uiList = new ArrayList<>();
        List<ReportRecordResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            ReportRecordResult.DataBean httpItem = httpList.get(i);
            ReportRecordData uiItem = new ReportRecordData();
            uiItem.setCity(httpItem.getCity());
            uiItem.setUpdated(httpItem.getUpdated());
            uiItem.setName(httpItem.getName());
            uiItem.setRemarks(httpItem.getRemarks());
            uiItem.setTel(httpItem.getTel());
            uiItem.setStatus(httpItem.getStatus());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

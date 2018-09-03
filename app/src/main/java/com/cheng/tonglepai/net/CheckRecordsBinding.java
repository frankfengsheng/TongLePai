package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.CheckokRecordsResult;
import com.cheng.tonglepai.data.CheckokRecordsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class CheckRecordsBinding implements IUiDataBinding<CheckokRecordsData,CheckokRecordsResult> {
    private CheckokRecordsResult mResult;
    private Context mContext;

    public CheckRecordsBinding(CheckokRecordsResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public CheckokRecordsData getUiData() {
        return getData();
    }

    private CheckokRecordsData getData() {
        CheckokRecordsData data = new CheckokRecordsData();
        data.setDetails(mResult.getData().get(0).getDetails());
        data.setDevice_list(mResult.getData().get(0).getDevice_list());
        data.setId(mResult.getData().get(0).getId());
        data.setName(mResult.getData().get(0).getName());

        List<CheckokRecordsData.RecordsBean> dataList = new ArrayList<>();
        for (int i = 0; i < mResult.getData().get(0).getRecords().size(); i++) {
            CheckokRecordsData.RecordsBean dataBean = new CheckokRecordsData.RecordsBean();
            dataBean.setCreated(mResult.getData().get(0).getRecords().get(i).getCreated());
            dataBean.setStatus(mResult.getData().get(0).getRecords().get(i).getStatus());
            dataList.add(dataBean);
        }

        data.setRecords(dataList);
        return data;
    }
}

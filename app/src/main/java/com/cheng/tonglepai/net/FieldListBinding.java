package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.FieldListResult;
import com.cheng.tonglepai.data.FieldListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class FieldListBinding implements IUiDataBinding<List<FieldListData>,FieldListResult> {
    private FieldListResult mResult;
    private Context mContext;

    public FieldListBinding(FieldListResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<FieldListData> getUiData() {
        return getData();
    }

    private List<FieldListData> getData() {
        List<FieldListData> uiList = new ArrayList<>();
        List<FieldListResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            FieldListResult.DataBean httpItem = httpList.get(i);
            FieldListData uiItem = new FieldListData();
            uiItem.setStore_name(httpItem.getStore_name());
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setStatus(httpItem.getStatus());
            uiItem.setId(httpItem.getId());
            uiItem.setNums(httpItem.getNums());
            uiItem.setStart_nums(httpItem.getStart_nums());
            uiItem.setAddress(httpItem.getAddress());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

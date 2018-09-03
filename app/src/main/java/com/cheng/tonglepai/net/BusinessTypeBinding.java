package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.BusinessTypeResult;
import com.cheng.tonglepai.data.BusinessTypeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class BusinessTypeBinding implements IUiDataBinding<List<BusinessTypeData>, BusinessTypeResult> {
    private BusinessTypeResult mResult;
    private Context mContext;

    public BusinessTypeBinding(BusinessTypeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<BusinessTypeData> getUiData() {
        return getData();
    }

    private List<BusinessTypeData> getData() {
        List<BusinessTypeData> uiList = new ArrayList<>();
        List<BusinessTypeResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            BusinessTypeData dataBean = new BusinessTypeData();
            dataBean.setId(httpList.get(i).getId());
            dataBean.setName(httpList.get(i).getName());
            dataBean.setSort(httpList.get(i).getSort());
            dataBean.setStatus(httpList.get(i).getStatus());

            uiList.add(dataBean);
        }

        return uiList;
    }
}

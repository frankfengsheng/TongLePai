package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorFieldListResult;
import com.cheng.tonglepai.data.InvestorFieldListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class InverstorFieldListBinding implements IUiDataBinding<List<InvestorFieldListData>,InvestorFieldListResult> {
    private InvestorFieldListResult mResult;
    private Context mContext;

    public InverstorFieldListBinding(InvestorFieldListResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public List<InvestorFieldListData> getUiData() {
        return getData();
    }

    private List<InvestorFieldListData> getData() {
        List<InvestorFieldListData> uiList = new ArrayList<>();
        List<InvestorFieldListResult.DataBean> httpList = mResult.getData();
        if (httpList == null || httpList.size() == 0) {
            return null;
        }
        for (int i = 0; i < httpList.size(); i++) {
            InvestorFieldListResult.DataBean httpItem = httpList.get(i);
            InvestorFieldListData uiItem = new InvestorFieldListData();
            uiItem.setDetails(httpItem.getDetails());
            uiItem.setName(httpItem.getName());
            uiItem.setId(httpItem.getId());
            uiItem.setExpected_revenue(httpItem.getExpected_revenue());
            uiItem.setNums(httpItem.getNums());
            uiList.add(uiItem);
        }
        return uiList;
    }
}

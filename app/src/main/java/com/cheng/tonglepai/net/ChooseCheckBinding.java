package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorChooseCheckResult;
import com.cheng.tonglepai.data.InvestorChooseCheckData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class ChooseCheckBinding implements IUiDataBinding<InvestorChooseCheckData, InvestorChooseCheckResult> {
    private InvestorChooseCheckResult mResult;
    private Context mContext;

    public ChooseCheckBinding(InvestorChooseCheckResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public InvestorChooseCheckData getUiData() {
        return getData();
    }

    private InvestorChooseCheckData getData() {
        if (mResult == null) {
            return null;
        }
        InvestorChooseCheckData data = new InvestorChooseCheckData();
        data.setBetting_fee(mResult.getBetting_fee());
        data.setTotal(mResult.getTotal());
        data.setId(mResult.getId());
        data.setShipping_fee(mResult.getShipping_fee());

        List<InvestorChooseCheckResult.DataBean> dataBean = mResult.getData();
        List<InvestorChooseCheckData.DataBean> dataList = new ArrayList<>();

        for (int i = 0; i < dataBean.size(); i++) {
            InvestorChooseCheckData.DataBean dataOne = new InvestorChooseCheckData.DataBean();
            dataOne.setCode(dataBean.get(i).getCode());
            dataOne.setDevice_name(dataBean.get(i).getDevice_name());
            dataOne.setImg(dataBean.get(i).getImg());
            dataOne.setNums(dataBean.get(i).getNums());
            dataList.add(dataOne);
        }
        data.setData(dataList);
        return data;
    }
}

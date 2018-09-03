package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.InvestorAllIncomeResult;
import com.cheng.tonglepai.data.InvestorAllIncomeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/28.
 */

public class InvestorAllIncomeBinding implements IUiDataBinding<InvestorAllIncomeData,InvestorAllIncomeResult> {
    private InvestorAllIncomeResult mResult;
    private Context mContext;

    public InvestorAllIncomeBinding(InvestorAllIncomeResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public InvestorAllIncomeData getUiData() {
        return getData();
    }

    private InvestorAllIncomeData getData() {
        InvestorAllIncomeData data = new InvestorAllIncomeData();
        data.setRmb(mResult.getRmb());

        List<InvestorAllIncomeData.DataBean> dataList = new ArrayList<>();
        for (int i = 0; i < mResult.getData().size(); i++) {
            InvestorAllIncomeData.DataBean dataBean = new InvestorAllIncomeData.DataBean();
            dataBean.setDetails(mResult.getData().get(i).getDetails());
            dataBean.setDevice_list(mResult.getData().get(i).getDevice_list());
            dataBean.setId(mResult.getData().get(i).getId());
            dataBean.setMoney(mResult.getData().get(i).getMoney());
            dataBean.setStore_name(mResult.getData().get(i).getStore_name());
            List<InvestorAllIncomeData.DataBean.TjBean> beanList = new ArrayList<>();
            for (int j = 0; j < mResult.getData().get(i).getTj().size(); j++) {
                InvestorAllIncomeData.DataBean.TjBean bean = new InvestorAllIncomeData.DataBean.TjBean();
                bean.setDate(mResult.getData().get(i).getTj().get(j).getDate());
                bean.setDays(mResult.getData().get(i).getTj().get(j).getDays());
                bean.setPrice(mResult.getData().get(i).getTj().get(j).getPrice());
                beanList.add(bean);
            }
            dataBean.setTj(beanList);

            dataList.add(dataBean);
        }
        data.setData(dataList);

        return data;
    }
}

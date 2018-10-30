package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.UnpassFieldDetailResult;
import com.cheng.tonglepai.data.UnpassFieldDetailData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/6/28.
 */

public class UnpassFieldDetailBinding implements IUiDataBinding<UnpassFieldDetailData, UnpassFieldDetailResult> {
    private UnpassFieldDetailResult mResult;
    private Context mContext;

    public UnpassFieldDetailBinding(UnpassFieldDetailResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public UnpassFieldDetailData getUiData() {
        return getData();
    }

    private UnpassFieldDetailData getData() {
        UnpassFieldDetailData data = new UnpassFieldDetailData();

        if (mResult == null || mResult.getData().size() == 0) {
            return null;
        }

        UnpassFieldDetailResult.DataBean httpItem = mResult.getData().get(0);
        data.setId(httpItem.getId());  //设备id
        data.setCustomer_flow(httpItem.getCustomer_flow());  //客流量
        data.setDetails(httpItem.getDetails());  //详细地址
        data.setMobile(httpItem.getMobile()); //联系方式
        data.setStore_area_all(httpItem.getStore_area_all()); //门店面积
        data.setStore_area_able(httpItem.getStore_area_able()); //可用面积
        data.setTelphone(httpItem.getTelphone());  //公司电话
        data.setArea_temp(httpItem.getArea_temp());  //省市区地址
        data.setProvince(httpItem.getProvince()); //省id
        data.setCity(httpItem.getCity()); //市id
        data.setDistinct(httpItem.getDistinct());//区id
        data.setExpected_revenue(httpItem.getExpected_revenue()); //预计收益
        data.setStore_name(httpItem.getStore_name()); //门店名称
        data.setStore_business_id(httpItem.getStore_business_id());//经营属性

        data.setStore_exterior_1(httpItem.getStore_exterior_1());
        data.setStore_exterior_2(httpItem.getStore_exterior_2());
        data.setStore_exterior_3(httpItem.getStore_exterior_3());
        data.setStore_exterior_4(httpItem.getStore_exterior_4());
        data.setStore_interior_1(httpItem.getStore_interior_1());
        data.setStore_interior_2(httpItem.getStore_interior_2());
        data.setStore_interior_3(httpItem.getStore_interior_3());
        data.setStore_interior_4(httpItem.getStore_interior_4());

        data.setDevice_list(httpItem.getDevice_list());

        return data;
    }
}

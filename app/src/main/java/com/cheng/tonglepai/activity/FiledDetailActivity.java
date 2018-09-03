package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.FieldDetailAdapter;
import com.cheng.tonglepai.data.InvestorChooseCheckData;
import com.cheng.tonglepai.data.OrderNoData;
import com.cheng.tonglepai.net.ChooseCheckRequest;
import com.cheng.tonglepai.net.OrderNoRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.MyToast;

/**
 * Created by cheng on 2018/6/7.
 */

public class FiledDetailActivity extends TitleActivity {
    private TextView tvShowNum, tvAddress, tvAddressDetail,tvShippingFee;
    private Button btnComfirm;
    public static final String SHOP_NAME = "shop.name";
    public static final String SHOP_ADDRESS = "shop.address";
    public static final String FIELD_ID = "field.id";
    private LoadingDialog loadingDialog;
    private TextView tvNeedPay;
    private ListView lvFieldDetail;
    private FieldDetailAdapter mAdapter;
    private String payMoney = "";
    public static FiledDetailActivity filedDetailActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_filed_detail);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地详情");
        initView();
        initData();
    }

    private void initData() {
        loadingDialog.show();
        ChooseCheckRequest mRequest = new ChooseCheckRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorChooseCheckData>() {
            @Override
            public void onSuccess(InvestorChooseCheckData data) {
                if (null != data && data.getData().size() != 0) {
                    payMoney = data.getTotal();
                    tvNeedPay.setText("合计："+data.getTotal()+"(含运费￥"+data.getShipping_fee()+"）");
//                    SpannableStringBuilder style = new SpannableStringBuilder("支付  ￥" + data.getTotal());
//                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#454545")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#FE4146")), 2, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                    SpannableStringBuilder style1 = new SpannableStringBuilder("运费  ￥" + data.getShipping_fee());
//                    style1.setSpan(new ForegroundColorSpan(Color.parseColor("#454545")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    style1.setSpan(new ForegroundColorSpan(Color.parseColor("#ff813b")), 2, style1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tvShippingFee.setText(style1);
//                    tvNeedPay.setText(style);

                    int a = 0;
                    for (int i = 0; i < data.getData().size(); i++) {
                        a = a + data.getData().get(i).getNums();
                    }
                    tvShowNum.setText("共计" + a + "台");
                }
                mAdapter.setData(data.getData());
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                loadingDialog.dismiss();
                MyToast.showDialog(FiledDetailActivity.this, msg);
            }
        });
        mRequest.requestChooseCheck(getIntent().getStringExtra(FIELD_ID));
    }

    private void initView() {
        filedDetailActivity = this;
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");

        mAdapter = new FieldDetailAdapter(this);
        lvFieldDetail = (ListView) findViewById(R.id.lv_goods_list);
        lvFieldDetail.setAdapter(mAdapter);

        tvNeedPay = (TextView) findViewById(R.id.tv_need_pay_money);

        tvShowNum = (TextView) findViewById(R.id.tv_need_num);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvAddressDetail = (TextView) findViewById(R.id.tv_address_detail);
        btnComfirm = (Button) findViewById(R.id.btn_comfirm_input);

        tvAddress.setText(getIntent().getStringExtra(SHOP_NAME));
        tvAddressDetail.setText(getIntent().getStringExtra(SHOP_ADDRESS));

//        tvShippingFee = (TextView) findViewById(R.id.tv_shipping_fee);

        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toComfirm();
            }
        });
    }

    /**
     * 确认投放
     */
    private void toComfirm() {
        OrderNoRequest mRequest = new OrderNoRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<OrderNoData>() {
            @Override
            public void onSuccess(OrderNoData data) {
                Intent intent = new Intent(FiledDetailActivity.this, PayOnlineActivity.class);
                intent.putExtra(PayOnlineActivity.PAY_MONEY, data.getTotal());
                intent.putExtra(PayOnlineActivity.PAY_ORDER_NO, data.getOrder_number());
                intent.putExtra(PayOnlineActivity.PAY_ID, data.getId());
                startActivity(intent);
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(FiledDetailActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
        mRequest.requestOrderNo(getIntent().getStringExtra(FIELD_ID), payMoney);
    }
}

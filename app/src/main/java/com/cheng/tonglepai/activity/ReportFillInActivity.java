package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.RefereePreparationRequest;

/**
 * Created by cheng on 2018/9/10.
 */

public class ReportFillInActivity extends TitleActivity {
    private EditText etName, etPhone, etCity, etDetailMiaoshu;
    private TextView tvNum;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_fill_info);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("填写推荐人信息");
        initView();
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_report_name);
        etPhone = (EditText) findViewById(R.id.et_report_phone);
        etCity = (EditText) findViewById(R.id.et_report_city);
        etDetailMiaoshu = (EditText) findViewById(R.id.detail_miaoshu);
        tvNum = (TextView) findViewById(R.id.detail_miaoshu_num);
        etDetailMiaoshu.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                temp = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(temp.length() + "/500字");
                if (temp.length() > 500) {
                    Toast.makeText(ReportFillInActivity.this, "你输入的字符超出了限制！", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnSubmit = (Button) findViewById(R.id.btn_to_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSubmit();
            }
        });
    }

    private void toSubmit() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            Toast.makeText(ReportFillInActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            Toast.makeText(ReportFillInActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            Toast.makeText(ReportFillInActivity.this, "请输入城市", Toast.LENGTH_SHORT).show();
            return;
        }

        RefereePreparationRequest mRequest = new RefereePreparationRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                if (data.getStatus() == 1) {
                    Intent intent = new Intent(ReportFillInActivity.this,ReportResultActivity.class);
                    intent.putExtra(ReportResultActivity.IS_SUCCESS,true);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ReportFillInActivity.this,ReportResultActivity.class);
                    intent.putExtra(ReportResultActivity.IS_SUCCESS,false);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                Intent intent = new Intent(ReportFillInActivity.this,ReportResultActivity.class);
                intent.putExtra(ReportResultActivity.IS_SUCCESS,false);
                startActivity(intent);
                finish();
            }
        });
        mRequest.requestRefereePreparation(etName.getText().toString().trim(), etPhone.getText().toString().trim(), etCity.getText().toString().trim(), etDetailMiaoshu.getText().toString().trim());


    }
}

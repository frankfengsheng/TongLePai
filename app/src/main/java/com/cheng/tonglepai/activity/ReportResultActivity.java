package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/9/10.
 */

public class ReportResultActivity extends TitleActivity {
    private TextView tvReportResult,tv_content;
    public static final String IS_SUCCESS = "is.success";
    private boolean isSuccess;
    private Button btnSure;
    private ImageView ivResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_public_result_new);
        MyApplication.getInstance().addActivity(this);
        isSuccess = getIntent().getBooleanExtra(IS_SUCCESS, false);
        setMidTitle("推荐人报备");
        initView();
    }

    private void initView() {
        ivResult = (ImageView) findViewById(R.id.iv_result);
        tvReportResult = (TextView) findViewById(R.id.tv_report_result);
        tv_content = (TextView) findViewById(R.id.tv_content);
        if (isSuccess) {
            tvReportResult.setText("推荐信息有效时间为60天，尽快让推荐人加入童乐派吧!");
            ivResult.setImageDrawable(getResources().getDrawable(R.drawable.report_success));
            tv_content.setVisibility(View.VISIBLE);
        }
        else {
            tvReportResult.setText("被推荐人已报备，请勿重复报备");
            ivResult.setImageDrawable(getResources().getDrawable(R.drawable.yijing));
            tv_content.setVisibility(View.GONE);
        }

        btnSure = (Button) findViewById(R.id.btn_to_submit);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

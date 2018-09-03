package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.UnCheckResonResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.CheckDownRequest;

/**
 * Created by cheng on 2018/7/11.
 */

public class PostFieldResultActivity extends TitleActivity {
    public static final String STORE_INFO_ID = "store.info.id";
    private TextView tvFieldResult;
    private Button btnToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_post_field_result);
        MyApplication.getInstance().addActivity(this);
        initView();
        initData();
        setMidTitle("场地报备");
    }


    private void initView() {
        tvFieldResult = (TextView) findViewById(R.id.tv_result);
        btnToUpdate = (Button) findViewById(R.id.btn_to_update);

        btnToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostFieldResultActivity.this, RepostFieldActivity.class);
                intent.putExtra(RepostFieldActivity.STORE_INFO_ID, getIntent().getStringExtra(STORE_INFO_ID));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        CheckDownRequest mRequest = new CheckDownRequest(this);

        mRequest.setListener(new BaseHttpRequest.IRequestListener<UnCheckResonResult>() {
            @Override
            public void onSuccess(UnCheckResonResult data) {
                tvFieldResult.setText(data.getData().get(0).getReason());
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(PostFieldResultActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        mRequest.requestApplyMoney(HttpConfig.newInstance(this).getUserid(), getIntent().getStringExtra(STORE_INFO_ID));

    }
}

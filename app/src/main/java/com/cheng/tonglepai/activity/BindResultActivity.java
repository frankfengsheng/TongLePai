package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/8/14.
 */

public class BindResultActivity extends TitleActivity {
    public static final String DEVICE_ID = "device_id";
    public static final String INFO_ID = "info_id";
    public static final String ID_CODE = "id.code";
    public static final String TYPE = "type";
    private Button btnToNext;
    private String type = "";
    private TextView tvResult, tvContent,tvTips;
    private ImageView ivBindResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_bind_result);
        MyApplication.getInstance().addActivity(this);
        type = getIntent().getStringExtra(TYPE);
        if (type.equals("first"))
            setMidTitle("付款码绑定");
        else {
            setMidTitle("通信模块码绑定");
        }
        initView();
    }

    private void initView() {
        btnToNext = (Button) findViewById(R.id.btn_to_next);
        tvResult = (TextView) findViewById(R.id.tv_show_result);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        tvContent = (TextView) findViewById(R.id.tv_content_bind);
        ivBindResult = (ImageView) findViewById(R.id.iv_bind_result);

        if (type.equals("绑定成功"))
            tvResult.setText("绑定成功");
        else if (type.equals("first")) {
            tvResult.setText("绑定成功");
        } else
            tvResult.setText("绑定失败");


        if (type.equals("绑定成功")) {
            tvContent.setText("你已经成功绑定该设备，该设备的所有收益数据，可在首页中查看。");
            ivBindResult.setImageDrawable(getResources().getDrawable(R.drawable.bindsuccess));
            btnToNext.setText("确定");
            tvTips.setVisibility(View.INVISIBLE);
        } else if (type.equals("first")) {
            tvContent.setText("点击“下一步”，扫描通信模块二维码后，完成绑定。");
            btnToNext.setText("下一步");
            ivBindResult.setImageDrawable(getResources().getDrawable(R.drawable.bindsuccess));
            tvTips.setVisibility(View.VISIBLE);
        } else {
            tvContent.setText(type);
            btnToNext.setText("确定");
            ivBindResult.setImageDrawable(getResources().getDrawable(R.drawable.applyfalse));
            tvTips.setVisibility(View.INVISIBLE);
        }

        btnToNext.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (type.equals("first")) {
                    Intent intent = new Intent(BindResultActivity.this, QRCodingNextActivity.class);
                    intent.putExtra(QRCodingNextActivity.DEVICE_ID, getIntent().getStringExtra(DEVICE_ID));
                    intent.putExtra(QRCodingNextActivity.INFO_ID, getIntent().getStringExtra(INFO_ID));
                    intent.putExtra(QRCodingNextActivity.ID_CODE, getIntent().getStringExtra(ID_CODE));
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
    }
}

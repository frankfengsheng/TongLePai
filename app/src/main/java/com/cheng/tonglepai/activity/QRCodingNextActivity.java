package com.cheng.tonglepai.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.PostBindRequest;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by cheng on 2018/8/14.
 */

public class QRCodingNextActivity extends TitleActivity {
    public static final String DEVICE_ID = "device_id";
    public static final String INFO_ID = "info_id";
    public static final String ID_CODE = "id_code";
    QRCodeView zxing;
    ImageView iv;
    final int TO_LOGIN_CODE = 5005;
    String tempUrlBeforeLogin;
    private String codeType;
    public static final int KEY_REQUEST_QRCODING_LOGIN = 512;
    private String resultBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_qr_coding);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("通信模块码绑定");
        zxing = (ZXingView) findViewById(R.id.zxingview);
        zxing.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                //ToastUtil.show(result);
                dealResult(result);
                zxing.startSpot();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Toast.makeText(QRCodingNextActivity.this, "没有调开摄像头", Toast.LENGTH_LONG).show();
//                LogUtil.e("TAG","----onScanQRCodeOpenCameraError");
//                Intent intent = new Intent(QrCodingActivity.this, QCodeResultActivity.class);
//                intent.putExtra(QCodeResultActivity.RESULT_TITLE, "内容错误");
//                startActivity(intent);
//                finish();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        zxing.showScanRect();
    }

    /**
     * 处理结果
     *
     * @param result
     */
    private void dealResult(String result) {

        resultBack = result;

        if (!TextUtils.isEmpty(resultBack)) {
            if (resultBack.equals(getIntent().getStringExtra(ID_CODE))) {
                Toast.makeText(this,"请正确操作",Toast.LENGTH_LONG).show();
                finish();
            } else {
                PostBindRequest mRequest = new PostBindRequest(this);

                mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                    @Override
                    public void onSuccess(BaseHttpResult data) {
                        Intent intent = new Intent(QRCodingNextActivity.this, BindResultActivity.class);
                        intent.putExtra(BindResultActivity.TYPE, "绑定成功");
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Intent intent = new Intent(QRCodingNextActivity.this, BindResultActivity.class);
                        intent.putExtra(BindResultActivity.TYPE, msg);
                        startActivity(intent);
                        finish();
                    }
                });
                mRequest.requestPostBind(getIntent().getStringExtra(INFO_ID), getIntent().getStringExtra(DEVICE_ID), resultBack, getIntent().getStringExtra(ID_CODE));

            }
        }
//        Intent intent = new Intent(QRCodingNextActivity.this,BindResultActivity.class);
//        intent.putExtra(BindResultActivity.DEVICE_ID,getIntent().getStringExtra(DEVICE_ID));
//        intent.putExtra(BindResultActivity.INFO_ID,getIntent().getStringExtra(DEVICE_ID));
//        startActivity(intent);
//        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        zxing.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zxing.showScanRect();
        zxing.startSpot();
    }

    @Override
    protected void onStop() {
        zxing.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxing.onDestroy();
        super.onDestroy();
    }


}

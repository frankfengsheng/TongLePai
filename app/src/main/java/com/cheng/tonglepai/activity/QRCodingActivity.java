package com.cheng.tonglepai.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by cheng on 2018/8/14.
 */

public class QRCodingActivity extends TitleActivity {
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
        setMidTitle("付款码绑定");
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
                Toast.makeText(QRCodingActivity.this,"没有调开摄像头",Toast.LENGTH_LONG).show();
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

        Intent intent = new Intent(QRCodingActivity.this,BindResultActivity.class);
        intent.putExtra(BindResultActivity.DEVICE_ID,getIntent().getStringExtra("device.id"));
        intent.putExtra(BindResultActivity.INFO_ID,getIntent().getStringExtra("info.id"));
        intent.putExtra(BindResultActivity.ID_CODE,resultBack);
        intent.putExtra(BindResultActivity.TYPE,"first");
        startActivity(intent);
        finish();
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

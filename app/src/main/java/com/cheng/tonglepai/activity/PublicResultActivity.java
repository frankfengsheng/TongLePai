package com.cheng.tonglepai.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.MyChooseToastDialog;
import com.cheng.tonglepai.tool.MyToast;

/**
 * Created by cheng on 2018/7/31.
 */

public class PublicResultActivity extends TitleActivity {
    private TextView tvContent, tvContactUs;
    private Button btnContactUs;
    public static final String TYPE = "type";
    private int type = 0;
    private MyChooseToastDialog progressDialog;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_public_result);
        MyApplication.getInstance().addActivity(this);
        type = getIntent().getIntExtra(TYPE, 0);
        initView();
        if (type == 1) {
            setMidTitle("撤回设备");
        } else if (type == 2) {
            setMidTitle("设备转移");
        } else if (type == 3) {
            setMidTitle("我要升级");
        }
    }

    private void initView() {
        btnContactUs = (Button) findViewById(R.id.btn_contact_us);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContactUs = (TextView) findViewById(R.id.tv_contact_us);
        if (type == 1)
            tvContent.setText("我们已接受到您的撤回申请");
        else if (type == 2)
            tvContent.setText("我们已经接到您的设备转投申请");
        else if (type == 3) {
            tvContent.setText("我们已经接到您的申请");
            tvContactUs.setVisibility(View.VISIBLE);
            btnContactUs.setVisibility(View.VISIBLE);
        }

        btnContactUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog = MyToast.showChooseDialog(PublicResultActivity.this, "您确定拨打：400-0366118", "拨打电话", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 检查是否获得了权限（Android6.0运行时权限）
                        if (ContextCompat.checkSelfPermission(PublicResultActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            // 没有获得授权，申请授权
                            if (ActivityCompat.shouldShowRequestPermissionRationale(PublicResultActivity.this,
                                    Manifest.permission.CALL_PHONE)) {
                                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                // 弹窗需要解释为何需要该权限，再次请求授权
                                Toast.makeText(PublicResultActivity.this, "请授权！", Toast.LENGTH_LONG).show();

                                // 帮跳转到该应用的设置界面，让用户手动授权
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }else{
                                // 不需要解释为何需要该权限，直接请求授权
                                ActivityCompat.requestPermissions(PublicResultActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        }else {
                            // 已经获得授权，可以打电话
                            CallPhone();
                        }


                    }
                });
            }
        });


    }

    private void CallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "400-0366118");
        intent.setData(data);
        startActivity(intent);
    }
}

package com.cheng.tonglepai.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MainActivity;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.data.SmsLoginData;
import com.cheng.tonglepai.data.VersionControlData;
import com.cheng.tonglepai.net.LoginRequest;
import com.cheng.tonglepai.net.VesionControlRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.VerifyTimerUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cheng on 2018/7/16.
 */

public class LoginActivity extends TitleActivity {
    private Button btnGetCode, btnLogin, btnRegister;
    private EditText etPhoneNo, etVerifyCode;
    private VerifyTimerUtil timerUtil;
    private TextView tvToRegister, tvToRepsw;
    private LoadingDialog loadingDialog;
    private ImageView backiv;
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private String version;
    private String url;
    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("登录");
        checkVersion();
        initView();
    }

    private void checkVersion() {
        try {
            version = getVersionCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        VesionControlRequest mRequest = new VesionControlRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<VersionControlData>() {
            @Override
            public void onSuccess(VersionControlData data) {
                if (data.getAndroidis_update().equals("1")) {
                    if (Double.parseDouble(data.getAndroid_number()) > Double.parseDouble(version)) {
                        url = data.getAndroid_address();
                        showDialogUpdate();
                    }
                }
            }

            @Override
            public void onFailed(String msg, int code) {

            }
        });
        mRequest.requestVesionControl(Double.parseDouble(version) + "");

    }

    private void showDialogUpdate() {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置提示框的图标
//                        setIcon(R.mipmap.ic_launcher).
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).

                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }

    /*
 * 获取当前程序的版本号
 */
    private String getVersionCode() throws Exception {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

    private void loadNewVersionProgress() {

        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void initView() {
        loginActivity = this;
        backiv = (ImageView) findViewById(R.id.title_left_back_iv);
        backiv.setVisibility(View.GONE);

        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("请等待...");
        loadingDialog.setCancelable(false);
        btnLogin = (Button) findViewById(R.id.btn_to_login);

        etPhoneNo = (EditText) findViewById(R.id.et_phone_number);
        etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        btnGetCode = (Button) findViewById(R.id.tv_get_verify_code);
        btnRegister = (Button) findViewById(R.id.btn_to_register);
        tvToRegister = (TextView) findViewById(R.id.tv_to_register);
        tvToRepsw = (TextView) findViewById(R.id.tv_to_forget_psw);

        if (!TextUtils.isEmpty(HttpConfig.newInstance(this).getUserPwd())) {
            etVerifyCode.setText(HttpConfig.newInstance(this).getUserPwd());
        }

        if (!TextUtils.isEmpty(HttpConfig.newInstance(this).getUserTel())) {
            etPhoneNo.setText(HttpConfig.newInstance(this).getUserTel());
        }

//        btnGetCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(etPhoneNo.getText().toString().trim())) {
//                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!AccountValidatorUtil.isMobile(etPhoneNo.getText().toString().trim())) {
//                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                loadingDialog.show();
//                TestRequest mRequest = new TestRequest(LoginActivity.this);
//                mRequest.setListener(new BaseHttpRequest.IRequestListener<TestData>() {
//                    @Override
//                    public void onSuccess(TestData data) {
//                        Toast.makeText(LoginActivity.this, "发送成功，请查收！", Toast.LENGTH_SHORT).show();
//                        loadingDialog.dismiss();
//                        timerUtil.verifyCodeComeDown();
//                    }
//
//                    @Override
//                    public void onFailed(String msg, int code) {
//                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        loadingDialog.dismiss();
//                    }
//                });
//                mRequest.requestTest(etPhoneNo.getText().toString().trim());
//            }
//        });

        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvToRepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest mRequest = new LoginRequest(LoginActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<SmsLoginData>() {
                    @Override
                    public void onSuccess(SmsLoginData data) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.USER_PHONE, data.getTel());
                        HttpConfig.newInstance(LoginActivity.this).setUserTel(data.getTel());
                        HttpConfig.newInstance(LoginActivity.this).setUserPwd(etVerifyCode.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg, int code) {
                        Toast.makeText(LoginActivity.this, "密码错误，登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
                mRequest.requestLogin(etPhoneNo.getText().toString().trim(), etVerifyCode.getText().toString().trim());

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
//            finish();
            MyApplication.getInstance().exit();
            System.exit(0);
        }
    }


}

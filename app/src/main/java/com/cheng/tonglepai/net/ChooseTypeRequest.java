package com.cheng.tonglepai.net;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.ChooseTypeResult;
import com.cheng.retrofit20.http.ChooseTypeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ChooseTypeData;
import com.cheng.tonglepai.tool.MyChooseToastDialog;
import com.cheng.tonglepai.tool.MyToast;
import com.cheng.tonglepai.tool.SelectUnpassDialog;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ChooseTypeRequest extends BaseHttpRequest<ChooseTypeData> {

    private Context mContext;
    private SelectUnpassDialog progressDialog;
    private MyChooseToastDialog progressDialog1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public ChooseTypeRequest(Context context) {
        this.mContext = context;
    }


    public void requestChooseType(String tel, String type) {
        HttpCommand httpCmd = newHttpCommand(tel, type);
        httpCmd.execute();
    }

    private RequestParams getParams(String tel, String type) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ChooseTypeCmd.K_TEL, tel);
        parameters.putParams(ChooseTypeCmd.K_CHOOSE_TYPE, type);
        return parameters;
    }

    private HttpCommand newHttpCommand(String tel, String type) {
        HttpCommand httpCmd = new ChooseTypeCmd(mContext, getParams(tel, type));
        httpCmd.setCallback(new BaseCallback<ChooseTypeResult>() {
            @Override
            public void onSuccess(Response<ChooseTypeResult> response) {
                if (response.body().getStatus() == 5) {
                    Toast.makeText(mContext, response.body().getMsg().toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                if (response.body().getStatus() == 14) {
                    progressDialog = MyToast.showUnpassDialog((Activity) mContext,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    progressDialog.dismiss();
                                    progressDialog1 = MyToast.showChooseDialog((Activity) mContext, "您确定拨打：400-0366118", "拨打电话", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 检查是否获得了权限（Android6.0运行时权限）
                                            if (ContextCompat.checkSelfPermission(mContext,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                // 没有获得授权，申请授权
                                                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                                                        Manifest.permission.CALL_PHONE)) {
                                                    Toast.makeText(mContext, "请授权！", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                                                    intent.setData(uri);
                                                    mContext.startActivity(intent);
                                                } else {
                                                    ActivityCompat.requestPermissions((Activity) mContext,
                                                            new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                                }
                                            } else {
                                                CallPhone();
                                                progressDialog1.dismiss();
                                            }
                                        }
                                    });
                                }
                            });

                    return;

                }

                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(new ChooseTypeBinding(response.body(), mContext).getUiData());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                mListener.onFailed(msg, code);
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        return httpCmd;
    }

    private void CallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "400-0366118");
        intent.setData(data);
        mContext.startActivity(intent);
    }
}

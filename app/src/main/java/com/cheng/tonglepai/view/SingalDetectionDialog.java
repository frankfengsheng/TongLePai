package com.cheng.tonglepai.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.BindDeviceRequest;
import com.cheng.tonglepai.net.UserInfoRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/10/29 0029.
 */

public class SingalDetectionDialog extends AlertDialog {

    private View mRootView;
    private LinearLayout ly_singal;
    private LinearLayout ly_close;
    private ImageView iv_singnal;
    private boolean detectSucessed=false;
    private Context context;
    private String machineCode;
    public SingalDetectionDialog(Context context) {
        super(context);

    }

    public SingalDetectionDialog(Context context, @StyleRes int themeResId, View rootView,String machineCode) {
        super(context, themeResId);
        this.context=context;
        rootView= LayoutInflater.from(context).inflate(R.layout.singal_detection_dialog,null);
        this.mRootView = rootView;
        this.machineCode=machineCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(mRootView);
        ly_singal= (LinearLayout) mRootView.findViewById(R.id.ly_singal_dialog);
        ly_close= (LinearLayout) mRootView.findViewById(R.id.ly_close);
        ly_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detectSucessed) dismiss();
            }
        });
        iv_singnal= (ImageView) mRootView.findViewById(R.id.iv_singnal);
        detectionSignal(machineCode);
    }

    /**
     * 显示本dialog
     * 外部对象调用
     *
     * @param url 需要现实的网页
     */
    public void ShowDialog(String url) {
        if (this.isShowing()) return;

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置

        // 两句的顺序不能调换
        this.show();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
        window.getDecorView().setPadding(0, 0, 0, 0);
    }
    /**
     * 检测信号
     * @param
     * @return
     */
    public void detectionSignal(String machine_code){
        Retrofit retrofit =new RetrofitClient().getRetrofit(context);
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(getContext()).getUserid());
        map.put(UserInfoCmd.K_TOKEN,HttpConfig.newInstance(getContext()).getAccessToken());
        map.put("device_code",machine_code);
        Call<SingalDetectionBean> call=loginInfoPost.DetectionSignal(map);
        call.enqueue(new Callback<SingalDetectionBean>() {
            @Override
            public void onResponse(Call<SingalDetectionBean> call, Response<SingalDetectionBean> response) {
                    SingalDetectionBean bean=response.body();
                    if(bean!=null){
                        refreshUI(bean);
                    }
            }
            @Override
            public void onFailure(Call<SingalDetectionBean> call, Throwable t) {

            }
        });

    }
    private void refreshUI(SingalDetectionBean bean){
        detectSucessed=true;
        ly_singal.setVisibility(View.GONE);
        iv_singnal.setVisibility(View.VISIBLE);
        switch (bean.getStatus()){
            case 60://差
                iv_singnal.setImageResource(R.drawable.singnal_cha);
                break;
            case 61://正常
                iv_singnal.setImageResource(R.drawable.singnal_normal);
                break;
            case 62://离线
                iv_singnal.setImageResource(R.drawable.singnal_offline);
                break;
            case 63://设备不存在
                break;
        }
    }

}

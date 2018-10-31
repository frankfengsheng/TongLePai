package com.cheng.tonglepai.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;
import com.cheng.tonglepai.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/10/29 0029.
 */

public class OfflineIntructionDialog extends AlertDialog {

    private View mRootView;

    private boolean detectSucessed=false;
    private Context context;
    private String content;
    private TextView tv_content;
   // private ImageView iv_close;
    private Button btn_know;

    public OfflineIntructionDialog(Context context) {
        super(context);

    }

    public OfflineIntructionDialog(Context context, @StyleRes int themeResId, View rootView, String content) {
        super(context, themeResId);
        this.context=context;
        rootView= LayoutInflater.from(context).inflate(R.layout.offline_intruduction_dialog,null);
        this.mRootView = rootView;
        this.content=content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(mRootView);
        tv_content= (TextView) mRootView.findViewById(R.id.tv_offline_content);
      // iv_close= (ImageView) mRootView.findViewById(R.id.iv_offline_close);
        btn_know= (Button) mRootView.findViewById(R.id.btn_offline_know);
        tv_content.setText(content);
        tv_content.setText("1、如遇到用户支付完成，设备不启动，出现网络差或者设备离线提示页面，可根据以下操作查找原因，如有帮助，联系童乐派客服4000-366-118\n" +
                "2、查找用户支付的费用是否还在童乐派账号里面，可以通过童乐派公众号个人中心查看乐乐币情况\n" +
                "3、点击检测信号按钮，重新检测设备信号，直到设备在线可以正常启动设备\n" +
                "4、关掉电源，重新启动设备，模块在接通电源2分后，重新检测设备是否在线\n");
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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


}

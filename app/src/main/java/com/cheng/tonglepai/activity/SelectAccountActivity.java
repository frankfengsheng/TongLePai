package com.cheng.tonglepai.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.UserInfoCmd;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.ApplyDetailAdapter;
import com.cheng.tonglepai.data.ApplyDetailUseData;
import com.cheng.tonglepai.model.BindingModel;
import com.cheng.tonglepai.net.AllApplyDetailRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cheng on 2018/6/8.
 */

public class SelectAccountActivity extends TitleActivity  implements View.OnClickListener{
    RadioButton rb_bank;
    RadioButton rb_wechat;
    TextView tv_bankName;
    TextView tv_banAccount;
    TextView tv_binding;
    TextView tv_nickname;
    private String bankName,bankAccout,nickName,openId;
    private RelativeLayout rl_bank;
    private RelativeLayout rl_wechat;
    private IWXAPI iwxapi;
    private String wechat_return_OpenId,wechat_return_nickName;
    private int ACCOUNT_TYPE=0;
    private ImageView ivBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_select_account);
        MyApplication.getInstance().addActivity(this);
        regToWx();
        setMidTitle("选择账户");
        initView();
        initData();
    }

    private void initView() {
        ACCOUNT_TYPE=getIntent().getIntExtra("ACCOUNT_TYPE",0);
        bankName = getIntent().getStringExtra(ApplyMoneyActivityNew.BANK_NAME);
        bankAccout = getIntent().getStringExtra(ApplyMoneyActivityNew.BANK_ACCOUNT);
        openId=getIntent().getStringExtra(ApplyMoneyActivityNew.OPEN_ID);
        nickName=getIntent().getStringExtra(ApplyMoneyActivityNew.WX_NICKNAME);
        tv_nickname= (TextView) findViewById(R.id.tv_nickname);
        rl_bank= (RelativeLayout) findViewById(R.id.rl_bank);
        rl_wechat= (RelativeLayout) findViewById(R.id.rl_wechat);
        rb_bank= (RadioButton) findViewById(R.id.rb_bank);
        rb_wechat= (RadioButton) findViewById(R.id.rb_wechat);
        tv_bankName= (TextView) findViewById(R.id.bank_name);
        tv_banAccount= (TextView) findViewById(R.id.bank_account);
        tv_binding= (TextView) findViewById(R.id.tv_wechat_binding);
        ivBack= (ImageView) findViewById(R.id.title_left_back_iv);
        ivBack.setOnClickListener(this);
        tv_binding.setOnClickListener(this);
        rl_wechat.setOnClickListener(this);
        rl_bank.setOnClickListener(this);
        rb_bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_wechat.setChecked(false);
                    ACCOUNT_TYPE=1;
                }
            }
        });
        rb_wechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_bank.setChecked(false);
                    ACCOUNT_TYPE=0;
                }
            }
        });

        if(!TextUtils.isEmpty(bankAccout)&&bankAccout.length()>5){
            tv_bankName.setText(bankName);
            tv_banAccount.setText(bankAccout);
            if(ACCOUNT_TYPE==1)rb_bank.setChecked(true);
        }else {
            rl_bank.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(openId)&&openId.length()>5){
            tv_nickname.setText(nickName);
            tv_binding.setText("换绑其他微信");
            rb_wechat.setVisibility(View.VISIBLE);
            if(ACCOUNT_TYPE==0)rb_wechat.setChecked(true);
        }else {
           tv_binding.setText("绑定微信");
            rb_wechat.setVisibility(View.INVISIBLE);
        }

    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wechat_binding:
               /* if(!TextUtils.isEmpty(openId)&&openId.length()>5){
                            SendAuth.Req req = new SendAuth.Req();
                            req.scope = "snsapi_userinfo";
                            req.state = "wechat_sdk_微信登录";
                            iwxapi.sendReq(req);
                }else{
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_微信登录";
                    iwxapi.sendReq(req);
                }*/
               Intent intent=new Intent(SelectAccountActivity.this,WechatBindingActivity.class);
               startActivity(intent);
                break;
            case R.id.title_left_back_iv:
               goBack();
                break;
            case R.id.rl_wechat:
                if(!TextUtils.isEmpty(openId)&&openId.length()>5) {
                    rb_wechat.setChecked(true);
                    goBack();
                }
                break;
            case R.id.rl_bank:
                rb_bank.setChecked(true);
                goBack();
                break;
        }
    }
    public void regToWx(){
        //通过Factory工厂获取IWAPi实例
        iwxapi= WXAPIFactory.createWXAPI(this,LoginActivity.APP_ID,true);
        //将应用API注册到微信
        if(iwxapi!=null) iwxapi.registerApp(LoginActivity.APP_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        wechat_return_OpenId=intent.getStringExtra("wx_openid");
        wechat_return_nickName=intent.getStringExtra("wx_nickname");
        if(!TextUtils.isEmpty(wechat_return_OpenId)&&wechat_return_OpenId.length()>5&&!TextUtils.isEmpty(openId)&&openId.length()>5){
                DialogUtil.showChangDialog("是否确定换绑？", this, "确定", new DialogUtil.OnDialogSureClick() {
                    @Override
                    public void sureClick() {
                        new BindingModel(SelectAccountActivity.this).ChangeBindingWechat(wechat_return_OpenId, wechat_return_nickName, new BindingModel.BindSuccessCallBack() {
                            @Override
                            public void bindSucess(WechatBindingBean bindingBean) {
                                if (bindingBean.getStatus() == 71) {
                                    tv_nickname.setText(wechat_return_nickName);
                                    ToastUtil.showToast(SelectAccountActivity.this, "微信绑定成功");
                                    rb_wechat.setChecked(true);
                                    goBack();
                                }else {
                                    ToastUtil.showToast(SelectAccountActivity.this, bindingBean.getMsg());
                                }
                            }
                            @Override
                            public void binFailed() {
                                ToastUtil.showToast(SelectAccountActivity.this, "微信绑定失败，该账号已经绑定其他微信");
                            }
                        });
                    }
                });
        }else {
           /* DialogUtil.showChangDialog("您是否确定将微信号“"+wechat_return_nickName+"”绑定到当前童乐派帐号作为提现账户？", this, "确定", new DialogUtil.OnDialogSureClick() {
                @Override
                public void sureClick(){
                    new BindingModel(SelectAccountActivity.this).bindingWechat(wechat_return_OpenId, wechat_return_nickName, new BindingModel.BindSuccessCallBack() {
                        @Override
                        public void bindSucess(WechatBindingBean bindingBean) {
                            if(bindingBean.getStatus()==71){
                                ToastUtil.showToast(SelectAccountActivity.this,"微信绑定成功");
                                tv_nickname.setText(wechat_return_nickName);
                                rb_wechat.setChecked(true);
                                goBack();
                            }else if(bindingBean.getStatus()==70) {
                                ToastUtil.showToast(SelectAccountActivity.this,"微信绑定失败，该账号已经绑定其他微信");
                            }
                        }

                        @Override
                        public void binFailed() {
                            ToastUtil.showToast(SelectAccountActivity.this,"微信绑定失败");
                        }
                    });

                }});*/
            //showDialog(wechat_return_nickName);
        }
    }

    private void showDialog(final String nickName){
        final Dialog dialog=new Dialog(this);
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_binding_wechat,null);
        TextView tvCancle= (TextView) view.findViewById(R.id.tv_cancle_binding);
        TextView tvBinding= (TextView) view.findViewById(R.id.tv_binding);
        TextView tvContent= (TextView) view.findViewById(R.id.tv_wechat_binding_content);
        tvContent.setText("您是否确定将微信号“"+nickName+"”绑定到当前童乐派帐号作为提现账户？");
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BindingModel(SelectAccountActivity.this).bindingWechat(wechat_return_OpenId, wechat_return_nickName, new BindingModel.BindSuccessCallBack() {
                    @Override
                    public void bindSucess(WechatBindingBean bindingBean) {
                        if(bindingBean.getStatus()==71){
                            ToastUtil.showToast(SelectAccountActivity.this,"微信绑定成功");
                            tv_nickname.setText(wechat_return_nickName);
                            rb_wechat.setChecked(true);
                             goBack();
                        }else if(bindingBean.getStatus()==70) {
                            ToastUtil.showToast(SelectAccountActivity.this,"微信绑定失败，该账号已经绑定其他微信");
                        }
                    }

                    @Override
                    public void binFailed() {
                        ToastUtil.showToast(SelectAccountActivity.this,"微信绑定失败");
                    }
                });
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        dialog.show();
        window.setAttributes(lp);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void goBack(){
        Intent intent=new Intent(this,ApplyMoneyActivityNew.class);
        intent.putExtra("ACCOUNT_TYPE",ACCOUNT_TYPE);
        startActivity(intent);
        finish();
    }

}

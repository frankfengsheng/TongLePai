package com.cheng.tonglepai.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.bean.WXLoginGetAccesBean;
import com.cheng.retrofit20.bean.WXUserInfoBean;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.tonglepai.activity.ApplyMoneyActivityNew;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.activity.SelectAccountActivity;
import com.cheng.tonglepai.tool.ScalePagerTransformer;
import com.cheng.tonglepai.tool.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, LoginActivity.APP_ID);
        api.registerApp(LoginActivity.APP_ID);
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
                ToastUtil.showToast(this, "参数不合法");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //Toast.makeText(context, "授权 "+code, Toast.LENGTH_SHORT).show();
            /*	Toast.makeText(this,code, Toast.LENGTH_SHORT).show();*/
                //判断是微信登陆还是分享
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                    String code = sendResp.code;
                    String url =  "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+LoginActivity.APP_ID+"&secret=eef1e81f8bc4aa50ffe8dc11b3b696e7&code="+code+"&grant_type=authorization_code";
                    getAccesToken(url);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtil.showToast(this,"您取消了授权");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtil.showToast(this,"授权失败");
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    /**
     * 微信登录
     * @param
     * @return
     */
    public void getAccesToken(String url){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://test.tonglepai.cn/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Call<WXLoginGetAccesBean> call=  loginInfoPost.WXLogin(url);
        call.enqueue(new Callback<WXLoginGetAccesBean>() {
            @Override
            public void onResponse(Call<WXLoginGetAccesBean> call, Response<WXLoginGetAccesBean> response) {
                WXLoginGetAccesBean bean=response.body();
                if(bean!=null){
                    String url="https://api.weixin.qq.com/sns/userinfo?access_token="+bean.getAccess_token()+"&openid="+bean.getOpenid();
                    getUserInfo(url);
                }
            }
            @Override
            public void onFailure(Call<WXLoginGetAccesBean> call, Throwable t) {

            }
        });
    }
    /**
     * 微信登录
     * @param
     * @return
     */
    public void getUserInfo(String url){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://test.tonglepai.cn/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Call<WXUserInfoBean> call=  loginInfoPost.WXGetUserInfo(url);
        call.enqueue(new Callback<WXUserInfoBean>() {
            @Override
            public void onResponse(Call<WXUserInfoBean> call, Response<WXUserInfoBean> response) {
                WXUserInfoBean bean=response.body();
                if(bean!=null){
                    String openId=bean.getOpenid();
                    String nickName=bean.getNickname();
                    Intent intent=new Intent(WXEntryActivity.this, SelectAccountActivity.class);
                    intent.putExtra(ApplyMoneyActivityNew.OPEN_ID,openId);
                    intent.putExtra(ApplyMoneyActivityNew.WX_NICKNAME,nickName);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<WXUserInfoBean> call, Throwable t) {

            }
        });
    }


}

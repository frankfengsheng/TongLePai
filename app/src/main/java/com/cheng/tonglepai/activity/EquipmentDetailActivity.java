package com.cheng.tonglepai.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.ApiService;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.RetrofitClient;
import com.cheng.retrofit20.data.EquimentDetailResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.DeviceListCmd;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceListAdapter;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.DeviceListData;
import com.cheng.tonglepai.net.DeviceListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cheng on 2018/7/4.
 */


public class EquipmentDetailActivity extends TitleActivity implements View.OnClickListener,OnBannerListener{

    private Banner banner;
    private TextView tv_title;
    private TextView tv_content;
    private String device_model;
    private EquimentDetailResult equimentDetailResult;
    LoadingDialog loadingDialog=null;
    private List<String> img_list=new ArrayList<>();
    private Button btn_back;
    private TextView tv_nocontent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_equiment_detail);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("设备详情");
        initView();

    }

    private void initView() {
        tv_nocontent= (TextView) findViewById(R.id.tv_no_content);
        btn_back= (Button) findViewById(R.id.btn_to_back);
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        device_model=getIntent().getStringExtra("device_model");
        banner= (Banner) findViewById(R.id.banner);
        tv_title= (TextView) findViewById(R.id.tv_equipment_title);
        tv_content= (TextView) findViewById(R.id.tv_equipment_content);
        loadingDialog.show();
        getEquimentDetail();
        btn_back.setOnClickListener(this);
    }
    private void initBanner(){
        banner.setViewPagerIsScroll(true);
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(img_list);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(5000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_to_back:
                finish();
                break;
        }
    }


    /**
     * 获取设备端详情
     * @param
     * @return
     */
    public void getEquimentDetail(){
        Retrofit retrofit =new RetrofitClient().getRetrofit(getApplicationContext());
        ApiService loginInfoPost=retrofit.create(ApiService.class);
        Map map=new HashMap();
        map.put(DeviceListCmd.K_TOKEN, HttpConfig.newInstance(getApplicationContext()).getAccessToken());
        map.put(DeviceListCmd.K_USER_ID,HttpConfig.newInstance(getApplicationContext()).getUserid());
        map.put("device_model",device_model);
        Call<EquimentDetailResult> call=loginInfoPost.getEquimentDetail(map);
        call.enqueue(new Callback<EquimentDetailResult>() {
            @Override
            public void onResponse(Call<EquimentDetailResult> call, Response<EquimentDetailResult> response) {
                if(loadingDialog!=null)loadingDialog.dismiss();
                 equimentDetailResult=response.body();
                if(equimentDetailResult.getStatus()==1){
                    refreshUI();
                }
            }
            @Override
            public void onFailure(Call<EquimentDetailResult> call, Throwable t) {
                if(loadingDialog!=null)loadingDialog.dismiss();
                tv_nocontent.setVisibility(View.VISIBLE);
            }
        });
    }
    private void refreshUI()
    {
        if(equimentDetailResult!=null) {

        if(!TextUtils.isEmpty(equimentDetailResult.getTitel()))tv_title.setText(equimentDetailResult.getTitel());
        if(!TextUtils.isEmpty(equimentDetailResult.getContent()))tv_content.setText(equimentDetailResult.getContent());
        if (equimentDetailResult != null && equimentDetailResult.getData() != null && equimentDetailResult.getData().size() > 0) {
             for (EquimentDetailResult.DataBean dataBean:equimentDetailResult.getData()){
                  if(dataBean!=null&&dataBean.getImg()!=null){
                      img_list.add(dataBean.getImg());
                  }
             }
            initBanner();
        }

    }
    }

    @Override
    public void OnBannerClick(int position) {

    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
           // Glide.with(context).load((String) path).into(imageView);
            MyBitmapUtil myBitmapUtil = new MyBitmapUtil(context, (String)path);
            myBitmapUtil.display((String)path, imageView);
        }
    }

    @Override
    protected void onDestroy() {
        if(btn_back!=null){
            btn_back.setOnClickListener(null);
            btn_back=null;
        }
        tv_content=null;
        tv_title=null;
        if(banner!=null)
        {
            banner.releaseBanner();
            banner=null;
        }
        super.onDestroy();
    }
}

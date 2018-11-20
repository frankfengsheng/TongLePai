package com.cheng.tonglepai.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cheng.tonglepai.MainActivity;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.tool.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cheng on 2018/6/8.
 */

public class WechatBindingActivity extends TitleActivity implements View.OnClickListener{

    private ImageView iv_wechat_binding;
    private ImageView iv_wechat_binding2;
    private ImageView iv_wechat_binding3;
    private ImageView iv_wechat_binding4;
    private ImageView iv_wechat_binding5;
    private Dialog dialog;
    private ImageView mImageView;
    Bitmap mBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_wechat_binding);
        setMidTitle("微信绑定流程");
        MyApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        iv_wechat_binding= (ImageView) findViewById(R.id.iv_wechat_binding1);
        iv_wechat_binding2= (ImageView) findViewById(R.id.iv_wechat_binding2);
        iv_wechat_binding3= (ImageView) findViewById(R.id.iv_wechat_binding3);
        iv_wechat_binding4= (ImageView) findViewById(R.id.iv_wechat_binding4);
        iv_wechat_binding5= (ImageView) findViewById(R.id.iv_wechat_binding5);
        iv_wechat_binding.setOnClickListener(this);
        iv_wechat_binding2.setOnClickListener(this);
        iv_wechat_binding3.setOnClickListener(this);
        iv_wechat_binding4.setOnClickListener(this);
        iv_wechat_binding5.setOnClickListener(this);


    }



    private void showBigImage(int resource,boolean isCanDownload){
        dialog = new Dialog(WechatBindingActivity.this, R.style.AlertDialog_AppCompat_Light);
        mImageView = getImageView(resource);
        dialog.setContentView(mImageView);

        //大图的点击事件（点击让他消失）
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if(isCanDownload) {
            mImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //弹出的“保存图片”的Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(WechatBindingActivity.this);
                    builder.setItems(new String[]{"保存到本地"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                            checkPermission(mBitmap);
                            // saveCroppedImage(((BitmapDrawable) mImageView.getDrawable()).getBitmap());
                        }
                    });
                    builder.show();
                    return true;

                }
            });
        }
        dialog.show();
    }
    //动态的ImageView
    private ImageView getImageView(int resource){
        ImageView iv = new ImageView(this);
        //宽高
        iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //设置Padding
        iv.setPadding(20,20,20,20);
        //imageView设置图片
        InputStream is = getResources().openRawResource(resource);
        Drawable drawable = BitmapDrawable.createFromStream(is, null);
        iv.setImageDrawable(drawable);
        return iv;
    }

    //保存图片
    private void saveCroppedImage(Bitmap bmp) {
        String fileName=null;
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
        Log.i("absoulutePath",Environment.getExternalStorageDirectory().getAbsolutePath());
        File file = new File(dir);
        if (!file.exists())
            file.mkdir();
        try {
            File file1 = new File(dir + getTime() + ".jpg");
            fileName=file1.toString();
            FileOutputStream fos = new FileOutputStream(file1);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            ToastUtil.showToast(this,"图片保存成功");
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    bmp, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file1);
            intent.setData(uri);
            this.sendBroadcast(intent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_wechat_binding1:
                showBigImage(R.mipmap.wechat_binding1,true);
                break;
            case R.id.iv_wechat_binding2:
                showBigImage(R.mipmap.wechat_binding2,true);
                break;
            case R.id.iv_wechat_binding3:
                showBigImage(R.mipmap.wechat_binding3,true);
                break;
            case R.id.iv_wechat_binding4:
                showBigImage(R.mipmap.wechat_binding4,true);
                break;
            case R.id.iv_wechat_binding5:
                showBigImage(R.mipmap.wechat_binding5,true);
                break;
        }
    }

    private void checkPermission(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT >= 23) {
            int write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED || read != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            }else {
                saveCroppedImage(mBitmap);
            }
        }else {
            saveCroppedImage(mBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==300&&grantResults[0] == PackageManager.PERMISSION_GRANTED)  saveCroppedImage(mBitmap);//下载最新的版本程序;
    }

    private String getTime() {//可根据需要自行截取数据显示
        SimpleDateFormat simpleDateFormatt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormatt.format(date);
    }
}
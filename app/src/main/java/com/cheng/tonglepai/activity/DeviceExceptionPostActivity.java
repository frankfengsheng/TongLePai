package com.cheng.tonglepai.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.net.PostExceptionDeviceRequest;
import com.cheng.tonglepai.net.PostFieldDeviceRequest;
import com.cheng.tonglepai.tool.Base64BitmapUtil;
import com.cheng.tonglepai.tool.GetPathFromUri4kitkat;
import com.cheng.tonglepai.tool.MyToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/7/11.
 */

public class DeviceExceptionPostActivity extends TitleActivity implements CheckBox.OnCheckedChangeListener {
    private CheckBox cbOne, cbTwo, cbThree, cbFour, cbFive, cbSixm;
    private EditText etContactsName, etContactsPhone, etDetail;
    private Button btnToSubmit;
    private List<String> type = new ArrayList<>();
    private String exceptionType;
    public static final String DEVICE_CODE = "device.code";
    public static final String DEVICE_NAME = "device.name";
    public static final String DEVICE_ID = "device.id";
    public static final String DETAILS = "details";
    private TextView tvDeviceNo;
    private ImageView upPhotoOne, upPhotoTwo, upPhotoThree, upPhotoFour;
    private Bitmap bitmapOne, bitmapTwo, bitmapThree, bitmapFour;
    private String photoOne = "", photoTwo = "", photoThree = "", photoFour = "";
    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_device_exception);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("异常反馈");
        verifyStoragePermissions(this);
        initView();
    }

    private void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void initView() {
        cbOne = (CheckBox) findViewById(R.id.cb_one);
        cbTwo = (CheckBox) findViewById(R.id.cb_two);
        cbThree = (CheckBox) findViewById(R.id.cb_three);
        cbFour = (CheckBox) findViewById(R.id.cb_four);
        cbFive = (CheckBox) findViewById(R.id.cb_five);
        cbSixm = (CheckBox) findViewById(R.id.cb_six);
        cbOne.setOnCheckedChangeListener(this);
        cbTwo.setOnCheckedChangeListener(this);
        cbThree.setOnCheckedChangeListener(this);
        cbFour.setOnCheckedChangeListener(this);
        cbFive.setOnCheckedChangeListener(this);
        cbSixm.setOnCheckedChangeListener(this);

        tvDeviceNo = (TextView) findViewById(R.id.tv_device_no);
        tvDeviceNo.setText("设备编号：" + getIntent().getStringExtra(DEVICE_CODE));
        etContactsName = (EditText) findViewById(R.id.tv_contacts_name);
        etContactsPhone = (EditText) findViewById(R.id.tv_contacts_phone);
        etDetail = (EditText) findViewById(R.id.detail_miaoshu);
        btnToSubmit = (Button) findViewById(R.id.btn_to_submit);
        upPhotoOne = (ImageView) findViewById(R.id.up_photo_one);
        upPhotoTwo = (ImageView) findViewById(R.id.up_photo_two);
        upPhotoThree = (ImageView) findViewById(R.id.up_photo_three);
        upPhotoFour = (ImageView) findViewById(R.id.up_photo_four);

        upPhotoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, 1);
            }
        });

        upPhotoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, 2);
            }
        });

        upPhotoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, 3);
            }
        });

        upPhotoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, 4);
            }
        });

        btnToSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etContactsName.getText().toString().trim())) {
                    MyToast.showDialog(DeviceExceptionPostActivity.this, "请输入输入联系人姓名");
                    return;
                }
                if (TextUtils.isEmpty(etContactsPhone.getText().toString().trim())) {
                    MyToast.showDialog(DeviceExceptionPostActivity.this, "请输入输入联系人手机号");
                    return;
                }
                if (cbOne.isChecked()) {
                    type.add(cbOne.getText().toString());
                }
                if (cbTwo.isChecked()) {
                    type.add(cbTwo.getText().toString());
                }
                if (cbThree.isChecked()) {
                    type.add(cbThree.getText().toString());
                }
                if (cbFour.isChecked()) {
                    type.add(cbFour.getText().toString());
                }
                if (cbFive.isChecked()) {
                    type.add(cbFive.getText().toString());
                }
                if (cbSixm.isChecked()) {
                    type.add(cbSixm.getText().toString());
                }
                if (null == type || type.size() == 0) {
                    MyToast.showDialog(DeviceExceptionPostActivity.this, "请选择异常类型");
                    return;
                } else {
                    exceptionType = type.toString().substring(1, type.toString().length() - 1);
                }
                if (TextUtils.isEmpty(etContactsName.getText().toString().trim())) {
                    MyToast.showDialog(DeviceExceptionPostActivity.this, "请输入联系人姓名");
                    return;
                }

                if (null != bitmapOne)
                    photoOne = "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapOne);
                if (null != bitmapTwo)
                    photoTwo = "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapTwo);
                if (null != bitmapThree)
                    photoThree = "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapThree);
                if (null != bitmapFour)
                    photoFour = "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapFour);

                if (HttpConfig.newInstance(DeviceExceptionPostActivity.this).getUserType() == 2) {
                    PostExceptionDeviceRequest mRequest = new PostExceptionDeviceRequest(DeviceExceptionPostActivity.this);

                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            finish();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(DeviceExceptionPostActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                    mRequest.requestPostExceptionDevice(etContactsName.getText().toString().trim(), etContactsPhone.getText().toString().trim(), exceptionType, etDetail.getText().toString().trim()
                            , photoOne, photoTwo, photoThree, photoFour, getIntent().getStringExtra(DEVICE_ID), getIntent().getStringExtra(DEVICE_CODE), getIntent().getStringExtra(DEVICE_NAME), getIntent().getStringExtra(DETAILS));
                } else if (HttpConfig.newInstance(DeviceExceptionPostActivity.this).getUserType() == 3) {
                    PostFieldDeviceRequest mRequest = new PostFieldDeviceRequest(DeviceExceptionPostActivity.this);

                    mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                        @Override
                        public void onSuccess(BaseHttpResult data) {
                            finish();
                        }

                        @Override
                        public void onFailed(String msg, int code) {
                            Toast.makeText(DeviceExceptionPostActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                    mRequest.requestPostFieldDevice(etContactsName.getText().toString().trim(), etContactsPhone.getText().toString().trim(), exceptionType, etDetail.getText().toString().trim()
                            , photoOne, photoTwo, photoThree, photoFour, getIntent().getStringExtra(DEVICE_ID), getIntent().getStringExtra(DEVICE_CODE), getIntent().getStringExtra(DEVICE_NAME), getIntent().getStringExtra(DETAILS));
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setChecked(true);
            buttonView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            buttonView.setChecked(false);
            buttonView.setTextColor(Color.parseColor("#686868"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapOne = compressScale(bitmapOneOld);
                        setImg(bitmapOne, upPhotoOne);
                        upPhotoTwo.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;

            case 2://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapTwo = compressScale(bitmapOneOld);
                        setImg(bitmapTwo, upPhotoTwo);
                        upPhotoThree.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;

            case 3://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapThree = compressScale(bitmapOneOld);
                        setImg(bitmapThree, upPhotoThree);
                        upPhotoFour.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;

            case 4://这里的requestCode是我自己设置的，就是确定返回到那个Activity的标志
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapFour = compressScale(bitmapOneOld);
                        setImg(bitmapFour, upPhotoFour);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void setImg(Bitmap bitmap, ImageView imageView) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        if (bitmap.getWidth() <= screenWidth) {
            imageView.setImageBitmap(bitmap);
        } else {
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, screenWidth, bitmap.getHeight() * screenWidth / bitmap.getWidth(), true);
            imageView.setImageBitmap(bmp);
        }
    }

    public static Bitmap compressScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        float hh = 512f;
        float ww = 512f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        //return bitmap;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 40) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}

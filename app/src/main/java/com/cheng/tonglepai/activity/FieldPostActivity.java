package com.cheng.tonglepai.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.BaseBackResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceListAdapter;
import com.cheng.tonglepai.adapter.SelectPictureAdapter;
import com.cheng.tonglepai.data.BusinessTypeData;
import com.cheng.tonglepai.data.DeviceListData;
import com.cheng.tonglepai.data.JsonBean;
import com.cheng.tonglepai.net.BusinessTypeRequest;
import com.cheng.tonglepai.net.DeviceListRequest;
import com.cheng.tonglepai.net.PostFieldInfoRequest;
import com.cheng.tonglepai.tool.Base64BitmapUtil;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.GetJsonDataUtil;
import com.cheng.tonglepai.tool.GetPathFromUri4kitkat;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.cheng.tonglepai.tool.MyListView;
import com.cheng.tonglepai.tool.MyToast;
import com.cheng.tonglepai.view.MyGridView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/7/4.
 */


public class FieldPostActivity extends TitleActivity implements DeviceListAdapter.DeviceListListener,View.OnClickListener,AdapterView.OnItemClickListener {

    public static final String STORE_IN_ID = "store.in.id";
    private EditText etShopName, etShopArea, etDeviceArea, etVisitPeople, etCanIncome, etDetailAddress;
    private EditText etContacts, etCompanyPhone;
    private TextView etManageType, etUserAddress;
    private Bitmap bitmapOne, bitmapTwo, bitmapThree, bitmapFour;
    private Bitmap bitmapOneNext, bitmapTwoNext, bitmapThreeNext, bitmapFourNext;
  /*  private ImageView upPhotoOne, upPhotoTwo, upPhotoThree, upPhotoFour;
    private ImageView upPhotoOneNext, upPhotoTwoNext, upPhotoThreeNext, upPhotoFourNext;*/
    private Button btnSubmit, btnToFieldList;
    private TextView tv_totlPrice,tv_count;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private List<BusinessTypeData> typeList = new ArrayList<>();
    private List<String> typeName = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2IdItems = new ArrayList<>();  //id列表
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3IdItems = new ArrayList<>(); //id列表
    private Thread thread, thread1;
    private String provinceId, cityId, areaId;
    private boolean isLoaded, isLoaded1;
    private TextView tvAddressShow, tvManageShow;
    private String manageTypeId;
    private LoadingDialog loadingDialog;
    private MyListView lvDevice;
    private LinearLayout ly_select;
    private DeviceListAdapter mAdapter;
    private int allnum = 0;
    private double totalPrice=0.00;
    private ArrayList<DeviceListData> dataList = new ArrayList<>();
    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;
    private MyGridView gridView_selectimg1;
    private List<String> imgUrl_list1 = new ArrayList<String>();
    private SelectPictureAdapter selectPictureAdapter;
    private MyGridView gridView_selectimg2;
    private List<String> imgUrl_list2 = new ArrayList<String>();
    private SelectPictureAdapter selectPictureAdapter2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_post);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地报备");
        verifyStoragePermissions(this);
        initView();
        //initData();


    }

    private void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void initData() {
        DeviceListRequest mRequest = new DeviceListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<ArrayList<DeviceListData>>() {
            @Override
            public void onSuccess(final ArrayList<DeviceListData> data) {
                dataList = data;
                mAdapter.setData(data);

            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(FieldPostActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        mRequest.requestAllDevice();
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("请等待");
        loadingDialog.setCancelable(true);

        lvDevice = (MyListView) findViewById(R.id.lv_device_list);
        lvDevice.setVisibility(View.GONE);
        mAdapter = new DeviceListAdapter(this);
        lvDevice.setAdapter(mAdapter);
        mAdapter.setOnIPostPackageNoListener(this);
        lvDevice.setOnItemClickListener(this);

        ly_select= (LinearLayout) findViewById(R.id.ly_field_select_equiment);
        ly_select.setOnClickListener(this);

        gridView_selectimg1= (MyGridView) findViewById(R.id.gd_select_img1);
        selectPictureAdapter=new SelectPictureAdapter(this,imgUrl_list1,4);
        gridView_selectimg1.setAdapter(selectPictureAdapter);
        gridView_selectimg1.setOnItemClickListener(this);

        gridView_selectimg2= (MyGridView) findViewById(R.id.gd_select_img2);
        selectPictureAdapter2=new SelectPictureAdapter(this,imgUrl_list2,4);
        gridView_selectimg2.setAdapter(selectPictureAdapter2);
        gridView_selectimg2.setOnItemClickListener(this);

        btnToFieldList = (Button) findViewById(R.id.btn_to_field_list);
        btnToFieldList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FieldPostActivity.this, HasPostFieldFieldActivity.class);
                startActivity(intent);
            }
        });

        tvAddressShow = (TextView) findViewById(R.id.tv_address_show);


        tv_totlPrice = (TextView) findViewById(R.id.tv_equipment_total_price);
        tv_count= (TextView) findViewById(R.id.tv_equipment_count);

        tv_totlPrice.setText("总计：￥"+totalPrice);
        tv_count.setText(allnum+"台");

        etManageType = (TextView) findViewById(R.id.et_manage_type);//经营属性
        etShopName = (EditText) findViewById(R.id.et_shop_name);//门店名称
        etShopArea = (EditText) findViewById(R.id.et_shop_area);//门店面积
        etDeviceArea = (EditText) findViewById(R.id.et_device_area);//设备面积
        etVisitPeople = (EditText) findViewById(R.id.et_visit_people);//客流量
        etCanIncome = (EditText) findViewById(R.id.et_can_income);//预计收益
        etUserAddress = (TextView) findViewById(R.id.et_user_address);//所在地区
        etDetailAddress = (EditText) findViewById(R.id.et_detail_address);//详细地址
        etContacts = (EditText) findViewById(R.id.et_contacts_type);//联系方式
        etCompanyPhone = (EditText) findViewById(R.id.et_company_phone);//公司电话

      /*  upPhotoOne = (ImageView) findViewById(R.id.up_photo_one);
        upPhotoTwo = (ImageView) findViewById(R.id.up_photo_two);
        upPhotoThree = (ImageView) findViewById(R.id.up_photo_three);
        upPhotoFour = (ImageView) findViewById(R.id.up_photo_four);

        upPhotoOneNext = (ImageView) findViewById(R.id.up_photo_next_one);
        upPhotoTwoNext = (ImageView) findViewById(R.id.up_photo_next_two);
        upPhotoThreeNext = (ImageView) findViewById(R.id.up_photo_next_three);
        upPhotoFourNext = (ImageView) findViewById(R.id.up_photo_next_four);

        upPhotoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 1);
            }
        });

        upPhotoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 2);
            }
        });

        upPhotoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 3);
            }
        });

        upPhotoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 4);
            }
        });

        upPhotoOneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 5);
            }
        });

        upPhotoTwoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 6);
            }
        });

        upPhotoThreeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 7);
            }
        });

        upPhotoFourNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivityForResult(albumIntent, 8);
            }
        });*/

        btnSubmit = (Button) findViewById(R.id.btn_to_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toSubmit();
            }
        });

        etUserAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLoaded)
                    mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
                else
                    mHandler.sendEmptyMessage(MSG_LOAD_DATA);
            }
        });

        tvManageShow = (TextView) findViewById(R.id.tv_manage_type_show);
        tvManageShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoaded1)
                    mHandler1.sendEmptyMessage(2222);
                else
                    mHandler1.sendEmptyMessage(1111);
            }
        });
    }

    private void toSubmit() {

        if ("0".equals(tv_count.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请选择需要设备台数");
            return;
        }

        if (TextUtils.isEmpty(etManageType.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入经营属性");
            return;
        }
        if (TextUtils.isEmpty(etShopName.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入门店名称");
            return;
        }
        if (TextUtils.isEmpty(etShopArea.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入门店面积");
            return;
        }
        if (TextUtils.isEmpty(etDeviceArea.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入设备面积");
            return;
        }
        if (TextUtils.isEmpty(etVisitPeople.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入客流量");
            return;
        }
        if (TextUtils.isEmpty(etCanIncome.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入预计收益");
            return;
        }
        if (TextUtils.isEmpty(tvAddressShow.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入所在地区");
            return;
        }
        if (TextUtils.isEmpty(etDetailAddress.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入详细地址");
            return;
        }
        if (TextUtils.isEmpty(etContacts.getText().toString().trim())) {
            MyToast.showDialog(FieldPostActivity.this, "请输入联系方式");
            return;
        }
        loadingDialog.show();
        Map<String, Object> params = new HashMap<String, Object>();
        for (int i=0;i<imgUrl_list1.size();i++){
            Bitmap bitmap = BitmapFactory.decodeFile(imgUrl_list1.get(i));
            switch (i){
                case 0:
                    params.put("store_interior_1", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 1:
                    params.put("store_interior_2", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 2:
                    params.put("store_interior_3", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 3:
                    params.put("store_interior_4", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
            }
        }
        for (int i=0;i<imgUrl_list2.size();i++){
            Bitmap bitmap = BitmapFactory.decodeFile(imgUrl_list2.get(i));
            switch (i){
                case 0:
                    params.put("store_exterior_1", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 1:
                    params.put("store_exterior_2", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 2:
                    params.put("store_exterior_3", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
                case 3:
                    params.put("store_exterior_4", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmap));
                    break;
            }
        }
/*
        if (null != bitmapOne)
            params.put("store_interior_1", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapOne));
        if (null != bitmapTwo)
            params.put("store_interior_2", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapTwo));
        if (null != bitmapThree)
            params.put("store_interior_3", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapThree));
        if (null != bitmapFour)
            params.put("store_interior_4", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapFour));
        if (null != bitmapOneNext)
            params.put("store_exterior_1", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapOneNext));
        if (null != bitmapTwoNext)
            params.put("store_exterior_2", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapTwoNext));
        if (null != bitmapThreeNext)
            params.put("store_exterior_3", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapThreeNext));
        if (null != bitmapFourNext)
            params.put("store_exterior_4", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapFourNext));*/
        params.put("city", cityId);
        params.put("customer_flow", etVisitPeople.getText().toString().trim());
        params.put("details", etDetailAddress.getText().toString().trim());
        params.put("distinct", areaId);
        params.put("expected_revenue", etCanIncome.getText().toString().trim());
        params.put("mobile", etContacts.getText().toString().trim());
        params.put("province", provinceId);
        params.put("store_area_able", etDeviceArea.getText().toString().trim());
        params.put("store_area_all", etShopArea.getText().toString().trim());
        params.put("store_business_id", manageTypeId);
        params.put("store_name", etShopName.getText().toString().trim());
        params.put("telphone", etCompanyPhone.getText().toString().trim());
        params.put("userid", HttpConfig.newInstance(FieldPostActivity.this).getUserid());
        params.put("area_temp", tvAddressShow.getText().toString().trim());
        params.put("betting_fee",totalPrice);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getShowNO() != 0) {
                Map<String, Object> params1 = new HashMap<String, Object>();
                params1.put(dataList.get(i).getDevice_model(), dataList.get(i).getShowNO());
                list.add(params1);
            }
        }
        params.put("tlp", list);

        Log.i("----------", JSON.toJSONString(params));
        PostFieldInfoRequest mRequest = new PostFieldInfoRequest(this);

        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseBackResult>() {
            @Override
            public void onSuccess(BaseBackResult data) {
                Toast.makeText(FieldPostActivity.this, "报备成功", Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
                Intent intent = new Intent(FieldPostActivity.this, PostFieldSuccessActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(FieldPostActivity.this, msg, Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();

            }
        });

        mRequest.requestPostFieldInfo(JSON.toJSONString(params));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
           /* case 1:
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

            case 2:
                if (resultCode == RESULT_OK) {
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

            case 3:
                if (resultCode == RESULT_OK) {
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

            case 4:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapFour = compressScale(bitmapOneOld);
                        setImg(bitmapFour, upPhotoFour);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 5:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapOneNext = compressScale(bitmapOneOld);
                        setImg(bitmapOneNext, upPhotoOneNext);
                        upPhotoTwoNext.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 6:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapTwoNext = compressScale(bitmapOneOld);
                        setImg(bitmapTwoNext, upPhotoTwoNext);
                        upPhotoThreeNext.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 7:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapThreeNext = compressScale(bitmapOneOld);
                        setImg(bitmapThreeNext, upPhotoThreeNext);
                        upPhotoFourNext.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 8:
                if (resultCode == RESULT_OK) {
                    try {
                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        Bitmap bitmapOneOld = BitmapFactory.decodeFile(path);
                        bitmapFourNext = compressScale(bitmapOneOld);
                        setImg(bitmapFourNext, upPhotoFourNext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;*/
            case 0x1001:
                if(data!=null) {

                     List<DeviceListData> dataList1 = (List<DeviceListData>) data.getSerializableExtra("list");
                     if (dataList1 != null && dataList1.size() > 0) refreshList(dataList1);
                }
                break;
            case 0x121:
                if (resultCode == RESULT_OK) {

                        String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                        imgUrl_list1.add(path);
                        selectPictureAdapter.notifyDataSetChanged();

                }
                break;
            case 0x122:
                if (resultCode == RESULT_OK) {

                    String path = GetPathFromUri4kitkat.getPath(this, data.getData());
                    imgUrl_list2.add(path);
                    selectPictureAdapter2.notifyDataSetChanged();

                }
                break;
        }
    }
    private  void refreshList( List<DeviceListData> dataList1){
        totalPrice=0;
        allnum=0;
        for(DeviceListData data:dataList1){
            //判断之前是否选择过该设备，如果选择过，只需要在原来的数量上+，如果未选择过，将该设备加入列表
         /*   boolean hasSelected=false;
            for(DeviceListData one:dataList){
                if(one.getId().equals(data.getId())){
                    hasSelected=true;
                    one.setShowNO(one.getShowNO()+data.getShowNO());
                    break;
                }
            }
            if(!hasSelected){
                dataList.add(data);
            }*/
            totalPrice = totalPrice + (Double.parseDouble(data.getPrice_purchase()) * data.getShowNO());
            allnum = allnum + data.getShowNO();
        }
        dataList.clear();
        dataList.addAll(dataList1);
        lvDevice.setVisibility(View.VISIBLE);
        mAdapter.setData(dataList);

        tv_totlPrice.setText("总计：￥"+new DecimalFormat("#.00").format(totalPrice));
        tv_count.setText(allnum+"台");

    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                tvAddressShow.setText(tx);
                provinceId = options1Items.get(options1).getCode_id();
                cityId = options2IdItems.get(options1).get(options2);
                areaId = options3IdItems.get(options1).get(options2).get(options3);
                Log.i("-----------", provinceId + "   " + cityId + "    " + areaId);
//                Toast.makeText(FieldPostActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

//                        Toast.makeText(FieldPostActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(SmsLoginActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    showPickerView();
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(SmsLoginActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<String> CityIdList = new ArrayList<>();//该省的城市id列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            ArrayList<ArrayList<String>> Province_AreaIdList = new ArrayList<>();//该省的所有地区id列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getCity_name();
                String CityId = jsonBean.get(i).getCityList().get(c).getCode_id();
                CityList.add(CityName);//添加城市
                CityIdList.add(CityId);//添加城市Id
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                ArrayList<String> City_AreaIdList = new ArrayList<>();//该城市的所有地区id列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getChildren() == null
                        || jsonBean.get(i).getCityList().get(c).getChildren().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int j = 0; j < jsonBean.get(i).getCityList().get(c).getChildren().size(); j++) {
                        City_AreaList.add(jsonBean.get(i).getCityList().get(c).getChildren().get(j).getCity_name());
                        City_AreaIdList.add(jsonBean.get(i).getCityList().get(c).getChildren().get(j).getCode_id());
                    }

                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                Province_AreaIdList.add(City_AreaIdList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据和id
             */
            options2Items.add(CityList);
            options2IdItems.add(CityIdList);

            /**
             * 添加地区数据和id
             */
            options3Items.add(Province_AreaList);
            options3IdItems.add(Province_AreaIdList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1111:
                    if (thread1 == null) {//如果已创建就不再重新创建子线程了

                        thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中请求经营属性
                                initManageData();
                            }
                        });
                        thread1.start();
                    }
                    break;

                case 2222:
                    isLoaded1 = true;
                    showPickerView1();
                    break;

                case 3333:
                    break;
            }
        }
    };

    private void initManageData() {
        BusinessTypeRequest mRequest = new BusinessTypeRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<BusinessTypeData>>() {
            @Override
            public void onSuccess(List<BusinessTypeData> data) {
                typeList = data;
                for (int i = 0; i < data.size(); i++) {
                    typeName.add(data.get(i).getName());

                }
                mHandler1.sendEmptyMessage(2222);
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(FieldPostActivity.this, msg, Toast.LENGTH_LONG);
            }
        });
        mRequest.requestAllDevice();
    }

    private void showPickerView1() {
        OptionsPickerView pvOptions1 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = typeList.get(options1).getName();

                etManageType.setText(tx);
                manageTypeId = typeList.get(options1).getId();
            }
        })

                .setTitleText("选择经营属性")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions1.setPicker(typeName);//三级选择器
        pvOptions1.show();
    }

    @Override
    public void reduceNo(int position) {
        if (0 == allnum) {
            Toast.makeText(FieldPostActivity.this, "已经到了最小值", Toast.LENGTH_SHORT).show();
            return;
        }
        allnum = allnum - 1;
        totalPrice=totalPrice-Double.parseDouble(dataList.get(position).getPrice_purchase());
        tv_count.setText(allnum+"台");
        tv_totlPrice.setText("总计：￥"+new DecimalFormat("#.00").format(totalPrice));

    }

    @Override
    public void addNo(int position) {
        allnum = allnum + 1;
        totalPrice=totalPrice+Double.parseDouble(dataList.get(position).getPrice_purchase());
        tv_count.setText(allnum+"台");
        tv_totlPrice.setText("总计：￥"+new DecimalFormat("#.00").format(totalPrice));
        Log.i("走不走", dataList.get(0).getShowNO() + "我我");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_field_select_equiment:
                Intent intent=new Intent(getApplicationContext(),FieldPostSelectEquipmentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("dataList",dataList);
                intent.putExtras(bundle);
                startActivityForResult(intent,0x1001);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.lv_device_list:
                Intent intent=new Intent(getApplicationContext(), EquipmentDetailActivity.class);
                intent.putExtra("device_model",dataList.get(position).getDevice_model());
                startActivity(intent);
                break;
            case R.id.gd_select_img1:
                if(position>=imgUrl_list1.size()) {
                    Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(albumIntent, 0x121);
                }
                break;
            case R.id.gd_select_img2:
                if(position>=imgUrl_list2.size()) {
                    Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(albumIntent, 0x122);
                }
                break;
        }

    }
}

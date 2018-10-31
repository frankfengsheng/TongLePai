package com.cheng.tonglepai.activity;

import android.annotation.SuppressLint;
import android.app.admin.DeviceAdminInfo;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceListAdapter;
import com.cheng.tonglepai.adapter.SelectPictureAdapter;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.BusinessTypeData;
import com.cheng.tonglepai.data.DeviceListData;
import com.cheng.tonglepai.data.JsonBean;
import com.cheng.tonglepai.data.UnpassFieldDetailData;
import com.cheng.tonglepai.net.BusinessTypeRequest;
import com.cheng.tonglepai.net.DeviceListRequest;
import com.cheng.tonglepai.net.RepostFieldInfoRequest;
import com.cheng.tonglepai.net.UnpassFieldDetailRequest;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheng on 2018/7/23.
 */

public class RepostFieldActivity extends TitleActivity implements DeviceListAdapter.DeviceListListener,View.OnClickListener,AdapterView.OnItemClickListener {
    public static final String STORE_INFO_ID = "store.info.id";
    private EditText etShopName, etShopArea, etDeviceArea, etVisitPeople, etCanIncome, etDetailAddress, etContacts, etCompanyPhone;
    private ImageView upPhotoOne, upPhotoTwo, upPhotoThree, upPhotoFour;
    private ImageView upPhotoOneNext, upPhotoTwoNext, upPhotoThreeNext, upPhotoFourNext;
    private List<BusinessTypeData> typeList = new ArrayList<>();
    private TextView etUserAddress, etManageType, tvAddressShow;
    private TextView tvNumAll;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2IdItems = new ArrayList<>();  //id列表
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3IdItems = new ArrayList<>(); //id列表
    private Thread thread, thread1;
    private String provinceId, cityId, areaId;
    private Bitmap bitmapOne, bitmapTwo, bitmapThree, bitmapFour;
    private Bitmap bitmapOneNext, bitmapTwoNext, bitmapThreeNext, bitmapFourNext;
    private Button btnSubmit;
    private boolean oneCanshow, twoCanshow, threeCanshow, fourCanshow;
    private boolean oneCanshowNext, twoCanshowNext, threeCanshowNext, fourCanshowNext;
    private LoadingDialog loadingDialog;
    private boolean isLoaded, isLoaded1;
    private ArrayList<DeviceListData> dataList = new ArrayList<>();
    private DeviceListAdapter mAdapter;
    private MyListView lvDevice;
    private int allnum = 0;
    private double totalPrice;
   // private List<UnpassFieldDetailData.DeviceListBean> deviceListBeen = new ArrayList<>();
    private List<Map> deviceListBeen=new ArrayList<>();
    private String manageTypeId;
    private List<String> typeName = new ArrayList<>();
    private TextView tvManageShow;
    private Button btnToFieldList;
    private String storeInfoId;
    private UnpassFieldDetailData mData;
    private LinearLayout ly_select;
    private TextView tv_totlPrice;//总价


   /* private MyGridView gridView_selectimg1;
    private List<String> imgUrl_list1 = new ArrayList<String>();
    private SelectPictureAdapter selectPictureAdapter;

    private MyGridView gridView_selectimg2;
    private List<String> imgUrl_list2 = new ArrayList<String>();
    private SelectPictureAdapter selectPictureAdapter2;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_repost);
        MyApplication.getInstance().addActivity(this);
        initView();
        initData();
        initDeviceData();
        setMidTitle("场地报备");
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);
        ly_select= (LinearLayout) findViewById(R.id.ly_field_select_equiment);
        lvDevice = (MyListView) findViewById(R.id.lv_device_list);
        mAdapter = new DeviceListAdapter(this);
        lvDevice.setAdapter(mAdapter);
        mAdapter.setOnIPostPackageNoListener(this);
        lvDevice.setOnItemClickListener(this);
        ly_select.setOnClickListener(this);

      /*  gridView_selectimg1= (MyGridView) findViewById(R.id.gd_select_img1);
        selectPictureAdapter=new SelectPictureAdapter(this,imgUrl_list1,4);
        gridView_selectimg1.setAdapter(selectPictureAdapter);
        gridView_selectimg1.setOnItemClickListener(this);

        gridView_selectimg2= (MyGridView) findViewById(R.id.gd_select_img2);
        selectPictureAdapter2=new SelectPictureAdapter(this,imgUrl_list2,4);
        gridView_selectimg2.setAdapter(selectPictureAdapter2);
        gridView_selectimg2.setOnItemClickListener(this);*/

        tvAddressShow = (TextView) findViewById(R.id.tv_address_show);


        etManageType = (TextView) findViewById(R.id.et_manage_type);//经营属性
        etShopName = (EditText) findViewById(R.id.et_shop_name);//门店名称
        etShopArea = (EditText) findViewById(R.id.et_shop_area);//门店面积
        etDeviceArea = (EditText) findViewById(R.id.et_device_area);//设备面积
        etVisitPeople = (EditText) findViewById(R.id.et_visit_people);//客流量
        etCanIncome = (EditText) findViewById(R.id.et_can_income);//预计收益
        etUserAddress = (TextView) findViewById(R.id.et_user_address);//选择所在地区
        etDetailAddress = (EditText) findViewById(R.id.et_detail_address);//详细地址
        etContacts = (EditText) findViewById(R.id.et_contacts_type);//联系方式
        etCompanyPhone = (EditText) findViewById(R.id.et_company_phone);//公司电话

        upPhotoOne = (ImageView) findViewById(R.id.up_photo_one);
        upPhotoTwo = (ImageView) findViewById(R.id.up_photo_two);
        upPhotoThree = (ImageView) findViewById(R.id.up_photo_three);
        upPhotoFour = (ImageView) findViewById(R.id.up_photo_four);

        upPhotoOneNext = (ImageView) findViewById(R.id.up_photo_next_one);
        upPhotoTwoNext = (ImageView) findViewById(R.id.up_photo_next_two);
        upPhotoThreeNext = (ImageView) findViewById(R.id.up_photo_next_three);
        upPhotoFourNext = (ImageView) findViewById(R.id.up_photo_next_four);



        btnToFieldList = (Button) findViewById(R.id.btn_to_field_list);
        btnToFieldList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepostFieldActivity.this, HasPostFieldFieldActivity.class);
                startActivity(intent);
            }
        });

        tvNumAll = (TextView) findViewById(R.id.tv_equipment_count);
        tv_totlPrice = (TextView) findViewById(R.id.tv_equipment_total_price);
        btnSubmit = (Button) findViewById(R.id.btn_to_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        upPhotoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(1);
            }
        });

        upPhotoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(2);
            }
        });

        upPhotoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(3);
            }
        });

        upPhotoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(4);
            }
        });

        upPhotoOneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(5);
            }
        });

        upPhotoTwoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(6);
            }
        });

        upPhotoThreeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(7);
            }
        });

        upPhotoFourNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto(8);
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

    private void goPhoto(int i) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, i);
    }

    private void initData() {
        loadingDialog.show();
        UnpassFieldDetailRequest mRequest = new UnpassFieldDetailRequest(this);

        mRequest.setListener(new BaseHttpRequest.IRequestListener<UnpassFieldDetailData>() {
            @Override
            public void onSuccess(UnpassFieldDetailData data) {
                mData = data;
//                etManageType.setText(data.getStore_business_id());
                storeInfoId = data.getId();
                etShopName.setText(data.getStore_name());
                etShopArea.setText(data.getStore_area_all());
                etDeviceArea.setText(data.getStore_area_able());
                etVisitPeople.setText(data.getCustomer_flow());
                etCanIncome.setText(data.getExpected_revenue());
                etDetailAddress.setText(data.getDetails());
                etContacts.setText(data.getMobile());
                etCompanyPhone.setText(data.getTelphone());
                tvAddressShow.setText(data.getArea_temp());
                cityId = data.getCity();
                areaId = data.getDistinct();
                provinceId = data.getProvince();

                if (!TextUtils.isEmpty(data.getStore_exterior_1())) {
                    new MyAsyncTask5().execute(data.getStore_exterior_1());
                    oneCanshow = true;
                 /*   imgUrl_list2.add(data.getStore_exterior_1());
                    selectPictureAdapter.notifyDataSetChanged();*/
                    upPhotoTwoNext.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_exterior_1());
                    myBitmapUtil.display(data.getStore_exterior_1(), upPhotoOneNext);
                }
                if (!TextUtils.isEmpty(data.getStore_exterior_2())) {
                    new MyAsyncTask6().execute(data.getStore_exterior_2());
                  /*  imgUrl_list2.add(data.getStore_exterior_1());
                    selectPictureAdapter.notifyDataSetChanged();*/
                    twoCanshow = true;
                    upPhotoThreeNext.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_exterior_2());
                    myBitmapUtil.display(data.getStore_exterior_2(), upPhotoTwoNext);
                }
                if (!TextUtils.isEmpty(data.getStore_exterior_3())) {
                    new MyAsyncTask7().execute(data.getStore_exterior_3());
                  /*  imgUrl_list2.add(data.getStore_exterior_1());
                    selectPictureAdapter.notifyDataSetChanged();*/
                    threeCanshow = true;
                    upPhotoFourNext.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_exterior_3());
                    myBitmapUtil.display(data.getStore_exterior_3(), upPhotoThreeNext);
                }
                if (!TextUtils.isEmpty(data.getStore_exterior_4())) {
                    new MyAsyncTask8().execute(data.getStore_exterior_4());
                   /* imgUrl_list2.add(data.getStore_exterior_1());
                    selectPictureAdapter.notifyDataSetChanged();*/
                    fourCanshow = true;
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_exterior_4());
                    myBitmapUtil.display(data.getStore_exterior_4(), upPhotoFourNext);
                }
                if (!TextUtils.isEmpty(data.getStore_interior_1())) {
                    new MyAsyncTask().execute(data.getStore_interior_1());
                    oneCanshowNext = true;
                    upPhotoTwo.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_interior_1());
                    myBitmapUtil.display(data.getStore_interior_1(), upPhotoOne);
                }
                if (!TextUtils.isEmpty(data.getStore_interior_2())) {
                    new MyAsyncTask2().execute(data.getStore_interior_2());
                    twoCanshowNext = true;
                    upPhotoThree.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_interior_2());
                    myBitmapUtil.display(data.getStore_interior_2(), upPhotoTwo);
                }
                if (!TextUtils.isEmpty(data.getStore_interior_3())) {
                    new MyAsyncTask3().execute(data.getStore_interior_3());
                    threeCanshowNext = true;
                    upPhotoFour.setVisibility(View.VISIBLE);
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_interior_3());
                    myBitmapUtil.display(data.getStore_interior_3(), upPhotoThree);
                }
                if (!TextUtils.isEmpty(data.getStore_interior_4())) {
                    new MyAsyncTask4().execute(data.getStore_interior_4());
                    fourCanshowNext = true;
                    MyBitmapUtil myBitmapUtil = new MyBitmapUtil(RepostFieldActivity.this, data.getStore_interior_4());
                    myBitmapUtil.display(data.getStore_interior_4(), upPhotoFour);
                }

                deviceListBeen = data.getDevice_list();

                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(RepostFieldActivity.this, msg, Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
            }
        });

        mRequest.requestUnpassFieldDetail(getIntent().getStringExtra(STORE_INFO_ID));
    }

    private void submit() {
        if ("0".equals(tvNumAll.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请选择需要设备台数");
            return;
        }

        if (TextUtils.isEmpty(etManageType.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入经营属性");
            return;
        }
        if (TextUtils.isEmpty(etShopName.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入门店名称");
            return;
        }
        if (TextUtils.isEmpty(etShopArea.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入门店面积");
            return;
        }
        if (TextUtils.isEmpty(etDeviceArea.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入设备面积");
            return;
        }
        if (TextUtils.isEmpty(etVisitPeople.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入客流量");
            return;
        }
        if (TextUtils.isEmpty(etCanIncome.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入预计收益");
            return;
        }
        if (TextUtils.isEmpty(etUserAddress.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入所在地区");
            return;
        }
        if (TextUtils.isEmpty(etDetailAddress.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入详细地址");
            return;
        }
        if (TextUtils.isEmpty(etContacts.getText().toString().trim())) {
            MyToast.showDialog(RepostFieldActivity.this, "请输入联系方式");
            return;
        }

        loadingDialog.show();


        Map<String, Object> params = new HashMap<String, Object>();

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
            params.put("store_exterior_4", "data:image/jpeg;base64," + Base64BitmapUtil.bitmapToBase64(bitmapFourNext));
        params.put("store_info_id", storeInfoId);
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
        params.put("userid", HttpConfig.newInstance(RepostFieldActivity.this).getUserid());
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


        RepostFieldInfoRequest mRequest = new RepostFieldInfoRequest(this);

        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                Intent intent = new Intent(RepostFieldActivity.this, HasPostFieldFieldActivity.class);
                startActivity(intent);
                loadingDialog.dismiss();
                finish();
            }

            @Override
            public void onFailed(String msg, int code) {
                loadingDialog.dismiss();
                Toast.makeText(RepostFieldActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        mRequest.requestRepostFieldInfo(JSON.toJSONString(params));
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                etUserAddress.setText(tx);
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
                    isLoaded = true;
                    showPickerView();
                    break;

                case MSG_LOAD_FAILED:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapOne = BitmapFactory.decodeFile(path);
                        upPhotoOne.setImageBitmap(bitmapOne);
                        if (!oneCanshowNext)
                            upPhotoTwo.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapTwo = BitmapFactory.decodeFile(path);
                        upPhotoTwo.setImageBitmap(bitmapTwo);
                        if (!twoCanshowNext)
                            upPhotoThree.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 3:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapThree = BitmapFactory.decodeFile(path);
                        upPhotoThree.setImageBitmap(bitmapThree);
                        if (!threeCanshowNext)
                            upPhotoFour.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 4:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapFour = BitmapFactory.decodeFile(path);
                        upPhotoFour.setImageBitmap(bitmapFour);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 5:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapOneNext = BitmapFactory.decodeFile(path);
                        upPhotoOneNext.setImageBitmap(bitmapOneNext);
                        if (!oneCanshow)
                            upPhotoTwoNext.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 6:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapTwoNext = BitmapFactory.decodeFile(path);
                        upPhotoTwoNext.setImageBitmap(bitmapTwoNext);
                        if (!twoCanshow)
                            upPhotoThreeNext.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 7:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapThreeNext = BitmapFactory.decodeFile(path);
                        upPhotoThreeNext.setImageBitmap(bitmapThreeNext);
                        if (!threeCanshow)
                            upPhotoFourNext.setVisibility(View.VISIBLE);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 8:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);
                        bitmapFourNext = BitmapFactory.decodeFile(path);
                        upPhotoFourNext.setImageBitmap(bitmapFourNext);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 0x1001:
                if(data!=null) {
                   List<DeviceListData> dataList = (List<DeviceListData>) data.getSerializableExtra("list");
                    if (dataList != null && dataList.size() > 0) refreshList(dataList,true);
                }
                break;

          /*  case 0x121:
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
                break;*/
        }
    }

    private  void refreshList( List<DeviceListData> dataList1,boolean isClear){
        totalPrice=0;
        allnum=0;
        for(DeviceListData data:dataList1){

            totalPrice = totalPrice + (Double.parseDouble(data.getPrice_purchase()) * data.getShowNO());
            allnum = allnum + data.getShowNO();
        }
        if(isClear) {
            dataList.clear();
            dataList.addAll(dataList1);
        }
        lvDevice.setVisibility(View.VISIBLE);
        mAdapter.setData(dataList);
        tv_totlPrice.setText("总计：￥"+totalPrice);
        tvNumAll.setText("已选设备"+allnum+"台");

    }

    private void initDeviceData() {
        DeviceListRequest mRequest = new DeviceListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<DeviceListData>>() {
            @Override
            public void onSuccess(final List<DeviceListData> data) {


                for(Map map: deviceListBeen){
                    Set<String> set=map.keySet();
                    for(String key:set)
                    {
                        for(DeviceListData data1:data){
                          if(key.equals(data1.getDevice_model())){
                              data1.setShowNO(((Double)map.get(key)).intValue() );
                              dataList.add(data1);
                          }
                        }
                    }
                }

               refreshList(dataList,false);
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(RepostFieldActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        mRequest.requestAllDevice();
    }

    @Override
    public void reduceNo(int position) {
        if (0 == allnum) {
            Toast.makeText(getApplicationContext(), "已经到了最小值", Toast.LENGTH_SHORT).show();
            return;
        }
        allnum = allnum - 1;
        totalPrice=totalPrice-Double.parseDouble(dataList.get(position).getPrice_purchase());
        tvNumAll.setText("已选设备"+allnum+"台");
        tv_totlPrice.setText("总计：￥"+(totalPrice));
    }

    @Override
    public void addNo(int position) {
        allnum = allnum + 1;
        totalPrice=totalPrice+Double.parseDouble(dataList.get(position).getPrice_purchase());
        tvNumAll.setText("已选设备"+allnum+"台");
        tv_totlPrice.setText("总计：￥"+(totalPrice));
    }

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
                Toast.makeText(RepostFieldActivity.this, msg, Toast.LENGTH_LONG);
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
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
            case R.id.lv_field_select_equiment:
                Intent intent=new Intent(getApplicationContext(), EquipmentDetailActivity.class);
                intent.putExtra("device_model",dataList.get(position).getDevice_model());
                startActivity(intent);
                break;
           /* case R.id.gd_select_img1:
                if(position>=imgUrl_list1.size()) {
                    Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                    startActivityForResult(albumIntent, 0x121);
                }
                break;
            case R.id.gd_select_img2:
                if(position>=imgUrl_list1.size()) {
                    Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                    startActivityForResult(albumIntent, 0x122);
                }
                break;*/
        }

    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapOne = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask2 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapTwo = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask3 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapThree = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask4 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapFour = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask5 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapOneNext = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask6 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapTwoNext = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask7 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapThreeNext = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    class MyAsyncTask8 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapFourNext = bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;

            try {
                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}

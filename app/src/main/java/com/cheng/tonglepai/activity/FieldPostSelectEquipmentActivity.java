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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.BaseBackResult;
import com.cheng.retrofit20.data.EquimentDetailResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceListAdapter;
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
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/7/4.
 */


public class FieldPostSelectEquipmentActivity extends TitleActivity implements View.OnClickListener,AdapterView.OnItemClickListener,DeviceListAdapter.DeviceListListener {


    private DeviceListAdapter mAdapter;
    private ArrayList<DeviceListData> dataList = new ArrayList<>();
    private ArrayList<DeviceListData> returndataList = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private ListView listView;
    private Button btn_sure;
    private ArrayList<DeviceListData> dataList1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_select_equipment_post);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("设备选择");
        initView();
        initData();

    }


    private void initData() {
        DeviceListRequest mRequest = new DeviceListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<ArrayList<DeviceListData>>() {
            @Override
            public void onSuccess(final ArrayList<DeviceListData> data) {
                if(loadingDialog!=null)loadingDialog.dismiss();
                dataList = data;
                if(dataList!=null&&dataList1!=null) {
                    for (DeviceListData data1 : dataList1) {
                        //把上个页面传过来的参数赋值给当前页面
                        for (int i = 0; i < dataList.size(); i++) {
                            if (data1.getId().equals(dataList.get(i).getId())) {
                                dataList.get(i).setShowNO(data1.getShowNO());
                                break;
                            }
                        }
                    }
                    mAdapter.setData(dataList);
                }else {
                    mAdapter.setData(dataList);
                }

            }

            @Override
            public void onFailed(String msg, int code) {
                if(loadingDialog!=null)loadingDialog.dismiss();
                Toast.makeText(FieldPostSelectEquipmentActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        mRequest.requestAllDevice();
    }

    private void initView() {
        dataList1= (ArrayList<DeviceListData>) getIntent().getSerializableExtra("dataList");
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("请等待");
        loadingDialog.setCancelable(true);
        loadingDialog.show();
        listView= (ListView) findViewById(R.id.lv_field_select_equiment);
        btn_sure= (Button) findViewById(R.id.btn_to_submit);
        btn_sure.setOnClickListener(this);
        mAdapter = new DeviceListAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        mAdapter.setOnIPostPackageNoListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_to_submit:
                for(DeviceListData data:dataList){
                    if(data.getShowNO()>0){
                        returndataList.add(data);
                    }
                }
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("list",returndataList);
                intent.putExtras(bundle);
                setResult(2,intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Intent intent=new Intent(getApplicationContext(), EquipmentDetailActivity.class);
         intent.putExtra("device_model",dataList.get(position).getDevice_model());
        startActivity(intent);
    }

    @Override
    public void reduceNo(int position) {

    }

    @Override
    public void addNo(int position) {

    }

    @Override
    protected void onDestroy() {
        if(mAdapter!=null){
            mAdapter.setOnIPostPackageNoListener(null);
            mAdapter=null;
        }
        if(btn_sure!=null)
        {
            btn_sure.setOnClickListener(null);
            btn_sure=null;
        }
        loadingDialog=null;
        listView=null;
        super.onDestroy();
    }
}

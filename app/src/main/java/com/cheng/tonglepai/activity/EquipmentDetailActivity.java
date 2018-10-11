package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.DeviceListAdapter;
import com.cheng.tonglepai.data.DeviceListData;
import com.cheng.tonglepai.net.DeviceListRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/7/4.
 */


public class EquipmentDetailActivity extends TitleActivity implements View.OnClickListener{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_field_select_equipment_post);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("设备选择");
        initView();


    }





    private void initView() {

    }


    @Override
    public void onClick(View v) {

    }
}

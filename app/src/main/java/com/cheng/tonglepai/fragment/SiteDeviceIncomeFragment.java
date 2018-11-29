
package com.cheng.tonglepai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.view.ChartView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/*
 * Created by User on 2016/6/28.冯
*/

public class SiteDeviceIncomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
   private View contentView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=inflater.inflate(R.layout.site_device_income_fragment, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_view();

    }
    private void init_view()
    {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_date:


                break;
        }
    }


}

package com.cheng.tonglepai.tool;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cheng.tonglepai.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSelectUtil {
    private static DateSelectUtil instance=null;
    private TimePickerView pvTime;
    private DateSelectUtil(){

    }
    public static synchronized DateSelectUtil getInstance(){
        if(instance==null){
            instance=new DateSelectUtil();
        }
        return instance;
    }

    /**
     *
     * @param context
     * @param ly_bottom
     * @param tv_date
     * @param selectDateCallBack
     * @param type 1年月 2. 年月日
     */
    public void selectDate(Context context, LinearLayout ly_bottom,TextView tv_date,int type,final selectDateCallBack selectDateCallBack ){


            //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
            //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
            Calendar selectedDate = Calendar.getInstance();

            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), 28);
            //时间选择器
            pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v, String year, String month) {//选中事件回调
                    // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                    /*btn_Time.setText(getTime(date));*/
                    String data=getTime(date);
                    selectDateCallBack.dateselect(data);
                }
            }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                        @Override
                        public void customLayout(View v) {
                            final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                            TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                            tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvTime.returnData();
                                    pvTime.dismiss();
                                   // chartView.setVisibility(View.VISIBLE);
                                }
                            });
                            ivCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvTime.dismiss();
                                   // chartView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    })
                    .setType(type==1?new boolean[]{true, true, false, false, false, false}:(type==2?new boolean[]{true, true, true, false, false, false}:null))
                    .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                    .setDividerColor(Color.DKGRAY)
                    .setContentTextSize(20)
                    .setDate(selectedDate)
                    .setTextColorCenter(Color.RED)
                    .setRangDate(null, selectedDate)
                    .setDecorView(ly_bottom)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                    .setBackgroundId(0x00000000)
                    .setOutSideCancelable(false)
                    .build();

            pvTime.setKeyBackCancelable(true);//系统返回键不屏蔽
            pvTime.show(tv_date, false);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

  public   interface  selectDateCallBack{
        void dateselect(String date);
    }
}

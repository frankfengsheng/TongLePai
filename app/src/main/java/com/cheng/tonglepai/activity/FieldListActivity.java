package com.cheng.tonglepai.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.FieldListAdapter;
import com.cheng.tonglepai.data.FieldListData;
import com.cheng.tonglepai.data.JsonBean;
import com.cheng.tonglepai.net.FieldListRequest;
import com.cheng.tonglepai.net.FieldListSearchRequest;
import com.cheng.tonglepai.net.FieldListYitouRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.GetJsonDataUtil;
import com.cheng.tonglepai.tool.LoadingDialog;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/7/3.
 */

public class FieldListActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvFieldList;
    private boolean isLoad, isFirst, needLoad;
    private FieldListAdapter mAdapter;
    private RadioButton rbPaixu, rbSearch, rbHasPost;
    private String byOrder = "1";
    private LoadingDialog loadingDialog;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2IdItems = new ArrayList<>();  //id列表
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3IdItems = new ArrayList<>(); //id列表
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String provinceId = "", cityId = "", areaId = "";
    private boolean isLoaded;
    private List<Drawable> list;
    private ViewPager vpBanner;
    private LinearLayout llRound;
    private ArrayList<ImageView> listImage;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    int currentItem = vpBanner.getCurrentItem();
                    currentItem++;
                    vpBanner.setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(0, 4000);
                    break;
                default:
                    break;
            }
        }
    };
    private int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_parnter_field_list);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("场地列表");
//        initHeadView();
        initRefreshLayout();
        initView();
        initData();
    }

    private void initHeadView() {

    }

    private void info() {
        listImage = new ArrayList<>();
        listImage.clear();
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(FieldListActivity.this);

            if (i == 0) {
                iv.setImageResource(R.drawable.dot_off);
            } else {
                iv.setImageResource(R.drawable.dot_on);
            }
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(10, 10);
            param.setMargins(5, 0, 5, 5);
            listImage.add(iv);
            llRound.addView(iv, param);
        }
    }

    private void initData() {
        loadingDialog.show();
        FieldListRequest mRequest = new FieldListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<FieldListData>>() {
            @Override
            public void onSuccess(List<FieldListData> data) {
                if (isLoad) {
                    isLoad = false;
                    if (data.size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.setData(data);
                if (data.size() < 10)
                    needLoad = false;
                else
                    needLoad = true;
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
                Toast.makeText(FieldListActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                if (isFirst)
                    mAdapter.clearData();
            }
        });

        mRequest.requestFieldList(byOrder, page + "");
    }

    private void initView() {
        loadingDialog = DialogUtil.createLoaddingDialog(this);
        loadingDialog.setMessage("加载中");
        loadingDialog.setCancelable(true);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_field_list);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));

        lvFieldList = (ListView) findViewById(R.id.lv_field_list);
        mAdapter = new FieldListAdapter(this);
        lvFieldList.setAdapter(mAdapter);
        View headView = getLayoutInflater().inflate(R.layout.view_listview_head, null);
        lvFieldList.addHeaderView(headView);

        rbPaixu = (RadioButton) headView.findViewById(R.id.rb_filed_paixu);
        rbSearch = (RadioButton) headView.findViewById(R.id.rb_filed_search);
        rbHasPost = (RadioButton) headView.findViewById(R.id.rb_field_haspost);
        vpBanner = (ViewPager) headView.findViewById(R.id.vp);
        llRound = (LinearLayout) headView.findViewById(R.id.ll);
        info();
        vpBanner.setAdapter(new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView iv = new ImageView(FieldListActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageDrawable(FieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv1 = new ImageView(FieldListActivity.this);
                iv1.setScaleType(ImageView.ScaleType.FIT_XY);
                iv1.setImageDrawable(FieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv2 = new ImageView(FieldListActivity.this);
                iv2.setScaleType(ImageView.ScaleType.FIT_XY);
                iv2.setImageDrawable(FieldListActivity.this.getResources().getDrawable(R.drawable.banner));
                // iv.setImageResource(strimage[(position % strimage.length)]);
                container.addView(iv);
                container.addView(iv1);
                container.addView(iv2);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }
        });

        vpBanner.setCurrentItem(3 * 5000);

        vpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                for (int i = 0; i < 3; i++) {
                    if (i == arg0 % 3) {
                        listImage.get(i).setImageResource(R.drawable.dot_off);
                    } else {
                        listImage.get(i).setImageResource(R.drawable.dot_on);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        handler.sendEmptyMessageDelayed(0, 3000);

        rbPaixu.setOnClickListener(this);
        rbSearch.setOnClickListener(this);
        rbHasPost.setOnClickListener(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        if (type == 1) {
            initData();
        } else if (type == 2) {
            initSearchData();
        } else if (type == 3) {
            initHasPostData();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            if (type == 1) {
                initData();
            } else if (type == 2) {
                initSearchData();
            } else if (type == 3) {
                initHasPostData();
            }
            return true;
        } else {
            return false;
        }
    }

    private void initRefreshLayout() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_filed_paixu:
                initBtn();
                type = 1;
                byOrder = "1";
                provinceId = "";
                cityId = "";
                areaId = "";
                isFirst = true;
                rbPaixu.setChecked(true);
                rbPaixu.setTextColor(Color.parseColor("#ffffff"));
                rbPaixu.setBackgroundColor(Color.parseColor("#45a7fe"));
                initData();
                break;
            case R.id.rb_filed_search:
                initBtn();
                type = 2;
                provinceId = "";
                cityId = "";
                areaId = "";
                byOrder = "0";
                isFirst = true;
                rbSearch.setChecked(true);
                if (isLoaded)
                    mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
                else
                    mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                rbSearch.setTextColor(Color.parseColor("#ffffff"));
                rbSearch.setBackgroundColor(Color.parseColor("#45a7fe"));

                break;
            case R.id.rb_field_haspost:
                initBtn();
                type = 3;
                provinceId = "";
                cityId = "";
                areaId = "";
                byOrder = "0";
                isFirst = true;
                rbHasPost.setChecked(true);
                rbHasPost.setTextColor(Color.parseColor("#ffffff"));
                rbHasPost.setBackgroundColor(Color.parseColor("#45a7fe"));
                initHasPostData();
                break;
        }
    }

    private void initHasPostData() {
        loadingDialog.show();
        FieldListYitouRequest mRequest = new FieldListYitouRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<FieldListData>>() {
            @Override
            public void onSuccess(List<FieldListData> data) {
                if (isLoad) {
                    isLoad = false;
                    if (data.size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.setData(data);
                if (data.size() < 10)
                    needLoad = false;
                else
                    needLoad = true;
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
                Toast.makeText(FieldListActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                mAdapter.clearData();
            }
        });

        mRequest.requestFieldListYitou(page + "");
    }


    private void initSearchData() {
        loadingDialog.show();
        FieldListSearchRequest mRequest = new FieldListSearchRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<FieldListData>>() {
            @Override
            public void onSuccess(List<FieldListData> data) {
                if (isLoad) {
                    isLoad = false;
                    if (data.size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    loadingDialog.dismiss();
                    return;
                }
                mAdapter.setData(data);
                if (data.size() < 10)
                    needLoad = false;
                else
                    needLoad = true;
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(String msg, int code) {
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                loadingDialog.dismiss();
                Toast.makeText(FieldListActivity.this, "没有数据", Toast.LENGTH_LONG).show();
                if (isFirst)
                    mAdapter.clearData();
            }
        });

        mRequest.requestFieldList(page + "", provinceId + "", cityId + "", areaId + "");
    }

    private void initBtn() {
        rbPaixu.setChecked(false);
        rbSearch.setChecked(false);
        rbHasPost.setChecked(false);
        rbPaixu.setTextColor(Color.parseColor("#686868"));
        rbSearch.setTextColor(Color.parseColor("#686868"));
        rbHasPost.setTextColor(Color.parseColor("#686868"));
        rbPaixu.setBackgroundColor(Color.parseColor("#ffffff"));
        rbSearch.setBackgroundColor(Color.parseColor("#ffffff"));
        rbHasPost.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                provinceId = options1Items.get(options1).getCode_id();
                cityId = options2IdItems.get(options1).get(options2);
                areaId = options3IdItems.get(options1).get(options2).get(options3);
                initSearchData();
                Log.i("-----------", provinceId + "   " + cityId + "    " + areaId);
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

}

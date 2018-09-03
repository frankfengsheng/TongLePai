package com.cheng.tonglepai.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.adapter.InverstorFieldListAdapter;
import com.cheng.tonglepai.data.InvestorFieldListData;
import com.cheng.tonglepai.net.InvestorFieldListRequest;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by cheng on 2018/7/3.
 */

public class InverstorFieldListActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private int page = 1;
    private ListView lvFieldList;
    private boolean isLoad;
    private boolean isFirst;
    private boolean needLoad;
    private InverstorFieldListAdapter mAdapter;
    private LinearLayout llFieldList;
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
        vpBanner = (ViewPager) findViewById(R.id.vp);
        llRound = (LinearLayout) findViewById(R.id.ll);
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
                ImageView iv = new ImageView(InverstorFieldListActivity.this);
                iv.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv1 = new ImageView(InverstorFieldListActivity.this);
                iv1.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv2 = new ImageView(InverstorFieldListActivity.this);
                iv2.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));
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
    }

    private void info() {
        listImage = new ArrayList<>();
        listImage.clear();
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(InverstorFieldListActivity.this);

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
        InvestorFieldListRequest mRequest = new InvestorFieldListRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<List<InvestorFieldListData>>() {
            @Override
            public void onSuccess(List<InvestorFieldListData> data) {
                if (isLoad) {
                    isLoad = false;
                    if (data.size() < 10)
                        needLoad = false;
                    else
                        needLoad = true;
                    mAdapter.setLoadData(data);
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    return;
                }
                mAdapter.setData(data);
                if (data.size() < 10)
                    needLoad = false;
                else
                    needLoad = true;
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onFailed(String msg, int code) {
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }
        });

        mRequest.requestFieldList(page + "");
    }

    private void initView() {
        lvFieldList = (ListView) findViewById(R.id.lv_field_list);
        mAdapter = new InverstorFieldListAdapter(this);
        lvFieldList.setAdapter(mAdapter);
        View headView = getLayoutInflater().inflate(R.layout.view_listview_head, null);
        lvFieldList.addHeaderView(headView);

        llFieldList = (LinearLayout) headView.findViewById(R.id.ll_field_list);
        llFieldList.setVisibility(View.GONE);
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
                ImageView iv = new ImageView(InverstorFieldListActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv1 = new ImageView(InverstorFieldListActivity.this);
                iv1.setScaleType(ImageView.ScaleType.FIT_XY);
                iv1.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));

                ImageView iv2 = new ImageView(InverstorFieldListActivity.this);
                iv2.setScaleType(ImageView.ScaleType.FIT_XY);
                iv2.setImageDrawable(InverstorFieldListActivity.this.getResources().getDrawable(R.drawable.banner));
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

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoad = false;
        isFirst = true;
        page = 1;
        initData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoad = true;
        isFirst = false;
        page++;
        if (needLoad) {
            initData();
            return true;
        } else {
            return false;
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_field_list);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}

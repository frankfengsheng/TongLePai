package com.cheng.tonglepai.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.fragment.investor.InvestorDeviceIncomeFragment;
import com.cheng.tonglepai.fragment.investor.InvestorDishouyiDeviceIncomeFragment;
import com.cheng.tonglepai.fragment.investor.InvestorSiteIncomeFragment;
import com.cheng.tonglepai.fragment.investor.InvestorStatisticsFragment;
import com.cheng.tonglepai.fragment.partner.PartnerDeviceIncomeFragment;
import com.cheng.tonglepai.fragment.partner.PartnerSiteIncomeFragment;
import com.cheng.tonglepai.fragment.partner.PartnerStatisticsFragment;
import com.cheng.tonglepai.view.CacheFragmentStatePagerAdapter;
import com.cheng.tonglepai.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/6/8.
 */

public class InvestorIncomeActivity extends TitleActivity implements View.OnClickListener {

    SlidingTabLayout slidingTabLayout;
    ViewPager viewPager;
    NavigationAdapter mPagerAdapter;
    private List<String> daohangList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_partner_income);
        setMidTitle("我的收益");
        MyApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        daohangList.add("综合统计");
        daohangList.add("场地收益");
        daohangList.add("设备收益");
        daohangList.add("低收益设备");
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab);
        viewPager = (ViewPager) findViewById(R.id.vp_income);
        initSlidingTabLayout();
    }


    @Override
    public void onClick(View v) {

    }

    /*
     * 实例顶部Tab
     **/
    private void initSlidingTabLayout() {
        mPagerAdapter = new NavigationAdapter(getSupportFragmentManager(), daohangList);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOnPageChangeListener(new PageChangeListener());
        slidingTabLayout.setCustomTabView(R.layout.news_tab_view2,
                R.id.news_tab_tv2);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tab_blue);
            }

        });
        slidingTabLayout.setOnPageChangeListener(new PageChangeListener());

    }

    private static class NavigationAdapter extends
            CacheFragmentStatePagerAdapter {

        private List<String> fragmentList;
        private List<String> daohangList;

        public NavigationAdapter(FragmentManager fm, List<String> daohangList) {
            super(fm);
            fragmentList = new ArrayList<String>();
            this.daohangList = daohangList;
        }

        Fragment f;

        @Override
        protected Fragment createItem(int position) {
            if (position == 0) f = new InvestorStatisticsFragment();
            if (position == 1) f = new InvestorSiteIncomeFragment();
            if (position == 2) f = new InvestorDeviceIncomeFragment();
            if (position == 3) f = new InvestorDishouyiDeviceIncomeFragment();
            return f;
        }

        @Override
        public int getCount() {
            return daohangList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return daohangList.get(position);
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

}
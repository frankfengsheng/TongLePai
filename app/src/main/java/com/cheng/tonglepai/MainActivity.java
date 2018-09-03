package com.cheng.tonglepai;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.tonglepai.activity.LearnMoreActivity;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.activity.TitleActivity;
import com.cheng.tonglepai.activity.UserCenterActivity;
import com.cheng.tonglepai.data.BitmapData;
import com.cheng.tonglepai.data.ChooseTypeData;
import com.cheng.tonglepai.data.IncomeMainData;
import com.cheng.tonglepai.net.ChooseTypeRequest;
import com.cheng.tonglepai.net.IncomeResultRequest;
import com.cheng.tonglepai.net.LoginoutRequest;
import com.cheng.tonglepai.tool.ScalePagerTransformer;
import com.cheng.tonglepai.tool.TimeUtil;
import com.cheng.tonglepai.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class MainActivity extends TitleActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    public static String USER_PHONE = "user.phone";
    private LinearLayout llTouziType, llHehuoType, llFieldType, llRefreshIncome;
    private TextView tvToPartner, tvToInvestor, tvTotalIncome, tvFieldIncome, tvPartnerIncome, tvInvestorIncome, tvIncomeDate, tvToField;
    private boolean isFirst = true;
    //    private static boolean isExit = false;
//    Handler mHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            isExit = false;
//        }
//    };
    private ImageView backiv;
    private CustomViewPager vpBanner;
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
    private List<BitmapData> dataList = new ArrayList<>();
    private ImageView ivLeft, ivRight;
    private TextView tv_to_phone;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private BGARefreshLayout bgaRefreshLayout;
    private ListView lvHomePage;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("童乐派");
        initBgalayout();
        initHeadView();
        initView();
        initData();
    }

    private void initBgalayout() {
        bgaRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_main_page);
        bgaRefreshLayout.setDelegate(this);
        bgaRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        lvHomePage = (ListView) findViewById(R.id.lv_main_page);
        lvHomePage.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        });
        headView = getLayoutInflater().inflate(R.layout.view_main_head, null);
        lvHomePage.addHeaderView(headView);
    }

    private void initHeadView() {
        BitmapData data = new BitmapData();
        data.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
        data.setType("1");
        dataList.add(data);
        BitmapData data1 = new BitmapData();
        data1.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.find_field));
        data1.setType("2");
        dataList.add(data1);
        BitmapData data2 = new BitmapData();
        data2.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.buy_device));
        data2.setType("3");
        dataList.add(data2);

        vpBanner = (CustomViewPager) headView.findViewById(R.id.vp);
        vpBanner.setOffscreenPageLimit(3);
        vpBanner.setPageMargin(30);
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

                ImageView iv = new ImageView(MainActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                final BitmapData currentBean = dataList.get(position % dataList.size());
                iv.setImageBitmap(currentBean.getBitmap());
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentBean.getType().equals("1")) {
                            ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                            mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                                @Override
                                public void onSuccess(ChooseTypeData data) {
                                    HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                                    HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                                    HttpConfig.newInstance(MainActivity.this).setUserType(3);
                                    HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                                    Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                                    intent.putExtra(UserCenterActivity.USER_TYPE, 3);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailed(String msg, int code) {

                                }
                            });
                            mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "3");
                        } else if (currentBean.getType().equals("2")) {
                            ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                            mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                                @Override
                                public void onSuccess(ChooseTypeData data) {
                                    HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                                    HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                                    HttpConfig.newInstance(MainActivity.this).setUserType(2);
                                    HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                                    Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                                    intent.putExtra(UserCenterActivity.USER_TYPE, 2);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailed(String msg, int code) {

                                }
                            });

                            mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "2");
                        } else if (currentBean.getType().equals("3")) {
                            ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                            mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                                @Override
                                public void onSuccess(ChooseTypeData data) {
                                    HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                                    HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                                    HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                                    HttpConfig.newInstance(MainActivity.this).setUserType(1);
                                    Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                                    intent.putExtra(UserCenterActivity.USER_TYPE, 1);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailed(String msg, int code) {

                                }
                            });

                            mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "1");
                        }
                    }
                });
                container.addView(iv);
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

        vpBanner.setPageTransformer(true, new ScalePagerTransformer());

//        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private void initData() {
        IncomeResultRequest mRequest = new IncomeResultRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<IncomeMainData>() {
            @Override
            public void onSuccess(IncomeMainData data) {
                tvTotalIncome.setText(data.getTotal());
                tvFieldIncome.setText(data.getInvest_price());
                tvInvestorIncome.setText(data.getPlace_price());
                tvPartnerIncome.setText(data.getPartner_price());
                tvIncomeDate.setText(TimeUtil.allTime(data.getTimes()));
                llRefreshIncome.setClickable(true);
                if (!isFirst) {
                    Toast.makeText(MainActivity.this, "数据刷新完成", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                llRefreshIncome.setClickable(true);
            }
        });
        mRequest.requestIncomeResult(getIntent().getStringExtra(USER_PHONE));
    }

    private void initView() {
        tv_to_phone = (TextView) headView.findViewById(R.id.tv_to_phone);
        ivLeft = (ImageView) headView.findViewById(R.id.iv_to_left);
        ivRight = (ImageView) headView.findViewById(R.id.iv_to_right);
        backiv = (ImageView) findViewById(R.id.title_left_back_iv);

        tv_to_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查是否获得了权限（Android6.0运行时权限）
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {
                        // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(MainActivity.this, "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
            }
        });
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vpBanner.getCurrentItem();
                currentItem--;
                vpBanner.setCurrentItem(currentItem);
            }
        });

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = vpBanner.getCurrentItem();
                currentItem++;
                vpBanner.setCurrentItem(currentItem);
            }
        });

        llTouziType = (LinearLayout) headView.findViewById(R.id.ll_touzi_type);
        llHehuoType = (LinearLayout) headView.findViewById(R.id.ll_hehuo_type);
        llFieldType = (LinearLayout) headView.findViewById(R.id.ll_field_type);
        llRefreshIncome = (LinearLayout) headView.findViewById(R.id.ll_refresh_income);

        tvIncomeDate = (TextView) headView.findViewById(R.id.tv_income_date);
        tvTotalIncome = (TextView) headView.findViewById(R.id.tv_total_income);
        tvFieldIncome = (TextView) headView.findViewById(R.id.tv_field_income);
        tvPartnerIncome = (TextView) headView.findViewById(R.id.tv_parnter_income);
        tvInvestorIncome = (TextView) headView.findViewById(R.id.tv_investor_income);
        tvToPartner = (TextView) headView.findViewById(R.id.tv_to_partner);
        tvToInvestor = (TextView) headView.findViewById(R.id.tv_to_investor);
        tvToField = (TextView) headView.findViewById(R.id.tv_to_field);

        tvToPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnMoreActivity.class);
                intent.putExtra(LearnMoreActivity.TYPE, 2);
                startActivity(intent);
            }
        });
        tvToInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnMoreActivity.class);
                intent.putExtra(LearnMoreActivity.TYPE, 1);
                startActivity(intent);
            }
        });

        tvToField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnMoreActivity.class);
                intent.putExtra(LearnMoreActivity.TYPE, 3);
                startActivity(intent);
            }
        });

        llTouziType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                    @Override
                    public void onSuccess(ChooseTypeData data) {
                        HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                        HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                        HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                        HttpConfig.newInstance(MainActivity.this).setUserType(1);
                        Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                        intent.putExtra(UserCenterActivity.USER_TYPE, 1);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(String msg, int code) {

                    }
                });

                mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "1");

            }
        });

        llHehuoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                    @Override
                    public void onSuccess(ChooseTypeData data) {
                        HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                        HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                        HttpConfig.newInstance(MainActivity.this).setUserType(2);
                        HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                        Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                        intent.putExtra(UserCenterActivity.USER_TYPE, 2);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(String msg, int code) {

                    }
                });

                mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "2");
            }
        });

        llFieldType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTypeRequest mRequest = new ChooseTypeRequest(MainActivity.this);
                mRequest.setListener(new BaseHttpRequest.IRequestListener<ChooseTypeData>() {
                    @Override
                    public void onSuccess(ChooseTypeData data) {
                        HttpConfig.newInstance(MainActivity.this).setUserid(data.getId());
                        HttpConfig.newInstance(MainActivity.this).setAccessToken(data.getToken());
                        HttpConfig.newInstance(MainActivity.this).setUserType(3);
                        HttpConfig.newInstance(MainActivity.this).setUserTel(getIntent().getStringExtra(USER_PHONE));
                        Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                        intent.putExtra(UserCenterActivity.USER_TYPE, 3);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(String msg, int code) {

                    }
                });

                mRequest.requestChooseType(getIntent().getStringExtra(USER_PHONE), "3");
            }
        });

        llRefreshIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llRefreshIncome.setClickable(false);
                isFirst = false;
                initData();
            }
        });


    }

    private void CallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "400-0366118");
        intent.setData(data);
        startActivity(intent);
    }

    private void logout() {
        LoginoutRequest mRequest = new LoginoutRequest(this);
        mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
            @Override
            public void onSuccess(BaseHttpResult data) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailed(String msg, int code) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        mRequest.requestLogout(HttpConfig.newInstance(MainActivity.this).getUserTel());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logout();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        initData();
        bgaRefreshLayout.endRefreshing();
        bgaRefreshLayout.endLoadingMore();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
//
//    private void exit() {
//        if (!isExit) {
//            isExit = true;
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//            // 利用handler延迟发送更改状态信息
//            mHandler.sendEmptyMessageDelayed(0, 2000);
//        } else {
//
//            LoginoutRequest mRequest = new LoginoutRequest(this);
//            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
//                @Override
//                public void onSuccess(BaseHttpResult data) {
//                    finish();
//                    System.exit(0);
//                }
//
//                @Override
//                public void onFailed(String msg, int code) {
//                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                }
//            });
//            mRequest.requestLogout(HttpConfig.newInstance(MainActivity.this).getUserTel());
//
//
//        }
//    }

}

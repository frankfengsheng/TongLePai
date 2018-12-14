package com.cheng.tonglepai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheng.retrofit20.bean.IsNeedPayBean;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.bitmap.MyBitmapUtil;
import com.cheng.tonglepai.data.InvestorUserInfoData;
import com.cheng.tonglepai.data.UserInfoData;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.net.FieldUserInfoRequest;
import com.cheng.tonglepai.net.InvestorUserInfoRequest;
import com.cheng.tonglepai.net.UserInfoRequest;
import com.cheng.tonglepai.tool.DialogUtil;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * Created by cheng on 2018/6/22.
 */

public class UserCenterActivity extends TitleActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    //1是投资人  2是合伙人、创客
    public static final String USER_TYPE = "user.type";
    private int userType;
    private TextView tvCanUse, tvhasUse, tvAllUse, tv_four;
    private TextView tvOne, tvTwo, tvThree, allIncome, lastIncome, dayIncome, line_weight;
    private RelativeLayout rlHasPostField, rlGroundManage, rlOrderStatus, rlToInput, rlDeviceManagement, rlPersonSetting, rlBindDevice,rlReportRecord;
    private RelativeLayout rlTixianDetail, rlFeedBack, rlMyIncome, rlPostField;
    private LinearLayout llHasPostField, llGroundManage, llOrderStatus, llFreight, llThree, lineOne, line_three, lineThree, llToDeviceManage, tv_five, tv_six;
    private TextView tvUserNmae, tvUserPhone, tvShareCode;
    private Button btnToFieldPost;
    private ImageView ivUserImage;
    private TextView tvUpdateLevel;
    private String useImg;
    private TextView tvDividerThree,tvSaoma,tvToubi;
    private BGARefreshLayout bgaRefreshLayout;
    private ListView lvHomePage;
    private LinearLayout llTypeOne,ll_partner_income,llLinePost;
    private RelativeLayout rlReportIncome,rl_city_income,rlCoinPost,rlDayIncome,rlDaySaoma,rlDayToubi;
    private RelativeLayout rl_tixian;
    private String canApplyMoney = "", bankAccount = "", bankName = "", pricePay = "", zPrice = "";
    private String openid="";
    private String wx_nicknam="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_to_investment);
        MyApplication.getInstance().addActivity(this);
        userType = getIntent().getIntExtra(USER_TYPE, 0);
        if (userType == 1) {
            setMidTitle("设备方");
        } else if (userType == 2) {
            setMidTitle("合伙人");
        } else if (userType == 3) {
            setMidTitle("场地方");
        }
        initView();
        initData();
    }
    private void initView() {
        bgaRefreshLayout = (BGARefreshLayout) findViewById(R.id.bga_home_page);
        bgaRefreshLayout.setDelegate(this);
        bgaRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        lvHomePage = (ListView) findViewById(R.id.lv_home_page);
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
        View headView = getLayoutInflater().inflate(R.layout.view_home_head, null);
        lvHomePage.addHeaderView(headView);

        llFreight = (LinearLayout) headView.findViewById(R.id.ll_freight);
        lineOne = (LinearLayout) headView.findViewById(R.id.line_one);
        line_three = (LinearLayout) headView.findViewById(R.id.line_three);
        llToDeviceManage = (LinearLayout) headView.findViewById(R.id.ll_device_management);
        llTypeOne = (LinearLayout) headView.findViewById(R.id.ll_type_one);
        rlMyIncome = (RelativeLayout) headView.findViewById(R.id.rl_my_income);
        rlReportIncome = (RelativeLayout) headView.findViewById(R.id.rl_report_income);
        rlPostField = (RelativeLayout) headView.findViewById(R.id.rl_post_field);
        ivUserImage = (ImageView) headView.findViewById(R.id.iv_user_img);
        btnToFieldPost = (Button) headView.findViewById(R.id.btn_to_field_post);
        tvOne = (TextView) headView.findViewById(R.id.tv_my_equipment);
        tvTwo = (TextView) headView.findViewById(R.id.tv_two);
        tvThree = (TextView) headView.findViewById(R.id.tv_three);
        allIncome = (TextView) headView.findViewById(R.id.all_income);
        lastIncome = (TextView) headView.findViewById(R.id.last_income);
        dayIncome = (TextView) headView.findViewById(R.id.day_income);
        tvUserNmae = (TextView) headView.findViewById(R.id.tv_user_name);
        tvUserPhone = (TextView) headView.findViewById(R.id.tv_user_phone);
        line_weight = (TextView) headView.findViewById(R.id.line_weight);
        tvCanUse = (TextView) headView.findViewById(R.id.tv_can_use);
        tv_four = (TextView) headView.findViewById(R.id.tv_four);
        tv_five = (LinearLayout) headView.findViewById(R.id.tv_five);
        tv_six = (LinearLayout) headView.findViewById(R.id.tv_six);
        tvhasUse = (TextView) headView.findViewById(R.id.tv_has_use);
        tvAllUse = (TextView) headView.findViewById(R.id.tv_all_use);
        tvShareCode = (TextView) headView.findViewById(R.id.tv_share_code);
        tvToubi = (TextView) headView.findViewById(R.id.day_toubi);
        tvSaoma = (TextView) headView.findViewById(R.id.day_saoma);
        tvUpdateLevel = (TextView) headView.findViewById(R.id.tv_update_level);
        tvDividerThree = (TextView) headView.findViewById(R.id.tv_three_divider);
        rlHasPostField = (RelativeLayout) headView.findViewById(R.id.rl_has_post_filed);
        rlGroundManage = (RelativeLayout) headView.findViewById(R.id.rl_ground_manage);
        rlOrderStatus = (RelativeLayout) headView.findViewById(R.id.rl_order_status);
        rlTixianDetail = (RelativeLayout) headView.findViewById(R.id.rl_tixian_detail);
        rlPersonSetting = (RelativeLayout) headView.findViewById(R.id.rl_person_setting);
        rlReportRecord = (RelativeLayout) headView.findViewById(R.id.rl_report_record);
        rlCoinPost = (RelativeLayout) headView.findViewById(R.id.rl_coin_post);
        rlFeedBack = (RelativeLayout) headView.findViewById(R.id.rl_feed_back);
        rl_city_income = (RelativeLayout) headView.findViewById(R.id.rl_city_income);
        rlBindDevice = (RelativeLayout) headView.findViewById(R.id.rl_bind_device);
        rlDayIncome = (RelativeLayout) headView.findViewById(R.id.rl_day_income);
        rlDaySaoma = (RelativeLayout) headView.findViewById(R.id.rl_day_saoma);
        rlDayToubi = (RelativeLayout) headView.findViewById(R.id.rl_day_toubi);
        lineThree = (LinearLayout) headView.findViewById(R.id.device_bind_device);
        ll_partner_income = (LinearLayout) headView.findViewById(R.id.ll_partner_income);
        llLinePost = (LinearLayout) findViewById(R.id.line_shangjiao);
        llHasPostField = (LinearLayout) headView.findViewById(R.id.rl_has_post_filed_line);
        llGroundManage = (LinearLayout) headView.findViewById(R.id.rl_ground_manage_line);
        llOrderStatus = (LinearLayout) headView.findViewById(R.id.rl_order_status_line);
        llThree = (LinearLayout) headView.findViewById(R.id.ll_three);

        rlToInput = (RelativeLayout) headView.findViewById(R.id.rl_to_input);
        rlDeviceManagement = (RelativeLayout) headView.findViewById(R.id.rl_device_management);
        rl_tixian= (RelativeLayout) headView.findViewById(R.id.rl_tixian);

        rlToInput.setOnClickListener(this);
        rlDeviceManagement.setOnClickListener(this);
        rlHasPostField.setOnClickListener(this);
        rlTixianDetail.setOnClickListener(this);
        btnToFieldPost.setOnClickListener(this);
        rlPersonSetting.setOnClickListener(this);
        rlReportIncome.setOnClickListener(this);
        rlBindDevice.setOnClickListener(this);
        rlGroundManage.setOnClickListener(this);
        rlFeedBack.setOnClickListener(this);
        rlPostField.setOnClickListener(this);
        rlReportRecord.setOnClickListener(this);
        rlOrderStatus.setOnClickListener(this);
        llFreight.setOnClickListener(this);
        rlMyIncome.setOnClickListener(this);
        rlToInput.setOnClickListener(this);
        tvShareCode.setOnClickListener(this);
        ivUserImage.setOnClickListener(this);
        llToDeviceManage.setOnClickListener(this);
        llTypeOne.setOnClickListener(this);
        llThree.setOnClickListener(this);
        rl_city_income.setOnClickListener(this);
        rlCoinPost.setOnClickListener(this);
        rl_tixian.setOnClickListener(this);

//        tvUpdateLevel.setOnClickListener(this);
        if (userType == 1) {
            btnToFieldPost.setVisibility(View.GONE);
            llFreight.setVisibility(View.GONE);
            rlToInput.setVisibility(View.VISIBLE);
            rlHasPostField.setVisibility(View.GONE);
            rlGroundManage.setVisibility(View.GONE);
            llHasPostField.setVisibility(View.GONE);
            llGroundManage.setVisibility(View.GONE);
            rlOrderStatus.setVisibility(View.VISIBLE);
            llOrderStatus.setVisibility(View.VISIBLE);
            tvShareCode.setVisibility(View.GONE);
            tvUpdateLevel.setVisibility(View.GONE);
            tvCanUse.setText("我的设备");
            tvhasUse.setText("已投设备");
            tvAllUse.setText("可投设备");
        } else if (userType == 2) {
            btnToFieldPost.setVisibility(View.GONE);
            llFreight.setVisibility(View.VISIBLE);
            rlToInput.setVisibility(View.GONE);
            rlHasPostField.setVisibility(View.VISIBLE);
            rlGroundManage.setVisibility(View.VISIBLE);
            llHasPostField.setVisibility(View.VISIBLE);
            llGroundManage.setVisibility(View.VISIBLE);
            tv_five.setVisibility(View.VISIBLE);
            rlReportRecord.setVisibility(View.VISIBLE);
            tv_six.setVisibility(View.VISIBLE);
            rlOrderStatus.setVisibility(View.GONE);
            llOrderStatus.setVisibility(View.GONE);
            line_three.setVisibility(View.GONE);
            tv_four.setVisibility(View.VISIBLE);
            ll_partner_income.setVisibility(View.VISIBLE);
            rl_city_income.setVisibility(View.VISIBLE);
            tvShareCode.setVisibility(View.GONE); //邀请码上线隐藏
            tvUpdateLevel.setVisibility(View.VISIBLE);  //等级上线隐藏
            rlBindDevice.setVisibility(View.VISIBLE);  //绑定设备
            lineThree.setVisibility(View.VISIBLE);
            rlPostField.setVisibility(View.VISIBLE);
            rlReportIncome.setVisibility(View.VISIBLE);
            tvCanUse.setText("可投场地");
            tvhasUse.setText("已投场地");
            tvAllUse.setText("报备场地");
        } else if (userType == 3) {
            btnToFieldPost.setVisibility(View.GONE);
            llFreight.setVisibility(View.GONE);
            rlToInput.setVisibility(View.GONE);
            line_three.setVisibility(View.GONE);
            rlHasPostField.setVisibility(View.GONE);
            rlGroundManage.setVisibility(View.GONE);
            llHasPostField.setVisibility(View.GONE);
            llGroundManage.setVisibility(View.GONE);
            rlOrderStatus.setVisibility(View.GONE);
            llOrderStatus.setVisibility(View.GONE);
            tvShareCode.setVisibility(View.GONE);
            tvUpdateLevel.setVisibility(View.GONE);
            llThree.setVisibility(View.GONE);
            tvDividerThree.setVisibility(View.GONE);
            line_weight.setVisibility(View.GONE);
            rlCoinPost.setVisibility(View.VISIBLE);
            llLinePost.setVisibility(View.VISIBLE);
            rlDayIncome.setVisibility(View.VISIBLE);
            rlDaySaoma.setVisibility(View.GONE);
            rlDayToubi.setVisibility(View.GONE);
            tvCanUse.setText("我的场地");
            tvhasUse.setText("已投设备");
        }

        initHeadData();
    }

    private void initData() {

        if (userType == 1) {
            InvestorUserInfoRequest mRequest = new InvestorUserInfoRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<InvestorUserInfoData>() {
                @Override
                public void onSuccess(InvestorUserInfoData data) {
                    if (TextUtils.isEmpty(data.getNickename()))
                        tvUserNmae.setText("未设置");
                    else
                        tvUserNmae.setText(data.getNickename());
                    tvUserPhone.setText(data.getTel());
                    tvOne.setText(data.getMy_device_nums());
                    tvTwo.setText(data.getYt_device_nums());
                    tvThree.setText(data.getZy_device_nums());

                    allIncome.setText(data.getZ_shouyi());
                    lastIncome.setText(data.getPrice());
                    dayIncome.setText(data.getToday());

//                    if(data.getLevel().equals("0")){
//                        tvUpdateLevel.setText("银牌");
//                    }else if(data.getLevel().equals("1")){
//                        tvUpdateLevel.setText("金牌");
//                    }else if(data.getLevel().equals("2")){
//                        tvUpdateLevel.setText("区县");
//                    }else if(data.getLevel().equals("3")){
//                        tvUpdateLevel.setText("城市");
//                    }

                    if (!TextUtils.isEmpty(data.getImg())) {
                        useImg = data.getImg();
                        Glide.with(UserCenterActivity.this).load(data.getImg()).into(ivUserImage);
                       /* MyBitmapUtil myBitmapUtil = new MyBitmapUtil(UserCenterActivity.this, data.getImg());
                        myBitmapUtil.display(data.getImg(), ivUserImage);*/
                    }
                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(UserCenterActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });

            mRequest.requestInvestorUserInfo();
        } else if (userType == 2) {
            UserInfoRequest mRequest = new UserInfoRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<UserInfoData>() {
                @Override
                public void onSuccess(UserInfoData data) {
                    if (TextUtils.isEmpty(data.getNickname()))
                        tvUserNmae.setText("未设置");
                    else
                    tvUserNmae.setText(data.getNickname());
                    tvUserPhone.setText(data.getTel());
                    tvOne.setText(data.getCt());
                    tvTwo.setText(data.getCount());
                    tvThree.setText(data.getCount1());
                    allIncome.setText(data.getTotal());
                    lastIncome.setText(data.getPrice());
                    dayIncome.setText(data.getNum());

                    if (data.getLevel().equals("0")) {
                        tvUpdateLevel.setText("普通");
                    } else if (data.getLevel().equals("1")) {
                        tvUpdateLevel.setText("金牌");
                    } else if (data.getLevel().equals("2")) {
                        tvUpdateLevel.setText("区县");
                    } else if (data.getLevel().equals("3")) {
                        tvUpdateLevel.setText("城市");
                    }
                    if (!TextUtils.isEmpty(data.getImg())) {
                        useImg = data.getImg();
                        Glide.with(UserCenterActivity.this).load(data.getImg()).into(ivUserImage);
                       /* MyBitmapUtil myBitmapUtil = new MyBitmapUtil(UserCenterActivity.this, data.getImg());
                        myBitmapUtil.display(data.getImg(), ivUserImage);*/
                    }
                }
                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(UserCenterActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
            mRequest.requestTest();

        } else if (userType == 3) {
            FieldUserInfoRequest mRequest = new FieldUserInfoRequest(this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<UserInfoData>() {
                @Override
                public void onSuccess(UserInfoData data) {
                    tvUserNmae.setText(data.getNickname());
                    tvUserPhone.setText(data.getTel());
                    tvOne.setText(data.getCt());
                    tvTwo.setText(data.getCount());
                    allIncome.setText(data.getTotal());
                    lastIncome.setText(data.getPrice());
                    dayIncome.setText(data.getNum());
                    tvSaoma.setText(data.getSm_shouyi());
                    tvToubi.setText(data.getTb_shouyi());
                    if (!TextUtils.isEmpty(data.getImg())) {
                        useImg = data.getImg();
                        Glide.with(UserCenterActivity.this).load(data.getImg()).into(ivUserImage);
                    }
                }
                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(UserCenterActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
            mRequest.requestFieldTest();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的投放
            case R.id.rl_to_input:
                jumpNextActivity(InverstorFieldListActivity.class, userType);
                break;
            //设备管理
            case R.id.ll_type_one:
                if (userType == 1)
                    jumpNextActivity(InvestorDeviceMangeActivity.class, userType);
                break;
            //设备管理
            case R.id.ll_device_management:
                if (userType == 1)
                    jumpNextActivity(InvestorDeviceMangeActivity.class, userType);
                else if (userType == 2 || userType == 3)
                    jumpNextActivity(DeviceMangeActivity.class, userType);
                break;
            //设备管理
            case R.id.rl_device_management:
                if (userType == 1)
                    jumpNextActivity(InvestorDeviceMangeActivity.class, userType);
                else if (userType == 2 || userType == 3)
                    jumpNextActivity(DeviceMangeActivity.class, userType);
                break;
            //提现明细
            case R.id.rl_tixian_detail:
                jumpNextActivity(ApplyDetailActivity.class, userType);
                break;
            //已报备场地
            case R.id.ll_three:
                if (userType == 2)
                    jumpNextActivity(HasPostFieldFieldActivity.class, userType);
                break;
            case R.id.rl_has_post_filed:
                jumpNextActivity(HasPostFieldFieldActivity.class, userType);
                break;
            //去场地报备
            case R.id.rl_post_field:
                jumpNextActivity(FieldPostActivity.class, userType);
                break;
            //去个人设置
            case R.id.rl_person_setting:
                Intent intent = new Intent(UserCenterActivity.this, PersonSettingActivity.class);
                intent.putExtra(PersonSettingActivity.NICKNAME, tvUserNmae.getText().toString().trim());
                intent.putExtra(PersonSettingActivity.USERPHOTO, useImg);
                intent.putExtra("type", userType);
                startActivity(intent);
                break;
            case R.id.iv_user_img:
                Intent intent1 = new Intent(UserCenterActivity.this, PersonSettingActivity.class);
                intent1.putExtra(PersonSettingActivity.NICKNAME, tvUserNmae.getText().toString().trim());
                intent1.putExtra(PersonSettingActivity.USERPHOTO, useImg);
                intent1.putExtra("type", userType);
                startActivity(intent1);
                break;
            //场地管理
            case R.id.rl_ground_manage:
                jumpNextActivity(FieldListActivity.class, userType);
                break;
            //我要反馈
            case R.id.rl_feed_back:
                jumpNextActivity(FeedBackActivity.class, userType);
                break;
            //投资人订单状态
            case R.id.rl_order_status:
                jumpNextActivity(OrderStatusDetailActivity.class, userType);
                break;
            //我的收益
            case R.id.rl_my_income:
                // jumpNextActivity(SeeProfitActivity.class, userType);
                if(userType==3) jumpNextActivity(MyIncomeActivity.class,userType);//场地方
                if(userType==1) jumpNextActivity(InvestorIncomeActivity.class,userType);//投资人
                if(userType==2) jumpNextActivity(PartnerIncomeActivity.class,userType);//合伙人
                break;
            //运费
            case R.id.ll_freight:
                Intent intent2 = new Intent(UserCenterActivity.this, HasPostFieldFieldActivity.class);
                intent2.putExtra("type", userType);
                intent2.putExtra(HasPostFieldFieldActivity.ISFREIGHT, true);
                startActivity(intent2);
                break;
            //邀请码
            case R.id.tv_share_code:
                jumpNextActivity(ShareCodeActivity.class, userType);
                break;
            //升级会员
            case R.id.tv_update_level:
                jumpNextActivity(UpdateLevelActivity.class, userType);
                break;
            //绑定设备
            case R.id.rl_bind_device:
                jumpNextActivity(BindDeviceActivity.class, userType);
                break;
            //合伙人报备
            case R.id.rl_report_record:
                jumpNextActivity(ReportRecordActivity.class, userType);
                break;
            //合伙人推荐收益
            case R.id.rl_report_income:
                jumpNextActivity(ReportIncomeActivity.class, userType);
                break;
            //合伙人辖区收益
            case R.id.rl_city_income:
                jumpNextActivity(CityIncomeActivity.class, userType);
                break;
            //投币上缴
            case R.id.rl_coin_post:
                jumpNextActivity(PostMoneyRecordActivity.class, userType);
                break;
            case R.id.rl_tixian:
                intent = new Intent(UserCenterActivity.this, ApplyMoneyActivityNew.class);
                intent.putExtra(ApplyMoneyActivityNew.CAN_APPLY_MONEY, canApplyMoney);
                intent.putExtra(ApplyMoneyActivityNew.BANK_ACCOUNT, bankAccount);
                intent.putExtra(ApplyMoneyActivityNew.BANK_NAME, bankName);
                intent.putExtra(ApplyMoneyActivityNew.USER_TYPE, userType);
                intent.putExtra(ApplyMoneyActivityNew.NEED_PAY, pricePay);
                intent.putExtra(ApplyMoneyActivityNew.OPEN_ID,openid);
                intent.putExtra(ApplyMoneyActivityNew.WX_NICKNAME,wx_nicknam);
                startActivity(intent);
                break;
        }
    }

    private void jumpNextActivity(Class activityClass, int type) {
        Intent it = new Intent();
        it.setClass(this, activityClass);
        it.putExtra("type", type);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //当前用户是场地方的时候判断是否需要去缴币
        if(userType==3){
            new MyIncomeModle(getApplicationContext()).IsPayMoney(new MyIncomeModle.IsNeedPayMoneyCallback() {
                @Override
                    public void Sucess(IsNeedPayBean bindingBean) {
                        if(bindingBean!=null&&bindingBean.getRemind()==1){
                            DialogUtil.showPayMoneyDialog("您有"+bindingBean.getPrice_pay()+"元投币收益需要缴纳至平台，请尽快处理!", UserCenterActivity.this, new DialogUtil.OnDialogSureClick() {
                                @Override
                                public void sureClick() {
                                    Intent intent = new Intent(UserCenterActivity.this, ToPostMoneyActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                @Override
                public void Faile() {

                }
            });
        }
        initData();
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


    private void initHeadData() {
        if (userType == 1) {
            new MyIncomeModle(this).InvestorCanapplayCallback(new MyIncomeModle.CanApplyCallback() {
                @Override
                public void bindSucess(CanApplyResult bindingBean) {
                    if(bindingBean!=null&&bindingBean.getData()!=null) {
                        if (!TextUtils.isEmpty(bindingBean.getData().getPrice()))
                            canApplyMoney = Double.parseDouble(bindingBean.getData().getPrice()) + "";
                        bankAccount = bindingBean.getData().getBank_account();
                        bankName = bindingBean.getData().getBank();
                        openid = bindingBean.getData().getOpenid();
                        wx_nicknam = bindingBean.getData().getWx_nickname();
                    }
                }
            });
        } else if (userType == 2) {
            new MyIncomeModle(this).MarkcanapplayCallback(new MyIncomeModle.CanApplyCallback() {
                @Override
                public void bindSucess(CanApplyResult bindingBean) {
                    if(bindingBean!=null&&bindingBean.getData()!=null) {
                        if (!TextUtils.isEmpty(bindingBean.getData().getPrice()))
                            canApplyMoney = Double.parseDouble(bindingBean.getData().getPrice()) + "";
                        bankAccount = bindingBean.getData().getBank_account();
                        bankName = bindingBean.getData().getBank();
                        openid = bindingBean.getData().getOpenid();
                        wx_nicknam = bindingBean.getData().getWx_nickname();
                    }
                }
            });

        } else if (userType == 3) {
            new MyIncomeModle(this).canapplayCallback(new MyIncomeModle.CanApplyCallback() {
                @Override
                public void bindSucess(CanApplyResult bindingBean) {
                    if(bindingBean!=null&&bindingBean.getData()!=null) {
                        if (!TextUtils.isEmpty(bindingBean.getData().getPrice()))
                         canApplyMoney = Double.parseDouble(bindingBean.getData().getPrice()) + "";
                        bankAccount = bindingBean.getData().getBank_account();
                        bankName = bindingBean.getData().getBank();
                        openid = bindingBean.getData().getOpenid();
                        wx_nicknam = bindingBean.getData().getWx_nickname();
                        pricePay = bindingBean.getData().getPrice_pay();
                    }
                }
            });
        }
    }
}

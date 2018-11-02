package com.cheng.tonglepai.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.bean.WechatWithDrawBean;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.tonglepai.MyApplication;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.model.BindingModel;
import com.cheng.tonglepai.model.MyIncomeModle;
import com.cheng.tonglepai.net.ApplyMoneyRequest;
import com.cheng.tonglepai.net.FieldApplyMoneyRequest;
import com.cheng.tonglepai.net.InvestorApplyMoneyRequest;
import com.cheng.tonglepai.tool.DialogUtil;
import com.cheng.tonglepai.tool.MyChooseToastDialog;
import com.cheng.tonglepai.tool.MyToast;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by cheng on 2018/9/9.
 */

public class ApplyMoneyActivityNew extends TitleActivity implements View.OnClickListener{
    public static final String CAN_APPLY_MONEY = "apply.money";
    public static final String BANK_ACCOUNT = "bank.account";
    public static final String BANK_NAME = "bank.name";
    public static final String USER_TYPE = "user.type";
    public static final String INCOME_ALL = "z.price";
    public static final String NEED_PAY = "price.pay";
    public static final String OPEN_ID="openid";
    public static final String WX_NICKNAME="wx_nickname";
    private int userType = 0;
    private TextView bankName, bankAccount, tvCanApplyMoney, tvRealMoney, tvNeedPay, tvLastMoney, tvToPost;
    private Button btnToApply;
    private EditText etApplyMoney;
    private String bankNameShow, bankShow;
    private TextView shouxu_money;
    private LinearLayout llLastMoney, llNeedMoney, llHeadApply;
    private MyChooseToastDialog progressDialog;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private RelativeLayout rl_withdraw;
    private String openid;
    private String wx_nickname;
    private ImageView iv_bank;

    private int ACCOUNT_TYPE=0;//提现账户类型 0微信 1.银行卡
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_apply_money_new);
        MyApplication.getInstance().addActivity(this);
        setMidTitle("提现");
        userType = getIntent().getIntExtra(USER_TYPE, 0);
        addRightView();
        initView();
    }

    private void initView() {
        llLastMoney = (LinearLayout) findViewById(R.id.ll_last_money);
        llNeedMoney = (LinearLayout) findViewById(R.id.ll_need_money);
        llHeadApply = (LinearLayout) findViewById(R.id.ll_head_apply);
        tvNeedPay = (TextView) findViewById(R.id.tv_need_money);
        tvToPost = (TextView) findViewById(R.id.tv_to_post);
        tvLastMoney = (TextView) findViewById(R.id.tv_last_money);
        bankName = (TextView) findViewById(R.id.bank_name);
        bankAccount = (TextView) findViewById(R.id.bank_account);
        iv_bank= (ImageView) findViewById(R.id.iv_bank);

        tvCanApplyMoney = (TextView) findViewById(R.id.tv_can_apply_money);
        tvRealMoney = (TextView) findViewById(R.id.tv_real_money);
        btnToApply = (Button) findViewById(R.id.btn_to_apply);
        rl_withdraw= (RelativeLayout) findViewById(R.id.rl_withdraw);
        rl_withdraw.setOnClickListener(this);
        shouxu_money = (TextView) findViewById(R.id.shouxu_money);
        etApplyMoney = (EditText) findViewById(R.id.et_apply_money);

        bankNameShow = getIntent().getStringExtra(BANK_NAME);
        bankShow = getIntent().getStringExtra(BANK_ACCOUNT);
        openid=getIntent().getStringExtra(OPEN_ID);
        wx_nickname=getIntent().getStringExtra(WX_NICKNAME);

        if(!TextUtils.isEmpty(openid)&&openid.length()>5){
            bankName.setText(wx_nickname);
            iv_bank.setImageResource(R.mipmap.weixin_account);
            btnToApply.setEnabled(true);
            ACCOUNT_TYPE=0;
        }else if(!TextUtils.isEmpty(bankShow)&&bankShow.length()>5){
            bankName.setText(bankNameShow);
            bankAccount.setText(bankShow);
            btnToApply.setEnabled(true);
            ACCOUNT_TYPE=1;
            rl_withdraw.setBackgroundResource(R.color.C3);
            etApplyMoney.setHint("银行卡提现最低限额100元");
        }else {
            bankAccount.setVisibility(View.GONE);
            bankName.setText("您尚未绑定提现账户。请绑定微信提现");
            bankAccount.setText("");
//            llHeadApply.setVisibility(View.INVISIBLE);
            btnToApply.setEnabled(false);
        }
        bankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("4000-366-118".equals(bankAccount.getText().toString().trim())) {
                    progressDialog = MyToast.showChooseDialog(ApplyMoneyActivityNew.this, "您确定拨打：4000-366-118", "拨打电话", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(ApplyMoneyActivityNew.this,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(ApplyMoneyActivityNew.this,
                                        Manifest.permission.CALL_PHONE)) {
                                    Toast.makeText(ApplyMoneyActivityNew.this, "请授权！", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                } else {
                                    ActivityCompat.requestPermissions(ApplyMoneyActivityNew.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                }
                            } else {
                                // 已经获得授权，可以打电话
                                CallPhone();
                            }
                        }
                    });
                }
            }
        });

      /*  if ("0".equals(getIntent().getStringExtra(CAN_APPLY_MONEY)))
            tvCanApplyMoney.setText("0.00");
        else {*/
            if (userType == 3) {
                if(!TextUtils.isEmpty(getIntent().getStringExtra(CAN_APPLY_MONEY))&&!TextUtils.isEmpty(getIntent().getStringExtra(NEED_PAY))) {
                    if ((Double.parseDouble(getIntent().getStringExtra(CAN_APPLY_MONEY)) - Double.parseDouble(getIntent().getStringExtra(NEED_PAY))) < 10) {
                        btnToApply.setEnabled(false);
                    } else {
                        btnToApply.setEnabled(true);
                    }
                    tvCanApplyMoney.setText(String.valueOf(Double.parseDouble(getIntent().getStringExtra(CAN_APPLY_MONEY)) - Double.parseDouble(getIntent().getStringExtra(NEED_PAY))));
                }
            } else {
                if(!TextUtils.isEmpty(getIntent().getStringExtra(CAN_APPLY_MONEY))) {
                    if (Double.parseDouble(getIntent().getStringExtra(CAN_APPLY_MONEY)) < 10) {
                        btnToApply.setEnabled(false);
                    } else {
                        btnToApply.setEnabled(true);
                    }
                    tvCanApplyMoney.setText(getIntent().getStringExtra(CAN_APPLY_MONEY));
                }
            }
       /* }*/
        if (userType == 3) {
            llLastMoney.setVisibility(View.VISIBLE);
            llNeedMoney.setVisibility(View.VISIBLE);
            tvLastMoney.setText(getIntent().getStringExtra(CAN_APPLY_MONEY));
            tvNeedPay.setText(getIntent().getStringExtra(NEED_PAY));
        } else {
            llLastMoney.setVisibility(View.GONE);
            llNeedMoney.setVisibility(View.GONE);
        }


        etApplyMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //微信提现跟银行卡提现手续费不同
                    if (ACCOUNT_TYPE == 0) {
                        if (Double.parseDouble(s.toString()) * 0.006 < 1) {
                            shouxu_money.setText("1");
                            tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - 1));
                        } else {
                            if (String.valueOf(Double.parseDouble(s.toString()) * 0.006).length() > 4) {
                                shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4));
                                tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4))));
                            } else {
                                shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006));
                                tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(s.toString()) * 0.006));
                            }
                        }
                    }else {
                        if (Double.parseDouble(s.toString()) * 0.006 < 5) {
                            shouxu_money.setText("5");
                            tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - 5));
                        } else {
                            if (String.valueOf(Double.parseDouble(s.toString()) * 0.006).length() > 4) {
                                shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4));
                                tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(String.valueOf(Double.parseDouble(s.toString()) * 0.006 + 0.01).substring(0, 4))));
                            } else {
                                shouxu_money.setText(String.valueOf(Double.parseDouble(s.toString()) * 0.006));
                                tvRealMoney.setText(String.valueOf(Double.parseDouble(s.toString()) - Double.parseDouble(s.toString()) * 0.006));
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });


        btnToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               toApply();
            }
        });

        tvToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyMoneyActivityNew.this, ToPostMoneyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addRightView() {
        TextView rightView = (TextView) View.inflate(this, R.layout.view_title_right_text, null);
        rightView.setText("查看明细");
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ApplyMoneyActivityNew.this, ApplyDetailActivity.class);
                intent.putExtra("type", userType);
                startActivity(intent);
            }
        });
        setRightView(rightView);
    }

    private void toApply() {
        String applyMoney = etApplyMoney.getText().toString().trim();
        if (TextUtils.isEmpty(applyMoney)) {
            Toast.makeText(ApplyMoneyActivityNew.this, "请输入申请提现金额", Toast.LENGTH_LONG).show();
            return;
        }
        if (ACCOUNT_TYPE==0&&Integer.parseInt(applyMoney) < 10) {
            Toast.makeText(ApplyMoneyActivityNew.this, "微信单笔提现不得小于10元", Toast.LENGTH_LONG).show();
            return;
        }
        if (ACCOUNT_TYPE==1&&Integer.parseInt(applyMoney) < 100) {
            Toast.makeText(ApplyMoneyActivityNew.this, "银行卡单笔提现不得小于100元", Toast.LENGTH_LONG).show();
            return;
        }
        if(userType == 3){
            if((Double.parseDouble(applyMoney)+Double.parseDouble(shouxu_money.getText().toString()))>(Double.parseDouble(getIntent().getStringExtra(CAN_APPLY_MONEY)) - Double.parseDouble(getIntent().getStringExtra(NEED_PAY)))){
                Toast.makeText(ApplyMoneyActivityNew.this, "提现金额超出可提现金额", Toast.LENGTH_LONG).show();
                return;
            }
        }
        //如果账户类型是银行就提现到银行卡，如果是微信就提现到微信
        if(ACCOUNT_TYPE==1) {
            DialogUtil.showChangDialog("确定提现到银行卡?",ApplyMoneyActivityNew.this,"立即提现", new DialogUtil.OnDialogSureClick() {
                @Override
                public void sureClick() {
                    ApplyToBank();
                }
            });

        }else if(ACCOUNT_TYPE==0){
            DialogUtil.showChangDialog("确定提现到微信账号"+wx_nickname+"?",ApplyMoneyActivityNew.this,"立即提现", new DialogUtil.OnDialogSureClick() {
                @Override
                public void sureClick() {
                    ApplyToWechat();
                }
            });
        }
    }

    private void CallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "400-0366118");
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_withdraw:
                Intent intent=new Intent(this,SelectAccountActivity.class);
                intent.putExtra(BANK_NAME,bankNameShow);
                intent.putExtra(BANK_ACCOUNT,bankShow);
                intent.putExtra(OPEN_ID,openid);
                intent.putExtra(WX_NICKNAME,wx_nickname);
                intent.putExtra("ACCOUNT_TYPE",ACCOUNT_TYPE);
                startActivityForResult(intent,0x002);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x110){
            ACCOUNT_TYPE=data.getIntExtra("ACCOUNT_TYPE",0);
            refreshUI();
        }
    }

    private void refreshUI(){
        if(ACCOUNT_TYPE==0&&!TextUtils.isEmpty(openid)&&openid.length()>5){
            bankName.setText(wx_nickname);
            iv_bank.setImageResource(R.mipmap.weixin_account);
            btnToApply.setEnabled(true);
            etApplyMoney.setHint("微信提现最低限额100元");
            bankAccount.setVisibility(View.VISIBLE);
        }else if(ACCOUNT_TYPE==1&&!TextUtils.isEmpty(bankShow)&&bankShow.length()>5) {
            bankName.setText(bankNameShow);
            bankAccount.setText(bankShow);
            bankAccount.setVisibility(View.VISIBLE);
            btnToApply.setEnabled(true);
            ACCOUNT_TYPE=1;
            etApplyMoney.setHint("银行卡提现最低限额100元");
            rl_withdraw.setBackgroundResource(R.color.C3);
            bankAccount.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 提现到银行卡
     */
    private void ApplyToBank(){
        if (userType == 2) {
            ApplyMoneyRequest mRequest = new ApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }

                @Override
                public void onFailed(String msg, int code) {
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                }
            });

            mRequest.requestApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        } else if (userType == 1) {
            InvestorApplyMoneyRequest mRequest = new InvestorApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    //马上提现
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);

                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }
            });

            mRequest.requestInvestorApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        } else if (userType == 3) {
            FieldApplyMoneyRequest mRequest = new FieldApplyMoneyRequest(ApplyMoneyActivityNew.this);
            mRequest.setListener(new BaseHttpRequest.IRequestListener<BaseHttpResult>() {
                @Override
                public void onSuccess(BaseHttpResult data) {
                    //马上提现
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);

                }

                @Override
                public void onFailed(String msg, int code) {
                    Toast.makeText(ApplyMoneyActivityNew.this, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }
            });

            mRequest.requestFieldApplyMoney(etApplyMoney.getText().toString().trim(), bankNameShow, bankShow, tvRealMoney.getText().toString().trim());
        }
    }

    /**
     * 提现到微信
     */
    private void ApplyToWechat(){
        new BindingModel(this).WechatWithDraw(etApplyMoney.getText().toString().trim(), tvRealMoney.getText().toString().trim(), new BindingModel.WechatWithDraw() {
            @Override
            public void withDraw(WechatWithDrawBean bindingBean) {
                if(bindingBean!=null&&bindingBean.getStatus()==1){
                    //马上提现
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                    intent.putExtra(ApplySuccessActivity.IS_SUCCESS, true);
                    intent.putExtra(ApplySuccessActivity.TYPE, userType);
                    startActivity(intent);
                }
            }

            @Override
            public void onFaile() {
                //马上提现
                Intent intent = new Intent(ApplyMoneyActivityNew.this, ApplySuccessActivity.class);
                intent.putExtra(ApplySuccessActivity.IS_SUCCESS, false);
                intent.putExtra(ApplySuccessActivity.TYPE, userType);
                startActivity(intent);
            }
        });
    }
}

package com.cheng.retrofit20;


import com.cheng.retrofit20.bean.BankWithDrawBean;
import com.cheng.retrofit20.bean.IsNeedPayBean;
import com.cheng.retrofit20.bean.SingalDetectionBean;
import com.cheng.retrofit20.bean.VerasionControlBean;
import com.cheng.retrofit20.bean.WXLoginGetAccesBean;
import com.cheng.retrofit20.bean.WXUserInfoBean;
import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.retrofit20.bean.WechatWithDrawBean;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.retrofit20.data.AllDeviceResult;
import com.cheng.retrofit20.data.ApplyDetailData;
import com.cheng.retrofit20.data.BaseBackResult;
import com.cheng.retrofit20.data.BindDeviceListResult;
import com.cheng.retrofit20.data.BusinessTypeResult;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.retrofit20.data.CanApplyResultNew;
import com.cheng.retrofit20.data.CheckinfoBillResult;
import com.cheng.retrofit20.data.CheckokInfoResult;
import com.cheng.retrofit20.data.CheckokRecordsResult;
import com.cheng.retrofit20.data.ChooseTypeResult;
import com.cheng.retrofit20.data.DeviceBillResult;
import com.cheng.retrofit20.data.DeviceListResult;
import com.cheng.retrofit20.data.DevicePriceResult;
import com.cheng.retrofit20.data.EquimentDetailResult;
import com.cheng.retrofit20.data.FieldIncomeResult;
import com.cheng.retrofit20.data.FieldListResult;
import com.cheng.retrofit20.data.HsaPostFieldResult;
import com.cheng.retrofit20.data.IncomeMainResult;
import com.cheng.retrofit20.data.InvestorAddviceResult;
import com.cheng.retrofit20.data.InvestorAllIncomeResult;
import com.cheng.retrofit20.data.InvestorChooseCheckResult;
import com.cheng.retrofit20.data.InvestorFieldListResult;
import com.cheng.retrofit20.data.InvestorIncomeResult;
import com.cheng.retrofit20.data.InvestorUserInfoResult;
import com.cheng.retrofit20.data.OrderNoResult;
import com.cheng.retrofit20.data.PartenerdesResult;
import com.cheng.retrofit20.data.PayReturnInfoResult;
import com.cheng.retrofit20.data.PostMoneyRecordResult;
import com.cheng.retrofit20.data.RefereeListResult;
import com.cheng.retrofit20.data.ReportRecordResult;
import com.cheng.retrofit20.data.ShareCodeResult;
import com.cheng.retrofit20.data.SmsLoginResult;
import com.cheng.retrofit20.data.TestData;
import com.cheng.retrofit20.data.UnCheckResonResult;
import com.cheng.retrofit20.data.UnpassFieldDetailResult;
import com.cheng.retrofit20.data.UserInfoResult;
import com.cheng.retrofit20.data.VersionControlResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by cheng on 2018/5/21.
 */

public interface ApiService {

    /**
     * 更新版本
     */
    @POST("index.php?s=/api/User/tlp_edition")
    @FormUrlEncoded
    Call<VerasionControlBean> getVersionControl(@FieldMap Map<String, String> params);

    /**
     * 首页收益情况
     */
    @POST("index.php?s=/api/User/check_total")
    @FormUrlEncoded
    Call<IncomeMainResult> getIncomeMain(@FieldMap Map<String, String> params);

    /**
     *
     */
    @POST("index.php?s=/api/User/sms/")
    @FormUrlEncoded
    Call<TestData> getOrderStatus(@FieldMap Map<String, String> params);

    /**
     *
     */
    @POST("index.php?s=/api/User/loginout/")
    @FormUrlEncoded
    Call<BaseHttpResult> getLoginout(@FieldMap Map<String, String> params);

    /**
     * 手机验证码登录
     */
    @POST("index.php?s=/api/User/login_by_code/")
    @FormUrlEncoded
    Call<SmsLoginResult> getPhoneSms(@FieldMap Map<String, String> params);

    /**
     * 用户登录
     */
    @POST("index.php?s=/api/User/login")
    @FormUrlEncoded
    Call<SmsLoginResult> getLogin(@FieldMap Map<String, String> params);

    /**
     * 用户登录
     */
    @POST("index.php?s=/api/User/modify_pwd")
    @FormUrlEncoded
    Call<BaseHttpResult> getModifyPsw(@FieldMap Map<String, String> params);

    /**
     * 用户注册
     */
    @POST("index.php?s=/api/User/register")
    @FormUrlEncoded
    Call<SmsLoginResult> getRegister(@FieldMap Map<String, String> params);

    /**
     * 投资人发送验证码
     */
    @POST("index.php?s=/api/Maker/sms/")
    @FormUrlEncoded
    Call<BaseHttpResult> getMakerSms(@FieldMap Map<String, String> params);

    /**
     * 合伙人提现
     */
    @POST("index.php?s=/api/Maker/tixian_new")
    @FormUrlEncoded
    Call<BaseHttpResult> getPartnerApplyMoney(@FieldMap Map<String, String> params);

    /**
     * 合伙人提现详情
     */
    @POST("index.php?s=/api/maker/des")
    @FormUrlEncoded
    Call<ApplyDetailData> getApplyDetailData(@FieldMap Map<String, String> params);

    /**
     * 合伙人已报备的情况
     */
    @POST("index.php?s=/api/maker/yibaobei")
    @FormUrlEncoded
    Call<HsaPostFieldResult> getHsaPostField(@FieldMap Map<String, String> params);

    /**
     * 合伙人已报备场地合伙人需要支付的运费
     */
    @POST("index.php?s=/api/maker/yibaobeipay")
    @FormUrlEncoded
    Call<HsaPostFieldResult> getNeedMoneyField(@FieldMap Map<String, String> params);

    /**
     * 合伙人已报备场地场地审核的结果
     */
    @POST("index.php?s=/api/maker/checked")
    @FormUrlEncoded
    Call<HsaPostFieldResult> getCheckedField(@FieldMap Map<String, String> params);

    /**
     * 合伙人用户信息
     */
    @POST("index.php?s=/api/maker/home")
    @FormUrlEncoded
    Call<UserInfoResult> getUserInfo(@FieldMap Map<String, String> params);

    /**
     * 异常设备
     */
    @POST("index.php?s=/api/maker/area_list_yichang")
    @FormUrlEncoded
    Call<AllDeviceResult> getExceptionDevice(@FieldMap Map<String, String> params);

    /**
     * 登录成功后合伙人的设备管理
     */
    @POST("index.php?s=/api/maker/adddevice")
    @FormUrlEncoded
    Call<AllDeviceResult> getAllDevice(@FieldMap Map<String, String> params);

    /**
     * 场地列表
     */
    @POST("index.php?s=/api/maker/area_list")
    @FormUrlEncoded
    Call<FieldListResult> getFieldList(@FieldMap Map<String, String> params);

    /**
     * 场地列表筛选
     */
    @POST("index.php?s=/api/maker/area_lis_search")
    @FormUrlEncoded
    Call<FieldListResult> getFieldSearchList(@FieldMap Map<String, String> params);

    /**
     * 场地列表已投
     */
    @POST("index.php?s=/api/maker/area_list_yitou")
    @FormUrlEncoded
    Call<FieldListResult> getFieldYitouList(@FieldMap Map<String, String> params);

    /**
     * 提交报修设备
     */
    @POST("index.php?s=/api/maker/device")
    @FormUrlEncoded
    Call<BaseHttpResult> getPostExceptionDevice(@FieldMap Map<String, String> params);

    /**
     * 设备是否需要修理
     */
    @POST("index.php?s=/api/maker/is_device")
    @FormUrlEncoded
    Call<BaseHttpResult> getNeedRepairDevice(@FieldMap Map<String, String> params);

    /**
     * 提交场地报备
     */
    @POST("index.php?s=/api/maker/report")
    Call<BaseBackResult> GetPostFieldInfo(@Body String params);

    /**
     * 合伙人场地收益
     */
    @POST("index.php?s=/api/maker/check")
    @FormUrlEncoded
    Call<FieldIncomeResult> getFieldIncome(@FieldMap Map<String, String> params);

    /**
     * 获取设备列表
     */
    @POST("index.php?s=/api/Maker/get_device")
    @FormUrlEncoded
    Call<DeviceListResult> getDeviceList(@FieldMap Map<String, String> params);

    /**
     * 获取未通过场地
     */
    @POST("index.php?s=/api/Maker/checkdown")
    @FormUrlEncoded
    Call<UnCheckResonResult> getCheckDown(@FieldMap Map<String, String> params);

    /**
     * 合伙人运费明细
     */
    @POST("index.php?s=/api/Maker/partnerdes")
    @FormUrlEncoded
    Call<PartenerdesResult> getPartenerdes(@FieldMap Map<String, String> params);

    /**
     * 我要反馈
     */
    @POST("index.php?s=/api/maker/feedback")
    @FormUrlEncoded
    Call<BaseHttpResult> getFeedBack(@FieldMap Map<String, String> params);

    /**
     * 选择身份接口
     */
    @POST("index.php?s=/api/user/choose_login")
    @FormUrlEncoded
    Call<ChooseTypeResult> getChooseType(@FieldMap Map<String, String> params);

    /**
     * 未通过场地详情
     */
    @POST("index.php?s=/api/Maker/checkreport")
    @FormUrlEncoded
    Call<UnpassFieldDetailResult> getUnpassFieldDetail(@FieldMap Map<String, String> params);

    /**
     * 合伙人获取经营属性
     */
    @POST("index.php?s=/api/Maker/business")
    @FormUrlEncoded
    Call<BusinessTypeResult> getBusinessType(@FieldMap Map<String, String> params);

    /**
     * 合伙人可提现金额
     */
    @POST("index.php?s=/api/Maker/amount_new")
    @FormUrlEncoded
    Call<CanApplyResult> getMarkerCanApply(@FieldMap Map<String, String> params);

    /**
     * 合伙人我的收益
     */
    @POST("index.php?s=/api/Maker/earningslist/")
    Call<InvestorAllIncomeResult> getMarkerAllIncome(@Body String params);

    /**
     * 合伙人运费形成订单
     */
    @POST("index.php?s=/api/Maker/orderonlycode")
    @FormUrlEncoded
    Call<OrderNoResult> getFreightOrderNo(@FieldMap Map<String, String> params);

    /**
     * 合伙人支付宝支付接口
     */
    @POST("index.php?s=/api/Maker/alipay_payment")
    @FormUrlEncoded
    Call<AlipayResult> getMakerAlipay(@FieldMap Map<String, String> params);

    /**
     * 合伙人支付成功返回信息
     */
    @POST("index.php?s=/api/Maker/returninfo")
    @FormUrlEncoded
    Call<PayReturnInfoResult> getMakerPayReturnInfo(@FieldMap Map<String, String> params);

    /**
     * 合伙人绑定手机
     */
    @POST("index.php?s=/api/Maker/update")
    @FormUrlEncoded
    Call<TestData> getMakerUpdate(@FieldMap Map<String, String> params);

    /**
     * 合伙人重新报备
     */
    @POST("index.php?s=/api/Maker/amendreport")
    Call<BaseHttpResult> getAmendReport(@Body String params);

    /**
     * 绑定的设备列表
     */
    @POST("index.php?s=/api/Maker/device_lists")
    @FormUrlEncoded
    Call<BindDeviceListResult> getBindDeviceList(@FieldMap Map<String, String> params);

    /**
     * 绑定设备
     */
    @POST("index.php?s=/api/Maker/binding_device")
    @FormUrlEncoded
    Call<BaseHttpResult> getBindingDeviceList(@FieldMap Map<String, String> params);
    // ------------------投资人接口

    /**
     * 投资人提现
     */
    @POST("index.php?s=/api/Investor/tixian_new")
    @FormUrlEncoded
    Call<BaseHttpResult> getInvestorApplyMoney(@FieldMap Map<String, String> params);

    /**
     * 投资人场地列表
     */
    @POST("index.php?s=/api/Investor/sitelist_new")
    @FormUrlEncoded
    Call<InvestorFieldListResult> getInvestorFieldListpe(@FieldMap Map<String, String> params);

    /**
     * 投资人查看场地
     */
    @POST("index.php?s=/api/Investor/choose_check")
    @FormUrlEncoded
    Call<InvestorChooseCheckResult> getChooseCheck(@FieldMap Map<String, String> params);

    /**
     * 投资人提现详情
     */
    @POST("index.php?s=/api/Investor/des")
    @FormUrlEncoded
    Call<ApplyDetailData> getInvestorApplyDetailData(@FieldMap Map<String, String> params);

    /**
     * 投资人我要反馈
     */
    @POST("index.php?s=/api/Investor/feedback")
    @FormUrlEncoded
    Call<BaseHttpResult> getInvestorFeedBack(@FieldMap Map<String, String> params);

    /**
     * 投资人发送验证码
     */
    @POST("index.php?s=/api/Investor/sms/")
    @FormUrlEncoded
    Call<TestData> getInvestorSms(@FieldMap Map<String, String> params);

    /**
     * 投资人綁定手機
     */
    @POST("index.php?s=/api/Investor/update")
    @FormUrlEncoded
    Call<TestData> getInvestorUpdate(@FieldMap Map<String, String> params);

    /**
     * 投资人收益情况
     */
    @POST("index.php?s=/api/Investor/check")
    @FormUrlEncoded
    Call<InvestorIncomeResult> getInvestorIncome(@FieldMap Map<String, String> params);

    /**
     * 投资人设备管理
     */
    @POST("index.php?s=/api/Investor/adddevice")
    @FormUrlEncoded
    Call<InvestorAddviceResult> getInvestorDevice(@FieldMap Map<String, String> params);

    /**
     * 投资人用户信息
     */
    @POST("index.php?s=/api/Investor/home")
    @FormUrlEncoded
    Call<InvestorUserInfoResult> getInvestorUserInfo(@FieldMap Map<String, String> params);

    /**
     * 投资人我的收益
     */
    @POST("index.php?s=/api/Investor/earningslist/")
    Call<InvestorAllIncomeResult> getInvestorAllIncome(@Body String params);

    /**
     * 投资人可提现金额
     */
    @POST("index.php?s=/api/Investor/amount_new")
    @FormUrlEncoded
    Call<CanApplyResult> getInvestorCanApply(@FieldMap Map<String, String> params);

    /**
     * 投场地形成订单
     */
    @POST("index.php?s=/api/Investor/orderonlycode")
    @FormUrlEncoded
    Call<OrderNoResult> getOrderNo(@FieldMap Map<String, String> params);

    /**
     * 支付宝支付接口
     */
    @POST("index.php?s=/api/Investor/alipay_payment")
    @FormUrlEncoded
    Call<AlipayResult> getAlipay(@FieldMap Map<String, String> params);

    /**
     * 支付成功返回信息
     */
    @POST("index.php?s=/api/Investor/returninfo")
    @FormUrlEncoded
    Call<PayReturnInfoResult> getPayReturnInfo(@FieldMap Map<String, String> params);

    /**
     * 查看已经成功的场地
     */
    @POST("index.php?s=/api/Investor/checkokinfo")
    @FormUrlEncoded
    Call<CheckokInfoResult> getCheckokInfo(@FieldMap Map<String, String> params);

    /**
     * 投资人查看运输状态
     */
    @POST("index.php?s=/api/Investor/checkrecords")
    @FormUrlEncoded
    Call<CheckokRecordsResult> getCheckokRecords(@FieldMap Map<String, String> params);

    /**
     * 投资人撤回设备
     */
    @POST("index.php?s=/api/Investor/fieldtransfer_new")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldMoveIn(@FieldMap Map<String, String> params);

    /**
     * 投资人迁入设备
     */
    @POST("index.php?s=/api/Investor/fieldmovein_new")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldTransfer(@FieldMap Map<String, String> params);

    /**
     * 投资人迁入设备
     */
    @POST("index.php?s=/api/User/share_code")
    @FormUrlEncoded
    Call<ShareCodeResult> getShareCode(@FieldMap Map<String, String> params);


    /**
     * 場地人用户信息
     */
    @POST("index.php?s=/api/field/home")
    @FormUrlEncoded
    Call<UserInfoResult> getFieldInfo(@FieldMap Map<String, String> params);

    /**
     * 场地人我要反馈
     */
    @POST("index.php?s=/api/Field/feedback")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldFeedBack(@FieldMap Map<String, String> params);

    /**
     * 场地人发送验证码
     */
    @POST("index.php?s=/api/Field/sms/")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldSms(@FieldMap Map<String, String> params);

    /**
     * 场地人绑定手机
     */
    @POST("index.php?s=/api/Field/update")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldUpdate(@FieldMap Map<String, String> params);

    /**
     * 场地人的设备管理
     */
    @POST("index.php?s=/api/Field/adddevice")
    @FormUrlEncoded
    Call<AllDeviceResult> getAllFieldDevice(@FieldMap Map<String, String> params);

    /**
     * 场地人的设备是否需要修理
     */
    @POST("index.php?s=/api/Field/is_device")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldRepairDevice(@FieldMap Map<String, String> params);

    /**
     * 场地人提交报修设备
     */
    @POST("index.php?s=/api/Field/device")
    @FormUrlEncoded
    Call<BaseHttpResult> getPostFieldExceptionDevice(@FieldMap Map<String, String> params);

    /**
     * 场地人提现
     */
    @POST("index.php?s=/api/Field/tixian_new")
    @FormUrlEncoded
    Call<BaseHttpResult> getFieldApplyMoney(@FieldMap Map<String, String> params);

    /**
     * 场地人可提现金额
     */
    @POST("index.php?s=/api/field/amount_new")
    @FormUrlEncoded
    Call<CanApplyResult> getFieldCanApply(@FieldMap Map<String, String> params);

    /**
     * 场地人我的收益
     */
    @POST("index.php?s=/api/Field/earningslist/")
    Call<InvestorAllIncomeResult> getFieldAllIncome(@Body String params);

    /**
     * 异常设备
     */
    @POST("index.php?s=/api/Field/area_list_yichang")
    @FormUrlEncoded
    Call<AllDeviceResult> getFieldDevice(@FieldMap Map<String, String> params);

    //-------------公共类-----------

    /**
     * 提现详情
     */
    @POST("index.php?s=/api/User/des")
    @FormUrlEncoded
    Call<ApplyDetailData> getApplyDetail(@FieldMap Map<String, String> params);

    /**
     * 收益详情
     */
    @POST("index.php?s=/api/Maker/checkinfo_bill")
    @FormUrlEncoded
    Call<CheckinfoBillResult> getCheckinfoBill(@FieldMap Map<String, String> params);

    /**
     * 收益详情
     */
    @POST("index.php?s=/api/field/checkinfo_bill")
    @FormUrlEncoded
    Call<CheckinfoBillResult> getFieldCheckinfoBill(@FieldMap Map<String, String> params);

    /**
     * 收益详情
     */
    @POST("index.php?s=/api/investor/checkinfo_bill")
    @FormUrlEncoded
    Call<CheckinfoBillResult> getInvestorCheckinfoBill(@FieldMap Map<String, String> params);

    /**
     * 合伙人报备列表
     */
    @POST("index.php?s=/api/maker/tj_list_new")
    @FormUrlEncoded
    Call<ReportRecordResult> getReportRecord(@FieldMap Map<String, String> params);

    /**
     * 推荐人报备
     */
    @POST("index.php?s=/api/user/referee_preparation")
    @FormUrlEncoded
    Call<BaseHttpResult> getRefereePreparation(@FieldMap Map<String, String> params);

    /**
     * 推荐人列表收益
     */
    @POST("index.php?s=/api/Maker/referee_list_new")
    @FormUrlEncoded
    Call<RefereeListResult> getRefereeList(@FieldMap Map<String, String> params);

    /**
     * 轄區收益
     */
    @POST("index.php?s=/api/Maker/district_revenue")
    @FormUrlEncoded
    Call<RefereeListResult> getCityIncome(@FieldMap Map<String, String> params);


    /**
     * 场地方余额缴费
     */
    @POST("index.php?s=/api/field/amount_pay")
    @FormUrlEncoded
    Call<BaseHttpResult> getAmountPay(@FieldMap Map<String, String> params);

    /**
     * 场地方支付宝上缴
     */
    @POST("index.php?s=/api/field/alipay_pricepay")
    @FormUrlEncoded
    Call<AlipayResult> getAliPricePay(@FieldMap Map<String, String> params);

    /**
     * 场地方上缴记录
     */
    @POST("index.php?s=/api/field/turn_record")
    @FormUrlEncoded
    Call<PostMoneyRecordResult> getPostMoneyRecord(@FieldMap Map<String, String> params);

    /**
     * 合伙人场地设备收益
     */
    @POST("index.php?s=/api/maker/device_price")
    @FormUrlEncoded
    Call<DevicePriceResult> getMarkerDevicePrice(@FieldMap Map<String, String> params);

    /**
     * 投资人场地设备收益
     */
    @POST("index.php?s=/api/investor/device_price")
    @FormUrlEncoded
    Call<DevicePriceResult> getInvestorDevicePrice(@FieldMap Map<String, String> params);

    /**
     * 场地人场地设备收益
     */
    @POST("index.php?s=/api/field/device_price")
    @FormUrlEncoded
    Call<DevicePriceResult> getFieldDevicePrice(@FieldMap Map<String, String> params);

    /**
     * 合伙人场地设备收益明细
     */
    @POST("index.php?s=/api/maker/device_bill")
    @FormUrlEncoded
    Call<DeviceBillResult> getMarkerDeviceBill(@FieldMap Map<String, String> params);

    /**
     * 投资人场地设备收益明细
     */
    @POST("index.php?s=/api/investor/device_bill")
    @FormUrlEncoded
    Call<DeviceBillResult> getInvestorDeviceBill(@FieldMap Map<String, String> params);

    /**
     * 场地人场地设备收益明细
     */
    @POST("index.php?s=/api/field/device_bill")
    @FormUrlEncoded
    Call<DeviceBillResult> getFieldDeviceBill(@FieldMap Map<String, String> params);

    /**
     * 获取摇摇车详情
     */
    @POST("index.php?s=/api/maker/check_device_info")
    @FormUrlEncoded
    Call<EquimentDetailResult> getEquimentDetail(@FieldMap Map<String, String> params);

    /**
     * 检测信号
     */
    @POST("index.php?s=/api/user/signal_detection")
    @FormUrlEncoded
    Call<SingalDetectionBean> DetectionSignal(@FieldMap Map<String, String> params);

    /**
     * 微信登录
     */
    @GET
    Call<WXLoginGetAccesBean> WXLogin(@Url String url);
    /**
     * 微信获取用户信息
     */
    @GET
    Call<WXUserInfoBean> WXGetUserInfo(@Url String url);

    /**
     * 绑定微信
     */
    @POST("index.php?s=/api/user/bind_wechat")
    @FormUrlEncoded
    Call<WechatBindingBean> bindingWechat(@FieldMap Map<String, String> params);
    /**
     * 换绑微信
     */
    @POST("index.php?s=/api/user/change_wechat")
    @FormUrlEncoded
    Call<WechatBindingBean> ChangeBindingWechat(@FieldMap Map<String, String> params);

    /**
     * =微信提现
     */
    @POST("index.php?s=/api/user/wechat_applicationcash")
    @FormUrlEncoded
    Call<WechatWithDrawBean> WechatWithDraw(@FieldMap Map<String, String> params);

    /**
     * 投资人提现
     */
    @POST("index.php?s=/api/Investor/tixian_new")
    @FormUrlEncoded
    Call<WechatWithDrawBean> TouziRenWithDraw(@FieldMap Map<String, String> params);

    /**
     * 场地人提现
     */
    @POST("index.php?s=/api/Field/tixian_new")
    @FormUrlEncoded
    Call<WechatWithDrawBean> ChangdirenWithDraw(@FieldMap Map<String, String> params);

    /**
     * 合伙人提现
     */
    @POST("index.php?s=/api/Maker/tixian_new")
    @FormUrlEncoded
    Call<WechatWithDrawBean> HehuorenWithDraw(@FieldMap Map<String, String> params);

    /**
     * 判断是否需要缴币
     */
    @POST(" index.php?s=/api/user/remind_coin")
    @FormUrlEncoded
    Call<IsNeedPayBean> IsNeedPayMoney(@FieldMap Map<String, String> params);
}

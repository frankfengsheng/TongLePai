package com.cheng.tonglepai.data;

/**
 * Created by cheng on 2018/9/9.
 */

public class CanApplyData {
    private String bank_account;
    private String bank;
    private String price;
    private String price_pay;//需缴金额
    private String z_price;//收益余额
    private String openid;//微信openid
    private String wx_nickname;//微信昵称

    public String getPrice_pay() {
        return price_pay;
    }

    public void setPrice_pay(String price_pay) {
        this.price_pay = price_pay;
    }

    public String getZ_price() {
        return z_price;
    }

    public void setZ_price(String z_price) {
        this.z_price = z_price;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }
}

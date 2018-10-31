package com.cheng.retrofit20.bean;

/**
 * Created by Administrator on 2018/10/30 0030.
 */

public class WXLoginGetAccesBean {


    /**
     * access_token : 15_XpzxHY70jSrgV84XkbhAmNXneH8YEmU0DZgye_xrpcvLh5NYATf3a5uuObDFTm5BCigasXceMb9sbIaXCXEczsG80WFkx_WkyUI-WTTOJpY
     * expires_in : 7200
     * refresh_token : 15_6SnHXfpE8-mADILy2fxyWC-b4rdkURxy5GKgBiKvZb_OnaL0F6JB7Wz-RrRQWe2cCiNvchAo-8calY_PoD3KQYAs8P2Zavy3V7m3cCH174c
     * openid : oQZPy1KPk91E5-M7nmrve9Z4xe4Y
     * scope : snsapi_userinfo
     * unionid : obOX908Csf6jYkHr4SITirDWPqEY
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}

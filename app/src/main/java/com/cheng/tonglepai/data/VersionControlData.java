package com.cheng.tonglepai.data;

/**
 * Created by cheng on 2018/8/13.
 */

public class VersionControlData {
    private String ios_address;
    private String android_address;
    private String ios_number;
    private String android_number;
    private String androidis_update;

    public String getAndroidis_update() {
        return androidis_update;
    }

    public void setAndroidis_update(String androidis_update) {
        this.androidis_update = androidis_update;
    }

    public String getIos_address() {
        return ios_address;
    }

    public void setIos_address(String ios_address) {
        this.ios_address = ios_address;
    }

    public String getAndroid_address() {
        return android_address;
    }

    public void setAndroid_address(String android_address) {
        this.android_address = android_address;
    }

    public String getIos_number() {
        return ios_number;
    }

    public void setIos_number(String ios_number) {
        this.ios_number = ios_number;
    }

    public String getAndroid_number() {
        return android_number;
    }

    public void setAndroid_number(String android_number) {
        this.android_number = android_number;
    }
}

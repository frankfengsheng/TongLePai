package com.cheng.tonglepai.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by yfjin on 16/3/21.
 */
public class AlipayUtil {


    public static boolean canPay(Context context) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            String pn = pI.packageName;
            if (pn.equalsIgnoreCase("com.eg.android.AlipayGphone")
                    || pn.equalsIgnoreCase("com.alipay.android.app"))
                return true;
        }
        return false;
    }
}

package com.cheng.tonglepai.tool;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class DoubleUtils {
    public static String DoulePares(Double aa){
        return new DecimalFormat("#.00").format(aa);
    }
}

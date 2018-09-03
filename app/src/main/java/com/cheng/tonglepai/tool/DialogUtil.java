package com.cheng.tonglepai.tool;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.cheng.tonglepai.R;

/**
 * Created by cheng on 2018/7/16.
 */

public class DialogUtil {
    public static LoadingDialog createLoaddingDialog(Context context) {
        LoadingDialog dialog = new LoadingDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_loading_progress);
        // 按返回键是否取消
        dialog.setCancelable(false);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
//         dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        return dialog;
    }
}

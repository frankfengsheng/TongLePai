package com.cheng.tonglepai.tool;

import android.app.Activity;

public class MyToast {

    public static void showDialog(Activity activity, String message_str) {
        MyToastDialog progressDialog = MyToastDialog.createDialog(activity);
        progressDialog.setMessage(message_str);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static MyChooseToastDialog showChooseDialog(Activity activity, String message_str, String title, android.view.View.OnClickListener listener) {
        MyChooseToastDialog progressDialog = MyChooseToastDialog.createDialog(activity);
        progressDialog.setMessage(message_str);
        progressDialog.setTitleShow(title);
        progressDialog.setOnClickListener(listener);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return progressDialog;
    }

    public static MyReturnDialog showReturnDialog(Activity activity, android.view.View.OnClickListener listener) {
        MyReturnDialog progressDialog = MyReturnDialog.createDialog(activity);
        progressDialog.setOnClickListener(listener);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static SelectUnpassDialog showUnpassDialog(Activity activity, android.view.View.OnClickListener listener) {
        SelectUnpassDialog progressDialog = SelectUnpassDialog.createDialog(activity);
        progressDialog.setOnClickListener(listener);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return progressDialog;
    }
}

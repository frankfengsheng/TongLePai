package com.cheng.tonglepai.tool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cheng.retrofit20.bean.WechatBindingBean;
import com.cheng.tonglepai.R;
import com.cheng.tonglepai.activity.SelectAccountActivity;
import com.cheng.tonglepai.model.BindingModel;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

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

    public static void showDialog(Context context,String message, final OnDialogSureClick click){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setMessage(message);
        View contentView=LayoutInflater.from(context).inflate(R.layout.layout_dialog,null);
        Button btn_sure= (Button) contentView.findViewById(R.id.btn_save);
        Button btn_cancle= (Button) contentView.findViewById(R.id.btn_cancle);
        TextView tv_title= (TextView) contentView.findViewById(R.id.tv_title);
        tv_title.setText(message);
        final Dialog dialog=normalDialog.create();
        dialog.show();
        dialog.getWindow().setContentView(contentView);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.sureClick();
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }
    public static   interface OnDialogSureClick{
        void sureClick();
    }

    public static void showChangDialog(final String content, Context context,String sureValue ,final OnDialogSureClick click){

        final Dialog dialog=new Dialog(context,R.style.dialog_parent);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);//设置dialog没有标题
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_binding_wechat,null);
        TextView tvCancle= (TextView) view.findViewById(R.id.tv_cancle_binding);
        TextView tvBinding= (TextView) view.findViewById(R.id.tv_binding);
        tvBinding.setText(sureValue);
        TextView tvContent= (TextView) view.findViewById(R.id.tv_wechat_binding_content);
        tvContent.setText(content);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              click.sureClick();dialog.cancel();
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        dialog.show();
        window.setAttributes(lp);

    }

}

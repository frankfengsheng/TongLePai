package com.cheng.tonglepai.tool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
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

    public static ProgressDialog creatDialog(Context context){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(null);//设置标题
        dialog.setMessage(null); //设置说明文字
        dialog.setIndeterminate(false); //设置进度条是否为不明确(来回旋转)
        dialog.setCanceledOnTouchOutside(false); //设置点击屏幕不消失
        dialog.setCancelable(true); //设置进度条是否可以按退回键取消
        Window wd= dialog.getWindow(); //获取屏幕管理器
        WindowManager.LayoutParams lp = wd.getAttributes();
        lp.alpha = 0.8f; //设置循环框的透明度
        wd.setAttributes(lp); //设置弹出框的透明度
        wd.setGravity(Gravity.CENTER); //设置水平居中
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

    /**
     * 微信绑定，微信换绑弹窗
     * @param content
     * @param context
     * @param sureValue
     * @param click
     */
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

    /**
     * 提示更新弹框
     * @param content
     * @param context
     * @param click
     */
    public static void showUpDateDialog(final String content, Context context ,final OnDialogSureClick click){

        final Dialog dialog=new Dialog(context,R.style.dialog_parent);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);//设置dialog没有标题
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_update,null);
        TextView tv_content= (TextView) view.findViewById(R.id.tv_update_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_content.setText(content);
        Button btn_sure= (Button) view.findViewById(R.id.btn_update_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.sureClick();
                dialog.cancel();
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

    /**
     * 缴币提醒弹框
     * @param content
     * @param context
     * @param click
     */
    public static void showPayMoneyDialog(final String content, Context context ,final OnDialogSureClick click){

        final Dialog dialog=new Dialog(context,R.style.Dialog_Fullscreen);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);//设置dialog没有标题
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_pay_money,null);
        TextView tv_content= (TextView) view.findViewById(R.id.tv_update_content);
        tv_content.setText(content);
        Button btn_sure= (Button) view.findViewById(R.id.btn_update_sure);
        Button btn_konw= (Button) view.findViewById(R.id.btn_update_know);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.sureClick();
                dialog.cancel();
            }
        });
        btn_konw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        lp.gravity=Gravity.CENTER;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        dialog.show();
        window.setAttributes(lp);
    }
}

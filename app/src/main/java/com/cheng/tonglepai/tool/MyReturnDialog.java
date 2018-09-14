package com.cheng.tonglepai.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cheng.tonglepai.R;


public class MyReturnDialog extends Dialog {

	private static MyReturnDialog myToastDialog = null;
	public MyReturnDialog progressDialog;

	public MyReturnDialog(Context context) {
		super(context);
	}

	public MyReturnDialog(Context context, int theme) {
		super(context, theme);
	}

	public static MyReturnDialog createDialog(Context context) {
		myToastDialog = new MyReturnDialog(context, R.style.CustomProgressDialog);
		myToastDialog.setContentView(R.layout.dialog_return_device);
		myToastDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		TextView btn_cancel = (TextView) myToastDialog.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myToastDialog.dismiss();
			}
		});

		return myToastDialog;
	}

	public MyReturnDialog setMessage(String strMessage) {
		TextView message = (TextView) myToastDialog.findViewById(R.id.message);

		if (message != null) {
			message.setText(strMessage);
		}

		return myToastDialog;
	}


	public MyReturnDialog setOnClickListener(View.OnClickListener listener) {
		TextView btn_ok = (TextView) myToastDialog.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(listener);

		return myToastDialog;
	}
}

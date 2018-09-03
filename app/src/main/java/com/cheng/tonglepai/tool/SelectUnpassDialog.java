package com.cheng.tonglepai.tool;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cheng.tonglepai.R;


public class SelectUnpassDialog extends Dialog {

	private static SelectUnpassDialog myToastDialog = null;
	public SelectUnpassDialog progressDialog;

	public SelectUnpassDialog(Context context) {
		super(context);
	}

	public SelectUnpassDialog(Context context, int theme) {
		super(context, theme);
	}

	public static SelectUnpassDialog createDialog(Context context) {
		myToastDialog = new SelectUnpassDialog(context, R.style.CustomProgressDialog);
		myToastDialog.setContentView(R.layout.dialog_unpass);
		myToastDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		TextView btn_cancel = (TextView) myToastDialog.findViewById(R.id.btn_ok);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myToastDialog.dismiss();
			}
		});

		return myToastDialog;
	}

	public SelectUnpassDialog setMessage(String strMessage) {
		TextView message = (TextView) myToastDialog.findViewById(R.id.message);

		if (message != null) {
			message.setText(strMessage);
		}

		return myToastDialog;
	}

	public SelectUnpassDialog setTitleShow(String strTitle) {
		TextView title = (TextView) myToastDialog.findViewById(R.id.title);

		if (!TextUtils.isEmpty(strTitle)) {
			title.setText(strTitle);
			title.setVisibility(View.VISIBLE);
		}else{
			title.setVisibility(View.GONE);
		}

		return myToastDialog;
	}

	public SelectUnpassDialog setOnClickListener(View.OnClickListener listener) {
		TextView btn_ok = (TextView) myToastDialog.findViewById(R.id.btn_cancel);
		btn_ok.setOnClickListener(listener);

		return myToastDialog;
	}
}

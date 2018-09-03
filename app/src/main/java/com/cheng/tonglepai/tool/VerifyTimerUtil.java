package com.cheng.tonglepai.tool;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class VerifyTimerUtil {

	private int mDisableTime = 60;
	private Handler mHandler = new Handler();
	private Timer timer = new Timer();
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					tickWork();
				}
			});
		}
	};
	
	Button tvVerifyCode;
	Context context;
	
	public VerifyTimerUtil(Context context, Button tvVerifyCode){
		this.tvVerifyCode = tvVerifyCode;
		this.context = context;
		
	}
	
	private void tickWork() {
		mDisableTime--;
		String comedownFormmater = "剩余%s秒";
		tvVerifyCode.setText(String.format(comedownFormmater, mDisableTime));
		if (mDisableTime <= 0) {
			timerTask.cancel();
			timer.cancel();
			mDisableTime = 60;
			tvVerifyCode.setEnabled(true);
			tvVerifyCode.setText("重新获取");
		}
	}
	
	public void verifyCodeComeDown() {
		tvVerifyCode.setEnabled(false);
		String comedownFormmater = "剩余%s秒";
		tvVerifyCode.setText(String.format(comedownFormmater, mDisableTime));
		tvVerifyCode.setTextColor(Color.parseColor("#45a7fe"));
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						tickWork();
					}
				});
			}
		};
		timer.schedule(timerTask, 0, 1000);

	}
	
}

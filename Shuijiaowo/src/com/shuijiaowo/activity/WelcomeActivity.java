package com.shuijiaowo.activity;

import model.User;
import LoadBitmap.LoadPIC;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shuijiaowo.R;

public class WelcomeActivity extends Activity {
	
	private User user;
	
	private ImageView welcomeImage;
	
	private Button accessAPP;
	
	private Handler handler;
	
	private boolean isLinkToLoginActivity = false;  //是否完成跳转
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	    StrictMode.setThreadPolicy(policy);  //主线程中使用网络操作
		
	    user = User.getUserInstance(getApplicationContext());
		
		welcomeImage = (ImageView) findViewById(R.id.welconme_image);
		accessAPP = (Button) findViewById(R.id.welcome_btn);
		
		LoadPIC load = new LoadPIC(new Handler(), getApplicationContext());
		load.getWelcomeBitmap(); //后台做完图片工作
		
		if(user.getPictureMap().get("Welcome") != null)
			welcomeImage.setImageBitmap(user.getPictureMap().get("Welcome"));
		accessAPP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinktoActivity();
			}
		});
		
		//延时启动
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(!isLinkToLoginActivity) //如果还没有跳转则跳转
					LinktoActivity(); 
			}
		};
		new Delay().start();
	}
	
	public void LinktoActivity() {
		isLinkToLoginActivity = true; 
		if(user.getToken() == null) {
			Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
			startActivity(intent);
		}else {
			toMainActivity();
		}
		finish();
	}
	
	public void toMainActivity() {
		Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
		startActivity(intent);
	}
	
	class Delay extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(1); //延时完毕
		}
	}
}

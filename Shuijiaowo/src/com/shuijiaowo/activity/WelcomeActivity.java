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
	
	private boolean isLinkToLoginActivity = false;  //�Ƿ������ת
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	    StrictMode.setThreadPolicy(policy);  //���߳���ʹ���������
		
	    user = User.getUserInstance(getApplicationContext());
		
		welcomeImage = (ImageView) findViewById(R.id.welconme_image);
		accessAPP = (Button) findViewById(R.id.welcome_btn);
		
		LoadPIC load = new LoadPIC(new Handler(), getApplicationContext());
		load.getWelcomeBitmap(); //��̨����ͼƬ����
		
		if(user.getPictureMap().get("Welcome") != null)
			welcomeImage.setImageBitmap(user.getPictureMap().get("Welcome"));
		accessAPP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinktoActivity();
			}
		});
		
		//��ʱ����
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(!isLinkToLoginActivity) //�����û����ת����ת
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
			handler.sendEmptyMessage(1); //��ʱ���
		}
	}
}

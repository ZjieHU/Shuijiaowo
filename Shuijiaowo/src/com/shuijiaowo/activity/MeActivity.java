package com.shuijiaowo.activity;

import com.example.shuijiaowo.R;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class MeActivity extends Activity {

	private Button btn_back, btn_video, btn_about, btn_logout;
	private String params = null;
	private String response;
	private TextView tv_account, tv_video;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me);

		// 找到账号信息、录音个数的TextView
		tv_account = (TextView) super.findViewById(R.id.tv_account);
		tv_video = (TextView) super.findViewById(R.id.tv_video);

		// 子线程请求账号、录音个数、关于我们地址
		// 读取请求token
		SharedPreferences mySharedPreferences = getSharedPreferences(
				"shuijiaowo", Activity.MODE_PRIVATE);
		String token = mySharedPreferences.getString("token", "");
		// 开启子线程，请求数据
		// 组合成params
		params = "Token=" + token;
		new Thread() {
			public void run() {
				// 返回是JSON
				response = PostUtil.sendPost(CommonUri.ACCOUNT_URL, params);

			}

		}.start();
		handler.sendEmptyMessage(0x123);

		// 返回主页
		toMain();
		// 录音
		toVideo();
		// 去关于我们
		toAbout();
		// 退出账号
		toLogout();
	}

	// 返回主页
	public void toMain() {
		btn_back = (Button) super.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this, MainActivity.class);
				startActivity(intent);

			}
		});

	}

	// 录音页面
	public void toVideo() {
		btn_video = (Button) super.findViewById(R.id.btn_video);
		btn_video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this, VideoActivity.class);
				startActivity(intent);

			}
		});

	}

	// 去关于我们
	public void toAbout() {
		btn_about = (Button) super.findViewById(R.id.btn_about);
		btn_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this, AboutActivity.class);
				startActivity(intent);

			}
		});

	}

	// 退出账号
	public void toLogout() {
		btn_logout = (Button) super.findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SharedPreferences sharedPreferences = getSharedPreferences(
						"shuijiaowo", Activity.MODE_PRIVATE);
				sharedPreferences.edit().clear().commit();

				Intent intent = new Intent(MeActivity.this, LoginActivity.class);
				startActivity(intent);

				Toast.makeText(MeActivity.this, "退出成功", Toast.LENGTH_SHORT)
						.show();

			}
		});

	}

	// 设置账号
	public void setAccount(String jSONString) {

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {

				setAccount(response);

			}

		}

	};

}

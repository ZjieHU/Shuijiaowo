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

		// �ҵ��˺���Ϣ��¼��������TextView
		tv_account = (TextView) super.findViewById(R.id.tv_account);
		tv_video = (TextView) super.findViewById(R.id.tv_video);

		// ���߳������˺š�¼���������������ǵ�ַ
		// ��ȡ����token
		SharedPreferences mySharedPreferences = getSharedPreferences(
				"shuijiaowo", Activity.MODE_PRIVATE);
		String token = mySharedPreferences.getString("token", "");
		// �������̣߳���������
		// ��ϳ�params
		params = "Token=" + token;
		new Thread() {
			public void run() {
				// ������JSON
				response = PostUtil.sendPost(CommonUri.ACCOUNT_URL, params);

			}

		}.start();
		handler.sendEmptyMessage(0x123);

		// ������ҳ
		toMain();
		// ¼��
		toVideo();
		// ȥ��������
		toAbout();
		// �˳��˺�
		toLogout();
	}

	// ������ҳ
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

	// ¼��ҳ��
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

	// ȥ��������
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

	// �˳��˺�
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

				Toast.makeText(MeActivity.this, "�˳��ɹ�", Toast.LENGTH_SHORT)
						.show();

			}
		});

	}

	// �����˺�
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

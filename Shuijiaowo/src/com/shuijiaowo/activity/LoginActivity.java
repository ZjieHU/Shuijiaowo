package com.shuijiaowo.activity;

import com.example.shuijiaowo.R;
import com.shuijiaowo.check.CheckString;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class LoginActivity extends Activity {

	String phoneNumberString, codeString;
	private EditText phoneNumberEditText, codeEditText;
	private Button btn_code, btn_login;
	private String params = null;
	private String response = "0";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// 找到手机号码输入框
		phoneNumberEditText = (EditText) super
				.findViewById(R.id.et_phone_number);

		// 点击获取验证码
		btn_code = (Button) super.findViewById(R.id.btn_code);
		btn_code.setOnClickListener(new codeOnClickListener());

		// 找到验证码输入框
		codeEditText = (EditText) super.findViewById(R.id.et_code);

		// 点击登录
		btn_login = (Button) super.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new loginOnClickListener());

	}

	// 点击获取验证码的类
	public class codeOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根

			// 获取手机号码
			phoneNumberString = phoneNumberEditText.getText().toString();

			// 组合成params
			params = "PhoneNumber=" + phoneNumberString;

			// 判断手机号码是否为空
			if (phoneNumberString.isEmpty() == true) {

				// 手机号码为空
				Toast.makeText(getApplicationContext(), "请您输入手机号码",
						Toast.LENGTH_SHORT).show();

			} else {

				// 判断是否为手机号码
				if (CheckString.isMobileNumber(phoneNumberString) == false) {

					// 手机号码格式不正确
					Toast.makeText(getApplicationContext(), "请您输入正确的手机号码",
							Toast.LENGTH_SHORT).show();

				} else {

					new Thread() {
						public void run() {

							// 返回String
							response = PostUtil.sendPost(CommonUri.CODE_URL,
									params);

						}

					}.start();
					handler.sendEmptyMessage(0x123);
				}

			}

		}

	}

	// 点击登录的类
	public class loginOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			// 获取手机号码和验证码
			phoneNumberString = phoneNumberEditText.getText().toString();
			codeString = codeEditText.getText().toString();

			// 组合成params
			params = "PhoneNumber=" + phoneNumberString + "&Code=" + codeString;

			// 判断手机号码
			if (phoneNumberString.isEmpty() == true) {

				// 手机号码为空
				Toast.makeText(getApplicationContext(), "请您输入手机号码",
						Toast.LENGTH_SHORT).show();

			} else {

				if (CheckString.isMobileNumber(phoneNumberString) == false) {

					// 手机号码格式不正确
					Toast.makeText(getApplicationContext(), "请您输入正确的手机号码",
							Toast.LENGTH_SHORT).show();

				} else {

					// 判断验证码是否为空
					if (codeString.isEmpty() == true) {

						// 验证码是否为空
						Toast.makeText(getApplicationContext(), "验证码为空",
								Toast.LENGTH_SHORT).show();

					} else {

						// 判断验证码是否正确
						if (CheckString.isCodeOk(codeString) == false) {

							// 验证码格式不正确
							Toast.makeText(getApplicationContext(),
									"请您输入正确的验证码", Toast.LENGTH_SHORT).show();

						} else {

							new Thread() {
								public void run() {

									// 返回String
									response = PostUtil.sendPost(
											CommonUri.LOGIN_URL, params);

								}

							}.start();
							handler.sendEmptyMessage(0x123);

						}

					}

				}

			}

		}

	}

	public void setLogin(String responseString) {

		Toast.makeText(getApplicationContext(), responseString,
				Toast.LENGTH_SHORT).show();

		if (responseString.equals("1")) {

			Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT)
					.show();

		}

		// // 创建并写入mySharedPreferences
		// SharedPreferences mySharedPreferences =
		// getSharedPreferences(
		// "shuijiaowo", Activity.MODE_PRIVATE);
		// SharedPreferences.Editor editor = mySharedPreferences
		// .edit();
		// editor.putString("token", response);
		// editor.commit();
		//
		// // 跳入主页
		// // Intent intent = new Intent(LoginActivity.this,
		// // MainActivity.class);
		// // startActivity(intent);
		//
		// String tokenString =
		// mySharedPreferences.getString("token",
		// "");
		//
		// Toast.makeText(getApplicationContext(), tokenString,
		// Toast.LENGTH_SHORT).show();

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			// 验证码返回处理
			if (msg.what == 0x123) {

				// 调用处理方法
				setLogin(response);

			}

		}

	};

}

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

		// �ҵ��ֻ����������
		phoneNumberEditText = (EditText) super
				.findViewById(R.id.et_phone_number);

		// �����ȡ��֤��
		btn_code = (Button) super.findViewById(R.id.btn_code);
		btn_code.setOnClickListener(new codeOnClickListener());

		// �ҵ���֤�������
		codeEditText = (EditText) super.findViewById(R.id.et_code);

		// �����¼
		btn_login = (Button) super.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new loginOnClickListener());

	}

	// �����ȡ��֤�����
	public class codeOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������

			// ��ȡ�ֻ�����
			phoneNumberString = phoneNumberEditText.getText().toString();

			// ��ϳ�params
			params = "PhoneNumber=" + phoneNumberString;

			// �ж��ֻ������Ƿ�Ϊ��
			if (phoneNumberString.isEmpty() == true) {

				// �ֻ�����Ϊ��
				Toast.makeText(getApplicationContext(), "���������ֻ�����",
						Toast.LENGTH_SHORT).show();

			} else {

				// �ж��Ƿ�Ϊ�ֻ�����
				if (CheckString.isMobileNumber(phoneNumberString) == false) {

					// �ֻ������ʽ����ȷ
					Toast.makeText(getApplicationContext(), "����������ȷ���ֻ�����",
							Toast.LENGTH_SHORT).show();

				} else {

					new Thread() {
						public void run() {

							// ����String
							response = PostUtil.sendPost(CommonUri.CODE_URL,
									params);

						}

					}.start();
					handler.sendEmptyMessage(0x123);
				}

			}

		}

	}

	// �����¼����
	public class loginOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {

			// ��ȡ�ֻ��������֤��
			phoneNumberString = phoneNumberEditText.getText().toString();
			codeString = codeEditText.getText().toString();

			// ��ϳ�params
			params = "PhoneNumber=" + phoneNumberString + "&Code=" + codeString;

			// �ж��ֻ�����
			if (phoneNumberString.isEmpty() == true) {

				// �ֻ�����Ϊ��
				Toast.makeText(getApplicationContext(), "���������ֻ�����",
						Toast.LENGTH_SHORT).show();

			} else {

				if (CheckString.isMobileNumber(phoneNumberString) == false) {

					// �ֻ������ʽ����ȷ
					Toast.makeText(getApplicationContext(), "����������ȷ���ֻ�����",
							Toast.LENGTH_SHORT).show();

				} else {

					// �ж���֤���Ƿ�Ϊ��
					if (codeString.isEmpty() == true) {

						// ��֤���Ƿ�Ϊ��
						Toast.makeText(getApplicationContext(), "��֤��Ϊ��",
								Toast.LENGTH_SHORT).show();

					} else {

						// �ж���֤���Ƿ���ȷ
						if (CheckString.isCodeOk(codeString) == false) {

							// ��֤���ʽ����ȷ
							Toast.makeText(getApplicationContext(),
									"����������ȷ����֤��", Toast.LENGTH_SHORT).show();

						} else {

							new Thread() {
								public void run() {

									// ����String
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

			Toast.makeText(getApplicationContext(), "���ͳɹ�", Toast.LENGTH_SHORT)
					.show();

		}

		// // ������д��mySharedPreferences
		// SharedPreferences mySharedPreferences =
		// getSharedPreferences(
		// "shuijiaowo", Activity.MODE_PRIVATE);
		// SharedPreferences.Editor editor = mySharedPreferences
		// .edit();
		// editor.putString("token", response);
		// editor.commit();
		//
		// // ������ҳ
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

			// ��֤�뷵�ش���
			if (msg.what == 0x123) {

				// ���ô�����
				setLogin(response);

			}

		}

	};

}

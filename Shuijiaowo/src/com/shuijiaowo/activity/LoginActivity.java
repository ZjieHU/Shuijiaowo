package com.shuijiaowo.activity;

import com.example.shuijiaowo.R;
import com.shuijiaowo.check.CheckString;
import com.shuijiaowo.common.AchieveURLText;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
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

	private AchieveURLText achieveURLText = new AchieveURLText(); //��ȡ��ҳ���ݵ���
	
	private String achieveSMSLoginURL = "http://shuijiaowo.mysdk.com.cn:8080/CodeServlet"
			+ "?Phone_Number=13333333333";
	private String achieveLoginURL = "http://shuijiaowo.mysdk.com.cn:8080/LoginServlet"
			+ "?Phone_Number=13333333333&Code=123456";
	
	private String phoneNumberString, codeString;
	private EditText phoneNumberEditText, codeEditText;
	private Button btn_code, btn_login;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		TelephonyManager tm = (TelephonyManager)
				getSystemService(getApplicationContext().TELEPHONY_SERVICE); //����ϵͳ�Ļ�ȡ�ֻ���ϢȨ��
		
		// �ҵ��ֻ����������
		phoneNumberEditText = (EditText) super
				.findViewById(R.id.et_phone_number);
		phoneNumberEditText.setText(tm.getLine1Number());
		phoneNumberEditText.setEnabled(false);

		// �������ǳ��Ƿ���ȷ
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

		@Override
		public void onClick(View v) { 
			// ��ȡ�ֻ�����
/*			phoneNumberString = phoneNumberEditText.getText().toString();

			// �ж��ֻ������Ƿ�Ϊ��
			if (phoneNumberString.isEmpty() == true) {

				// �ֻ�����Ϊ��
				Toast.makeText(getApplicationContext(), "���������ֻ�����",
						Toast.LENGTH_SHORT).show();

			} else {
				// �ж��Ƿ�Ϊ�ֻ�����
				if (CheckString.isMobileNumber(phoneNumberString) == false) {
					// �ֻ������ʽ����ȷ
					Toast.makeText(getApplicationContext(), "�����sim��",
							Toast.LENGTH_SHORT).show();
				} else {
					if(achieveURLText.getURLText(achieveSMSLoginURL).equals("1")){
						Toast.makeText(getApplicationContext(),
								"�û����Ѵ���",
								Toast.LENGTH_LONG).show();
					}else {
						Toast.makeText(getApplicationContext(),
								"��Ȼʧ����",
								Toast.LENGTH_LONG).show();
					}
					
				}

			} */
			Intent i = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(i);
		} 
			

	}

	// �����¼����
	public class loginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// ��ȡ�ֻ��������֤��
			phoneNumberString = phoneNumberEditText.getText().toString();
			codeString = codeEditText.getText().toString();


			// �ж��ֻ�����
			if (phoneNumberString.isEmpty() == true) {  //�ֻ�����ȷ
				// �ֻ�����Ϊ��
				Toast.makeText(getApplicationContext(), "���������ֻ�����",
						Toast.LENGTH_SHORT).show();
			} else { //�ֻ��Ų���ȷ
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
						
						Toast.makeText(getApplicationContext(), "�û������ֻ��Ų�ƥ��", 1).show();
					}
				}
			}
		}
	}
}

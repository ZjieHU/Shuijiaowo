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

	private AchieveURLText achieveURLText = new AchieveURLText(); //获取网页内容的类
	
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
				getSystemService(getApplicationContext().TELEPHONY_SERVICE); //启动系统的获取手机信息权限
		
		// 找到手机号码输入框
		phoneNumberEditText = (EditText) super
				.findViewById(R.id.et_phone_number);
		phoneNumberEditText.setText(tm.getLine1Number());
		phoneNumberEditText.setEnabled(false);

		// 点击检查昵称是否正确
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

		@Override
		public void onClick(View v) { 
			// 获取手机号码
/*			phoneNumberString = phoneNumberEditText.getText().toString();

			// 判断手机号码是否为空
			if (phoneNumberString.isEmpty() == true) {

				// 手机号码为空
				Toast.makeText(getApplicationContext(), "请您输入手机号码",
						Toast.LENGTH_SHORT).show();

			} else {
				// 判断是否为手机号码
				if (CheckString.isMobileNumber(phoneNumberString) == false) {
					// 手机号码格式不正确
					Toast.makeText(getApplicationContext(), "请插入sim卡",
							Toast.LENGTH_SHORT).show();
				} else {
					if(achieveURLText.getURLText(achieveSMSLoginURL).equals("1")){
						Toast.makeText(getApplicationContext(),
								"用户名已存在",
								Toast.LENGTH_LONG).show();
					}else {
						Toast.makeText(getApplicationContext(),
								"居然失败了",
								Toast.LENGTH_LONG).show();
					}
					
				}

			} */
			Intent i = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(i);
		} 
			

	}

	// 点击登录的类
	public class loginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// 获取手机号码和验证码
			phoneNumberString = phoneNumberEditText.getText().toString();
			codeString = codeEditText.getText().toString();


			// 判断手机号码
			if (phoneNumberString.isEmpty() == true) {  //手机号正确
				// 手机号码为空
				Toast.makeText(getApplicationContext(), "请您输入手机号码",
						Toast.LENGTH_SHORT).show();
			} else { //手机号不正确
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
						
						Toast.makeText(getApplicationContext(), "用户名与手机号不匹配", 1).show();
					}
				}
			}
		}
	}
}

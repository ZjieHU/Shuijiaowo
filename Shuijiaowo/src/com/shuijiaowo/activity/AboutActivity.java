package com.shuijiaowo.activity;

import com.example.shuijiaowo.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class AboutActivity extends Activity {

	private Button btn_back;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_about);

		// 返回主页
		toMe();

		// 打开关于我们
		webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl("http://www.qq.com/");
	}

	// 返回主页
	public void toMe() {
		btn_back = (Button) super.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutActivity.this, MeActivity.class);
				startActivity(intent);

			}
		});

	}

}

package com.shuijiaowo.activity;

import java.util.Calendar;

import com.example.shuijiaowo.R;
import com.shuijiaowo.activity.LoginActivity.codeOnClickListener;
import com.shuijiaowo.check.CheckString;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

@SuppressLint("HandlerLeak")
public class ClockAddActivity extends Activity {

	private Button btn_back, btn_fre;
	private TextView view_tittle, tv_fre, btn_save;
	private EditText et_remarks;
	private String tittleString, remarksString;
	private String params = null;
	private TimePicker tp = null;
	private String response;
	String Time = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_add);

		// 返回主页
		toMainActivity();
		// 频次选择
		btn_fre();
		// 顶部标题
		view_tittle = (TextView) findViewById(R.id.tittle);
		// 频次设置
		tv_fre = (TextView) findViewById(R.id.tv_fre);
		// 闹钟备注
		et_remarks = (EditText) super.findViewById(R.id.et_remarks);
		remarksString = et_remarks.getText().toString();

		// 得到跳转到该Activity的Intent对象
		Intent intent = getIntent();
		String typeid = intent.getStringExtra("typeid");

		if (typeid.equals("getup")) {
			tittleString = "起床";
		} else if (typeid.equals("th")) {
			tittleString = "有事";
		} else if (typeid.equals("meet")) {
			tittleString = "见面";
		} else if (typeid.equals("rest")) {
			tittleString = "休息";
		} else if (typeid.equals("study")) {
			tittleString = "学习";
		} else if (typeid.equals("other")) {
			tittleString = "其他事";
		}

		// 时间选择
		view_tittle.setText(tittleString);
		// 时间选择器
		this.tp = (TimePicker) super.findViewById(R.id.tp1);
		// 默认时间格式为12小时制的，这里我们改成24小时的制
		this.tp.setIs24HourView(true);
		// 设置显示当前时间
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		
		Time = hour +":"+minute;
		
		this.tp.setCurrentHour(hour);
		this.tp.setCurrentMinute(minute);
		this.tp.setOnTimeChangedListener(new getTime());

		// 新增按钮
		btn_save = (Button) super.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new clockSaveOnClickListener());

	}

	// 返回主页
	public void toMainActivity() {
		btn_back = (Button) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClockAddActivity.this,
						MainActivity.class);
				startActivity(intent);

			}
		});

	}

	// 频次选择
	public void btn_fre() {
		btn_fre = (Button) super.findViewById(R.id.btn_fre);
		btn_fre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ClockAddActivity.this);
				builder.setTitle("如何叫我呢？");
				// 指定下拉列表的显示数据
				final String[] cities = { "只叫我一次吧", "每天叫我哦", "工作日叫我", "周六日叫我" };
				// 设置一个下拉的列表选择项
				builder.setItems(cities, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						tv_fre.setText(cities[which]);
						Toast.makeText(ClockAddActivity.this,
								"选择为：" + cities[which], Toast.LENGTH_SHORT)
								.show();
					}
				});
				builder.show();
			}
		});

	}

	// 点击闹钟保存
	public class clockSaveOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO 自动生成的方法存根
			String fre = "1";
			
			//获得设定的时间
			
			// 组合成params
			params = "Type=" + tittleString + "&Time=" + Time + "&Fre=" + fre
					+ "&Remarks=" + remarksString;
			Toast.makeText(getApplicationContext(), params, 1).show();
			new Thread() {
				public void run() {
					// 返回的是0或1
					response = PostUtil.sendPost(CommonUri.CLOCK_SAVE_URL,
							params);

					if (response.equals("0")) {
						// 增加失败
						handler.sendEmptyMessage(0x123);

					} else if (response.equals("1")) {

						// 增加成功
						handler.sendEmptyMessage(0x1231);

					}

				}

			}.start();
			// handler.sendEmptyMessage(tag);

		}

	}

	public class getTime implements OnTimeChangedListener {

		@Override
		public void onTimeChanged(TimePicker view, int hour, int minute) {
			Time = hour + ":" + minute;
		}
		
	}
	
	// 设定闹钟
	public void setClock() {

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {
				Toast.makeText(getApplicationContext(), "保存失败，请重新保存",
						Toast.LENGTH_SHORT).show();

			} else if (msg.what == 0x1231) {

				// 设定闹钟
				setClock();
				// 保存成功，返回主页
				Intent intent = new Intent(ClockAddActivity.this,
						MainActivity.class);
				startActivity(intent);

				Toast.makeText(getApplicationContext(), "保存成功",
						Toast.LENGTH_SHORT).show();

			}

		}

	};

}

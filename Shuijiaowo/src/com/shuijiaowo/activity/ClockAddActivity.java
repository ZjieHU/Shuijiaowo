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

		// ������ҳ
		toMainActivity();
		// Ƶ��ѡ��
		btn_fre();
		// ��������
		view_tittle = (TextView) findViewById(R.id.tittle);
		// Ƶ������
		tv_fre = (TextView) findViewById(R.id.tv_fre);
		// ���ӱ�ע
		et_remarks = (EditText) super.findViewById(R.id.et_remarks);
		remarksString = et_remarks.getText().toString();

		// �õ���ת����Activity��Intent����
		Intent intent = getIntent();
		String typeid = intent.getStringExtra("typeid");

		if (typeid.equals("getup")) {
			tittleString = "��";
		} else if (typeid.equals("th")) {
			tittleString = "����";
		} else if (typeid.equals("meet")) {
			tittleString = "����";
		} else if (typeid.equals("rest")) {
			tittleString = "��Ϣ";
		} else if (typeid.equals("study")) {
			tittleString = "ѧϰ";
		} else if (typeid.equals("other")) {
			tittleString = "������";
		}

		// ʱ��ѡ��
		view_tittle.setText(tittleString);
		// ʱ��ѡ����
		this.tp = (TimePicker) super.findViewById(R.id.tp1);
		// Ĭ��ʱ���ʽΪ12Сʱ�Ƶģ��������Ǹĳ�24Сʱ����
		this.tp.setIs24HourView(true);
		// ������ʾ��ǰʱ��
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);// Сʱ
		int minute = cal.get(Calendar.MINUTE);// ��
		
		Time = hour +":"+minute;
		
		this.tp.setCurrentHour(hour);
		this.tp.setCurrentMinute(minute);
		this.tp.setOnTimeChangedListener(new getTime());

		// ������ť
		btn_save = (Button) super.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new clockSaveOnClickListener());

	}

	// ������ҳ
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

	// Ƶ��ѡ��
	public void btn_fre() {
		btn_fre = (Button) super.findViewById(R.id.btn_fre);
		btn_fre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ClockAddActivity.this);
				builder.setTitle("��ν����أ�");
				// ָ�������б����ʾ����
				final String[] cities = { "ֻ����һ�ΰ�", "ÿ�����Ŷ", "�����ս���", "�����ս���" };
				// ����һ���������б�ѡ����
				builder.setItems(cities, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						tv_fre.setText(cities[which]);
						Toast.makeText(ClockAddActivity.this,
								"ѡ��Ϊ��" + cities[which], Toast.LENGTH_SHORT)
								.show();
					}
				});
				builder.show();
			}
		});

	}

	// ������ӱ���
	public class clockSaveOnClickListener implements OnClickListener {

		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			String fre = "1";
			
			//����趨��ʱ��
			
			// ��ϳ�params
			params = "Type=" + tittleString + "&Time=" + Time + "&Fre=" + fre
					+ "&Remarks=" + remarksString;
			Toast.makeText(getApplicationContext(), params, 1).show();
			new Thread() {
				public void run() {
					// ���ص���0��1
					response = PostUtil.sendPost(CommonUri.CLOCK_SAVE_URL,
							params);

					if (response.equals("0")) {
						// ����ʧ��
						handler.sendEmptyMessage(0x123);

					} else if (response.equals("1")) {

						// ���ӳɹ�
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
	
	// �趨����
	public void setClock() {

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {
				Toast.makeText(getApplicationContext(), "����ʧ�ܣ������±���",
						Toast.LENGTH_SHORT).show();

			} else if (msg.what == 0x1231) {

				// �趨����
				setClock();
				// ����ɹ���������ҳ
				Intent intent = new Intent(ClockAddActivity.this,
						MainActivity.class);
				startActivity(intent);

				Toast.makeText(getApplicationContext(), "����ɹ�",
						Toast.LENGTH_SHORT).show();

			}

		}

	};

}

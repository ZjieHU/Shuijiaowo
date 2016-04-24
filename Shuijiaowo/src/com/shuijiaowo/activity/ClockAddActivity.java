package com.shuijiaowo.activity;

import java.util.Calendar;

import service.SqlServiceImpl;
import model.Clock;
import model.User;

import com.example.shuijiaowo.R;
import com.shuijiaowo.activity.LoginActivity.codeOnClickListener;
import com.shuijiaowo.check.CheckString;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

import encodeTool.ConverDate;
import GestureOperation.GestureWidgetOnClick;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	
	private View fre_choice;
	private boolean isfreChoiceVisible = false;
	
	private User user;
	private SqlServiceImpl sqlServiceImpl ;
	private ConverDate convertDate;
	private boolean isNewClock = true;
	
	private Button btn_back;
	private View btn_fre;
	private TextView view_tittle, tv_fre, btn_save;
	private EditText et_remarks;
	private String tittleString;
	private TimePicker tp = null;
	
	private Intent intent;
	
	StringBuilder Time = new StringBuilder();
	String currentTime = new String();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_add);
		
		convertDate = new ConverDate();
		sqlServiceImpl = new SqlServiceImpl(getApplicationContext());
		user = User.getUserInstance(getApplicationContext());
		
		fre_choice = findViewById(R.id.fre_choice);
		
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
		et_remarks.getText().toString();

		// �õ���ת����Activity��Intent����
		intent = getIntent();
		final String typeid = intent.getStringExtra("typeid");

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
		}else {
			isNewClock = false;
			tittleString = user.getClockList().get(Integer.parseInt(typeid)).getType();
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
		int year = cal.get(Calendar.YEAR); //��
		int month = cal.get(Calendar.MONTH) + 1;//��
		int day = cal.get(Calendar.DAY_OF_MONTH); //��
		
		saveClock(hour,minute,year,month,day);
		
		this.tp.setCurrentHour(hour);
		this.tp.setCurrentMinute(minute);
		this.tp.setOnTimeChangedListener(new getTime());

		// ������ť
		btn_save = (Button) super.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new clockSaveOnClickListener());
		
		//����������������ذ�ť
		ImageView deleteCurrentClock = (ImageView)findViewById(R.id.delete_clock);
		if(isNewClock) {
			deleteCurrentClock.setVisibility(View.GONE);
		}else {
			deleteCurrentClock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { //ɾ����ǰ����
					user.getClockList().remove(Integer.parseInt(typeid));
					LinkToMainActivity();
				}
			});
		}
	}
	
	public void LinkToMainActivity() {
		Intent i = new Intent(ClockAddActivity.this,MainActivity.class);
		startActivity(i);
		MainActivity.getMainActivityInstance().finish();
		finish();
	}
	
	public void saveClock(int hour , int minute , int year , int month , int day) {
		if(year != 0 && month != 0 && day != 0) {
			Time.append(year+"-");
			if(month < 10) {
				Time.append("0"+month+"-");
			}else{
				Time.append(month+"-");
			}
			if(day < 10) {
				Time.append("0"+day+" ");
			}else{
				Time.append(day+" ");
			}
			if(hour < 10) {
				currentTime = "0" + hour + ":" ;
			}else {
				currentTime = hour + ":" ;
			}
			if(minute < 10) {
				currentTime += "0" + minute + ":00" ;
			}else {
				currentTime += minute + ":00" ;
			}
		}else {
			if(hour < 10) {
				currentTime = "0" + hour + ":" ;
			}else {
				currentTime = hour + ":" ;
			}
			if(minute < 10) {
				currentTime += "0" + minute + ":00" ;
			}else {
				currentTime += minute + ":00" ;
			}
		}
	}
	
	// ������ҳ
	public void toMainActivity() {
		btn_back = (Button) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinkToMainActivity();
			}
		});
	}

	// Ƶ��ѡ��
	public void btn_fre() {
		btn_fre = (View) super.findViewById(R.id.btn_fre);
		btn_fre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			/*	AlertDialog.Builder builder = new AlertDialog.Builder(
						ClockAddActivity.this);
				builder.setTitle("��ν����أ�");
				// ָ�������б����ʾ����
				final String[] cities = { "ֻ����һ�ΰ�", "ÿ�����Ŷ", "�����ս���", "�����ս���" };
				// ����һ���������б�ѡ����
				builder.setItems(cities, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv_fre.setText(cities[which]);
					}
				});
				builder.show(); */
				if(isfreChoiceVisible) {
					fre_choice.setVisibility(View.VISIBLE);
					isfreChoiceVisible = false;
				}else {
					fre_choice.setVisibility(View.GONE);
					isfreChoiceVisible = true;
				}
			}
		});
	}

	// ������ӱ���
	public class clockSaveOnClickListener implements OnClickListener {

		public void onClick(View v) {
			String time = Time.toString() + currentTime;
			if(user.getClockList().size() <= 6) {
				Clock clock = new Clock();
				clock.setType(tittleString);
				clock.setTime(convertDate.StrConvertDate(time));
				clock.setFre(tv_fre.getText().toString());
				clock.setRemarks(et_remarks.getText().toString());
				user.getClockList().add(clock);
				if(!isNewClock) {
					user.getClockList().remove(Integer.parseInt(intent.getStringExtra("typeid")));
				}
				sqlServiceImpl.saveUSer(user);
				LinkToMainActivity();
				Toast.makeText(getApplicationContext(), "����ɹ�", 1).show();
			}else {
				Toast.makeText(getApplicationContext(), "�����б��������", 1).show();
			}
		}
	}

	public class getTime implements OnTimeChangedListener {
		@Override
		public void onTimeChanged(TimePicker view, int hour, int minute) {
			saveClock(hour,minute,0,0,0);
		}
	}
}

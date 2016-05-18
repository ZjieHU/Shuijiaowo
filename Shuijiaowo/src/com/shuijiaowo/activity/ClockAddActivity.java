package com.shuijiaowo.activity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import model.Clock;
import model.User;
import service.SqlServiceImpl;
import GestureOperation.ClockOnClickTime;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.shuijiaowo.R;

import encodeTool.ConverDate;

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
	
	private Intent intent;
	
	public static TextView t_1_2, t_2_2, t_1_1, t_2_1, t_1_3, t_2_3;
	 
	static StringBuilder Time ;
	public static String currentTime = new String();
	
	private static ClockAddActivity clockAddActivity;
	
	public static ClockAddActivity getInstance() {
		return clockAddActivity;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_add);
		
		clockAddActivity = this;
		
		Time = new StringBuilder();
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
		final String typeid = intent.getStringExtra("typeid");  //�����б��position

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

		// ������ʾ��ǰʱ��
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); //��
		int month = cal.get(Calendar.MONTH) + 1;//��
		int day = cal.get(Calendar.DAY_OF_MONTH); //��
		
		// ������ť
		btn_save = (Button) super.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new clockSaveOnClickListener());
		
		setOnClickTime(); //���õ�����ֵ�ʱ�������ClockAddҳ�����������
		
		//����������������ذ�ť
		ImageView deleteCurrentClock = (ImageView)findViewById(R.id.delete_clock);
		if(isNewClock) { //������µ����ӣ���ɾ����ť����
			deleteCurrentClock.setVisibility(View.GONE);
		}else {  //��������µİ�ť����ɾ����ť��ʾ
			setCurrentClockDisplay();
			deleteCurrentClock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { //ɾ����ǰ����
					user.getClockList().remove(Integer.parseInt(typeid));
					LinkToMainActivity();
				}
			});
		}
		
		int hour = Integer.parseInt(t_1_2.getText().toString().replace("ʱ", "").trim());
		int minute = Integer.parseInt(t_2_2.getText().toString().replace("��", "").trim());
		
		saveClock(hour,minute,year,month,day); //�Ż�Time+currentTime�ַ���,�ַ�������Ϊ����ҳ��ʾʱ��ͷ������ڸ�ʽ��ת��
	}
	
	//������޸��򣬵�ǰ��ʾ����ʱ��ΪListView�б���ʾʱ��
	public void setCurrentClockDisplay() {
		String[] time = intent.getStringExtra("title").split(":");
		int hour = Integer.parseInt(time[0]);
		int second = Integer.parseInt(time[1]);
		t_1_1.setText((hour-1)+"");
		t_1_2.setText(hour+" ʱ");
		t_1_3.setText((hour+1)+"");
		t_2_1.setText((second - 1)+"");
		t_2_2.setText(second+" ��");
		t_2_3.setText((second+1)+"");
	}
	
	//���õ��ʱ�����ֵĲ���Ч��
	public void setOnClickTime() {  
		t_1_1 = (TextView) findViewById(R.id.time_1_1); //����
		t_1_1.setId(1);
		t_1_2 = (TextView) findViewById(R.id.time_1_2);
		t_1_2.setId(2);
		t_1_3 = (TextView) findViewById(R.id.time_1_3);
		t_1_3.setId(3);
		t_2_1 = (TextView) findViewById(R.id.time_2_1); //����
		t_2_1.setId(4);
		t_2_2 = (TextView) findViewById(R.id.time_2_2); //����
		t_2_2.setId(5);
		t_2_3 = (TextView) findViewById(R.id.time_2_3); //����
		t_2_3.setId(6);
		t_1_1.setOnClickListener(new ClockOnClickTime(t_1_2,t_1_3));
		t_2_1.setOnClickListener(new ClockOnClickTime(t_2_2,t_2_3));
		t_1_3.setOnClickListener(new ClockOnClickTime(t_1_2,t_1_1));
		t_2_3.setOnClickListener(new ClockOnClickTime(t_2_2,t_2_1));
		
	}
	
	public void LinkToMainActivity() {
		MainActivity.getMainActivityInstance().finish();
		finish();
		Intent i = new Intent(ClockAddActivity.this,MainActivity.class);
		startActivity(i);
	}
	
	public void startClock() {
		Intent intent = new Intent(this,ClockStart.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int count = user.getClockList().size() - 1;
		if(count > 0) {
			Clock clock = user.getClockList().get(count);
			Timestamp time = clock.getTime();
			Timestamp t = new Timestamp(time.getTime());
			time.setTime(System.currentTimeMillis());
			time.setMinutes(t.getMinutes());
			time.setHours(t.getHours());
			time.setSeconds(0);
			if(time.getTime() > System.currentTimeMillis()) { //����ʱ����ڵ�ǰʱ��
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, //AlarmManager.POWER_OFF_WAKEUP  ��APIӰ�죬����ʱ��������ػ�Ҳ�ɻ���
					time.getTime(),
					15*1000,
					pi);
			}
		}  
	}
	
	public static void saveClock(int hour , int minute , int year , int month , int day) {
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
//			Log.v("myTag", Time.toString()+currentTime);
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
			String time = Time.toString() + currentTime; //��Timestamp��ʽ����ϵ��ַ���
//			Log.v("myTag", time);
			if(user.getClockList().size() <= 6) {
				Clock clock = new Clock();
				clock.setType(tittleString);
				clock.setTime(convertDate.StrConvertDate(time));
				clock.setFre(tv_fre.getText().toString());
				clock.setRemarks(et_remarks.getText().toString());
				user.getClockList().add(clock);
				startClock(); //ע�������¼�
				if(!isNewClock) {
					user.getClockList().remove(Integer.parseInt(intent.getStringExtra("typeid")));
				}
				sqlServiceImpl.saveUSer(user);
				startClock();
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

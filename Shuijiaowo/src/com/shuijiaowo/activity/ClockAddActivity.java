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
		et_remarks.getText().toString();
 
		// 得到跳转到该Activity的Intent对象
		intent = getIntent();
		final String typeid = intent.getStringExtra("typeid");  //闹钟列表的position

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
		}else {
			isNewClock = false;
			tittleString = user.getClockList().get(Integer.parseInt(typeid)).getType();
		}

		// 时间选择
		view_tittle.setText(tittleString);

		// 设置显示当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR); //年
		int month = cal.get(Calendar.MONTH) + 1;//月
		int day = cal.get(Calendar.DAY_OF_MONTH); //日
		
		// 新增按钮
		btn_save = (Button) super.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(new clockSaveOnClickListener());
		
		setOnClickTime(); //设置点击数字的时间操作，ClockAdd页面的六个数字
		
		//如果不是新增则隐藏按钮
		ImageView deleteCurrentClock = (ImageView)findViewById(R.id.delete_clock);
		if(isNewClock) { //如果是新的闹钟，则删除按钮隐藏
			deleteCurrentClock.setVisibility(View.GONE);
		}else {  //如果不是新的按钮，则删除按钮显示
			setCurrentClockDisplay();
			deleteCurrentClock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { //删除当前闹钟
					user.getClockList().remove(Integer.parseInt(typeid));
					LinkToMainActivity();
				}
			});
		}
		
		int hour = Integer.parseInt(t_1_2.getText().toString().replace("时", "").trim());
		int minute = Integer.parseInt(t_2_2.getText().toString().replace("分", "").trim());
		
		saveClock(hour,minute,year,month,day); //放回Time+currentTime字符串,字符串作用为在首页显示时间和符合日期格式的转换
	}
	
	//如果是修改则，当前显示闹钟时间为ListView列表显示时间
	public void setCurrentClockDisplay() {
		String[] time = intent.getStringExtra("title").split(":");
		int hour = Integer.parseInt(time[0]);
		int second = Integer.parseInt(time[1]);
		t_1_1.setText((hour-1)+"");
		t_1_2.setText(hour+" 时");
		t_1_3.setText((hour+1)+"");
		t_2_1.setText((second - 1)+"");
		t_2_2.setText(second+" 分");
		t_2_3.setText((second+1)+"");
	}
	
	//设置点击时间数字的操作效果
	public void setOnClickTime() {  
		t_1_1 = (TextView) findViewById(R.id.time_1_1); //左上
		t_1_1.setId(1);
		t_1_2 = (TextView) findViewById(R.id.time_1_2);
		t_1_2.setId(2);
		t_1_3 = (TextView) findViewById(R.id.time_1_3);
		t_1_3.setId(3);
		t_2_1 = (TextView) findViewById(R.id.time_2_1); //右上
		t_2_1.setId(4);
		t_2_2 = (TextView) findViewById(R.id.time_2_2); //右中
		t_2_2.setId(5);
		t_2_3 = (TextView) findViewById(R.id.time_2_3); //右下
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
			if(time.getTime() > System.currentTimeMillis()) { //闹钟时间大于当前时间
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, //AlarmManager.POWER_OFF_WAKEUP  受API影响，绝对时间计数，关机也可唤醒
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
	
	// 返回主页
	public void toMainActivity() {
		btn_back = (Button) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinkToMainActivity();
			}
		});
	}

	// 频次选择
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
	
	// 点击闹钟保存
	public class clockSaveOnClickListener implements OnClickListener {

		public void onClick(View v) {
			String time = Time.toString() + currentTime; //与Timestamp格式相符合的字符串
//			Log.v("myTag", time);
			if(user.getClockList().size() <= 6) {
				Clock clock = new Clock();
				clock.setType(tittleString);
				clock.setTime(convertDate.StrConvertDate(time));
				clock.setFre(tv_fre.getText().toString());
				clock.setRemarks(et_remarks.getText().toString());
				user.getClockList().add(clock);
				startClock(); //注册闹钟事件
				if(!isNewClock) {
					user.getClockList().remove(Integer.parseInt(intent.getStringExtra("typeid")));
				}
				sqlServiceImpl.saveUSer(user);
				startClock();
				LinkToMainActivity();
				Toast.makeText(getApplicationContext(), "保存成功", 1).show();
			}else {
				Toast.makeText(getApplicationContext(), "闹钟列表最大六条", 1).show();
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

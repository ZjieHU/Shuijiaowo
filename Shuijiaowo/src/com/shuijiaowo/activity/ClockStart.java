package com.shuijiaowo.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.shuijiaowo.R;

public class ClockStart extends Activity {
	
	private MediaPlayer play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_start);
		cancleClock();
		Button btn = (Button) findViewById(R.id.close_button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				play.stop();
			}
		});
		playMusic();
	}
	
	public void playMusic() {
		play = MediaPlayer.create(this, R.raw.tik);
		play.setLooping(true);
		play.start();
	}
	
	//取消闹钟并且删除当前窗口,包括取消之前的服务
	public void cancleClock() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(ClockAddActivity.getInstance(),ClockStart.class);
		PendingIntent pi = PendingIntent
				.getActivity(ClockAddActivity.getInstance(), 0, intent, 0);
		alarmManager.cancel(pi);
//		finish();
	} 
}

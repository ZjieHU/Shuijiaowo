package com.shuijiaowo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shuijiaowo.R;

public class VoiceAdd extends Activity {
	
	private ImageView audioImage;
	private TextView audioStatus;
	private TextView audioTime;
	
	private int countTime;
	
	private boolean startAudio = false;
	private boolean endAudio = true;
	
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addvoive);
		
		View v = findViewById(R.id.audio_pic);
		audioImage = (ImageView) v.findViewById(R.id.audio);
		audioStatus = (TextView) findViewById(R.id.audio_txt);
		audioTime = (TextView) findViewById(R.id.audio_time);
		
		v.setOnTouchListener(new TouchListener());
	}
	
	
	public class TouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN && !startAudio) {
				countTime = 0;
				startAudio = true;
				audioImage.setImageResource(R.drawable.record_btn);
				audioStatus.setText("正在录音");
				endAudio = false;
				new TimeAudioo().start();
				handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						audioTime.setText(countTime/100 + ":" + countTime % 100 +"");
					};
				};
			}else if(event.getAction() == MotionEvent.ACTION_UP) {
				audioImage.setImageResource(R.drawable.record_over_btn);
				audioStatus.setText("录音结束");
				startAudio = false;
				endAudio = true; //录音已经结束
			}
			return true;
		}
	}
	
	public class TimeAudioo extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(countTime > 1490) {
					return;
				}
				countTime++;
				handler.sendEmptyMessage(0);
			}while(!endAudio); //endAudio一直是false时候运行
		}
	}
}

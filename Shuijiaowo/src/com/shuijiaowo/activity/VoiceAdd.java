package com.shuijiaowo.activity;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
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
	
	private MediaRecorder mediaRecorder ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addvoive);
		
		View v = findViewById(R.id.audio_pic);
		audioImage = (ImageView) v.findViewById(R.id.audio);
		audioStatus = (TextView) findViewById(R.id.audio_txt);
		audioTime = (TextView) findViewById(R.id.audio_time);
		
		initRecordAudio();
		
		v.setOnTouchListener(new TouchListener());
	}
	
	
	public class TouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN && !startAudio) {
				if(mediaRecorder != null) mediaRecorder.reset();
				countTime = 0;
				startAudio = true;
				audioImage.setImageResource(R.drawable.record_btn);
				audioStatus.setText("����¼��");
				endAudio = false;
				new TimeAudioo().start();
				handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						audioTime.setText(countTime/100 + ":" + countTime % 100 +"");
					};
				};
				startRecordAudio();
			}else if(event.getAction() == MotionEvent.ACTION_UP) {
				audioImage.setImageResource(R.drawable.record_over_btn);
				audioStatus.setText("¼������");
				startAudio = false;
				endAudio = true; //¼���Ѿ�����
				closeRecordAudio();
			}
			return true;
		}
	}
	
	//�ر�¼��
	public void closeRecordAudio() {
		mediaRecorder.stop();
		mediaRecorder.release();
	}
	
	//��ʼ��¼��ѡ��
	public void initRecordAudio() {
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  //������Դ����˷�
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //��Ƶ��ʽ
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);  //����
		mediaRecorder.setOutputFile("file:///sdcard/myvido/a.3pg");  //�����ļ�λ��
	}
	
	//��ʼ¼��
	public void startRecordAudio() {
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				if(countTime > 1500) {
					return;
				}
				countTime++;
				handler.sendEmptyMessage(0);
			}while(!endAudio); //endAudioһֱ��falseʱ������
		}
	}
}

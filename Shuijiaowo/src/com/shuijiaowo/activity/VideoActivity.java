package com.shuijiaowo.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.example.shuijiaowo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class VideoActivity extends Activity implements OnClickListener {

	private Button btn_back;

	private Button start;
	private Button stop;
	private ListView listView;
	// 录音文件播放
	private MediaPlayer myPlayer;
	// 录音
	private MediaRecorder myRecorder;
	// 音频文件保存地址
	private String path;
	private String paths = path;
	private File saveFilePath;
	// 所录音的文件
	String[] listFile = null;

	ShowRecorderAdpter showRecord;
	AlertDialog aler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		// 录制声音
		toMe();
		// 返回录音
		toVodeAdd();

	}

	// 返回个人中心
	public void toMe() {
		btn_back = (Button) super.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VideoActivity.this, MeActivity.class);
				startActivity(intent);

			}
		});

	}

	// 录制声音
	public void toVodeAdd() {
		start = (Button) findViewById(R.id.btn_start);
		stop = (Button) findViewById(R.id.stop);
		listView = (ListView) findViewById(R.id.video_list);
		myPlayer = new MediaPlayer();
		myRecorder = new MediaRecorder();
		// 从麦克风源进行录音
		myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		// 设置输出格式
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// 设置编码格式
		myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		showRecord = new ShowRecorderAdpter();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				path = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/XIONGRECORDERS";
				File files = new File(path);
				if (!files.exists()) {
					files.mkdir();
				}
				listFile = files.list();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		if (listFile != null) {
			listView.setAdapter(showRecord);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class ShowRecorderAdpter extends BaseAdapter {

		@Override
		public int getCount() {
			return listFile.length;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;

		}

		@Override
		public View getView(final int postion, View arg1, ViewGroup arg2) {
			View views = LayoutInflater.from(VideoActivity.this).inflate(
					R.layout.video_list, null);
			TextView filename = (TextView) views
					.findViewById(R.id.show_file_name);
			Button plays = (Button) views.findViewById(R.id.bt_list_play);
			Button stop = (Button) views.findViewById(R.id.bt_list_stop);

			filename.setText(listFile[postion]);
			// 播放录音
			plays.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						myPlayer.reset();
						myPlayer.setDataSource(path + "/" + listFile[postion]);
						if (!myPlayer.isPlaying()) {

							myPlayer.prepare();
							myPlayer.start();
						} else {
							myPlayer.pause();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// 停止播放
			stop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (myPlayer.isPlaying()) {
						myPlayer.stop();
					}
				}
			});
			return views;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			final EditText filename = new EditText(this);
			Builder alerBuidler = new Builder(this);
			alerBuidler
					.setTitle("录音名称")
					.setView(filename)
					.setPositiveButton("开始录音",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String text = filename.getText().toString();
									try {
										paths = path + "/" + text + ".amr";
										saveFilePath = new File(paths);
										myRecorder.setOutputFile(saveFilePath
												.getAbsolutePath());
										saveFilePath.createNewFile();
										myRecorder.prepare();
										// 开始录音
										myRecorder.start();
										start.setText("... ... ");

										start.setEnabled(false);
										aler.dismiss();
										// 重新读取 文件
										File files = new File(path);
										listFile = files.list();
										// 刷新ListView
										showRecord.notifyDataSetChanged();
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							});
			aler = alerBuidler.create();
			aler.setCanceledOnTouchOutside(false);
			aler.show();
			break;
		case R.id.stop:
			if (saveFilePath.exists() && saveFilePath != null) {
				myRecorder.stop();
				myRecorder.release();
				// 判断是否保存 如果不保存则删除
				new AlertDialog.Builder(this)
						.setTitle("是否保存该录音")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										saveFilePath.delete();
										// 重新读取 文件
										File files = new File(path);
										listFile = files.list();
										// 刷新ListView
										showRecord.notifyDataSetChanged();
									}
								}).setPositiveButton("确定", null).show();

			}
			start.setText("新增录音");
			start.setEnabled(true);
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// 释放资源
		if (myPlayer.isPlaying()) {
			myPlayer.stop();
			myPlayer.release();
		}
		myPlayer.release();
		myRecorder.release();
		super.onDestroy();
	}

}

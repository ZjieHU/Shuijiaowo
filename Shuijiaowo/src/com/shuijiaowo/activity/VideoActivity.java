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
	// ¼���ļ�����
	private MediaPlayer myPlayer;
	// ¼��
	private MediaRecorder myRecorder;
	// ��Ƶ�ļ������ַ
	private String path;
	private String paths = path;
	private File saveFilePath;
	// ��¼�����ļ�
	String[] listFile = null;

	ShowRecorderAdpter showRecord;
	AlertDialog aler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		// ¼������
		toMe();
		// ����¼��
		toVodeAdd();

	}

	// ���ظ�������
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

	// ¼������
	public void toVodeAdd() {
		start = (Button) findViewById(R.id.btn_start);
		stop = (Button) findViewById(R.id.stop);
		listView = (ListView) findViewById(R.id.video_list);
		myPlayer = new MediaPlayer();
		myRecorder = new MediaRecorder();
		// ����˷�Դ����¼��
		myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		// ���������ʽ
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		// ���ñ����ʽ
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
			// ����¼��
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
			// ֹͣ����
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
					.setTitle("¼������")
					.setView(filename)
					.setPositiveButton("��ʼ¼��",
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
										// ��ʼ¼��
										myRecorder.start();
										start.setText("... ... ");

										start.setEnabled(false);
										aler.dismiss();
										// ���¶�ȡ �ļ�
										File files = new File(path);
										listFile = files.list();
										// ˢ��ListView
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
				// �ж��Ƿ񱣴� �����������ɾ��
				new AlertDialog.Builder(this)
						.setTitle("�Ƿ񱣴��¼��")
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										saveFilePath.delete();
										// ���¶�ȡ �ļ�
										File files = new File(path);
										listFile = files.list();
										// ˢ��ListView
										showRecord.notifyDataSetChanged();
									}
								}).setPositiveButton("ȷ��", null).show();

			}
			start.setText("����¼��");
			start.setEnabled(true);
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// �ͷ���Դ
		if (myPlayer.isPlaying()) {
			myPlayer.stop();
			myPlayer.release();
		}
		myPlayer.release();
		myRecorder.release();
		super.onDestroy();
	}

}

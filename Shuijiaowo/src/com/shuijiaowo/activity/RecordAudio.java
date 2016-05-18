package com.shuijiaowo.activity;

import java.util.ArrayList;

import model.Audio;
import model.User;
import GestureOperation.GestureWidgetOnClick;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuijiaowo.R;

/*
 * 录音名称的列表
 */
public class RecordAudio extends Activity {
	
	public static int ONCLICKID_BACK = 2;
	public static int ONCLICKID_ADDVOICE = 3;
	
	private User user;
	
	private ArrayList<Audio> audioList ;
	
	private ListView listView;
	
	private Button back;
	private Button addVoice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voicelist);
		
		user = User.getUserInstance(getApplicationContext());
		audioList = user.getAudioMeList();
		
		back = (Button) findViewById(R.id.btn_back);
		back.setId(ONCLICKID_BACK);
		back.setOnClickListener(new GestureWidgetOnClick(this));
		
		addVoice = (Button) findViewById(R.id.btn_save);
		addVoice.setId(ONCLICKID_ADDVOICE);
		addVoice.setOnClickListener(new GestureWidgetOnClick(this));
		
		listView = (ListView) findViewById(R.id.VoiceList);
		listView.setAdapter(new Adapter());
	}
	
	private class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			
			return audioList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			Audio audio = audioList.get(position);
			if(convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.voicedetail, null);
				holder = new ViewHolder(); 
				holder.textView_Left = (TextView) convertView.findViewById(R.id.voicelist_left);
				holder.textView_right = (TextView) convertView.findViewById(R.id.voicelist_right);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.textView_Left.setText(audio.getName());
			holder.textView_right.setText(audio.getTime().getSeconds()+"");
			return convertView;
		}
		
	}
	
	public class ViewHolder {
		public TextView textView_Left;
		public TextView textView_right;
	}
}


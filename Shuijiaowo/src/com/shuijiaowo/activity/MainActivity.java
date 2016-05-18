package com.shuijiaowo.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import model.Clock;
import model.User;
import GestureOperation.GestureListView;
import GestureOperation.GestureOnclickListView;
import GestureOperation.GestureWidgetOnClick;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.example.shuijiaowo.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	public static int TPID = 1; //头像ID
	
	private static MainActivity main;
	
	private Button audio;
	
	private boolean visible = false;
	
	private ImageView clockHeader;
	private ImageView pus_sign_pop;
	private View plus_sign_pop_txt; //加号之后的边框
	private View[] menu = new View[6];
	
	
	private User user;

	public static Context context;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main = this;
		context = getApplicationContext();
		user = User.getUserInstance(getApplicationContext());
		
		// 个人中心
		// toMe();
		
		// 叫我分类
		pus_sign_pop = (ImageView) findViewById(R.id.puls_sign_pop);
		plus_sign_pop_txt = findViewById(R.id.plus_sign_pop_txt);
		audio = (Button) findViewById(R.id.audio);
		
		audio.setOnClickListener(new clockClassClickListener());
		
		// 监听事件
		LoadPictureMenu();
		// 闹钟列表
		clockList();
	}

	public void LoadPictureMenu() {
		//右部边框里面分类加载
		menu[0] = findViewById(R.id.menu1);
		menu[1] = findViewById(R.id.menu2);
		menu[2] = findViewById(R.id.menu3);
		menu[3] = findViewById(R.id.menu4);
		menu[4] = findViewById(R.id.menu5);
		menu[5] = findViewById(R.id.menu6);
		for(View v : menu) {
			v.setOnClickListener(new clockClassClickListener());
		}
	}
	
	public static MainActivity getMainActivityInstance() {
		if(main != null) {
			return main;
		}
		return null;
	}
	
	public void popupmenu(View v) {
		//popupMenu.show();
		if(visible) {
			pus_sign_pop.setVisibility(View.INVISIBLE);
			plus_sign_pop_txt.setVisibility(View.INVISIBLE);
			visible = false;
		}else {
			pus_sign_pop.setVisibility(View.VISIBLE);
			plus_sign_pop_txt.setVisibility(View.VISIBLE);
			visible = true;
		}
			
	}

	// 叫我点击类
	class clockClassClickListener implements OnClickListener {

		Intent intent = new Intent(MainActivity.this, ClockAddActivity.class);
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu1 : 
				intent.putExtra("typeid", "getup");
				startActivity(intent);
				break;
			case R.id.menu2 :
				intent.putExtra("typeid", "meet");
				startActivity(intent);
				break;
			case R.id.menu3 :
				intent.putExtra("typeid", "th");
				startActivity(intent);
				break;
			case R.id.menu4 :
				intent.putExtra("typeid", "rest");
				startActivity(intent);
				break;
			case R.id.menu5 :
				intent.putExtra("typeid", "study");
				startActivity(intent);
				break;
			case R.id.menu6 :
				intent.putExtra("typeid", "other");
				startActivity(intent);
				break;
			case R.id.audio : //进入录音页面
				intent = new Intent(MainActivity.this,RecordAudio.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
	
	// 闹钟列表总类
	public void clockList() {
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemLongClickListener(new GestureListView(getApplicationContext()));  //长按操作
		listView.setOnItemClickListener(new GestureOnclickListView(main));  //点击进入详细页面
		MyAdapter adapter = new MyAdapter(context);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == GestureWidgetOnClick.PHOTOREQUEST && resultCode == RESULT_OK) {
			int position = GestureWidgetOnClick.position;
			if(position == -1) return;
			Uri uri = data.getData();
			if(uri != null) {
				try {
					Bitmap bitmap = MediaStore.Images.Media.
							getBitmap(getContentResolver(),uri);
					user.getClockList().get(position).setHeaderPitcure(bitmap);
					clockHeader.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Log.v("myTag", e.toString());
				} catch (IOException e) {
					Log.v("myTag", e.toString());
				}
			}
		}
	}
	
	public void ChangePicture(ImageView imageView , int position) {
		clockHeader = imageView;
		imageView.setId(TPID);
		imageView.setOnClickListener(new GestureWidgetOnClick(this , position));
	}
	
	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return user.getClockList().size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		// final方法
		// 注意原本getView方法中的int position变量是非final的，现在改为final
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			
			Clock clock = user.getClockList().get(position);
			
			ViewHolder holder = null;
			
			if (convertView == null) {

				holder = new ViewHolder();
				// 可以理解为从vlist获取view 之后把view返回给ListView
				
				convertView = this.mInflater.inflate(R.layout.vlist, null);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				holder.viewBtn = (TextView)convertView.findViewById(R.id.view_txt);
				holder.imageView = (ImageView)convertView.findViewById(R.id.sItemIcon);
				ChangePicture(holder.imageView,position);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if(clock != null) {
				if(clock.getTime() != null) {
					Timestamp time = clock.getTime();
					String hours = time.getHours()+"";
					String minute = time.getMinutes()+"";
					holder.title.setText(hours+":"+minute+":00"); //HH:mm
				}
				Bitmap bitmap = clock.getHeaderPitcure();
				if(bitmap != null) {
					holder.imageView.setImageBitmap(bitmap);
				}
				holder.info.setText(clock.getRemarks());
				holder.viewBtn.setText(clock.getFre());
			}
			return convertView;
		}
	}

	
	
	// 提取出来方便点
	public final class ViewHolder {
		public TextView title;
		public TextView info;
		public TextView viewBtn;
		public ImageView imageView;
	}  

}

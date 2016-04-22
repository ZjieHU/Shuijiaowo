package com.shuijiaowo.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
	
	PopupMenu popupMenu;
	Menu menu;
	
	private static MainActivity main;
	
	private ImageView clockHeader;
	
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
		popupMenu = new PopupMenu(this, findViewById(R.id.btn_clock_class));
		menu = popupMenu.getMenu();
		LoadPictureMenu(menu);
		// 监听事件
		popupMenu.setOnMenuItemClickListener(new clockClassClickListener());

		// 闹钟列表
		clockList();
	}

	public void LoadPictureMenu(Menu menu) {
		// 通过XML文件添加菜单项
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.clock_class, menu);
		setIconEnable(menu, true);
		menu.getItem(0).setIcon(R.drawable.akb);
		menu.getItem(1).setIcon(R.drawable.ic_sex_male);
		menu.getItem(2).setIcon(android.R.drawable.ic_menu_send);
		menu.getItem(3).setIcon(android.R.drawable.ic_menu_agenda);
		menu.getItem(4).setIcon(android.R.drawable.ic_menu_gallery);
		menu.getItem(5).setIcon(android.R.drawable.ic_menu_report_image);
	}
	
	public static MainActivity getMainActivityInstance() {
		if(main != null) {
			return main;
		}
		return null;
	}
	
	public void popupmenu(View v) {
		popupMenu.show();
	}

	// 叫我点击类
	class clockClassClickListener implements OnMenuItemClickListener {

		Intent intent = new Intent(MainActivity.this, ClockAddActivity.class);

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.getup:

				intent.putExtra("typeid", "getup");
				startActivity(intent);

				break;
			case R.id.th:

				intent.putExtra("typeid", "th");
				startActivity(intent);

				break;
			case R.id.meet:

				intent.putExtra("typeid", "meet");
				startActivity(intent);
				break;
			case R.id.rest:

				intent.putExtra("typeid", "rest");
				startActivity(intent);
				break;
			case R.id.study:

				intent.putExtra("typeid", "study");
				startActivity(intent);

				break;
			case R.id.other:

				intent.putExtra("typeid", "other");
				startActivity(intent);
				break;
			default:
				break;
			}
			return false;
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

		// ****************************************final方法
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
					String t = clock.getTime().toString();
					holder.title.setText(t.substring(11, t.length()-2)); //HH:mm:ss
				}
				Bitmap bitmap = clock.getHeaderPitcure();
				Toast.makeText(getApplicationContext(), (bitmap == null)+"", 1).show();
				if(bitmap != null) {
					holder.imageView.setImageBitmap(bitmap);
				}
				holder.info.setText(clock.getRemarks());
				holder.viewBtn.setText(clock.getFre());
			}
//			Log.v("myTag", "1"+(convertView == null));
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
	
	//enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效  
    private void setIconEnable(Menu menu, boolean enable)  
    {  
        try   
        {  
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");  
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);  
            m.setAccessible(true);  
              
            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)  
            m.invoke(menu, enable);  
              
        } catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
    }  

}

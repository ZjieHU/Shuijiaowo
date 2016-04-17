package com.shuijiaowo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import com.example.shuijiaowo.R;
import com.shuijiaowo.common.CommonUri;
import com.shuijiaowo.util.PostUtil;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	PopupMenu popupMenu;
	Menu menu;

	private String params = null;
	private String response;

	// 闹钟列表
	private List<Map<String, Object>> mData;
	private int flag;
	public static String title[] = new String[] { "菜名0", "菜名1", "菜名2", "菜名3",
			"菜名4", "菜名5", "菜名6", "菜名7", "菜名8", "菜名9" };
	public static String info[] = new String[] { "￥：28", "￥：28", "￥：28",
			"￥：28", "￥：28", "￥：28", "￥：28", "￥：28", "￥：28", "￥：28" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 读取请求token
		SharedPreferences mySharedPreferences = getSharedPreferences(
				"shuijiaowo", Activity.MODE_PRIVATE);
		String token = mySharedPreferences.getString("token", "");
		// 开启子线程，请求数据
		// 组合成params
		params = "Token=" + token;
		new Thread() {
			public void run() {
				// 返回的是0或1
				response = PostUtil.sendPost(CommonUri.CLOCK_LIST_URL, params);

			}

		}.start();
		handler.sendEmptyMessage(0x123);

		// 个人中心
		// toMe();

		// 叫我分类
		popupMenu = new PopupMenu(this, findViewById(R.id.btn_clock_class));
		menu = popupMenu.getMenu();
		// 通过XML文件添加菜单项
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.clock_class, menu);
		// 监听事件
		popupMenu.setOnMenuItemClickListener(new clockClassClickListener());

		// 闹钟列表

	}

	public void popupmenu(View v) {
		popupMenu.show();
	}

	// // 去个人中心
	// public void toMe() {
	// meButton = (Button) super.findViewById(R.id.me);
	// meButton.setOnClickListener(new onClickListener() {
	//
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Intent intent = new Intent(MainActivity.this, MeActivity.class);
	// startActivity(intent);
	//
	// }
	// });
	//
	// }

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
		mData = getData();
		ListView listView = (ListView) findViewById(R.id.listView);
		MyAdapter adapter = new MyAdapter(this);
		listView.setAdapter(adapter);

	}

	// 获取动态数组数据 可以由其他地方传来(json等)
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title[i]);
			map.put("info", info[i]);
			list.add(map);
		}

		return list;
	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mData.size();
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
			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				// 可以理解为从vlist获取view 之后把view返回给ListView

				convertView = mInflater.inflate(R.layout.vlist, null);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				holder.viewBtn = (Button) convertView
						.findViewById(R.id.view_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));
			holder.viewBtn.setTag(position);
			// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					showInfo(position);
				}
			});

			// holder.viewBtn.setOnClickListener(MyListener(position));

			return convertView;
		}
	}

	// 提取出来方便点
	public final class ViewHolder {
		public TextView title;
		public TextView info;
		public Button viewBtn;
	}

	public void showInfo(int position) {

		ImageView img = new ImageView(MainActivity.this);
		img.setImageResource(R.drawable.b);
		new AlertDialog.Builder(this)
				.setView(img)
				.setTitle("详情" + position)
				.setMessage("菜名：" + title[position] + "   价格:" + info[position])
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {

				// 调用闹钟总类进行刷新
				clockList();

			}

		}

	};

}

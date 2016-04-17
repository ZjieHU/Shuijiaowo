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

	// �����б�
	private List<Map<String, Object>> mData;
	private int flag;
	public static String title[] = new String[] { "����0", "����1", "����2", "����3",
			"����4", "����5", "����6", "����7", "����8", "����9" };
	public static String info[] = new String[] { "����28", "����28", "����28",
			"����28", "����28", "����28", "����28", "����28", "����28", "����28" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ��ȡ����token
		SharedPreferences mySharedPreferences = getSharedPreferences(
				"shuijiaowo", Activity.MODE_PRIVATE);
		String token = mySharedPreferences.getString("token", "");
		// �������̣߳���������
		// ��ϳ�params
		params = "Token=" + token;
		new Thread() {
			public void run() {
				// ���ص���0��1
				response = PostUtil.sendPost(CommonUri.CLOCK_LIST_URL, params);

			}

		}.start();
		handler.sendEmptyMessage(0x123);

		// ��������
		// toMe();

		// ���ҷ���
		popupMenu = new PopupMenu(this, findViewById(R.id.btn_clock_class));
		menu = popupMenu.getMenu();
		// ͨ��XML�ļ���Ӳ˵���
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.clock_class, menu);
		// �����¼�
		popupMenu.setOnMenuItemClickListener(new clockClassClickListener());

		// �����б�

	}

	public void popupmenu(View v) {
		popupMenu.show();
	}

	// // ȥ��������
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

	// ���ҵ����
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

	// �����б�����
	public void clockList() {
		mData = getData();
		ListView listView = (ListView) findViewById(R.id.listView);
		MyAdapter adapter = new MyAdapter(this);
		listView.setAdapter(adapter);

	}

	// ��ȡ��̬�������� �����������ط�����(json��)
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

		// ****************************************final����
		// ע��ԭ��getView�����е�int position�����Ƿ�final�ģ����ڸ�Ϊfinal
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				// �������Ϊ��vlist��ȡview ֮���view���ظ�ListView

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
			// ��Button��ӵ����¼� ���Button֮��ListView��ʧȥ���� ��Ҫ��ֱ�Ӱ�Button�Ľ���ȥ��
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

	// ��ȡ���������
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
				.setTitle("����" + position)
				.setMessage("������" + title[position] + "   �۸�:" + info[position])
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == 0x123) {

				// ���������������ˢ��
				clockList();

			}

		}

	};

}

package GestureOperation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.shuijiaowo.R;
import com.shuijiaowo.activity.ClockAddActivity;

public class GestureOnclickListView implements OnItemClickListener {

	private Activity activity;
	
	public GestureOnclickListView(Activity a) {
		this.activity = a;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(activity,ClockAddActivity.class);
		intent.putExtra("typeid", position+"");
		TextView txtView = (TextView) view.findViewById(R.id.title);
		intent.putExtra("title", txtView.getText().toString());
		activity.startActivity(intent);
	}

}

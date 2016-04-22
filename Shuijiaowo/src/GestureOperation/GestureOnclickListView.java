package GestureOperation;

import com.shuijiaowo.activity.ClockAddActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

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
		activity.startActivity(intent);
	}

}

package GestureOperation;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuijiaowo.activity.ClockAddActivity;
import com.shuijiaowo.activity.MainActivity;
import com.shuijiaowo.activity.RecordAudio;
import com.shuijiaowo.activity.VoiceAdd;

public class GestureWidgetOnClick implements OnClickListener {

	public static int PHOTOREQUEST = 0;
	
	private Activity activity;
	
	public static int position = -1;  //此处表示点击闹钟列表的指针
	
	public GestureWidgetOnClick(Activity activity , int position) {
		this.activity = activity;
		this.position = position;
	}
	
	public GestureWidgetOnClick(Activity activity ) {
		this.activity = activity;
	};
	
	@Override
	public void onClick(View v) {
		if(v.getId() == MainActivity.TPID) { //进入选择相片页面
			Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			activity.startActivityForResult(intent, PHOTOREQUEST);
		}else if(v.getId() == RecordAudio.ONCLICKID_BACK) {  //我的声音点击返回
			MainActivity.getMainActivityInstance().finish();
			Intent intent = new Intent(activity,MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}else if(v.getId() == RecordAudio.ONCLICKID_ADDVOICE) { //我的声音点击增加声音
			Intent intent = new Intent(activity,VoiceAdd.class);
			activity.startActivity(intent);
		}
	}
}

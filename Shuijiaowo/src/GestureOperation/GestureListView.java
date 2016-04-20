package GestureOperation;

import com.shuijiaowo.activity.MainActivity;

import service.SqlServiceImpl;
import model.User;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class GestureListView implements OnItemLongClickListener{

	private Context context;
	
	public GestureListView(Context c) {this.context = c;}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Builder builder = new Builder(MainActivity.getMainActivityInstance());
		final int p = position;
		builder.setTitle("��ȷ��ɾ����");
		builder.setPositiveButton("ȷ��ɾ��" , new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				User user = User.getUserInstance(context);
				user.getClockList().remove(p);
				SqlServiceImpl sqlServiceImpl = new SqlServiceImpl(context);
				sqlServiceImpl.saveUSer(user);
				MainActivity.getMainActivityInstance().onCreate(null);
			}
		});
		builder.setNegativeButton("ȡ��ɾ��", null);
		builder.show();
		return true;
	}

}

package GestureOperation;


import com.shuijiaowo.activity.ClockAddActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/*
 * ���ʱ�䣬ʱ���һ���߼�һ
 */
public class ClockOnClickTime implements OnClickListener {

	private TextView txt_middle, txt_down;
	
	public ClockOnClickTime(TextView txt_middle, TextView txt_down) {
		this.txt_middle = txt_middle;
		this.txt_down = txt_down;
	}
	
	@Override
	public void onClick(View v) {
		TextView txt = (TextView)v;
		if(txt.getId() == 1) { //��ߵ������
			int up = Integer.parseInt(txt.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) + 1;
			int middle = Integer.parseInt(txt_middle.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) + 1;
			int down = Integer.parseInt(txt_down.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) + 1;
			if(down == 24) {
				down = 0;
			}
			if(middle == 24) {
				middle = 0;
			}
			if(up == 24) {
				up = 0;
			}
			txt.setText(up+"");
			txt_middle.setText(middle+" ʱ");
			txt_down.setText(down+"");
		}else if(txt.getId() == 3) { //��ߵ������
			int down = Integer.parseInt(txt.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) - 1;
			int middle = Integer.parseInt(txt_middle.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) - 1;
			int up = Integer.parseInt(txt_down.getText().toString()
					.replace("ʱ", "").replace("��", "").trim()) - 1;
			if(down == -1) {
				down = 23;
			}
			if(middle == -1) {
				middle = 23;
			}
			if(up == -1) {
				up = 23;
			}
			txt.setText(down+"");
			txt_middle.setText(middle+" ʱ");
			txt_down.setText(up+"");
		}else if(txt.getId() == 4) {
			int up = Integer.parseInt(txt.getText().toString()
					.replace("��", "").replace("��", "").trim()) + 1;
			int middle = Integer.parseInt(txt_middle.getText().toString()
					.replace("��", "").replace("��", "").trim()) + 1;
			int down = Integer.parseInt(txt_down.getText().toString()
					.replace("��", "").replace("��", "").trim()) + 1;
			if(down == 60) {
				down = 0;
			}
			if(middle == 60) {
				middle = 0;
			}
			if(up == 60) {
				up = 0;
			}
			txt.setText(up+"");
			txt_middle.setText(middle+" ��");
			txt_down.setText(down+"");
		}else if(txt.getId() == 6) {
			int down = Integer.parseInt(txt.getText().toString()
					.replace("��", "").replace("��", "").trim()) - 1;
			int middle = Integer.parseInt(txt_middle.getText().toString()
					.replace("��", "").replace("��", "").trim()) - 1;
			int up = Integer.parseInt(txt_down.getText().toString()
					.replace("��", "").replace("��", "").trim()) - 1;
			if(down == -1) {
				down = 59;
			}
			if(middle == -1) {
				middle = 59;
			}
			if(up == -1) {
				up = 59;
			}
			txt.setText(down+"");
			txt_middle.setText(middle+" ��");
			txt_down.setText(up+"");
		}
		ClockAddActivity.saveClock(Integer.parseInt(ClockAddActivity.t_1_3.getText().toString()) - 1,
				Integer.parseInt(ClockAddActivity.t_2_3.getText().toString()) - 1, 0, 0, 0);
	}

}

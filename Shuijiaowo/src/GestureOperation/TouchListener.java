package GestureOperation;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchListener implements OnTouchListener {

	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			
		}
		return false;
	}

}

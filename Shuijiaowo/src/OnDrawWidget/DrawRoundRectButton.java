package OnDrawWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

public class DrawRoundRectButton extends Button {

	public DrawRoundRectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		RectF rect = new RectF(2, 2, getWidth() - 2, getHeight() - 2);
		canvas.drawRoundRect(rect, 5, 5, paint);
		super.onDraw(canvas);
	}

}

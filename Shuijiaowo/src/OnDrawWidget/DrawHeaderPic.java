package OnDrawWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class DrawHeaderPic extends ImageView {

	 Paint paint = new Paint();
	public DrawHeaderPic(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return; 
		}
		
		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		Bitmap newBitmap = dealRawBitmap(bitmap);  
		//将newBitmap 转换成圆形  
        Bitmap circleBitmap = toRoundCorner(newBitmap, 20);  

        final Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());  
        paint.reset();  
        //绘制到画布上  
        canvas.drawBitmap(circleBitmap, rect, rect, paint);
	}

	//将图片处理成正方形
	private Bitmap dealRawBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		//获取宽度
		int minWidth = width > height ? height : width;
		int minHeight = height > width ? width : height;
		//计算正方形的范围  
        int leftTopX = (width - minWidth)/2;  
        int leftTopY = (height - minWidth)/2; 
        //裁剪成正方形  
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,leftTopX,leftTopY,minWidth,minWidth,null,false);
        return ScaleBitmap(newBitmap);
	}
	
	private Bitmap ScaleBitmap(Bitmap bitmap) {
		int width = getWidth();
		float scale = (float)width / (float)bitmap.getWidth();
		Matrix matrix = new Matrix();  
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
	}
	
	 private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {  
		  
	        //指定为 ARGB_4444 可以减小图片大小  
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);  
	        Canvas canvas = new Canvas(output);  
	  
	        final int color = 0xff424242;  
	        final Rect rect = new Rect(0, 0,bitmap.getWidth(), bitmap.getHeight());  
	        paint.setAntiAlias(true);  
	        canvas.drawARGB(0, 0, 0, 0);  
	        paint.setColor(color);  
	        int x = bitmap.getWidth();  
	        canvas.drawCircle(x / 2, x / 2, x / 2, paint);  
	        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  
	        canvas.drawBitmap(bitmap, rect, rect, paint);  
	        return output;  
	    }  
	
}

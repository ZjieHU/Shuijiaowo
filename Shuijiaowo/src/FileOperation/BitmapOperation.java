package FileOperation;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class BitmapOperation {
	
	private Context context;
	
	public BitmapOperation(Context c) {
		this.context = c;
	}
	
	public void saveBitmap(Bitmap bitmap, String PictureName) {
		try {
			FileOutputStream fis = context.openFileOutput(PictureName+".png",
					Activity.MODE_PRIVATE);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, os);
			fis.write(os.toByteArray());
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap getBitmap(String pictureName) {
		Bitmap bitmap = null;
		try {
			FileInputStream fis = context.openFileInput(pictureName+".png");
			bitmap = BitmapFactory.decodeStream(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
}

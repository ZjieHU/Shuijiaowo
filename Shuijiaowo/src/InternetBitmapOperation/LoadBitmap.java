package InternetBitmapOperation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class LoadBitmap {
	
	public Bitmap getBitmap(String url) {
		Bitmap bitmap = null ;
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (MalformedURLException e) {
			Log.v("myTag", e.toString());
		} catch (IOException e) {
			Log.v("myTag", e.toString());
		}
		return bitmap;
	}
	
}

package LoadBitmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import model.User;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.shuijiaowo.R;

import service.SqlServiceImpl;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import InternetBitmapOperation.LoadBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

public class LoadPIC {
	
	private String versionURL = "http://shuijiaowo.mysdk.com.cn:8080/WBVersionServlet";
	
	private String InternetPictureURL = "http://shuijiaowo.mysdk.com.cn:8080/WBServlet"; //网络图片的JSON数据
	
	private String PictureAddr = "http://shuijiaowo.mysdk.com.cn:8080/PIC/"; //网络图的详细地址
	
	private TreeMap<String,Bitmap> PictureMap = new TreeMap<String,Bitmap>(); //图片树
	
	private LoadBitmap loadBitmap = new LoadBitmap();
	
	private Context context;
	private User user;
	
	public LoadPIC(Handler handler, Context context) {
		this.context = context;
		user = User.getUserInstance(context);
	}
	
	private String getVersion(){
		String version = null ;
		try {
			URL url = new URL(versionURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(is,"utf-8"));
			version = read.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	/*
	 * Version>oldVersion ? loadInternetBitmap : loadSqliteBitmap
	 * 加载并且保存本地数据库
	 */
	public void getWelcomeBitmap() {
		String version = user.getVersion();
		String NewVersion = getVersion();
		boolean isLoadIntentBitmap = true;  //是否需要加载网络图片
		if(version == null) { //判断是否是第一次打开APP
			version = "0"; //第一次打开需要更新
			isLoadIntentBitmap = false;  //不需要更新
			user.getPictureMap().put("Welcome", BitmapFactory.
					decodeResource(context.getResources(), R.drawable.welcome));
			user.getPictureMap().put("Banner", BitmapFactory.
					decodeResource(context.getResources(), R.drawable.bn));
			SqlServiceImpl sqlServiceImpl = new SqlServiceImpl(context);
			sqlServiceImpl.saveUSer(user);
		}else {
			if(Integer.parseInt(version) == Integer.parseInt(NewVersion)) { //判断是否需要更新
				Log.v("myTag", "不需要更新");
				isLoadIntentBitmap = false;  //不需要更新
			}
		}
		if(isLoadIntentBitmap) {  //加载网络图片,并且保存入数据库
			user.setVersion(version);
			try {
				JSONObject json = new JSONObject(InternetPictureJSON());
				String welPictureURL = json.getString("Welcome1");
				String banPictureURL = json.getString("Banner");
				Bitmap welBitmap = loadBitmap.getBitmap(PictureAddr+welPictureURL);//确定选择的图片
				Bitmap banBitmap = loadBitmap.getBitmap(PictureAddr+banPictureURL);//确定选择的图片
				PictureMap.put("Welcome", welBitmap); 
				PictureMap.put("Banner", banBitmap); 
				user.setPictureMap(PictureMap);
				SqlServiceImpl sqlServiceImpl = new SqlServiceImpl(context);
				sqlServiceImpl.saveUSer(user);
			} catch (JSONException e) {
				Log.v("myTag", e.toString());
			}
		}
	}
	
	private String InternetPictureJSON() {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(InternetPictureURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String str = new String();
			while((str = read.readLine()) != null) {
				sb.append(str);
			}
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}

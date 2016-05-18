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
	
	private String InternetPictureURL = "http://shuijiaowo.mysdk.com.cn:8080/WBServlet"; //����ͼƬ��JSON����
	
	private String PictureAddr = "http://shuijiaowo.mysdk.com.cn:8080/PIC/"; //����ͼ����ϸ��ַ
	
	private TreeMap<String,Bitmap> PictureMap = new TreeMap<String,Bitmap>(); //ͼƬ��
	
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
	 * ���ز��ұ��汾�����ݿ�
	 */
	public void getWelcomeBitmap() {
		String version = user.getVersion();
		String NewVersion = getVersion();
		boolean isLoadIntentBitmap = true;  //�Ƿ���Ҫ��������ͼƬ
		if(version == null) { //�ж��Ƿ��ǵ�һ�δ�APP
			version = "0"; //��һ�δ���Ҫ����
			isLoadIntentBitmap = false;  //����Ҫ����
			user.getPictureMap().put("Welcome", BitmapFactory.
					decodeResource(context.getResources(), R.drawable.welcome));
			user.getPictureMap().put("Banner", BitmapFactory.
					decodeResource(context.getResources(), R.drawable.bn));
			SqlServiceImpl sqlServiceImpl = new SqlServiceImpl(context);
			sqlServiceImpl.saveUSer(user);
		}else {
			if(Integer.parseInt(version) == Integer.parseInt(NewVersion)) { //�ж��Ƿ���Ҫ����
				Log.v("myTag", "����Ҫ����");
				isLoadIntentBitmap = false;  //����Ҫ����
			}
		}
		if(isLoadIntentBitmap) {  //��������ͼƬ,���ұ��������ݿ�
			user.setVersion(version);
			try {
				JSONObject json = new JSONObject(InternetPictureJSON());
				String welPictureURL = json.getString("Welcome1");
				String banPictureURL = json.getString("Banner");
				Bitmap welBitmap = loadBitmap.getBitmap(PictureAddr+welPictureURL);//ȷ��ѡ���ͼƬ
				Bitmap banBitmap = loadBitmap.getBitmap(PictureAddr+banPictureURL);//ȷ��ѡ���ͼƬ
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

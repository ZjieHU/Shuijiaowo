package model;

import java.util.ArrayList;
import java.util.TreeMap;

import service.SqlServiceImpl;
import android.content.Context;
import android.graphics.Bitmap;

public class User {
	
	private static SqlServiceImpl sqlServiceImpl;
	
	private String Token;
	private String Version;
	//欢迎页面的key值为Welcome,主页最上面的版面为Banner
	private TreeMap<String,Bitmap> PictureMap = new TreeMap<String,Bitmap>();
	private ArrayList<Clock> ClockList = new ArrayList<Clock>();
	private ArrayList<Audio> AudioMeList = new ArrayList<Audio>();
	
	private static User user = null;
	
	
	public static User getUserInstance(Context c) {
		if(user == null) {
			sqlServiceImpl = new SqlServiceImpl(c);
			user = sqlServiceImpl.getUser();
		}
		return user;
	}
	
	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}
	public TreeMap<String, Bitmap> getPictureMap() {
		return PictureMap;
	}

	public void setPictureMap(TreeMap<String, Bitmap> pictureMap) {
		PictureMap = pictureMap;
	}
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public ArrayList<Clock> getClockList() {
		return ClockList;
	}
	public void setClockList(ArrayList<Clock> clockList) {
		ClockList = clockList;
	}
	public ArrayList<Audio> getAudioMeList() {
		return AudioMeList;
	}
	public void setAudioMeList(ArrayList<Audio> audioMeList) {
		AudioMeList = audioMeList;
	}
}

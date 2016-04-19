package model;

import java.util.ArrayList;

import android.content.Context;
import service.SqlServiceImpl;

public class User {
	
	private static SqlServiceImpl sqlServiceImpl;
	
	private String Token;
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

package model;

import java.util.ArrayList;

public class User {
	
	private String Token;
	private ArrayList<Clock> ClockList = new ArrayList<Clock>();
	private ArrayList<Audio> AudioMeList = new ArrayList<Audio>();
	
	private static User user = null;
	
	public static User getUserInstance() {
		if(user == null) {
			user = new User();
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

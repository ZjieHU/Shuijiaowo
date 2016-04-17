package model;

import java.util.ArrayList;

public class User {
	
	private String Token;
	private ArrayList<String> ClockList = new ArrayList<String>();
	private ArrayList<String> AudioMeList = new ArrayList<String>();
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public ArrayList<String> getClockList() {
		return ClockList;
	}
	public void setClockList(ArrayList<String> clockList) {
		ClockList = clockList;
	}
	public ArrayList<String> getAudioMeList() {
		return AudioMeList;
	}
	public void setAudioMeList(ArrayList<String> audioMeList) {
		AudioMeList = audioMeList;
	}
	
	
}

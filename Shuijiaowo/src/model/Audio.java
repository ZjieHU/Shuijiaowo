package model;

import java.sql.Timestamp;

public class Audio {
	
	private String User_KEY;
	private String AudioID;
	private String Name;
	private String URL;
	private Timestamp Time;
	
	public String getUser_KEY() {
		return User_KEY;
	}
	public void setUser_KEY(String user_KEY) {
		User_KEY = user_KEY;
	}
	public String getAudioID() {
		return AudioID;
	}
	public void setAudioID(String audioID) {
		AudioID = audioID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Timestamp getTime() {
		return Time;
	}
	public void setTime(Timestamp time) {
		Time = time;
	}
	
}

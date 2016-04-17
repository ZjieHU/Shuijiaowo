package model;

import java.sql.Timestamp;

public class platform_user {
	
	private String User_KEY;
	private String Phone_Number;
	private int Audio_Count;
	private String Token;
	private String Code_KEY;
	private Timestamp Time;
	
	public String getUser_KEY() {
		return User_KEY;
	}
	public void setUser_KEY(String user_KEY) {
		User_KEY = user_KEY;
	}
	public String getPhone_Number() {
		return Phone_Number;
	}
	public void setPhone_Number(String phone_Number) {
		Phone_Number = phone_Number;
	}
	public int getAudio_Count() {
		return Audio_Count;
	}
	public void setAudio_Count(int audio_Count) {
		Audio_Count = audio_Count;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public String getCode_KEY() {
		return Code_KEY;
	}
	public void setCode_KEY(String code_KEY) {
		Code_KEY = code_KEY;
	}
	public Timestamp getTime() {
		return Time;
	}
	public void setTime(Timestamp time) {
		Time = time;
	}
	
}

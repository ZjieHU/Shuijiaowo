package model;

import java.sql.Timestamp;

public class Clock {
	
	private String User_Key;
	private String ClockID;
	private String Type;
	private Timestamp Time_Start;
	private String Remarks;
	private String Remind_Audio;
	private String Fre;
	private Timestamp Time;
	
	public String getUser_Key() {
		return User_Key;
	}
	public void setUser_Key(String user_Key) {
		User_Key = user_Key;
	}
	public String getClockID() {
		return ClockID;
	}
	public void setClockID(String clockID) {
		ClockID = clockID;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Timestamp getTime_Start() {
		return Time_Start;
	}
	public void setTime_Start(Timestamp time_Start) {
		Time_Start = time_Start;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getRemind_Audio() {
		return Remind_Audio;
	}
	public void setRemind_Audio(String remind_Audio) {
		Remind_Audio = remind_Audio;
	}
	public String getFre() {
		return Fre;
	}
	public void setFre(String fre) {
		Fre = fre;
	}
	public Timestamp getTime() {
		return Time;
	}
	public void setTime(Timestamp time) {
		Time = time;
	}
}
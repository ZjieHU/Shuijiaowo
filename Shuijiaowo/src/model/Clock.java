package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Clock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String User_Key;
	private String ClockID;
	private String Type;  //设置定时的种类
	private Timestamp Time_Start;
	private String Remarks;  //输入叫我干啥事
	private String Remind_Audio;
	private String Fre;
	private Timestamp Time;  //到达时间
	private Audio audio;
	
	public Audio getAudio() {
		return audio;
	}
	public void setAudio(Audio audio) {
		this.audio = audio;
	}
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

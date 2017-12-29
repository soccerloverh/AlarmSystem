package com.hck.model;

public class Alarm{
	protected int info_id;
	protected String alarm_name;
	protected int alarm_type_id;
	protected int alarm_level;
	protected String alarm_note;
	
	
	public  String getAlarm_name() {
		return alarm_name;
	}
	public  void setAlarm_name(String alarm_name) {
		this.alarm_name = alarm_name;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public  int getAlarm_type_id() {
		return alarm_type_id;
	}
	public void setAlarm_type_id(int alarm_type) {
		this.alarm_type_id = alarm_type;
	}
	public  int getAlarm_level() {
		return alarm_level;
	}
	public  void setAlarm_level(int alarm_level) {
		this.alarm_level = alarm_level;
	}
	public  String getAlarm_note() {
		return alarm_note;
	}
	public  void setAlarm_note(String alarm_note) {
		this.alarm_note = alarm_note;
	}
	
}

package com.hck.model;


import com.hck.model.Alarm;



public class EmailAlarm extends Alarm{
	private int email_id;
	private String email_location;
	private String email_blockwords;
	public String getEmail_location() {
		return email_location;
	}
	
	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public void setEmail_location(String email_location) {
		this.email_location = email_location;
	}
	public String getEmail_blockwords() {
		return email_blockwords;
	}
	public void setEmail_blockwords(String email_blockwords) {
		this.email_blockwords = email_blockwords;
	}

	@Override
	public String toString() {
		return "EmailAlarm [email_id=" + email_id + ", email_location=" + email_location + ", email_blockwords="
				+ email_blockwords + ", info_id=" + info_id + ", alarm_name=" + alarm_name + ", alarm_type_id="
				+ alarm_type_id + ", alarm_level=" + alarm_level + ", alarm_note=" + alarm_note + "]";
	}	
}

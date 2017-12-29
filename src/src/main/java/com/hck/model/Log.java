package com.hck.model;


import java.util.Date;

public class Log {
	private int log_id;
	private String log_userName;
	private String log_ip;
	private String log_operation;
	private Long log_time;
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getLog_userName() {
		return log_userName;
	}
	public void setLog_userName(String log_userName) {
		this.log_userName = log_userName;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}
	public String getLog_operation() {
		return log_operation;
	}
	public void setLog_operation(String log_operation) {
		this.log_operation = log_operation;
	}
	public Long getLog_time() {
		return log_time;
	}
	public void setLog_time(Long log_time) {
		this.log_time = log_time;
	}
	
	
}

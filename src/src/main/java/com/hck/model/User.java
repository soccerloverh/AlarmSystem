package com.hck.model;

public class User{
	private int user_id;
	private String user_name;
	private String user_password;
	private int user_level;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public int getUser_level() {
		return user_level;
	}

	public void setUser_level(int user_level) {
		this.user_level = user_level;
	}

	public String toString() {
		return "User:[ id="+user_id+", name="+user_name+", password="+user_password+", level="+user_level;
	}
}

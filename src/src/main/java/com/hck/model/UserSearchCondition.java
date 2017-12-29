package com.hck.model;

public class UserSearchCondition {
	private String user_name;
	private int user_level;
	private SqlLimit limit;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = "%"+user_name+"%";
	}
	public int getUser_level() {
		return user_level;
	}
	public void setUser_level(int user_level) {
		this.user_level = user_level;
	}
	public SqlLimit getLimit() {
		return limit;
	}
	public void setLimit(SqlLimit limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "UserSearchCondition [user_name=" + user_name + ", user_level=" + user_level + ", limit=" + limit + "]";
	}
}

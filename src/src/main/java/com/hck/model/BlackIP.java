package com.hck.model;

import com.hck.model.Alarm;

public class BlackIP extends Alarm{
	private String blackIP_ip;
	private int blackIP_id;
	
	public int getBlackIP_id() {
		return blackIP_id;
	}

	public void setBlackIP_id(int blackIP_id) {
		this.blackIP_id = blackIP_id;
	}

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public String getBlackIP_ip() {
		return blackIP_ip;
	}

	public void setBlackIP_ip(String blackIP_ip) {
		this.blackIP_ip = blackIP_ip;
	}
	
}

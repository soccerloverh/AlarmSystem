package com.hck.model;

import com.hck.model.Alarm;

public class BlackDNS extends Alarm {
	private String blackDNS_DNS;
	private int info_id;
	private int BlackDNS_id;
	
	public int getBlackDNS_id() {
		return BlackDNS_id;
	}

	public void setBlackDNS_id(int blackDNS_id) {
		BlackDNS_id = blackDNS_id;
	}

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public String getBlackDNS_DNS() {
		return blackDNS_DNS;
	}

	public void setBlackDNS_DNS(String blackDNS_DNS) {
		this.blackDNS_DNS = blackDNS_DNS;
	}
	
}

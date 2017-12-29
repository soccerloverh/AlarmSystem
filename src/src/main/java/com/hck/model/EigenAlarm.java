package com.hck.model;

import com.hck.model.Alarm;

public class EigenAlarm extends Alarm{
	private String eigen_sourceIP;
	private String eigen_destinationIP;
	private String eigen_protocol;
	private int info_id;
	private int eigen_id;
	
	public int getEigen_id() {
		return eigen_id;
	}
	public void setEigen_id(int eigen_id) {
		this.eigen_id = eigen_id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public String getEigen_sourceIP() {
		return eigen_sourceIP;
	}
	public void setEigen_sourceIP(String eigen_sourceIP) {
		this.eigen_sourceIP = eigen_sourceIP;
	}
	public String getEigen_destinationIP() {
		return eigen_destinationIP;
	}
	public void setEigen_destinationIP(String eigen_destinationIP) {
		this.eigen_destinationIP = eigen_destinationIP;
	}
	public String getEigen_protocol() {
		return eigen_protocol;
	}
	public void setEigen_protocol(String eigen_protocol) {
		this.eigen_protocol = eigen_protocol;
	}
	@Override
	public String toString() {
		return "EigenAlarm [eigen_sourceIP=" + eigen_sourceIP + ", eigen_destinationIP=" + eigen_destinationIP
				+ ", eigen_protocol=" + eigen_protocol + ", info_id=" + info_id + ", eigen_id=" + eigen_id + "]";
	}
	
}

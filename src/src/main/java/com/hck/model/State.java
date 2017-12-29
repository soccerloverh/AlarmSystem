package com.hck.model;

public class State {
	public static final String OK = "ok";
	public static final String ERRO = "erro";
	private String status;
	private String href;
	private String info;
	
	public State(String status, String href, String info) {
		super();
		this.status = status;
		this.href = href;
		this.info = info;
	}
	
	public State() {
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "State [status=" + status + ", href=" + href + ", info=" + info + "]";
	}
	
}

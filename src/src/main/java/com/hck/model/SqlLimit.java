package com.hck.model;

public class SqlLimit {
	private int offset;
	private int rowcount;
	public SqlLimit() {}
	
	public SqlLimit(int offset, int rowcount) {
		super();
		this.offset = offset;
		this.rowcount = rowcount;
	}

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getRowcount() {
		return rowcount;
	}
	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	@Override
	public String toString() {
		return "SqlLimit [offset=" + offset + ", rowcount=" + rowcount + "]";
	}
	
}

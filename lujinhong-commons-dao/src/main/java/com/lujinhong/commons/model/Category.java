package com.lujinhong.commons.model;


public class Category {

	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSequnce() {
		return sequnce;
	}
	public void setSequnce(int sequnce) {
		this.sequnce = sequnce;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	private int cid;
	private String title;
	private int sequnce = 0;
	private int deleted = 0;
}

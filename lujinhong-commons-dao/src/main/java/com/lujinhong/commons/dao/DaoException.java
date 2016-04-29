package com.lujinhong.commons.dao;

public class DaoException extends Exception{

	private static final long serialVersionUID = -8688934267604854370L;
	private String message;
	public DaoException(){}
	public DaoException(String message){
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString(){
		return message;
	}

}

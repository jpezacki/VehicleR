package com.vr.exception;

public class NoSuchColorException extends Exception{
	
	private String val;
	
	private NoSuchColorException(){}
	
	public NoSuchColorException(String val){
		this.val = val;
	}
	
	public String getMessage(){
		return "No such color: "+val;
	}

}

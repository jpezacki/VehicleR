package com.vr.exception;

public class WrongParameterException extends Exception{
	
	String val;
	Integer index;
	
	private WrongParameterException(){}
	
	public WrongParameterException(String val, Integer index){
		this.val = val;
		this.index = index;
	}
	
	public String getMessage(){
		return "Wrong parameter at position " + index + " : " + val;
	}

}

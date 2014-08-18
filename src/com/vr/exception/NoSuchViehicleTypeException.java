package com.vr.exception;

public class NoSuchViehicleTypeException extends Exception{

	String val;
	
	private NoSuchViehicleTypeException(){}
	
	public NoSuchViehicleTypeException(String val){
		this.val = val;
	}
	
	public String getMessage(){
		return "There is no such viehicle nor comand: " + val;
	}
	
}

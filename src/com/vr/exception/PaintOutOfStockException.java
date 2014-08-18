package com.vr.exception;

public class PaintOutOfStockException extends Exception{
	
	public String getMessage(){

		return "No more paint of this color";
	
	}

}

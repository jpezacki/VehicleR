package com.vr;

import java.util.HashMap;
import java.util.Map;

import com.vr.core.enums.Color;

public class PaintFactory {
	
	private static Map<Color,Boolean> paint = new HashMap<Color,Boolean>();
	
	private static PaintFactory instance;
	public static synchronized PaintFactory getInstance(){
		
		if(instance == null){
	
			instance = new PaintFactory();
			instance.init();	
		
		}
		
		return instance;
	}
	
	public void init(){
		
		for(Color c : Color.values()) 
			paint.put(c, true);
	
	}
	
	public void returnPaint(Color c){
		paint.put(c, true);
	}
	
	
	public Boolean isPaintInStock(Color c){
		
		synchronized(this){
			
			if(paint.get(c)){
				paint.put(c, false);
				return true;
			}
				
		}
		
		return false;
	}

}

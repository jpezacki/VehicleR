package com.vr.core;

import com.vr.annotation.VehicleMeta;
import com.vr.core.enums.Color;
import com.vr.exception.PaintOutOfStockException;


@VehicleMeta(viehicleName="ferrari", minNumberOfArguments=1, maxNumberOfArguments=1)
public class Ferrari extends Car{
	
	public Ferrari() throws PaintOutOfStockException{
		setColor(Color.red);
		setNumberOfWheels(4);
		setNumberOfSteeringWheels(1);
	}
	
	public String toString(){
	
		return "Ferrari | red | 4 wheels | 1 steering wheel";
		
	}
	
	public int compareTo(Vehicle arg0) {
		if(arg0 instanceof Ferrari) return 0;
		return 1;
	}

}

package com.vr.core;

import com.vr.annotation.CommandParameter;
import com.vr.core.enums.Color;
import com.vr.exception.PaintOutOfStockException;

public abstract class Vehicle implements Comparable<Vehicle>{
	
	public abstract String toString();
	
	@CommandParameter(index=1, inputType="select", label = "Color")
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) throws PaintOutOfStockException {
		
		this.color = color;
	
	}

	
}

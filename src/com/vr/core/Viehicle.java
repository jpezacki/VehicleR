package com.vr.core;

import com.vr.core.enums.Color;
import com.vr.exception.PaintOutOfStockException;

public abstract class Viehicle implements Comparable<Viehicle>{
	
	public abstract String toString();
	
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) throws PaintOutOfStockException {
		
		this.color = color;
	
	}

	
}

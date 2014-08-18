package com.vr.core;

import com.vr.annotation.CommandParameter;
import com.vr.annotation.ViehicleMeta;
import com.vr.core.enums.BicycleType;

@ViehicleMeta(viehicleName="bicycle", minNumberOfArguments=3 ,maxNumberOfArguments=3)
public class Bicycle extends Viehicle{

	@CommandParameter(index=2)
	private BicycleType type;
	
	public String toString(){
		
		return "Bicycle | " + getColor().name() + " | " + type.name();
	
	}

	public BicycleType getType() {
		return type;
	}

	public void setType(BicycleType type) {
		this.type = type;
	}

	public Boolean equals(Bicycle arg0) {
		if(getColor().equals(arg0.getColor()) &&
				getType().equals(arg0.getType())) 
			return true;
		return false;
	}

	@Override
	public int compareTo(Viehicle arg0) {
		if(arg0 instanceof Bicycle &&
				((Bicycle)arg0).getType().equals(this.getType()) &&
					this.getColor().equals(arg0.getColor()))
						return 0;
		return 1;
	}
	
}

package com.vr.core;

import com.vr.annotation.CommandParameter;
import com.vr.annotation.ViehicleMeta;
import com.vr.core.enums.Sex;


@ViehicleMeta(viehicleName="motorcycle", minNumberOfArguments=3 ,maxNumberOfArguments=3)
public class Motorcycle extends Viehicle{
	
	@CommandParameter(index=2)
	private Sex sex;
	
	public String toString(){
		
		return "Motorcycle | " + this.getColor().name() + " | " + sex.name();
	
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public int compareTo(Viehicle arg0) {
		if(arg0 instanceof Motorcycle &&
				((Motorcycle)arg0).getSex().equals(this.getSex()) &&
						this.getColor().equals(arg0.getColor()))
							return 0;
		return 1;
	}

}

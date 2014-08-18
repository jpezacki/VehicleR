package com.vr.core;


import com.vr.annotation.CommandParameter;
import com.vr.annotation.ViehicleMeta;

@ViehicleMeta(viehicleName="car", minNumberOfArguments=4,maxNumberOfArguments=4)
public class Car extends Viehicle{

	@CommandParameter(index=2)
	private Integer numberOfWheels;
	
	@CommandParameter(index=3)
	private Integer numberOfSteeringWheels;

	public String toString(){
	
		return "car | " + getColor().name() + " | " + numberOfWheels + " wheels | " + numberOfSteeringWheels + " steering wheels";
		
	}
	
	public Integer getNumberOfSteeringWheels() {
		return numberOfSteeringWheels;
	}

	public void setNumberOfSteeringWheels(Integer numberOfSteeringWheels) {
		this.numberOfSteeringWheels = numberOfSteeringWheels;
	}

	public Integer getNumberOfWheels() {
		return numberOfWheels;
	}

	public void setNumberOfWheels(Integer numberOfWheels) {
		this.numberOfWheels = numberOfWheels;
	}

	public int compareTo(Viehicle arg0) {
		if(arg0 instanceof Car &&
				((Car)arg0).getNumberOfSteeringWheels().equals(this.getNumberOfSteeringWheels()) &&
					((Car)arg0).getNumberOfWheels() == this.getNumberOfWheels() &&
						this.getColor().equals(arg0.getColor()))
							return 0;
		return 1;
	}
	
	
}

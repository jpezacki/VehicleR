package com.vr.core;

import com.vr.annotation.CommandParameter;
import com.vr.annotation.ViehicleMeta;
import com.vr.core.enums.YesNo;

@ViehicleMeta(viehicleName="boat", minNumberOfArguments=3 , maxNumberOfArguments=4)
public class Boat extends Viehicle{
	
	@CommandParameter(index=2)
	private YesNo floats;
	
	@CommandParameter(index=3)
	private Integer numberOfPeriscopes = 0;
	
	public String toString(){
		
		if(floats.equals(YesNo.yes))
			return "boat | " + getColor().name() + " | floats : " + floats.name();
		else
			return "boat | " + getColor().name() + " | floats : " + floats.name() + " | " + getNumberOfPeriscopes() + " periscopes";
			
	}

	public YesNo getFloats() {
		return floats;
	}

	public void setFloats(YesNo floats) {
		this.floats = floats;
	}
	
	public Integer getNumberOfPeriscopes() {
		return numberOfPeriscopes;
	}

	public void setNumberOfPeriscopes(Integer numberOfPerscopes) {
		this.numberOfPeriscopes = numberOfPerscopes;
	}

	@Override
	public int compareTo(Viehicle arg0) {
		if(arg0 instanceof Boat &&
				((Boat)arg0).getFloats().equals(this.getFloats()) &&
					((Boat)arg0).getNumberOfPeriscopes() == this.getNumberOfPeriscopes() &&
						this.getColor().equals(arg0.getColor()))
							return 0;
		return 1;
	}


}

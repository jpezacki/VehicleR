package com.vr.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ViehicleMeta{

	String viehicleName();
	
	int minNumberOfArguments();
	
	int maxNumberOfArguments();
	
}

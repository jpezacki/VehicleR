package com.vr.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.vr.core.enums.YesNo;


@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameter {
	
	int index();
	
	//YesNo[] requiredYesNoValue();

}

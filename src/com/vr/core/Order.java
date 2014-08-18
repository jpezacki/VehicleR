package com.vr.core;

import java.util.ArrayList;
import java.util.List;

import com.vr.core.enums.Command;
import com.vr.core.enums.OrderStatus;

public class Order{
	
	private static Long topId = 0l;
	
	private Long id;

	private Command command;
	
	private OrderStatus status;
	
	private Viehicle viehicle;
	
	private List<String> errors = new ArrayList<String>();
	
	public Order(){
		id = ++topId;
	}
	
	public String toString(){
		return viehicle.toString();
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Viehicle getViehicle() {
		return viehicle;
	}

	public void setViehicle(Viehicle viehicle) {
		this.viehicle = viehicle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}


}

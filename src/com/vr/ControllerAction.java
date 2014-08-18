package com.vr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.vr.core.Order;
import com.vr.core.enums.Command;
import com.vr.core.enums.OrderStatus;
import com.vr.exception.NoSuchColorException;
import com.vr.exception.PaintOutOfStockException;
import com.vr.exception.WrongNumberOfParameters;
import com.vr.exception.WrongParameterException;

public class ControllerAction extends ActionSupport{
	
	private static Map<Long,Order> orders = new HashMap<Long,Order>();

	private Order o = new Order();

	private String command;
	
	private Long orderId;
	
	public String index(){
		return SUCCESS;
	}
	
	public String resetPaint(){
		PaintFactory.getInstance().init();
		return SUCCESS;
	}
	
	public String process(){
		OrderFactory of = new OrderFactory();
		try {
			o = of.processCommand(command);
			
			if(o.getCommand()!=null){
				
				for(Order o1 : getOrders())
					if(o1.getViehicle().compareTo(o.getViehicle()) == 0)
						orderId = o1.getId();
				
				System.out.println(">>>>>>"+orderId);
				
				if(o.getCommand().equals(Command.cancel))
					return cancelOrder();
				
				if(o.getCommand().equals(Command.delete))
					return deleteOrder();
				
			}else
			
				if(o.getErrors().size() == 0) orders.put(o.getId(), o);

		} catch (WrongNumberOfParameters | PaintOutOfStockException | NoSuchColorException | SecurityException | WrongParameterException e) {
			o.getErrors().add(e.getMessage());
		} 
	
		return SUCCESS;
	}
	
	public String cancelOrder(){
		if(orderId!=null){
			PaintFactory.getInstance().returnPaint(orders.get(orderId).getViehicle().getColor());
			orders.get(orderId).setStatus(OrderStatus.cancelled);
		}
		return SUCCESS;
	}
	
	public String deleteOrder(){
		if(orderId!=null){
			PaintFactory.getInstance().returnPaint(orders.get(orderId).getViehicle().getColor());
			orders.remove(orderId);
		}
		return SUCCESS;
	}
	

	public static List<Order> getOrders() {
		return new ArrayList<Order>(orders.values());
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Order getO() {
		return o;
	}

	public void setO(Order o) {
		this.o = o;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}

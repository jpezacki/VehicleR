package com.vr;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reflections.Reflections;

import com.opensymphony.xwork2.ActionSupport;
import com.vr.annotation.CommandParameter;
import com.vr.annotation.VehicleMeta;
import com.vr.core.Order;
import com.vr.core.Vehicle;
import com.vr.core.enums.Command;
import com.vr.core.enums.OrderStatus;
import com.vr.exception.NoSuchColorException;
import com.vr.exception.NoSuchViehicleTypeException;
import com.vr.exception.PaintOutOfStockException;
import com.vr.exception.WrongNumberOfParameters;
import com.vr.exception.WrongParameterException;

public class ControllerAction extends ActionSupport{
	
	private static Map<Long,Order> orders = new HashMap<Long,Order>();

	private Order o = new Order();

	private String command;
	
	private String json;
	private String vehicle;
	
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
	
	public String getVehiclesAjax(){
		Reflections reflections = new Reflections("com.vr.core");    
		Set<Class<? extends Vehicle>> vs = reflections.getSubTypesOf(Vehicle.class);
		
		JSONArray vi = new JSONArray();
		
		for(Class c : vs){
			VehicleMeta meta = (VehicleMeta) c.getAnnotation(VehicleMeta.class);
			if(meta == null) continue;
			vi.put(meta.viehicleName());
		}
		
		json = new JSONObject().put("Vehicles", vi).toString();
		
		return SUCCESS;
	}
	
	public String getVehicleOptionsAjax() throws NoSuchViehicleTypeException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		OrderFactory of = new OrderFactory();
		Vehicle v = of.createViehicle(vehicle);
		
		JSONArray options = new JSONArray();
		
		List<Field> fs = new ArrayList<Field>(); 
		fs.addAll(Arrays.asList(v.getClass().getDeclaredFields()));
		
		fs.addAll(Arrays.asList(v.getClass().getSuperclass().getDeclaredFields()));
		
		for(Field f : fs){
			
			if(f.getAnnotation(CommandParameter.class) == null) continue;
			
			JSONObject option = new JSONObject();
			
			option.put("index", f.getAnnotation(CommandParameter.class).index());
			option.put("label", f.getAnnotation(CommandParameter.class).label());
			option.put("type", f.getAnnotation(CommandParameter.class).inputType());
			
			if(f.getType().isEnum()){
			
				JSONArray vals = new JSONArray();
				for(Object val : (Object[]) f.getType().getMethod("values").invoke(f))
					vals.put(val.toString());
				option.put("vals",vals);
			}
			
			options.put(option);
		}
			
		json = new JSONObject().put("options", options).toString();
		
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

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}


}

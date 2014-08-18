package com.vr;

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.Reflections;

import com.vr.PaintFactory;
import com.vr.annotation.CommandParameter;
import com.vr.annotation.ViehicleMeta;
import com.vr.core.Boat;
import com.vr.core.Ferrari;
import com.vr.core.Order;
import com.vr.core.Viehicle;
import com.vr.core.enums.Color;
import com.vr.core.enums.Command;
import com.vr.core.enums.OrderStatus;
import com.vr.core.enums.YesNo;
import com.vr.exception.NoSuchColorException;
import com.vr.exception.NoSuchViehicleTypeException;
import com.vr.exception.PaintOutOfStockException;
import com.vr.exception.WrongNumberOfParameters;
import com.vr.exception.WrongParameterException;

public class OrderFactory {
	
	public Order processCommand(String str) throws WrongNumberOfParameters, SecurityException, PaintOutOfStockException, NoSuchColorException, WrongParameterException{

		Order or = new Order();
		
		String args[] = str.trim().split("\\s+");
		for(int i = 0 ; i < args.length ; i++) args[i] = args[i].trim();
		
		if(args.length == 0) throw new WrongNumberOfParameters(); 
		
		Command com = null;
		
		try {
			
			com = Command.valueOf(args[0]);
			
			String args1[] = new String[args.length -1];
			for(int i = 1 ; i < args.length ; i++)
				args1[i-1] = args[i];
			or = createOrder(args1);
			or.setCommand(com);

		} catch (Exception e) {
			or = createOrder(args);
			if(or.getViehicle()!= null && !PaintFactory.getInstance().isPaintInStock(or.getViehicle().getColor()))
				throw new PaintOutOfStockException();

		}
		
		
		return or;

	}
	
	public Order createOrder(String[] args) throws WrongNumberOfParameters, PaintOutOfStockException, NoSuchColorException, SecurityException, WrongParameterException, WrongParameterException{
		
		Order or = new Order();
		or.setStatus(OrderStatus.inprogress);
		
		
		try {
			
			or.setViehicle( createViehicle(args[0]));
		
		} catch (NoSuchViehicleTypeException e) {
		
			or.getErrors().add(e.getMessage());
			return or;
		
		}
		
		if(args.length < or.getViehicle().getClass().getAnnotation(ViehicleMeta.class).minNumberOfArguments() ||
				args.length > or.getViehicle().getClass().getAnnotation(ViehicleMeta.class).maxNumberOfArguments())
		
			throw new WrongNumberOfParameters();
			
		if(or.getViehicle() instanceof Ferrari)
			
			or.getViehicle().setColor(Color.red);
			
		else
			
			try {
			
				or.getViehicle().setColor(Color.valueOf(args[1]));
			
			} catch (IllegalArgumentException e) {

				throw new NoSuchColorException(args[1]);
			
			}
				
		
		for(Field f : or.getViehicle().getClass().getDeclaredFields()){
						
			if(f.getAnnotation(CommandParameter.class) == null) continue;
			
			int i = f.getAnnotation(CommandParameter.class).index();
			
			
			if(i >= args.length && i + 1 <= or.getViehicle().getClass().getAnnotation(ViehicleMeta.class).maxNumberOfArguments()) continue;
			
			try {

				f.setAccessible(true);
				
				if(f.getType().isEnum()){
					
					Object val = f.getType().getMethod("valueOf", String.class).invoke(f , args[i]);
					if(val == null)
						throw new WrongParameterException(args[i],i);
					
					f.set(or.getViehicle(), val);
				
				}else
				
					f.set(or.getViehicle(), Integer.parseInt(args[i]));
				
				
				f.setAccessible(false);
				
				
			} catch (Throwable e) {
				
				e.printStackTrace();
				throw new WrongParameterException(args[i],i); 
			}
			
		}
		
		
		if(or.getViehicle() != null && or.getViehicle() instanceof Boat && ((Boat)or.getViehicle()).getFloats().equals(YesNo.yes)) 
			((Boat)or.getViehicle()).setNumberOfPeriscopes(0);
		
		
		return or;
		
	}
	
	public Viehicle createViehicle(String name) throws NoSuchViehicleTypeException{
		Reflections reflections = new Reflections("com.vr.core");    
		Set<Class<? extends Viehicle>> vs = reflections.getSubTypesOf(Viehicle.class);
		
		for(Class c : vs){
			
			ViehicleMeta meta = (ViehicleMeta) c.getAnnotation(ViehicleMeta.class);

			if(meta == null) continue;
			
			if(meta.viehicleName().equals(name))
				
				try {
					
					return (Viehicle) c.newInstance();
				
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		
		throw new NoSuchViehicleTypeException(name);
		
	}

}

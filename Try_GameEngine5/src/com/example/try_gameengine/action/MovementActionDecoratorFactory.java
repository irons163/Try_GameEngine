package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

public class MovementActionDecoratorFactory {
	
	public static List<Class<? extends MovementDecorator>> createDHMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(HalfDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createDHDMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(HalfDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createDDMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(DoubleDecorator.class);
//		decoratorClassList.add(HalfDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createDDDDMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createCopyMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(CopyMoveDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createCCMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(CopyMoveDecorator.class);
		decoratorClassList.add(CopyMoveDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createCDMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(CopyMoveDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createDCMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(CopyMoveDecorator.class);
		return decoratorClassList;
	}
	
	public static List<Class<? extends MovementDecorator>> createCDCMovementDecorator(){
		List<Class<? extends MovementDecorator>> decoratorClassList = new ArrayList<Class<? extends MovementDecorator>>();
		decoratorClassList.add(CopyMoveDecorator.class);
		decoratorClassList.add(DoubleDecorator.class);
		decoratorClassList.add(CopyMoveDecorator.class);
		return decoratorClassList;
	}
	
}

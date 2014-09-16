package com.example.try_gameengine.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SpecialMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		MovementAction action = new MovementActionSet();
		action.addMovementAction(new MovementActionItem(1000, 200, 10, 0));
		action.addMovementAction(new MovementActionItem(1000, 200, -10, 0));
		action.addMovementAction(new MovementActionItem(1000, 200, -10, 0));
		action.addMovementAction(new MovementActionItem(1000, 200, -10, 0));
		action.addMovementAction(new MovementActionItem(1000, 200, -10, 0));
		action.addMovementAction(new MovementActionItem(1000, 200, -10, 0));
		return action;
	}

	@Override
	public MovementAction createMovementAction(List<MovementActionInfo> infos) {
		// TODO Auto-generated method stub
		MovementAction action = new MovementActionSet();
		for(MovementActionInfo info : infos){
//			action.addMovementAction(new MovementActionItem(info.getTotal(), info.getDelay(), info.getDx(), info.getDy()));
			action.addMovementAction(new MovementActionItem(info));	
		}
//		action.initTimer();
		return action;
	}
	
	@Override
	public MovementAction createMovementAction(List<MovementActionInfo> infos, List<Class<? extends MovementDecorator>> decoratorClassList) {
		// TODO Auto-generated method stub
		MovementAction action = new MovementActionSet();
		for(Class<? extends MovementDecorator> decoratorClass : decoratorClassList){
			try {
				action = decoratorClass.getConstructor(MovementAction.class).newInstance(action);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for(MovementActionInfo info : infos){
//			action.addMovementAction(new MovementActionItem(info.getTotal(), info.getDelay(), info.getDx(), info.getDy()));
			action.addMovementAction(new MovementActionItem(info));	
		}
//		action.initTimer();
		return action;
	}

}

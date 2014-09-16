package com.example.try_gameengine.action.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionFrameItem;
import com.example.try_gameengine.action.MovementActionFrameItem.FrameTrigger;
import com.example.try_gameengine.action.listener.IActionListener;

public class MovementTimeFlowControl {
	List<MovementAction> movementActions = new ArrayList<MovementAction>();
	MovementAction action;
	MovementActionFrameItem.FrameTrigger frameTrigger = new MovementActionFrameItem.FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			
		}
	};
	
	IActionListener actionListener = new IActionListener() {
		
		@Override
		public void beforeChangeFrame(int nextFrameId) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterChangeFrame(int periousFrameId) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void actionFinish() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionStart() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionCycleFinish() {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void addToFlowQueue(MovementAction action){
//		enemy.getAction().settrigger;
//		enemy.;
		movementActions.add(action);
	}
	
	public void flow(){
		frameTrigger.trigger();
//		enemy.trigger;
//		for(MovementAction action : movementActions){
//			action.start();
//			if(action.isFinish)
//				
//		}
//		flowTrigger();
		action.start();
	}
	
	int myFrame;
	
	public interface MovementActionFinish{
		public void finish();
	}
	
	MovementActionFinish movementActionFinish = new MovementActionFinish() {
		
		@Override
		public void finish() {
			// TODO Auto-generated method stub
			nextAction();
		}
	};
	
	private void nextAction(){
		if(myFrame<movementActions.size()){
			myFrame++;
		
			movementActions.get(myFrame).start();
		}
	}
	
	public void multiMove(){
		frameTrigger.trigger();
//		enemy.trigger;
		for(MovementAction action : movementActions){
			action.start();
		}
	}
	
	public void allFinishObjectRestart(){
		for(MovementAction action : movementActions){
			if(action.getAction().isFinish())
				action.start();
		}
		
	}
	
	public void allFinishAndNotFinishObjectRestart(){
		for(MovementAction action : movementActions){
			action.start();
		}
	}
	
}

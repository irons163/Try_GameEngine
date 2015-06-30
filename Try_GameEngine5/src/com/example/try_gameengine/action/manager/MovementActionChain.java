package com.example.try_gameengine.action.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.listener.IActionListener;

public class MovementActionChain {
	String id;
	List<MovementAction> movementActions = new ArrayList<MovementAction>();
	OnChainListener onChainListener;
	
	public MovementActionChain(String id, MovementAction movementAction) {
		// TODO Auto-generated constructor stub
		this.id = id;
		movementActions.add(movementAction);
	}
	
	public void setOnChainListener(OnChainListener onChainListener){
		this.onChainListener = onChainListener;
	}
	
	public void start(){
		for(int i = 0; i<movementActions.size(); i++){
			MovementAction action = movementActions.get(i);
			action.start();
			action.setActionListener(new IActionListener() {
				
				@Override
				public void beforeChangeFrame(int nextFrameId) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterChangeFrame(int periousFrameId) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void actionStart() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void actionFinish() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void actionCycleFinish() {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	interface OnChainListener{
		public void onFirstStart();
		public void onLastStart();
		public void onStart(int startIndex);
		public void onFirstFinish();
		public void onLastFinish();
		public void onFinish(int finishIndex);
	}
	
}

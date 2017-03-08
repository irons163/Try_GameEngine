package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;
//import com.rits.cloning.Cloner;

import android.util.Log;

public class PauseInEndingDecorator extends MovementDecorator {
	private MovementAction action;

	public PauseInEndingDecorator(MovementAction action) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		return info;
	}

	@Override
	public void start() {
		action.getAction().start();
	}

	@Override
	public MovementAction getAction() {
		return action.getAction();
	}

	@Override
	public String getDescription() {
		return "Double " + action.getDescription();
	}

	@Override
	public MovementAction initMovementAction() {
		return initTimer();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();

		if (this.getAction().getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		} else {
			this.getAction().initTimer();
			doIn();
		}
		
		setPauseInEnding();
		return this;
	}

	@Override
	public MovementAction addMovementAction(MovementAction action) {
		getAction().addMovementAction(action);
		return this;
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		getAction().setTimerOnTickListener(timerOnTickListener);
	}

	@Override
	public MovementActionInfo getInfo() {
		return coreCalculationMovementActionInfo(action.getInfo());
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		return action.getCurrentActionList();
	}

	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub
		return action.getCurrentInfoList();
	}

	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}

	@Override
	public void doIn() {
		action.doIn();
		int i = 0;
		for (MovementActionInfo info : this.getAction().currentInfoList) {
			Log.e("count", ++i + "");
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);
			coreCalculationMovementActionInfo(this.getAction().getInfo());
		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
	}

	@Override
	public void cancelMove() {
		action.getAction().cancelMove();
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
	
	@Override
	public void setActionListener(IActionListener actionListener) {
		// TODO Auto-generated method stub
		super.setActionListener(actionListener);
	}
	
	private void setPauseInEnding(){
		final IActionListener actionListener = action.getAction().getActionListener();
		if(actionListener !=null){
			IActionListener newActionListener = new IActionListener() {
				
				@Override
				public void beforeChangeFrame(int nextFrameId) {
					// TODO Auto-generated method stub
					actionListener.beforeChangeFrame(nextFrameId);
				}
				
				@Override
				public void afterChangeFrame(int periousFrameId) {
					// TODO Auto-generated method stub
					actionListener.afterChangeFrame(periousFrameId);
				}
				
				@Override
				public void actionStart() {
					// TODO Auto-generated method stub
					actionListener.actionStart();
				}
				
				@Override
				public void actionFinish() {
					// TODO Auto-generated method stub
					actionListener.actionFinish();
				}
				
				@Override
				public void actionCycleFinish() {
					// TODO Auto-generated method stub
					actionListener.actionCycleFinish();
					action.getAction().pause();
				}
			};
			
			action.getAction().setActionListener(newActionListener);
		}else{
			IActionListener newActionListener = new IActionListener() {
				
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
					action.getAction().pause();
				}
			};
			
			action.getAction().setActionListener(newActionListener);
		}
	}
}

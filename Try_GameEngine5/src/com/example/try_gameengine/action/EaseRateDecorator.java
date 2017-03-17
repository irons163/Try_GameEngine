package com.example.try_gameengine.action;

import java.util.List;

//import com.rits.cloning.Cloner;

import android.util.Log;

/**
 * @author irons
 *
 */
public class EaseRateDecorator extends MovementDecorator {
	float rate;
	
	public EaseRateDecorator(MovementAction action, float rate) {
		this.action = action;
		this.rate = rate;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	/**
	 * @param info
	 * @return
	 */
	protected MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		doinin(info);
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
	protected MovementAction initTimer(){ super.initTimer();

		if (this.getAction().getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		} else {
			this.getAction().initTimer();
			doIn();
		}
		
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

	void doinin(final MovementActionInfo info){
		info.getData().setMovementActionItemUpdateTimeDataDelegate(new MovementActionItemTrigger.DataDelegate() {
			@Override
			public void update(float t) {
				// TODO Auto-generated method stub
				
				double percent = Math.pow(((double)t)/info.getData().getShouldActiveTotalValue(), rate);
				super.update((long) (t * percent));
			}
		});
	}

	@Override
	public void cancelMove() {
		action.getAction().cancelMove();
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
}

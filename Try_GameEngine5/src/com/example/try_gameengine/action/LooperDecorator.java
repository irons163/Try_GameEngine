package com.example.try_gameengine.action;

import java.util.List;

//import com.rits.cloning.Cloner;

import android.util.Log;

/**
 * @author irons
 *
 */
public class LooperDecorator extends MovementDecorator {

	public LooperDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	/**
	 * @param action
	 * @return
	 */
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		return action;
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

		this.getAction().isLoop = true;

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

	@Override
	public void cancelMove() {
		action.getAction().cancelMove();
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
}

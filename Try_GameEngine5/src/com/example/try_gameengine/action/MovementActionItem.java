package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * MovementActionItem is a item(leaf) in MovementAction composites.
 * 
 * @author irons
 * 
 */
public abstract class MovementActionItem extends MovementAction {
	MovementActionInfo info;
	IRotationController rotationController;
	IGravityController gravityController;
	boolean isReset = true;
	private boolean isActionFinish = false;
//	public int frameIdx;
	public boolean isStop = false;
	boolean isFirstTime = true;
	boolean triggerEnable = false;
	/**
	 * constructor.
	 * 
	 * @param info
	 */
	public MovementActionItem(MovementActionInfo info) {
		if (info.getDescription() != null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}
	
	/**
	 * constructor.
	 * 
	 * @param info
	 */
	public MovementActionItem(MovementActionInfo info, String description) {
		this.description = description + ",";
		this.info = info;
		movementItemList.add(this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	protected MovementAction initTimer() {
		return this;
	}

	@Override
	public MovementAction getAction() {
		return this;
	}

	@Override
	public List<MovementAction> getActions() {
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		return info;
	}

	@Override
	public void setInfo(MovementActionInfo info) {
		this.info = info;
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		List<MovementAction> actions = new ArrayList<MovementAction>();
		actions.add(this);
		return actions;
	}

	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}

	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}

	@Override
	public void cancelMove() {
		
	}

	@Override
	void pause() {
		
	}

	@Override
	public boolean isFinish() {
		return isActionFinish;
	}

	@Override
	public void accept(IMovementActionVisitor movementActionVisitor) {
		movementActionVisitor.visitLeaf(this);
	}
}

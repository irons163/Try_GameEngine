package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.try_gameengine.action.IGravityController.PathType;
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate;
import com.example.try_gameengine.action.info.MovementActionMoveByGravityInfo;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

/**
 * MovementActionItemAlpha is a movement action that control alpha value.
 * @author irons
 *
 */
public class MovementActionItemMoveByGravity extends MovementActionItemUpdateTime implements Cloneable{ 
	
//	public MovementActionItemMoveByGravity(long millisTotal, long millisDelay, final int dx, final int dy){
//		this(millisTotal, millisDelay, dx, dy, "MovementItem");
//	}
//	
//	public MovementActionItemMoveByGravity(long millisTotal, long millisDelay, final int dx, final int dy, String description){
//		super(millisTotal, millisDelay, dx, dy, description);
//	}
	
	public MovementActionItemMoveByGravity(MovementActionMoveByGravityInfo info){
		super(info);
	}
	
	
	@Override
	public MovementAction getAction(){
		return this;
	}
	
	public List<MovementAction> getActions(){
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void setInfo(MovementActionInfo info) {
		// TODO Auto-generated method stub
		this.info = info;
	}
	
	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		List<MovementAction> actions = new ArrayList<MovementAction>();
		actions.add(this);
		return actions;
	}
	
	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		currentInfoList.add(this.info);
		return infos;
	}
	
	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}
	
	@Override
	public void cancelMove(){
		isStop = true;
		synchronized (MovementActionItemMoveByGravity.this) {
			MovementActionItemMoveByGravity.this.notifyAll();
		}
	}
	
	@Override
	void pause(){	
		data.setShouldPauseValue(data.getShouldActiveIntervalValue());
	}
	
	@Override
	public boolean isFinish(){
		return isStop;
	}
	
	public void setPathType(PathType pathType) {
		// TODO Auto-generated method stub
		((MovementActionMoveByGravityInfo)info).setPathType(pathType);
	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
	
	@Override
	protected MovementActionItemMoveByGravity clone() throws CloneNotSupportedException {
		MovementActionMoveByGravityInfo info = (MovementActionMoveByGravityInfo) getInfo().clone();
		MovementActionItemMoveByGravity movementActionItemMoveByGravity = new MovementActionItemMoveByGravity(info);
		return movementActionItemMoveByGravity;
	}
}

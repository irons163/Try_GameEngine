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
public class MovementActionItemForMilliseconds extends MovementActionItem {
	IRotationController rotationController;
	IGravityController gravityController;
	long millisTotal;
	long millisDelay;
	float dx;
	float dy;
	long resumeTotal;
	long resetTotal;
	
	boolean isReset = true;
	boolean isActionFinish = false;
	public String name;
	public int frameIdx;
	public boolean isStop = false;
	boolean isFirstTime = true;
	/**
	 * constructor.
	 * 
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param millisDelay
	 * 			milliseconds for delay.
	 * @param dx
	 * 			x-dir move for per delay time.
	 * @param dy
	 * 			y-dir move for per delay time.
	 */
	public MovementActionItemForMilliseconds(long millisTotal, long millisDelay, final int dx,
			final int dy) {
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}

	/**
	 * constructor.
	 * 
	 * @param millisTotal
	 * 			milliseconds for whole action running.
	 * @param millisDelay
	 * 			milliseconds for delay.
	 * @param dx
	 * 			x-dir move for per delay time.
	 * @param dy
	 * 			y-dir move for per delay time.
	 * @param description
	 * 			description for this movement action.
	 */
	public MovementActionItemForMilliseconds(long millisTotal, long millisDelay, final int dx,
			final int dy, String description) {
		super(new MovementActionInfo(millisTotal, millisDelay, dx, dy, description));
	}

	/**
	 * constructor.
	 * 
	 * @param info
	 */
	public MovementActionItemForMilliseconds(MovementActionInfo info) {
		super(info);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();
		return this;
	}

	/**
	 * If rotationController is not null do rotation execute.
	 */
	private void doRotation() {
		if (rotationController != null) {
			rotationController.execute(info);
			dx = info.getDx();
			dy = info.getDy();
		}
	}

	/**
	 * If gravityController is not null do gravity execute.
	 */
	private void doGravity() {
		if (gravityController != null) {
			gravityController.execute(info);
			dx = info.getDx();
			dy = info.getDy();
		}
	}

	/**
	 * reset action.
	 */
	private void doReset() {
		if (gravityController != null) {
			gravityController.reset(info);
		}
		if (rotationController != null)
			rotationController.reset(info);

		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		initTimer();
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
}

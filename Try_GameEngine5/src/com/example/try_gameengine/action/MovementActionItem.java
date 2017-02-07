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
public class MovementActionItem extends MovementAction {
	CountDownTimer countDownTimer;
	long millisTotal;
	long millisDelay;
	float dx;
	float dy;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;
	IRotationController rotationController;
	IGravityController gravityController;
	boolean isReset = true;
	private boolean isActionFinish = false;
	
	long[] frameTimes;
	int resumeFrameIndex;

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
	public MovementActionItem(long millisTotal, long millisDelay, final int dx,
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
	public MovementActionItem(long millisTotal, long millisDelay, final int dx,
			final int dy, String description) {
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}

	/**
	 * constructor.
	 * 
	 * @param info
	 */
	public MovementActionItem(MovementActionInfo info) {
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		if (info.getDescription() != null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (isActionFinish) {
			isReset = true;
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (isReset) {
						isReset = false;
						synchronized (MovementActionItem.this) {
							countDownTimer.start();
							try {
								MovementActionItem.this.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								isActionFinish = false;
							}
						}

					}
				}
			});
			thread.start();
		}

		isActionFinish = false;
		if (isFirstTime) {
			resetTotal = millisTotal;
			isFirstTime = false;

			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (isReset) {
						isReset = false;
						synchronized (MovementActionItem.this) {
							countDownTimer.start();
							try {
								MovementActionItem.this.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								isActionFinish = false;
							}
						}

					}
				}
			});
			thread.start();
		}

		if (info.getSprite() != null)
			info.getSprite().setAction(info.getSpriteActionName());

	}

	@Override
	protected MovementAction initTimer() {
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		rotationController = info.getRotationController();
		gravityController = info.getGravityController();

		resumeFrameIndex = 0;

		countDownTimer = new CountDownTimer(millisTotal, millisDelay) {

			@Override
			public void onTick(long millisUntilFinished) {
				Log.e("t", millisUntilFinished + "");
				Log.e("t", millisUntilFinished / 1000 + "");
				// TODO Auto-generated method stub
				float x = dx;
				float y = dy;

				doRotation();
				doGravity();
				Log.e("dx", dx + "");
				Log.e("dy", dy + "");

				resumeTotal = millisUntilFinished;
				timerOnTickListener.onTick(dx, dy);
			}

			@Override
			public void onFinish() {
				if (isLoop) {
					handler.sendEmptyMessage(0);
					Log.e("Timer", "loop");
				} else {
					synchronized (MovementActionItem.this) {
						MovementActionItem.this.notifyAll();
					}
					doReset();
					isActionFinish = true;
					Log.e("Timer", "finish");
				}
			}
		};
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
		countDownTimer.cancel();
	}

	/**
	 * handler is use for old movement action witch use timer.
	 */
	Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			initTimer();
			info.setTotal(resetTotal);
			isReset = true;
			thread.interrupt();
			start();
		};
	};

	@Override
	void pause() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!isActionFinish) {
					countDownTimer.cancel();

					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					millisTotal = resumeTotal;
					info.setTotal(millisTotal);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
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

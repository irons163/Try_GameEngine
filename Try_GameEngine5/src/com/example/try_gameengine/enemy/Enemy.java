package com.example.try_gameengine.enemy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.os.CountDownTimer;

import com.example.try_gameengine.action.ICircleController;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.action.SimultaneouslyMultiCircleMovementActionSet;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.observer.Observer;
import com.example.try_gameengine.observer.Subject;

public abstract class Enemy extends Sprite implements Subject, Observer{
//	private float x, y;
//	protected Bitmap bitmap;
//	protected MovementAction action;
	private List<Observer> observers = new ArrayList<Observer>(); 
	
	public Enemy(float x, float y) {
		super(x, y, true);
		// TODO Auto-generated constructor stub
//		this.x = x;
//		this.y = y;
//		this.bitmap = BitmapUtil.redPoint;
		initBitmap();
	}
	
	public Enemy(float x, float y, MovementAction action) {
		super(x, y, true);
//		this.x = x;
//		this.y = y;
//		this.bitmap = BitmapUtil.redPoint;
		initBitmap();
		setWH();
		this.action = action;
		setMovementActioinTimerOnTickListener();
		if(action instanceof SimultaneouslyMultiCircleMovementActionSet)
		infos = ((SimultaneouslyMultiCircleMovementActionSet)action).getCurrentInfoList();
	}
	
	public abstract void initBitmap();
	
	private void setWH(){
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}
	
	public void startMovementActioin(){
		if(action!=null)
			action.start();
	}
	
	private void setMovementActioinTimerOnTickListener(){
		if(action!=null)
			action.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
				
				@Override
				public void onTick(float dx, float dy) {
					// TODO Auto-generated method stub
					move(dx, dy);
				}
			});
	}
	
//	public void draw(Canvas canvas){
//		canvas.drawBitmap(bitmap, x, y, null);
//	}
	
	public void move(float dx, float dy){
//		x += dx;
//		y += dy;
		
		setX(getCenterX() + dx - w/2);
		setY(getCenterY() + dy - h/2);
		
		notifyObservers();
	}
	
	public void moveLeftAndRight(float dx){
//		x += dx;
	}
	
	public void moveUpAndDown(float dy){
//		y += dy;
	}
	
	public void moveRandom(){
		
	}
	
	public void moveUP(){
		CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
//				enemyManager.moveEnemiesUpAndDown(30);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		};
		
		countDownTimer.start();
	}
	
	public void startMoveLeft(int s, int delay, float dx){
		CountDownTimer countDownTimer = new CountDownTimer(s*1000, delay*1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				moveUP();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}
		};
		
		countDownTimer.start();
	}
	
	public void startMoveLeftAndRight(int s, int delay, float dx){
		CountDownTimer countDownTimer = new CountDownTimer(s*1000, delay*1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				moveUP();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}
		};
		
		countDownTimer.start();
	}
	
	public String getMovementActionDescriptions(){
		return action.getDescription();
	}

	public MovementAction getAction() {
		return action;
	}

	public void setAction(MovementAction action) {
		this.action = action;
	}
	
	public MovementAtionController getC(){
		return action.controller;
	}

	List<MovementActionInfo> infos;
	
	@Override
	public void update(float mx, float my, float angle) {
		// TODO Auto-generated method stub
//		setX(mx);
//		setY(my);
		
//		for(MovementActionInfo info : infos){
//			((ICircleController)info.getRotationController()).action(mx, my, angle);
//		}
		
		PointF pointF = ((SimultaneouslyMultiCircleMovementActionSet)action).notyMediator2(((ICircleController)infos.get(0).getRotationController()), mx, my, angle);
//		if(pointF!=null){
//		setX(pointF.x);
//		setY(pointF.y);
//		}
	}

	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		int i = observers.indexOf(o);
		if(i >= 0){
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Observer observer : observers){
			observer.update(getX(), getY(), 5);
		}
	}
	
	
}

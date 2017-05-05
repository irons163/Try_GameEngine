package com.example.try_gameengine.action.info;

import java.util.Arrays;
import java.util.Collections;

import android.graphics.Bitmap;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemTrigger;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.MovementActionItemAnimate2.FrameTrigger;
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemTriggerInitDelegate;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.LightImage;
import com.example.try_gameengine.framework.Sprite.SpriteAction;

public class MovementActionAnimationInfo extends MovementActionInfo implements MovementActionItemTriggerInitDelegate{
	private Bitmap[] bitmapFrames;
	private LightImage[] lightImageFrames;
	private int[] frameTriggerTimes; 
	private float scale;
	
//	public MovementActionAnimationInfo(Bitmap[] bitmapFrames, float secondPerOneTime){
//		this((long)(secondPerOneTime*1000*bitmapFrames.length), bitmapFrames, null);
//	}
//	
//	public MovementActionAnimationInfo(Bitmap[] bitmapFrames, float secondPerOneTime, String description){
//		this((long)(secondPerOneTime*1000*bitmapFrames.length), secondPerOneTime, bitmapFrames, null);
//	}
//	
//	public MovementActionAnimationInfo(long millisTotal, float secondPerOneTime, Bitmap[] bitmapFrames, String description){
//		this((long)(secondPerOneTime*1000*bitmapFrames.length), secondPerOneTime, bitmapFrames, description, null);
//	}
//	
//	public MovementActionAnimationInfo(long millisTotal, float secondPerOneTime, Bitmap[] bitmapFrames, String description, String spriteActionName){
//		super(millisTotal, 1, 0, 0, description, null, spriteActionName);
//		
//		int[] frameUpdateTimes = new int[bitmapFrames.length];
//		Arrays.fill(frameTriggerTimes, (int) secondPerOneTime*1000);
//		
//		init(millisTotal, bitmapFrames, frameUpdateTimes);
//	}
//	
//	public MovementActionAnimationInfo(long millisTotal, Bitmap[] bitmapFrames, int[] frameUpdateTimes){
//		this(millisTotal, bitmapFrames, frameUpdateTimes, "MovementActionItemAnimate", null);
//	}
//	
//	public MovementActionAnimationInfo(long millisTotal, Bitmap[] bitmapFrames, int[] frameUpdateTimes, String description, String spriteActionName){
//		super(millisTotal, 1, 0, 0, description, null, spriteActionName);
//		init(millisTotal, bitmapFrames, frameUpdateTimes);
//	}
	
	private void init(long millisTotal, Bitmap[] bitmapFrames, int[] frameUpdateTimes){
//		this.description = description + ",";
		this.bitmapFrames = bitmapFrames;
		this.frameTriggerTimes = frameUpdateTimes;
		this.scale = 1.0f;
	}
	
	public MovementActionAnimationInfo(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		this(triggerTotal, triggerTotal, bitmapFrames, frameTriggerTimes, "MovementActionItemAnimate");
	}
	
	public MovementActionAnimationInfo(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes, String description){
		super(triggerTotal, triggerInterval, 0, 0, description, null, description);
		
		if(frameTriggerTimes == null){
			frameTriggerTimes = new int[bitmapFrames.length];
			Arrays.fill(frameTriggerTimes, (int) triggerInterval);
		}

		init(triggerTotal, bitmapFrames, frameTriggerTimes);
	}
	
	public MovementActionAnimationInfo(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		this(triggerTotal, triggerTotal, lightImageFrames, frameTriggerTimes, "MovementActionItemAnimate");
	}
	
	public MovementActionAnimationInfo(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes, String description){
		super(triggerTotal, triggerInterval, 0, 0, description, null, description);
		
		this.lightImageFrames = lightImageFrames;
		
		if(frameTriggerTimes == null){
			frameTriggerTimes = new int[lightImageFrames.length];
			Arrays.fill(frameTriggerTimes, (int) triggerInterval);
//			Arrays.fill(frameTriggerTimes, (int) info.getDelay());
		}
		
		init(triggerTotal, new Bitmap[lightImageFrames.length], frameTriggerTimes);
	}
	
	@Override
	public void update(TimerOnTickListener timerOnTickListener) {
		
//		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(dx, dy);
//		}else{
			if(this.getSprite().currentAction!=null && this.getSprite().currentAction == this.getSprite().actions.get(this.getSpriteActionName()));
			this.getSprite().currentAction.trigger();
//		}
	}
	
	@Override
	public void update(float t, TimerOnTickListener timerOnTickListener) {
		
//		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(newDx, newDy);
//		}else{
			if(this.getSprite().currentAction!=null && this.getSprite().currentAction == this.getSprite().actions.get(this.getSpriteActionName()));
			this.getSprite().currentAction.trigger(t);
//		}
	}

	@Override
	public void ggg() {
		
	}
	
	@Override
	public void didCycleFinish() {
		// TODO Auto-generated method stub
//		super.didCycleFinish();
	}
	
	@Override
	public void createUpdateByEverytimeData() {
		super.createUpdateByEverytimeData();
		data.initWithInitDelegate(this);
	}
	
	@Override
	public void createUpdateByIntervalTimeData() {
		super.createUpdateByIntervalTimeData();
		data.initWithInitDelegate(this);
	}
	
	@Override
	public void createUpdateByTriggerData() {
		super.createUpdateByTriggerData();
		data.initWithInitDelegate(this);
	}

	@Override
	public void initForUpdateTime() {
		SpriteAction spriteAction = getSprite().addAction(getSpriteActionName(), bitmapFrames, frameTriggerTimes, scale, isLoop, new com.example.try_gameengine.framework.IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				if(lightImageFrames!=null)
					getSprite().setLightImage(lightImageFrames[nextFrameId]);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				
			}
			
			@Override
			public void actionFinish() {
//				isStop = true;
//				doReset();	
//				triggerEnable = false;
//				
//				if(actionListener!=null)
//					actionListener.actionFinish();
//				
//				synchronized (MovementActionItemAnimate2.this) {
//					MovementActionItemAnimate2.this.notifyAll();
//				}
			}
		});
		
		spriteAction.updateByMovement = true;				
	}

	@Override
	public void initForFrameTrigger() {
		SpriteAction spriteAction = getSprite().addActionFPS(getSpriteActionName(), bitmapFrames, frameTriggerTimes, scale, isLoop, new com.example.try_gameengine.framework.IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				if(lightImageFrames!=null)
					getSprite().setLightImage(lightImageFrames[nextFrameId]);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				
			}
			
			@Override
			public void actionFinish() {
//				isStop = true;
//				doReset();	
//				triggerEnable = false;
//				
//				if(actionListener!=null)
//					actionListener.actionFinish();
//				
//				synchronized (MovementActionItemAnimate2.this) {
//					MovementActionItemAnimate2.this.notifyAll();
//				}
			}
		});
		
		spriteAction.updateByMovement = true;						
	}
}

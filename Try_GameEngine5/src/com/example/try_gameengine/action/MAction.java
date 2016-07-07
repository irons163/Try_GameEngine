package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionAttachToTargetSpriteVisitor;
import com.example.try_gameengine.action.visitor.MovementActionNoRepeatSpriteActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;
import com.example.try_gameengine.action.visitor.MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.LightImage;
import com.example.try_gameengine.framework.Sprite;
import com.rits.cloning.Cloner;

//MAction use threadPool it would delay during action by action.
public class MAction {
	
	public interface MActionBlock{
		void runBlock();
	}
	
	public static MovementAction moveByX(float dx, long durationMs){
		float fps = Config.fps; //60
		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
		float perMove = dx * perFrame; //1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(durationMs, (long)(perFrame*1000), perMove, 0, "L", null, false));
	}
	
	public static MovementAction moveByY(float dy, long durationMs){
		float fps = Config.fps; //60
		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
		float perMove = dy * perFrame; //1*(1/60)=1/60
		
		long millisTotal = durationMs;
		long totalTrigger = (long) (millisTotal/(1000.0f/Config.fps));
		
//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, 0, perMove, "L", null, false));
	}
	
	public static MovementAction moveByY(float dy, long durationFPSFream, int count){
//		float fps = Config.fps; //60
//		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
//		float perMove = dy * perFrame; //1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(count, durationFPSFream, 0, dy, "L", null, false));
	}
	
	public static MovementAction moveTo(float targetX, float targetY, long durationMs){	
		MovementActionInfo movementActionInfo = new MovementActionFPSInfo(durationMs, 50, 0, 0, "L", null, false);
		movementActionInfo.setTargetXY(targetX, targetY);
		return new MovementActionItemBaseReugularFPS(movementActionInfo);
	}
	
	public static MovementAction moveTo(float targetX, float targetY, long durationFPSFream, int count){	
		MovementActionInfo movementActionInfo = new MovementActionFPSInfo(count, durationFPSFream, 0, 0, "L", null, false);
		movementActionInfo.setTargetXY(targetX, targetY);
		return new MovementActionItemBaseReugularFPS(movementActionInfo);
	}
	
	public static MovementAction repeat(MovementAction movementAction, int count){
		MovementAction movementActionsetWithThreadPool = new MovementActionSetWithThreadPool();
		Cloner cloner=new Cloner();
		cloner.setDumpClonedClasses(true);
		cloner.dontCloneInstanceOf(Sprite.class);
		for(int i = 0; i < count; i++){
			MovementAction clone=cloner.deepClone(movementAction);
			movementActionsetWithThreadPool.addMovementAction(clone);
		}
		return movementActionsetWithThreadPool;
	}
	
	public static MovementAction repeatFaster(MovementAction movementAction, long count){
//		MovementAction movementActionsetWithThreadPool = new MovementActionSetWithThreadPool();
		MovementAction repeatAction = new RepeatDecorator(movementAction, count);
//		movementActionsetWithThreadPool.addMovementAction(repeatAction);
		return repeatAction;
	}
	
	//SpriteAction == texture change action in sprite.
	//this repeat only repeat MovementAction not repeat the SpriteAction.
	public static MovementAction repeatFasterWithoutRepeatSpriteAction(MovementAction movementAction, long count){
		MovementAction repeatAction = new RepeatDecorator(movementAction, count);
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(repeatAction);
		IMovementActionVisitor movementActionVisitor = new MovementActionNoRepeatSpriteActionVisitor();
		objectStructure.handleRequest(movementActionVisitor);
		return repeatAction;
	}
	
	public static MovementAction repeatForever(MovementAction movementAction){
		return new LooperDecorator(movementAction);
	}
	
	public static MovementAction runBlock(MActionBlock block){
		
		return new MovementActionBlock(block);
	}
	
	public static MovementAction runBlockNoDelay(MActionBlock block){
		
		return new MovementActionNoDelayBlock(block);
	}
	
	public static MovementAction alphaAction(long millisTotal, int alpha){
		return new MovementActionItemAlpha(millisTotal, alpha);	
	}
	
	public static MovementAction alphaAction(long millisTotal, int originalAlpha, int alpha){
		return new MovementActionItemAlpha(millisTotal, originalAlpha, alpha);	
	}
	
	public static MovementAction alphaAction(long triggerTotal, long triggerInterval, int alpha){
		return new MovementActionItemAlpha(triggerTotal, triggerInterval, alpha);	
	}
	
	public static MovementAction alphaAction(long triggerTotal, long triggerInterval, int originalAlpha, int alpha){
		return new MovementActionItemAlpha(triggerTotal, triggerInterval, originalAlpha, alpha);	
	}
	
	public static MovementAction animateAction(Bitmap[] bitmapFrames, float secondPerOneTime){
		return new MovementActionItemAnimate(bitmapFrames, secondPerOneTime);
	}
	
	public static MovementAction animateAction(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(millisTotal, bitmapFrames, frameTriggerTimes);	
	}
	
	public static MovementAction animateAction(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(millisTotal, bitmapFrames, frameTriggerTimes, scale);	
	}
	
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes);	
	}
	
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes, scale);	
	}
	
	public static MovementAction animateAction(LightImage[] lightImageFrames, float secondPerOneTime){
		return new MovementActionItemAnimate(lightImageFrames, secondPerOneTime);
	}
	
	public static MovementAction animateAction(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(millisTotal, lightImageFrames, frameTriggerTimes);	
	}
	
	public static MovementAction animateAction(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(millisTotal, lightImageFrames, frameTriggerTimes, scale);	
	}
	
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes);	
	}
	
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes, scale);	
	}
	
	public static MovementAction scaleXToAction(long millisTotal, float scaleX){
		return new MovementActionItemScale(millisTotal, scaleX, MovementActionItemScale.NO_SCALE);	
	}
	
	public static MovementAction scaleYToAction(long millisTotal, float scaleY){
		return new MovementActionItemScale(millisTotal, MovementActionItemScale.NO_SCALE, scaleY);	
	}
	
	public static MovementAction scaleToAction(long millisTotal, float scaleX, float scaleY){
		return new MovementActionItemScale(millisTotal, scaleX, scaleY);	
	}
	
	public static MovementAction scaleXToAction(long triggerTotal, long triggerInterval, float scaleX){
		return new MovementActionItemScale(triggerTotal, triggerInterval, scaleX, MovementActionItemScale.NO_SCALE);	
	}
	
	public static MovementAction scaleYToAction(long triggerTotal, long triggerInterval, float scaleY){
		return new MovementActionItemScale(triggerTotal, triggerInterval, MovementActionItemScale.NO_SCALE, scaleY);	
	}
	
	public static MovementAction scaleToAction(long triggerTotal, long triggerInterval, float scaleX, float scaleY){
		return new MovementActionItemScale(triggerTotal, triggerInterval, scaleX, scaleY);	
	}
	
	public static MovementAction rotationToAction(long millisTotal, float rotation){
		return new MovementActionItemRotation(millisTotal, rotation);	
	}
	
	public static MovementAction rotationToAction(long triggerTotal, long triggerInterval, float rotation){
		return new MovementActionItemRotation(triggerTotal, triggerInterval, rotation);	
	}
	
	public static MovementAction waitAction(long triggerTotal){
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(triggerTotal, 1, 0, 0, "waitAction", null, false));	
	}
	
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionsetWithThreadPool = new MovementActionSetWithThreadPool();

		for(int i = 0; i < movementActions.length; i++){
			movementActionsetWithThreadPool.addMovementAction(movementActions[i]);
		}
		return movementActionsetWithThreadPool;
	}
	
	public static MovementAction group(MovementAction[] movementActions){
		MovementAction movementActionSetGroupWithOutThread = new MovementActionSetGroupWithOutThread();

		for(int i = 0; i < movementActions.length; i++){
			movementActionSetGroupWithOutThread.addMovementAction(movementActions[i]);
		}
		return movementActionSetGroupWithOutThread;
	}
	
	public static void attachToTargetSprite(MovementAction movementAction, Sprite targetSprite){
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(movementAction);
		IMovementActionVisitor movementActionVisitor = new MovementActionAttachToTargetSpriteVisitor(targetSprite);
		objectStructure.handleRequest(movementActionVisitor);
	}
	
	public static void setDefaultTimeToTickListenerIfNotSetYetToTargetSprite(MovementAction movementAction, Sprite targetSprite){
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(movementAction);
		IMovementActionVisitor movementActionVisitor = new MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor(targetSprite);
		objectStructure.handleRequest(movementActionVisitor);
	}
	
	static class MovementActionBlock extends MovementAction{
		protected MActionBlock block;
		
		public MovementActionBlock(MActionBlock block){
			this.block = block;
		}

		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			if (!isFinish) {		
				if(!isLoop){
					isFinish = true;
				}else{
					actionListener.beforeChangeFrame(0);
					block.runBlock();
					actionListener.afterChangeFrame(0);
				}
			}
			
			if(isFinish){
				actionListener.actionFinish();
				synchronized (MovementActionBlock.this) {
					MovementActionBlock.this.notifyAll();
				}
			}
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			actionListener.actionStart();
			actionListener.beforeChangeFrame(0);
			block.runBlock();
			actionListener.afterChangeFrame(0);
			if(!isLoop)
				isFinish = true;
		}
		
		@Override
		public MovementActionInfo getInfo() {
			// TODO Auto-generated method stub
			return null;
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
			return infos;
		}
		
		@Override
		void cancelMove() {
			// TODO Auto-generated method stub
//			super.cancelMove();
			isFinish = true;
			synchronized (MovementActionBlock.this) {
				MovementActionBlock.this.notifyAll();
			}
		}
		
		@Override
		public boolean isFinish(){
			return isFinish;
		}
		
		@Override
		public void accept(IMovementActionVisitor movementActionVisitor){
			movementActionVisitor.visitLeaf(this);
		}
	}
	
	static class MovementActionNoDelayBlock extends MovementActionBlock{

		public MovementActionNoDelayBlock(MActionBlock block) {
			super(block);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			if (!isFinish) {		
				if(!isLoop){
					isFinish = true;
				}else{
					actionListener.beforeChangeFrame(0);
					block.runBlock();
					actionListener.afterChangeFrame(0);
				}
			}
			
			if(isFinish){
				executor.submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						actionListener.actionFinish();
						synchronized (MovementActionNoDelayBlock.this) {
							MovementActionNoDelayBlock.this.notifyAll();
						}
					}
				});
			}
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			actionListener.actionStart();
			actionListener.beforeChangeFrame(0);
			block.runBlock();
			actionListener.afterChangeFrame(0);
			if(!isLoop){
				isFinish = true;
				executor.submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						actionListener.actionFinish();
						synchronized (MovementActionNoDelayBlock.this) {
							MovementActionNoDelayBlock.this.notifyAll();
						}
					}
				});
			}
		}
		
		@Override
		void cancelMove() {
			// TODO Auto-generated method stub
//			super.cancelMove();
			isFinish = true;
			synchronized (MovementActionNoDelayBlock.this) {
				MovementActionNoDelayBlock.this.notifyAll();
			}
		}
	}
}

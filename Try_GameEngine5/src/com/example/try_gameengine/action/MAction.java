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


/**
 * {@code MAction} has a set of methods to create many useful MovementActions.
 * @author irons
 *
 */
public class MAction { //MAction use threadPool it would delay during action by action.
	
	/**
	 * {@code MActionBlock} is a block to make developers do their things in MovementAction sets.
	 * @author irons
	 *
	 */
	public interface MActionBlock{
		void runBlock();
	}
	
	/**
	 * {@code moveByX} is a MovementAction to move x-dir by {@code dx} during {@code durationMs} millisecond.
	 * @param dx
	 * 			x-dir move distance.
	 * @param durationMs
	 * 			milliseconds for move.
	 * @return
	 */
	public static MovementAction moveByX(float dx, long durationMs){
		/*
		float fps = Config.fps; //ex:60
		float perFrame = 1000.0f/durationMs/fps; //ex:1000/1000/60=1/60;
//		float perFrame = durationMs/1000.0f/fps; //ex:1000/1000/60=1/60;
		float perMove = dx * perFrame; //ex:1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(durationMs, (long)(perFrame*1000), perMove, 0, "L", null, false));
		*/
		
		float fps = Config.fps; //60
		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
		float perMove = dx * perFrame; //1*(1/60)=1/60
		
		long millisTotal = durationMs;
		long totalTrigger = (long) (millisTotal/(1000.0f/Config.fps));
		
//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, perMove, 0, "L"));
	}
	
	/**
	 * {@code moveByY} is a MovementAction to move y-dir by {@code dy} during {@code durationMs} millisecond.
	 * @param dy
	 * 			y-dir move distance.
	 * @param durationMs
	 * 			milliseconds for move.
	 * @return
	 */
	public static MovementAction moveByY(float dy, long durationMs){
		float fps = Config.fps; //60
		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
		float perMove = dy * perFrame; //1*(1/60)=1/60
		
		long millisTotal = durationMs;
		long totalTrigger = (long) (millisTotal/(1000.0f/Config.fps));
		
//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, 0, perMove, "L"));
	}
	
	/**
	 * {@code moveByY} is a MovementAction to move y-dir by {@code dy} during {@code durationMs} millisecond.
	 * @param dy
	 * 			y-dir move distance.
	 * @param durationFPSFream
	 * 			FPS count for move.
	 * @param count
	 * 			repeat times.
	 * @return
	 */
	public static MovementAction moveByY(float dy, long durationFPSFream, int count){
//		float fps = Config.fps; //60
//		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
//		float perMove = dy * perFrame; //1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(count, durationFPSFream, 0, dy, "L"));
	}
	
	/**
	 * {@code moveByY} is a MovementAction to move xy-dir to targetX and targetY during {@code durationMs} millisecond.
	 * @param targetX
	 * 			
	 * @param targetY
	 * 			
	 * @param durationMs
	 * 			
	 * @return
	 */
	public static MovementAction moveTo(float targetX, float targetY, long durationMs){	
		long millisTotal = durationMs;
		long totalTrigger = (long) (millisTotal/(1000.0f/Config.fps));
		
		MovementActionInfo movementActionInfo = new MovementActionFPSInfo(totalTrigger, 1, 0, 0, "L");
		movementActionInfo.setTargetXY(targetX, targetY);
		return new MovementActionItemBaseReugularFPS(movementActionInfo);
	}
	
	/**
	 * 
	 * @param targetX
	 * @param targetY
	 * @param durationFPSFream
	 * @param count
	 * @return
	 */
	public static MovementAction moveTo(float targetX, float targetY, long durationFPSFream, int count){	
//		MovementActionInfo movementActionInfo = new MovementActionInfo(count, durationFPSFream, 0, 0, "L", null, false);
		MovementActionInfo movementActionInfo = new MovementActionFPSInfo(count, durationFPSFream, 0, 0, "L");
		movementActionInfo.setTargetXY(targetX, targetY);
		return new MovementActionItemBaseReugularFPS(movementActionInfo);
	}
	
	/**
	 * {@code repeat} is a method not 
	 * @param movementAction
	 * @param count
	 * @return
	 */
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
	
	/**
	 * {@code repeatFaster} is easy to repeat the target movementAction. 
	 * If the {@code MovementAction} has {@code SpriteAction}, then {@code SpriteAction} repeat also. 
	 * 
	 * @param movementAction
	 * 			target movementAction for repeat.
	 * @param count
	 * 			repeat count.
	 * @return
	 */
	public static MovementAction repeatFaster(MovementAction movementAction, long count){
		MovementAction repeatAction = new RepeatDecorator(movementAction, count);
		return repeatAction;
	}
	

	/**
	 * {@code repeatFasterWithoutRepeatSpriteAction} is a repeat action but only not repeat the {@code SpriteAction}, 
	 * so when the {@code SpriteAction} done but {@code repeatFasterWithoutRepeatSpriteAction} still in repeat, 
	 * {@code SpriteAction} just do nothing. 
	 * 
	 * @param movementAction
	 * 			target movementAction for repeat.
	 * @param count
	 * 			repeat count.
	 * @return
	 */
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
	
	/**
	 * create a MovementAction repeat forever.
	 * @param movementAction
	 * @return
	 */
	public static MovementAction repeatForever(MovementAction movementAction){
		return new LooperDecorator(movementAction);
	}
	
	/**
	 * create a MovementActionBlock which has MActionBlock to deal with.
	 * @param block
	 * 			a MActionBlock for deal with custom things while MovementAction running.
	 * @return {@link MovementActionBlock}.
	 */
	public static MovementAction runBlock(MActionBlock block){
		
		return new MovementActionBlock(block);
	}
	
	/**
	 * create a MovementActionNoDelayBlock which has MActionBlock to deal with.. 
	 * @param block
	 * 			a MActionBlock for deal with custom things while MovementAction running.
	 * @return {@link MovementActionNoDelayBlock}.
	 */
	public static MovementAction runBlockNoDelay(MActionBlock block){
		
		return new MovementActionNoDelayBlock(block);
	}
	
	/**
	 * {@code alphaAction} is a MovementAction to control alpha value to target alpha value during {@code millisTotal} millisecond.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param alpha
	 * 			target alpha value.
	 * @return
	 */
	public static MovementAction alphaAction(long millisTotal, int alpha){
		return new MovementActionItemAlpha(millisTotal, alpha);	
	}
	
	public static MovementAction alphaAction2(long millisTotal, int alpha){
		return new MovementActionItemAlpha2(millisTotal, alpha);	
	}
	
	/**
	 * {@code alphaAction} is a MovementAction to control alpha value from original alpha value to target alpha value during {@code millisTotal} millisecond.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param originalAlpha
	 * 			alpha value when movement action start.
	 * @param alpha
	 * 			target alpha value.
	 * @return
	 */
	public static MovementAction alphaAction(long millisTotal, int originalAlpha, int alpha){
		return new MovementActionItemAlpha(millisTotal, originalAlpha, alpha);	
	}
	
	/**
	 * {@code alphaAction} is a MovementAction to control alpha value to target alpha value during {@code triggerTotal} FPS by {@code triggerInterval} FPS.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param alpha
	 * 			target alpha value.
	 * @return
	 */
	public static MovementAction alphaAction(long triggerTotal, long triggerInterval, int alpha){
		return new MovementActionItemAlpha(triggerTotal, triggerInterval, alpha);	
	}
	
	/**
	 * {@code alphaAction} is a MovementAction to control alpha value to target alpha value during {@code triggerTotal} FPS by {@code triggerInterval} FPS.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param originalAlpha
	 * 			alpha value when movement action start.
	 * @param alpha
	 * 			target alpha value.
	 * @return
	 */
	public static MovementAction alphaAction(long triggerTotal, long triggerInterval, int originalAlpha, int alpha){
		return new MovementActionItemAlpha(triggerTotal, triggerInterval, originalAlpha, alpha);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param bitmapFrames
	 * 			bitmaps for animate.
	 * @param secondPerOneTime
	 * 			time for each bitmap frame be show.
	 * @return MovementActionItemAnimate.
	 */
	public static MovementAction animateAction(Bitmap[] bitmapFrames, float secondPerOneTime){
		return new MovementActionItemAnimate(bitmapFrames, secondPerOneTime);
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param bitmapFrames
	 * 			bitmaps for animate.
	 * @param secondPerOneTime
	 * 			time for each bitmap frame be show.
	 * @return MovementActionItemAnimate.
	 */
	public static MovementAction animateAction(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(millisTotal, bitmapFrames, frameTriggerTimes);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param bitmapFrames
	 * 			bitmaps for animate.
	 * @param frameTriggerTimes
	 * 			trigger interval count for action.
	 * @param scale
	 * 			not work now.
	 * @return
	 */
	public static MovementAction animateAction(long millisTotal, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(millisTotal, bitmapFrames, frameTriggerTimes, scale);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param bitmapFrames
	 * 			bitmaps for animate.
	 * @param frameTriggerTimes
	 * @return
	 */
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param bitmapFrames
	 * 			Bitmaps.
	 * @param frameTriggerTimes
	 * 			trigger interval count for action.
	 * @param scale
	 * 			not work now.
	 * @return
	 */
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes, scale);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param secondPerOneTime
	 * 			
	 * @return MovementAction
	 */
	public static MovementAction animateAction(LightImage[] lightImageFrames, float secondPerOneTime){
		return new MovementActionItemAnimate(lightImageFrames, secondPerOneTime);
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			
	 * @return
	 */
	public static MovementAction animateAction(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(millisTotal, lightImageFrames, frameTriggerTimes);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			total trigger count for action.
	 * @param scale
	 * 			not work now.
	 * @return
	 */
	public static MovementAction animateAction(long millisTotal, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(millisTotal, lightImageFrames, frameTriggerTimes, scale);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * 			
	 * @return
	 */
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes);	
	}
	
	/**
	 * {@code animateAction} is a MovementAction to control animating with bitmaps, each bitmap frame be show during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param lightImageFrames
	 * 			images of {@link LightImage}.
	 * @param frameTriggerTimes
	 * @param scale
	 * 			not work now.
	 * @return
	 */
	public static MovementAction animateAction(long triggerTotal, long triggerInterval, LightImage[] lightImageFrames, int[] frameTriggerTimes, float scale){
		return new MovementActionItemAnimate(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes, scale);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param scaleX
	 * @return
	 */
	public static MovementAction scaleXToAction(long millisTotal, float scaleX){
		return new MovementActionItemScale(millisTotal, scaleX, MovementActionItemScale.NO_SCALE);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param scaleY
	 * @return
	 */
	public static MovementAction scaleYToAction(long millisTotal, float scaleY){
		return new MovementActionItemScale(millisTotal, MovementActionItemScale.NO_SCALE, scaleY);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * @param scaleX
	 * @param scaleY
	 * @return
	 */
	public static MovementAction scaleToAction(long millisTotal, float scaleX, float scaleY){
		return new MovementActionItemScale(millisTotal, scaleX, scaleY);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param scaleX
	 * 			
	 * @return
	 */
	public static MovementAction scaleXToAction(long triggerTotal, long triggerInterval, float scaleX){
		return new MovementActionItemScale(triggerTotal, triggerInterval, scaleX, MovementActionItemScale.NO_SCALE);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * 
	 * @param scaleY
	 * 			
	 * @return
	 */
	public static MovementAction scaleYToAction(long triggerTotal, long triggerInterval, float scaleY){
		return new MovementActionItemScale(triggerTotal, triggerInterval, MovementActionItemScale.NO_SCALE, scaleY);	
	}
	
	/**
	 * {@code scaleXToAction} is a MovementAction to control scaleX to target scaleX during {@code secondPerOneTime}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param scaleX
	 * 			
	 * @param scaleY
	 * 			
	 * @return
	 */
	public static MovementAction scaleToAction(long triggerTotal, long triggerInterval, float scaleX, float scaleY){
		return new MovementActionItemScale(triggerTotal, triggerInterval, scaleX, scaleY);	
	}
	
	/**
	 * {@code rotationToAction} is a MovementAction to control rotation to target rotation during {@code millisTotal}.
	 * @param millisTotal
	 * 			like duration milliseconds.
	 * 
	 * @param rotation
	 * 			
	 * @return
	 */
	public static MovementAction rotationToAction(long millisTotal, float rotation){
		return new MovementActionItemRotation(millisTotal, rotation);	
	}
	
	/**
	 * {@code rotationToAction} is a MovementAction to control rotation to target rotation during {@code millisTotal}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @param triggerInterval
	 * 			trigger interval count for action.
	 * @param rotation
	 * 			rotation.
	 * @return
	 */
	public static MovementAction rotationToAction(long triggerTotal, long triggerInterval, float rotation){
		return new MovementActionItemRotation(triggerTotal, triggerInterval, rotation);	
	}
	
	/**
	 * {@code waitAction} is a MovementAction to controlduring {@code millisTotal}.
	 * @param triggerTotal
	 * 			total trigger count for action.
	 * @return
	 */
	public static MovementAction waitAction(long triggerTotal){
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(triggerTotal, 1, 0, 0, "waitAction"));	
	}
	
	/**
	 * {@code sequence} is a MovementAction to control the array of movementActions concurrently.
	 * @param movementActions 
	 * 			the array of movementAcionts.
	 * @return MovementAction
	 */
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionsetWithThreadPool = new MovementActionSetWithThreadPool();

		for(int i = 0; i < movementActions.length; i++){
			movementActionsetWithThreadPool.addMovementAction(movementActions[i]);
		}
		return movementActionsetWithThreadPool;
	}
	
	/**
	 * {@code group} create a MovementAction to control the array of movementActions concurrently.
	 * @param movementActions
	 * 			the array of movementAcionts.
	 * @return MovementAction;
	 */
	public static MovementAction group(MovementAction[] movementActions){
		MovementAction movementActionSetGroupWithOutThread = new MovementActionSetGroupWithOutThread();

		for(int i = 0; i < movementActions.length; i++){
			movementActionSetGroupWithOutThread.addMovementAction(movementActions[i]);
		}
		return movementActionSetGroupWithOutThread;
	}
	
	/**
	 * attach SpriteAction to movement action.
	 * @param movementAction
	 * 			action for attach sprite.
	 * @param targetSprite
	 * 			sprite attach to movement action.
	 */
	public static boolean attachSpriteActionWithSpriteActionName(MovementAction movementAction, String spriteActionName){
		if(movementAction.getInfo()!=null){
			movementAction.getInfo().setSpriteActionName(spriteActionName);
			return true;
		}
		return false;
	}
	
	/**
	 * attach sprite to movement action.
	 * @param movementAction
	 * 			action for attach sprite.
	 * @param targetSprite
	 * 			sprite attach to movement action.
	 */
	public static void attachToTargetSprite(MovementAction movementAction, Sprite targetSprite){
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(movementAction);
		IMovementActionVisitor movementActionVisitor = new MovementActionAttachToTargetSpriteVisitor(targetSprite);
		objectStructure.handleRequest(movementActionVisitor);
	}
	
	/**
	 * @param movementAction
	 * @param targetSprite
	 */
	public static void setDefaultTimeToTickListenerIfNotSetYetToTargetSprite(MovementAction movementAction, Sprite targetSprite){
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(movementAction);
		IMovementActionVisitor movementActionVisitor = new MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor(targetSprite);
		objectStructure.handleRequest(movementActionVisitor);
	}
	
	/**
	 * A {@code MovementActionBlock} extends MovementAction to create a biock. This has a frame delay because when it start that need to wait next trigger to do block.
 	 * @author irons
	 *
	 */
	static class MovementActionBlock extends MovementAction{
		protected MActionBlock block;
		
		/**
		 * constructor.
		 * @param block
		 */
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
	
	/**
	 * A {@code MovementActionNoDelayBlock} extends MovementActionBlock to create a block with no delay.
	 * @author irons
	 *
	 */
	static class MovementActionNoDelayBlock extends MovementActionBlock{

		/**constructor.
		 * @param block
		 */
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

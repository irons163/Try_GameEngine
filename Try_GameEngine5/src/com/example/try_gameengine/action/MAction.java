package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionNoRepeatSpriteActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;
import com.rits.cloning.Cloner;

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

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(durationMs, (long)(perFrame*1000), 0, perMove, "L", null, false));
	}
	
	public static MovementAction moveByY(float dy, long durationFPSFream, int count){
//		float fps = Config.fps; //60
//		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
//		float perMove = dy * perFrame; //1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(count, durationFPSFream, 0, dy, "L", null, false));
	}
	
	public static MovementAction moveTo(float targetX, float targetY, long durationMs){	
		MovementActionInfo movementActionInfo = new MovementActionInfo(durationMs, 50, 0, 0, "L", null, false);
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
	
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionsetWithThreadPool = new MovementActionSetWithThreadPool();

		for(int i = 0; i < movementActions.length; i++){
			movementActionsetWithThreadPool.addMovementAction(movementActions[i]);
		}
		return movementActionsetWithThreadPool;
	}
	
	static class MovementActionBlock extends MovementAction{
		protected MActionBlock block;
		
		public MovementActionBlock(MActionBlock block){
			this.block = block;
		}

		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			if(isFinish){
				synchronized (MovementActionBlock.this) {
					MovementActionBlock.this.notifyAll();
				}
			}
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			block.runBlock();
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
			synchronized (MovementActionBlock.this) {
				MovementActionBlock.this.notifyAll();
			}
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
			
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			block.runBlock();
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					synchronized (MovementActionNoDelayBlock.this) {
						MovementActionNoDelayBlock.this.notifyAll();
					}
				}
			});
		}
		
		@Override
		void cancelMove() {
			// TODO Auto-generated method stub
//			super.cancelMove();
			synchronized (MovementActionNoDelayBlock.this) {
				MovementActionNoDelayBlock.this.notifyAll();
			}
		}
	}
}

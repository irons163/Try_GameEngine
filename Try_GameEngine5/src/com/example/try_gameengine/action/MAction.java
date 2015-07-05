package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

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
	
	public static MovementAction repeatFaster(MovementAction movementAction, int count){
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
	
	public static MovementAction repeatForever(MovementAction movementAction){
		return new LooperDecorator(movementAction);
	}
	
	public static MovementAction runBlock(MActionBlock block){
		
		return new MovementActionBlock(block);
	}
	
	static class MovementActionBlock extends MovementAction{
		private MActionBlock block;
		
		public MovementActionBlock(MActionBlock block){
			this.block = block;
		}

		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			block.runBlock();
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
		
	}
}

package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Config;

/**
 * {@code MAction2} has a set of methods to create many useful MovementActions with no thread.
 * @author irons
 *
 */
public class MAction2 {
	
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
		
//		float fps = Config.fps; //60
//		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
//		float perMove = dx * perFrame; //1*(1/60)=1/60
		
		long millisTotal = durationMs;
		long millisInterval = (long) (1000.0f/Config.fps); //1000/60=100/6;
		float perMove = (float) (dx * ((double)millisInterval/millisTotal)); //10*((100/6)/1000) = 10*(1/60) = 10/60 = 1/6;
		
//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
		return new MovementActionItemUpdateTime(new MovementActionInfo(millisTotal, millisInterval, perMove, 0, "L", null, false));
	}
	
	/**
	 * {@code moveByY} is a MovementAction to move y-dir by {@code dy} during {@code durationMs} millisecond.
	 * @param dy
	 * 			y-dir move distance.
	 * @param durationMs
	 * 			milliseconds for move.
	 * @return
	 */
	public static MovementAction jumpTo(float dy, long durationMs){
		float fps = Config.fps; //60
		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
		float perMove = dy * perFrame; //1*(1/60)=1/60
		
		long millisTotal = durationMs;
		long totalTrigger = (long) (millisTotal/(1000.0f/Config.fps));
		
		MovementActionInfo movementActionInfo = new MovementActionInfo(totalTrigger, 1, 0, perMove, "L", null, false); 
		movementActionInfo.gravityController = new GravityController();
//		movementActionInfo.gravityController.
		new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, 0, perMove, "L", null, false));
		
//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, 0, perMove, "L", null, false));
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
		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(totalTrigger, 1, 0, perMove, "L", null, false));
	}
	
	/**
	 * sequence create lots of action with no thread.
	 * @param movementActions 
	 * 			the array of movementaciotns.
//	 * @return {@code MovementAction}.
	 */
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionSetWithOutThread = new MovementActionSetWithOutThread();

		for(int i = 0; i < movementActions.length; i++){
			movementActionSetWithOutThread.addMovementAction(movementActions[i]);
		}
		return movementActionSetWithOutThread;
	}
}

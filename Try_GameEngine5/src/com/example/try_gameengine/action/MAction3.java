package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Config;

/**
 * {@code MAction2} has a set of methods to create many useful MovementActions with no thread.
 * @author irons
 *
 */
public class MAction3 {
	
	/**
	 * {@code moveByX} is a MovementAction to move x-dir by {@code dx} during {@code durationMs} millisecond.
	 * @param dx
	 * 			x-dir move distance.
	 * @param durationMs
	 * 			milliseconds for move.
	 * @return
	 */
	public static MovementAction moveByX(float dx, long durationMs){
		return new MovementActionItemUpdateTime(new MovementActionInfo(durationMs, 1, dx, 0, "L"));
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
	
//	public static MovementAction cyclePathMovement(MovementActionItemMoveByCurve moveByCurve){
//		MovementAction action = new MovementActionSetWithOutThread();
//		MovementActionItemMoveByCurve newMoveByCurve = null;
//		try {
//			newMoveByCurve = (MovementActionItemMoveByCurve) moveByCurve.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		newMoveByCurve.setMathUtil(moveByCurve.getMathUtil());
//		newMoveByCurve.isCyclePath();
//		action.addMovementAction(moveByCurve);
//		action.addMovementAction(newMoveByCurve);
//		return action;
//	}
}

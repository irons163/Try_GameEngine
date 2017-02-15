package com.example.try_gameengine.action;


/**
 * MovementAtionController can control some work.
 * @author irons
 *
 */
public class MovementAtionController {
	MovementAction action;
	
	/**
	 * @param action
	 */
	public void setMovementAction(MovementAction action){
		this.action = action;
	}
	
	/**
	 * 
	 */
	public void cancelCurrentMove(){
		action.cancelMove();
	}
	
	/**
	 * 
	 */
	public void cancelAllMove(){
		action.cancelAllMove();
	}
	
	/**
	 * 
	 */
	public void pause(){
		action.pause();
	}
	
	public void pause(long milliseconds){
		action.pause();
	}
	
	/**
	 * 
	 */
	public void resume(){
		action.pause();
	}
	
	/**
	 * 
	 */
	public void restart(){
		action.cancelMove();
		action.start();
	}
	
	/**
	 * @param loop
	 */
	public void looper(boolean loop){
		if(action.isFinish()){
			
		}
	}
}

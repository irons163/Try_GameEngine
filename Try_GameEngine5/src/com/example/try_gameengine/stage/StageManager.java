package com.example.try_gameengine.stage;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.example.try_gameengine.application.GameApplication;

import android.content.Context;
import android.content.Intent;

/**
 * {@code StageManager} use to manage the stages work, include add, start , stop, change , next , previous etc.
 * {@code StageManager} is a static class, it means only one StageManager used.
 * @author irons
 *
 */
public class StageManager {
	public static List<Stage> stages = new ArrayList<Stage>();
	private static Stage currentActiveStage;
	private static int currentStageIndex;
	private static Context context;
	
	public static void init(Context context){
		StageManager.context = context;
	}
	
	public static void init(Stage currentActiveStage){
		StageManager.currentActiveStage = currentActiveStage;
		addStage(currentActiveStage);
	}
	
	/**
	 * add Stage into stageManager.
	 * @param stage
	 */
	public static void addStage(Stage stage){
		stages.add(stage);
	}
	
	/**
	 * @return
	 */
	public static List<Stage> getStages(){
		return stages;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public static Stage getStage(String id){
		Stage targetStage = null;
		for(int i =0; i<stages.size(); i++){
			Stage stage = stages.get(i);
			if(stage.getId().equals(id)){
				targetStage = stage;
			}
		}

		return targetStage;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public static int getStageIndex(String id){
		int targetStageIndex = -1;
		for(int i =0; i<stages.size(); i++){
			Stage stage = stages.get(i);
			if(stage.getId().equals(id)){
				targetStageIndex = i;
			}
		}

		return targetStageIndex;
	}
	
	/**
	 * @param id
	 */
	public static void startStage(String id){
		if(currentActiveStage!=null)
			currentActiveStage.stop(); 
		Stage stage = getStage(id);
		if(stage!=null){
			stage.start();
			currentActiveStage = stage;
		}
	}
	
	/**
	 * @param id
	 */
	public static void stopStage(String id){
		Stage stage = getStage(id);
		if(stage!=null){
			stage.stop();
		}
	}
	
	/**
	 * @param index
	 */
	public static void startStage(int index){
		if(currentActiveStage!=null)
			currentActiveStage.stop(); 
		if(index >=0 && index < stages.size()){
			Stage stage = stages.get(index);
//			stage.start();
			startStage(context, stage);
			currentActiveStage = stage;
			currentStageIndex = index;
		}
	}
	
	/**
	 * @param index
	 */
	public static void stopStage(int index){
		if(index >=0 && index < stages.size()){
			stages.get(index).stop();
		}
	}
	
	/**
	 * startSratge like start activity. 
	 * @param context
	 * 			android context.
	 * @param targetStage
	 * 			target
	 */
	private static void startStage(Context context, Stage targetStage){
		Intent intent = new Intent(context, targetStage.getClass());
		context.startActivity(intent);
	}// has problem: it create a new stage, not use the targetStage.
	
	/**
	 * create next Stage and to next stage, not close current stage.
	 */
	public static void next(){
		currentStageIndex++;
		if(currentActiveStage!=null)
			currentActiveStage.stop(); 
		if(currentStageIndex == stages.size()){
			currentStageIndex = 0;
		}
		Stage stage = stages.get(currentStageIndex);
		stage.start();
		currentActiveStage = stage;
	}
	
	/**
	 * changeStage to previous stage, not close current stage.
	 */
	public static void previous(){
		previous(false);
	}// has problem: it create a new stage.
	
	/**
	 * changeStage to previous stage.
	 * @param isCloseCurrentStage 
	 * 			to control when change, close current stage or not.
	 */
	public static void previous(boolean isCloseCurrentStage){
		currentStageIndex--;
		if(currentActiveStage!=null)
			currentActiveStage.stop(); 
		if(currentStageIndex == -1){
			currentStageIndex = stages.size()-1;
		}
		Stage stage = stages.get(currentStageIndex);
		changeStage(currentActiveStage, stage, isCloseCurrentStage);
		currentActiveStage = stage;
	}// has problem: it create a new stage.
	
	/**
	 * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity. 
	 * @param currentStage
	 * 			which stage used now.
	 * @param cls
	 * 			target class to show.
	 * @param isCloseCurrentStage
	 * 			to control when change, close current stage or not.
	 */
	public static void changeStage(Stage currentStage, Class<?> cls, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, cls);
		currentStage.startActivity(intent);
		stopStage(currentStage);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	/**
	 * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity. 
	 * @param currentStage
	 * 			which stage used now.
	 * @param cls
	 * 			target class to show.
	 * @param flag
	 * 			the flag for android intent because stage is kind of activity.
	 * @param isCloseCurrentStage
	 * 			to control when change, close current stage or not.
	 */
	public static void changeStage(Stage currentStage, Class<?> cls, int flag, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, cls);
		intent.addFlags(flag);
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	/**
	 * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity. 
	 * @param currentStage
	 * @param StargeId
	 * @param isCloseCurrentStage
	 * 			to control when change, close current stage or not.
	 */
	public static void changeStage(Stage currentStage, String StargeId, boolean isCloseCurrentStage){
		Stage stage = StageManager.getStage(StargeId);
		Intent intent = new Intent(currentStage, stage.getClass());
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}

//	public void changeStage(int StargeIndex){
//		
//	}
	
	/**
	 * change stage from current stage to target stage. 
	 * @param currentStage
	 * 			which stage used now.
	 * @param targetStage
	 * 			target stage used now.
	 * @param isCloseCurrentStage
	 * 			to control when change, close current stage or not.
	 */
	public static void changeStage(Stage currentStage, Stage targetStage, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, targetStage.getClass());
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	/**
	 * get current active stage.
	 * @return current stage.
	 */
	public static Stage getCurrentStage(){
		return currentActiveStage;
	}
	
	/**
	 * stop stage.
	 * @param currentStage
	 */
	private static void stopStage(Stage currentStage){
		currentStage.stop();
	}
}

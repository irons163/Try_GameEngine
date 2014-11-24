package com.example.try_gameengine.stage;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.example.try_gameengine.application.Application;

import android.content.Context;
import android.content.Intent;

public class StageManager {
	public static List<Stage> stages = new ArrayList<Stage>();
	private static Stage currentActiveStage;
	private static int currentStageIndex;
	private static Context context;
	
	public static void init(Context context){
		StageManager.context = context;
	}
	
	public static void addStage(Stage stage){
		stages.add(stage);
	}
	
	public static List<Stage> getStages(){
		return stages;
	}
	
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
	
	public static void startStage(String id){
		if(currentActiveStage!=null)
			currentActiveStage.stop(); 
		Stage stage = getStage(id);
		if(stage!=null){
			stage.start();
			currentActiveStage = stage;
		}
	}
	
	public static void stopStage(String id){
		Stage stage = getStage(id);
		if(stage!=null){
			stage.stop();
		}
	}
	
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
	
	public static void stopStage(int index){
		if(index >=0 && index < stages.size()){
			stages.get(index).stop();
		}
	}
	
	private static void startStage(Context context, Stage targetStage){
		Intent intent = new Intent(context, targetStage.getClass());
		context.startActivity(intent);
	}
	
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
	
	public static void previous(){
		previous(false);
	}
	
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
	}
	
	public static void changeStage(Stage currentStage, Class<?> cls, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, cls);
		currentStage.startActivity(intent);
		stopStage(currentStage);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	public static void changeStage(Stage currentStage, Class<?> cls, int flag, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, cls);
		intent.addFlags(flag);
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	public static void changeStage(Stage currentStage, String StargeId, boolean isCloseCurrentStage){
		Stage stage = StageManager.getStage(StargeId);
		Intent intent = new Intent(currentStage, stage.getClass());
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}

	public void changeStage(int StargeIndex){
		
	}
	
	public static void changeStage(Stage currentStage, Stage targetStage, boolean isCloseCurrentStage){
		Intent intent = new Intent(currentStage, targetStage.getClass());
		currentStage.startActivity(intent);
		if(isCloseCurrentStage)
			currentStage.finish();
	}
	
	private static void stopStage(Stage currentStage){
		currentStage.stop();
	}
}

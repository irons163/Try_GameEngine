package com.example.try_gameengine.scene;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.try_gameengine.framework.LayerManager;

public class SceneManager {
	private List<Scene> scenes = new ArrayList<Scene>();
	private Scene currentActiveScene;
	private int currentSceneIndex;
	
	public void addScene(Scene scene){
		scenes.add(scene);
	}
	
	public List<Scene> getScenes(){
		return scenes;
	}
	
	public Scene getScene(String id){
		Scene targetScene = null;
		for(int i =0; i<scenes.size(); i++){
			Scene scene = scenes.get(i);
			if(scene.getId().equals(id)){
				targetScene = scene;
			}
		}

		return targetScene;
	}
	
	public int getSceneIndex(String id){
		int targetSceneIndex = -1;
		for(int i =0; i<scenes.size(); i++){
			Scene scene = scenes.get(i);
			if(scene.getId().equals(id)){
				targetSceneIndex = i;
			}
		}

		return targetSceneIndex;
	}
	
	public void startScene(String id){
		if(currentActiveScene!=null)
			currentActiveScene.stop(); 
		Scene scene = getScene(id);
		if(scene!=null){
			scene.start();
			currentActiveScene = scene;
		}
	}
	
	public void stopScene(String id){
		Scene scene = getScene(id);
		if(scene!=null){
			scene.stop();
		}
	}
	
	public void startScene(int index){
		boolean isNeedStopCurrentActiveScene = true;
		if(index >=0 && index < scenes.size()){
			Scene scene = scenes.get(index);
			if(scene instanceof DialogScene){
				isNeedStopCurrentActiveScene = ((DialogScene) scene).getIsNeedToStopTheActiveScene();
			}
		}
		if(currentActiveScene!=null){
			if(isNeedStopCurrentActiveScene){
				currentActiveScene.stop();
				currentActiveScene.addMode(Scene.BLOCK);
			}
		}
		if(index >=0 && index < scenes.size()){
			Scene scene = scenes.get(index);
			
//			if(!(scene instanceof DialogScene)){
				LayerManager.setLayerBySenceIndex(index);
				scene.setLayerLevel(index);
//			}
			scene.start();
			currentActiveScene = scene;
			currentSceneIndex = index;
		}
	}
	
	public void startLastScene(){
		startScene(scenes.size()-1);
	}
	
	public void stopScene(int index){
		if(index >=0 && index < scenes.size()){
			scenes.get(index).stop();
		}
	}
	
	public void next(){
		currentSceneIndex++;
		if(currentActiveScene!=null)
			currentActiveScene.stop(); 
		if(currentSceneIndex == scenes.size()){
			currentSceneIndex = 0;
		}
		Scene scene = scenes.get(currentSceneIndex);
		scene.start();
		currentActiveScene = scene;
	}
	
	public void previousWithCycle(){
		currentSceneIndex--;
		if(currentActiveScene!=null){
			currentActiveScene.stop();
			if(currentActiveScene instanceof DialogScene)
				currentActiveScene.finish();
		}
		if(currentSceneIndex == -1){
			currentSceneIndex = scenes.size()-1;
		}
		Scene scene = scenes.get(currentSceneIndex);
		if(!(scene instanceof DialogScene)){
			LayerManager.setLayerBySenceIndex(currentSceneIndex);
		}
		if(currentActiveScene instanceof DialogScene){
			int savedMode = scene.getMode();
			scene.setMode(Scene.RESUME_WITHOUT_SET_VIEW);
			scene.start();
			scene.setMode(savedMode);
			scene.removeMode(Scene.BLOCK);
		}else{
			scene.start();
		}
		
		currentActiveScene = scene;
	}
	
	/**
	 * @return if false, there is not previous scene. The current scene is the first scene in scene manager.
	 * Otherwise, return true.
	 */
	public boolean previous(){
		if(currentSceneIndex==0){
			if(currentActiveScene!=null){
				currentActiveScene.stop();
				scenes.remove(currentActiveScene);
				currentActiveScene.finish();
			}
			return false;
		}else{
			previousWithCycle();
			return true;
		}
	}
	
	public void stopAllScenes(){
		for(Scene scene : scenes){
			scene.stop();
		}
	}
	
	public void removeScene(Scene scene){
		scenes.remove(scene);
	}
	
	public void removeScene(int index){
		scenes.remove(index);
	}
	
	//remove scene but not destroy, if you want add it back.
	public void removeSceneButNotDestroy(Scene scene){
		scenes.remove(scene);
		scene.finish();
	}
	
	public void removeSceneButNotDestroy(int index){
		scenes.remove(index).finish();
	}
	
	public void removeAllScenes(){
		for(Scene scene : scenes){
			scene.finish();
		}
		scenes.clear();
	}
}

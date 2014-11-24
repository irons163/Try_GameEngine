package com.example.try_gameengine.scene;

import java.util.ArrayList;
import java.util.List;

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
		if(currentActiveScene!=null)
			currentActiveScene.stop(); 
		if(index >=0 && index < scenes.size()){
			Scene scene = scenes.get(index);
			LayerManager.setLayerBySenceIndex(index);
			scene.start();
			currentActiveScene = scene;
			currentSceneIndex = index;
		}
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
	
	public void previous(){
		currentSceneIndex--;
		if(currentActiveScene!=null)
			currentActiveScene.stop(); 
		if(currentSceneIndex == -1){
			currentSceneIndex = scenes.size()-1;
		}
		Scene scene = scenes.get(currentSceneIndex);
		LayerManager.setLayerBySenceIndex(currentSceneIndex);
		scene.start();
		currentActiveScene = scene;
	}
	
	public void stopAllScenes(){
		for(Scene scene : scenes){
			scene.stop();
		}
	}
}

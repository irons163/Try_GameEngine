package com.example.try_gameengine.scene;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;

import com.example.try_gameengine.framework.LayerManager;

public class SceneManager {
	private List<Scene> scenes = new ArrayList<Scene>();
	private Scene currentActiveScene;
	private int currentSceneIndex;
	private Map<SceneClassInfo, Class<? extends Scene>> sceneClassMap;
	
	class SceneClassInfo{
		private Context context;
		private String id;
		private int sceneLayerLevel = -1;
		private int mode = -1;
		private Object obj;
		
		
		public Context getContext() {
			return context;
		}
		public void setContext(Context context) {
			this.context = context;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public int getSceneLayerLevel() {
			return sceneLayerLevel;
		}
		public int getMode() {
			return mode;
		}
		public void setSceneLayerLevel(int sceneLayerLevel) {
			this.sceneLayerLevel = sceneLayerLevel;
		}
		public void setMode(int mode) {
			this.mode = mode;
		}	
	}
	
	public void addScene(Scene scene){
		scenes.add(scene);
		if(scene.sceneLayerLevel<0){
			LayerManager.setLayerBySenceIndex(scenes.size()-1);
			scene.setLayerLevel(scenes.size()-1);
		}
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id){
		addScene(sceneClass, context, id, scenes.size(), Scene.RESTART);
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id, int sceneLayerLevel){
		addScene(sceneClass, context, id, scenes.size(), Scene.RESTART);
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id, int sceneLayerLevel, int mode){
		if(sceneClassMap==null)
			sceneClassMap = new HashMap<SceneManager.SceneClassInfo, Class<? extends Scene>>();
		SceneClassInfo sceneClassInfo = new SceneClassInfo();
		sceneClassInfo.setContext(context);
		sceneClassInfo.setId(id);
		sceneClassInfo.setSceneLayerLevel(sceneLayerLevel);
		sceneClassInfo.setMode(mode);
		sceneClassMap.put(sceneClassInfo, sceneClass);
	}
	
	public List<Scene> getScenes(){
		return scenes;
	}
	
	public Scene getScene(String id){
		Scene targetScene = null;
		for(int i =0; i<scenes.size(); i++){
			Scene scene = scenes.get(i);
			if(scene.getId()!=null && scene.getId().equals(id)){
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
		startScene(index, null);
	}
	
	public void startScene(int index, Object objForSendToScene){
		boolean isNeedStopCurrentActiveScene = true;
		Entry<SceneClassInfo, Class<? extends Scene>> sceneClassForStart = null;
		if(sceneClassMap!=null)
		for(Entry<SceneClassInfo, Class<? extends Scene>> sceneClass : sceneClassMap.entrySet()){
			
			if(index == sceneClass.getKey().getSceneLayerLevel()){
				Context context = sceneClass.getKey().getContext();
				String id = sceneClass.getKey().getId();
				int sceneLayerLevel = sceneClass.getKey().getSceneLayerLevel();
				int mode = sceneClass.getKey().getMode();
				Scene scene = null;
				for(int i = 0; i < 3; i++){
					try {
						if(i==0){
							scene = sceneClass.getValue().getConstructor(Context.class, String.class, Integer.class, int.class).newInstance(context, id, sceneLayerLevel, mode);
						}else if(i==1){
							scene = sceneClass.getValue().getConstructor(Context.class, String.class, int.class).newInstance(context, id, sceneLayerLevel);
						}else{
							scene = sceneClass.getValue().getConstructor(Context.class, String.class).newInstance(context, id);
						}
						
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(scene!=null)
						break;
				}

				if(scene==null)
					throw new RuntimeException();
				scenes.add(index, scene);		
				sceneClassForStart = sceneClass;
			}
		}
		if(sceneClassForStart!=null)
			sceneClassMap.remove(sceneClassForStart.getKey());
		
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
			LayerManager.setLayerBySenceIndex(index);
			scene.setLayerLevel(index);

			scene.startWithObj(objForSendToScene);
//			scene.start();
			currentActiveScene = scene;
			currentSceneIndex = index;
		}
	}
	
	public void startLastScene(){
		startScene(scenes.size()-1);
	}
	
	public void startLastScene(Object objForSendToScene){
		startScene(scenes.size()-1, objForSendToScene);
	}
	
	public void stopScene(int index){
		if(index >=0 && index < scenes.size()){
			scenes.get(index).stop();
		}
	}
	
	public void nextWithCycle(){
		nextWithCycle(null);
	}
	
	public void nextWithCycle(Object objForSendToScene){
		currentSceneIndex++;
//		if(currentActiveScene!=null)
//			currentActiveScene.stop(); 
		if(currentSceneIndex == scenes.size()){
			if(sceneClassMap!=null && sceneClassMap.size()==0){
				currentSceneIndex = 0;
			}
		}
		
		startScene(currentSceneIndex, objForSendToScene);
//		Scene scene = scenes.get(currentSceneIndex);
//		scene.startWithObj(objForSendToScene);
////		scene.start();
//		currentActiveScene = scene;
	}
	
	public boolean next(){
		return next(null);
	}
	
	public boolean next(Object objForSendToScene){
		if(currentSceneIndex==scenes.size()-1 && (sceneClassMap==null || sceneClassMap.size()==0)){
			return false;
		}else{
			nextWithCycle(objForSendToScene);
			return true;
		}
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
		scene.finish();
	}
	
	public void removeScene(int index){
		scenes.remove(index).finish();
	}
	
	//remove scene but not destroy, if you want add it back.
	public void removeSceneButNotDestroy(Scene scene){
		scenes.remove(scene);
		
	}
	
	public void removeSceneButNotDestroy(int index){
		scenes.remove(index);
	}
	
	public void removeAllScenes(){
		for(Scene scene : scenes){
			scene.finish();
		}
		scenes.clear();
	}
	
	public Scene getCurrentActiveScene(){
		return currentActiveScene;
	}
}

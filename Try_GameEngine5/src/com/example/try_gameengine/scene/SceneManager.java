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
	private int nextSceneIndexForAdd = currentSceneIndex;
	
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
			LayerManager.getInstance().setLayerBySenceIndex(nextSceneIndexForAdd);
			scene.setLayerLevel(nextSceneIndexForAdd);
			nextSceneIndexForAdd = nextSceneIndexForAdd+1;
		}else{
			nextSceneIndexForAdd = scene.getLayerLevel()+1;
		}
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id){
		addScene(sceneClass, context, id, nextSceneIndexForAdd, Scene.RESTART);
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id, int sceneLayerLevel){
		addScene(sceneClass, context, id, nextSceneIndexForAdd, Scene.RESTART);
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
		nextSceneIndexForAdd = sceneLayerLevel+1;
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
			if(scene.getId()!=null && scene.getId().equals(id)){
				targetSceneIndex = scene.getLayerLevel();
			}
		}

		return targetSceneIndex;
	}
	
	private Scene createScene(int index){
		Entry<SceneClassInfo, Class<? extends Scene>> sceneClassForStart = null;
		Scene scene = null;
		if(sceneClassMap!=null)
		for(Entry<SceneClassInfo, Class<? extends Scene>> sceneClass : sceneClassMap.entrySet()){
			
			if(index == sceneClass.getKey().getSceneLayerLevel()){
				Context context = sceneClass.getKey().getContext();
				String id = sceneClass.getKey().getId();
				int sceneLayerLevel = sceneClass.getKey().getSceneLayerLevel();
				int mode = sceneClass.getKey().getMode();
				
				LayerManager.getInstance().setLayerBySenceIndex(index);
				
				for(int i = 0; i < 3; i++){
					try {
						if(i==0){
							scene = sceneClass.getValue().getConstructor(Context.class, String.class, int.class, int.class).newInstance(context, id, sceneLayerLevel, mode);
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
				scenes.add(scene);
				sceneClassForStart = sceneClass;
			}
		}
		if(sceneClassForStart!=null)
			sceneClassMap.remove(sceneClassForStart.getKey());
		
		if(scene == null){
			for(Scene sceneExist : scenes){
				if(index == sceneExist.getLayerLevel()){
					scene = sceneExist;
					break;
				}
			}
		}
		
		return scene;
	}
	
	public void startScene(String id){
		if(currentActiveScene!=null)
			currentActiveScene.stop(); 
		int index = getSceneIndex(id);
		startScene(index);
	}
	
	public void stopScene(String id){
		Scene scene = getScene(id);
		if(scene!=null){
			scene.stop();
		}
	}
	
	public boolean startScene(int index){
		return startScene(index, null);
	}
	
	public boolean startScene(int index, Object objForSendToScene){
		boolean isNeedStopCurrentActiveScene = true;
		Scene scene = createScene(index);
		
		if(scene == null)
			return false;
		
		if(scene instanceof DialogScene){
			isNeedStopCurrentActiveScene = ((DialogScene) scene).getIsNeedToStopTheActiveScene();
		}

		if(currentActiveScene!=null){
			if(isNeedStopCurrentActiveScene){
				currentActiveScene.stop();
				currentActiveScene.addMode(Scene.BLOCK);
			}
		}

		LayerManager.getInstance().setLayerBySenceIndex(index);
		scene.startWithObj(objForSendToScene);
		currentActiveScene = scene;
		currentSceneIndex = index;
		
		return true;
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
		nextWithCycle(true, null);
	}
	
	public void nextWithCycle(boolean isCycle, Object objForSendToScene){
		int currentActiveSceneOrderInScenes = 0;
		for(int i = 0; i < scenes.size(); i++){
			if(scenes.get(i).equals(currentActiveScene)){
				currentActiveSceneOrderInScenes = i;
				break;
			}
		}
		
		currentActiveSceneOrderInScenes++;
		if(currentActiveSceneOrderInScenes == scenes.size()){
			currentActiveSceneOrderInScenes = 0;
		}
		
		Scene scene = scenes.get(currentActiveSceneOrderInScenes);
		scene.startWithObj(objForSendToScene);
		currentActiveScene = scene;
		currentSceneIndex = scene.getLayerLevel();
	}
	
	public boolean next(){
		return next(null);
	}
	
	public boolean next(Object objForSendToScene){
		if(currentSceneIndex==scenes.size()-1){
			return false;
		}else{
			nextWithCycle(false, objForSendToScene);
			return true;
		}
	}
	
	public boolean startSceneWithNextSceneIndexWithCycle(boolean isCycle, Object objForSendToScene){
		Scene scene = null;
		for(int i = 0; i < nextSceneIndexForAdd; i++){
			currentSceneIndex++;
			if(currentSceneIndex == nextSceneIndexForAdd){
				if(!isCycle){
					currentSceneIndex = nextSceneIndexForAdd-1;
					break;
				}
				currentSceneIndex = 0;
			}
			
			scene = createScene(currentSceneIndex);
			
			if(scene!=null)
				break;
		}
		
		if(scene==null)
			return false;
		
		if(currentActiveScene!=null){
			currentActiveScene.stop();
			if(currentActiveScene instanceof DialogScene)
				currentActiveScene.finish();
		}
		
		LayerManager.getInstance().setLayerBySenceIndex(currentSceneIndex);
		scene.startWithObj(objForSendToScene);
		currentActiveScene = scene;
		
		return true;
	}
	
	public boolean startSceneWithNextSceneIndex(){
		return startSceneWithNextSceneIndex(null);
	}
	
	public boolean startSceneWithNextSceneIndex(Object objForSendToScene){
		if(currentSceneIndex==nextSceneIndexForAdd-1){
			return false;
		}else{
			if(startSceneWithNextSceneIndexWithCycle(false, objForSendToScene))
				return true;
			else{
				if(currentActiveScene!=null){
					currentActiveScene.stop();
					scenes.remove(currentActiveScene);
					currentActiveScene.finish();
					currentActiveScene = null;
				}
				return false;
			}
		}
	}
	
	public void previousWithCycle(){
		int currentActiveSceneOrderInScenes = 0;
		for(int i = 0; i < scenes.size(); i++){
			if(scenes.get(i).equals(currentActiveScene)){
				currentActiveSceneOrderInScenes = i;
				break;
			}
		}
		
		currentActiveSceneOrderInScenes--;
		
		if(currentActiveScene!=null){
			currentActiveScene.stop();
			if(currentActiveScene instanceof DialogScene)
				currentActiveScene.finish();
		}
		if(currentActiveSceneOrderInScenes == -1){
			currentActiveSceneOrderInScenes = scenes.size()-1;
		}
		Scene scene = scenes.get(currentActiveSceneOrderInScenes);
//		if(!(scene instanceof DialogScene)){
			LayerManager.getInstance().setLayerBySenceIndex(currentActiveSceneOrderInScenes);
//		}
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
		currentSceneIndex = scene.getLayerLevel();
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
				currentActiveScene = null;
			}
			return false;
		}else{
			previousWithCycle();
			return true;
		}
	}
	
	public boolean startSceneWithPreviousSceneIndexWithCycle(boolean isCycle){
		if(currentActiveScene!=null){
			currentActiveScene.stop();
			if(currentActiveScene instanceof DialogScene)
				currentActiveScene.finish();
		}
		
		Scene scene = null;
		for(int i = 0; i < nextSceneIndexForAdd; i++){
			currentSceneIndex--;
			if(currentSceneIndex == -1){
				if(!isCycle){
					currentSceneIndex = 0;
					break;
				}
				currentSceneIndex = nextSceneIndexForAdd-1;
			}
			
			scene = createScene(currentSceneIndex);
			
			if(scene!=null)
				break;
		}
		
		if(scene==null)
			return false;
		
		LayerManager.getInstance().setLayerBySenceIndex(currentSceneIndex);
			
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
		
		return true;
	}
	
	public boolean startSceneWithPreviousSceneIndex(){
		if(currentSceneIndex==0){
			if(currentActiveScene!=null){
				currentActiveScene.stop();
				scenes.remove(currentActiveScene);
				currentActiveScene.finish();
				currentActiveScene = null;
			}
			return false;
		}else{
			if(startSceneWithPreviousSceneIndexWithCycle(false))
				return true;
			else{
				if(currentActiveScene!=null){
					currentActiveScene.stop();
					scenes.remove(currentActiveScene);
					currentActiveScene.finish();
					currentActiveScene = null;
				}
				return false;
			}
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

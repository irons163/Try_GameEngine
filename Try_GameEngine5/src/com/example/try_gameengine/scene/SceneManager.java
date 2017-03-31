package com.example.try_gameengine.scene;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	private static class SceneManagerHolder {
		public static SceneManager SceneManager = new SceneManager();
	}

	public static SceneManager getInstance() {
		return SceneManagerHolder.SceneManager;
	}
	
	private SceneManager() {
		
	}
	
	@Deprecated
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
	
	public void addScene(SceneBuilder sceneBuilder){
		int sceneIndex = sceneBuilder.getSceneIndex();
		Scene scene;
		if(sceneIndex < 0){
			LayerManager.getInstance().setLayerBySenceIndex(nextSceneIndexForAdd);
//			scene.setLayerLevel(nextSceneIndexForAdd);
			scene = sceneBuilder.createScene(nextSceneIndexForAdd);
			scene.setLayerLevel(nextSceneIndexForAdd);
			nextSceneIndexForAdd = nextSceneIndexForAdd+1;
		}else{
			LayerManager.getInstance().setLayerBySenceIndex(sceneIndex);
			scene = sceneBuilder.createScene(sceneIndex);
			scene.setLayerLevel(sceneIndex);
			nextSceneIndexForAdd = sceneIndex+1;
		}
		
		scenes.add(scene);
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id){
		addScene(sceneClass, context, id, nextSceneIndexForAdd, Scene.RESTART);
	}
	
	public void addScene(Class<? extends Scene> sceneClass, Context context, String id, int sceneLayerLevel){
//		addScene(sceneClass, context, id, nextSceneIndexForAdd, Scene.RESTART);
		addScene(sceneClass, context, id, sceneLayerLevel, Scene.RESTART);
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
	
	public Scene getSceneAt(int index){
		Scene targetScene = null;
		for(int i =0; i<scenes.size(); i++){
			Scene scene = scenes.get(i);
			if(scene.getLayerLevel() == index){
				targetScene = scene;
				break;
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
							if(scene!=null)
								scene.setLayerLevel(sceneLayerLevel);
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
	
	/*
	 * Previous.
	 */
	/**
	 * @return if false, there is not previous scene. The current scene is the first scene in scene manager.
	 * Otherwise, return true.
	 */
	public boolean previousWithExistedScenes(){
		return previousWithExistedScenes(null);
	}
	
	public boolean previousWithExistedScenes(Object objForSendToScene){
		if(currentSceneIndex==0){
			return false;
		}else{
			return previousWithExistedScenesCycle(false, objForSendToScene);
		}
	}
	
	public boolean previousWithExistedScenesCycle(){
		return previousWithExistedScenesCycle(true, null);
	}
	
//	public boolean previousWithExistedScenes(){
//		if(currentSceneIndex==0){
//			if(currentActiveScene!=null){
//				currentActiveScene.stop();
//				scenes.remove(currentActiveScene);
//				currentActiveScene.finish();
//				currentActiveScene = null;
//			}
//			return false;
//		}else{
//			previousWithExistedScenesCycle();
//			return true;
//		}
//	}
	
	public boolean previousWithExistedScenesCycle(boolean isCycle, Object objForSendToScene){
		int currentActiveSceneOrderInScenes = 0;
		
		boolean isFind = false;
		for(int i = 0; i < scenes.size(); i++){
			if(scenes.get(i).equals(currentActiveScene)){
				currentActiveSceneOrderInScenes = i;
				isFind = true;
				break;
			}
		}
		
		if(!isFind)
			throw new RuntimeException("The current scene is not exist in the exsitedScenes.");
		
		currentActiveSceneOrderInScenes--;
		
		if(currentActiveScene!=null){
			currentActiveScene.stop();
			if(currentActiveScene instanceof DialogScene)
				currentActiveScene.finish();
		}
		if(currentActiveSceneOrderInScenes == -1){
			if(isCycle)
				currentActiveSceneOrderInScenes = scenes.size()-1;
			else
				return false;
		}
		Scene scene = scenes.get(currentActiveSceneOrderInScenes);
//		if(!(scene instanceof DialogScene)){
			LayerManager.getInstance().setLayerBySenceIndex(scene.getLayerLevel());
//		}
		if(currentActiveScene instanceof DialogScene){
			int savedMode = scene.getMode();
			scene.setMode(Scene.RESUME_WITHOUT_SET_VIEW);
			scene.startWithObj(objForSendToScene);
			scene.setMode(savedMode);
			scene.removeMode(Scene.BLOCK);
		}else{
			scene.startWithObj(objForSendToScene);
		}
		
		currentActiveScene = scene;
		currentSceneIndex = scene.getLayerLevel();
		
		return true;
	}
	
	/*
	 * Next.
	 */
	public boolean nextWithExistedScenes(){
		return nextWithExistedScenes(null);
	}
	
	public boolean nextWithExistedScenes(Object objForSendToScene){
		if(currentSceneIndex==scenes.size()-1){
			return false;
		}else{
			nextWithExistedScenesCycle(false, objForSendToScene);
			return true;
		}
	}
	
	public boolean nextWithExistedScenesCycle(){
		return nextWithExistedScenesCycle(true, null);
	}
	
	public boolean nextWithExistedScenesCycle(boolean isCycle, Object objForSendToScene){
		int currentActiveSceneOrderInScenes = 0;
		
		boolean isFind = false;
		for(int i = 0; i < scenes.size(); i++){
			if(scenes.get(i).equals(currentActiveScene)){
				currentActiveSceneOrderInScenes = i;
				isFind = true;
				break;
			}
		}
		
		if(!isFind)
			throw new RuntimeException("The current scene is not exist in the exsitedScenes.");
		
		currentActiveSceneOrderInScenes++;
		if(currentActiveSceneOrderInScenes == scenes.size()){
			if(isCycle)
				currentActiveSceneOrderInScenes = 0;
			else
				return false;
		}
		
		Scene scene = scenes.get(currentActiveSceneOrderInScenes);
		
		boolean isNeedStopCurrentActiveScene = true;
		if(scene instanceof DialogScene){
			isNeedStopCurrentActiveScene = ((DialogScene) scene).getIsNeedToStopTheActiveScene();
		}

		if(currentActiveScene!=null){
			if(isNeedStopCurrentActiveScene){
				currentActiveScene.stop();
				currentActiveScene.addMode(Scene.BLOCK);
			}
		}

		LayerManager.getInstance().setLayerBySenceIndex(scene.getLayerLevel());
		
		scene.startWithObj(objForSendToScene);
		currentActiveScene = scene;
		currentSceneIndex = scene.getLayerLevel();
		
		return true;
	}
	
	/*
	 * Start Scene.
	 */
	public boolean startNextScene(){
		return startNextScene(null);
	}
	
	public boolean startNextScene(Object objForSendToScene){
		if(currentSceneIndex==nextSceneIndexForAdd-1){
			return false;
		}else{
			if(startNextSceneWithCycle(false, objForSendToScene))
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
	
	public boolean startNextSceneWithCycle(boolean isCycle, Object objForSendToScene){
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
	
	public boolean startPreviousSceneWithCycle(boolean isCycle){
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
	
	public boolean startPreviousScene(){
		if(currentSceneIndex==0){
			if(currentActiveScene!=null){
				currentActiveScene.stop();
				scenes.remove(currentActiveScene);
				currentActiveScene.finish();
				currentActiveScene = null;
			}
			return false;
		}else{
			if(startPreviousSceneWithCycle(false))
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

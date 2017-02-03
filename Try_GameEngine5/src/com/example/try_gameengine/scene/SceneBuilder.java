package com.example.try_gameengine.scene;

public abstract class SceneBuilder{
//	private Scene scene;
	private int sceneIndex = -1;
	
	public SceneBuilder() {
		
	}
	
	public SceneBuilder(int sceneIndex) {
		// TODO Auto-generated constructor stub
		this.sceneIndex = sceneIndex;
//		LayerManager.getInstance().setLayerBySenceIndex(sceneIndex);
//		scene = createScene(sceneIndex);
	}
	
	public abstract Scene createScene(int sceneIndex);
	
//	Scene getScene(){
//		return scene;
//	}
	
	int getSceneIndex() {
		return sceneIndex;
	}
}

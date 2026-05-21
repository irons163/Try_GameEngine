package com.example.try_gameengine.scene

abstract class SceneBuilder {
    //	Scene getScene(){
    //		return scene;
    //	}
    //	private Scene scene;
    var sceneIndex: Int = -1
        private set

    constructor()

    constructor(sceneIndex: Int) {
        // TODO Auto-generated constructor stub
        this.sceneIndex = sceneIndex
        //		LayerManager.getInstance().setLayerBySenceIndex(sceneIndex);
//		scene = createScene(sceneIndex);
    }

    abstract fun createScene(sceneIndex: Int): Scene?
}

package com.example.try_gameengine.scene;

import com.example.try_gameengine.Camera.Camera;


public class TransformSceneEffect {
	Camera camera;
	public void shakeSceneEffect(Scene scene){
	 camera = scene.getCamera();
		
		
	}
	
	public void process(){
		float dx = 0;
		if(dx<10){
			camera.translate(dx, 0);
		}else{
			
		}
	}
}

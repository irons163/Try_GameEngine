package com.example.try_gameengine.utils;

import android.graphics.PointF;

public class SpriteDetectAreaBehavior implements ISpriteDetectAreaBehavior{
	private DetectArea detectArea;
	
	@Override
	public void setSpriteDetectArea(DetectArea detectArea) {
		// TODO Auto-generated method stub
		this.detectArea = detectArea;
	}

	@Override
	public DetectArea getSpriteDetectArea() {
		// TODO Auto-generated method stub
		return detectArea;
	}

	@Override
	public void updateSpriteDetectAreaCenter(PointF center) {
		// TODO Auto-generated method stub
		DetectArea successor = detectArea;
		do{
			successor.setCenter(center);
			successor = successor.getSuccessor();
		}while(successor!=null);	
	}

	@Override
	public boolean detect(DetectArea otherDetectArea) {
		// TODO Auto-generated method stub
		return detectArea.detect(new DetectAreaRequest(otherDetectArea));
	}

	@Override
	public boolean detect(IDetectAreaRequest requestDetectArea) {
		// TODO Auto-generated method stub
		return detectArea.detect(requestDetectArea);
	}
	
	public void setSpriteDetectAreaListener(ISpriteDetectAreaListener spriteDetectAreaListener){
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
	}
	
	public ISpriteDetectAreaListener getSpriteDetectAreaListener(){
		return detectArea.getSpriteDetectAreaListener();
	}

}

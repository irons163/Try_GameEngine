package com.example.try_gameengine.utils;

import android.graphics.PointF;

/**
 * {@code SpriteDetectAreaBehavior} has a set of DetectArea methods. It can be detected the chain of member DetectArea.
 * @author irons
 *
 */
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
	
	/**
	 * set {@code SpriteDetectAreaListener} for the member DetectArea.
	 * @param spriteDetectAreaListener
	 */
	public void setSpriteDetectAreaListener(ISpriteDetectAreaListener spriteDetectAreaListener){
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
	}
	
	/**
	 * get {@code SpriteDetectAreaListener} for the member DetectArea.
	 * @return {@code SpriteDetectAreaListener}
	 */
	public ISpriteDetectAreaListener getSpriteDetectAreaListener(){
		return detectArea.getSpriteDetectAreaListener();
	}

}

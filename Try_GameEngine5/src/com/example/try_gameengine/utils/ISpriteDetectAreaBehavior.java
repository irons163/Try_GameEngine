package com.example.try_gameengine.utils;

import android.graphics.PointF;

public interface ISpriteDetectAreaBehavior {
	public void setSpriteDetectArea(DetectArea detectArea);
	public DetectArea getSpriteDetectArea();
	public void updateSpriteDetectAreaCenter(PointF center);
	public boolean detect(DetectArea requestDetectArea);
	public boolean detect(IDetectAreaRequest requestDetectArea);
}
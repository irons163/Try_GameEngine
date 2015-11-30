package com.example.try_gameengine.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;

public interface ISpriteDetectAreaHandler {
//	private ISpriteDetectAreaBehavior spriteDetectAreaBehavior;
	public void setSpriteDetectAreaBehavior(ISpriteDetectAreaBehavior spriteDetectAreaBehavior);
	
	public ISpriteDetectAreaBehavior getSpriteDetectAreaBehavior();
	
//	public void addSuccessor();
}


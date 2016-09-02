package com.example.try_gameengine.framework;

import android.graphics.Bitmap;

public class HUDLayer extends Layer{

	public HUDLayer(){
		super();
	}
	
	@Override
	public void setzPosition(int zPosition) {
		throw new RuntimeException("HUD Layer not support the setzPosition method");
	}
	
	@Override
	public void setAutoAdd(boolean autoAdd) {
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}
	
	@Override
	public void setAutoAdd(boolean autoAdd, int sceneLayerLevel) {
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}
	
	@Override
	public void addWithLayerLevelIncrease(ILayer layer) {
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}
	
	@Override
	public void addWithLayerLevelIncrease(ILayer layer, int increaseNum) {
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}

	@Override
	public void addWithOutLayerLevelIncrease(ILayer layer){
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}
	
	@Override
	public void addWithLayerLevel(ILayer layer, int layerLevel) {
		throw new RuntimeException("HUD Layer not support the setAutoAdd method");
	}
}

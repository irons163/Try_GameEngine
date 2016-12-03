package com.example.try_gameengine.framework;

/**
 * {@code HUDLayer} is a layer that display on the front of screen.
 * @author irons
 *
 */
public class HUDLayer extends Layer{

	/**
	 * Constructor. 
	 */
	public HUDLayer(){
		super();
		LayerManager.getInstance().getInstance().addHUDLayer(this); //Add Layer in the LayerManager.getInstance() HUD.
	}
	
	@Override
	public void setParent(ILayer parent) {
		// TODO Auto-generated method stub
		throw new RuntimeException("HUD Layer not support the setParent method");
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

package com.example.try_gameengine.framework;

public abstract class ALayerComponent {
	
	public void add(ALayerComponent layerComponent){
		throw new UnsupportedOperationException();
	}
	
	public void remove(ALayerComponent layerComponent){
		throw new UnsupportedOperationException();
	}
	
	public ALayerComponent getChild(int i){
		throw new UnsupportedOperationException();
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public String getDescription(ALayerComponent layerComponent){
		throw new UnsupportedOperationException();
	}
	
	public double getPrice(){
		throw new UnsupportedOperationException();
	}
	
	public boolean isVegetarian(ALayerComponent layerComponent){
		throw new UnsupportedOperationException();
	}
	
	public void print(){
		throw new UnsupportedOperationException();
	}

}

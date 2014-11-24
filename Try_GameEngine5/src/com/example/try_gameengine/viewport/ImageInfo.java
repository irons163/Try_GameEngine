package com.example.try_gameengine.viewport;

public class ImageInfo {
	int x = 0;
	int y = 0;
	int w = 0;
	int h = 0;
	int layer = 0;
	
	public ImageInfo(){
		
	}
	
	public ImageInfo(int x, int y, int w, int h, int layer){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.layer = layer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	
}

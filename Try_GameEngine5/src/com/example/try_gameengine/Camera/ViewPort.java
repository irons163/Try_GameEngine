package com.example.try_gameengine.Camera;

import android.graphics.RectF;

public class ViewPort {
	private RectF viewportRectF = new RectF();
	private float scale = 1.0f;
	private float rotation;

	public float getX() {
		return viewportRectF.left;
	}

	public float getY() {
		return viewportRectF.top;
	}

	public float getWidth() {
		return viewportRectF.width();
	}

	public float getHeight() {
		return viewportRectF.height();
	}

	public void setX(float x) {
		viewportRectF.left = x;
	}

	public void setY(float y) {
		viewportRectF.top = y;
	}

	public void setWidth(float width) {
		viewportRectF.right = viewportRectF.left + width;
	}

	public void setHeight(float height) {
		viewportRectF.bottom = viewportRectF.top + height;
	}
	
	public void setXY(float x, float y){
		setX(x);
		setY(y);
	}
	
	public void setWH(float w, float h){
		setWidth(w);
		setHeight(h);
	}
	
	public void setXYWH(float x, float y, float w, float h){
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
	}
	
	// not implement yet
	public float getScale() {
		return scale;
	}
	// not implement yet
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setViewPortRectF(RectF rectF){
		viewportRectF = rectF;
	}
	
	public RectF getViewPortRectF(){
		return viewportRectF;
	}
}

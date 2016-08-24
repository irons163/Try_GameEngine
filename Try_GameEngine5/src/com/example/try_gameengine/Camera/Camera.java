package com.example.try_gameengine.Camera;

import com.example.try_gameengine.framework.ILayer;

import android.graphics.Matrix;
import android.graphics.RectF;

public class Camera extends ACamera{
	private Matrix matrix = new Matrix();
	private float locationX, locationY;
	private float rotation;
	private float scale = 1.0f;
	private float dx, dy;
	private ViewPort viewPort;
	
	public Camera() {
		// TODO Auto-generated constructor stub
	}
	
	public Camera(float locationX, float locationY){
		this.locationX = locationX;
		this.locationY = locationY;
	}
	
	public void setLocation(float locationX, float locationY){
		this.locationX = locationX;
		this.locationY = locationY;
	}
	
	@Override
	public void rotation(float rotation) {
		// TODO Auto-generated method stub
		this.rotation = rotation;
		resetMatrix();
	}
	@Override
	public void translate(float dx, float dy) {
		// TODO Auto-generated method stub
		this.dx = dx;
		this.dy = dy;
		resetMatrix();
	}
	@Override
	public void zoom(float scale) {
		// TODO Auto-generated method stub
		this.scale = scale;
		resetMatrix();
	}
	@Override
	public void bindLayerXY() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void bindLayerX() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void bindLayerY() {
		// TODO Auto-generated method stub
		
	}
	
	ILayer layer;
	
	public void bindLayer(ILayer layer) {
		// TODO Auto-generated method stub
		this.layer = layer;
		setViewPort(layer.getX(), layer.getY(), viewPort.getWidth(), viewPort.getHeight());
	}
	
	@Override
	public void setIsAutoStopOnBound() {
		// TODO Auto-generated method stub
		
	}
	
	public void setViewPort(float x, float y, float w ,float h){
		if(viewPort == null){
			viewPort = new ViewPort();
		}
		
		viewPort.setXYWH(x, y, w, h);
	}
	
	public RectF getViewPortRectF(){
		if(viewPort != null){
			return viewPort.getViewPortRectF();
		}
		return null;
	}
	
	private void resetMatrix(){
		matrix.reset();
		matrix.postScale(scale, scale, locationX, locationY);
		matrix.postTranslate(dx, dy);
		matrix.postRotate(rotation, locationX, locationY);
	}
	
	public Matrix getMatrix(){
		return matrix; 
	}
}

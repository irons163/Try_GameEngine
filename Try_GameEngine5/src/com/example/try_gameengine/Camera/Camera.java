package com.example.try_gameengine.Camera;

import com.example.try_gameengine.framework.ILayer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class Camera extends ACamera{
	private Matrix matrix = new Matrix();
//	private float locationX, locationY;
	private float rotation;
//	private float scale = 1.0f;
	private float offsetX, offsetY;
	private ViewPort viewPort;
	
	private RectF cameraRange = new RectF();
	private RectF cameraRangeOri = new RectF();
	private float minScaleFactor = 0.2f;
	private float maxScaleFactor = 5.0f;
	private float xscale = 1.0f;
	private float yscale = 1.0f;
	
	public Camera(float width, float height) {
		// TODO Auto-generated constructor stub
		cameraRange.right = cameraRange.left + width;
		cameraRange.bottom = cameraRange.top + height;
		cameraRangeOri.set(cameraRange);
	}
	
	public Camera(float left, float top, float width, float height){
//		this.locationX = locationX;
//		this.locationY = locationY;
		cameraRange.left = left;
		cameraRange.top = top;
		cameraRange.right = cameraRange.left + width;
		cameraRange.bottom = cameraRange.top + height;
		cameraRangeOri.set(cameraRange);
	}
	
//	public void setLocation(float locationX, float locationY){
//		this.locationX = locationX;
//		this.locationY = locationY;
//	}
	
	public void setFrame(RectF frame){
		cameraRange.set(frame);
	}
	
	public void setWH(float w, float h){
//		cameraRange.set(cameraRangeOri);
		cameraRange.right = cameraRange.left + w;
		cameraRange.bottom = cameraRange.top + h;
//		cameraRangeOri.set(cameraRange);
	}
	
	public void setCameraScaleBeforeApply(float scale){
		scale = Math.max(minScaleFactor, Math.min(scale, maxScaleFactor));
        this.setXscale(scale);
        this.setYscale(scale);
	}
	
	public void setCameraScaleBeforeApply(float scale, float locationX, float locationY){
		scale = Math.max(minScaleFactor, Math.min(scale, maxScaleFactor));
        this.setXscale(scale, locationX);
        this.setYscale(scale, locationY);
	}
	
	public void setCameraXScaleBeforeApply(float xscale){
		xscale = Math.max(minScaleFactor, Math.min(xscale, maxScaleFactor));
        this.setXscale(xscale);
	}
	
	public void setCameraXScaleBeforeApply(float xscale, float locationX){
		xscale = Math.max(minScaleFactor, Math.min(xscale, maxScaleFactor));
        this.setXscale(xscale, locationX);
	}
	
	public void setCameraYScaleBeforeApply(float yscale){
		yscale = Math.max(minScaleFactor, Math.min(yscale, maxScaleFactor));
        this.setYscale(yscale);
	}
	
	public void setCameraYScaleBeforeApply(float yscale, float locationY){
		yscale = Math.max(minScaleFactor, Math.min(yscale, maxScaleFactor));
        this.setYscale(yscale, locationY);
	}
	
	public void setCameraRotateBeforeApply(float rotation){
		this.rotation = rotation;
	}
	
	public void setCameraTranslateBeforeApply(float offsetX, float offsetY){

//		cameraRange.set(cameraRangeOri);
		cameraRange.offset(offsetX-this.offsetX, offsetY-this.offsetY);
//		cameraRangeOri.set(cameraRange);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	private void setXscale(float xscale){
		setXscale(xscale, cameraRange.centerX());
	}
	
	private void setYscale(float yscale){
		setYscale(yscale, cameraRange.centerY());
	}
	
	private void setXscale(float xscale, float locationX){
		if(xscale==0)
			return;
		cameraRange.left = locationX - ((locationX - cameraRange.left) * Math.abs(xscale/this.xscale));
		cameraRange.right = locationX + ((cameraRange.right - locationX) * Math.abs(xscale/this.xscale));
		this.xscale = xscale;
	}
	
	private void setYscale(float yscale, float locationY){
		if(yscale==0)
			return;
		cameraRange.top = locationY - ((locationY - cameraRange.top) * Math.abs(yscale/this.yscale));
		cameraRange.bottom = locationY + ((cameraRange.bottom - locationY) * Math.abs(yscale/this.yscale));
		this.yscale = yscale;
	}
	
	private float getXscale(){
		return xscale;
	}
	
	private float getYscale(){
		return yscale;
	}
	
	/*
	public void applyCamera(){
		cameraRange.right = cameraRange.left + w;
		cameraRange.bottom = cameraRange.top + h;
		
		cameraRange.offset(dx, dy);
		
		cameraRange.left = locationX - ((locationX - cameraRange.left) * Math.abs(xscale));
		cameraRange.right = locationX + ((cameraRange.right - locationX) * Math.abs(xscale));
		cameraRange.top = locationY - ((locationY - cameraRange.top) * Math.abs(yscale));
		cameraRange.bottom = locationY + ((cameraRange.bottom - locationY) * Math.abs(yscale));
	}*/
	
	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	@Override
	public void rotation(float rotation) {
		// TODO Auto-generated method stub
		setCameraRotateBeforeApply(getRotation()+rotation);
//		this.rotation += rotation;
//		resetMatrix();
//		applyCameraRotate();
	}
	@Override
	public void translate(float offsetX, float offsetY) {
		// TODO Auto-generated method stub
//		this.dx = dx;
//		this.dy = dy;
//		cameraRange.offset(dx, dy);
//		resetMatrix();
//		applyCameraTranslate();
		setCameraTranslateBeforeApply(getOffsetX() + offsetX, getOffsetY() + offsetY);
	}
	@Override
	public void zoom(float scale) {
		// TODO Auto-generated method stub
		setXscale(getXscale() * scale);
		setYscale(getYscale() * scale);
//		applyCameraScale();
//		resetMatrix();
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
	
	public void bindLayerToCameraXY(float x, float y){
		
	}
	
	public float getRotation() {
		return rotation;
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
	
//	public void applyViewPort(Canvas canvas){
//		if(viewPort == null){
//			return;
//		}
//		
//		//viewport
//		viewPort.getHeight();
////		canvas.save();
//		canvas.rotate(dx);
//		canvas.clipRect(getViewPortRectF());
//		
//		// camera
//		canvas.rotate(rotation, cameraRange.centerX(), cameraRange.centerY());
//		
//		dx = viewPort.getX() - cameraRange.left;
//		dy = viewPort.getY() - cameraRange.top;
//		canvas.translate(dx, dy);
//		float xscaleFactor = viewPort.getWidth()/cameraRange.width();
////		canvas.scale(xscaleFactor, xscaleFactor);
//		float yscaleFactor = viewPort.getHeight()/cameraRange.height();
//		canvas.scale(xscaleFactor, yscaleFactor);
//		
////		canvas.save();
//	}
	
	public void applyViewPort(Canvas canvas){
//		resetMatrix();
		//viewport
		if(viewPort!=null){
			canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//			canvas.rotate(viewPort.getRotation());
			canvas.setMatrix(viewPort.getMatrix());
			canvas.clipRect(getViewPortRectF());
			canvas.restore();
		}
//		calculateMatrix();
	}
	
	private void applyCameraSpaceScaleToViewPort(){
		if(viewPort!=null){
			float xscaleFactor = viewPort.getWidth()/cameraRange.width();
//			matrix.postScale(xscaleFactor, xscaleFactor);
			float yscaleFactor = viewPort.getHeight()/cameraRange.height();
			matrix.postScale(xscaleFactor, yscaleFactor, cameraRange.left, cameraRange.top);
		}
	}
	
	private void applyCameraSpaceRotate(){
		matrix.postRotate(-rotation, cameraRange.centerX(), cameraRange.centerY());
	}
	
	private void applyCameraSpaceTranslateToViewPort(){
		if(viewPort!=null){
			offsetX = viewPort.getX() - cameraRange.left;
			offsetY = viewPort.getY() - cameraRange.top;
		}else{
			offsetX = 0 - cameraRange.left;
			offsetY = 0 - cameraRange.top;
		}
		matrix.postTranslate(offsetX, offsetY);
	}
	
	private void applyCameraSpaceLRDirectionAndTBDirection(){
		int lrDir = xscale<0 ? -1:1;
		int tbDir = yscale<0 ? -1:1;
		matrix.postScale(lrDir, tbDir, cameraRange.centerX(), cameraRange.centerY());
	}
	
	public void applyCameraSpaceToViewPort(){
		resetMatrix();
		
		applyCameraSpaceRotate();
		
		applyCameraSpaceLRDirectionAndTBDirection();
		
		applyCameraSpaceScaleToViewPort();
		
		applyCameraSpaceTranslateToViewPort();
	}
	
	/*
	private void calculateMatrix(){
		matrix.postRotate(rotation, cameraRange.centerX(), cameraRange.centerY());
		if(viewPort!=null){
			offsetX = viewPort.getX() - cameraRange.left;
			offsetY = viewPort.getY() - cameraRange.top;
		}else{
			offsetX = 0 - cameraRange.left;
			offsetY = 0 - cameraRange.top;
		}
		matrix.postTranslate(offsetX, offsetY);
		
		if(viewPort!=null){
			float xscaleFactor = viewPort.getWidth()/cameraRange.width();
//			matrix.postScale(xscaleFactor, xscaleFactor);
			float yscaleFactor = viewPort.getHeight()/cameraRange.height();
			matrix.postScale(xscaleFactor, yscaleFactor);
		}
	}*/
	
	private void resetMatrix(){
		matrix.reset();
//		matrix.postScale(scale, scale, locationX, locationY);
//		matrix.postTranslate(dx, dy);
//		matrix.postRotate(rotation, locationX, locationY);
	}
	
	public Matrix getMatrix(){
		return matrix; 
	}
	
	/*
	public void postCameraTranslate(float dx, float dy){
		float xscaleFactor = viewPort.getWidth()/cameraRange.width();
		dx = dx * xscaleFactor;
		float yscaleFactor = viewPort.getHeight()/cameraRange.height();
		dy = dy * yscaleFactor;
		translate(dx, dy);
		matrix.postTranslate(dx, dy);
	}
	
	public void postCameraRotation(){
		matrix.postRotate(rotation, cameraRange.centerX(), cameraRange.centerY());
	}
	
//	public void postCameraScale(float scale){
//		float xscaleFactor = viewPort.getWidth()/cameraRange.width();
//		matrix.postScale(xscaleFactor, xscaleFactor);
//		float yscaleFactor = viewPort.getHeight()/cameraRange.height();
//		matrix.postScale(xscaleFactor, yscaleFactor);
//	}
	
	
	public void postCameraScale(float scaleFactor){
		float xscale = this.getXscale();
		float yscale = this.getYscale();
		
		float xyFactor = yscale/xscale;
		xscale *= scaleFactor;
		
        // Don't let the object get too small or too large.
        xscale = Math.max(minScaleFactor, Math.min(xscale, maxScaleFactor));
        yscale = xscale*xyFactor;
        this.setXscale(xscale);
        this.setYscale(yscale);
//        applyCameraScale();
		if(viewPort!=null){
			float xscaleFactor = viewPort.getWidth()/cameraRange.width();
//			matrix.postScale(xscaleFactor, xscaleFactor);
			float yscaleFactor = viewPort.getHeight()/cameraRange.height();
			matrix.postScale(xscaleFactor, yscaleFactor, cameraRange.left, cameraRange.top);
		}
	}*/
}

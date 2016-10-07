package com.example.try_gameengine.Camera;

import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * ViewPort is a display range for camera.
 * @author irons
 *
 */
public class ViewPort {
	private RectF viewportRectF = new RectF();
	private float scale = 1.0f;
	private float rotation;
	private Matrix matrix = new Matrix();

	/**
	 * get position X of view port.
	 * @return position x.
	 */
	public float getX() {
		return viewportRectF.left;
	}

	/**
	 * get position Y of view port.
	 * @return position Y.
	 */
	public float getY() {
		return viewportRectF.top;
	}

	/**
	 * get width of view port.
	 * @return view port width.
	 */
	public float getWidth() {
		return viewportRectF.width();
	}

	/**
	 * get height of view port.
	 * @return view port height.
	 */
	public float getHeight() {
		return viewportRectF.height();
	}

	/**
	 * set position x of view port.
	 * @param x
	 * 			set position x.
	 */
	public void setX(float x) {
		viewportRectF.left = x;
	}

	/**
	 * set position y of view port.
	 * @param y
	 * 			set position y.
	 */
	public void setY(float y) {
		viewportRectF.top = y;
	}

	/**
	 * set width of view port.
	 * @param width
	 * 			set width.
	 */
	public void setWidth(float width) {
		viewportRectF.right = viewportRectF.left + width;
	}

	/**
	 * set height of view port.
	 * @param height
	 * 			set height.
	 */
	public void setHeight(float height) {
		viewportRectF.bottom = viewportRectF.top + height;
	}
	
	/**
	 * set XY.
	 * @param x
	 * 			set x. 
	 * @param y 
	 * 			set y.
	 */
	public void setXY(float x, float y){
		setX(x);
		setY(y);
	}
	
	/**
	 * set WH.
	 * @param w  
	 * 			set width.
	 * @param h
	 * 			set height.
	 */
	public void setWH(float w, float h){
		setWidth(w);
		setHeight(h);
	}
	
	/**
	 * set XYWH.
	 * @param x
	 * 			set x.
	 * @param y
	 * 			set Y.
	 * @param w
	 * 			set W.
	 * @param h
	 * 			set H.
	 */
	public void setXYWH(float x, float y, float w, float h){
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
	}
	
	/*
	// not implement yet
	public float getScale() {
		return scale;
	}
	// not implement yet
	public void setScale(float scale) {
		this.scale = scale;
	}
	*/
	
	/**
	 * @return
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
		matrix.setRotate(rotation);
	}

	/**
	 * @param rectF
	 */
	public void setViewPortRectF(RectF rectF){
		viewportRectF = rectF;
	}
	
	/**
	 * get view port range rectF.
	 * @return rectF.
	 */
	public RectF getViewPortRectF(){
		return viewportRectF;
	}
	
	/**
	 * get Matrix.
	 * @return matrix.
	 */
	public Matrix getMatrix(){
		return matrix;
	}
}

package com.example.try_gameengine.Camera;

import com.example.try_gameengine.framework.ILayer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * This camera.
 * @author irons
 *
 */
public class Camera extends ACamera{
	private Matrix matrix = new Matrix();
	private float rotation;
	private float offsetX, offsetY;
	private ViewPort viewPort;
	
	private RectF cameraRange = new RectF();
	private RectF cameraRangeOri = new RectF();
	private float minScaleFactor = 0.2f;
	private float maxScaleFactor = 5.0f;
	private float xscale = 1.0f;
	private float yscale = 1.0f;
	
	/**
	 * constructor.
	 * @param width width of camera.
	 * @param height height of camera.
	 */
	public Camera(float width, float height) {
		cameraRange.right = cameraRange.left + width;
		cameraRange.bottom = cameraRange.top + height;
		cameraRangeOri.set(cameraRange);
	}
	
	/**
	 * constructor.
	 * @param left left of camearaRange.
	 * @param top of camearaRange
	 * @param width of camearaRange
	 * @param height of camearaRange.
	 */
	public Camera(float left, float top, float width, float height){
		cameraRange.left = left;
		cameraRange.top = top;
		cameraRange.right = cameraRange.left + width;
		cameraRange.bottom = cameraRange.top + height;
		cameraRangeOri.set(cameraRange);
	}
	
	/**
	 * who setFrame .
	 * @param frame
	 */
	public void setFrame(RectF frame){
		cameraRange.set(frame);
	}
	
	/**
	 * set width and set height for camera.(cameraRange)
	 * @param w 
	 * 			width of camera.
	 * @param h 
	 * 			height of camera.
	 */
	public void setWH(float w, float h){
		cameraRange.right = cameraRange.left + w;
		cameraRange.bottom = cameraRange.top + h;
	}
	
	/**
	 * set scale value for camera before apply.
	 * @param scale 
	 * 			set scale value.
	 */
	public void setCameraScaleBeforeApply(float scale){
		scale = Math.max(minScaleFactor, Math.min(scale, maxScaleFactor));
        this.setXscale(scale);
        this.setYscale(scale);
	}
	
	/** set scale value and scale position for camera.
	 * @param scale
	 * 			the scale value of camera.
	 * @param locationX
	 * 			the position for scale.
	 * @param locationY
	 * 			the position for scale.
	 */
	public void setCameraScaleBeforeApply(float scale, float locationX, float locationY){
		scale = Math.max(minScaleFactor, Math.min(scale, maxScaleFactor));
        this.setXscale(scale, locationX);
        this.setYscale(scale, locationY);
	}
	
	/**
	 * set x scale value for camera before apply.
	 * @param xscale
	 * 			set x scale for camera.
	 */
	public void setCameraXScaleBeforeApply(float xscale){
		xscale = Math.max(minScaleFactor, Math.min(xscale, maxScaleFactor));
        this.setXscale(xscale);
	}
	
	/**
	 * set scale value for camera before apply.
	 * @param xscale
	 * @param locationX
	 */
	public void setCameraXScaleBeforeApply(float xscale, float locationX){
		xscale = Math.max(minScaleFactor, Math.min(xscale, maxScaleFactor));
        this.setXscale(xscale, locationX);
	}
	
	/**
	 * @param yscale
	 */
	public void setCameraYScaleBeforeApply(float yscale){
		yscale = Math.max(minScaleFactor, Math.min(yscale, maxScaleFactor));
        this.setYscale(yscale);
	}
	
	/**
	 * @param yscale
	 * @param locationY
	 */
	public void setCameraYScaleBeforeApply(float yscale, float locationY){
		yscale = Math.max(minScaleFactor, Math.min(yscale, maxScaleFactor));
        this.setYscale(yscale, locationY);
	}
	
	/**
	 * @param rotation
	 */
	public void setCameraRotateBeforeApply(float rotation){
		this.rotation = rotation;
	}
	
	/**
	 * 
	 * @param offsetX
	 * @param offsetY
	 */
	public void setCameraTranslateBeforeApply(float offsetX, float offsetY){

//		cameraRange.set(cameraRangeOri);
		cameraRange.offset(offsetX-this.offsetX, offsetY-this.offsetY);
//		cameraRangeOri.set(cameraRange);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	/**
	 * set x scale value.
	 * @param xscale
	 */
	private void setXscale(float xscale){
		setXscale(xscale, cameraRange.centerX());
	}
	
	/**
	 * set y scale value.
	 * @param yscale
	 * 			y scale value.
	 */
	private void setYscale(float yscale){
		setYscale(yscale, cameraRange.centerY());
	}
	
	/**
	 * set x scale value.
	 * @param xscale
	 * 			x scale value.
	 * @param locationX
	 * 			the position of scale.
	 */
	private void setXscale(float xscale, float locationX){
		if(xscale==0)
			return;
		cameraRange.left = locationX - ((locationX - cameraRange.left) * Math.abs(xscale/this.xscale));
		cameraRange.right = locationX + ((cameraRange.right - locationX) * Math.abs(xscale/this.xscale));
		this.xscale = xscale;
	}
	
	/**
	 * set y scale value.
	 * @param yscale
	 * 			y scale.
	 * @param locationY
	 * 			the position of scale.
	 */
	private void setYscale(float yscale, float locationY){
		if(yscale==0)
			return;
		cameraRange.top = locationY - ((locationY - cameraRange.top) * Math.abs(yscale/this.yscale));
		cameraRange.bottom = locationY + ((cameraRange.bottom - locationY) * Math.abs(yscale/this.yscale));
		this.yscale = yscale;
	}
	
	/**
	 * get x scale value.
	 * @return xscale.
	 */
	private float getXscale(){
		return xscale;
	}
	
	/**
	 * get y scale value.
	 * @return yscale.
	 */
	private float getYscale(){
		return yscale;
	}
	
	/**
	 * get x offset.
	 * @return offsetX.
	 */
	public float getOffsetX() {
		return offsetX;
	}

	/**
	 * get y offset Y/
	 * @return offsetY.
	 */
	public float getOffsetY() {
		return offsetY;
	}

	@Override
	public void rotation(float rotation) {
		// TODO Auto-generated method stub
		setCameraRotateBeforeApply(getRotation()+rotation);
	}
	@Override
	public void translate(float offsetX, float offsetY) {
		// TODO Auto-generated method stub
		setCameraTranslateBeforeApply(getOffsetX() + offsetX, getOffsetY() + offsetY);
	}
	@Override
	public void zoom(float scale) {
		setXscale(getXscale() * scale);
		setYscale(getYscale() * scale);
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
	
	/**
	 * not finish.
	 * @param x
	 * @param y
	 */
	public void bindLayerToCameraXY(float x, float y){
		
	}
	
	/**
	 * get rotation value.
	 * @return float.
	 */
	public float getRotation() {
		return rotation;
	}




	ILayer layer;
	
	/**
	 * bindLayer. not test yet.
	 * @param layer
	 */
	public void bindLayer(ILayer layer) {
		// TODO Auto-generated method stub
		this.layer = layer;
		setViewPort(layer.getX(), layer.getY(), viewPort.getWidth(), viewPort.getHeight());
	}
	
	@Override
	public void setIsAutoStopOnBound() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * set view port's x,y,w,h.
	 * @param x
	 * 			left of view port.
	 * @param y
	 * 			top of view port.
	 * @param w
	 * 			width of view port.
	 * @param h
	 * 			height of view port.
	 */
	public void setViewPort(float x, float y, float w ,float h){
		if(viewPort == null){
			viewPort = new ViewPort();
		}
		
		viewPort.setXYWH(x, y, w, h);
	}
	
	/**
	 * get view port.
	 * @return {@code ViewPort}.
	 */
	public ViewPort getViewPort(){
		return viewPort;
	}
	
	/**
	 * get view port rect.
	 * @return {@code RectF}.
	 */
	public RectF getViewPortRectF(){
		if(viewPort != null){
			return viewPort.getViewPortRectF();
		}
		return null;
	}
	
	/**
	 * apply view port for it's setting.
	 * @param canvas 
	 * 			the canvas to draw.
	 */
	public void applyViewPort(Canvas canvas){
		//viewport
		if(viewPort!=null){
			canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//			canvas.rotate(viewPort.getRotation());
			canvas.setMatrix(viewPort.getMatrix());
			canvas.clipRect(getViewPortRectF());
			canvas.restore();
		}
	}
	
	/**
	 * apply camera space to view port with scale value.
	 */
	private void applyCameraSpaceScaleToViewPort(){
		if(viewPort!=null){
			float xscaleFactor = viewPort.getWidth()/cameraRange.width();
//			matrix.postScale(xscaleFactor, xscaleFactor);
			float yscaleFactor = viewPort.getHeight()/cameraRange.height();
			matrix.postScale(xscaleFactor, yscaleFactor, cameraRange.left, cameraRange.top);
		}
	}
	
	/**
	 * apply camera space to view port with rotation value.
	 */
	private void applyCameraSpaceRotate(){
		matrix.postRotate(-rotation, cameraRange.centerX(), cameraRange.centerY());
	}
	
	/**
	 * apply camera space to view port with offset(translate) value.
	 */
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
	
	/**
	 * apply camera space to view port with rotation value.
	 */
	private void applyCameraSpaceLRDirectionAndTBDirection(){
		int lrDir = xscale<0 ? -1:1;
		int tbDir = yscale<0 ? -1:1;
		matrix.postScale(lrDir, tbDir, cameraRange.centerX(), cameraRange.centerY());
	}
	
	/**
	 * apply camera space to view port.
	 */
	public void applyCameraSpaceToViewPort(){
		resetMatrix();
		
		applyCameraSpaceRotate();
		
		applyCameraSpaceLRDirectionAndTBDirection();
		
		applyCameraSpaceScaleToViewPort();
		
		applyCameraSpaceTranslateToViewPort();
	}
	
	/**
	 * reset camera matrix.
	 */
	private void resetMatrix(){
		matrix.reset();
	}
	
	/**
	 * get matrix.
	 * @return {@code Matrix}.
	 */
	public Matrix getMatrix(){
		return matrix; 
	}
}

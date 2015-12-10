package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;


/** * 层类，组件的父类，添加组件，设置组件位置，绘制自己， 是所有人物和背景的基类 * * @author Administrator * */
public abstract class ALayer implements Cloneable{
	private float x;// 层的x坐标
	private float y;// 层的y坐标
	public float centerX;
	public float centerY;
//	public float centerX;// 层的x坐标
//	public float centerY;// 层的y坐标
	public int w;// 层的宽度
	public int h;// 层的高度
//	public Rect src, dst;// 引用Rect类
	public Rect src;
	public RectF dst;
	public Bitmap bitmap;// 引用Bitmap类
	
	List<ALayer> layers = new ArrayList<ALayer>();
	ALayer parent;
	RectF smallViewRect;
	
	private int layerLevel;
	private boolean autoAdd;
	private boolean isComposite;
	private int alpha = 255;
	private Paint paint;
	
	private int zPosition = Integer.MIN_VALUE;
	
	private Runnable mPendingCheckForLongPress;
	private Runnable mPerformClick;
	private boolean pressed = false;
	private boolean mHasPerformedLongPress = false;
	private Handler handler = new Handler();
	private long longPressTimeout = 2000;
	
	private OnLayerClickListener onLayerClickListener;
	private OnLayerLongClickListener onLayerLongClickListener;
	
	private boolean isTouching = false;
	
	public interface OnLayerClickListener{
		public void onClick(ALayer layer);
	}
	
	public interface OnLayerLongClickListener{
		public boolean onLongClick(ALayer layer);
	}
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}
	
	protected ALayer(int w, int h, boolean autoAdd) {
//		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}
	
//	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
//		this.bitmap = BitmapUtil.createSpecificSizeBitmap(drawable, width, height)(resId);BitmapUtil.getBitmapFromRes(resId);
//		this.w = w;
//		this.h = h;
//		src = new Rect();
//		dst = new Rect();
//		if (autoAdd) {
//			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
//		}
//	}
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			setLayerLevel(level);
			LayerManager.addLayerByLayerLevel(this, level);// 在LayerManager类中添加本组件
		}
	}
	
	protected ALayer(Bitmap bitmap, float x, float y, boolean autoAdd) {
		this.bitmap = bitmap;
		setBitmapAndAutoChangeWH(bitmap);
		setPosition(x, y);
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}
	
	protected ALayer(float x, float y, boolean autoAdd) {
		setPosition(x, y);
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}

	/** * 设置组件位置的方法 * * @param x * @param y */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.centerX = x + w / 2;
		this.centerY = y + h / 2;
//		this.centerX = x - w / 2;
//		this.centerX = y - h / 2;
	}

	/** * 绘制自己的抽象接口 * * @param canvas * @param paint */
	public abstract void drawSelf(Canvas canvas, Paint paint);
	
//	public void addWithLayerLevelIncrease(ALayer layer){
//		throw new UnsupportedOperationException();
//		
//	}
//	
//	public void addWithOutLayerLevelIncrease(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public void remove(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public ALayer getChild(int i){
//		throw new UnsupportedOperationException();
//	}
//	
//	public String getDescription(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//
//	
//	public void print(){
//		throw new UnsupportedOperationException();
//	}
//	
//	public Iterator createIterator(){
//		throw new UnsupportedOperationException();
//		
//	}
//	
//	public void moveAllChild(int offsetLayerLevel){
//		throw new UnsupportedOperationException();
//	}
	


//	@Override
//	public void add(ALayer layer) {
//		// TODO Auto-generated method stub
//		layers.add(layer);
//	}
	

	public RectF getSmallViewRect() {
		return smallViewRect;
	}

	public void setSmallViewRect(RectF smallViewRect) {
		this.smallViewRect = smallViewRect;
	}

	public void remove(ALayer layer) {
		// TODO Auto-generated method stub
		if(layers.remove(layer)){
			layer.parent = null;
			LayerManager.deleteLayerByLayerLevel(layer, layer.layerLevel);
		}
	}

	public void addWithLayerLevelIncrease(ALayer layer) {
		// TODO Auto-generated method stub
		layer.layerLevel = layerLevel + 1;
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}
	
	public void addWithLayerLevelIncrease(ALayer layer, int increaseNum) {
		// TODO Auto-generated method stub

		layer.layerLevel = layerLevel + increaseNum;
		for(int i =0; i<increaseNum;i++){
			LayerManager.increaseNewLayer();
		}
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}

	public void addWithOutLayerLevelIncrease(ALayer layer){
		layer.layerLevel = layerLevel;
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}
	
	public void addWithLayerLevel(ALayer layer, int layerLevel) {
		// TODO Auto-generated method stub
//		int a = LayerManager.get;
//		for(int i = ; i<layerLevel;i++){
//			LayerManager.increaseNewLayer();
//		}
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layerLevel);
	}
	
	//composite
	public void addChild(ALayer layer){
		if(layer.getParent()==null){
			setComposite(true);
			layer.setComposite(true);
			layers.add(layer);
			layer.setParent(this);
		}else{
			throw new RuntimeException("child already has parent.");
		}
	}

	public ALayer getChild(int i) {
		// TODO Auto-generated method stub
		return layers.get(i);
	}

	public Iterator createIterator(){
		return new CompositeIterator(layers.iterator());
		
	}

	public void moveAllChild(int offsetLayerLevel){
		for(ALayer layer : layers){
//			layer.moveAllChild(offsetLayerLevel);
			int oldLayerLevel = layer.layerLevel;
			int newoldLayerLevel = layer.layerLevel + offsetLayerLevel;
			LayerManager.changeLayerToNewLayerLevel(layer, oldLayerLevel, newoldLayerLevel);
		}
	}
	
	public void setParent(ALayer parent){
		this.parent = parent;
	}
	
	public ALayer getParent(){
		return parent;
	}
	
	public void setInitWidth(int w){
		this.w = w;
		this.centerX = x + w / 2;
		
	}
	
	public void setInitHeight(int h){
		this.h = h;
		this.centerY = y + h / 2;
	}
	
	public void setWidth(int w){
		this.w = w;
	}
	
	public void setHeight(int h){
		this.h= h;
	}
	
	public float getX(){
		return x;
	}
	
	public float getCenterX(){
		return centerX;
	}
	
	public void setX(float x){
		this.x = x;
		this.centerX = x + w/2;
	}
	
	public float getY(){
		return y;
	}
	
	public float getCenterY(){
		return centerY;
	}
	
	public void setY(float y){
		this.y = y;
		this.centerY = y + h/2;
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}

	public int getLayerLevel() {
		return layerLevel;
	}

	public void setLayerLevel(int layerLevel) {
		this.layerLevel = layerLevel;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
		if(paint==null)
			paint = new Paint();
		paint.setAlpha(this.alpha);
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void removeFromParent(){
		if(parent!=null)
			parent.remove(this);
	}
	
	public int getzPosition() {
		return zPosition;
	}

	public void setzPosition(int zPosition) {
		this.zPosition = zPosition;
	}

	public boolean iszPositionValid(){
		return zPosition != Integer.MIN_VALUE;
	}
	
	public boolean isTouching() {
		return isTouching;
	}

	public void setTouching(boolean isTouching) {
		this.isTouching = isTouching;
	}

	public boolean isComposite() {
		return isComposite;
	}

	public void setComposite(boolean isComposite) {
		this.isComposite = isComposite;
	}
	
	public PointF locationInLayer(float x, float y){
		PointF locationInLayer = new PointF(x, y);
		if(isComposite()){
			for(ALayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
				locationInLayer.x = locationInLayer.x - layer.getX();
				locationInLayer.y = locationInLayer.y - layer.getY();
			}
		}
		return locationInLayer;
	}
	
	public PointF locationInSceneByCompositeLocation(float locationInLayerX, float locationInLayerY){
		PointF locationInScene = new PointF(locationInLayerX, locationInLayerY);
		if(isComposite()){
			for(ALayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
				locationInScene.x = locationInScene.x + layer.getX();
				locationInScene.y = locationInScene.y + layer.getY();
			}
		}
		return locationInScene;
	}
	
	public ALayer getRootLayer(){
		ALayer rootLayer = this;
		while(rootLayer.parent!=null){
			rootLayer = rootLayer.parent;
		}
		return rootLayer;
	}
	
	public List<ALayer> getLayersFromRootLayerToCurrentLayerInComposite(){
		List<ALayer> layersFromRootLayerToCurrentLayer = new ArrayList<ALayer>();
		layersFromRootLayerToCurrentLayer.add(0, this);
		ALayer rootLayer = this;
		while(rootLayer.parent!=null){
			if(!rootLayer.parent.isComposite())
				break;
			rootLayer = rootLayer.parent;
			layersFromRootLayerToCurrentLayer.add(0, rootLayer);
		}
		return layersFromRootLayerToCurrentLayer;
	}
	
	public void setOnLayerClickListener(OnLayerClickListener onLayerClickListener){
		this.onLayerClickListener = onLayerClickListener;
	}
	
	public void setOnLayerLongClickListener(OnLayerLongClickListener onLayerLongClickListener){
		this.onLayerLongClickListener = onLayerLongClickListener;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		for(ALayer child : layers){
			if(child.onTouchEvent(event)){
				return false;
			}
		}

		float x;
		float y;
		
		if(isComposite()){
			PointF locationInLayer = locationInLayer(event.getX(), event.getY());
			x = locationInLayer.x;
			y = locationInLayer.y;
		}else{
			x = event.getX();
			y = event.getY();
		}
		
		RectF f = new RectF(0, 0, w,
				h);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			RectF f = new RectF(
//					rectF.centerX()
//							- this.getCompoundDrawables()[3].getBounds()
//									.width() / 2.0f, 
//					rectF.bottom
//							- this.getCompoundDrawables()[3].getBounds()
//									.height() , 
//					rectF.centerX()
//							+ this.getCompoundDrawables()[3].getBounds()
//									.width() / 2.0f, 
//					rectF.bottom);
			
			if (!f.contains(x, y)) {
				return false;
			}
			mHasPerformedLongPress = false;

			if (mPendingCheckForLongPress == null) {
				mPendingCheckForLongPress = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (performLongClick()) {
							mHasPerformedLongPress = true;
						}
					}
				};
			}
			handler.postDelayed(mPendingCheckForLongPress,
					longPressTimeout - 0);

			isTouching = true;
			pressed = true;
			break;
		case MotionEvent.ACTION_UP:
			if (!isTouching) {
				return false;
//				break;
			}
			
			isTouching = false;
			
			if(!pressed){
				break;
			}
			
			pressed = false;
			
			if (mHasPerformedLongPress) {
				break;
			}
			removeLongPressCallback();
			if (mPerformClick == null) {
				mPerformClick = new PerformClick();
			}
			if (!handler.post(mPerformClick)) {
				performClick();
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (!isTouching) {
				return false;
//				break;
			}
			// setPressed(false);
			isTouching = false;
			pressed = false;
			// removeTapCallback();
			removeLongPressCallback();
			break;
		case MotionEvent.ACTION_MOVE:
			if (!pressed) {
				return false;
//				break;
			}
			if (!f.contains(x, y)) {
				removeLongPressCallback();
				pressed = false;
			}
			break;
		default:
			break;
		}

		return true;
	}

	private void removeLongPressCallback() {
		if (mPendingCheckForLongPress != null) {
			handler.removeCallbacks(mPendingCheckForLongPress);
		}
	}

	private final class PerformClick implements Runnable {
		public void run() {
			performClick();
		}
	}
	
	public boolean performClick() {
        if (onLayerClickListener != null) {
        	onLayerClickListener.onClick(this);
            return true;
        }
        return false;
    }
	
	public boolean performLongClick() {
        boolean handled = false;
        if (onLayerLongClickListener != null) {
            handled = onLayerLongClickListener.onLongClick(this);
        }
        return handled;
    }

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ALayer layer = (ALayer) super.clone();
		if(src!=null)
			layer.src = new Rect(src);
		if(dst!=null)
			layer.dst = new RectF(dst);
		
		layer.layers = new ArrayList<ALayer>(layers.size());
	    for(ALayer item: layers) layer.layers.add((ALayer)item.clone());
	    
	    if(smallViewRect!=null)
	    	layer.smallViewRect = new RectF(smallViewRect);
	    
	    if(autoAdd){
	    	LayerManager.addLayerByLayerLevel(layer, getLayerLevel());
	    }
	    
	    layer.paint = new Paint(paint);
	    
//	    ALayer parent maybe not need clone.
	    
		return layer;
	}
}

package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.example.try_gameengine.stage.StageManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;


/** * 层类，组件的父类，添加组件，设置组件位置，绘制自己， 是所有人物和背景的基类 * * @author Administrator * */
public abstract class ALayer implements ILayer{
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
	
	ConcurrentLinkedQueue<ILayer> layers = new ConcurrentLinkedQueue<ILayer>();
	ILayer parent;
	RectF smallViewRect;
	PointF locationInScene;
	
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
	private Handler handler;
	private long longPressTimeout = 2000;
	
	private OnLayerClickListener onLayerClickListener;
	private OnLayerLongClickListener onLayerLongClickListener;
	
	private boolean isEnableMultiTouch = false;
	
	private boolean isTouching = false;
	
	private boolean isEnable = true;
	
	private boolean isHidden = false;
	
	private int mActivePointerId;
	
	private RectF frame = new RectF();
	
	private LayerParam layerParam = new LayerParam();
	
	public static class LayerParam implements Cloneable{
		private boolean isEnabledPercentagePositionX;
		private boolean isEnabledPercentagePositionY;
		private boolean isEnabledPercentageSizeW;
		private boolean isEnabledPercentageSizeH;
		
		private float percentageX;
		private float percentageY;
		private float percentageW;
		private float percentageH;

		
		
		public boolean isEnabledPercentagePositionX() {
			return isEnabledPercentagePositionX;
		}
		public boolean isEnabledPercentagePositionY() {
			return isEnabledPercentagePositionY;
		}
		public boolean isEnabledPercentageSizeW() {
			return isEnabledPercentageSizeW;
		}
		public boolean isEnabledPercentageSizeH() {
			return isEnabledPercentageSizeH;
		}
		public void setEnabledPercentagePositionX(boolean isEnabledPercentagePositionX) {
			this.isEnabledPercentagePositionX = isEnabledPercentagePositionX;
		}
		public void setEnabledPercentagePositionY(boolean isEnabledPercentagePositionY) {
			this.isEnabledPercentagePositionY = isEnabledPercentagePositionY;
		}
		public void setEnabledPercentageSizeW(boolean isEnabledPercentageSizeW) {
			this.isEnabledPercentageSizeW = isEnabledPercentageSizeW;
		}
		public void setEnabledPercentageSizeH(boolean isEnabledPercentageSizeH) {
			this.isEnabledPercentageSizeH = isEnabledPercentageSizeH;
		}
		public float getPercentageX() {
			return percentageX;
		}
		public float getPercentageY() {
			return percentageY;
		}
		public float getPercentageW() {
			return percentageW;
		}
		public float getPercentageH() {
			return percentageH;
		}

		public void setPercentageX(float percentageX) {
			this.percentageX = percentageX;
		}
		public void setPercentageY(float percentageY) {
			this.percentageY = percentageY;
		}
		public void setPercentageW(float percentageW) {
			this.percentageW = percentageW;
		}
		public void setPercentageH(float percentageH) {
			this.percentageH = percentageH;
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
	}
	
	public interface OnLayerClickListener{
		public void onClick(ILayer layer);
	}
	
	public interface OnLayerLongClickListener{
		public boolean onLongClick(ILayer layer);
	}
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		getFrame().set(x, y, x+w, y+h);
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer(int w, int h, boolean autoAdd) {
//		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		getFrame().set(x, y, x+w, y+h);
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
//	protected ILayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
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
		getFrame().set(x, y, x+w, y+h);
		if (autoAdd) {
			this.autoAdd = autoAdd;
			setLayerLevel(level);
			LayerManager.addLayerByLayerLevel(this, level);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer(Bitmap bitmap, float x, float y, boolean autoAdd) {
		this.bitmap = bitmap;
		setBitmapAndAutoChangeWH(bitmap);
		setPosition(x, y);
		src = new Rect();
		dst = new RectF();
		getFrame().set(x, y, x+w, y+h);
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer(float x, float y, boolean autoAdd) {
		setPosition(x, y);
		src = new Rect();
		dst = new RectF();
		getFrame().set(x, y, x+w, y+h);
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	private void initALayer(){
		if(StageManager.getCurrentStage()!=null)
		StageManager.getCurrentStage().runOnUiThread(new Runnable() {
		    public void run() {
		        Log.d("UI thread", "I am the UI thread");
		        handler = new Handler();
		    }
		});	
	}

	/** * 设置组件位置的方法 * * @param x * @param y */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.centerX = x + w / 2;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		
		if(isComposite() && getParent()!=null)
			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setPosition(child.getX(), child.getY());
				}
			}		
		}
//		this.centerX = x - w / 2;
//		this.centerX = y - h / 2;
	}

	public void frameTrig(){
		for(ILayer layer : layers){
			if(layer instanceof ALayer && layer.isComposite())
				((ALayer)layer).frameTrig();
		}
	}

	/** * 绘制自己的抽象接口 * * @param canvas * @param paint */
	public abstract void drawSelf(Canvas canvas, Paint paint);
	
//	public void addWithLayerLevelIncrease(ILayer layer){
//		throw new UnsupportedOperationException();
//		
//	}
//	
//	public void addWithOutLayerLevelIncrease(ILayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public void remove(ILayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public ILayer getChild(int i){
//		throw new UnsupportedOperationException();
//	}
//	
//	public String getDescription(ILayer layer){
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
//	public void add(ILayer layer) {
//		// TODO Auto-generated method stub
//		layers.add(layer);
//	}
	

	public RectF getSmallViewRect() {
		return smallViewRect;
	}

	public void setSmallViewRect(RectF smallViewRect) {
		this.smallViewRect = smallViewRect;
	}

	public void remove(ILayer layer) {
		// TODO Auto-generated method stub
		if(layer instanceof ALayer)
			((ALayer)layer).willRemove();
		if(layers.remove(layer)){	
			if(layer.isComposite() && layer.getParent()!=null){
				layer.setLocationInScene(null);
				layer.setComposite(false);
			}
			layer.setParent(null);
			LayerManager.deleteLayerByLayerLevel(layer, layer.getLayerLevel());
		}
	}
	
	protected void willRemove(){
		willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}

	protected void willDoSometiongBeforeOneOfAncestorLayerWillRemoved(){
		for(ILayer layer : layers){
			if(layer.isComposite()){
				((ALayer)layer).willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
			}
		}
	}
	
	public void addWithLayerLevelIncrease(ILayer layer) {
		// TODO Auto-generated method stub
		layer.setLayerLevel(layerLevel + 1);
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.getLayerLevel());
	}
	
	public void addWithLayerLevelIncrease(ILayer layer, int increaseNum) {
		// TODO Auto-generated method stub

		layer.setLayerLevel(layerLevel + increaseNum);
		for(int i =0; i<increaseNum;i++){
			LayerManager.increaseNewLayer();
		}
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.getLayerLevel());
	}

	public void addWithOutLayerLevelIncrease(ILayer layer){
		layer.setLayerLevel(layerLevel);
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.getLayerLevel());
	}
	
	public void addWithLayerLevel(ILayer layer, int layerLevel) {
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
	public void addChild(ILayer layer){
		if(layer.getParent()==null){
//			setComposite(true);
			layer.setComposite(true);
			layers.add(layer);
			layer.setParent(this);
			layer.setLocationInScene(this.locationInSceneByCompositeLocation(layer.getX(), layer.getY()));
		}else{
			throw new RuntimeException("child already has parent.");
		}
	}

	public ILayer getChild(int i) {
		// TODO Auto-generated method stub
//		return layers.get(i);
		int index = 0;
		for(ILayer layer : layers){
			if(index == i){
				return layer;
			}
			index++;
		}
		return null;
	}

	public ConcurrentLinkedQueue<ILayer> getLayers() {
		return layers;
	}

	public Iterator createIterator(){
		return new CompositeIterator(layers.iterator());
		
	}

	public void moveAllChild(int offsetLayerLevel){
		for(ILayer layer : layers){
//			layer.moveAllChild(offsetLayerLevel);
			int oldLayerLevel = layer.getLayerLevel();
			int newoldLayerLevel = layer.getLayerLevel() + offsetLayerLevel;
			LayerManager.changeLayerToNewLayerLevel(layer, oldLayerLevel, newoldLayerLevel);
		}
	}
	
	public void setParent(ILayer parent){
		this.parent = parent;
	}
	
	public ILayer getParent(){
		return parent;
	}
	
	public void setInitWidth(int w){
		this.w = w;
		this.centerX = x + w / 2;
		getFrame().set(x, y, x+w, y+h);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
					child.setX(w * child.getLayerParam().getPercentageX());
				}
			}		
		}
	}
	
	public void setInitHeight(int h){
		this.h = h;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY()){
					child.setY(h * child.getLayerParam().getPercentageY());
				}
			}		
		}
	}
	
	public void setWidth(int w){
		this.w = w;
		this.centerX = x + w / 2;
		getFrame().set(x, y, x+w, y+h);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
					child.setX(w * child.getLayerParam().getPercentageX());
				}
			}		
		}
	}
	
	public void setHeight(int h){
		this.h= h;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY()){
					child.setY(h * child.getLayerParam().getPercentageY());
				}
			}		
		}
	}
	
	public void calculateWHByChildern(){
		if(getLayers().size()!=0){
			PointF pointWHMax = null;
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					((ALayer)child).calculateWHByChildern();
					float w = ((ALayer)child).w + child.getX();
					float h = ((ALayer)child).h + child.getY();
					PointF childPointWH = new PointF(w, h);
					if(pointWHMax==null)
						pointWHMax = childPointWH;
					else{
						if(childPointWH.x > pointWHMax.x)
							pointWHMax.x = childPointWH.x;
						if(childPointWH.y > pointWHMax.y)
							pointWHMax.y = childPointWH.y;
					}			
				}
			}
			if(pointWHMax!=null){
				this.setWidth((int)pointWHMax.x);
				this.setHeight((int)pointWHMax.y);
			}
		}
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
		getFrame().set(x, y, x+w, y+h);
		if(isComposite() && getParent()!=null)
			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setX(child.getX());
				}
			}		
		}
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
		getFrame().set(x, y, x+w, y+h);
		if(isComposite() && getParent()!=null)
			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setY(child.getY());
				}
			}		
		}
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public RectF getDst() {
		return dst;
	}

	public int getLayerLevel() {
		return layerLevel;
	}

	public void setLayerLevel(int layerLevel) {
		this.layerLevel = layerLevel;
	}

	public int getAlpha() {
		if(paint==null)
			return alpha;
		return getPaint().getAlpha();
	}

	public void setAlpha(int alpha) {
//		this.alpha = alpha;
		if(paint==null)
			paint = new Paint();
		paint.setAlpha(alpha);
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void removeFromParent(){
		willRemoveFromParent();
		if(parent!=null){
			parent.remove(this);
			removeFromAuto();
		}else{
			removeFromAuto();
		}
	}
	
	public void willRemoveFromParent(){
		willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}
	
	public void removeFromAuto(){
		if(autoAdd){
			LayerManager.deleteLayerBySearchAll(this);
			autoAdd = false;
		}
	}
	
	public int getzPosition() {
		return zPosition;
	}

	public void setzPosition(int zPosition) {
		this.zPosition = zPosition;
		LayerManager.updateLayersDrawOrderByZposition(this);
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

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean isComposite() {
		return isComposite;
	}

	public void setComposite(boolean isComposite) {
		this.isComposite = isComposite;
	}
	
	public RectF getFrame() {
		return frame;
	}

	public void setFrame(RectF frame) {
		
		if(frame!=null){
			setPosition(frame.left, frame.top);	
			setInitWidth((int) (frame.right - frame.left));
			setInitHeight((int) (frame.bottom - frame.top));	
		}else
			this.frame = frame;
	}
	
	public LayerParam getLayerParam() {
		return layerParam;
	}

	public void setLayerParam(LayerParam layerParam) {
		this.layerParam = layerParam;
	}

	public PointF getLocationInScene() {
		return locationInScene;
	}
	
	

	public boolean isAutoAdd() {
		return autoAdd;
	}

	public void setAutoAdd(boolean autoAdd) {
		if(this.autoAdd == autoAdd)
			return;
		this.autoAdd = autoAdd;
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}else{
			removeFromAuto();
		}
	}
	
	public boolean isEnableMultiTouch() {
		return isEnableMultiTouch;
	}

	public void setEnableMultiTouch(boolean isEnableMultiTouch) {
		this.isEnableMultiTouch = isEnableMultiTouch;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		setEnable(!isHidden);
		if(isHidden){
			if(getPaint()!=null)
				this.alpha = getPaint().getAlpha();
			setAlpha(0);
		}else{
			setAlpha(this.alpha);
		}
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setHidden(isHidden);
				}
			}		
		}
	}
	
	public void setLocationInScene(PointF locationInScene) {
		this.locationInScene = locationInScene;
		for(ILayer child : layers){
			child.setLocationInScene(locationInSceneByCompositeLocation(child.getX(), child.getY()));	
		}	
	}

	public PointF locationInLayer(float x, float y){
		PointF locationInLayer = new PointF(x, y);
//		if(isComposite()){
			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
				locationInLayer.x = locationInLayer.x - layer.getX();
				locationInLayer.y = locationInLayer.y - layer.getY();
			}
//		}
		return locationInLayer;
	}
	
	public PointF locationInSceneByCompositeLocation(float locationInLayerX, float locationInLayerY){
		PointF locationInScene = new PointF(locationInLayerX, locationInLayerY);
//		if(isComposite()){
			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
				locationInScene.x = locationInScene.x + layer.getX();
				locationInScene.y = locationInScene.y + layer.getY();
			}
//		}
		return locationInScene;
	}
	
	public ILayer getRootLayer(){
		ILayer rootLayer = this;
		while(rootLayer.getParent()!=null){
			rootLayer = rootLayer.getParent();
		}
		return rootLayer;
	}
	
	public List<ILayer> getLayersFromRootLayerToCurrentLayerInComposite(){
		List<ILayer> layersFromRootLayerToCurrentLayer = new ArrayList<ILayer>();
		layersFromRootLayerToCurrentLayer.add(0, this);
		ILayer rootLayer = this;
		while(rootLayer.getParent()!=null){
			if(!rootLayer.isComposite())
				break;
			rootLayer = rootLayer.getParent();
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
		for(ILayer child : layers){
			if(child.onTouchEvent(event)){
				return false;
			}
		}

		if(!isEnable())
			return false;
          
		float x;
		float y;
		
		final int downPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN
        	||(event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
//        	final int downPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
//                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        	if (isTouching || pressed) {
				return false;
			}
                    mActivePointerId = event.getPointerId(downPointerIndex);
        }else if(event.getPointerId(downPointerIndex)!=mActivePointerId){
        	return false;
        }
		
		RectF f;
		if(isComposite()){
            x = event.getX(downPointerIndex);
            y = event.getY(downPointerIndex);
			PointF locationInLayer = locationInLayer(x, y);
			x = locationInLayer.x;
			y = locationInLayer.y;
			f = new RectF(0, 0, w, h);
		}else{
            x = event.getX(downPointerIndex);
            y = event.getY(downPointerIndex);
			f = new RectF(getX(), getY(), getX()+w, getY()+h);
		}
		
//		RectF f = new RectF(0, 0, w,
//				h);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			if(!isEnableMultiTouch())
				return false;
			
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
			onTouched(event);
			break;
		case MotionEvent.ACTION_POINTER_UP:
			if(!isEnableMultiTouch())
				return false;
		case MotionEvent.ACTION_UP:
			onTouched(event);
			if (!isTouching) {
				return false;
//				break;
			}
			
			isTouching = false;
			
			if(!pressed){
				break;
			}

			pressed = false;
			
			onTouched(event);
			
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
			onTouched(event);
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
			onTouched(event);
			break;
		default:
			break;
		}

		return true;
	}
	
	protected abstract void onTouched(MotionEvent event);

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
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ALayer layer = (ALayer) super.clone();
		if(src!=null)
			layer.src = new Rect(src);
		if(dst!=null)
			layer.dst = new RectF(dst);
		
//		layer.layers = new ArrayList<ILayer>(layers.size());
		layer.layers = new ConcurrentLinkedQueue<ILayer>();
		
		for(ILayer item: layers) layer.layers.add((ALayer) ((ALayer)item).clone());
//	    for(ILayer item: layers) {
//	    	if(item instanceof ALayer){
//	    		ALayer layerCanClone = (ALayer) ((ALayer)item).clone();
//	    		layer.layers.add(layerCanClone);
//	    	}else
//	    		throw new CloneNotSupportedException();
//	    }
	    
	    if(smallViewRect!=null)
	    	layer.smallViewRect = new RectF(smallViewRect);
	    
	    if(locationInScene!=null)
	    	layer.locationInScene = new PointF(locationInScene.x, locationInScene.y);
	    
	    if(autoAdd){
	    	LayerManager.addLayerByLayerLevel(layer, getLayerLevel());
	    }
	    
	    if(paint!=null)
	    	layer.paint = new Paint(paint);
	
	    if(mPendingCheckForLongPress!=null){
	    	layer.mPendingCheckForLongPress = new Runnable() {
	
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (performLongClick()) {
						mHasPerformedLongPress = true;
					}
				}
			};
	    }
	    
	    if(mPerformClick!=null){
	    	layer.mPerformClick = new PerformClick();
	    }
	    
//	    private Runnable mPendingCheckForLongPress;
//		private Runnable mPerformClick;
		
		layer.handler = new Handler();
		
		if(frame!=null)
			layer.setFrame(new RectF(getFrame()));
		
		layer.layerParam = (LayerParam) this.layerParam.clone();
				
		
//		private OnLayerClickListener onLayerClickListener;
//		private OnLayerLongClickListener onLayerLongClickListener;
		
//		private boolean isTouching = false;
	    
//	    ILayer parent maybe not need clone.
	    
		return layer;
	}
}

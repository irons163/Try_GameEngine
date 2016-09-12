package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.try_gameengine.scene.Scene;
import com.example.try_gameengine.stage.StageManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;


/** * 层类，组件的父类，添加组件，设置组件位置，绘制自己， 是所有人物和背景的基类 * * @author Administrator * */
/**
 * @author user
 *
 */
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
	
	List<ILayer> layers = new CopyOnWriteArrayList<ILayer>();
	ILayer parent;
	RectF smallViewRect;
	PointF locationInScene;
	
	private int layerLevel;
	private boolean autoAdd;
	private boolean isComposite;
	private int alpha = 255;
	private Paint paint;
	
	private int zPosition = 0; //default 0
	
	private boolean isUsedzPosition = false;
	
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
	
	private boolean canMoving = true;
	
	private boolean isEnable = true;
	
	private boolean isHidden = false;
	
	private static final int INVALID_POINTER_ID = -1;
	
	private int mActivePointerId = INVALID_POINTER_ID;
	
	private RectF frame = new RectF();
	
	protected static int NONE_COLOR = 0;
	
	private int backgroundColor = NONE_COLOR;
	
	protected boolean isClipOutside = false;
	
	private boolean isAutoSizeByChildren = false;
	
	protected RectF frameInScene = new RectF();
	
	private LayerParam layerParam = new LayerParam();
	
	//Adjust position and size by parent layer.
	public static class LayerParam implements Cloneable{
		private boolean isEnabledPercentagePositionX;
		private boolean isEnabledPercentagePositionY;
		private boolean isEnabledPercentageSizeW;
		private boolean isEnabledPercentageSizeH;
		private boolean isEnabledBindPositionXY;
		
		private float percentageX;
		private float percentageY;
		private float percentageW;
		private float percentageH;
		private float bindPositionX;
		private float bindPositionY;
		
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
		public boolean isEnabledBindPositionXY() {
			return isEnabledBindPositionXY;
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
		public void setEnabledBindPositionXY(boolean isEnabledBindPositionXY) {
			this.isEnabledBindPositionXY = isEnabledBindPositionXY;
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
		public float getBindPositionX() {
			return bindPositionX;
		}
		public float getBindPositionY() {
			return bindPositionY;
		}
		public PointF getBindPositionXY() {
			return new PointF(bindPositionX, bindPositionY);
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
		public void setBindPositionXY(float bindPositionX, float bindPositionY) {
			this.bindPositionX = bindPositionX;
			this.bindPositionY = bindPositionY;
		}
		
		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
	}
	
	private PointF anchorPoint = new PointF(0, 0);
	private PointF anchorPointXY = new PointF();
	
	public interface OnLayerClickListener{
		public void onClick(ILayer layer);
	}
	
	public static final int NO_FLAG = 0;
	public static final int TOUCH_UP_CAN_OUTSIDE_SELF_RANGE = 1;
	public static final int TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN = 2;
	public static final int TOUCH_UP_DISABLE_WHEN_CLICK_LISTENER_ENABLE = 4;
	public static final int TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN = 8;
	public static final int TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE = 16;
	public static final int TOUCH_EVENT_ONLY_ACTIVE_ON_SELF = 64;
	public static final int TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN = 128;
	public static final int TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING = 192; // 64 & 128
	
	protected int flag = NO_FLAG;
	
	public void setFlag(int flag){
		this.flag = flag;
	}
	
	public int getFlag(){
		return this.flag;
	}
	
	public void addFlag(int flag){
		this.flag = this.flag|flag;
	}
	
	public void removeFlag(int flag){
		this.flag &= ~flag;
	}
	
	public interface OnLayerLongClickListener{
		public boolean onLongClick(ILayer layer);
	}
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		this.bitmap = bitmap;
		setWidthPrivate(w);
		setHeightPrivate(h);
//		this.w = w;
//		this.h = h;
//		this.centerX = w / 2;
//		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
//		getFrame().set(x, y, x+w, y+h);
//		setFrameInScene(frameInSceneByCompositeLocation());
		
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer(int w, int h, boolean autoAdd) {
		setWidthPrivate(w);
		setHeightPrivate(h);
//		this.bitmap = bitmap;
//		this.w = w;
//		this.h = h;
//		this.centerX = w / 2;
//		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
//		getFrame().set(x, y, x+w, y+h);
//		setFrameInScene(frameInSceneByCompositeLocation());
		
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer(boolean autoAdd) {
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
		initALayer();
	}
	
	protected ALayer() {
		this(false);
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
		setWidthPrivate(w);
		setHeightPrivate(h);
//		this.w = w;
//		this.h = h;
//		this.centerX = w / 2;
//		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
//		getFrame().set(x, y, x+w, y+h);
//		setFrameInScene(frameInSceneByCompositeLocation());
		
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
		setFrameInScene(frameInSceneByCompositeLocation());
		
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
		setFrameInScene(frameInSceneByCompositeLocation());
		
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
		anchorPointXY.x = x;
		x = x - anchorPoint.x * w;
		
//		x = x - anchorPointXY.x - anchorPoint.x * w;
		
		anchorPointXY.y = y;
		y = y - anchorPoint.y * h;
		
		
		this.x = x;
		this.y = y;
		this.centerX = x + w / 2;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(isComposite() && getParent()!=null)
			locationInScene = parent.locationInSceneByCompositeLocation(getX(), getY());
//			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setPosition(child.getX(), child.getY());
				}
			}		
		}
//		this.centerX = x - w / 2;
//		this.centerX = y - h / 2;
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}

	public void frameTrig(){
		for(ILayer layer : layers){
			if(layer instanceof ALayer && layer.isComposite() && !layer.isAutoAdd()) //if the layer is auto add, not trigger.
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
//				layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
				layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
				layer.setX(layer.getX()); //want to do colculationMatrix();
			}
			layer.setParent(null);
			LayerManager.deleteLayerByLayerLevel(layer, layer.getLayerLevel());
			if(layer.isAutoAdd())
				((ALayer)layer).autoAdd = false;
		}
	}
	
	public void removeAllChildren(){
		for(ILayer layer : layers){
			remove(layer);
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
		((ALayer)layer).autoAdd = true;
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
		((ALayer)layer).autoAdd = true;
		LayerManager.addLayerByLayerLevel(layer, layer.getLayerLevel());
	}

	public void addWithOutLayerLevelIncrease(ILayer layer){
		layer.setLayerLevel(layerLevel);
		layers.add(layer);
		layer.setParent(this);
		((ALayer)layer).autoAdd = true;
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
		((ALayer)layer).autoAdd = true;
		LayerManager.addLayerByLayerLevel(layer, layerLevel);
	}
	
	//composite
	public void addChild(ILayer layer){
		if(layer.getParent()==null){
			layer.setComposite(true);
			layers.add(layer);
			layer.setParent(this);
			
			if(layer.isUsedzPosition())
				layer.setAutoAdd(true);
			
			if(layer.getLayerParam().isEnabledPercentagePositionX()){
				layer.setX(w * layer.getLayerParam().getPercentageX());	
			}
			if(layer.getLayerParam().isEnabledPercentagePositionY()){
				layer.setY(h * layer.getLayerParam().getPercentageY());
			}
			
			layer.setLocationInScene(this.locationInSceneByCompositeLocation(layer.getX(), layer.getY()));
			
			if(layer.getLayerParam().isEnabledPercentageSizeW()){
				layer.setWidth((int)(w * layer.getLayerParam().getPercentageW()));	
			}
			if(layer.getLayerParam().isEnabledPercentageSizeH()){
				layer.setHeight((int)(h * layer.getLayerParam().getPercentageH()));
			}
			
//			layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
			layer.setX(layer.getX()); //want to do colculationMatrix();
		}else{
			throw new RuntimeException("child already has parent.");
		}
	}

	public ILayer getChild(int i) {
		int index = 0;
		for(ILayer layer : layers){
			if(index == i){
				return layer;
			}
			index++;
		}
		return null;
	}

	public List<ILayer> getLayers() {
		return layers;
	}

	public Iterator createIterator(){
		return new CompositeIterator(layers.iterator());
		
	}

	public void moveAllChild(int offsetLayerLevel){
		for(ILayer layer : layers){
//			layer.moveAllChild(offsetLayerLevel);
			if(layer.isComposite())
				continue;
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
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(anchorPoint.x != 0)
			setX(anchorPointXY.x);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
					child.setX(w * child.getLayerParam().getPercentageX());
				}
				if(child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW()){
					child.setWidth((int)(w * child.getLayerParam().getPercentageW()));
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public void setInitHeight(int h){
		this.h = h;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(anchorPoint.y != 0)
			setY(anchorPointXY.y);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY()){
					child.setY(h * child.getLayerParam().getPercentageY());
				}
				if(child.isComposite() && child.getLayerParam().isEnabledPercentageSizeH()){
					child.setHeight((int)(h * child.getLayerParam().getPercentageH()));
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public void setWidth(int w){
		setWidthPrivate(w);
	}
	
	private void setWidthPrivate(int w){
		this.w = w;
		this.centerX = x + w / 2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(anchorPoint.x != 0)
			setX(anchorPointXY.x);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
					child.setX(w * child.getLayerParam().getPercentageX());
				}
				if(child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW()){
					child.setWidth((int)(w * child.getLayerParam().getPercentageW()));
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public void setHeight(int h){
		setHeightPrivate(h);
	}
	
	private void setHeightPrivate(int h){
		this.h= h;
		this.centerY = y + h / 2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(anchorPoint.y != 0)
			setY(anchorPointXY.y);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY()){
					child.setY(h * child.getLayerParam().getPercentageY());
				}
				if(child.isComposite() && child.getLayerParam().isEnabledPercentageSizeH()){
					child.setHeight((int)(h * child.getLayerParam().getPercentageH()));
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public int getWidth(){
		return w;
	}
	
	public int getHeight(){
		return h;
	}
	
	public void calculateWHByChildern(){
		if(getLayers().size()!=0){
			PointF pointWHMax = null;
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					((ALayer)child).calculateWHByChildern();
//					float w = ((ALayer)child).w + child.getX();
//					float h = ((ALayer)child).h + child.getY();
					float w = ((ALayer)child).w + child.getLeft();
					float h = ((ALayer)child).h + child.getTop();
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
	
	public boolean isAutoSizeByChildren() {
		return isAutoSizeByChildren;
	}

	ALayer aLayer;
	
	public void setAutoSizeByChildren(ALayer layer) {
		if(layer != null){
			this.isAutoSizeByChildren = true;
			aLayer = layer;
//			addChild(layer);
			aLayer.setAutoAdd(true);
		}else{
			this.isAutoSizeByChildren = false;
			remove(aLayer);
			aLayer = null;
		}
		
		checkAndDoAutoSize();
	}
	
	private void checkAndDoAutoSize(){ 
//		if(!isAutoSizeByChildren())
//			return;
//		if(isAutoSizeByChildren()){
				
//				PointF locationInLayer = locationInLayer(0, 0);
//				RectF rectF = getRootLayer().autoCalculateSizeByChildern();
//				rectF.offset(locationInLayer.x, locationInLayer.y);
//				layer.setFrame(rectF);
//		}
		
		ALayer theFirstAutoSizeLayer = null;
		ILayer targetLayer = this;
		if(((ALayer)targetLayer).isAutoSizeByChildren())
			theFirstAutoSizeLayer = (ALayer) targetLayer;
		while(targetLayer.getParent()!=null && targetLayer.isComposite()){
			if(((ALayer)targetLayer.getParent()).isAutoSizeByChildren())
				theFirstAutoSizeLayer = (ALayer) targetLayer.getParent();
			targetLayer = targetLayer.getParent();
		}
		
		if(theFirstAutoSizeLayer != null)
			theFirstAutoSizeLayer.autoCalculateSizeByChildern();
	}

	public RectF autoCalculateSizeByChildern(){
		RectF pointWHMax;
		if(isAncestorClipOutSide()){
			pointWHMax = getClipRange();
			if(pointWHMax==null)
				pointWHMax = new RectF();
		}else{
			pointWHMax = new RectF(getFrameInScene());
		}
		
		if(getLayers().size()!=0){
			
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					RectF childFrame = ((ALayer)child).autoCalculateSizeByChildern();
//					float w = ((ALayer)child).w + child.getX();
//					float h = ((ALayer)child).h + child.getY();
//					float right = ((ALayer)child).w + child.getLeft();
//					float top = ((ALayer)child).h + child.getTop();
//					PointF childPointWH = new PointF(w, h);
//					if(pointWHMax==null)
//						pointWHMax = child.getFrameInScene();
//					else{
						if(childFrame.left < pointWHMax.left)
							pointWHMax.left = childFrame.left;
						if(childFrame.top < pointWHMax.top)
							pointWHMax.top = childFrame.top;
						if(childFrame.right > pointWHMax.right)
							pointWHMax.right = childFrame.right;
						if(childFrame.bottom > pointWHMax.bottom)
							pointWHMax.bottom = childFrame.bottom;
//					}
				}
			}
//			if(pointWHMax!=null){
//				pointWHMax.union(getFrameInScene());
//			}
		}
		
		if(isAutoSizeByChildren()){
//			PointF locationInLayer = locationInLayer(0, 0);
			RectF resizeFrame = new RectF(pointWHMax);
//			resizeFrame.offset(locationInLayer.x, locationInLayer.y);
			aLayer.setFrame(resizeFrame);
		}
		
		return pointWHMax;
	}
	
	private void checkParentAndDoParentAutoSize(){ // has some limit conditions.
		if(isComposite() && ((ALayer)getParent()).isAutoSizeByChildren()){
			if(getLayerParam().isEnabledPercentagePositionX() || getLayerParam().isEnabledPercentagePositionY() 
					|| getLayerParam().isEnabledPercentageSizeW() || getLayerParam().isEnabledPercentageSizeH()){
				return;
			}
			if(!getParent().getLayerParam().isEnabledPercentageSizeW() && !getParent().getLayerParam().isEnabledPercentageSizeH()){
				((ALayer)getParent()).calculateWHByChildern();
			}
		}
	}
	
	public float getX(){
		return anchorPointXY.x;
	}
	
	public float getLeft(){
		return x;
	}
	
	public float getCenterX(){
		return centerX;
	}
	
	public void setX(float x){
		anchorPointXY.x = x;
		x = x - anchorPoint.x * w;
		
		this.x = x;
		this.centerX = x + w/2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(isComposite() && getParent()!=null)
//			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
			locationInScene = parent.locationInSceneByCompositeLocation(getX(), getY());
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setX(child.getX());
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public float getY(){
		return anchorPointXY.y;
	}
	
	public float getCenterY(){
		return centerY;
	}
	
	public float getTop(){
		return y;
	}
	
	public void setY(float y){
		anchorPointXY.y = y;
		y = y - anchorPoint.y * h;
		
		this.y = y;
		this.centerY = y + h/2;
		getFrame().set(x, y, x+w, y+h);
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(isComposite() && getParent()!=null)
//			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
			locationInScene = parent.locationInSceneByCompositeLocation(getX(), getY());
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite()){
					child.setY(child.getY());
				}
			}		
		}
		
		checkAndDoAutoSize();
//		checkParentAndDoParentAutoSize();
	}
	
	public PointF getAnchorPoint() {
		return anchorPoint;
	}

	public void setAnchorPoint(PointF anchorPoint) {
		setAnchorPoint(anchorPoint.x, anchorPoint.y);
	}

	public void setAnchorPoint(float x, float y) {
		if (!(x == anchorPoint.x && y == anchorPoint.y)) {
			this.anchorPoint.set(x, y);
//			this.anchorPointXY.set(getX(), getY());
			setPosition(getX(), getY());
		}
	}
	
	public PointF getAnchorPointXY() {
		return anchorPointXY;
	}

	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}

	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
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
			removeFromLayerManager();
		}else{
			removeFromLayerManager();
		}
	}
	
	private void removeFromLayerManager(){
		if(autoAdd){
			LayerManager.deleteLayerBySearchAll(this);
			autoAdd = false;
		}
	}
	
	protected void willRemoveFromParent(){
		willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}
	
	protected void willRemoveFromAuto(){
		willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}
	
	public void removeFromAuto(){
		willRemoveFromAuto();
		removeFromLayerManager();
	}
	
	public int getzPosition() {
		return zPosition;
	}
	//Need add LayerManager.(AutoDraw)
	public void setzPosition(int zPosition) {
		this.zPosition = zPosition;
		this.isUsedzPosition = true;
		if(!autoAdd){
			autoAdd = true;
			LayerManager.addLayer(this);
		}
		LayerManager.updateLayersDrawOrderByZposition(this);
	}
	
	/**
	 * 
	 */
	public void resetzPosition(){
		this.isUsedzPosition = false;
		setAutoAdd(false);
	}
	
	@Override
	public boolean isUsedzPosition() {
		// TODO Auto-generated method stub
		return isUsedzPosition;
	}

	/**
	 * @param isClipOutside
	 */
	public void setIsClipOutside(boolean isClipOutside){
		this.isClipOutside = isClipOutside;
		if(isClipOutside && getPaint()==null)
			setPaint(new Paint());
	}
	
	public boolean isClipOutside(){
		return isClipOutside;
	}
	
//	protected void setParentRectF(RectF parentRectF){
//		this.parentRectF = parentRectF;
//	}
	
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
	
	public RectF getFrameInScene(){
		return frameInScene;
	}
	
	public void setFrameInScene(RectF frameInScene){
		this.frameInScene = frameInScene;
//		autoCalculateSizeByChildern();
//		setX(getX());
//		for(ILayer child : layers){
//			if(child.isComposite())
//				child.setFrameInScene(child.frameInSceneByCompositeLocation());	
//		}
	}
	
	public void setBackgroundColor(int backgroundColor){
		this.backgroundColor = backgroundColor;
		if(paint==null)
			paint = new Paint();
	}
	
	public void setBackgroundColorNone(){
		this.backgroundColor = NONE_COLOR;
	}
	
	public int getBackgroundColor(){
		return backgroundColor;
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
		
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}else{
			removeFromAuto();
//			this.autoAdd = autoAdd; //removeFromAuto() do this, so here is not need do again. 
		}
	}
	
	public void setAutoAdd(boolean autoAdd, int sceneLayerLevel){
		if(this.autoAdd == autoAdd)
			return;
		
		if (autoAdd) {
			this.autoAdd = autoAdd;
			LayerManager.addSceneLayerByLayerLevel(this, sceneLayerLevel);
		}else{
			removeFromAuto();
//			this.autoAdd = autoAdd; //removeFromAuto() do this, so here is not need do again. 
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
	
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isHidden() {
		return isHidden;
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
	
	public boolean checkIsFlagEnable(int flagForCheck){
//		return ((getFlag() & flagForCheck) != 0); // not correct if flagForCheck is a mix flag, like: flagForCheck = (Aflag & Bflag);
		return ((getFlag() & flagForCheck) == flagForCheck);
	}
	
	public boolean isEnableTouchOnSlef(){
		return !checkIsFlagEnable(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN); // If only active children, means not active self.
	}
	
	public void setEnableTouchOnSlef(boolean enableTouchOnSelf){
		if(isEnableTouchOnSlef() == enableTouchOnSelf)
			return;
		if(!enableTouchOnSelf){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
		}else{
			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
		}
	}
	
	public boolean isEnableTouchOnSlefAndChildren(){
		return !(checkIsFlagEnable(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN) || checkIsFlagEnable(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF)); // Need active on self and children.
	}
	
	public void setEnableTouchOnSlefAndChildren(boolean enableTouchOnSelfAndChildren){
		if(isEnableTouchOnSlefAndChildren() == enableTouchOnSelfAndChildren)
			return;
		if(!enableTouchOnSelfAndChildren){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING);
		}else{
			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING);
		}
	}
	
	public void setLocationInScene(PointF locationInScene) {
		this.locationInScene = locationInScene;
		for(ILayer child : layers){
			if(child.isComposite())
				child.setLocationInScene(locationInSceneByCompositeLocation(child.getX(), child.getY()));	
		}	
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.framework.ILayer#locationInLayer(float, float)
	 */
	public PointF locationInLayer(float x, float y){
		PointF locationInLayer = new PointF(x, y);
//		if(isComposite()){
			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
//				locationInLayer.x = locationInLayer.x - layer.getX();
//				locationInLayer.y = locationInLayer.y - layer.getY();
				locationInLayer.x = locationInLayer.x - layer.getLeft();
				locationInLayer.y = locationInLayer.y - layer.getTop();
			}
//		}
		return locationInLayer;
	}
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.framework.ILayer#locationInSceneByCompositeLocation(float, float)
	 */
	public PointF locationInSceneByCompositeLocation(float locationInLayerX, float locationInLayerY){
		PointF locationInScene = new PointF(locationInLayerX, locationInLayerY);
//		if(isComposite()){
			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
//				locationInScene.x = locationInScene.x + layer.getX();
//				locationInScene.y = locationInScene.y + layer.getY();
				locationInScene.x = locationInScene.x + layer.getLeft();
				locationInScene.y = locationInScene.y + layer.getTop();
			}
//		}
		return locationInScene;
	}
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.framework.ILayer#frameInSceneByCompositeLocation()
	 */
	public RectF frameInSceneByCompositeLocation(){
		RectF frameInScene = new RectF();
//		if(isComposite()){
			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
				frameInScene.left = frameInScene.left + layer.getLeft();
				frameInScene.top = frameInScene.top + layer.getTop();
			}
			frameInScene.right = frameInScene.left + getWidth();
			frameInScene.bottom = frameInScene.top + getHeight();
//		}
		return frameInScene;
	}
	
//	public RectF frameInSceneByCompositeLocation(RectF rectF){
//		RectF frameInScene = new RectF(rectF);
////		if(isComposite()){
//			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
////				locationInScene.x = locationInScene.x + layer.getX();
////				locationInScene.y = locationInScene.y + layer.getY();
//				frameInScene.left = frameInScene.left + layer.getFrame().left;
//				frameInScene.top = frameInScene.top + layer.getFrame().top;
//				frameInScene.right = frameInScene.right + layer.getFrame().right;
//				frameInScene.bottom = frameInScene.bottom + layer.getFrame().bottom;
//			}
////		}
//		return frameInScene;
//	}
	
	
	/**
	 * @return
	 */
	protected boolean isAncestorClipOutSide(){
		boolean isAncestorClipOutSide = false;
		ILayer layer = this;
		while((layer = layer.getParent()) != null){
			isAncestorClipOutSide = layer.isClipOutside();
			if(isAncestorClipOutSide)
				break;
		}
		return isAncestorClipOutSide;
	}
	
	/**
	 * @return
	 */
	private RectF getClipRange(){
		ILayer layer = this;
		RectF clipRange = new RectF(this.getFrameInScene());
//		RectF clipRange = new RectF(this.getFrame());
		while((layer = layer.getParent()) != null){
			if(!layer.isClipOutside())
				continue;
			if(!clipRange.intersect(layer.getFrameInScene())){
//			if(!clipRange.intersect(layer.getFrame()))
				clipRange = null;
				break;
			}
		}
		return clipRange;
	}
	
//	Matrix matrix;
	public Canvas getCC(Canvas canvas, Paint paint) {
		if(isAncestorClipOutSide()){
//			matrix = canvas.getMatrix();
			getC(canvas, paint);
		}
		return canvas;
	}
	
	// This has a better clip out side method, not need to access every parent now.
	public Canvas getC(Canvas canvas, Paint paint){
		if(isAncestorClipOutSide()){
			if(getParent().isClipOutside()){
//				canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
				canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
				if(getParent() instanceof Sprite){
					canvas.concat(((Sprite)getParent()).spriteMatrix);
					canvas.clipRect(((Sprite)getParent()).drawRectF);
				}else{
					canvas.clipRect(getParent().getFrameInScene());
				}
				canvas.restore();
			}
			((ALayer)getParent()).getC(canvas, paint);
		}
		return canvas;
	}
	
	public void bindAllChildrenPositionXY(){
		for(ILayer child : getLayers()){
			child.getLayerParam().setBindPositionXY(child.getX(), child.getY());
			child.getLayerParam().setEnabledBindPositionXY(true);
		}
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
		if(!isEnable())
			return false;
		
		float x;
		float y;
		
		final int downPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

		
		RectF f;
        x = event.getX(downPointerIndex);
        y = event.getY(downPointerIndex);
		float a[] = new float[]{x, y};
		boolean isIndentify = true;
		if(StageManager.getCurrentStage().getSceneManager()!=null && StageManager.getCurrentStage().getSceneManager().getCurrentActiveScene()!=null)
			isIndentify = StageManager.getCurrentStage().getSceneManager().getCurrentActiveScene().getCamera().getMatrix().isIdentity();
		if(isIndentify && this instanceof Sprite){
			if(((Sprite)this).spriteMatrix!=null){
				synchronized (((Sprite)this).spriteMatrix) {
					isIndentify = isIndentify && ((Sprite)this).spriteMatrix.isIdentity();
				}
			}
		}
			
		if(!isIndentify){
//            f = getFrameInScene();
			f = frameInSceneByCompositeLocation();
//			f = new RectF(getLeft(), getTop(), getLeft()+w, getTop()+h);
			Scene scene = StageManager.getCurrentStage().getSceneManager().getCurrentActiveScene();
			Matrix matrix = new Matrix();
			if(this instanceof Sprite){
				if(scene!=null) // If user not use scene system, scene is null.
					matrix = new Matrix(scene.getCamera().getMatrix());
				if(((Sprite)this).spriteMatrix!=null){
					synchronized (((Sprite)this).spriteMatrix) {
						Matrix matrix2 =  new Matrix(((Sprite)this).spriteMatrix);
						matrix2.setTranslate(0, 0);
						matrix.postConcat(matrix2);
					}
				}
				matrix.invert(matrix);
//				matrix = matrix2;
			}else{
				if(scene!=null) // If user not use scene system, scene is null.
					scene.getCamera().getMatrix().invert(matrix);
			}

			
			matrix.mapPoints(a);
		}else if(isComposite()){
            x = event.getX(downPointerIndex);
            y = event.getY(downPointerIndex);
			PointF locationInLayer = locationInLayer(x, y);
			x = locationInLayer.x;
			y = locationInLayer.y;
			f = new RectF(0, 0, w, h);
		}else{
            x = event.getX(downPointerIndex);
            y = event.getY(downPointerIndex);
			f = new RectF(getLeft(), getTop(), getLeft()+w, getTop()+h);
		}
		
		if(isClipOutside()){
			if(!isIndentify){
				if (!f.contains(a[0], a[1])) {
//					return false;
					if((event.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN
							&& (event.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_POINTER_DOWN){
						/*// It seems not need.
						MotionEvent cancelEvent = MotionEvent.obtain(event);
						cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
						*/
					}else{
						return false;
					}
				}
			}else if(!f.contains(x, y)){
//				return false;
				if((event.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN
						&& (event.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_POINTER_DOWN){
					/*// It seems not need.
					MotionEvent cancelEvent = MotionEvent.obtain(event);
					cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
					*/
				}else{
					return false;
				}
			}
		}
		
		if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_SELF)==0){
//			for(int i = layers.size(); i < layers.size(); i++){
//				
//			}
			ListIterator<ILayer> iterator = layers.listIterator(layers.size());
			while(iterator.hasPrevious()){
				ILayer child = iterator.previous();
				if(!child.isAutoAdd() && child.onTouchEvent(event)){
					/*
					if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)!=0)
						return true;
					else
						return false;
					*/
					return true; //if child accept the touch event, not do self touch event and return true.
				}	
			}
		}
		
		if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)!=0){
			return false;
		}


        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
        	if (isTouching || pressed) {
				return false;
			}
                    mActivePointerId = event.getPointerId(downPointerIndex);
        }if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN){
//        	final int downPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
//                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        	if(!isEnableMultiTouch())
				return false;
        	
        	if (isTouching || pressed) {
				return false;
			}
                    mActivePointerId = event.getPointerId(downPointerIndex);
        }else if(event.getPointerId(downPointerIndex)!=mActivePointerId){
//        	if(!((flag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0 && (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE))
        	if((flag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0){
        		if((event.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_MOVE){
        			mActivePointerId = INVALID_POINTER_ID;
        			canMoving = true;
        		}
        	}else
        		return false;
        }

		
//		RectF f = new RectF(0, 0, w,
//				h);
		boolean enablePerformClick = true;
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
//			if(!isEnableMultiTouch())
//				return false;
			
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
			
			if(!isIndentify){
				if (!f.contains(a[0], a[1])) {
					return false;
				}
			}else if (!f.contains(x, y)) {
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
				enablePerformClick = false;
//			if(!isEnableMultiTouch())
//				return false;
		case MotionEvent.ACTION_UP:
			mActivePointerId = INVALID_POINTER_ID;
			canMoving = true;
			
			if((flag & TOUCH_UP_DISABLE_WHEN_CLICK_LISTENER_ENABLE)==0 || onLayerClickListener == null){
				if((flag & TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN)!=0 && (flag & TOUCH_UP_CAN_OUTSIDE_SELF_RANGE)!=0){
					onTouched(event); 
					
					if(!pressed){
						break;
					}
	
					pressed = false;
					
				}else if((flag & TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN)!=0 && (flag & TOUCH_UP_CAN_OUTSIDE_SELF_RANGE)==0){
					if(!isIndentify){
						if (f.contains(a[0], a[1])) {
							onTouched(event);
						}
					}else if (f.contains(x, y)) {
						onTouched(event);
					}
					
					if(!pressed){
						break;
					}
	
					pressed = false;
					
				}else if((flag & TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN)==0 && (flag & TOUCH_UP_CAN_OUTSIDE_SELF_RANGE)!=0){
					if (!isTouching) {
						return false;
	//					break;
					}
					
					isTouching = false;
					
					onTouched(event);
					
					if(!pressed){
						break;
					}
	
					pressed = false;
					
				}else if((flag & TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN)==0 && (flag & TOUCH_UP_CAN_OUTSIDE_SELF_RANGE)==0){
					if (!isTouching) {
						return false;
	//					break;
					}
					
					isTouching = false;
					
					if(!pressed){
						break;
					}
	
					onTouched(event);
					
					pressed = false;
					
				}
			}
			
			if (mHasPerformedLongPress) {
				break;
			}
			removeLongPressCallback();
			
			if(enablePerformClick){
				if (mPerformClick == null) {
					mPerformClick = new PerformClick();
				}
				if (!handler.post(mPerformClick)) {
					performClick();
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			mActivePointerId = INVALID_POINTER_ID;
			
			canMoving = true;
			
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
			if (((flag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)==0 && !pressed) || !canMoving) {
				return false;
			}

//			canMoving = true;
			
			if ((flag & TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE)!=0){
				if(!isIndentify){
					if (!f.contains(a[0], a[1])) {
						removeLongPressCallback();
					}
				}else if (!f.contains(x, y)) {
					removeLongPressCallback();
				}
			}else{
				if(!isIndentify){
					if (!f.contains(a[0], a[1])) {
						removeLongPressCallback();
						pressed = false;
						if ((flag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0){
							canMoving = false;
							return false;
						}
					}
				}else if (!f.contains(x, y)) {
					removeLongPressCallback();
					pressed = false;
					if ((flag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0){
						canMoving = false;
						return false;
					}
				}
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
		
		layer.layers = new ArrayList<ILayer>(layers.size());
//		layer.layers = new ConcurrentLinkedDeque<ILayer>();
		
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
	    
	    layer.zPosition = this.zPosition;
	    
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
		
		layer.flag = this.flag;
				
		layer.backgroundColor = this.backgroundColor;
		
		layer.isUsedzPosition = this.isUsedzPosition;
		
//		private OnLayerClickListener onLayerClickListener;
//		private OnLayerLongClickListener onLayerLongClickListener;
		
//		private boolean isTouching = false;
	    
//	    ILayer parent maybe not need clone.
	    
		return layer;
	}
}

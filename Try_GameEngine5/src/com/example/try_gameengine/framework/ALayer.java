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
	
	private int zPosition = 0; //default 0
	
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
	
	protected RectF frameInScene = new RectF();
	
	private LayerParam layerParam = new LayerParam();
	
	//Adjust position and size by parent layer.
	//the size function not implement yet.
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
		this.setWidth(w);
		this.setHeight(h);
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
		this.setWidth(w);
		this.setHeight(h);
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
		this.setWidth(w);
		this.setHeight(h);
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
			layer.setComposite(true);
			layers.add(layer);
			layer.setParent(this);
			
			if(layer.getLayerParam().isEnabledPercentagePositionX()){
				layer.setX(w * layer.getLayerParam().getPercentageX());	
			}
			if(layer.getLayerParam().isEnabledPercentagePositionY()){
				layer.setY(h * layer.getLayerParam().getPercentageY());
			}
			
			layer.setLocationInScene(this.locationInSceneByCompositeLocation(layer.getX(), layer.getY()));
//			layer.setFrameInScene(layer.frameInSceneByCompositeLocation(layer.getFrame()));
			layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
			layer.setX(layer.getX()); //want to do colculationMatrix();
			
//			if(layer instanceof Sprite){
//				((Sprite)layer).locationLeftTopInScene = this.locationInSceneByCompositeLocation(layer.getLeft(), layer.getTop());
//				((Sprite)layer).setX(layer.getX()); //want to do colculationMatrix();
//			}
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
		setFrameInScene(frameInSceneByCompositeLocation());
		
		if(anchorPoint.x != 0)
			setX(anchorPointXY.x);
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
					child.setX(w * child.getLayerParam().getPercentageX());
				}
//				else if(child.isComposite()){
//					child.setX(child.getX());
//				}
			}		
		}
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
//				else if(child.isComposite()){
//					child.setY(child.getY());
//				}
			}		
		}
	}
	
	public void setWidth(int w){
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
//				else if(child.isComposite()){
//					child.setX(child.getX());
//				}
			}		
		}
	}
	
	public void setHeight(int h){
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
//				else if(child.isComposite()){
//					child.setY(child.getY());
//				}
			}		
		}
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
		if(!autoAdd){
			autoAdd = true;
			LayerManager.addLayer(this);
		}
		LayerManager.updateLayersDrawOrderByZposition(this);
	}

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
			if(child.isComposite())
				child.setLocationInScene(locationInSceneByCompositeLocation(child.getX(), child.getY()));	
		}	
	}

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
	
	protected RectF getClipRange(){
		ILayer layer = this;
		RectF clipRange = new RectF(this.getFrameInScene());
		while((layer = layer.getParent()) != null){
			if(!layer.isClipOutside())
				continue;
			if(!clipRange.intersect(layer.getFrameInScene()))
				clipRange = null;
		}
		return clipRange;
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
		
		if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_SELF)==0){
			for(ILayer child : layers){
				if(child.onTouchEvent(event)){
					if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)!=0)
						return true;
					else
						return false;
				}
			}
		}
		
		if((flag & TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)!=0){
			return false;
		}

		float x;
		float y;
		
		final int downPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
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
			f = new RectF(getLeft(), getTop(), getLeft()+w, getTop()+h);
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
					if (f.contains(x, y)) {
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
				if (!f.contains(x, y)) {
					removeLongPressCallback();
				}
			}else{
				if (!f.contains(x, y)) {
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
		
		layer.flag = this.flag;
				
		layer.backgroundColor = this.backgroundColor;
		
//		private OnLayerClickListener onLayerClickListener;
//		private OnLayerLongClickListener onLayerLongClickListener;
		
//		private boolean isTouching = false;
	    
//	    ILayer parent maybe not need clone.
	    
		return layer;
	}
}

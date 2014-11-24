package com.example.try_gameengine.framework;

import java.util.Hashtable;

import com.example.try_gameengine.action.MovementAction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Sprite extends ALayer {
	public int frameIdx;// 當前幀下標
	public int currentFrame = 0;// 當前幀
	public Hashtable<String, SpriteAction> actions;// 動作集合
	public SpriteAction currentAction;// 當前動作

	public boolean isStop = false;
//	public boolean isEnableInterruptAction = false;
	public float scale = 1.0f;
	public boolean canCollision = true;
	public MovementAction action;
	
	protected RectF moveRage;
	
	private int frameWidth;
	private int frameHeight;
	private int length;
	private int[] frameSequence;
	private  int frameIndex; 
	
	public Sprite(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		actions = new Hashtable<String, Sprite.SpriteAction>();// 用Hashtable保存動作集合
	}
	
	public Sprite(Bitmap bitmap, int scale, boolean autoAdd) {
		super(bitmap, 0, 0, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	     matrix.postScale(scale, scale);
	      
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		setWidth(resizedBitmap.getWidth());
		setHeight(resizedBitmap.getHeight());
	     actions = new Hashtable<String, Sprite.SpriteAction>();

	}
	
	public Sprite(Bitmap bitmap, int resId, int w, int h, float scale, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	      
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

	}
	
	public Sprite(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, 0, 0, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	     matrix.postScale(scale, scale);
	      
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		setWidth(resizedBitmap.getWidth());
		setHeight(resizedBitmap.getHeight());
	     actions = new Hashtable<String, Sprite.SpriteAction>();

	     setPosition(x, y);
	}
	
	public Sprite(Bitmap bitmap, float x, float y, int resId, int w, int h, float scale, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	     matrix.postScale(scale, scale);
	      
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

		setPosition(x, y);
	}
	
	public Sprite(float x, float y, boolean autoAdd) {
		super(0, 0, autoAdd);
//		this.scale = scale;
//		Matrix matrix = new Matrix();
//	     matrix.postScale(scale, scale);
//	      
//	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//		setWidth(resizedBitmap.getWidth());
//		setHeight(resizedBitmap.getHeight());
	     actions = new Hashtable<String, Sprite.SpriteAction>();

	     setPosition(x, y);
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}
	
	public void setBitmapAndFrameWH(Bitmap bitmap,int frameWidth ,int frameHeight ){
		this.bitmap = bitmap;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.w = frameWidth;
		this.h = frameHeight;
		this.length = (bitmap.getWidth()/frameWidth)*(bitmap.getHeight()/frameHeight);
	}
	
	public void setFrameSequence(int[] sequence)
	{			
		this.frameSequence = sequence;
		frameIndex = 0;
		currentFrame = sequence[0];
	}
	
	public void setMovementAction(MovementAction movementAction){
		this.action = movementAction;
	}
	
	public void setAction(String actionName) {
		frameIdx = 0;
		currentAction = actions.get(actionName);
		currentAction.initUpdateTime();
		scale = currentAction.scale;
		isStop = false;			
	}
	
	public void setMoveRage(float x, float y, float height, float width){
		moveRage = new RectF(x, y, x+width, y+height);
	}
	
	public void setMoveRage(RectF moveRage){
		this.moveRage = moveRage;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		
//		if (currentAction != null) {
//			if(currentAction.frames!=null){
//				currentFrame = currentAction.frames[frameIdx];
//			}else{
//				bitmap = currentAction.bitmapFrames[frameIdx];
//			}
//		} 
		if(length>0){
			paint(canvas,paint);
		}else{
			src.left = (int) (currentFrame * w * scale);// 左端寬度：當前幀乘上幀的寬度再乘上圖片縮放率
			src.top = 0;
			src.right = (int) (src.left + w * scale);// 右端寬度：左端寬度加上(幀的寬度乘上圖片縮放率)
			src.bottom = h;
//			dst.left = (int) x - w / 2;
//			dst.top = (int) y - h / 2;
			dst.left = (float) (centerX - w / 2);
			dst.top = (float) (centerY - h / 2);
			dst.right = (float) (dst.left + w * scale);
			dst.bottom = (float) (dst.top + h * scale);
//			canvas.drawBitmap(bitmap, src, dst, paint);
			canvas.drawBitmap(bitmap, src, dst, paint);

//			Log.e("time2", a+""+"XX"+System.currentTimeMillis());
		}

	}
	
	public void paint(Canvas canvas,Paint paint)
	{
		canvas.save();
		float x = getX();
		float y = getY();
		canvas.clipRect(x, y, x+frameWidth, y+frameHeight);
		canvas.drawBitmap(bitmap, x-(currentFrame%(bitmap.getWidth()/frameWidth))*frameWidth, 
				y - (currentFrame/(bitmap.getWidth()/frameWidth))*frameHeight, paint);
		canvas.restore();
		
		dst.left = (float) (centerX - w / 2);
		dst.top = (float) (centerY - h / 2);
		dst.right = (float) (dst.left + w * scale);
		dst.bottom = (float) (dst.top + h * scale);
	}

	/**
	 * * 瘛餃�銝�葵�其����瘜�* * @param name * @param frames * @param frameTime
	 * */
	public void addAction(String name, int[] frames, int[] frameTime) {
		SpriteAction sp = new SpriteAction();
		sp.frames = frames;//幀的數量
		sp.frameTime = frameTime;//每一幀切換的時間
		sp.name = name;
		actions.put(name, sp);
	}
	
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime) {
		addAction(name, bitmapFrames, frameTime, 1.0f, true, new DefaultActionListener());
	}
	
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime, boolean isLoop) {
		addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, new DefaultActionListener());
	}
	
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime, boolean isLoop, IActionListener actionListener) {
		addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, actionListener);
	}
	
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime, float scale, boolean isLoop, IActionListener actionListener) {
		SpriteAction sp = new SpriteAction();
		sp.bitmapFrames = bitmapFrames;// 幀圖片集合
		sp.frameTime = frameTime;//每一幀切換的時間
		sp.isLoop = isLoop;
		sp.name = name;
		sp.scale = scale;
		sp.actionListener = actionListener;
		actions.put(name, sp);
	}
	
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes, boolean isLoop) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes, float scale, boolean isLoop, IActionListener actionListener) {
		SpriteAction sp = new SpriteActionBaseFPS();
		sp.bitmapFrames = bitmapFrames;// 幀圖片集合
		sp.frameTime = frameTriggerTimes;//每一幀切換的時間
		sp.isLoop = isLoop;
		sp.name = name;
		sp.scale = scale;
		sp.actionListener = actionListener;
		actions.put(name, sp);
	}
	
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, float scale, boolean isLoop, IActionListener actionListener) {
		SpriteAction sp = new SpriteActionBaseFPS();
		sp.frames = sequence;// 幀圖片集合
		sp.frameTime = frameTriggerTimes;//每一幀切換的時間
		sp.isLoop = isLoop;
		sp.name = name;
		sp.scale = scale;
		sp.actionListener = actionListener;
		actions.put(name, sp);
	}

	public void process(){
		long a = System.currentTimeMillis();
		if (currentAction != null) {
			if(currentAction.frames!=null){
				currentAction.nextFrame();
			}else{
				currentAction.nextBitmap();
			}		
		}
		a = System.currentTimeMillis() - a;
	}
	
	public void move(float dx, float dy) {
		if(moveRage==null){
			setX(getCenterX() + dx - w/2);
			setY(getCenterY() + dy - h/2);
		}else{
			if(getX()+dx<=moveRage.left){
				setX(moveRage.left);
			}else if(getX()+w+dx>=moveRage.right){
				setX(moveRage.right-w);
			}else{
				setX(getCenterX() + dx - w/2);
			}
			
			if(getY()+dy<=moveRage.top){
				setY(moveRage.top);
			}else if(getY()+h+dy>=moveRage.bottom){
				setY(moveRage.bottom - h);
			}else{
				setY(getCenterY() + dy - h/2);
			}
		}
//		if(parent!=null){
//			return;
//		}
//		
//		for(ALayer layer : layers){
//			((Sprite)layer).move(dx, dy);
//		}
	} 
	
	public void frameTrig(){
		
		if(action!=null)
			action.trigger();
		if(currentAction!=null)
			currentAction.trigger();
	}
	
	public String getActionName(){
		return currentAction.name;
	}
	
	public void forceToNextFrameBitmap(){
		currentAction.forceToNextBitmap();
	}
	
	public boolean isNeedCreateNewInstance(){
		return false;
	}
	
	public boolean isNeedRemoveInstance(){
		return getX()<0 || getX() > CommonUtil.screenWidth || getY() < 0 || getY() > CommonUtil.screenHeight;
	}

	class SpriteAction {
		public int[] frames;
		public int[] frameTime;

		public Bitmap[] bitmapFrames;
		
		public boolean isLoop;
		
		public String name;
		
		protected long updateTime;

		public float scale;
		
		public IActionListener actionListener = new DefaultActionListener();
		
		public void nextFrame() {
			if (System.currentTimeMillis() > updateTime) {
				nextFrameBySequence();
				updateTime = System.currentTimeMillis() + frameTime[frameIdx];
			}
		}
		
		public void nextBitmap(){			
			if (System.currentTimeMillis() > updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx+1);
//				frameIdx++;
//				frameIdx %= bitmapFrames.length;
				
				if(!isLoop && frameIdx==bitmapFrames.length-1){
					bitmap = bitmapFrames[frameIdx];
					isStop = true;
					actionListener.actionFinish();
				}else{
					bitmap = bitmapFrames[frameIdx];
					
					frameIdx++;// 帧下标增加
					frameIdx %= bitmapFrames.length;
					
					updateTime = System.currentTimeMillis() + frameTime[frameIdx];
					
					int w = bitmap.getWidth();
					int h = bitmap.getHeight();
					
					setWidth(bitmap.getWidth());
					setHeight(bitmap.getHeight());
					int periousId = frameIdx-1<0 ? bitmapFrames.length+(frameIdx-1) : frameIdx-1;
					actionListener.afterChangeFrame(periousId);
				}
			}
		}
		
		public void forceToNextBitmap(){
			bitmap = bitmapFrames[frameIdx];
			frameIdx++;
			frameIdx %= bitmapFrames.length;
			if(!isLoop && frameIdx==0){
				isStop = true;		
			}
		}
		
		public void trigger(){
			process();
		}
		
		public void initUpdateTime(){
			updateTime = System.currentTimeMillis() + frameTime[frameIdx];
		}
		
		public void nextFrameBySequence()
		{
			if(frames == null)
			{
				currentFrame++;
				if(currentFrame > length-1)
					currentFrame = 0;
			}else
			{
				frameIdx++;
				if(frameIdx > frames.length-1)
					frameIdx = 0;
				currentFrame = frames[frameIdx];
			}
		}
	}
	
	class SpriteActionBaseFPS extends SpriteAction{
		private int triggerCount;
		
		@Override
		public void nextFrame() {
			if (triggerCount >= updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx+1);
//				frameIdx++;
//				frameIdx %= bitmapFrames.length;
				
				if(!isLoop && frameIdx==frames.length-1){
					nextFrameBySequence();
					triggerCount=0;
					isStop = true;
					actionListener.actionFinish();
				}else{
					
					nextFrameBySequence();
					
					
					triggerCount=0;
					updateTime = frameTime[frameIdx];
					
//					int w = bitmap.getWidth();
//					int h = bitmap.getHeight();
//					
//					setWidth(bitmap.getWidth());
//					setHeight(bitmap.getHeight());
					int periousId = frameIdx-1<0 ? frames.length+(frameIdx-1) : frameIdx-1;
					actionListener.afterChangeFrame(periousId);
				}
			}
		}
		
		@Override
		public void nextBitmap(){			
			if (triggerCount >= updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx+1);
//				frameIdx++;
//				frameIdx %= bitmapFrames.length;
				
				if(!isLoop && frameIdx==bitmapFrames.length-1){
					bitmap = bitmapFrames[frameIdx];
					triggerCount=0;
					isStop = true;
					actionListener.actionFinish();
				}else{
					bitmap = bitmapFrames[frameIdx];
					
					frameIdx++;// 帧下标增加
					frameIdx %= bitmapFrames.length;
					
					triggerCount=0;
					updateTime = frameTime[frameIdx];
					
					int w = bitmap.getWidth();
					int h = bitmap.getHeight();
					
					setWidth(bitmap.getWidth());
					setHeight(bitmap.getHeight());
					int periousId = frameIdx-1<0 ? bitmapFrames.length+(frameIdx-1) : frameIdx-1;
					actionListener.afterChangeFrame(periousId);
				}
			}
		}
		
		@Override
		public void trigger(){
			
			if(!isStop)
				triggerCount++;
			process();
		}
		
		@Override
		public void initUpdateTime(){
			updateTime = frameTime[frameIdx];
		}
	}
}

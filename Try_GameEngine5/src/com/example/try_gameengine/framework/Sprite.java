package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.loon.framework.android.game.physics.LWorld;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.try_gameengine.action.MAction;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.framework.Config.DestanceType;
import com.example.try_gameengine.physics.PhysicsBody;
import com.example.try_gameengine.utils.SpriteDetectAreaHandler;

public class Sprite extends Layer {
	public int frameIdx;// 當前幀下標
	public int currentFrame = 0;// 當前幀
	public Hashtable<String, SpriteAction> actions;// 動作集合
	public SpriteAction currentAction;// 當前動作

	public boolean isStop = false;
//	public boolean isEnableInterruptAction = false;
	public float scale = 1.0f;
	public boolean canCollision = true;
	public MovementAction action;
	public ConcurrentLinkedQueue<MovementAction> movementActions = new ConcurrentLinkedQueue<MovementAction>();
	
	protected RectF moveRage;
	private MoveRageType moveRageType = MoveRageType.StopOneSide;
	private int moveRageReflectFactorX = 1;
	private int moveRageReflectFactorY = 1;
	
	private int bitmapOrginalFrameWidth;
	private int bitmapOrginalFrameHeight;
	private float frameWidth;
	private float frameHeight;
	private int length;
	private int[] frameSequence;
	private  int frameIndex; 
	
	protected boolean isCollisionRectFEnable = false;
	protected RectF collisionRectF;
	private float collisionRectFWidth, collisionRectFHeight;
	private float collisionOffsetX, collisionOffsetY;
	
	private PhysicsBody physicsBody;
	
	private SpriteDetectAreaHandler spriteDetectAreaHandler;
	
	public enum MoveRageType{
		StopOneSide, StopInCurrentPosition, StopAll, Reflect
	}
	
	public Sprite(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		actions = new Hashtable<String, Sprite.SpriteAction>();// 用Hashtable保存動作集合
		
		initCollisionRectF();
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

	     initCollisionRectF();
	}
	
	public Sprite(Bitmap bitmap, int resId, int w, int h, float scale, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	      
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

		initCollisionRectF();
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
	     
	     initCollisionRectF();
	}
	
	public Sprite(Bitmap bitmap, float x, float y, int resId, int w, int h, float scale, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		this.scale = scale;
		Matrix matrix = new Matrix();
	     matrix.postScale(scale, scale);
	      
	     Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

		setPosition(x, y);
		
		initCollisionRectF();
	}
	
	//It has bug for centerX == x, if use this, need setWH and setPosition again! 
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
	     
	     initCollisionRectF();
	}
	
	private void initCollisionRectF(){
		collisionRectFWidth = w;
		collisionRectFHeight = h;
		collisionRectF = new RectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
	}
	
	public void setCollisionOffsetX(float collisionOffsetX){
		this.collisionOffsetX = collisionOffsetX;
	}
	
	public void setCollisionOffsetY(float collisionOffsetY){
		this.collisionOffsetY = collisionOffsetY;
	}
	
	public void setCollisionOffsetXY(float collisionOffsetX, float collisionOffsetY){
		this.collisionOffsetX = collisionOffsetX;
		this.collisionOffsetY = collisionOffsetY;
	}
	
	public void setBitmapAndFrameWH(Bitmap bitmap,int frameWidth ,int frameHeight ){
		this.bitmap = bitmap;
		this.bitmapOrginalFrameWidth = frameWidth;
		this.bitmapOrginalFrameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		setWidth(frameWidth);
		setHeight(frameHeight);
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
		movementActions.clear();
		movementActions.add(this.action);
	}
	
	public MovementAction getMovementAction(){
		return action;
	}
	
	private void addMovementAction(MovementAction movementAction){
		this.action = movementAction;
		movementActions.add(this.action);
	}
	
	public void removeAllMovementActions(){
		for(MovementAction action : movementActions){
			if(action.controller!=null)
				action.controller.cancelAllMove();
		}
		movementActions.clear();
	}
	
	public void setAction(String actionName) {
		if(actionName==null)
			return;
		frameIdx = 0;
		currentFrame = 0;
		if(currentAction!=null)
			currentAction.forceToFinish();
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
	
	public RectF getMoveRage() {
		return moveRage;
	}

	public void setMoveRageType(MoveRageType moveRageType){
		this.moveRageType = moveRageType;
	}
	
	public MoveRageType getMoveRageType(){
		return moveRageType;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {	
		if(bitmap!=null){
			Paint originalPaint = paint;		
			//use self paint first
			if(getPaint()!=null){
				paint = getPaint();
			}
			
			if(length>0){
				paint(canvas,paint);		
				//use self paint first
				paint = originalPaint;
			}else{
				if(spriteMatrix!=null){
					canvas.setMatrix(spriteMatrix);
				}
		
				src.left = (int) (currentFrame * w * scale);// 左端寬度：當前幀乘上幀的寬度再乘上圖片縮放率
				src.top = 0;
				src.right = (int) (src.left + w * scale);// 右端寬度：左端寬度加上(幀的寬度乘上圖片縮放率)
				src.bottom = h;
				
				dst.left = (float) (centerX - w / 2);
				dst.top = (float) (centerY - h / 2);
				dst.right = (float) (dst.left + w * scale);
				dst.bottom = (float) (dst.top + h * scale);
				
				if(isComposite()){
					if(parent!=null){
						PointF locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
						dst.left = locationInScene.x;
						dst.top = locationInScene.y;
						dst.right = (float) (dst.left + w * scale);
						dst.bottom = (float) (dst.top + h * scale);
					}
				}
				
				customBitampSRCandDST(src, dst);
				canvas.drawBitmap(bitmap, src, dst, paint);
			}
			
			//use self paint first
			paint = originalPaint;
		}
		
		
		for(ILayer layer : layers){
			if(layer.isComposite()){
				layer.drawSelf(canvas, paint);
			}
		}
		
	}
	
	public void customBitampSRCandDST(Rect src, RectF dst){
		
	}
	
	public Matrix spriteMatrix;
	public boolean drawWithoutClip = false;
	public float drawOffsetX;
	private float xScale = 1.0f;
	
	public void setXscale(float xScale){
		this.xScale = xScale;
		if(spriteMatrix==null)
			spriteMatrix = new Matrix();
		if(spriteMatrix!=null){
			spriteMatrix.reset();
			spriteMatrix.postScale(xScale, 1, getX() + w/2, getY() + h/2 );
		}
	}
	public void paint(Canvas canvas,Paint paint)
	{	
		if(spriteMatrix==null)
			spriteMatrix = new Matrix();
		
		canvas.save();
		float x = getX();
		float y = getY();
		
		if(isComposite()){
			if(parent!=null){
				PointF locationInScene = parent.locationInSceneByCompositeLocation(x, y);
				x = locationInScene.x;
				y = locationInScene.y;
			}
		}
		
		if(spriteMatrix!=null){
//			canvas.clipRect(x, y, x+frameWidth, y+frameHeight);
//			canvas.drawBitmap(bitmap, spriteMatrix, paint);
			canvas.setMatrix(spriteMatrix);
			canvas.clipRect(x+drawOffsetX, y, x+frameWidth+drawOffsetX, y+frameHeight);
//			canvas.drawBitmap(bitmap, x, y, paint);
			canvas.drawBitmap(bitmap, x-(currentFrame%(bitmap.getWidth()/(int)frameWidth))*frameWidth+drawOffsetX, 
					y - (currentFrame/(bitmap.getWidth()/(int)frameWidth))*frameHeight, paint);
		}else if(!drawWithoutClip){
			canvas.clipRect(x+drawOffsetX, y, x+frameWidth+drawOffsetX, y+frameHeight);
			canvas.drawBitmap(bitmap, x-(currentFrame%(bitmap.getWidth()/(int)frameWidth))*frameWidth+drawOffsetX, 
					y - (currentFrame/(bitmap.getWidth()/(int)frameWidth))*frameHeight, paint);
		}else{
			canvas.setMatrix(spriteMatrix);
			
			canvas.drawBitmap(bitmap, new Rect((int)(currentFrame%(bitmap.getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameWidth+(int)drawOffsetX
					, (int)(currentFrame/(bitmap.getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameHeight
					,(int)(currentFrame%(bitmap.getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameWidth+bitmapOrginalFrameWidth+(int)drawOffsetX
					, (int)(currentFrame/(bitmap.getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameHeight+bitmapOrginalFrameHeight)
			, new RectF(x+drawOffsetX
					, y
					, x+frameWidth+drawOffsetX
					, y+frameHeight)
			, paint);
		}
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
	
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, float scale, boolean isLoop, IActionListener actionListener) {
		SpriteAction sp = new SpriteActionBaseFPS();
		sp.frames = sequence;// 幀圖片集合
		sp.frameTime = frameTriggerTimes;//每一幀切換的時間
		sp.isLoop = isLoop;
		if(name!=null)
			sp.name = name;
		else
			sp.name = "";
		sp.scale = scale;
		sp.actionListener = actionListener;
		actions.put(sp.name, sp);
		setAction(sp.name);
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
		if(Config.destanceType == DestanceType.DpToPx){
			dx = CommonUtil.convertDpToPixel(dx);
			dy = CommonUtil.convertDpToPixel(dy);
		}else if(Config.destanceType == DestanceType.PxToDp){
			dx = CommonUtil.convertPixelsToDp(dx);
			dy = CommonUtil.convertPixelsToDp(dy);
		}else if(Config.destanceType == DestanceType.ScreenPersent){
			dx = CommonUtil.converDxWithDefaultScreenPersentToCurrentScreenPersent(dx);
			dy = CommonUtil.converDyWithDefaultScreenPersentToCurrentScreenPersent(dy);
		}
		
		moveXY(dx, dy);
	} 
	
	public void moveWithPx(float dx, float dy){
		moveXY(dx, dy);
	}
	
	private void moveXY(float dx, float dy) {	
		if(moveRage==null){
			setX(getCenterX() + dx - w/2);
			setY(getCenterY() + dy - h/2);
		}else{
			switch (moveRageType) {
			case StopOneSide:
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
				break;
			case StopInCurrentPosition:
							
				break;
			case StopAll:
				
				break;
			case Reflect:
				
				dx *= moveRageReflectFactorX;
				dy *= moveRageReflectFactorY;
				
				if(getX()+dx<=moveRage.left){
					moveRageReflectFactorX *= -1;
					setX(moveRage.left);
				}else if(getX()+w+dx>=moveRage.right){
					moveRageReflectFactorX *= -1;
					setX(moveRage.right-w);
				}else{
					setX(getCenterX() + dx - w/2);
				}
				
				if(getY()+dy<=moveRage.top){
					moveRageReflectFactorY *= -1;
					setY(moveRage.top);
				}else if(getY()+h+dy>=moveRage.bottom){
					moveRageReflectFactorY *= -1;
					setY(moveRage.bottom - h);
				}else{
					setY(getCenterY() + dy - h/2);
				}
				break;
			default:
				break;
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
	
	@Override
	public void frameTrig(){
		
//		if(action!=null)
//			action.trigger();
		for(MovementAction action : movementActions){
			action.trigger();
		}
		if(currentAction!=null)
			currentAction.trigger();
		
		super.frameTrig();
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
	
	public void setCollisionRectF(RectF collisionRectF){
		this.collisionRectF = collisionRectF;
	}
	
	public void setCollisionRectF(float left, float top, float right, float bottom){
		if(!isCollisionRectFEnable)
			return;
		if(collisionRectF==null)
			collisionRectF = new RectF(left, top, right, bottom);
		else
			collisionRectF.set(left, top, right, bottom);
	}
	
	public RectF getCollisionRectF(){
		return collisionRectF;
	}
	
	public void setCollisionRectFEnable(boolean isCollisionRectFEnable){
		this.isCollisionRectFEnable = isCollisionRectFEnable;
	}
	
	public boolean isCollisionRectFEnable(){
		return isCollisionRectFEnable;
	}
	
	public void setCollisionRectFWidth(float collisionRectFWidth){
		this.collisionRectFWidth = collisionRectFWidth;
	}
	
	public void setCollisionRectFHeight(float collisionRectFHeight){
		this.collisionRectFHeight = collisionRectFHeight;
	}
	
	public void setCollisionRectFWH(float collisionRectFWidth, float collisionRectFHeight){
		this.collisionRectFWidth = collisionRectFWidth;
		this.collisionRectFHeight = collisionRectFHeight;
	}
	
	public float getFrameWidth(){
		return frameWidth;
	}
	
	public void setFrameWidth(float frameWidth){
		this.frameWidth = frameWidth;
	}
	
	public float getFrameHeight(){
		return frameHeight;
	}
	
	public void setFrameHeight(float frameHeight){
		this.frameHeight = frameHeight;
	}
	
	public void resetFrameWH(){
		this.frameWidth = bitmapOrginalFrameWidth;
		this.frameHeight = bitmapOrginalFrameHeight;
	}
	
	public int getBitmapOrginalFrameWidth(){
		return bitmapOrginalFrameWidth;
	}
	
	public int getBitmapOrginalFrameHright(){
		return bitmapOrginalFrameHeight;
	}
	
	public void runMovementAction(MovementAction movementAction){
		initRunMovementAction(movementAction);
		setMovementAction(movementAction);
	}
	
	public void runMovementActionAndAppend(MovementAction movementAction){
		initRunMovementAction(movementAction);
		addMovementAction(movementAction);
	}
	
	private void initRunMovementAction(MovementAction movementAction){
		MAction.attachToTargetSprite(movementAction, this);
		MAction.setDefaultTimeToTickListenerIfNotSetYetToTargetSprite(movementAction, this);
		if(movementAction.controller==null)
			movementAction.setMovementActionController(new MovementAtionController());
		movementAction.getCurrentInfoList();
		movementAction.modifyWithSpriteXY(getX(), getY());
		movementAction.initMovementAction();
		movementAction.start();
	}
	
	public void cancelCurrentMovementAction(){
		if(getMovementAction()!=null)
			getMovementAction().controller.cancelAllMove();
	}
	
	public void cancelCurrentMovementActionAndCurrentMovementActionInChirdren(){
		cancelCurrentMovementAction();
		
		checkChildrenForCancelCurrentMovementAction(this);
	}
	
	protected void checkChildrenForCancelCurrentMovementAction(ILayer checkLayer){
		for(ILayer layer : checkLayer.getLayers()){
			if(layer.isComposite() && layer instanceof Sprite){
				((Sprite)layer).cancelCurrentMovementActionAndCurrentMovementActionInChirdren();
			}else if(layer.isComposite()){
				checkChildrenForCancelCurrentMovementAction(layer);
			}
		}
	}
	
	public void setPhysicsBody(PhysicsBody physicsBody, LWorld world){
		this.physicsBody = physicsBody;
		this.physicsBody.setUserData(this);
		physicsBody.addToWorld(world);
	}
	
	public void setDynamic(boolean dynamic){
		this.physicsBody.setDynamic(dynamic);
	}
	
	public void setSpriteDetectAreaHandler(SpriteDetectAreaHandler spriteDetectAreaHandler){
		this.spriteDetectAreaHandler = spriteDetectAreaHandler;
		this.spriteDetectAreaHandler.setObjectTag(this);
	}
	
	public SpriteDetectAreaHandler getSpriteDetectAreaHandler(){
		return spriteDetectAreaHandler;
	}
	
	protected void updateSpriteDetectAreaCenter(PointF center){
		if(spriteDetectAreaHandler!=null)
			spriteDetectAreaHandler.updateSpriteDetectAreaCenter(center);
	}
	
	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		super.setX(x);
		
		if(isComposite()){
			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+w/2, locationInScene.y+h/2));
		}else{
			setCollisionRectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}	
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
		
		if(isComposite()){
			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+w/2, locationInScene.y+h/2));
		}else{
			setCollisionRectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);	
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);	
		
		if(isComposite()){
			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+w/2, locationInScene.y+h/2));
		}else{
			setCollisionRectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	public void setInitWidth(int w) {
		// TODO Auto-generated method stub
		this.setWidth(w);
	}
	
	@Override
	public void setInitHeight(int h) {
		// TODO Auto-generated method stub
		this.setHeight(h);
	}
	
	@Override
	public void setWidth(int w) {
		// TODO Auto-generated method stub
		super.setWidth(w);
		collisionOffsetX = (float)w/this.w*collisionOffsetX;
		if(collisionRectFWidth==0)
			collisionRectFWidth = w;
		else
			collisionRectFWidth = (float)w/this.w*collisionRectFWidth;
		setCollisionRectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
		if(isComposite()){
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+w/2, locationInScene.y+h/2));
		}else{
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}	
	}
	
	@Override
	public void setHeight(int h) {
		// TODO Auto-generated method stub
		super.setHeight(h);
		collisionOffsetY = (float)h/this.h*collisionOffsetY;
		if(collisionRectFHeight==0)
			collisionRectFHeight = h;
		else
			collisionRectFHeight = (float)h/this.h*collisionRectFHeight;
		setCollisionRectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
		if(isComposite()){
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+w/2, locationInScene.y+h/2));
		}else{
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	protected void willDoSometiongBeforeOneOfAncestorLayerWillRemoved() {
		// TODO Auto-generated method stub
		if(isComposite() || getParent()==null)
			cancelCurrentMovementAction();
		super.willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}
	
	public class SpriteAction {
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
		
		public void forceToFinish(){
			if(!isStop){
				isStop = true;
				actionListener.actionFinish();
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
	
	public class SpriteActionBaseFPS extends SpriteAction{
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

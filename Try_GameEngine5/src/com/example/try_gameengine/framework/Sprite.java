package com.example.try_gameengine.framework;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.loon.framework.android.game.physics.LWorld;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import com.example.try_gameengine.action.MAction;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.framework.Config.DestanceType;
import com.example.try_gameengine.physics.PhysicsBody;
import com.example.try_gameengine.utils.SpriteDetectAreaHandler;

/**
 * @author irons
 *
 */
/**
 * @author irons
 *
 */
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
	private int frameColNum;
	private int frameRowNum;
	private int length;
	private int[] frameSequence;
	private  int frameIndex; 
	
	protected boolean isCollisionRectFEnable = false;
	protected RectF collisionRectF;
	private float collisionRectFWidth, collisionRectFHeight;
	private float collisionOffsetX, collisionOffsetY;
	
	private PhysicsBody physicsBody;
	
	private SpriteDetectAreaHandler spriteDetectAreaHandler;
	
	protected PointF locationLeftTopInScene = new PointF(); 
	
	public Matrix spriteMatrix;
	public boolean drawWithoutClip = false;
	public float drawOffsetX;
	private float xScale = 1.0f;
	private float yScale = 1.0f;
	private float xScaleForBitmapWidth = 1.0f;
	private float yScaleForBitmapHeight = 1.0f;
	private int widthWithoutxScale;
	private int heightWithoutyScale;
	
	private float rotation;
	private RotationType rotationType = RotationType.AUTO;
	
	public enum MoveRageType{
		StopOneSide, StopInCurrentPosition, StopAll, Reflect
	}
	
	public enum RotationType{
		AUTO, // Default, the root layer rotate with center and the child layers rotate with anchor point.
		ROTATE_WITH_CENTER,
		ROTATE_WITH_ANCHOR_POINT
	}
	
	public Sprite(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		setBitmap(bitmap);
		setWidth(w);
		setHeight(h);
		
		actions = new Hashtable<String, Sprite.SpriteAction>();// 用Hashtable保存動作集合
		
		initCollisionRectF();
	}
	
	public Sprite(Bitmap bitmap, int scale, boolean autoAdd) {
		super(bitmap, 0, 0, autoAdd);
		setBitmap(bitmap);
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
		setBitmap(bitmap);
		this.scale = scale;
		Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	      
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

		setWidth(w);
		setHeight(h);
		
		initCollisionRectF();
	}
	
	public Sprite(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, 0, 0, autoAdd);
		setBitmap(bitmap);
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
		setBitmap(bitmap);
		this.scale = scale;
		Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	      
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		actions = new Hashtable<String, Sprite.SpriteAction>();

		setWidth(w);
		setHeight(h);
		
		setPosition(x, y);
		initCollisionRectF();
	}
	
	//It has bug for centerX == x, if use this, need setWH and setPosition again! 
	public Sprite(float x, float y, boolean autoAdd) {
		super(0, 0, autoAdd);
		actions = new Hashtable<String, Sprite.SpriteAction>();
		setPosition(x, y);
		initCollisionRectF();
	}
	
	/**
	 * @param autoAdd
	 */
	public Sprite(boolean autoAdd) {
		super(autoAdd);

	     actions = new Hashtable<String, Sprite.SpriteAction>();  
	     initCollisionRectF();
	}
	
	/**
	 * 
	 */
	public Sprite() {
		super(false);

	     actions = new Hashtable<String, Sprite.SpriteAction>();
	     initCollisionRectF();
	}
	
	/**
	 * 
	 */
	private void initCollisionRectF(){
		collisionRectFWidth = getWidth();
		collisionRectFHeight = getHeight();
		collisionRectF = new RectF(getX()+collisionOffsetX, getY()+collisionOffsetY, getX()+collisionOffsetX+collisionRectFWidth, getY()+collisionOffsetY+collisionRectFHeight);
	}
	
	/**
	 * @param collisionOffsetX
	 */
	public void setCollisionOffsetX(float collisionOffsetX){
		this.collisionOffsetX = collisionOffsetX;
	}
	
	/**
	 * @param collisionOffsetY
	 */
	public void setCollisionOffsetY(float collisionOffsetY){
		this.collisionOffsetY = collisionOffsetY;
	}
	
	/**
	 * @param collisionOffsetX
	 * @param collisionOffsetY
	 */
	public void setCollisionOffsetXY(float collisionOffsetX, float collisionOffsetY){
		this.collisionOffsetX = collisionOffsetX;
		this.collisionOffsetY = collisionOffsetY;
	}
	
	@Override
	public void setBitmap(Bitmap bitmap) {
		// TODO Auto-generated method stub
		super.setBitmap(bitmap);
		if(isBitmapSacleToFitSize()){
			setWidth(getWidth());
			setHeight(getHeight());
		}
	}
	
	/**
	 * @param bitmap
	 * @param frameWidth
	 * @param frameHeight
	 */
	public void setBitmapAndFrameWH(Bitmap bitmap,int frameWidth ,int frameHeight ){
		this.setBitmap(bitmap);
		this.bitmapOrginalFrameWidth = frameWidth;
		this.bitmapOrginalFrameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameColNum = (int)Math.ceil(bitmap.getWidth()/frameWidth);
		this.frameRowNum = (int)Math.ceil(bitmap.getHeight()/frameHeight);
		this.length = this.frameColNum*this.frameRowNum;
		setWidth(frameWidth);
		setHeight(frameHeight);
	}
	
	/**
	 * @param bitmap
	 * @param frameColNum
	 * @param frameRowNum
	 */
	public void setBitmapAndFrameColAndRowNumAndAutoWH(Bitmap bitmap, int frameColNum , int frameRowNum){
		this.setBitmap(bitmap);
		int frameWidth = bitmap.getWidth()/frameColNum;
		int frameHeight = bitmap.getHeight()/frameRowNum;
		this.bitmapOrginalFrameWidth = frameWidth;
		this.bitmapOrginalFrameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameColNum = frameColNum;
		this.frameRowNum = frameRowNum;
		this.length = frameColNum*frameRowNum;
		setWidth(frameWidth);
		setHeight(frameHeight);
	}
	
	/**
	 * @param bitmap
	 * @param frameWidth
	 * @param frameHeight
	 * @param frameColNum
	 * @param frameRowNum
	 */
	public void setBitmapAndFrameWHAndColAndRowNum(Bitmap bitmap,int frameWidth ,int frameHeight,int frameColNum ,int frameRowNum){
		this.setBitmap(bitmap);
		this.bitmapOrginalFrameWidth = frameWidth;
		this.bitmapOrginalFrameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameColNum = frameColNum;
		this.frameRowNum = frameRowNum;
		this.length = frameColNum*frameRowNum;
		setWidth(frameWidth);
		setHeight(frameHeight);
	}
	
	/**
	 * @param sequence
	 */
	public void setFrameSequence(int[] sequence)
	{			
		this.frameSequence = sequence;
		frameIndex = 0;
		currentFrame = sequence[0];
	}
	
	/**
	 * @param lightImage
	 */
	public void setLightImage(LightImage lightImage){
		if(lightImage.getBitmap()!=null){
			setBitmap(lightImage.getBitmap());
		}else if(getBitmap()==null){
			return;
		}
		
		setBitmapAndFrameWH(getBitmap(), lightImage.getClipIfno().getWidth(), lightImage.getClipIfno().getHeight());
		currentFrame = (int)(lightImage.getClipIfno().getClipStartX()/(frameWidth*frameColNum)) + (int)(lightImage.getClipIfno().getClipStartY()/(frameHeight*frameRowNum))*frameColNum;
	}
	
//	public LightImage getLightImage(){
//		return null;
//	}
	
	/**
	 * @param movementAction
	 */
	public void setMovementAction(MovementAction movementAction){
		this.action = movementAction;
		movementActions.clear();
		movementActions.add(this.action);
	}
	
	/**
	 * @return
	 */
	public MovementAction getMovementAction(){
		return action;
	}
	
	/**
	 * @param movementAction
	 */
	private void addMovementAction(MovementAction movementAction){
		this.action = movementAction;
		movementActions.add(this.action);
	}
	
	/**
	 * 
	 */
	public void removeAllMovementActions(){
		for(MovementAction action : movementActions){
			if(action.controller!=null)
				action.controller.cancelAllMove();
		}
		movementActions.clear();
	}
	
	/**
	 * @param actionName
	 */
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
	
	/**
	 * set move to .
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 */
	public void setMoveRage(float x, float y, float height, float width){
		moveRage = new RectF(x, y, x+width, y+height);
	}
	
	/**
	 * set move range to sprite .
	 * @param moveRage 
	 * 			the range of move.
	 */
	public void setMoveRage(RectF moveRage){
		this.moveRage = moveRage;
	}
	
	/**
	 * get move range from sprite.
	 * @return RectF.
	 */
	public RectF getMoveRage() {
		return moveRage;
	}

	/**
	 * @param moveRageType
	 */
	public void setMoveRageType(MoveRageType moveRageType){
		this.moveRageType = moveRageType;
	}
	
	/**
	 * @return
	 */
	public MoveRageType getMoveRageType(){
		return moveRageType;
	}

	@Override
	protected void doDrawself(Canvas canvas, Paint paint) {
		canvas.save();
		
		do {

		canvas = getClipedCanvas(canvas, paint);
		Paint originalPaint = paint;		
		
//		if(bitmap!=null){		
			if(length>0){
				paint(canvas,paint);		
				//use input paint first
				paint = originalPaint;
			}else{
				boolean isUseCanvasScale = false;
				if(xScale*xScaleForBitmapWidth<0 || yScale*yScaleForBitmapHeight<0){
					isUseCanvasScale = true;
				}
				
				getSrc().left = (int) (currentFrame * getWidth() * scale);// 左端寬度：當前幀乘上幀的寬度再乘上圖片縮放率
				getSrc().top = 0;
				getSrc().right = (int) ((getSrc().left + getWidth() * scale)/(xScaleForBitmapWidth));// 右端寬度：左端寬度加上(幀的寬度乘上圖片縮放率)
				getSrc().bottom = (int) (getHeight()/(yScaleForBitmapHeight));
				getDst().left = (float) (getCenterX() - getWidth() / 2);//try mix anchor point
				getDst().top = (float) (getCenterY() - getHeight() / 2);
				getDst().right = (float) (getDst().left + getWidth() * scale);
				getDst().bottom = (float) (getDst().top + getHeight() * scale);
				
				
					if(isComposite()){
						if(getParent()!=null){
//							dst.left = locationLeftTopInScene.x + getAnchorPoint().x*w;
//							dst.top = locationLeftTopInScene.y + getAnchorPoint().y*h;
							getDst().left = locationLeftTopInScene.x + getAnchorPoint().x*getWidth() -getAnchorPoint().x*getWidth()/xScaleForBitmapWidth;
							getDst().top = locationLeftTopInScene.y + getAnchorPoint().y*getHeight() -getAnchorPoint().y*getHeight()/yScaleForBitmapHeight;
							getDst().right = (float) (getDst().left + getWidth()/xScaleForBitmapWidth * scale);
							getDst().bottom = (float) (getDst().top + getHeight()/yScaleForBitmapHeight * scale);
						}else{
						}
						
					}else{
						getDst().left = (float) (getAnchorPointXY().x-getAnchorPoint().x*getWidth()/xScaleForBitmapWidth);//try mix anchor point
						getDst().top = (float) (getAnchorPointXY().y-getAnchorPoint().y*getHeight()/yScaleForBitmapHeight);
						getDst().right = (float) (getDst().left + getWidth()/xScaleForBitmapWidth * scale);
						getDst().bottom = (float) (getDst().top + getHeight()/yScaleForBitmapHeight * scale);
					}
					
					if(spriteMatrix!=null){
						canvas.concat(spriteMatrix);
					}
					
					drawRectF = getDst();
					drawBackgroundColor(canvas, paint, drawRectF);
					if(getBitmap()!=null)
						canvas.drawBitmap(getBitmap(), getDst().left, getDst().top, paint);
			}
//		}
		
		//use input paint first
		paint = originalPaint;
		
		} while (false);
		
		canvas.restore();
	}
	
	@Override
	protected void doDrawChildren(Canvas canvas, Paint paint) {
		for(ILayer layer : getLayers()){
			if(layer.isComposite() && !layer.isAutoAdd()){ //if the layer is auto add, not draw.
				layer.drawSelf(canvas, paint);
			}
		}
	}
	
	/**
	 * @param src
	 * @param dst
	 */
	public void customBitampSRCandDST(Rect src, RectF dst){
		
	}
	
	/**
	 * @param xScale
	 */
	public void setXscale(float xScale){
		float factor = xScale/this.xScale;
		this.xScale = xScale;
		setSuperWidth((int)(widthWithoutxScale*Math.abs(xScale)));
//		colculationScale();
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child instanceof Sprite){
					((Sprite)child).setXscale(((Sprite)child).getXscale()*factor);
				}
			}		
		}
	}
	
	/**
	 * @return
	 */
	public float getXscale(){
		return xScale;
	}
	
	/**
	 * @param yScale
	 */
	public void setYscale(float yScale){
		float factor = yScale/this.yScale;
		this.yScale = yScale;
//		setHeight((int)(getHeight()*Math.abs(yScale)));
		setSuperHeight((int)(heightWithoutyScale*Math.abs(yScale)));
//		colculationScale();
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child instanceof Sprite){
					((Sprite)child).setYscale(((Sprite)child).getYscale()*factor);
				}
			}		
		}
	}
	
	/**
	 * @return
	 */
	public float getYscale(){
		return yScale;
	}
	
	/**
	 * @param rotation
	 */
	public void setRotation(float rotation){
		float offsetRotation = rotation - this.rotation;
		this.rotation = rotation;
		
		if(getParent()!=null && isComposite() && getLayerParam().isEnabledBindPositionXY()){
			if(getParent() instanceof Sprite){
//				float position[] = new float[]{getParent().getLeft() + ((Sprite)getParent()).getAnchorPoint().x * ((Sprite)getParent()).getWidth(), getParent().getTop() + ((Sprite)getParent()).getAnchorPoint().y * ((Sprite)getParent()).getHeight()};
				float position[] = new float[]{getParent().getLeft() + ((Sprite)getParent()).getAnchorPoint().x * ((Sprite)getParent()).getWidth() + getLayerParam().getBindPositionX(), getParent().getTop() + ((Sprite)getParent()).getAnchorPoint().y * ((Sprite)getParent()).getHeight() + getLayerParam().getBindPositionY()};
				Matrix matrix = ((Sprite)getParent()).spriteMatrix;
				matrix.mapPoints(position);
				setPosition(position[0] - getParent().getLeft() , position[1] - getParent().getTop());
			}
		}else
			colculationMatrix();
		
		if(getLayers().size()!=0){
			for(ILayer child : getLayers()){
				if(child.isComposite() && child instanceof Sprite){
					((Sprite)child).setRotation(((Sprite)child).getRotation() + offsetRotation);
				}
			}		
		}
	}
	
	/**
	 * @return
	 */
	public float getRotation(){
		return rotation;
	}
	
	/**
	 * @param rotationType
	 */
	public void setRotationType(RotationType rotationType){
		this.rotationType = rotationType;
		setRotation(getRotation()); //reset rotation to reset the child layers' position.
	}
	
	/**
	 * @return
	 */
	public RotationType getRotationType(){
		return rotationType;
	}
	
	/**
	 * @param canvas
	 * @param paint
	 * @param drawRectF
	 */
	private void drawBackgroundColor(Canvas canvas, Paint paint, RectF drawRectF){
		//use input paint first
		int oldColor = 0;
		Style oldStyle = null;
		if(paint==null && getPaint()!=null){
			paint = getPaint();
			if(getBackgroundColor()!=NONE_COLOR){
				oldColor = getPaint().getColor();
				oldStyle = getPaint().getStyle();
				getPaint().setColor(getBackgroundColor());
				getPaint().setStyle(Style.FILL);
				int oldAlpha = getPaint().getAlpha();
				getPaint().setAlpha((int) (getAlpha()*oldAlpha/255.0f));
//				canvas.drawRect(x, y, x + w, y + h,paint);
				canvas.drawRect(drawRectF, paint);
				getPaint().setColor(oldColor);
				getPaint().setStyle(oldStyle);
				getPaint().setAlpha(oldAlpha);
			}
		}else if(paint!=null){
			canvas.drawRect(drawRectF, paint);
		}
	}
	
	RectF drawRectF = null;
	private void paint(Canvas canvas,Paint paint)
	{	
		if(spriteMatrix==null)
			spriteMatrix = new Matrix();

		float x = getX();
		float y = getY();
		
		if(isComposite()){
			if(getParent()!=null){
				PointF locationInScene = getParent().locationInSceneByCompositeLocation(x, y);
				x = locationInScene.x;
				y = locationInScene.y;
			}
		}
		
		
		
		if(spriteMatrix!=null){
//			canvas.setMatrix(spriteMatrix);
			
			canvas.concat(spriteMatrix);
//			if(!doClip(canvas))
//				return;
			
			drawRectF = new RectF(x - getAnchorPoint().x*getWidth()/xScaleForBitmapWidth, y - getAnchorPoint().y*getHeight()/yScaleForBitmapHeight, x - getAnchorPoint().x*getWidth()/xScaleForBitmapWidth + getWidth()/xScaleForBitmapWidth,y - getAnchorPoint().y*getHeight()/yScaleForBitmapHeight + getHeight()/yScaleForBitmapHeight);
			canvas.clipRect(drawRectF);
			
			drawBackgroundColor(canvas, paint, drawRectF);

			float xx = x - ((float)getBitmap().getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)getBitmap().getWidth())/frameColNum)+drawOffsetX;
			float yy = y - ((float)getBitmap().getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)getBitmap().getHeight())/frameRowNum);
			canvas.drawBitmap(getBitmap(), xx, yy, paint);
			
//			if(xScale*xScaleForBitmapWidth<0 && yScale*yScaleForBitmapHeight<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else if(xScale*xScaleForBitmapWidth<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else if(yScale*yScaleForBitmapHeight<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else{
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
		}else if(!drawWithoutClip){
			drawRectF = new RectF(x+drawOffsetX, y, x+frameWidth+drawOffsetX, y+frameHeight);
			canvas.clipRect(drawRectF);
			drawBackgroundColor(canvas, paint, drawRectF);
			canvas.drawBitmap(getBitmap(), x-(currentFrame%(getBitmap().getWidth()/(int)frameWidth))*frameWidth+drawOffsetX, 
					y - (currentFrame/(getBitmap().getWidth()/(int)frameWidth))*frameHeight, paint);
		}else{
//			canvas.setMatrix(spriteMatrix);
			canvas.concat(spriteMatrix);
//			if(!doClip(canvas))
//				return;
			
			drawRectF = new RectF(x+drawOffsetX
					, y
					, x+frameWidth+drawOffsetX
					, y+frameHeight);
			drawBackgroundColor(canvas, paint, drawRectF);
			canvas.drawBitmap(getBitmap(), new Rect((int)(currentFrame%(getBitmap().getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameWidth+(int)drawOffsetX
					, (int)(currentFrame/(getBitmap().getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameHeight
					,(int)(currentFrame%(getBitmap().getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameWidth+bitmapOrginalFrameWidth+(int)drawOffsetX
					, (int)(currentFrame/(getBitmap().getWidth()/bitmapOrginalFrameWidth))*bitmapOrginalFrameHeight+bitmapOrginalFrameHeight)
			, drawRectF
			, paint);
		}
		
		getDst().left = (float) (getCenterX() - getWidth() / 2);
		getDst().top = (float) (getCenterY() - getHeight() / 2);
		getDst().right = (float) (getDst().left + getWidth() * scale);
		getDst().bottom = (float) (getDst().top + getHeight() * scale);
	}

	/**
	 * Add sprite action detail.
	 * @param name
	 * 			Name of sprite action.
	 * @param frames
	 * 			frames of sprite action.
	 * @param frameTime
	 * 			frameTime of sprite action.
	 */
	public void addAction(String name, int[] frames, int[] frameTime) {
		SpriteAction sp = new SpriteAction();
		sp.frames = frames;//幀的數量
		sp.frameTime = frameTime;//每一幀切換的時間
		sp.name = name;
		actions.put(name, sp);
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * 			Name of sprite action.
	 * @param bitmapFrames
	 * 			frames of sprite action.
	 * @param frameTime
	 * 			frameTime of sprite action.
	 */
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime) {
		addAction(name, bitmapFrames, frameTime, 1.0f, true, new DefaultActionListener());
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * 			Name of sprite action.
	 * @param bitmapFrames
	 * 			frames of sprite action.
	 * @param frameTime
	 * 			frameTime of sprite action.
	 * @param isLoop
	 * 			
	 */
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime, boolean isLoop) {
		addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, new DefaultActionListener());
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * 			Name of sprite action.
	 * @param bitmapFrames
	 * 			frames of sprite action.
	 * @param frameTime
	 * 			frameTime of sprite action.
	 * @param isLoop
	 * 			
	 * @param actionListener
	 * 			
	 */
	public void addAction(String name, Bitmap[] bitmapFrames, int[] frameTime, boolean isLoop, IActionListener actionListener) {
		addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, actionListener);
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * @param bitmapFrames
	 * @param frameTime
	 * 			frames of sprite action.
	 * @param scale
	 * @param isLoop
	 * @param actionListener
	 */
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
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * @param bitmapFrames
	 * @param frameTriggerTimes
	 * 			frames of sprite action.
	 */
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * @param bitmapFrames
	 * @param frameTriggerTimes
	 * @param isLoop
	 */
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes, boolean isLoop) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * @param bitmapFrames
	 * @param frameTriggerTimes
	 * @param isLoop
	 * @param actionListener
	 */
	public void addActionFPS(String name, Bitmap[] bitmapFrames, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	/**
	 * Add sprite action detail.
	 * @param name
	 * @param bitmapFrames
	 * @param frameTriggerTimes
	 * @param scale
	 * @param isLoop
	 * @param actionListener
	 */
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
	
	/**
	 * run sprite action with detail.
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 */
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param isLoop
	 */
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param isLoop
	 * @param actionListener
	 */
	public void runActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param scale
	 * @param isLoop
	 * @param actionListener
	 */
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
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 */
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, true, new DefaultActionListener());
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param isLoop
	 */
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, new DefaultActionListener());
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param isLoop
	 * @param actionListener
	 */
	public void addActionFPSFrame(String name, int[] sequence, int[] frameTriggerTimes, boolean isLoop, IActionListener actionListener) {
		addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener);
	}
	
	/**
	 * @param name
	 * @param sequence
	 * @param frameTriggerTimes
	 * @param scale
	 * @param isLoop
	 * @param actionListener
	 */
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

	/**
	 * 
	 */
	private void process(){
		if (currentAction != null) {
			if(currentAction.frames!=null){
				currentAction.nextFrame();
			}else{
				currentAction.nextBitmap();
			}		
		}	
	}
	
	/**
	 * @param dx
	 * @param dy
	 */
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
	
	/**
	 * @param dx
	 * @param dy
	 */
	public void moveWithPx(float dx, float dy){
		moveXY(dx, dy);
	}
	
	/**
	 * @param dx
	 * @param dy
	 */
	private void moveXY(float dx, float dy) {	
		if(moveRage==null){
//			setX(getCenterX() + dx - w/2);
//			setY(getCenterY() + dy - h/2);
			setX(getX() + dx);
			setY(getY() + dy);
		}else{
			switch (moveRageType) {
			case StopOneSide:
				if(getX()+dx<=moveRage.left){
					setX(moveRage.left);
				}else if(getX()+getWidth()+dx>=moveRage.right){
					setX(moveRage.right-getWidth());
				}else{
					setX(getCenterX() + dx - getWidth()/2);
				}
				
				if(getY()+dy<=moveRage.top){
					setY(moveRage.top);
				}else if(getY()+getHeight()+dy>=moveRage.bottom){
					setY(moveRage.bottom - getHeight());
				}else{
					setY(getCenterY() + dy - getHeight()/2);
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
				}else if(getX()+getWidth()+dx>=moveRage.right){
					moveRageReflectFactorX *= -1;
					setX(moveRage.right-getWidth());
				}else{
					setX(getCenterX() + dx - getWidth()/2);
				}
				
				if(getY()+dy<=moveRage.top){
					moveRageReflectFactorY *= -1;
					setY(moveRage.top);
				}else if(getY()+getHeight()+dy>=moveRage.bottom){
					moveRageReflectFactorY *= -1;
					setY(moveRage.bottom - getHeight());
				}else{
					setY(getCenterY() + dy - getHeight()/2);
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
	
	/**
	 * @return
	 */
	public String getActionName(){
		return currentAction.name;
	}
	
	/**
	 * 
	 */
	public void forceToNextFrameBitmap(){
		currentAction.forceToNextBitmap();
	}
	
	/**
	 * @return
	 */
	public boolean isNeedCreateNewInstance(){
		return false;
	}
	
	/**
	 * @return
	 */
	public boolean isNeedRemoveInstance(){
		return getX()<0 || getX() > CommonUtil.screenWidth || getY() < 0 || getY() > CommonUtil.screenHeight;
	}
	
	//Be care for the isCompostie();
	/**
	 * @param collisionRectF
	 */
	public void setCollisionRectF(RectF collisionRectF){
		this.collisionRectF = collisionRectF;
		collisionOffsetX = collisionRectF.left - getLeft();
		collisionOffsetY = collisionRectF.top - getTop();
		collisionRectFWidth = collisionRectF.width();
		collisionRectFHeight = collisionRectF.height();
	}
	
	/**
	 * set collision RectF.
	 * @param left
	 * 			Left of Collision RectF.
	 * @param top
	 * 			Top of Collision RectF.
	 * @param right
	 * 			Right of Collision RectF.
	 * @param bottom
	 * 			Bottom of Collision RectF.
	 */
	public void setCollisionRectF(float left, float top, float right, float bottom){
//		if(!isCollisionRectFEnable)
//			return;
		if(collisionRectF==null)
			collisionRectF = new RectF(left, top, right, bottom);
		else
			collisionRectF.set(left, top, right, bottom);
		collisionOffsetX = collisionRectF.left - getLeft();
		collisionOffsetY = collisionRectF.top - getTop();
		collisionRectFWidth = collisionRectF.width();
		collisionRectFHeight = collisionRectF.height();
	}
	
	/**
	 * get CollisionRectF.
	 * @return RectF.
	 */
	public RectF getCollisionRectF(){
		return collisionRectF;
	}
	
	/**
	 * is Collision RectF enable.
	 * @param isCollisionRectFEnable
	 */
	public void setCollisionRectFEnable(boolean isCollisionRectFEnable){
		this.isCollisionRectFEnable = isCollisionRectFEnable;
	}
	
	/**
	 * check is Collision RectF enable.
	 * @return enable.
	 */
	public boolean isCollisionRectFEnable(){
		return isCollisionRectFEnable;
	}
	
	/**
	 * set collision width. 
	 * @param collisionRectFWidth
	 * 			the width of collision RectF.
	 */
	public void setCollisionRectFWidth(float collisionRectFWidth){
		this.collisionRectFWidth = collisionRectFWidth;
	}
	
	/**
	 * set collision height. 
	 * @param collisionRectFHeight
	 * 			the height of collision RectF.
	 */
	public void setCollisionRectFHeight(float collisionRectFHeight){
		this.collisionRectFHeight = collisionRectFHeight;
	}
	
	/**
	 * set collision width and height. 
	 * @param collisionRectFWidth
	 * 			the width of collision RectF.
	 * @param collisionRectFHeight
	 * 			the height of collision RectF.
	 */
	public void setCollisionRectFWH(float collisionRectFWidth, float collisionRectFHeight){
		this.collisionRectFWidth = collisionRectFWidth;
		this.collisionRectFHeight = collisionRectFHeight;
	}
	
	/**
	 * get frame width.
	 * @return frame width.
	 */
	public float getFrameWidth(){
		return frameWidth;
	}
	
	/**
	 * set frame width.
	 * @param frameWidth
	 */
	public void setFrameWidth(float frameWidth){
		this.frameWidth = frameWidth;
	}
	
	/**
	 * get frame height.
	 * @return frameHeight.
	 */
	public float getFrameHeight(){
		return frameHeight;
	}
	
	/**
	 * set frame height.
	 * @param frameHeight
	 */
	public void setFrameHeight(float frameHeight){
		this.frameHeight = frameHeight;
	}
	
	/**
	 * reset frame width and height.
	 */
	public void resetFrameWH(){
		this.frameWidth = bitmapOrginalFrameWidth;
		this.frameHeight = bitmapOrginalFrameHeight;
	}
	
	/**
	 * get bitmap original frame width without scale or other thing.
	 * @return int width.
	 */
	public int getBitmapOrginalFrameWidth(){
		return bitmapOrginalFrameWidth;
	}
	
	/**
	 * get bitmap original frame width without scale or other thing.
	 * @return int height.
	 */
	public int getBitmapOrginalFrameHright(){
		return bitmapOrginalFrameHeight;
	}
	
	/**
	 * run MovementAction in self.
	 * @param movementAction
	 * 			{@code MovementAction} is  d
	 */
	public void runMovementAction(MovementAction movementAction){
		initRunMovementAction(movementAction);
		setMovementAction(movementAction);
	}
	
	/**
	 * run MovementAction and append.
	 * @param movementAction
	 * 			run movementAction.
	 */
	public void runMovementActionAndAppend(MovementAction movementAction){
		initRunMovementAction(movementAction);
		addMovementAction(movementAction);
	}
	
	/**
	 * init MovementAction.
	 * @param movementAction
	 */
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
	
	//use in game engein by removeFromParent() and willDoSometiongBeforeOneOfAncestorLayerWillRemoved().
	//cancel the sprites which ancestorLayer removed from composite group.
	public void cancelCurrentMovementAction(){
		removeAllMovementActions();
	}
	
	//not use in game engein yet, just call for user.
	public void cancelCurrentMovementActionAndCurrentMovementActionInChirdren(){
		cancelCurrentMovementAction();
		
		checkChildrenForCancelCurrentMovementAction(this);
	}
	
	/**
	 * 
	 * @param checkLayer
	 */
	protected void checkChildrenForCancelCurrentMovementAction(ILayer checkLayer){
		for(ILayer layer : checkLayer.getLayers()){
			if(layer.isComposite() && layer instanceof Sprite){
				((Sprite)layer).cancelCurrentMovementActionAndCurrentMovementActionInChirdren();
			}else if(layer.isComposite()){
				checkChildrenForCancelCurrentMovementAction(layer);
			}
		}
	}
	
	/**
	 * set physicsBody.
	 * @param physicsBody
	 * 			physicsBody for execute physic.
	 * @param world
	 * 			the physics world.
	 */
	public void setPhysicsBody(PhysicsBody physicsBody, LWorld world){
		this.physicsBody = physicsBody;
		this.physicsBody.setUserData(this);
		physicsBody.addToWorld(world);
	}
	
	/**
	 * physicsBody set dynamic.
	 * @param dynamic
	 */
	public void setDynamic(boolean dynamic){
		this.physicsBody.setDynamic(dynamic);
	}
	
	/**
	 * set SpriteDetectAreaHandler.
	 * @param spriteDetectAreaHandler
	 * 			to set the SpriteDetectAreaHandler to deal with.
	 */
	public void setSpriteDetectAreaHandler(SpriteDetectAreaHandler spriteDetectAreaHandler){
		this.spriteDetectAreaHandler = spriteDetectAreaHandler;
		this.spriteDetectAreaHandler.setObjectTag(this);
	}
	
	/**
	 * Get SpriteDetectAreaHandler
	 * @return SpriteDetectAreaHandler
	 */
	public SpriteDetectAreaHandler getSpriteDetectAreaHandler(){
		return spriteDetectAreaHandler;
	}
	
	/**
	 * update center of SpriteDetectArea.
	 * @param center
	 * 			in SpriteDetectArea.
	 */
	protected void updateSpriteDetectAreaCenter(PointF center){
		if(spriteDetectAreaHandler!=null)
			spriteDetectAreaHandler.updateSpriteDetectAreaCenter(center);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		if(isComposite())
			locationLeftTopInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - getWidth() / 2), (float) (getCenterY() - getHeight() / 2));
		colculationMatrix();
		
		if(isComposite()){//this is not test yet after add anchor point. It might be wrong.
//			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			PointF locationInScene = new PointF(locationLeftTopInScene.x, locationLeftTopInScene.y);
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+getWidth()/2, locationInScene.y+getHeight()/2));
		}else{
			setCollisionRectF(getLeft()+collisionOffsetX, getTop()+collisionOffsetY, getLeft()+collisionOffsetX+collisionRectFWidth, getTop()+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}	
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
		if(isComposite())
			locationLeftTopInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - getWidth() / 2), (float) (getCenterY() - getHeight() / 2));
		colculationMatrix();
		
		if(isComposite()){//this is not test yet after add anchor point. It might be wrong.
//			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			PointF locationInScene = new PointF(locationLeftTopInScene.x, locationLeftTopInScene.y);
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+getWidth()/2, locationInScene.y+getHeight()/2));
		}else{
			setCollisionRectF(getLeft()+collisionOffsetX, getTop()+collisionOffsetY, getLeft()+collisionOffsetX+collisionRectFWidth, getTop()+collisionOffsetY+collisionRectFHeight);	
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);	
		if(isComposite())
			locationLeftTopInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - getWidth() / 2), (float) (getCenterY() - getHeight() / 2));
		colculationMatrix();
		
		if(isComposite()){//this is not test yet after add anchor point. It might be wrong.
			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
			setCollisionRectF(locationInScene.x+collisionOffsetX, locationInScene.y+collisionOffsetY, locationInScene.x+collisionOffsetX+collisionRectFWidth, locationInScene.y+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(locationInScene.x+getWidth()/2, locationInScene.y+getHeight()/2));
		}else{
			setCollisionRectF(getLeft()+collisionOffsetX, getTop()+collisionOffsetY, getLeft()+collisionOffsetX+collisionRectFWidth, getTop()+collisionOffsetY+collisionRectFHeight);
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	public void setSize(int w, int h) {
		setWidth(w);
		setHeight(h);
	}
	
	@Override
	public void setInitWidth(int w) {
		this.setWidth(w);
	}
	
	@Override
	public void setInitHeight(int h) {
		this.setHeight(h);
	}
	
	@Override
	public void setWidth(int w) {
		widthWithoutxScale = w;
		w = (int)(w*Math.abs(xScale));
		setSuperWidth(w);
	}
	
	/**
	 * set the width.
	 * @param w
	 * 			width.
	 */
	private void setSuperWidth(int w){
		super.setWidth(w);
		if(isComposite())
			locationLeftTopInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - w / 2), (float) (getCenterY() - getHeight() / 2));
		if(getBitmap()!=null){
			if(frameColNum!=0){
				xScaleForBitmapWidth = w/((float)(getBitmap().getWidth()/frameColNum));
				this.frameWidth = w;
			}else if(frameWidth==0){
				xScaleForBitmapWidth = w/(float)getBitmap().getWidth();
			}
			
			colculationMatrix();
		}
		
		collisionOffsetX = (float)w/this.getWidth()*collisionOffsetX;
		if(collisionRectFWidth==0)
			collisionRectFWidth = w;
		else
			collisionRectFWidth = (float)w/this.getWidth()*collisionRectFWidth;
		setCollisionRectF(getLeft()+collisionOffsetX, getTop()+collisionOffsetY, getLeft()+collisionOffsetX+collisionRectFWidth, getTop()+collisionOffsetY+collisionRectFHeight);
		if(isComposite()){
			updateSpriteDetectAreaCenter(new PointF(getLocationInScene().x+w/2, getLocationInScene().y+getHeight()/2));
		}else{
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	@Override
	public void setHeight(int h) {
		// TODO Auto-generated method stub
		heightWithoutyScale = h;
		h = (int)(h*Math.abs(yScale));
		setSuperHeight(h);
	}
	
	/**
	 * set the height.
	 * @param h
	 * 			height.
	 */
	private void setSuperHeight(int h){
		super.setHeight(h);
		if(isComposite())
			locationLeftTopInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - getWidth() / 2), (float) (getCenterY() - h / 2));
		if(getBitmap()!=null){
			if(frameRowNum!=0){
				yScaleForBitmapHeight = h/((float)(getBitmap().getHeight()/frameRowNum));
				this.frameHeight = h;
			}else if(frameHeight==0){
				yScaleForBitmapHeight = h/(float)getBitmap().getHeight();
			}
			
			colculationMatrix();
		}
		
		collisionOffsetY = (float)h/this.getHeight()*collisionOffsetY;
		if(collisionRectFHeight==0)
			collisionRectFHeight = h;
		else
			collisionRectFHeight = (float)h/this.getHeight()*collisionRectFHeight;
		setCollisionRectF(getLeft()+collisionOffsetX, getTop()+collisionOffsetY, getLeft()+collisionOffsetX+collisionRectFWidth, getTop()+collisionOffsetY+collisionRectFHeight);
		if(isComposite()){
			updateSpriteDetectAreaCenter(new PointF(getLocationInScene().x+getWidth()/2, getLocationInScene().y+h/2));
		}else{
			updateSpriteDetectAreaCenter(new PointF(getCenterX(), getCenterY()));
		}
	}
	
	/**
	 * calculate the scale.
	 */
	private void colculationScale(){
		if(spriteMatrix==null)
			spriteMatrix = new Matrix();
		if(spriteMatrix!=null){
			spriteMatrix.reset();
			if(isComposite()){
				if(xScale<0 && yScale<0)
					spriteMatrix.postScale(-1*xScaleForBitmapWidth, -1*yScaleForBitmapHeight, locationLeftTopInScene.x +getAnchorPoint().x*getWidth(), locationLeftTopInScene.y  + getAnchorPoint().y*getHeight());
				else if(xScale<0)
					spriteMatrix.postScale(-1*xScaleForBitmapWidth, yScaleForBitmapHeight, locationLeftTopInScene.x +getAnchorPoint().x*getWidth(), locationLeftTopInScene.y  + getAnchorPoint().y*getHeight());
				else if(yScale<0)
					spriteMatrix.postScale(xScaleForBitmapWidth, -1*yScaleForBitmapHeight, locationLeftTopInScene.x +getAnchorPoint().x*getWidth(), locationLeftTopInScene.y  + getAnchorPoint().y*getHeight());
				else
					spriteMatrix.postScale(xScaleForBitmapWidth, yScaleForBitmapHeight, locationLeftTopInScene.x +getAnchorPoint().x*getWidth(), locationLeftTopInScene.y  + getAnchorPoint().y*getHeight());	
			}else{
				if(xScale<0 && yScale<0)
					spriteMatrix.postScale(-1*xScaleForBitmapWidth, -1*yScaleForBitmapHeight, getLeft() + getAnchorPoint().x*getWidth(),  getTop() + getAnchorPoint().y*getHeight());
				else if(xScale<0)
					spriteMatrix.postScale(-1*xScaleForBitmapWidth, yScaleForBitmapHeight, getLeft() + getAnchorPoint().x*getWidth(),  getTop() + getAnchorPoint().y*getHeight());
				else if(yScale<0)
					spriteMatrix.postScale(xScaleForBitmapWidth, -1*yScaleForBitmapHeight, getLeft() + getAnchorPoint().x*getWidth(),  getTop() + getAnchorPoint().y*getHeight());
				else	
					spriteMatrix.postScale(xScaleForBitmapWidth, yScaleForBitmapHeight, getLeft() + getAnchorPoint().x*getWidth(),  getTop() + getAnchorPoint().y*getHeight());
			}
			
			if(this.length>0){
				if(xScale*xScaleForBitmapWidth<0 && yScale*yScaleForBitmapHeight<0){
					spriteMatrix.postTranslate(-2*getWidth()*(getAnchorPoint().x-0.5f),-2*getHeight()*(getAnchorPoint().y-0.5f));
				}else if(xScale*xScaleForBitmapWidth<0){
					spriteMatrix.postTranslate(-2*getWidth()*(getAnchorPoint().x-0.5f),0);
				}else if(yScale*yScaleForBitmapHeight<0){
					spriteMatrix.postTranslate(0,-2*getHeight()*(getAnchorPoint().y-0.5f));
				}
			}else{
				if(xScale*xScaleForBitmapWidth<0 && yScale*yScaleForBitmapHeight<0){
					spriteMatrix.postTranslate(-getWidth()*(getAnchorPoint().x-0.5f)*2,-getHeight()*(getAnchorPoint().y-0.5f)*2);
				}else if(xScale*xScaleForBitmapWidth<0){
					spriteMatrix.postTranslate(-getWidth()*(getAnchorPoint().x-0.5f)*2,0);
				}else if(yScale*yScaleForBitmapHeight<0){
					spriteMatrix.postTranslate(0,-getHeight()*(getAnchorPoint().y-0.5f)*2);
				}
			}
			
		}
	}
	
	/**
	 * calculate the matrix.
	 */
	private void colculationMatrix(){
		colculationScale();
		
		if(isComposite()){
			switch (rotationType) {
			case AUTO: // child layer rotate the same of ROTATE_WITH_ANCHOR_POINT.
				spriteMatrix.postRotate(rotation, locationLeftTopInScene.x + getAnchorPoint().x*getWidth(), locationLeftTopInScene.y + getAnchorPoint().y*getWidth());
				break;
			case ROTATE_WITH_CENTER:
				spriteMatrix.postRotate(rotation, locationLeftTopInScene.x + getWidth()/2, locationLeftTopInScene.y + getHeight()/2);
				break;
			case ROTATE_WITH_ANCHOR_POINT:
				spriteMatrix.postRotate(rotation, locationLeftTopInScene.x + getAnchorPoint().x*getWidth(), locationLeftTopInScene.y + getAnchorPoint().y*getWidth());
				break;
			}
		}else{
			switch (rotationType) {
			case AUTO: // root layer rotate the same of ROTATE_WITH_CENTER.
				spriteMatrix.postRotate(rotation, getLeft() + getWidth()/2,  getTop() + getHeight()/2);
				break;
			case ROTATE_WITH_CENTER:
				spriteMatrix.postRotate(rotation, getLeft() + getWidth()/2,  getTop() + getHeight()/2);
				break;
			case ROTATE_WITH_ANCHOR_POINT:
				spriteMatrix.postRotate(rotation, getLeft() + getAnchorPoint().x*getWidth(),  getTop() + getAnchorPoint().y*getHeight());
				break;
			}
		}
		
		RectF newFrameInScene = new RectF();
		float left, top, right, bottom;
		
		if(getBitmap()!=null){
			if(isComposite()){
				/*
				 * w/xScaleForBitmapWidth almost equal (float)getBitmap().getWidth() or ((float)getBitmap().getWidth())/frameColNum. 
				 */
				if(this.length>0){
					left = locationLeftTopInScene.x + getAnchorPoint().x*getWidth() - getAnchorPoint().x*((float)getBitmap().getWidth())/frameColNum;
					top = locationLeftTopInScene.y + getAnchorPoint().y*getHeight() - getAnchorPoint().y*((float)getBitmap().getHeight())/frameRowNum;
					right = left + ((float)getBitmap().getWidth())/frameColNum;
					bottom = top + ((float)getBitmap().getHeight())/frameRowNum;
					spriteMatrix.mapRect(newFrameInScene, new RectF(left, top, right, bottom));
				}else {
					left = locationLeftTopInScene.x + getAnchorPoint().x*getWidth() - getAnchorPoint().x*getWidth()/xScaleForBitmapWidth;
					top = locationLeftTopInScene.y + getAnchorPoint().y*getHeight() - getAnchorPoint().y*getHeight()/yScaleForBitmapHeight;
					right = left + getWidth()/xScaleForBitmapWidth;
					bottom = top + getHeight()/yScaleForBitmapHeight;
					spriteMatrix.mapRect(newFrameInScene, 
							new RectF(left, top, right, bottom));
				}
			}else{
				if(this.length>0){
					left = getLeft() + getAnchorPoint().x*getWidth() - getAnchorPoint().x*((float)getBitmap().getWidth())/frameColNum;
					top = getTop() + getAnchorPoint().y*getHeight() - getAnchorPoint().y*((float)getBitmap().getHeight())/frameRowNum;
					right = left + ((float)getBitmap().getWidth())/frameColNum;
					bottom = top + ((float)getBitmap().getHeight())/frameRowNum;
					spriteMatrix.mapRect(newFrameInScene, 
							new RectF(left, top, right, bottom));
				}else{
					left = getLeft() + getAnchorPoint().x*getWidth() - getAnchorPoint().x*getWidth()/xScaleForBitmapWidth;
					top = getTop() + getAnchorPoint().y*getHeight() - getAnchorPoint().y*getHeight()/yScaleForBitmapHeight;
					right = left + getWidth()/xScaleForBitmapWidth;
					bottom = top + getHeight()/yScaleForBitmapHeight;
					spriteMatrix.mapRect(newFrameInScene, new RectF(left, top, right, bottom));
				}
			}
		}else {
			left = getLeft();
			top = getTop();
			right = left + getWidth();
			bottom = top + getHeight();
			spriteMatrix.mapRect(newFrameInScene, new RectF(getAnchorPointXY().x, getAnchorPointXY().y, getAnchorPointXY().x+getWidth(), getAnchorPointXY().y+getHeight()));
		}
		
		setFrameInScene(newFrameInScene);
		
		autoCalculateSizeByChildern();
		
		dealWithSpriteMatrixAfterCalculationMatrix(spriteMatrix);
	}
	
	protected void dealWithSpriteMatrixAfterCalculationMatrix(Matrix spriteMatrix){
		//deal with Sprite Matrix.
	}
	
	@Override
	protected void willDoSometiongBeforeOneOfAncestorLayerWillRemoved() {
		// TODO Auto-generated method stub
		/*
		//The case that is NOT composite and is NOT null is the Sprite is in autoDraw and is a Layer's child. 
		//Because maybe the auto need the movementAction, user do not want to cancel.
		if(isComposite() || getParent()==null) 
			cancelCurrentMovementAction();
			*/
		cancelCurrentMovementAction(); // Not consider which is auto add or not, just cancel.
		super.willDoSometiongBeforeOneOfAncestorLayerWillRemoved();
	}
	
	/**
	 * {@code SpriteAction} is a class.
	 * @author irons
	 *
	 */
	public class SpriteAction {
		public int[] frames;
		public int[] frameTime;
		public Bitmap[] bitmapFrames;
		public boolean isLoop;
		public String name;
		protected long updateTime;
		public float scale;
		public IActionListener actionListener = new DefaultActionListener();
		
		/**
		 * 
		 */
		public void nextFrame() {
			if (System.currentTimeMillis() > updateTime) {
				nextFrameBySequence();
				updateTime = System.currentTimeMillis() + frameTime[frameIdx];
			}
		}
		
		/**
		 * 
		 */
		public void nextBitmap(){			
			if (System.currentTimeMillis() > updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx);
				
				if(!isLoop && frameIdx==bitmapFrames.length-1){
					setBitmap(bitmapFrames[frameIdx]);
					isStop = true;
					actionListener.actionFinish();
				}else{
					setBitmap(bitmapFrames[frameIdx]);
					
					frameIdx++;
					frameIdx %= bitmapFrames.length;
					
					updateTime = System.currentTimeMillis() + frameTime[frameIdx];
					
					int w = getBitmap().getWidth();
					int h = getBitmap().getHeight();
					
					setWidth(getBitmap().getWidth());
					setHeight(getBitmap().getHeight());
					actionListener.afterChangeFrame(frameIdx);
				}
			}
		}
		
		/**
		 * force to change to next bitmao.
		 */
		public void forceToNextBitmap(){
			setBitmap(bitmapFrames[frameIdx]);
			frameIdx++;
			frameIdx %= bitmapFrames.length;
			if(!isLoop && frameIdx==0){
				isStop = true;		
			}
		}
		
		/**
		 * force to change to finish.
		 */
		public void forceToFinish(){
			if(!isStop){
				isStop = true;
				actionListener.actionFinish();
			}
		}
		
		/**
		 * trigger the sprite action.
		 */
		public void trigger(){
			process();
		}
		
		/**
		 * init the update time.
		 */
		public void initUpdateTime(){
			updateTime = System.currentTimeMillis() + frameTime[frameIdx];
		}
		
		/**
		 * change to next frame.
		 */
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
	
	/**
	 * {@code SpriteActionBaseFPS}
	 * @author irons
	 *
	 */
	public class SpriteActionBaseFPS extends SpriteAction{
		private int triggerCount;
		
		@Override
		public void nextFrame() {
			if (triggerCount >= updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx);
				
				if(!isLoop && frameIdx==frames.length-1){
					nextFrameBySequence();
					triggerCount=0;
					isStop = true;
					actionListener.actionFinish();
				}else{					
					nextFrameBySequence();
			
					triggerCount=0;
					updateTime = frameTime[frameIdx];

					actionListener.afterChangeFrame(frameIdx);
				}
			}
		}
		
		@Override
		public void nextBitmap(){			
			if (triggerCount >= updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx);
				
				if(!isLoop && frameIdx==bitmapFrames.length-1){
					if(bitmapFrames[frameIdx]!=null){
						setBitmap(bitmapFrames[frameIdx]);
					}
					triggerCount=0;
					isStop = true;
					actionListener.actionFinish();
				}else{
					
					if(bitmapFrames[frameIdx]!=null){
						setBitmap(bitmapFrames[frameIdx]);
								
						int w = getBitmap().getWidth();
						int h = getBitmap().getHeight();
						
						setWidth(getBitmap().getWidth());
						setHeight(getBitmap().getHeight());
					}
					actionListener.afterChangeFrame(frameIdx);
					
					frameIdx++;
					frameIdx %= bitmapFrames.length;
					
					triggerCount=0;
					updateTime = frameTime[frameIdx];
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

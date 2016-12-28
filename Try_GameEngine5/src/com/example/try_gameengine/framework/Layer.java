package com.example.try_gameengine.framework;

import java.text.BreakIterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.view.MotionEvent;

public class Layer extends ALayer{

	public Layer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
	}

	public Layer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
	}

	public Layer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	public Layer(Bitmap bitmap, float x, float y, boolean autoAdd) {
//		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
		super(bitmap, x, y, autoAdd);
	}
	
	public Layer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
	}
	
	public Layer(boolean autoAdd) {
		super(autoAdd);
	}
	
	public Layer() {
		super();
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
//		if(isHidden())
		if(super.checkSelfToAncestorIsHiddenOrNot())
			return;
		
		doDrawself(canvas, paint);
		
		doDrawChildren(canvas, paint);
	}
	
	protected void doDrawself(Canvas canvas, Paint paint) {
		if(getBackgroundColor()!=NONE_COLOR || getBitmap()!=null){
			canvas.save();
			
			do {
				canvas = getClipedCanvas(canvas, paint);
				Paint originalPaint = paint;
				
				//use input paint first
				int oldColor = 0;
				Style oldStyle = null;
				int oldAlpha = 255;
				boolean isDrawBackgroundColor = false;
				if(originalPaint==null && getPaint()!=null){
					paint = getPaint();
	//				paint.setAntiAlias(true);
					if(getBackgroundColor()!=NONE_COLOR){
						isDrawBackgroundColor = true;
						oldColor = getPaint().getColor();
						oldStyle = getPaint().getStyle();
						oldAlpha = getPaint().getAlpha();
						getPaint().setColor(getBackgroundColor());
						getPaint().setAlpha((int) (getAlpha()*oldAlpha/255.0f));
						getPaint().setStyle(Style.FILL);
						canvas.drawRect(getFrameInScene(), paint);
					}
				}else if(originalPaint!=null){
					canvas.drawRect(getFrameInScene(), paint);
				}
				
				drawBitmap(canvas, paint, oldColor, oldStyle, oldAlpha,
						isDrawBackgroundColor);
				
				//use input paint first
				paint = originalPaint;
			} while (false);
			
			canvas.restore();
		}
	}

	protected void drawBitmap(Canvas canvas, Paint paint, int oldColor,
			Style oldStyle, int oldAlpha, boolean isDrawBackgroundColor) {
		calcilation();
		
		if(isDrawBackgroundColor){
			getPaint().setColor(oldColor);
			getPaint().setStyle(oldStyle);
			getPaint().setAlpha(oldAlpha);
		}
		if(getBitmap()!=null)
			canvas.drawBitmap(getBitmap(), getSrc(), getDst(), paint);
	}

	private void calcilation() {
		if(isComposite()){
			getSrc().left = 0;
			getSrc().top = 0;
			if(getBitmap()!=null && isBitmapSacleToFitSize()){
				getSrc().right = getBitmap().getWidth();
				getSrc().bottom = getBitmap().getHeight();
			}else{
				getSrc().right = getWidth();
				getSrc().bottom = getHeight();
			}
			
			if(getParent()!=null){
				PointF locationInScene = getParent().locationInSceneByCompositeLocation((float) (getCenterX() - getWidth() / 2), (float) (getCenterY() - getHeight() / 2));
				getDst().left = locationInScene.x;
				getDst().top = locationInScene.y;
				getDst().right = (float) (getDst().left + getWidth());
				getDst().bottom = (float) (getDst().top + getHeight());
			}else{
				getDst().left = (float) (getCenterX() - getWidth() / 2);
				getDst().top = (float) (getCenterY() - getHeight() / 2);
				getDst().right = (float) (getDst().left + getWidth());
				getDst().bottom = (float) (getDst().top + getHeight());
			}
		}else{
			getSrc().left = 0;
			getSrc().top = 0;
			getSrc().right = getWidth();
			getSrc().bottom = getHeight();
			getDst().left = (float) (getCenterX() - getWidth() / 2);
			getDst().top = (float) (getCenterY() - getHeight() / 2);
			getDst().right = (float) (getDst().left + getWidth());
			getDst().bottom = (float) (getDst().top + getHeight());
		}
	}
	
	protected void doDrawChildren(Canvas canvas, Paint paint) {
		for(ILayer layer : getLayers()){
			if(layer.isComposite() && !layer.isAutoAdd()) //if the layer is auto add, not draw.
				layer.drawSelf(canvas, paint);
		}
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}

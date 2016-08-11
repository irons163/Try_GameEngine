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
		
		if(getBackgroundColor()!=NONE_COLOR || bitmap!=null){
			canvas.save();
			
			do {
				if(isAncestorClipOutSide()){
					RectF rectF = null;
					if((rectF = getClipRange())!=null){
						Rect rect = new Rect();
						rectF.round(rect);
//						canvas.clipRegion(new Region(rect));
						canvas.clipRect(rect);
					}else{
						break;
					}
				}
				
				Paint originalPaint = paint;
				
				//use self paint first
				int oldColor = 0;
				Style oldStyle = null;
				if(originalPaint==null && getPaint()!=null){
					paint = getPaint();
	//				paint.setAntiAlias(true);
					if(getBackgroundColor()!=NONE_COLOR && getPaint()!=null){
						oldColor = getPaint().getColor();
						oldStyle = getPaint().getStyle();
						getPaint().setColor(getBackgroundColor());
						getPaint().setStyle(Style.FILL);
						canvas.drawRect(getFrameInScene(), paint);
					}
				}
				
				if(isComposite()){
					src.left = 0;
					src.top = 0;
					src.right = w;
					src.bottom = h;
					
					if(parent!=null){
						PointF locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
						dst.left = locationInScene.x;
						dst.top = locationInScene.y;
						dst.right = (float) (dst.left + w);
						dst.bottom = (float) (dst.top + h);
					}else{
						dst.left = (float) (centerX - w / 2);
						dst.top = (float) (centerY - h / 2);
						dst.right = (float) (dst.left + w);
						dst.bottom = (float) (dst.top + h);
					}
					
					if(getBackgroundColor()!=NONE_COLOR){
	//					if(isClipOutside)
	//						dst.intersect(getParent().getDst());
	//					canvas.drawRect(dst, paint);
						getPaint().setColor(oldColor);
						getPaint().setStyle(oldStyle);
					}
					if(bitmap!=null)
						canvas.drawBitmap(bitmap, src, dst, paint);
					
					/*//use input paint first 
					paint = originalPaint;
					originalPaint = null;
					if(paint!=null){
						paint.setAlpha(originalAlpha);
					}
					*/
					
					//use self paint first
	//				paint = originalPaint;
					
				}else{
					src.left = 0;
					src.top = 0;
					src.right = w;
					src.bottom = h;
					dst.left = (float) (centerX - w / 2);
					dst.top = (float) (centerY - h / 2);
					dst.right = (float) (dst.left + w);
					dst.bottom = (float) (dst.top + h);
					
					if(getBackgroundColor()!=NONE_COLOR){
	//					canvas.drawRect(getFrame(), paint);
						getPaint().setColor(oldColor);
						getPaint().setStyle(oldStyle);
					}
					if(bitmap!=null)
						canvas.drawBitmap(bitmap, src, dst, paint);
					
					/*//use input paint first 
					paint = originalPaint;
					originalPaint = null;
					if(paint!=null){
						paint.setAlpha(originalAlpha);
					}
					*/
					
					//use self paint first
	//				paint = originalPaint;
				}
				
	
				
				//use self paint first
				paint = originalPaint;
			} while (false);
			
			canvas.restore();
//			canvas.restoreToCount(sc);
		}
		
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

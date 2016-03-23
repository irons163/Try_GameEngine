package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
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

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		
		if(bitmap!=null){
			Paint originalPaint = paint;
			
			/*//use input paint first 
			int originalAlpha = 255;
			if(paint==null){
				paint = getPaint();
			}else{
				originalAlpha = paint.getAlpha();
				paint.setAlpha(getAlpha());
			}
			*/
			
			//use self paint first
			if(getPaint()!=null){
				paint = getPaint();
			}
			
			if(isComposite()){
				src.left = 0;
				src.top = 0;
				src.right = w;
				src.bottom = h;
				
				if(parent!=null){
					
//					dst.left = getLocationInScene().x;
//					dst.top = getLocationInScene().y;
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
				
				canvas.drawBitmap(bitmap, src, dst, paint);
				
				/*//use input paint first 
				paint = originalPaint;
				originalPaint = null;
				if(paint!=null){
					paint.setAlpha(originalAlpha);
				}
				*/
				
				//use self paint first
				paint = originalPaint;
				
			}else{
				src.left = 0;
				src.top = 0;
				src.right = w;
				src.bottom = h;
				dst.left = (float) (centerX - w / 2);
				dst.top = (float) (centerY - h / 2);
				dst.right = (float) (dst.left + w);
				dst.bottom = (float) (dst.top + h);
				canvas.drawBitmap(bitmap, src, dst, paint);
				
				/*//use input paint first 
				paint = originalPaint;
				originalPaint = null;
				if(paint!=null){
					paint.setAlpha(originalAlpha);
				}
				*/
				
				//use self paint first
				paint = originalPaint;
			}
		}
		
		for(ILayer layer : getLayers()){
			if(layer.isComposite())
				layer.drawSelf(canvas, paint);
		}
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}

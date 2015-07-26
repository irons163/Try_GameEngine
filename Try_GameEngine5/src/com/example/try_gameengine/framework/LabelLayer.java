package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class LabelLayer extends Layer{
	private String text;
	private Paint paint;
	
	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	public LabelLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	public LabelLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		this.text = text;
		paint = new Paint();
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		if(text!=null)
			canvas.drawText(text, getX(), getY(), paint!=null?paint:this.paint);
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}
	
	public void setPaint(Paint paint){
		this.paint = paint;
	}
	
	public void setTextSize(float textSize){
		paint.setTextSize(textSize);
	}
	
	public void setTextStyle(Typeface typeface){
		paint.setTypeface(typeface);
	}
	
	public void setTextColor(int color){
		paint.setColor(color);
	}
}

package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class LabelLayer extends Layer{
	private String text;
//	private Paint paint = getPaint();
	
	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		setPaint(new Paint());
	}

	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		setPaint(new Paint());
	}

	public LabelLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		setPaint(new Paint());
	}

	public LabelLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		this.text = text;
//		paint = new Paint();
		setPaint(new Paint());
	}
	
	public LabelLayer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
//		paint = new Paint();
		setPaint(new Paint());
	}
	
	public LabelLayer(String text, float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		this.text = text;
//		paint = new Paint();
		setPaint(new Paint());
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		if(text!=null)
			canvas.drawText(text, getX(), getY(), paint!=null?paint:getPaint());
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}
	
	public void setTextSize(float textSize){
		getPaint().setTextSize(textSize);
	}
	
	public void setTextStyle(Typeface typeface){
		getPaint().setTypeface(typeface);
	}
	
	public void setTextColor(int color){
		getPaint().setColor(color);
	}
}

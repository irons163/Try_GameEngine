package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class LabelLayer extends Layer{
	private String text;
	private boolean isAutoHWByText = false;
//	private Paint paint = getPaint();
	
	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		initPaint();
	}

	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		initPaint();
	}

	public LabelLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
//		paint = new Paint();
		initPaint();
	}

	public LabelLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		this.text = text;
//		paint = new Paint();
		initPaint();
	}
	
	public LabelLayer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
//		paint = new Paint();
		initPaint();
	}
	
	public LabelLayer(String text, float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		this.text = text;
//		paint = new Paint();
		initPaint();
	}
	
	private void initPaint(){
		Paint paint = new Paint();
		paint.setTypeface(Typeface.DEFAULT);// your preference here
		paint.setTextSize(35);// have this the same as your text size
		setPaint(paint);
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		if(text!=null){
			if(isComposite() && getParent()!=null)
				canvas.drawText(text, getLocationInScene().x, getLocationInScene().y, paint!=null?paint:getPaint());
			else
				canvas.drawText(text, getLeft(), getTop(), paint!=null?paint:getPaint());
		}
	}
	
	public void setAutoHWByText(){
		isAutoHWByText = true;
		if(isAutoHWByText && getPaint()!=null)
			calculateWHByText();
//		setInitWidth(0);
//		setInitHeight(0);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if(isAutoHWByText && getPaint()!=null)
			calculateWHByText();
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
	
	private void calculateWHByText(){
		Paint paint = getPaint();
		Rect bounds = new Rect();

		int text_height = 0;
		int text_width = 0;

//		paint.setTypeface(Typeface.DEFAULT);// your preference here
//		paint.setTextSize(25);// have this the same as your text size

//		String text = "Some random text";

		paint.getTextBounds(text, 0, text.length(), bounds);

		text_height =  bounds.height();
		text_width =  bounds.width();
		
		setInitHeight(text_height);
		setInitWidth(text_width);
	}
}

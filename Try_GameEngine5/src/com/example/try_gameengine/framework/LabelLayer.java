package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetricsInt;

public class LabelLayer extends Layer{
	private String text;
	private boolean isAutoHWByText = false;
	private LabelBaseLine labelBaseLine = LabelBaseLine.DEFAULT_ANDROID_BASELINE;
	private float baseline;
//	private Paint paint = getPaint();
	
	public enum LabelBaseLine{
		DEFAULT_ANDROID_BASELINE, //default
		BASELINE_FOR_TEXT_TOP,
		BASELINE_FOR_TEXT_BOTTOM
	}
	
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
		isAutoHWByText = true;
	}
	
	public LabelLayer(String text, float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		this.text = text;
//		paint = new Paint();
		initPaint();
		setAutoHWByText();
	}
	
	private void initPaint(){
		Paint paint = new Paint();
		paint.setTypeface(Typeface.DEFAULT);// your preference here
		paint.setTextSize(35);// have this the same as your text size
		setPaint(paint);
	}
	
	@Override
	public void setPaint(Paint paint) {
		// TODO Auto-generated method stub
		super.setPaint(paint);
		autoHWByText();
		calculateY();
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
//		super.drawSelf(canvas, paint);
		super.doDrawself(canvas, paint);
		
		if(paint!=null)
			calculateY(paint);
		
		float y = 0;
		switch (labelBaseLine) {
		case DEFAULT_ANDROID_BASELINE:
			break;
		case BASELINE_FOR_TEXT_TOP:
		case BASELINE_FOR_TEXT_BOTTOM:
			y = baseline;
			break;
		}
		if(text!=null){
//			if(isAncestorClipOutSide()){
//				RectF rectF = null;
//				if((rectF = getClipRange())!=null){
//					canvas.save();
//					Rect rect = new Rect();
//					rectF.round(rect);
//					canvas.clipRegion(new Region(rect));
//				}
//			}
			
			do{
//				if(isAncestorClipOutSide()){
//					canvas.save();
//					RectF rectF = null;
//					if((rectF = getClipRange())!=null){
//						Rect rect = new Rect();
//						rectF.round(rect);
////						canvas.clipRegion(new Region(rect));
//						canvas.clipRect(rect);
//					}else{
//						break;
//					}
//				}
				
				canvas = getC(canvas, paint);
				
				if(isComposite() && getParent()!=null)
					canvas.drawText(text, getLocationInScene().x - getAnchorPoint().x*getWidth(), getLocationInScene().y - getAnchorPoint().y*getHeight() - y, paint!=null?paint:getPaint());
				else
					canvas.drawText(text, getLeft(), getTop() - y, paint!=null?paint:getPaint());
			}while(false);
			
			if(isAncestorClipOutSide())
				canvas.restore();
		}
		
		super.doDrawChildren(canvas, paint);
	}
	
	private void autoHWByText(){
		if(isAutoHWByText && getPaint()!=null)
			calculateWHByText();
	}
	
	public void setAutoHWByText(){
		isAutoHWByText = true;
		autoHWByText();
	}
	
	public void enableAutoHWByText(boolean isAutoHWByText){
		this.isAutoHWByText = isAutoHWByText;
	}
	
	public void setLabelBaseLine(LabelBaseLine labelBaseLine){
		this.labelBaseLine = labelBaseLine;
		calculateY();
	}
	
	public LabelBaseLine getLabelBaseLine(){
		return labelBaseLine;
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
		setWidth(bitmap.getWidth());
		setHeight(bitmap.getHeight());
	}
	
	@Override
	public void setWidth(int w) {
		// TODO Auto-generated method stub
		super.setWidth(w);
		isAutoHWByText = false;
	}
	
	@Override
	public void setHeight(int h) {
		// TODO Auto-generated method stub
		super.setHeight(h);
		isAutoHWByText = false;
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		calculateY();
	}
	
	public void setTextSize(float textSize){
		getPaint().setTextSize(textSize);
		autoHWByText();
		calculateY();
	}
	
	public void setTextStyle(Typeface typeface){
		getPaint().setTypeface(typeface);
		autoHWByText();
		calculateY();
	}
	
	public void setTextColor(int color){
		getPaint().setColor(color);
	}
	
	private void calculateWHByText(){
		Paint paint = getPaint();
//		Rect bounds = new Rect();

		int text_height = 0;
		int text_width = 0;

//		paint.setTypeface(Typeface.DEFAULT);// your preference here
//		paint.setTextSize(25);// have this the same as your text size

//		String text = "Some random text";

//		paint.getTextBounds(text, 0, text.length(), bounds);
//
//		text_height =  bounds.height();
//		text_width =  bounds.width();
		
		FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
//		paint.getTextBounds(text, 0, text.length(), bounds);
		
		
		text_height = fontMetricsInt.bottom - fontMetricsInt.top;
//		text_width =  bounds.width();
		text_width = (int) paint.measureText(text);
		
		setInitHeight(text_height);
		setInitWidth(text_width);
	}
	
	private void calculateY(){
		calculateY(getPaint());
	}
	
	private void calculateY(Paint paint){
		if(paint!=null && labelBaseLine!=LabelBaseLine.DEFAULT_ANDROID_BASELINE){
			FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
			baseline = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent;
			if(labelBaseLine==LabelBaseLine.BASELINE_FOR_TEXT_TOP)
				baseline -= getHeight();
		}
	}
}

package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetricsInt;

/**
 * {@code LabeLayer} is a layer. This can make a label to display. 
 * @author irons
 *
 */
public class LabelLayer extends Layer{
	private String text;
	private boolean isAutoHWByText = false;
	private AlignmentVertical alignmentVertical = AlignmentVertical.ALIGNMENT_TOP;
	private float baseline;
	
	public enum AlignmentVertical{
		ALIGNMENT_TOP, //the draw text topY is equal to self positionY.
		ALIGNMENT_BOTTOM,
		ALIGNMENT_CENTER,
		ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM,
		ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP,
		ALIGNMENT_ANDROID_TEXT_BASELINE //the draw text baseline is equal to self positionY.
	}
	
	/**
	 * Constructors.
	 * @param bitmap
	 * 			bitmap to the layer.
	 * @param w
	 * 			width.
	 * @param h
	 * 			height.
	 * @param autoAdd
	 * 			add to the LayerManager.getInstance() to control.
	 * @param level
	 * 			?
	 */
	public LabelLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
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
		switch (alignmentVertical) {
		case ALIGNMENT_TOP:
		case ALIGNMENT_BOTTOM:
		case ALIGNMENT_CENTER:
		case ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM:
		case ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP:
			y = baseline;
			break;
		case ALIGNMENT_ANDROID_TEXT_BASELINE:
			break;
		}
		if(text!=null){
			canvas.save();
			
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
				
				canvas = getClipedCanvas(canvas, paint);
				
				if(isComposite() && getParent()!=null)
					canvas.drawText(text, getLocationInScene().x - getAnchorPoint().x*getWidth(), getLocationInScene().y - getAnchorPoint().y*getHeight() - y, paint!=null?paint:getPaint());
				else
					canvas.drawText(text, getLeft(), getTop() - y, paint!=null?paint:getPaint());
			}while(false);
			
//			if(isAncestorClipOutSide())
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
		calculateY();
	}
	
	public void enableAutoHWByText(boolean isAutoHWByText){
		this.isAutoHWByText = isAutoHWByText;
	}
	
	public void setAlignmentVertical(AlignmentVertical alignmentVertical){
		this.alignmentVertical = alignmentVertical;
		calculateY();
	}
	
	public AlignmentVertical getAlignmentVertical(){
		return alignmentVertical;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if(isAutoHWByText && getPaint()!=null){
			calculateWHByText();
			calculateY();
		}
	}

	public void setBitmap(Bitmap bitmap){
//		this.bitmap = bitmap;
		super.setBitmap(bitmap);
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.setBitmap(bitmap);
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
		calculateY();
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
		if(paint!=null && alignmentVertical!=AlignmentVertical.ALIGNMENT_ANDROID_TEXT_BASELINE){
			FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
			baseline = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent;
			if(alignmentVertical==AlignmentVertical.ALIGNMENT_TOP)
//				baseline -= getHeight();
				baseline -= (fontMetricsInt.bottom - fontMetricsInt.top);
			else if(alignmentVertical==AlignmentVertical.ALIGNMENT_BOTTOM)
				baseline -= getHeight();
			else if(alignmentVertical==AlignmentVertical.ALIGNMENT_CENTER)
				baseline -= (getHeight()+(fontMetricsInt.bottom - fontMetricsInt.top))/2.0f;
			else if(alignmentVertical==AlignmentVertical.ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM)
				; //do nothing
			else if(alignmentVertical==AlignmentVertical.ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP)
				baseline -= (getHeight()+(fontMetricsInt.bottom - fontMetricsInt.top));
		}
	}
}

package com.example.try_gameengine.framework;

import com.example.try_gameengine.framework.LabelLayer.LabelBaseLine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class ButtonLayer extends Layer{
	private final int NORMAL_INDEX = 0;
	private final int DOWN_INDEX = 1;
	private final int UP_INDEX = 2;
	
	boolean isClickCancled = false;
	private LabelLayer labelLayer;
	private String text;
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	private int[] buttonColors = new int[3];
	private boolean hasButtonColors;
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(ButtonLayer buttonLayer) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public ButtonLayer() {
		super();
		initButtonColors();
	}
	
	public ButtonLayer(boolean autoAdd) {
		super(autoAdd);
		initButtonColors();
	}
	
	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	public ButtonLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	public ButtonLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		initButtonColors();
		initLabelLayer(text);
	}
	
	private void initButtonColors(){
		setButtonColors(Color.GRAY, Color.DKGRAY, Color.GRAY);
	}
	
	private void initLabelLayer(String text){
		labelLayer = new LabelLayer(text, 0, 0, false);
		labelLayer.setAutoHWByText();
		labelLayer.setPosition(getWidth()/2, getHeight()/2);
		labelLayer.setAnchorPoint(0.5f, 0.5f);
		labelLayer.setLabelBaseLine(LabelBaseLine.BASELINE_FOR_TEXT_TOP);
		labelLayer.setEnable(false);
		addChild(labelLayer);
	}
	
	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		super.setX(x);
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
	}
	
	@Override
	public void setWidth(int w) {
		// TODO Auto-generated method stub
		super.setWidth(w);
	}
	
	@Override
	public void setHeight(int h) {
		// TODO Auto-generated method stub
		super.setHeight(h);
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	
//	@Override
//	public void setBitmap(Bitmap normal){
//		this.bitmap = normal;
//		buttonBitmaps[NORMAL_INDEX] = normal;
//	}
	
//	@Override
//	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
//		this.bitmap = bitmap;
//		buttonBitmaps[NORMAL_INDEX] = bitmap;
//		setInitWidth(bitmap.getWidth());
//		setInitHeight(bitmap.getHeight());
//	}
	
	
	
	public void setButtonBitmap(Bitmap normal, Bitmap down , Bitmap up){
		buttonBitmaps[NORMAL_INDEX] = normal;
		buttonBitmaps[DOWN_INDEX] = down;
		buttonBitmaps[UP_INDEX] = up;
	}
	
	public void setButtonBitmaps(Bitmap[] buttonBitmaps){
		this.buttonBitmaps = buttonBitmaps;
	}
	
	public void setButtonColors(int normal, int down , int up){
		setBackgroundColor(normal);
		buttonColors[NORMAL_INDEX] = normal;
		buttonColors[DOWN_INDEX] = down;
		buttonColors[UP_INDEX] = up;
		hasButtonColors = true;
	}
	
	public void setButtonColorsNone(){
		setBackgroundColorNone();
		buttonColors[NORMAL_INDEX] = NONE_COLOR;
		buttonColors[DOWN_INDEX] = NONE_COLOR;
		buttonColors[UP_INDEX] = NONE_COLOR;
		hasButtonColors = false;
	}
	
	public void setText(String text){
		if(labelLayer==null){
			initLabelLayer(text);
		}else{
			labelLayer.setText(text);
		}
	}
	
	public String getText(){
		if(labelLayer!=null){
			return labelLayer.getText();
		}
		
		return null;
	}
	
	public void setTextSize(float textSize){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setTextSize(textSize);
	}
	
	public void setTextStyle(Typeface typeface){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setTypeface(typeface);
	}
	
	public void setTextColor(int color){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setColor(color);
	}
	
	public boolean onTouch(MotionEvent event){
		float x = event.getX();
		float y = event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN && this.dst.contains(x, y)){
			setBackgroundColor(buttonColors[DOWN_INDEX]);
			if(buttonBitmaps[DOWN_INDEX]!=null){
				this.bitmap = buttonBitmaps[DOWN_INDEX];
			}
			isClickCancled = false;
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && this.dst.contains(x, y)){
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && !this.dst.contains(x, y)){
			isClickCancled = true;
			return false;			
		}else if(event.getAction()==MotionEvent.ACTION_UP && isClickCancled){
			setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_UP && this.dst.contains(x, y) && !isClickCancled){
			setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
			onClickListener.onClick(this);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
//		if(event.getAction()==MotionEvent.ACTION_DOWN && isPressed()){
//			setBackgroundColor(buttonColors[DOWN_INDEX]);
//			if(buttonBitmaps[DOWN_INDEX]!=null){
//				this.bitmap = buttonBitmaps[DOWN_INDEX];
//			}
//			isClickCancled = false;
//		}else if(event.getAction()==MotionEvent.ACTION_MOVE && isPressed()){
//		}else if(event.getAction()==MotionEvent.ACTION_MOVE && !isPressed()){
//			isClickCancled = true;
//		}else if(event.getAction()==MotionEvent.ACTION_UP && isClickCancled && !isPressed()){
//			setBackgroundColor(buttonColors[UP_INDEX]);
//			if(buttonBitmaps[UP_INDEX]!=null){
//				this.bitmap = buttonBitmaps[UP_INDEX];
//			}
//		}else if(event.getAction()==MotionEvent.ACTION_UP && isPressed() && !isClickCancled){
//			setBackgroundColor(buttonColors[UP_INDEX]);
//			if(buttonBitmaps[UP_INDEX]!=null){
//				this.bitmap = buttonBitmaps[UP_INDEX];
//			}
//		}
		
		if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[DOWN_INDEX]);
			if(buttonBitmaps[DOWN_INDEX]!=null){
				this.bitmap = buttonBitmaps[DOWN_INDEX];
			}
			isClickCancled = false;
		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && isPressed()){

		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && !isPressed()){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[NORMAL_INDEX]);
			if(buttonBitmaps[NORMAL_INDEX]!=null){
				this.bitmap = buttonBitmaps[NORMAL_INDEX];
			}
			isClickCancled = true;
		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()){
			if(hasButtonColors)	
				setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
		}else if((event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_CANCEL){
			if(hasButtonColors)	
				setBackgroundColor(buttonColors[NORMAL_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[NORMAL_INDEX];
			}
		}
	}
	
	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
		setOnLayerClickListener(new OnLayerClickListener() {
			
			@Override
			public void onClick(ILayer layer) {
				// TODO Auto-generated method stub
				ButtonLayer.this.onClickListener.onClick((ButtonLayer)layer);
			}
		});
		
		if(labelLayer!=null)
		labelLayer.setOnLayerClickListener(new OnLayerClickListener() {
			
			@Override
			public void onClick(ILayer layer) {
				// TODO Auto-generated method stub
				ButtonLayer.this.onClickListener.onClick(ButtonLayer.this);
			}
		});
	}
	
	public interface OnClickListener{
		public void onClick(ButtonLayer buttonLayer);
	}
	
}

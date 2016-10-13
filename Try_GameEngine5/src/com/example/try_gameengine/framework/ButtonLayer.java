package com.example.try_gameengine.framework;

import com.example.try_gameengine.framework.LabelLayer.LabelBaseLine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

/**
 * {@code ButtonLayer} is a layer.
 * @author irons
 *
 */
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
	
	/**
	 * Constructor.
	 */
	public ButtonLayer() {
		super();
		initButtonColors();
	}
	
	/**
	 * Constructor.
	 * @param autoAdd
	 * 			
	 */
	public ButtonLayer(boolean autoAdd) {
		super(autoAdd);
		initButtonColors();
	}
	
	/**
	 * @param bitmap
	 * @param w
	 * @param h
	 * @param autoAdd
	 * @param level
	 */
	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	/**
	 * @param bitmap
	 * @param w
	 * @param h
	 * @param autoAdd
	 */
	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	/**
	 * @param w
	 * @param h
	 * @param autoAdd
	 */
	public ButtonLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		initButtonColors();
		buttonBitmaps[NORMAL_INDEX] = bitmap;
	}

	/**
	 * @param text
	 * @param w
	 * @param h
	 * @param autoAdd
	 */
	public ButtonLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		initButtonColors();
		initLabelLayer(text);
	}
	
	/**
	 * 
	 */
	private void initButtonColors(){
		setButtonColors(Color.GRAY, Color.DKGRAY, Color.GRAY);
	}
	
	/**
	 * @param text
	 */
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
	
	/**
	 * set button bitmaps to the button for difference status.
	 * @param normal
	 * 			color for normal status.
	 * @param down
	 * 			color for down status.
	 * @param up
	 * 			color for up status.
	 */
	public void setButtonBitmap(Bitmap normal, Bitmap down , Bitmap up){
		buttonBitmaps[NORMAL_INDEX] = normal;
		buttonBitmaps[DOWN_INDEX] = down;
		buttonBitmaps[UP_INDEX] = up;
	}
	
	/**
	 * set button bitmaps to the button for difference status.
	 * @param buttonBitmaps
	 * 			the bitmaps for difference status.
	 */
	public void setButtonBitmaps(Bitmap[] buttonBitmaps){
		this.buttonBitmaps = buttonBitmaps;
	}
	
	/**
	 * set the colors to the button for difference status.
	 * @param normal
	 * 			color for normal status.
	 * @param down
	 * 			color for down status.
	 * @param up
	 * 			color for up status.
	 */
	public void setButtonColors(int normal, int down , int up){
		setBackgroundColor(normal);
		buttonColors[NORMAL_INDEX] = normal;
		buttonColors[DOWN_INDEX] = down;
		buttonColors[UP_INDEX] = up;
		hasButtonColors = true;
	}
	
	/**
	 * set the button colors to None.
	 */
	public void setButtonColorsNone(){
		setBackgroundColorNone();
		buttonColors[NORMAL_INDEX] = NONE_COLOR;
		buttonColors[DOWN_INDEX] = NONE_COLOR;
		buttonColors[UP_INDEX] = NONE_COLOR;
		hasButtonColors = false;
	}
	
	/**
	 * set text to the button layer.
	 * @param text
	 * 			the text of button layer to show.
	 */
	public void setText(String text){
		if(labelLayer==null){
			initLabelLayer(text);
		}else{
			labelLayer.setText(text);
		}
	}
	
	/**
	 * set text size to this button layer.
	 * @param textSize
	 * 			text size.
	 */
	public void setTextSize(float textSize){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setTextSize(textSize);
	}
	
	/**
	 * set the type face to this button layer.
	 * @param typeface
	 * 			the type face to the button layer.
	 */
	public void setTextStyle(Typeface typeface){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setTypeface(typeface);
	}
	
	/**
	 * set the text color to this button layer.
	 * @param color
	 * 			the text color is 
	 */
	public void setTextColor(int color){
		if(labelLayer!=null && labelLayer.getPaint()!=null)
			labelLayer.getPaint().setColor(color);
	}
	
	@Override
	protected void onTouched(MotionEvent event) {
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
	
	/**
	 * set on click listener to the button layer to listen the event.
	 * @param onClickListener
	 */
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
	
	/**
	 * the on click listener use for the button layer.
	 * @author irons
	 *
	 */
	public interface OnClickListener{
		public void onClick(ButtonLayer buttonLayer);
	}
	
}

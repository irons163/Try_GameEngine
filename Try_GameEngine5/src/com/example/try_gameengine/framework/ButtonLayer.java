package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class ButtonLayer extends Layer{
	private final int NORMAL_BITMAP_INDEX = 0;
	private final int DOWN_BITMAP_INDEX = 1;
	private final int UP_BITMAP_INDEX = 2;
	
	boolean isClickCancled = false;
	private String text;
	private Paint paint;
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(ButtonLayer buttonLayer) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
		buttonBitmaps[NORMAL_BITMAP_INDEX] = bitmap;
		paint = new Paint();
	}

	public ButtonLayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
		buttonBitmaps[NORMAL_BITMAP_INDEX] = bitmap;
		paint = new Paint();
	}

	public ButtonLayer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
		buttonBitmaps[NORMAL_BITMAP_INDEX] = bitmap;
		paint = new Paint();
	}

	public ButtonLayer(String text, int w, int h, boolean autoAdd){
		super(w, h, autoAdd);
		this.text = text;
		paint = new Paint();
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
//		if(bitmap!=null){
//			canvas.drawBitmap(bitmap, 0, 0, paint);
//		}
//		if(text!=null)1
//			canvas.drawText(text, getX(), getY(), paint!=null?paint:this.paint);
	}
	
	public void setBitmap(Bitmap normal){
		this.bitmap = normal;
		buttonBitmaps[NORMAL_BITMAP_INDEX] = normal;
	}
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap){
		this.bitmap = bitmap;
		buttonBitmaps[NORMAL_BITMAP_INDEX] = bitmap;
		setInitWidth(bitmap.getWidth());
		setInitHeight(bitmap.getHeight());
	}
	
	public void setButtonBitmap(Bitmap normal, Bitmap down , Bitmap up){
		buttonBitmaps[NORMAL_BITMAP_INDEX] = normal;
		buttonBitmaps[DOWN_BITMAP_INDEX] = down;
		buttonBitmaps[UP_BITMAP_INDEX] = up;
	}
	
	public void seButton1tBitmaps(Bitmap[] buttonBitmaps){
		this.buttonBitmaps = buttonBitmaps;
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
	
	public boolean onTouch(MotionEvent event){
		
		float x = event.getX();
		float y = event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN && this.dst.contains(x, y)){
			if(buttonBitmaps[DOWN_BITMAP_INDEX]!=null){
				this.bitmap = buttonBitmaps[DOWN_BITMAP_INDEX];
			}
			isClickCancled = false;
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && this.dst.contains(x, y)){
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && !this.dst.contains(x, y)){
			isClickCancled = true;
			return false;			
		}else if(event.getAction()==MotionEvent.ACTION_UP && isClickCancled){
			if(buttonBitmaps[UP_BITMAP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_BITMAP_INDEX];
			}
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_UP && this.dst.contains(x, y) && !isClickCancled){
			if(buttonBitmaps[UP_BITMAP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_BITMAP_INDEX];
			}
			onClickListener.onClick(this);
			return true;
		}
		return false;
	}
	
	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	
	public interface OnClickListener{
		public void onClick(ButtonLayer buttonLayer);
	}
	
}

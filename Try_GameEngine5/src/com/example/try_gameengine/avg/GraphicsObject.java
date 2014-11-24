package com.example.try_gameengine.avg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GraphicsObject {
	private FontObject font;
	private LColor color;
	private boolean isClose;
	private Canvas canvas;

	private Paint paint;
	
	public GraphicsObject(Canvas canvas, Paint paint) {
		// TODO Auto-generated constructor stub
		if (paint == null) {
			this.paint = new Paint();
		}else{
			this.paint = paint;
		}
		this.canvas = canvas;
		this.setFont(FontObject.getDefaultFont());
	}
	
	public FontObject getFont(){
		return font;
	}
	
	public void setFont(FontObject font){
		this.font = font;
	}
	
	public LColor getColor(){
		return color;
	}
	
	public void setColor(LColor color){
		this.color = color;
	}
	
	public void drawString(String message, int x, int y) {
		if (isClose) {
			return;
		}
		int flag = paint.getFlags();
		int colorTmp = paint.getColor();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color.getARGB());
		canvas.drawText(message, x, y, paint);
		paint.setFlags(flag);
		paint.setColor(colorTmp);
	}
	
	public void drawImage(Bitmap img, int x, int y) {
		if (img != null) {
			drawBitmap(img, x, y);
		}
	}
	
	public void drawBitmap(Bitmap bit, int x, int y) {
		if (isClose) {
			return;
		}
		if (bit == null) {
			return;
		}
		canvas.drawBitmap(bit, x, y, paint);
	}
	
	public void setAlphaValue(int alpha) {
		if (isClose) {
			return;
		}
		paint.setAlpha(alpha);
	}

	public void setAlpha(float alpha) {
		setAlphaValue((int) (255 * alpha));
	}

	public float getAlpha() {
		if (isClose) {
			return 0F;
		}
		return paint.getAlpha() / 255;
	}
	
	public void dispose() {
		isClose = true;
		font = null;
		paint = null;
		canvas = null;
//		if (mirrorImage != null) {
//			mirrorImage.clear();
//			mirrorImage = null;
//		}
	}
}

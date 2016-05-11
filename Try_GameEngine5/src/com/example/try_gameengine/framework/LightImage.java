package com.example.try_gameengine.framework;

import com.example.try_gameengine.avg.GraphicsUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class LightImage {
	private Bitmap bitmap;
	private ClipInfo clipInfo;
	
	public class ClipInfo{
		private int clipStartX, clipStartY, width, height;

		public ClipInfo(int clipStartX, int clipStartY, int width, int height) {
			this.clipStartX = clipStartX;
			this.clipStartY = clipStartY;
			this.width = width;
			this.height = height;
		}

		public int getClipStartX() {
			return clipStartX;
		}

		public int getClipStartY() {
			return clipStartY;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public void setClipStartX(int clipStartX) {
			this.clipStartX = clipStartX;
		}

		public void setClipStartY(int clipStartY) {
			this.clipStartY = clipStartY;
		}

		public void setOffsetX(int offsetX) {
			this.width = offsetX;
		}

		public void setOffsetY(int offsetY) {
			this.height = offsetY;
		}

	}
	
	public LightImage() {
		// TODO Auto-generated constructor stub
	}
	
	public LightImage(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public LightImage(String resPath){
		this.bitmap = GraphicsUtils.loadImage(resPath);
	}
	
	public LightImage(LightImage lightImage) {
		this.bitmap = lightImage.getBitmap();
		this.clipInfo = lightImage.getClipIfno();
	}
	
	public void setClipInfo(ClipInfo clipInfo){
		this.clipInfo = clipInfo;
	}
	
	public ClipInfo getClipIfno(){
		return this.clipInfo;
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	
	public void drawSelf(Canvas canvas){
		canvas.drawBitmap(bitmap, new Rect(clipInfo.getClipStartX(), clipInfo.getClipStartY(), clipInfo.getWidth(), clipInfo.getHeight()), new RectF(), null);
	}
}

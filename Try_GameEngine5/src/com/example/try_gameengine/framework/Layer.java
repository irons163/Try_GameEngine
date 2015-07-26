package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Layer extends ALayer{

	public Layer(int w, int h, boolean autoAdd) {
		super(w, h, autoAdd);
		// TODO Auto-generated constructor stub
	}

	public Layer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		super(bitmap, w, h, autoAdd, level);
		// TODO Auto-generated constructor stub
	}

	public Layer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		super(bitmap, w, h, autoAdd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		
		if(bitmap!=null){
			src.left = 0;
			src.top = 0;
			src.right = w;
			src.bottom = h;
			dst.left = (float) (centerX - w / 2);
			dst.top = (float) (centerY - h / 2);
			dst.right = (float) (dst.left + w);
			dst.bottom = (float) (dst.top + h);
			canvas.drawBitmap(bitmap, src, dst, paint);
		}
	}

}

package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class HUDLayer extends Layer{

	public HUDLayer(Bitmap bitmap, float x, float y, boolean autoAdd) {
		super(bitmap, x, y, autoAdd);
		// TODO Auto-generated constructor stub
	}

	public HUDLayer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		for(ALayer layer : layers){
			layer.drawSelf(canvas, paint);
		}
	}
	
}

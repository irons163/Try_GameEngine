package com.example.try_gameengine.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackGroundLayer extends ALayer {
	public BackGroundLayer(Bitmap bitmap, int w, int h) {
		super(bitmap, w, h, true);
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		src.left = 0;
		src.top = 0;
		src.right = w;
		src.bottom = h;
		dst.left = (int) getX();
		dst.top = (int) getY();
		dst.right = dst.left + w;
		dst.bottom = dst.top + h;
		canvas.drawBitmap(bitmap, src, dst, paint);
	}
}

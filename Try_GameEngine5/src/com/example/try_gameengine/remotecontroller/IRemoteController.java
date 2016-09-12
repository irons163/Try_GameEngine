package com.example.try_gameengine.remotecontroller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author irons
 * 
 */

public interface IRemoteController {
	public boolean onTouchEvent(MotionEvent event);

	public void drawRemoteController(Canvas canvas, Paint paint);
}

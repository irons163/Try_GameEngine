package com.example.try_gameengine.framework;

import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class DialogLayer extends HUDLayer{
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(ButtonLayer buttonLayer) {
			// TODO Auto-generated method stub
			
		}
	};
	
	protected DialogLayer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		super.onTouchEvent(event);
//		return true;
//	}
}

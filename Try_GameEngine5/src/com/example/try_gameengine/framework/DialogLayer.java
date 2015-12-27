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
	
	
	public ButtonLayer leftButton, midButton, rightButton;
	
	protected DialogLayer(float x, float y, boolean autoAdd) {
		super(x, y, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	public void setCostumeButton(ButtonLayer buttonLayer){
		
	}
	
	public void initWithOneButton(){
		midButton = new ButtonLayer("OK", w, h, false);
		midButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				DialogLayer.this.removeFromParent();
			}
		});
		addChild(midButton);
	}
	
	
	
	public void setButtonOnClickListener(ButtonLayer.OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		if(midButton!=null)
			midButton.drawSelf(canvas, paint);
	}

//	@Override
//	public void drawSelf(Canvas canvas, Paint paint) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		super.onTouchEvent(event);
//		return true;
//	}
}

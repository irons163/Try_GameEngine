package com.example.try_gameengine.framework;

import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * {@code DialogLayer} is a layer to show dialog.
 * @author irons
 *
 */
public class DialogLayer extends HUDLayer{
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(ButtonLayer buttonLayer) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public ButtonLayer leftButton, midButton, rightButton;
	
	public void setCostumeButton(ButtonLayer buttonLayer){
		
	}
	
	public void initWithOneButton(){
		midButton = new ButtonLayer("OK", getWidth(), getHeight(), false);
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
}

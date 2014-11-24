package com.example.try_gameengine.avg;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;


/**
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class LMessage {

	private FontObject messageFont = FontObject.getFont(LSystem.FONT_NAME, 40);

	private LColor fontColor = LColor.white;

	private long printTime, totalDuration;

	private int dx, dy, dw, dh;

	private MessageBoxStringView print;

	protected boolean visible = true;
	
	float alpha = 1.0f;
	
	public int width ,height;
	
	Bitmap background;
	
	public int x;
	public int y;
	
	public LMessage(Context context, int width, int height) {
		this(context, 0, 0, width, height);
	}

	public LMessage(Context context, int x, int y, int width, int height) {
		this(context, null, x, y, width, height);
	}

	public LMessage(Context context, String fileName, int x, int y) {
		this(context, GraphicsUtils.loadImage(fileName), x, y);
	}

	public LMessage(Context context, Bitmap formImage, int x, int y) {
		this(context, formImage, x, y, formImage.getWidth(), formImage.getHeight());
	}

	public LMessage(Context context, Bitmap formImage, int x, int y, int width, int height) {
		if (formImage == null) {
			this.setBackground(formImage, width, height);
			this.setAlpha(0.3F);
		} else {
			this.setBackground(formImage, width, height);
			if (width == -1) {
				width = formImage.getWidth();
			}
			if (height == -1) {
				height = formImage.getHeight();
			}
		}
		
		this.x = x;
		this.y = y;
		
		this.print = new MessageBoxStringView(context, this, x, y, width, height);
		this.setTipIcon("system/images/creese.png");
		this.totalDuration = 50;
	}

//	public void complete() {
//		print.complete();
//	}

	public void setLeftOffset(int left) {
		print.setLeftOffset(left);
	}

	public void setTopOffset(int top) {
		print.setTopOffset(top);
	}

	public int getLeftOffset() {
		return print.getLeftOffset();
	}

	public int getTopOffset() {
		return print.getTopOffset();
	}

	public int getMessageLength() {
		return print.getMessageLength();
	}

	public void setMessageLength(int messageLength) {
		print.setMessageLength(messageLength);
	}

	public void setTipIcon(String fileName) {
		print.setCreeseIcon(GraphicsUtils.loadImage(fileName));
	}

	public void setTipIcon(Bitmap icon) {
		print.setCreeseIcon(icon);
	}

//	public void setNotTipIcon() {
//		print.setCreeseIcon(null);
//	}

	public void setDelay(long delay) {
		this.totalDuration = (delay < 1 ? 1 : delay);
	}

	public boolean isComplete() {
		return print.isComplete();
	}

	public void setPauseIconAnimationLocation(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setMessage(String context, boolean isComplete) {
		print.setMessage(context, isComplete);
	}

	public void setMessage(String context) {
		print.setMessage(context);
	}

	/**
	 * 處理點擊事件（請重載實現）
	 * 
	 */
	public void doClick() {
	}

	protected void processTouchClicked() {
		this.doClick();
	}

	public void update(long elapsedTime) {
		if (!visible) {
			return;
		}
//		super.update(elapsedTime);
		
		
		
		if (print.isComplete()) {
//			animation.update(elapsedTime);
		}
		printTime += elapsedTime;
		if (printTime >= totalDuration) {
			printTime = printTime % totalDuration;
			print.next();
		}
	}

	public void createCustomUI(GraphicsObject g, int x, int y) {
		if (!visible) {
			return;
		}
		LColor oldColor = g.getColor();
		FontObject oldFont = g.getFont();
		g.setColor(fontColor);
		g.setFont(messageFont);
		print.draw(g, fontColor);
		g.setColor(oldColor);
		g.setFont(oldFont);
		if (print.isComplete()) {
//			if (animation.getSpriteImage() != null) {
//				g.setAlpha(1.0F);
//				updateIcon();
//				g.drawImage(animation.getSpriteImage().getImage(), dx, dy);
//			}
		}

	}

	private void updateIcon() {
		this.setPauseIconAnimationLocation(x + getWidth() - dw / 2
				- 20, y + getHeight() - dh - 10);
	}

	public LColor getFontColor() {
		return fontColor;
	}

	public void setFontColor(LColor fontColor) {
		this.fontColor = fontColor;
	}

	public FontObject getMessageFont() {
		return messageFont;
	}

	public void setMessageFont(FontObject messageFont) {
		this.messageFont = messageFont;
	}

	public String getUIName() {
		return "Message";
	}

	public void setBackground(String fileName) {
		this.setBackground(GraphicsUtils.loadImage(fileName, false));
	}

	public void setBackground(String fileName, boolean t) {
		this.setBackground(GraphicsUtils.loadImage(fileName, t));
	}

//	public void setBackground(LColor color) {
//		Bitmap image = Bitmap.createImage(getWidth(), getHeight(),
//				Config.RGB_565);
//		LGraphics g = image.getLGraphics();
//		g.setColorAll(color);
//		g.dispose();
//		setBackground(image);
//	}

	public void setBackground(Bitmap background) {
		this.background = background;
		this.setAlpha(1.0f);
		this.width = background.getWidth();
		this.height = background.getHeight();
//		if (this.width == 0) {
//			this.width = 10;
//		}
//		if (this.height == 0) {
//			this.height = 10;
//		}
//		validateUI();
	}
	
	public void setBackground(Bitmap background, int width, int height) {
		this.background = background;
		this.setAlpha(1.0f);
		this.width = width;
		this.height = height;
	}
	
	public void setAlpha(float alpha){
		this.alpha = alpha;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public MessageBoxStringView getPrint(){
		return print;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}
		this.visible = visible;
	}
	
	public boolean intersects(int x1, int y1) {
		return (this.visible)
				&& (x1 >= x && x1 <= x + this.width
						&& y1 >= y && y1 <= y
						+ this.height);
	}
	
	public void onTouch(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_UP){
			float touchX = event.getX();
			float touchY = event.getY();
			
			if((touchX >= x && touchX <= x+width) && (touchY >= y && touchY <= y+height)){
				
				
				
			}
		}
	}
}


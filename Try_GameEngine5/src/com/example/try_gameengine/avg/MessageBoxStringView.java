package com.example.try_gameengine.avg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MessageBoxStringView {
	SurfaceHolder holder;
	protected boolean visible = true;
	private char[] showMessages;
	
	private int size, tmp_left, left, fontSize, fontHeight;
	private int width, height, leftOffset, topOffset, next, messageCount;
	private int messageLength = 10;
	private int interceptMaxString;

	private int interceptCount;

	private LColor fontColor = LColor.white;
	private boolean onComplete, newLine;
	private Bitmap creeseIcon;
	private int iconWidth;
	private StringBuffer messageBuffer = new StringBuffer(messageLength);
	
	private String messages;
	
	View view;
	int x, y;
	Context context;
	LMessage message;
	
	public MessageBoxStringView(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		
//		holder = getHolder();
//		holder.addCallback(this);
	}
	
	public MessageBoxStringView(Context context, LMessage message, int x, int y, int width, int height) {
//		super(context);
		// TODO Auto-generated constructor stub
		
//		holder = getHolder();
//		holder.addCallback(this);
		this.context = context;
		this.message = message;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public MessageView getView(){
		MessageView messageView = new MessageView(context, x, y, width, height);

		return messageView;
	}
	
	private void draw(GraphicsObject g) {
		draw(g, LColor.white);
	}
	
	public void draw(GraphicsObject g, LColor old) {
		if (!visible) {
			return;
		}
		float alpha = g.getAlpha();
		g.setAlpha(1.0f);
		drawMessage(g, old);
		g.setAlpha(alpha);
	}
	
	private void drawMessage(GraphicsObject g, LColor old) {
		if (!visible) {
			return;
		}
		synchronized (showMessages) {
			this.size = showMessages.length;
			this.fontSize = g.getFont().getSize();
			this.fontHeight = g.getFont().getHeight();
			this.tmp_left = (width - (fontSize * messageLength)) / 2
					- (int) (fontSize * 1.5);
			this.left = tmp_left;
			int index = 0, offset = 0, font = 0, tmp_font = 0;
			int fontSizeDouble = fontSize * 2;
			for (int i = 0; i < size; i++) {
				if (interceptCount < interceptMaxString) {
					interceptCount++;
					g.setColor(fontColor);
					continue;
				} else {
					interceptMaxString = 0;
					interceptCount = 0;
				}
				if (showMessages[i] == 'n'
						&& showMessages[i > 0 ? i - 1 : 0] == '\\') {
					index = 0;
					left = tmp_left;
					offset++;
					continue;
				} else if (showMessages[i] == '\n') {
					index = 0;
					left = tmp_left;
					offset++;
					continue;
				} else if (showMessages[i] == '<') {
					LColor color = getColor(showMessages[i < size - 1 ? i + 1
							: i]);
					if (color != null) {
						interceptMaxString = 1;
						fontColor = color;
					}
					next();
					continue;
				} else if (showMessages[i > 0 ? i - 1 : i] == '<'
						&& getColor(showMessages[i]) != null) {
					continue;
				} else if (showMessages[i] == '/') {
					if (showMessages[i < size - 1 ? i + 1 : i] == '>') {
						interceptMaxString = 1;
						fontColor = old;
					}
					continue;
				} else if (index > messageLength) {
					index = 0;
					left = tmp_left;
					offset++;
					newLine = false;
				} else if (showMessages[i] == '\\') {
					continue;
				}
				String mes = String.valueOf(showMessages[i]);
				tmp_font = g.getFont().charWidth(showMessages[i]);
				if (Character.isLetter(showMessages[i])) {
					font = tmp_font;
				} else {
					font = fontSize;
				}
				left += font;
				if (i != size - 1) {
					g.drawString(mes, x + left + leftOffset,
							(offset * fontHeight) + y + fontSizeDouble
									+ topOffset);
				} else if (!newLine && !onComplete) {
					g.drawImage(creeseIcon, x + left + leftOffset
							+ iconWidth, (offset * fontHeight) + y
							+ fontSize + topOffset);
				}
				index++;
			}
			if (messageCount == next) {
				onComplete = true;
			}
			
			g.drawImage(message.background, x, y);
		}
	}
	
	public boolean next() {
		synchronized (showMessages) {
			if (!onComplete) {
				if (messageCount == next) {
					onComplete = true;
					return false;
				}
				if (messageBuffer.length() > 0) {
					messageBuffer.delete(messageBuffer.length() - 1,
							messageBuffer.length());
				}
				this.messageBuffer.append(messages.charAt(messageCount));
				this.messageBuffer.append("_");
				this.showMessages = messageBuffer.toString().toCharArray();
				this.size = showMessages.length;
				this.messageCount++;
			} else {
				return false;
			}
			return true;
		}
	}
	
	private LColor getColor(char flagName) {
		if ('r' == flagName || 'R' == flagName) {
			return LColor.red;
		}
		if ('b' == flagName || 'B' == flagName) {
			return LColor.black;
		}
		if ('l' == flagName || 'L' == flagName) {
			return LColor.blue;
		}
		if ('g' == flagName || 'G' == flagName) {
			return LColor.green;
		}
		if ('o' == flagName || 'O' == flagName) {
			return LColor.orange;
		}
		if ('y' == flagName || 'Y' == flagName) {
			return LColor.yellow;
		} else {
			return null;
		}
	}
	
	public void setMessage(String context) {
		setMessage(context, false);
	}

	public void setMessage(String context, boolean isComplete) {
		this.visible = false;
		this.showMessages = new char[] { '\0' };
		this.interceptMaxString = 0;
		this.next = 0;
		this.messageCount = 0;
		this.interceptCount = 0;
		this.size = 0;
		this.tmp_left = 0;
		this.left = 0;
		this.fontSize = 0;
		this.fontHeight = 0;
		this.messages = context;
		this.next = context.length();
		this.onComplete = false;
		this.newLine = false;
		this.messageCount = 0;
		this.messageBuffer.delete(0, messageBuffer.length());
//		if (isComplete) {
//			this.complete();
//		}
		this.visible = true;
	}
	
	public boolean isComplete() {
		return onComplete;
	}
	
	public Bitmap getCreeseIcon() {
		return creeseIcon;
	}

	public void setCreeseIcon(Bitmap creeseIcon) {
		this.creeseIcon = creeseIcon;
		this.iconWidth = creeseIcon.getWidth();
	}

	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

//	public int getHeight() {
//		return height;
//	}

	public void setHeight(int height) {
		this.height = height;
	}

//	public int getWidth() {
//		return width;
//	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLeftOffset() {
		return leftOffset;
	}

	public void setLeftOffset(int leftOffset) {
		this.leftOffset = leftOffset;
	}

	public int getTopOffset() {
		return topOffset;
	}

	public void setTopOffset(int topOffset) {
		this.topOffset = topOffset;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public class MessageView extends SurfaceView implements Callback{
		private Canvas canvas;
		boolean isUseSelf = true;
		
		public MessageView(Context context, int x, int y, int width, int height) {
			super(context);
			holder = getHolder();
			holder.addCallback(this);
			
			holder.setFormat(PixelFormat.TRANSPARENT);
			setZOrderOnTop(true);
			setZOrderMediaOverlay(true);
		}
		
		public void setIsUseSelf(boolean isUseSelf){
			this.isUseSelf = false;
		}
		
		public void setCanvas(Canvas canvas){
			this.canvas = canvas;
		}
		
		public void draw(){
			if(isUseSelf){
				canvas = holder.lockCanvas();
				canvas.drawColor(Color.WHITE);
			}
//			canvas.drawText("123" +
//					"123", x, y, paint);
			Paint paint = new Paint();
			paint.setTextSize(message.getMessageFont().getSize());
			GraphicsObject g = new GraphicsObject(canvas, paint);
			
			message.createCustomUI(g, x, y);
//			drawMessage(g, new LColor(100, 100, 100));
			
			AVGUtils.pause(30);
			
			message.update(30);
			
			if(isUseSelf)
				holder.unlockCanvasAndPost(canvas);
		}
				
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					draw();
					
				}
				
			}
		});

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			if(isUseSelf)
				thread.start();
			ViewGroup.LayoutParams lp = getLayoutParams();
			lp.width = width;
			lp.height = height;
			setLayoutParams(lp);
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub

//			bb(event);

			return true;
		}
	}
	

	
}

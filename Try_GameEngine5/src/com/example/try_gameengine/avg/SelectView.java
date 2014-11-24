package com.example.try_gameengine.avg;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;

public class SelectView extends SurfaceView implements Callback{
	private Canvas canvas;
	boolean isUseSelf = true;
	SurfaceHolder holder;
	private int width, height, x, y;
	
	public SelectView(Context context, int x, int y, int width, int height) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		
		holder.setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);
		setZOrderMediaOverlay(true);
		
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void setIsUseSelf(boolean isUseSelf){
		this.isUseSelf = false;
	}
	
	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
	}
	
	int size;
	
	public void setTextSize(int size){
		this.size = size;
	}
	
	int color;
	
	public void setTextColor(int color){
		this.color = color;
	}
	
	String[] selects;
	
	public void setSelects(String[] selects){
		this.selects = selects;
		
	}
	
	boolean isVisible;
	
	public void setVisible(boolean isVisible){
		this.isVisible = isVisible;
	}
	
	int selectIndex = -1;
	public int getResultIndex(){
		return selectIndex;
	}
	
	public void setMessage(String message, List<String> list) {
		setMessage(message, getListToStrings(list));
	}

	public void setMessage(String[] selects) {
		setMessage(null, selects);
	}
	
	private String message, result;
	
	int selectSize, doubleSizeFont;
	
	public void setMessage(String message, String[] selects) {
		this.message = message;
		this.selects = selects;
		this.selectSize = selects.length;
		if (doubleSizeFont == 0) {
			doubleSizeFont = 20;
		}
	}
	
	private static String[] getListToStrings(List<String> list) {
		if (list == null || list.size() == 0)
			return null;
		String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (String) list.get(i);
		}
		return result;
	}
	
	public void draw(){
		if(!isVisible)
			return;
		if(isUseSelf){
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.WHITE);
		}
//		canvas.drawText("123" +
//				"123", x, y, paint);
		Paint paint = new Paint();
		paint.setTextSize(size);
		paint.setColor(color);
		GraphicsObject g = new GraphicsObject(canvas, paint);
		
//		message.createCustomUI(g, x, y);
		
		canvas.drawText(message, x, y+150, paint);
		
		for(int i = 0; i < selects.length; i++){
			String text = selects[i];
			canvas.drawText(text, x, y+i*height/3+200, paint);
		}

//		drawMessage(g, new LColor(100, 100, 100));
		
		AVGUtils.pause(30);
		
//		message.update(30);
		
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

//		bb(event);

		if(event.getAction() == MotionEvent.ACTION_DOWN){
			float touchX = event.getX();
			float touchY = event.getY();
			
			for(int i = 0; i < selects.length; i++){
				if ((touchX >= x && touchX <= x + width)
						 && (touchY >= y+i*height/3+200 && touchY <= y+(i+1)*height/3+200)) {
					selectIndex = i;
				}
				
			}
		}
		
		return true;
	}
}

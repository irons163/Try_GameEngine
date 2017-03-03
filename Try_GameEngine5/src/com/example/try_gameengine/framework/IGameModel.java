package com.example.try_gameengine.framework;

import com.example.try_gameengine.Camera.Camera;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public interface IGameModel {
	public Data getData();
	public void setData(Data data);
	public void registerObserver(IMoveObserver moveObserver);
	public void removeObserver(IMoveObserver moveObserver);
	public void onTouchEvent(MotionEvent event);
	public void start();
	public void setSurfaceHolder(SurfaceHolder surfaceHolder);
	public void restart();
	public void stop();
	public int getBackgroundColor();
	public void setBackgroundColor(int backgroundColor);
	public Camera getCamera();
	public void setCamera(Camera camera);
	public void addPreProcessBlock(ProcessBlock processBlock);
}

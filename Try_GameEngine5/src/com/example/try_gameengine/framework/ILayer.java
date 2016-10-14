package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.example.try_gameengine.framework.ALayer.LayerParam;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

//import com.example.try_gameengine.framework.ILayer.OnLayerClickListener;
//import com.example.try_gameengine.framework.ILayer.OnLayerLongClickListener;

public interface ILayer extends Cloneable{
	
	public void setPosition(float x, float y);

	/** * 绘制自己的抽象接口 * * @param canvas * @param paint */
	public abstract void drawSelf(Canvas canvas, Paint paint);

	public RectF getSmallViewRect();

	public void setSmallViewRect(RectF smallViewRect);

	public void remove(ILayer layer);
	
	public boolean isAutoAdd();

	public void addWithLayerLevelIncrease(ILayer layer);
	public void addWithLayerLevelIncrease(ILayer layer, int increaseNum);

	public void addWithOutLayerLevelIncrease(ILayer layer);
	
	public void addWithLayerLevel(ILayer layer, int layerLevel);
	
	//composite
	public void addChild(ILayer layer);

	public ILayer getChild(int i);
	
	public List<ILayer> getLayers();

	public Iterator createIterator();

	public void moveAllChild(int offsetLayerLevel);
	
	public void setParent(ILayer parent);
	
	public ILayer getParent();
	
	public void setInitWidth(int w);
	
	public void setInitHeight(int h);
	
	public void setWidth(int w);
	
	public void setHeight(int h);
	
	public int getWidth();
	
	public int getHeight();
	
	public float getX();
	
	public float getLeft();
	
	public float getCenterX();
	
	public void setX(float x);
	
	public float getY();
	
	public float getTop();
	
	public float getCenterY();
	
	public void setY(float y);
	
	public void setBitmapAndAutoChangeWH(Bitmap bitmap);
	
	public void setBitmap(Bitmap bitmap);
	
	public Bitmap getBitmap();
	
	public RectF getDst();

	public int getLayerLevel();

	public void setLayerLevel(int layerLevel);

	public int getAlpha();

	public void setAlpha(int alpha);

	public Paint getPaint();

	public void setPaint(Paint paint);
	
	public void removeFromParent();
	
	public void removeFromAuto();

	public int getzPosition();

	public void setzPosition(int zPosition);

//	public boolean iszPositionValid();
	
	public boolean isTouching();

	public void setTouching(boolean isTouching);
	
	public boolean isPressed();
	
	public void setPressed(boolean pressed);

	public boolean isComposite();

	public void setComposite(boolean isComposite);
	
	public PointF getLocationInScene();

	public void setLocationInScene(PointF locationInScene);

	public PointF locationInLayer(float x, float y);
	
	public PointF locationInSceneByCompositeLocation(float locationInLayerX, float locationInLayerY);
	
	public RectF frameInSceneByCompositeLocation();
	
	public ILayer getRootLayer();
	
	public List<ILayer> getLayersFromRootLayerToCurrentLayerInComposite();
	
//	public void setOnLayerClickListener(OnLayerClickListener onLayerClickListener);
//	
//	public void setOnLayerLongClickListener(OnLayerLongClickListener onLayerLongClickListener);
	
	public boolean onTouchEvent(MotionEvent event);
	
	public Object clone() throws CloneNotSupportedException;

	public LayerParam getLayerParam();

	public RectF getFrame();
	
	public RectF getFrameInScene();
	
	public void setFrameInScene(RectF frameInScene);
	
	public boolean isClipOutside();
	
	public void setBackgroundColor(int backgroundColor);
	
	public void setFlag(int flag);
	
	public int getFlag();
	
	public void addFlag(int flag);
	
	public void removeFlag(int flag);
	
	public boolean isUsedzPosition();
	
	public void setAutoAdd(boolean autoAdd);
	
	public boolean isEnable();
	
	public void setEnable(boolean isEnable);
	
	public boolean isHidden();
	
	public void setHidden(boolean isHidden);
	
	public boolean isVisible();
	
	public void setVisible(boolean isVisible);
}

package com.example.try_gameengine.framework;

import org.loon.framework.android.game.physics.RectBox;

import com.example.try_gameengine.avg.NumberUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.view.MotionEvent;

public class StatusBar extends ALayer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean hit, visible, showValue, dead;

	private int width, height;

	private int value, valueMax, valueMin;

	private float w, we;

	private String hpString;

	private int color;

	private RectBox rect;

	public StatusBar(int width, int height) {
		this(0, 0, width, height);
	}

	public StatusBar(int x, int y, int width, int height) {
		this(100, 100, x, y, width, height);
	}

	public StatusBar(int value, int max, int x, int y, int width, int height) {
		this.value = value;
		this.valueMax = max;
		this.valueMin = value;
		this.w = (float) (width * value) / valueMax;
		this.we = (float) (width * valueMin) / valueMax;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.hit = true;
		this.setPosition(x, y);
		this.color = Color.RED;
		setPaint(new Paint());
	}

	public void set(int v) {
		this.value = v;
		this.valueMax = v;
		this.valueMin = v;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
	}

	public void empty() {
		this.value = 0;
		this.valueMin = 0;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
	}

	private void drawBar(Canvas g, int i, int j, int k, int x, int y) {
//		g.setColor(Color.GRAY);
//		g.fillRect(x, y, width, height);
		
		if (valueMin <= value) {
			if (!dead) {
				getPaint().setColor(Color.YELLOW);
			}
			getPaint().setStyle(Style.FILL);
			g.drawRect(x, y, x + (width * j) / k, y + height,getPaint());
			getPaint().setColor(color);
			g.drawRect(x, y, x + (width * i) / k, y + height,getPaint());
		} else {
			getPaint().setStyle(Style.FILL);
			getPaint().setColor(Color.YELLOW);
			g.drawRect(x, y, x + (width * i) / k, y + height,getPaint());
			getPaint().setColor(color);
			g.drawRect(x, y, x + (width * j) / k, y + height,getPaint());
		}
		getPaint().setColor(Color.WHITE);
	}

	public void updateTo(int v1, int v2) {
		this.setValue(v1);
		this.setUpdate(v2);
	}

	public void setUpdate(int val) {
		valueMin = NumberUtils.mid(0, val, valueMax);
		w = (float) (width * value) / valueMax;
		we = (float) (width * valueMin) / valueMax;
	}

	public void setDead(boolean d) {
		this.dead = d;
	}

	public boolean state() {
		if (w == we)
			return false;
		if (w > we) {
			w--;
			value = NumberUtils.mid(valueMin, ((int) w * valueMax) / width,
					value);
		} else {
			w++;
			value = NumberUtils.mid(value, ((int) w * valueMax) / width,
					valueMin);
		}
		return true;
	}

	private void createUI(Canvas g) {
		if (visible) {			
			if (showValue) {
				hpString = "" + value;
//				g.setColor(Color.WHITE);
				Paint paint = getPaint();
				paint.setColor(Color.WHITE);
//				int w = g.getFont().stringWidth(hpString);
//				int h = g.getFont().getSize();
				Rect rect = new Rect();
				paint.getTextBounds(hpString, 0, hpString.length(), rect);
//				int w = (int) paint.measureText(hpString);
				int w = rect.width();
				int h = rect.height();
				g.drawText("" + value, (getXInScene() + width / 2 - w / 2) + 2, (getYInScene()
						+ height / 2 + h / 2), paint);
			}
			drawBar(g, (int) we, (int) w, width, (int)getXInScene(), (int)getYInScene());
		}
	}

	public float getXInScene() {
		if(isComposite())
			return getLocationInScene().x;
		else
			return super.getX();
	}
	
	public float getYInScene() {
		if(isComposite())
			return getLocationInScene().y;
		else
			return super.getY();
	}
//	public RectBox getCollisionBox() {
//		if (rect == null) {
//			rect = new RectBox(x(), y(), width, height);
//		} else {
//			rect.setBounds(x(), y(), width, height);
//		}
//		return rect;
//	}

	public boolean isShowHP() {
		return showValue;
	}

	public void setShowHP(boolean showHP) {
		this.showValue = showHP;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

//	public void update(long elapsedTime) {
//		if (visible && hit) {
//			state();
//		}
//	}

	public int getMaxValue() {
		return valueMax;
	}

	public void setMaxValue(int valueMax) {
		this.valueMax = valueMax;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
		this.state();
	}

	public int getMinValue() {
		return valueMin;
	}

	public void setMinValue(int valueMin) {
		this.valueMin = valueMin;
		this.w = (width * value) / valueMax;
		this.we = (width * valueMin) / valueMax;
		this.state();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

//	public float getAlpha() {
//		return 0;
//	}

	public Bitmap getBitmap() {
		return null;
	}

	public void dispose() {

	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		if (visible && hit) {
			state();
		}
		
		createUI(canvas);
		
		for(ILayer layer : getLayers()){
			if(layer.isComposite() && !layer.isAutoAdd()) //if the layer is auto add, not draw.
				layer.drawSelf(canvas, paint);
		}
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
}

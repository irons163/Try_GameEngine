package com.example.try_gameengine.framework;

import org.loon.framework.android.game.physics.RectBox;

import com.example.try_gameengine.avg.NumberUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

/**
 * @author irons
 *
 */
public class StatusBar extends Layer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean hit, visible, showValue, dead;

//	private int w, h;

	private int value, valueMax, valueMin;

	private float widthOfValue, widthOfValueMin;

	private String hpString;

	private int color;

	private RectBox rect;

	/**
	 * constructor.
	 * @param width
	 * 			the width of status bar.
	 * @param height
	 * 			the height of status bar.
	 */
	public StatusBar(int width, int height) {
		this(0, 0, width, height);
	}

	/**
	 * constructor.
	 * @param x
	 * 			the position X of status bar.
	 * @param y
	 * 			the position Y of status bar.
	 * @param width
	 * 			the width of status bar.
	 * @param height
	 * 			the height of status bar.
	 */
	public StatusBar(int x, int y, int width, int height) {
		this(100, 100, x, y, width, height);
	}

	/**
	 * constructor.
	 * @param value
	 * 			the current value of status bar.
	 * @param max
	 * 			the max value of status bar.
	 * @param x
	 * 			the position X of status bar.
	 * @param y
	 * 			the position Y of status bar.
	 * @param width
	 * 			the width of status bar.
	 * @param height
	 * 			the height of status bar.
	 */
	public StatusBar(int value, int max, int x, int y, int width, int height) {
		this.value = value;
		this.valueMax = max;
		this.valueMin = value;
		this.widthOfValue = (float) (width * value) / valueMax;
		this.widthOfValueMin = (float) (width * valueMin) / valueMax;
		this.setWidth(width);
		this.setHeight(height);
		this.visible = true;
		this.hit = true;
		this.setPosition(x, y);
		this.color = Color.RED;
		setPaint(new Paint());
	}

	/**
	 * set value.
	 * @param v
	 * 			the current value of status bar.
	 */
	public void set(int v) {
		this.value = v;
		this.valueMax = v;
		this.valueMin = v;
		this.widthOfValue = (getWidth() * value) / valueMax;
		this.widthOfValueMin = (getWidth() * valueMin) / valueMax;
	}

	/**
	 * set value to 0 in status bar.
	 */
	public void empty() {
		this.value = 0;
		this.valueMin = 0;
		this.widthOfValue = (getWidth() * value) / valueMax;
		this.widthOfValueMin = (getWidth() * valueMin) / valueMax;
	}

	/**
	 * draw bar.
	 * @param g canvas
	 * @param widthOfValueMin widthOfValueMin
	 * @param widthOfValue widthOfValue
	 * @param width w
	 * @param x getXInScene()
	 * @param y getYInScene()
	 */
	private void drawBar(Canvas g, int widthOfValueMin, int widthOfValue, int width, int x, int y) {
//		g.setColor(Color.GRAY);
//		g.fillRect(x, y, width, height);
		
		if (valueMin <= value) {
			if (!dead) {
				getPaint().setColor(Color.YELLOW);
			}
			getPaint().setStyle(Style.FILL);
			g.drawRect(x, y, x + (getWidth() * widthOfValue) / width, y + getHeight(),getPaint());
			getPaint().setColor(color);
			g.drawRect(x, y, x + (getWidth() * widthOfValueMin) / width, y + getHeight(),getPaint());
		} else {
			getPaint().setStyle(Style.FILL);
			getPaint().setColor(Color.YELLOW);
			g.drawRect(x, y, x + (getWidth() * widthOfValueMin) / width, y + getHeight(),getPaint());
			getPaint().setColor(color);
			g.drawRect(x, y, x + (getWidth() * widthOfValue) / width, y + getHeight(),getPaint());
		}
		getPaint().setColor(Color.WHITE);
	}

	/**
	 * @param v1 value
	 * @param v2 minValue
	 */
	public void updateTo(int v1, int v2) {
		this.setValue(v1);
		this.setUpdate(v2);
	}

	/**
	 * ?? bug?
	 * @param val
	 */
	public void setUpdate(int val) {
		valueMin = NumberUtils.mid(0, val, valueMax);
		widthOfValue = (float) (getWidth() * value) / valueMax;
		widthOfValueMin = (float) (getWidth() * valueMin) / valueMax;
	}

	/**
	 * set is dead.
	 * @param d
	 */
	public void setDead(boolean d) {
		this.dead = d;
	}

	/**
	 * to deal with status bar.
	 * If value > minValue, then auto decrease value and return true.
	 * If value < minValue, then auto increase value and return true.
	 * If value == minValue, then return false.
	 * @return boolean changed value or not.
	 */
	public boolean state() {
		if (widthOfValue == widthOfValueMin)
			return false;
		if (widthOfValue > widthOfValueMin) {
			widthOfValue--;
			value = NumberUtils.mid(valueMin, ((int) widthOfValue * valueMax) / getWidth(),
					value);
		} else {
			widthOfValue++;
			value = NumberUtils.mid(value, ((int) widthOfValue * valueMax) / getWidth(),
					valueMin);
		}
		return true;
	}

	/**
	 * draw UI.
	 * @param canvas
	 */
	private void createUI(Canvas canvas) {
		if (visible) {			
			if (showValue) {
				hpString = "" + value;
				Paint paint = getPaint();
				paint.setColor(Color.WHITE);
				Rect rect = new Rect();
				paint.getTextBounds(hpString, 0, hpString.length(), rect);
				int w = rect.width();
				int h = rect.height();
				canvas.drawText("" + value, (getXInScene() + w / 2 - w / 2) + 2, (getYInScene()
						+ h / 2 + h / 2), paint);
			}
			drawBar(canvas, (int) widthOfValueMin, (int) widthOfValue, getWidth(), (int)getXInScene(), (int)getYInScene());
		}
	}

	/**
	 * get position X in scene.
	 * @return position x.
	 */
	public float getXInScene() {
		if(isComposite())
			return getLocationInScene().x;
		else
			return super.getX();
	}
	
	/**
	 * get position Y in scene.
	 * @return position y.
	 */
	public float getYInScene() {
		if(isComposite())
			return getLocationInScene().y;
		else
			return super.getY();
	}

	/**
	 * is show HP or not.
	 * @return boolean.
	 */
	public boolean isShowHP() {
		return showValue;
	}

	/**
	 * @param showHP
	 */
	public void setShowHP(boolean showHP) {
		this.showValue = showHP;
	}

	/**
	 * @return
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
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

	/**
	 * get Max value.
	 * @return int.
	 */
	public int getMaxValue() {
		return valueMax;
	}

	/**
	 * set Max Value.
	 * @param valueMax
	 * 			set the max value of status bar.
	 */
	public void setMaxValue(int valueMax) {
		this.valueMax = valueMax;
		this.widthOfValue = (getWidth() * value) / valueMax;
		this.widthOfValueMin = (getWidth() * valueMin) / valueMax;
		this.state();
	}

	/**
	 * get min value.
	 * @return int.
	 */
	public int getMinValue() {
		return valueMin;
	}

	/**
	 * set min value.
	 * @param valueMin
	 * 			the min value of status bar.
	 */
	public void setMinValue(int valueMin) {
		this.valueMin = valueMin;
		this.widthOfValue = (getWidth() * value) / valueMax;
		this.widthOfValueMin = (getWidth() * valueMin) / valueMax;
		this.state(); //? maybe a bug.
	}

	/**
	 * get value.
	 * @return int.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * set Value.
	 * @param value
	 * 			set value of status bar.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * check is hit or not.
	 * @return boolean.
	 */
	public boolean isHit() {
		return hit;
	}

	/**
	 * set is hit in status bar.
	 * @param hit
	 */
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
		super.doDrawself(canvas, paint);
		
		if (visible && hit) {
			state();
		}
		
		createUI(canvas);
		
		super.doDrawChildren(canvas, paint);
	}

	@Override
	public void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
}

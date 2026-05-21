package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import com.example.try_gameengine.avg.NumberUtils
import org.loon.framework.android.game.physics.RectBox

/**
 * @author irons
 // */
class StatusBar(value: Int, max: Int, x: Int, y: Int, width: Int, height: Int) : Layer() {
    /**
     * check is hit or not.
     * @return boolean.
     // */
    /**
     * set is hit in status bar.
     * @param this.isHit
     // */
    var isHit: Boolean
    private var visible: Boolean
    /**
     * is show HP or not.
     * @return boolean.
     // */
    /**
     * @param showHP
     // */
    var isShowHP: Boolean = false
    private var dead = false

    /**
     * get value.
     * @return int.
     // */
    /**
     * set Value.
     * @param value
     * set value of status bar.
     // */
    //	private int w, h;
    var value: Int
    private var valueMax: Int
    private var valueMin: Int

    private var widthOfValue: Float
    private var widthOfValueMin: Float

    private var hpString: String? = null

    /**
     * @return
     // */
    /**
     * @param color
     // */
    var color: Int

    private var rect: RectBox? = null

    /**
     * constructor.
     * @param width
     * the width of status bar.
     * @param height
     * the height of status bar.
     // */
    constructor(width: Int, height: Int) : this(0, 0, width, height)

    /**
     * constructor.
     * @param x
     * the position X of status bar.
     * @param y
     * the position Y of status bar.
     * @param width
     * the width of status bar.
     * @param height
     * the height of status bar.
     // */
    constructor(x: Int, y: Int, width: Int, height: Int) : this(100, 100, x, y, width, height)

    /**
     * constructor.
     * @param value
     * the current value of status bar.
     * @param max
     * the max value of status bar.
     * @param x
     * the position X of status bar.
     * @param y
     * the position Y of status bar.
     * @param width
     * the width of status bar.
     * @param height
     * the height of status bar.
     // */
    init {
        this.value = value
        this.valueMax = max
        this.valueMin = value
        this.widthOfValue = (width * value).toFloat() / valueMax
        this.widthOfValueMin = (width * valueMin).toFloat() / valueMax
        this.setWidth(width)
        this.setHeight(height)
        this.visible = true
        this.isHit = true
        this.setPosition(x.toFloat(), y.toFloat())
        this.color = Color.RED
        setPaint(Paint())
    }

    /**
     * set value.
     * @param v
     * the current value of status bar.
     // */
    fun set(v: Int) {
        this.value = v
        this.valueMax = v
        this.valueMin = v
        this.widthOfValue = ((getWidth() * value) / valueMax).toFloat()
        this.widthOfValueMin = ((getWidth() * valueMin) / valueMax).toFloat()
    }

    /**
     * set value to 0 in status bar.
     // */
    fun empty() {
        this.value = 0
        this.valueMin = 0
        this.widthOfValue = ((getWidth() * value) / valueMax).toFloat()
        this.widthOfValueMin = ((getWidth() * valueMin) / valueMax).toFloat()
    }

    /**
     * draw bar.
     * @param g canvas
     * @param widthOfValueMin widthOfValueMin
     * @param widthOfValue widthOfValue
     * @param width w
     * @param x getXInScene()
     * @param y getYInScene()
     // */
    private fun drawBar(
        g: Canvas,
        widthOfValueMin: Int,
        widthOfValue: Int,
        width: Int,
        x: Int,
        y: Int
    ) {
//		g.setColor(Color.GRAY);
//		g.fillRect(x, y, width, height);

        if (valueMin <= value) {
            if (!dead) {
                getPaint()!!.setColor(Color.YELLOW)
            }
            getPaint()!!.setStyle(Paint.Style.FILL)
            g.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + (getWidth() * widthOfValue) / width).toFloat(),
                (y + getHeight()).toFloat(),
                getPaint()!!
            )
            getPaint()!!.setColor(color)
            g.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + (getWidth() * widthOfValueMin) / width).toFloat(),
                (y + getHeight()).toFloat(),
                getPaint()!!
            )
        } else {
            getPaint()!!.setStyle(Paint.Style.FILL)
            getPaint()!!.setColor(Color.YELLOW)
            g.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + (getWidth() * widthOfValueMin) / width).toFloat(),
                (y + getHeight()).toFloat(),
                getPaint()!!
            )
            getPaint()!!.setColor(color)
            g.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + (getWidth() * widthOfValue) / width).toFloat(),
                (y + getHeight()).toFloat(),
                getPaint()!!
            )
        }
        getPaint()!!.setColor(Color.WHITE)
    }

    /**
     * @param v1 value
     * @param v2 minValue
     // */
    fun updateTo(v1: Int, v2: Int) {
        this.value = v1
        this.setUpdate(v2)
    }

    /**
     * ?? bug?
     * @param val
     // */
    fun setUpdate(`val`: Int) {
        valueMin = NumberUtils.mid(0, `val`, valueMax)
        widthOfValue = (getWidth() * value).toFloat() / valueMax
        widthOfValueMin = (getWidth() * valueMin).toFloat() / valueMax
    }

    /**
     * set is dead.
     * @param d
     // */
    fun setDead(d: Boolean) {
        this.dead = d
    }

    /**
     * to deal with status bar.
     * If value > minValue, then auto decrease value and return true.
     * If value < minValue, then auto increase value and return true.
     * If value == minValue, then return false.
     * @return boolean changed value or not.
     // */
    fun state(): Boolean {
        if (widthOfValue == widthOfValueMin) return false
        if (widthOfValue > widthOfValueMin) {
            widthOfValue--
            value = NumberUtils.mid(
                valueMin, (widthOfValue.toInt() * valueMax) / getWidth(),
                value
            )
        } else {
            widthOfValue++
            value = NumberUtils.mid(
                value, (widthOfValue.toInt() * valueMax) / getWidth(),
                valueMin
            )
        }
        return true
    }

    /**
     * draw UI.
     * @param canvas
     // */
    private fun createUI(canvas: Canvas) {
        if (visible) {
            if (this.isShowHP) {
                hpString = "" + value
                val paint = getPaint()!!
                paint.setColor(Color.WHITE)
                val rect = Rect()
                paint.getTextBounds(hpString, 0, hpString!!.length, rect)
                val w = rect.width()
                val h = rect.height()
                canvas.drawText(
                    "" + value, (this.xInScene + w / 2 - w / 2) + 2, ((this.yInScene
                            + h / 2 + h / 2)), paint
                )
            }
            drawBar(
                canvas,
                widthOfValueMin.toInt(),
                widthOfValue.toInt(),
                getWidth(),
                this.xInScene.toInt(),
                this.yInScene.toInt()
            )
        }
    }

    val xInScene: Float
        /**
         * get position X in scene.
         * @return position x.
         // */
        get() {
            if (isComposite()) return getLocationInScene()!!.x
            else return super.getX()
        }

    val yInScene: Float
        /**
         * get position Y in scene.
         * @return position y.
         // */
        get() {
            if (isComposite()) return getLocationInScene()!!.y
            else return super.getY()
        }

    override fun isVisible(): Boolean {
        return visible
    }

    override fun setVisible(visible: Boolean) {
        this.visible = visible
    }

    //	public void update(long elapsedTime) {
    //		if (visible && hit) {
    //			state();
    //		}
    //	}
    var maxValue: Int
        /**
         * get Max value.
         * @return int.
         // */
        get() = valueMax
        /**
         * set Max Value.
         * @param valueMax
         * set the max value of status bar.
         // */
        set(valueMax) {
            this.valueMax = valueMax
            this.widthOfValue = ((getWidth() * value) / valueMax).toFloat()
            this.widthOfValueMin = ((getWidth() * valueMin) / valueMax).toFloat()
            this.state()
        }

    var minValue: Int
        /**
         * get min value.
         * @return int.
         // */
        get() = valueMin
        /**
         * set min value.
         * @param valueMin
         * the min value of status bar.
         // */
        set(valueMin) {
            this.valueMin = valueMin
            this.widthOfValue = ((getWidth() * value) / valueMax).toFloat()
            this.widthOfValueMin = ((getWidth() * valueMin) / valueMax).toFloat()
            this.state() //? maybe a bug.
        }

    //	public float getAlpha() {
    //		return 0;
    //	}
    public override fun getBitmap(): Bitmap? {
        return null
    }

    fun dispose() {
    }

    public override fun drawSelf(canvas: Canvas?, paint: Paint?) {
        canvas ?: return
        // TODO Auto-generated method stub
        super.doDrawself(canvas, paint)

        if (visible && this.isHit) {
            state()
        }

        createUI(canvas)

        super.doDrawChildren(canvas, paint)
    }

    public override fun onTouched(event: MotionEvent?) {
        // TODO Auto-generated method stub
    }

    companion object {
        /**
         * 
         // */
        private const val serialVersionUID = 1L
    }
}

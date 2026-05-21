package com.example.try_gameengine.avg

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class GraphicsObject(canvas: Canvas?, paint: Paint?) {
    var font: FontObject? = null
    private var color: LColor? = null
    private var isClose = false
    private var canvas: Canvas?

    private var paint: Paint? = null

    init {
        // TODO Auto-generated constructor stub
        if (paint == null) {
            this.paint = Paint()
        } else {
            this.paint = paint
        }
        this.canvas = canvas
        this.font = FontObject.Companion.defaultFont
    }

    fun getColor(): LColor {
        return color!!
    }

    fun setColor(color: LColor) {
        this.color = color
    }

    fun drawString(message: String, x: Int, y: Int) {
        if (isClose) {
            return
        }
        val flag = paint!!.getFlags()
        val colorTmp = paint!!.getColor()
        paint!!.setFlags(Paint.ANTI_ALIAS_FLAG)
        paint!!.setColor(color!!.getARGB())
        canvas!!.drawText(message, x.toFloat(), y.toFloat(), paint!!)
        paint!!.setFlags(flag)
        paint!!.setColor(colorTmp)
    }

    fun drawImage(img: Bitmap?, x: Int, y: Int) {
        if (img != null) {
            drawBitmap(img, x, y)
        }
    }

    fun drawBitmap(bit: Bitmap?, x: Int, y: Int) {
        if (isClose) {
            return
        }
        if (bit == null) {
            return
        }
        canvas!!.drawBitmap(bit, x.toFloat(), y.toFloat(), paint)
    }

    fun setAlphaValue(alpha: Int) {
        if (isClose) {
            return
        }
        paint!!.setAlpha(alpha)
    }

    var alpha: Float
        get() {
            if (isClose) {
                return 0f
            }
            return (paint!!.getAlpha() / 255).toFloat()
        }
        set(alpha) {
            setAlphaValue((255 * alpha).toInt())
        }

    fun dispose() {
        isClose = true
        font = null
        paint = null
        canvas = null
        //		if (mirrorImage != null) {
//			mirrorImage.clear();
//			mirrorImage = null;
//		}
    }
}

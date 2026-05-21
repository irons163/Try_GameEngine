package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

open class Layer : ALayer {
    constructor(w: Int, h: Int, autoAdd: Boolean) : super(w, h, autoAdd)

    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean, level: Int) : super(
        bitmap,
        w,
        h,
        autoAdd,
        level
    )

    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean) : super(bitmap, w, h, autoAdd)

    constructor(bitmap: Bitmap?, x: Float, y: Float, autoAdd: Boolean) : super(
        bitmap,
        x,
        y,
        autoAdd
    )

    constructor(x: Float, y: Float, autoAdd: Boolean) : super(x, y, autoAdd)

    constructor(autoAdd: Boolean) : super(autoAdd)

    constructor() : super()

    override fun drawSelf(canvas: Canvas?, paint: Paint?) {
        canvas ?: return
        // TODO Auto-generated method stub
//		if(isHidden())
        if (super.checkSelfToAncestorIsHiddenOrNot()) return

        doDrawself(canvas, paint)

        doDrawChildren(canvas, paint)
    }

    protected open fun doDrawself(canvas: Canvas, paint: Paint?) {
        var canvas = canvas
        canvas.save()
        canvas = getClipedCanvas(canvas, paint) ?: return
        doDrawSelfWithClipedCanvas(canvas, paint)
        canvas.restore()
    }

    protected open fun doDrawSelfWithClipedCanvas(canvas: Canvas, paint: Paint?) {
        var paint = paint
        if (getBackgroundColor() != ALayer.NONE_COLOR || getBitmap() != null) {
            do {
                val originalPaint: Paint? = paint


                //use input paint first
                var oldColor = 0
                var oldStyle: Paint.Style? = null
                var oldAlpha = 255
                var isDrawBackgroundColor = false
                if (originalPaint == null && getPaint() != null) {
                    paint = getPaint()
                    //				paint.setAntiAlias(true);
                    if (getBackgroundColor() != ALayer.NONE_COLOR) {
                        isDrawBackgroundColor = true
                        val layerPaint = getPaint()!!
                        oldColor = layerPaint.color
                        oldStyle = layerPaint.style
                        oldAlpha = layerPaint.alpha
                        layerPaint.color = getBackgroundColor()
                        layerPaint.alpha = (getAlpha() * oldAlpha / 255.0f).toInt()
                        layerPaint.style = Paint.Style.FILL
                        canvas.drawRect(getFrameInScene(), layerPaint)
                    }
                } else if (originalPaint != null) {
                    canvas.drawRect(getFrameInScene(), paint!!)
                }

                drawBitmap(
                    canvas, paint, oldColor, oldStyle, oldAlpha,
                    isDrawBackgroundColor
                )


                //use input paint first
                paint = originalPaint!!
            } while (false)
        }
    }

    protected fun drawBitmap(
        canvas: Canvas, paint: Paint?, oldColor: Int,
        oldStyle: Paint.Style?, oldAlpha: Int, isDrawBackgroundColor: Boolean
    ) {
        calcilation()

        if (isDrawBackgroundColor) {
            val layerPaint = getPaint()!!
            layerPaint.color = oldColor
            layerPaint.style = oldStyle
            layerPaint.alpha = oldAlpha
        }
        if (getBitmap() != null) canvas.drawBitmap(getBitmap()!!, getSrc()!!, getDst()!!, paint)
    }

    private fun calcilation() {
        if (isComposite()) {
            getSrc()!!.left = 0
            getSrc()!!.top = 0
            if (getBitmap() != null && isBitmapSacleToFitSize()) {
                getSrc()!!.right = getBitmap()!!.getWidth()
                getSrc()!!.bottom = getBitmap()!!.getHeight()
            } else {
                getSrc()!!.right = getWidth()
                getSrc()!!.bottom = getHeight()
            }

            if (getParent() != null) {
                val locationInScene = getParent()!!.locationInSceneByCompositeLocation(
                    (getCenterX() - getWidth() / 2),
                    (getCenterY() - getHeight() / 2)
                )
                getDst()!!.left = locationInScene!!.x
                getDst()!!.top = locationInScene!!.y
                getDst()!!.right = (getDst()!!.left + getWidth())
                getDst()!!.bottom = (getDst()!!.top + getHeight())
            } else {
                getDst()!!.left = (getCenterX() - getWidth() / 2)
                getDst()!!.top = (getCenterY() - getHeight() / 2)
                getDst()!!.right = (getDst()!!.left + getWidth())
                getDst()!!.bottom = (getDst()!!.top + getHeight())
            }
        } else {
            getSrc()!!.left = 0
            getSrc()!!.top = 0
            getSrc()!!.right = getWidth()
            getSrc()!!.bottom = getHeight()
            getDst()!!.left = (getCenterX() - getWidth() / 2)
            getDst()!!.top = (getCenterY() - getHeight() / 2)
            getDst()!!.right = (getDst()!!.left + getWidth())
            getDst()!!.bottom = (getDst()!!.top + getHeight())
        }
    }

    protected open fun doDrawChildren(canvas: Canvas?, paint: Paint?) {
        for (layer in getLayers()!!) {
            if (layer.isComposite() && !layer.isAutoAdd())  //if the layer is auto add, not draw.
                layer.drawSelf(canvas, paint)
        }
    }

    public override fun onTouched(event: MotionEvent?) {
        // TODO Auto-generated method stub
    }
}

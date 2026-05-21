package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import com.example.try_gameengine.avg.GraphicsUtils

/**
 * `LightImage` is a class that provide a easy and light image.
 * The light mean it can not instance while need.
 * @author irons
 // */
class LightImage {
    private var bitmap: Bitmap? = null
    private var clipInfo: ClipInfo? = null

    inner class ClipInfo(clipStartX: Int, clipStartY: Int, width: Int, height: Int) {
        var clipStartX: Int
        var clipStartY: Int
        var width: Int
            private set
        var height: Int
            private set

        init {
            this.clipStartX = clipStartX
            this.clipStartY = clipStartY
            this.width = width
            this.height = height
        }

        fun setOffsetX(offsetX: Int) {
            this.width = offsetX
        }

        fun setOffsetY(offsetY: Int) {
            this.height = offsetY
        }
    }

    constructor()

    constructor(bitmap: Bitmap) {
        this.bitmap = bitmap
        setClipInfo(ClipInfo(0, 0, bitmap.getWidth(), bitmap.getHeight()))
    }

    constructor(resPath: String?) {
        this.bitmap = GraphicsUtils.loadImage(resPath)
        setClipInfo(ClipInfo(0, 0, bitmap!!.getWidth(), bitmap!!.getHeight()))
    }

    constructor(lightImage: LightImage) {
        this.bitmap = lightImage.getBitmap()
        this.clipInfo = lightImage.clipIfno
    }

    fun setClipInfo(clipInfo: ClipInfo) {
        this.clipInfo = clipInfo
    }

    val clipIfno: ClipInfo
        get() = this.clipInfo!!

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    fun getBitmap(): Bitmap {
        return this.bitmap!!
    }

    val width: Int
        get() = clipInfo!!.width

    val height: Int
        get() = clipInfo!!.height

    fun drawSelf(canvas: Canvas) {
        canvas.drawBitmap(
            bitmap!!, Rect(
                clipInfo!!.clipStartX,
                clipInfo!!.clipStartY,
                clipInfo!!.width,
                clipInfo!!.height
            ), RectF(), null
        )
    }
}

package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import com.example.try_gameengine.framework.Sprite

/**
 * `DetectAreaSpriteRect` is an class for an `SpriteRect` to detect collision. It extends [DetectArea].
 * @author irons
 // */
class DetectAreaSpriteRect : DetectAreaRect {
    private var sprite: Sprite? = null

    /**
     * An default `SpriteRectListener` to listen frame of Sprite and center of Sprite.
     // */
    private var spriteRectListener: SpriteRectListener? = object : SpriteRectListener {
        override fun calculateSpriteRect(): RectF? {
            // TODO Auto-generated method stub
            if (sprite == null) return null
            val rectF: RectF?
            val locationInScene = sprite!!.getLocationInScene()
            if (locationInScene != null) rectF = RectF(
                locationInScene.x,
                locationInScene.y,
                locationInScene.x + sprite!!.getWidth(),
                locationInScene.y + sprite!!.getHeight()
            )
            else rectF = sprite!!.getFrame()
            return rectF
        }

        override fun calculateSpriteCenter(): PointF? {
            // TODO Auto-generated method stub;
            if (sprite == null) return null
            val pointF: PointF?
            val locationInScene = sprite!!.getLocationInScene()
            if (locationInScene != null) pointF = PointF(
                locationInScene.x + sprite!!.getWidth() / 2,
                locationInScene.y + sprite!!.getHeight() / 2
            )
            else pointF = PointF(sprite!!.getFrame().centerX(), sprite!!.getFrame().centerY())
            return pointF
        }
    }

    /**
     * `SpriteRectListener` is use to calculate the rect(frame) of sprite and center of sprite.
     * @author irons
     // */
    interface SpriteRectListener {
        /**
         * calculate the rect of sprite.
         * @return RectF
         // */
        fun calculateSpriteRect(): RectF?

        /**
         * calculate the center of sprite.
         * @return PointF
         // */
        fun calculateSpriteCenter(): PointF?
    }

    /**
     * constructor
     * @param sprite
     // */
    constructor(sprite: Sprite?) : super(RectF()) {
        this.sprite = sprite
        setRectF(getRectF())
    }

    /**
     * constructor
     * @param rectF
     * @param spriteRectListener
     // */
    constructor(rectF: RectF?, spriteRectListener: SpriteRectListener?) : super(rectF ?: RectF()) {
        this.spriteRectListener = spriteRectListener
        setRectF(getRectF())
    }

    public override fun detect(request: IDetectAreaRequest): Boolean {
        val isDetected: Boolean =
            DetectArea.Companion.detectConditionWithTwoArea(this, request.getDetectArea())
        if (isDetected) {
            Log.e("Sprite RectF", "detected!")
            val listener = this.spriteDetectAreaListener
            if (listener != null) listener.didDetected(
                this,
                request
            )
        } else {
            val next = successor
            if (next != null) {
                val listener = this.spriteDetectAreaListener
                if (listener == null || !listener.stopDoSuccessorDetected(
                        this,
                        request,
                        isDetected
                    )
                ) return next.detect(request)
            }
        }
        return isDetected
    }

    override fun getRectF(): RectF {
        return super.getRectF()
    }

    override fun setRectF(rectF: RectF?) {
//		this.rectF = rectF;
        if (spriteRectListener != null) super.setRectF(spriteRectListener!!.calculateSpriteRect())
        else super.setRectF(rectF)
        if (getRectF() != null && spriteRectListener != null) this.center =
            spriteRectListener!!.calculateSpriteCenter()
    }

    public override fun setCenter(center: PointF) {
        // TODO Auto-generated method stub
//		rectF.offset(center.x - rectF.centerX(), center.y - rectF.centerY()); //this use rectF.center to calculate.
//		this.center = center;
        setRectF(getRectF())
        if (spriteRectListener != null) this.center = spriteRectListener!!.calculateSpriteCenter()
    }
}

package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF
import android.util.Log

/**
 * `DetectAreaRect` is an class for an rect to detect collision. It extends [DetectArea].
 * @author irons
 // */
open class DetectAreaRect(rectF: RectF?) : DetectArea(DetectAreaType.RECT) {
    private var rectF: RectF? = null

    /**
     * Constructor of DetectAreaRect.
     * @param rectF
     // */
    init {
        setRectF(rectF ?: RectF())
    }

    override fun detect(request: IDetectAreaRequest): Boolean {
        val isDetected: Boolean =
            DetectArea.Companion.detectConditionWithTwoArea(this, request.getDetectArea())
        if (isDetected) {
            Log.e("RectF", "detected!")
            if (this.spriteDetectAreaListener != null) this.spriteDetectAreaListener!!.didDetected(
                this,
                request
            )
        } else {
            if (successor != null) {
                if (this.spriteDetectAreaListener == null || !this.spriteDetectAreaListener!!.stopDoSuccessorDetected(
                        this,
                        request,
                        isDetected
                    )
                ) return this.successor!!.detect(request)
            }
        }
        return isDetected
    }

    /**
     * get rect of this DetectAreaRect.
     * @return RectF.
     // */
    open fun getRectF(): RectF {
        return rectF!!
    }

    /**
     * set RectF to this DetectAreaRect.
     * @param rectF
     // */
    open fun setRectF(rectF: RectF?) {
        this.rectF = rectF
        if (rectF != null) this.center = PointF(rectF.centerX(), rectF.centerY())
    }

    override fun setCenter(center: PointF) {
//		rectF.offset(center.x - this.center.x, center.y - this.center.y); //this use point center to calculate, but sometimes the rectF is updated, center point not.
        rectF!!.offset(
            center.x - rectF!!.centerX(),
            center.y - rectF!!.centerY()
        ) //this use rectF.center to calculate.
        this.center = center
    }
}

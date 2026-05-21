package com.example.try_gameengine.utils

import android.graphics.PointF
import android.util.Log

/**
 * `DetectAreaPoint` is an class for an point to detect collision. It extends [DetectArea].
 * @author irons
 // */
class DetectAreaPoint(point: PointF?) : DetectArea(DetectAreaType.POINT) {
    /**
     * Constructor of DetectAreaPoint.
     * @param point
     // */
    init {
        this.center = point
    }

    override fun detect(request: IDetectAreaRequest): Boolean {
        // TODO Auto-generated method stub
        val isDetected: Boolean =
            DetectArea.Companion.detectConditionWithTwoArea(this, request.getDetectArea())
        if (isDetected) {
            Log.e("Point", "detected!")
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

    val point: PointF?
        /**
         * get point of this DetectAreaPoint.
         * @return PointF.
         // */
        get() = center
}

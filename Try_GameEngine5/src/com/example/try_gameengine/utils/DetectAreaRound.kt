package com.example.try_gameengine.utils

import android.graphics.PointF
import android.util.Log

/**
 * `DetectAreaRound` is an class for an round to detect collision. It extends [DetectArea].
 * @author irons
 // */
class DetectAreaRound(center: PointF?, radius: Float) : DetectArea(DetectAreaType.ROUND) {
    /**
     * get radius of this DetectAreaRect.
     * @return radius.
     // */
    /**
     * set radius to this DetectAreaRect.
     * @param radius
     // */
    var radius: Float

    /**
     * Constructor of DetectAreaRound.
     * @param center
     * center of the round.
     * @param radius
     * radius of the round.
     // */
    init {
        this.center = center
        this.radius = radius
    }

    override fun detect(request: IDetectAreaRequest): Boolean {
        // TODO Auto-generated method stub
        val isDetected: Boolean =
            DetectArea.Companion.detectConditionWithTwoArea(this, request.getDetectArea())
        if (isDetected) {
            Log.e("Round", "detected!")
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
}

package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF

/**
 * This Helper help to create DetectArea.
 * 
 * @author irons
 // */
object SpriteDetectAreaHelper {
    /**
     * create a DetectAreaPoint by point.
     * @param point
     * for detected.
     * @return DetectArea
     // */
    fun createDetectAreaPoint(point: PointF?): DetectArea {
        return DetectAreaPoint(point)
    }

    /**
     * create a DetectAreaRoundt by center and radius.
     * @param center
     * the center of round for detected.
     * @param radius
     * the radius of round for detected.
     * @return DetectArea
     // */
    fun createDetectAreaRound(center: PointF?, radius: Float): DetectArea {
        return DetectAreaRound(center, radius)
    }

    /**
     * create a DetectAreaRect by rectF.
     * @param rect
     * for detected.
     * @return DetectArea
     // */
    fun createDetectAreaRect(rect: RectF?): DetectArea {
        return DetectAreaRect(rect)
    }
}

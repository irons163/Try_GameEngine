package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * `DetectArea` is an abstract class for an area to detect collision.
 * @author irons
 // */
abstract class DetectArea(detectAreaType: DetectAreaType?) {
    /**
     * get `DetectAreaType`.
     * @return `DetectAreaType`.
     // */
    /**
     * set `DetectAreaType`.
     * @param detectAreaType
     // */
    var detectAreaType: DetectAreaType?
    /**
     * successor for next detect area.
     * @return a `DetectArea`.
     // */
    /**
     * set successor which can handle the event when this `DetectArea` can not handle.
     * @param successor is a next `DetectArea` after this `DetectArea` for detected.
     // */
    var successor: DetectArea? = null
    @JvmField
    protected var center: PointF? = null
    /**
     * @return get spriteDetectAreaListener.
     // */
    /**
     * set spriteDetectAreaListener.
     * @param spriteDetectAreaListener
     // */
    var spriteDetectAreaListener: ISpriteDetectAreaListener? = null
    /**
     * get the tag from this `DetectArea`.
     * @return a string for tag.
     // */
    /**
     * set a tag to this `DetectArea`.
     * @param tag
     // */
    var tag: String? = null
    /**
     * get the object tag from this `DetectArea`.
     * @return
     // */
    /**
     * set a object tag to this `DetectArea`. It can use as a tag, also as a attached object to deal with what you want.
     * @param objectTag
     // */
    var objectTag: Any? = null

    /**
     * Constructs a new `DetectArea` instance with a detectAreaType.
     * @param detectAreaType
     * decision what the `DetectArea` is.
     // */
    init {
        this.detectAreaType = detectAreaType
    }

    /**
     * @param request
     * @return `true` is mean detected that the request and this `DetectArea` are collision. `false` otherwise.
     // */
    open fun detect(request: IDetectAreaRequest): Boolean {
        if (successor != null) {
            return this.successor!!.detect(request)
        } else {
            return false
        }
    }

    /**
     * set center point of this `DetectArea`.
     * @param center
     // */
    open fun setCenter(center: PointF) {
        this.center = center
    }

    /**
     * @return
     // */
    fun getCenter(): PointF {
        return center!!
    }

    companion object {
        /**
         * Detect the two `DetectArea` are collision or not.
         * @param detectArea
         * @param detectArea2
         * @return `true` is mean detected that the request and this `DetectArea` are collision. `false` otherwise.
         // */
        fun detectConditionWithTwoArea(
            detectArea: DetectArea,
            detectArea2: DetectArea
        ): Boolean {
            val detectAreaLower: DetectArea?
            val detectAreaUpper: DetectArea?
            if (detectArea.detectAreaType!!.ordinal > detectArea2.detectAreaType!!.ordinal) {
                detectAreaUpper = detectArea
                detectAreaLower = detectArea2
            } else {
                detectAreaUpper = detectArea2
                detectAreaLower = detectArea
            }

            var isDetected = false
            when (detectAreaUpper.detectAreaType) {
                DetectAreaType.POINT -> isDetected = pointToPoint(
                    detectAreaUpper as DetectAreaPoint,
                    detectAreaLower as DetectAreaPoint
                )

                DetectAreaType.ROUND -> when (detectAreaLower.detectAreaType) {
                    DetectAreaType.POINT -> isDetected = roundToPoint(
                        detectAreaUpper as DetectAreaRound,
                        detectAreaLower as DetectAreaPoint
                    )

                    DetectAreaType.ROUND -> isDetected = roundToRound(
                        detectAreaUpper as DetectAreaRound,
                        detectAreaLower as DetectAreaRound
                    )

                    else -> {}
                }

                DetectAreaType.RECT -> when (detectAreaLower.detectAreaType) {
                    DetectAreaType.POINT -> isDetected = rectoPoint(
                        detectAreaUpper as DetectAreaRect,
                        detectAreaLower as DetectAreaPoint
                    )

                    DetectAreaType.ROUND -> isDetected = rectToRound(
                        detectAreaUpper as DetectAreaRect,
                        detectAreaLower as DetectAreaRound
                    )

                    DetectAreaType.RECT -> isDetected = rectToRect(
                        detectAreaUpper as DetectAreaRect,
                        detectAreaLower as DetectAreaRect
                    )

                    else -> {}
                }

                else -> {}
            }

            return isDetected
        }

        // /////// 0-0
        private fun pointToPoint(
            detectAreaPoint: DetectAreaPoint,
            detectAreaPoint2: DetectAreaPoint
        ): Boolean {
            val pointF = detectAreaPoint.getPoint()!!
            val pointF2 = detectAreaPoint2.getPoint()!!

            if (pointF.x == pointF2.x && pointF.y == pointF2.y) {
                return true
            }
            return false
        }

        // ////// 1-0,1-1
        private fun roundToPoint(
            detectAreaRound: DetectAreaRound,
            detectAreaPoint: DetectAreaPoint
        ): Boolean {
            val center = detectAreaRound.getCenter()
            val rdius = detectAreaRound.getRadius()
            val point = detectAreaPoint.getPoint()!!

            if (point.x <= center.x + rdius && point.x >= center.x - rdius && point.y <= center.y + rdius && point.y >= center.y - rdius) {
                return true
            }

            return false
        }

        private fun roundToRound(
            detectAreaRound: DetectAreaRound,
            detectAreaRound2: DetectAreaRound
        ): Boolean {
            val center = detectAreaRound.getCenter()
            val rdius = detectAreaRound.getRadius()
            val center2 = detectAreaRound2.getCenter()
            val rdius2 = detectAreaRound2.getRadius()

            if (sqrt(
                    (center.x - center2.x).toDouble().pow(2.0) + (center.y - center2.y).toDouble()
                        .pow(2.0)
                ) <= rdius + rdius2
            ) {
                return true
            }

            return false
        }

        // ///// 2-0,2-1,2-2
        private fun rectoPoint(
            detectAreaRect: DetectAreaRect,
            detectAreaPoint: DetectAreaPoint
        ): Boolean {
            val rectF = detectAreaRect.getRectF()
            val point = detectAreaPoint.getPoint()!!

            if (rectF.contains(point.x, point.y)) {
                return true
            }

            return false
        }

        private fun rectToRound(
            detectAreaRect: DetectAreaRect,
            detectAreaRound: DetectAreaRound
        ): Boolean {
            val rectF = detectAreaRect.getRectF()
            val point = detectAreaRound.getCenter()
            val rdius = detectAreaRound.getRadius()

            val circleDistanceX = abs(point.x - rectF.centerX())
            val circleDistanceY = abs(point.y - rectF.centerY())

            if (circleDistanceX > (rectF.width() / 2 + rdius)) {
                return false
            }
            if (circleDistanceY > (rectF.height() / 2 + rdius)) {
                return false
            }

            if (circleDistanceX <= (rectF.width() / 2)) {
                return true
            }
            if (circleDistanceY <= (rectF.height() / 2)) {
                return true
            }

            val cornerDistance_sq = sqrt(
                (circleDistanceX - rectF.width()
                        / 2).toDouble()
            ) + sqrt((circleDistanceY - rectF.height() / 2).toDouble())

            return cornerDistance_sq <= sqrt(rdius.toDouble())
        }

        private fun rectToRect(
            detectAreaRect: DetectAreaRect,
            detectAreaRect2: DetectAreaRect
        ): Boolean {
            val rectF = detectAreaRect.getRectF()
            val rectF2 = detectAreaRect2.getRectF()

            if (RectF.intersects(rectF, rectF2)) {
                return true
            }

            return false
        }
    }
}

package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF

/**
 * `SpriteDetectAreaHandler` is use for deal with detect area controller.
 * @author irons
 // */
class SpriteDetectAreaHandler : ISpriteDetectAreaHandler {
    private var spriteDetectAreaBehavior: ISpriteDetectAreaBehavior
    private val detectAreas: MutableList<DetectArea> = ArrayList<DetectArea>()

    /**
     * constructor of SpriteDetectAreaHandler
     // */
    init {
        this.spriteDetectAreaBehavior = SpriteDetectAreaBehavior()
    }

    override fun setSpriteDetectAreaBehavior(spriteDetectAreaBehavior: ISpriteDetectAreaBehavior?) {
        this.spriteDetectAreaBehavior = spriteDetectAreaBehavior ?: SpriteDetectAreaBehavior()
    }

    override fun getSpriteDetectAreaBehavior(): ISpriteDetectAreaBehavior {
        return spriteDetectAreaBehavior
    }

    /**
     * add point to make `DetectAreaByPoint` for successor. It will be run when previous DetectAreas not catch the detect event.
     * @param point
     // */
    fun addSuccessorDetectAreaByPoint(point: PointF?) {
        val detectAreaPoint = DetectAreaPoint(point)
        detectAreas.add(detectAreaPoint)
    }

    /**
     * add point to make `DetectAreaByPoint` for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event.
     * @param point
     * 
     * @param spriteDetectAreaListener
     // */
    fun addSuccessorDetectAreaByPoint(
        point: PointF?,
        spriteDetectAreaListener: ISpriteDetectAreaListener?
    ) {
        val detectAreaPoint = DetectAreaPoint(point)
        detectAreaPoint.setSpriteDetectAreaListener(spriteDetectAreaListener)
        detectAreas.add(detectAreaPoint)
    }

    /**
     * add center and radius to make a `DetectAreaByRound` for successor. It will be run when previous DetectAreas not catch the detect event.
     * @param center
     * the center of round for detected.
     * @param radius
     * the radius of round for detected.
     // */
    fun addSuccessorDetectAreaByRound(center: PointF?, radius: Float) {
        val detectAreaRound = DetectAreaRound(center, radius)
        detectAreas.add(detectAreaRound)
    }

    /**
     * add center and radius to make a `DetectAreaByRound` for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event.
     * @param center
     * the center of round for detected.
     * @param radius
     * the radius of round for detected.
     * @param spriteDetectAreaListener
     * for listen the detect status.
     // */
    fun addSuccessorDetectAreaByRound(
        center: PointF?,
        radius: Float,
        spriteDetectAreaListener: ISpriteDetectAreaListener?
    ) {
        val detectAreaRound = DetectAreaRound(center, radius)
        detectAreaRound.setSpriteDetectAreaListener(spriteDetectAreaListener)
        detectAreas.add(detectAreaRound)
    }

    /**
     * add point to make `DetectAreaByRect` for successor. It will be run when previous DetectAreas not catch the detect event.
     * @param rect
     * for detected.
     // */
    fun addSuccessorDetectAreaByRect(rect: RectF?) {
        val detectAreaRect = DetectAreaRect(rect)
        detectAreas.add(detectAreaRect)
    }

    /**
     * add rect to make a `DetectAreaByRect` for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event.
     * @param rect
     * for detected.
     * @param spriteDetectAreaListener
     * for listen the detect status.
     // */
    fun addSuccessorDetectAreaByRect(
        rect: RectF?,
        spriteDetectAreaListener: ISpriteDetectAreaListener?
    ) {
        val detectAreaRect = DetectAreaRect(rect)
        detectAreaRect.setSpriteDetectAreaListener(spriteDetectAreaListener)
        detectAreas.add(detectAreaRect)
    }

    /**
     * add `DetectArea` for successor. It will be run when previous DetectAreas not catch the detect event.
     * @param detectArea
     * for detected.
     // */
    fun addSuccessorDetectArea(detectArea: DetectArea?) {
        detectAreas.add(detectArea!!)
    }

    /**
     * add `DetectArea` for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event.
     * @param detectArea
     * for detected.
     * @param spriteDetectAreaListener
     * for listen the detect status.
     // */
    fun addSuccessorDetectArea(
        detectArea: DetectArea,
        spriteDetectAreaListener: ISpriteDetectAreaListener?
    ) {
        detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener)
        detectAreas.add(detectArea)
    }

    /**
     * @param spriteDetectAreaListener
     // */
    fun addSpriteDetectAreaListenerToLastSuccessor(spriteDetectAreaListener: ISpriteDetectAreaListener?) {
        if (detectAreas.size == 0) return
        val detectArea = detectAreas.get(detectAreas.size - 1)
        detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener)
    }

    /**
     * replace oldDetectArea to newDetectArea.
     * @param oldDetectArea
     * @param newDetectArea
     * @return `true` is the replace success.
     // */
    fun replaceDetectArea(oldDetectArea: DetectArea, newDetectArea: DetectArea): Boolean {
        if (detectAreas.size == 0) return false

        val replaceIndex = detectAreas.indexOf(oldDetectArea)

        if (replaceIndex >= 0) {
            val successor = oldDetectArea.getSuccessor()
            newDetectArea.setSuccessor(successor)
            oldDetectArea.setSuccessor(null)

            detectAreas.set(replaceIndex, newDetectArea)
            detectAreas.remove(oldDetectArea)
            return true
        } else {
            return false
        }
    }

    /**
     * apply the successors after set these successors. If not call this method, the new setting not work.
     // */
    fun apply() {
        if (detectAreas.size == 0) return

        var detectAreaHandler = detectAreas.get(0)
        val firstDetectAreaOfDetectAreaChain: DetectArea? = detectAreaHandler
        for (i in 1..<detectAreas.size) {
            val successor = detectAreas.get(i)
            detectAreaHandler.setSuccessor(successor)
            detectAreaHandler = successor
        }
        spriteDetectAreaBehavior.setSpriteDetectArea(firstDetectAreaOfDetectAreaChain)
    }

    /**
     * update
     * @param center
     // */
    fun updateSpriteDetectAreaCenter(center: PointF?) {
        spriteDetectAreaBehavior.updateSpriteDetectAreaCenter(center)
    }

    /**
     * detect
     * @param point
     * @return
     // */
    fun detectByPoint(point: PointF?): Boolean {
        return spriteDetectAreaBehavior.detect(DetectAreaPoint(point))
    }

    /**
     * detect by round area.
     * @param center
     * round center of the detected area .
     * @param radius
     * the round radius.
     * @return `true` if detected, `false` otherwise.
     // */
    fun detectByRound(center: PointF?, radius: Float): Boolean {
        return spriteDetectAreaBehavior.detect(DetectAreaRound(center, radius))
    }

    /**
     * detect by rect area.
     * @param rect
     * rect of the detected area .
     * @return `true` if detected, `false` otherwise.
     // */
    fun detectByRect(rect: RectF?): Boolean {
        return spriteDetectAreaBehavior.detect(DetectAreaRect(rect))
    }

    /**
     * detect by [DetectArea].
     * @param request
     * the detected area for detect.
     * @return `true` if detected, `false` otherwise.
     // */
    fun detectByDetectArea(request: DetectArea?): Boolean {
        return spriteDetectAreaBehavior.detect(request)
    }

    /**
     * detect by [IDetectAreaRequest].
     * @param request
     * the IDetectAreaRequest for detect.
     * @return `true` if detected, `false` otherwise.
     // */
    fun detectByDetectAreaRequest(request: IDetectAreaRequest?): Boolean {
        return spriteDetectAreaBehavior.detect(request)
    }

    /**
     * set a tag to all `DetectArea`. This is useful for deal with the group by the same tag.
     * @param tag
     // */
    fun setTag(tag: String?) {
        for (i in detectAreas.indices) {
            val detectArea = detectAreas.get(i)
            detectArea.setTag(tag)
        }
    }

    /**
     * set an object as tag to all `DetectArea`. This is useful for deal with the group by the same object.
     * @param objectTag
     // */
    fun setObjectTag(objectTag: Any?) {
        for (i in detectAreas.indices) {
            val detectArea = detectAreas.get(i)
            detectArea.setObjectTag(objectTag)
        }
    }

    /**
     * reset spriteDetectAreaBehavior and clear the object tag in detectAreas and clear detectArea.
     // */
    fun reset() {
        spriteDetectAreaBehavior.setSpriteDetectArea(null)
        for (detectArea in detectAreas) {
            detectArea.setObjectTag(null)
        }
        detectAreas.clear()
    }
}

package com.example.try_gameengine.utils

import android.graphics.PointF

/**
 * `SpriteDetectAreaBehavior` has a set of DetectArea methods. It can be detected the chain of member DetectArea.
 * @author irons
 // */
class SpriteDetectAreaBehavior : ISpriteDetectAreaBehavior {
    private var detectArea: DetectArea? = null

    override fun setSpriteDetectArea(detectArea: DetectArea?) {
        // TODO Auto-generated method stub
        this.detectArea = detectArea
    }

    override fun getSpriteDetectArea(): DetectArea? {
        // TODO Auto-generated method stub
        return detectArea!!
    }

    override fun updateSpriteDetectAreaCenter(center: PointF?) {
        // TODO Auto-generated method stub
        var successor = detectArea
        do {
            successor!!.setCenter(center!!)
            successor = successor.getSuccessor()
        } while (successor != null)
    }

    override fun detect(otherDetectArea: DetectArea?): Boolean {
        // TODO Auto-generated method stub
        return detectArea!!.detect(DetectAreaRequest(otherDetectArea!!))
    }

    override fun detect(requestDetectArea: IDetectAreaRequest?): Boolean {
        // TODO Auto-generated method stub
        return detectArea!!.detect(requestDetectArea!!)
    }

    var spriteDetectAreaListener: ISpriteDetectAreaListener?
        /**
         * get `SpriteDetectAreaListener` for the member DetectArea.
         * @return `SpriteDetectAreaListener`
         // */
        get() = detectArea!!.getSpriteDetectAreaListener()
        /**
         * set `SpriteDetectAreaListener` for the member DetectArea.
         * @param spriteDetectAreaListener
         // */
        set(spriteDetectAreaListener) {
            detectArea!!.setSpriteDetectAreaListener(spriteDetectAreaListener)
        }
}

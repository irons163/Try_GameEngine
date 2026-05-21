package com.example.try_gameengine.Camera

/**
 * Camera is common object in game engine. It can control the whole display.
 * @author irons
 // */
abstract class ACamera {
    /**
     * rotation camera.
     * @param rotation
     // */
    abstract fun rotation(rotation: Float)

    /**
     * translate camera.
     * @param dx for x-dir translate.
     * @param dy for y-dir translate.
     // */
    abstract fun translate(dx: Float, dy: Float)

    /**
     * zoom(change xscale or yscale) cameara.
     * @param scale
     // */
    abstract fun zoom(scale: Float)

    abstract fun bindLayerXY()
    abstract fun bindLayerX()
    abstract fun bindLayerY()
    abstract fun setIsAutoStopOnBound()
}

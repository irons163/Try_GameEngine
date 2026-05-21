package com.example.try_gameengine.action

import kotlin.math.sqrt


class RotationCurveController(rotation: Float) : IRotationController {
    @JvmField
    var rotation: Float
    var origineDx: Float = 0f
    var origineDy: Float = 0f
    var firstExecute: Boolean = true
    var initspeedX: Float = 0f

    init {
        this.rotation = rotation
    }


    override fun execute(info: MovementActionInfo?, t: Float) {
        // TODO Auto-generated method stub
    }

    override fun execute(info: MovementActionInfo) {
        // TODO Auto-generated method stub
        if (firstExecute) {
            origineDx = info.getDx()
            origineDy = info.getDy()

            initspeedX = sqrt((origineDx * origineDx + origineDy * origineDy).toDouble()).toFloat()

            firstExecute = false
        }

        var dx = info.getDx()
        var dy = info.getDy()
        val mathUtil = MathUtil(dx, dy)
        mathUtil.setInitSpeed(initspeedX)
        //		mathUtil.initAngle();
        mathUtil.genAngle()
        mathUtil.genSpeedByRotate(rotation)
        //		mathUtil.genSpeed();
        dx = mathUtil.getSpeedX()
        dy = mathUtil.getSpeedY()

        info.setDx(dx)
        info.setDy(dy)
    }

    override fun reset(info: MovementActionInfo?) {
        info ?: return
        info.setDx(origineDx)
        info.setDy(origineDy)
        firstExecute = true
    }

    override fun setRotation(rotation: Float) {
        this.rotation = rotation
    }

    override fun getRotation(): Float {
        return rotation
    }

    override fun copyNewRotationController(): IRotationController {
        return RotationCurveController(rotation)
    }


    override fun getMathUtil(): MathUtil? {
        // TODO Auto-generated method stub
        return null
    }


    override fun setMathUtil(mathUtil: MathUtil?) {
        // TODO Auto-generated method stub
    }


    override fun isInverseAngel() {
        // TODO Auto-generated method stub
    }


    override fun isCyclePath() {
        // TODO Auto-generated method stub
    }


    override fun isInversePath() {
        // TODO Auto-generated method stub
    }


    override fun isWavePath() {
        // TODO Auto-generated method stub
    }


    override fun isSlopeWavePath() {
        // TODO Auto-generated method stub
    }


    override fun start(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
    }
}

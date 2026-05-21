package com.example.try_gameengine.action


class RotationOnceController(rotation: Float) : IRotationController {
    @JvmField
    var rotation: Float
    var origineDx: Float = 0f
    var origineDy: Float = 0f
    var firstExecute: Boolean = true

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

            val mathUtil = MathUtil(origineDx, origineDy)
            val totalSpeed = mathUtil.genTotalSpeed()
            mathUtil.setInitSpeed(totalSpeed)
            mathUtil.genAngle()
            mathUtil.genSpeedByRotate(rotation)

            val dx = mathUtil.getSpeedX()
            val dy = mathUtil.getSpeedY()
            info.setDx(dx)
            info.setDy(dy)
            firstExecute = false
        }
    }

    override fun reset(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        info ?: return
        info.setDx(origineDx)
        info.setDy(origineDy)
        firstExecute = true
    }

    override fun setRotation(rotation: Float) {
        // TODO Auto-generated method stub
        this.rotation = rotation
    }

    override fun getRotation(): Float {
        // TODO Auto-generated method stub
        return rotation
    }

    override fun copyNewRotationController(): IRotationController {
        // TODO Auto-generated method stub
        return RotationOnceController(rotation)
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

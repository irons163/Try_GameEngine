package com.example.try_gameengine.action


interface IRotationController {
    fun start(info: MovementActionInfo?)
    fun getRotation(): Float
    fun setRotation(rotation: Float)
    fun execute(info: MovementActionInfo)
    fun execute(info: MovementActionInfo?, t: Float)
    fun reset(info: MovementActionInfo?)
    fun isInverseAngel()
    fun isCyclePath()
    fun isInversePath()
    fun isWavePath()
    fun isSlopeWavePath()
    fun getMathUtil(): MathUtil?
    fun setMathUtil(mathUtil: MathUtil?)
    fun copyNewRotationController(): IRotationController?
}

package com.example.try_gameengine.action


interface IGravityController {
    enum class PathType {
        NORMAL,
        INVERSE_PATH,
        CYCLE_PATH,
        WAVE_PATH,
        WAVE_SLOPE_PATH,
        REFLECTION_PATH_BY_HORIZONTAL_MIRROR,
        REFLECTION_PATH_BY_VERTICAL_MIRROR
    }

    fun start(info: MovementActionInfo)
    fun execute(info: MovementActionInfo)
    fun execute(info: MovementActionInfo, t: Float)
    fun reset(info: MovementActionInfo?)
    fun setPathType(pathType: PathType?)
    fun getMathUtil(): MathUtil?
    fun setMathUtil(mathUtil: MathUtil)
    fun copyNewGravityController(): IGravityController?
}

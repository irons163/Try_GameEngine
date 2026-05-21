package com.example.try_gameengine.action

interface IMovementLimitCondition {
    fun initStartCondition(x: Float, y: Float, dx: Float, dy: Float)
    fun executeLimitCondition(x: Float, y: Float, dx: Float, dy: Float)
}

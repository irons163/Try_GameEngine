package com.example.try_gameengine.action.listener

interface IActionListener {
    fun actionStart()
    fun beforeChangeFrame(nextFrameId: Int)
    fun afterChangeFrame(periousFrameId: Int)
    fun actionCycleFinish()
    fun actionFinish()
}



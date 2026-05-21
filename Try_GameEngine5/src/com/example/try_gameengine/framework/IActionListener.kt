package com.example.try_gameengine.framework

interface IActionListener {
    fun beforeChangeFrame(nextFrameId: Int)
    fun afterChangeFrame(periousFrameId: Int)
    fun actionFinish()
}

internal class DefaultActionListener : IActionListener {
    override fun beforeChangeFrame(nextFrameId: Int) {
        // TODO Auto-generated method stub
    }

    override fun afterChangeFrame(periousFrameId: Int) {
        // TODO Auto-generated method stub
    }

    override fun actionFinish() {
        // TODO Auto-generated method stub
    }
}

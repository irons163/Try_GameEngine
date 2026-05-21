package com.example.try_gameengine.remotecontroller

import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.Config

/**
 * @author irons
 // */
class RemoteLoader {
    private val upKey: UpKey? = null
    private val downKey: DownKey? = null
    private var rightKey: RightKey? = null
    private var leftKey: LeftKey? = null
    private var remoteControl: RemoteControl? = null

    init {
        load()
    }

    private fun load() {
        remoteControl = RemoteControl()

        rightKey = RightKey(
            BitmapUtil.rightKey!!,
            Config.currentScreenWidth - BitmapUtil.rightKey!!.getWidth(),
            Config.currentScreenHeight - BitmapUtil.rightKey!!.getHeight(),
            1,
            false
        )
        val right = rightKey!!
        right.isEnableMultiTouch = true
        val upKeyCommandPressDown = RightKeyCommandPressDown(right)
        val upKeyCommandPressUp = RightKeyCommandPressUp(right)
        leftKey = LeftKey(
            BitmapUtil.leftKey!!,
            0f,
            Config.currentScreenHeight - BitmapUtil.leftKey!!.getHeight(),
            1,
            false
        )
        val left = leftKey!!
        left.isEnableMultiTouch = true
        val leftKeyCommandPressDown = LeftKeyCommandPressDown(left)
        val leftKeyCommandPressUp = LeftKeyCommandPressUp(left)

        remoteControl!!.setCommand(0, upKeyCommandPressDown, upKeyCommandPressUp)
        remoteControl!!.setCommand(1, leftKeyCommandPressDown, leftKeyCommandPressUp)
    }

    fun getUpKey(): UpKey? {
        return upKey
    }

    fun getDownKey(): DownKey? {
        return downKey
    }

    fun getLeftKey(): LeftKey? {
        return leftKey
    }

    fun getRightKey(): RightKey? {
        return rightKey
    }

    fun getRemoteControl(): RemoteControl {
        return remoteControl!!
    }
}

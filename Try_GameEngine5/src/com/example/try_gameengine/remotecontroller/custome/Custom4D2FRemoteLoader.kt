package com.example.try_gameengine.remotecontroller.custome

import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.Config

class Custom4D2FRemoteLoader {
    private var upKey: Key? = null
    private var downKey: Key? = null
    private var rightKey: Key? = null
    private var leftKey: Key? = null
    private var enterKey: Key? = null
    private var cancelKey: Key? = null
    private var remoteControl: Custom4D2FRemoteControl? = null

    init {
        // TODO Auto-generated constructor stub
        load()
    }

    private fun load() {
        remoteControl = Custom4D2FRemoteControl()

        leftKey = Key(
            BitmapUtil.leftKey!!,
            0f,
            Config.currentScreenHeight - BitmapUtil.leftKey!!.getHeight() * 2,
            1,
            false
        )
        val left = leftKey!!
        left.setEnableMultiTouch(true)
        val leftKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(left, Custom4D2FCommandType.LeftKeyDownCommand)
        val leftKeyCommandPressUp =
            Custom4D2FKeyCommandPressUp(left, Custom4D2FCommandType.LeftKeyUpCommand)
        rightKey = Key(
            BitmapUtil.rightKey!!,
            left.getX() + left.getWidth() * 2,
            Config.currentScreenHeight - BitmapUtil.rightKey!!.getHeight() * 2,
            1,
            false
        )
        val right = rightKey!!
        right.setEnableMultiTouch(true)
        val rightKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(right, Custom4D2FCommandType.RightKeyDownCommand)
        val rightKeyCommandPressUp =
            Custom4D2FKeyCommandPressUp(right, Custom4D2FCommandType.RightKeyUpCommand)

        upKey = Key(
            BitmapUtil.upKey!!,
            left.getX() + left.getWidth() + left.getWidth() / 2 - BitmapUtil.upKey!!.getWidth() / 2,
            Config.currentScreenHeight - BitmapUtil.leftKey!!.getHeight() * 2 - BitmapUtil.leftKey!!.getHeight() / 2 - BitmapUtil.upKey!!.getHeight() / 2,
            1,
            false
        )
        val up = upKey!!
        up.setEnableMultiTouch(true)
        val upKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(up, Custom4D2FCommandType.UPKeyDownCommand)
        val upKeyCommandPressUp =
            Custom4D2FKeyCommandPressUp(up, Custom4D2FCommandType.UPKeyUpCommand)
        downKey = Key(
            BitmapUtil.downKey!!,
            left.getX() + left.getWidth() + left.getWidth() / 2 - BitmapUtil.downKey!!.getWidth() / 2,
            Config.currentScreenHeight - BitmapUtil.leftKey!!.getHeight() + BitmapUtil.leftKey!!.getHeight() / 2 - BitmapUtil.downKey!!.getHeight() / 2,
            1,
            false
        )
        val down = downKey!!
        down.setEnableMultiTouch(true)
        val downKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(down, Custom4D2FCommandType.DownKeyDownCommand)
        val downKeyCommandPressUp =
            Custom4D2FKeyCommandPressDown(down, Custom4D2FCommandType.DownKeyUpCommand)

        enterKey = Key(
            BitmapUtil.enterKey!!,
            Config.currentScreenWidth - BitmapUtil.enterKey!!.getWidth(),
            Config.currentScreenHeight - BitmapUtil.enterKey!!.getHeight(),
            1,
            false
        )
        val enter = enterKey!!
        enter.setEnableMultiTouch(true)
        val enterKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(enter, Custom4D2FCommandType.EnterKeyDownCommand)
        val enterKeyCommandPressUp =
            Custom4D2FKeyCommandPressUp(enter, Custom4D2FCommandType.EnterKeyUpCommand)
        cancelKey = Key(
            BitmapUtil.cancelKey!!,
            0f,
            Config.currentScreenHeight - BitmapUtil.cancelKey!!.getHeight(),
            1,
            false
        )
        val cancel = cancelKey!!
        cancel.setEnableMultiTouch(true)
        val cancelKeyCommandPressDown =
            Custom4D2FKeyCommandPressDown(cancel, Custom4D2FCommandType.CancelKeyDownCommand)
        val cancelKeyCommandPressUp =
            Custom4D2FKeyCommandPressDown(cancel, Custom4D2FCommandType.CancelKeyUpCommand)

        remoteControl!!.setCommand(0, rightKeyCommandPressDown, rightKeyCommandPressUp)
        remoteControl!!.setCommand(1, leftKeyCommandPressDown, leftKeyCommandPressUp)
        remoteControl!!.setCommand(2, upKeyCommandPressDown, upKeyCommandPressUp)
        remoteControl!!.setCommand(3, downKeyCommandPressDown, downKeyCommandPressUp)
        remoteControl!!.setCommand(4, enterKeyCommandPressDown, enterKeyCommandPressUp)
        remoteControl!!.setCommand(5, cancelKeyCommandPressDown, cancelKeyCommandPressUp)
    }

    fun getUpKey(): Key? {
        return upKey
    }

    fun getDownKey(): Key? {
        return downKey
    }

    fun getLeftKey(): Key {
        return leftKey!!
    }

    fun getRightKey(): Key? {
        return rightKey
    }

    fun getEnterKey(): Key? {
        return enterKey
    }

    fun getCancelKey(): Key? {
        return cancelKey
    }

    fun getRemoteControl(): Custom4D2FRemoteControl {
        return remoteControl!!
    }
}

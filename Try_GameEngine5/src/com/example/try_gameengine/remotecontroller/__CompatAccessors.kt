@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.remotecontroller

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.utils.GameTimeUtil

internal fun DownCommand.getUpKey() = this.upKey
internal fun DownCommand.setUpKey(value: UpKey) { this.upKey = value }
internal fun DownKeyCommandPressDown.getDownKey() = this.downKey
internal fun DownKeyCommandPressDown.setDownKey(value: DownKey) { this.downKey = value }
internal fun DownKeyCommandPressUp.getDownKey() = this.downKey
internal fun DownKeyCommandPressUp.setDownKey(value: DownKey) { this.downKey = value }
internal fun LeftKeyCommandPressDown.getLeftKey() = this.leftKey
internal fun LeftKeyCommandPressDown.setLeftKey(value: LeftKey) { this.leftKey = value }
internal fun LeftKeyCommandPressUp.getLeftKey() = this.leftKey
internal fun LeftKeyCommandPressUp.setLeftKey(value: LeftKey) { this.leftKey = value }
internal fun RemoteControl.getOffCommands() = this.offCommands
internal fun RemoteControl.getOnCommands() = this.onCommands
internal fun RemoteControl.getSlot() = this.slot
internal fun RemoteControl.setOffCommands(value: Array<Command>) { this.offCommands = value }
internal fun RemoteControl.setOnCommands(value: Array<Command>) { this.onCommands = value }
internal fun RemoteControl.setSlot(value: Command?) { this.slot = value }
internal fun RemoteController.getRemoteControllerTimeUtil() = this.remoteControllerTimeUtil
internal fun RemoteController.setRemoteControllerTimeUtil(value: GameTimeUtil) { this.remoteControllerTimeUtil = value }
internal fun RightKeyCommandPressDown.getRightKey() = this.rightKey
internal fun RightKeyCommandPressDown.setRightKey(value: RightKey) { this.rightKey = value }
internal fun RightKeyCommandPressUp.getRightKey() = this.rightKey
internal fun RightKeyCommandPressUp.setRightKey(value: RightKey) { this.rightKey = value }
internal fun UpKeyCommandPressDown.getUpKey() = this.upKey
internal fun UpKeyCommandPressDown.setUpKey(value: UpKey) { this.upKey = value }
internal fun UpKeyCommandPressMove.getUpKey() = this.upKey
internal fun UpKeyCommandPressMove.setUpKey(value: UpKey) { this.upKey = value }
internal fun UpKeyCommandPressUp.getUpKey() = this.upKey
internal fun UpKeyCommandPressUp.setUpKey(value: UpKey) { this.upKey = value }

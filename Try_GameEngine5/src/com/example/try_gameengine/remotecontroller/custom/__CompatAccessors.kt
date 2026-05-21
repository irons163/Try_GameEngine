@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.remotecontroller.custom

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer
import com.example.try_gameengine.remotecontroller.IRemoteController
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteController
import com.example.try_gameengine.remotecontroller.custome.Key
import com.example.try_gameengine.utils.GameTimeUtil

internal fun CustomCommandPressDown.getEvent() = this.event
internal fun CustomCommandPressDown.getKey() = this.key
internal fun CustomCommandPressDown.setEvent(value: MotionEvent?) { this.event = value }
internal fun CustomCommandPressDown.setKey(value: Key) { this.key = value }
internal fun CustomRemoteControl.getOffCommands() = this.offCommands
internal fun CustomRemoteControl.getOnCommands() = this.onCommands
internal fun CustomRemoteControl.setOffCommands(value: MutableList<CustomCommand>) { this.offCommands = value }
internal fun CustomRemoteControl.setOnCommands(value: MutableList<CustomCommand>) { this.onCommands = value }
internal fun CustomRemoteController.getRemoteControllerTimeUtil() = this.remoteControllerTimeUtil
internal fun CustomRemoteController.setRemoteControllerTimeUtil(value: GameTimeUtil) { this.remoteControllerTimeUtil = value }
internal fun CustomRemoteLoader.getKeys() = this.keys
internal fun CustomRemoteLoader.setKeys(value: MutableList<Key?>) { this.keys = value }

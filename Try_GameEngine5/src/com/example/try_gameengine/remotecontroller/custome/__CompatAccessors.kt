@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.remotecontroller.custome

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.example.try_gameengine.remotecontroller.IRemoteController
import com.example.try_gameengine.utils.GameTimeUtil

internal fun Custom4D2FRemoteControl.getOffCommands() = this.offCommands
internal fun Custom4D2FRemoteControl.getOnCommands() = this.onCommands
internal fun Custom4D2FRemoteControl.getSlot() = this.slot
internal fun Custom4D2FRemoteControl.setOffCommands(value: Array<Custom4D2FCommand>) { this.offCommands = value }
internal fun Custom4D2FRemoteControl.setOnCommands(value: Array<Custom4D2FCommand>) { this.onCommands = value }
internal fun Custom4D2FRemoteControl.setSlot(value: Custom4D2FCommand?) { this.slot = value }
internal fun Custom4D2FRemoteController.getRemoteControllerTimeUtil() = this.remoteControllerTimeUtil
internal fun Custom4D2FRemoteController.setRemoteControllerTimeUtil(value: GameTimeUtil) { this.remoteControllerTimeUtil = value }

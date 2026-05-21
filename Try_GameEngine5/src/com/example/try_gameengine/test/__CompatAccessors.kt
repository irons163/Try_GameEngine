@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.test

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.CountDownTimer
import android.util.Log
import com.example.try_gameengine.action.CopyMoveDecorator
import com.example.try_gameengine.action.DoubleDecorator
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementActionSet
import com.example.try_gameengine.action.MovementActionSetWithThread
import com.example.try_gameengine.action.MovementAtionController
import com.example.try_gameengine.action.MovementInfoFactory
import com.example.try_gameengine.action.SpecialMovementActionFactory
import com.example.try_gameengine.framework.Sprite
import com.example.try_gameengine.observer.Observer
import com.example.try_gameengine.observer.Subject

internal fun Enemy.getAction() = this.action
internal fun Enemy.getC() = this.c
internal fun Enemy.getInfos() = this.infos
internal fun Enemy.getMovementActionDescriptions() = this.movementActionDescriptions
internal fun Enemy.setAction(value: MovementAction?) { this.action = value }
internal fun Enemy.setInfos(value: MutableList<MovementActionInfo?>?) { this.infos = value }
internal fun EnemyManager.getEnemies() = this.enemies

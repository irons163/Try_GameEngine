@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.action

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.IActionListener
import com.example.try_gameengine.framework.LightImage
import com.example.try_gameengine.framework.Sprite
import java.util.Arrays
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

internal fun CircleController.getAngle() = this.angle
internal fun CircleController.getFirstExecute() = this.firstExecute
internal fun CircleController.getInitspeedX() = this.initspeedX
internal fun CircleController.getOffsetRotationPerUpdate() = this.offsetRotationPerUpdate
internal fun CircleController.getRotation() = this.rotation
internal fun CircleController.setAngle(value: Float) { this.angle = value }
internal fun CircleController.setFirstExecute(value: Boolean) { this.firstExecute = value }
internal fun CircleController.setInitspeedX(value: Float) { this.initspeedX = value }
internal fun CircleController.setOffsetRotationPerUpdate(value: Float) { this.offsetRotationPerUpdate = value }
internal fun CircleController.setRotation(value: Float) { this.rotation = value }
internal fun EaseRateDecorator.getRate() = this.rate
internal fun EaseRateDecorator.setRate(value: Float) { this.rate = value }
internal fun GravityController.getAy() = this.ay
internal fun GravityController.getDistanceX() = this.distanceX
internal fun GravityController.getFirstExecute() = this.firstExecute
internal fun GravityController.getMathUtil() = this.mathUtil
internal fun GravityController.getPathType() = this.pathType
internal fun GravityController.getSavedMathUtil() = this.savedMathUtil
internal fun GravityController.setAy(value: Float) { this.ay = value }
internal fun GravityController.setDistanceX(value: Float) { this.distanceX = value }
internal fun GravityController.setFirstExecute(value: Boolean) { this.firstExecute = value }
internal fun GravityController.setMathUtil(value: MathUtil?) { this.mathUtil = value }
internal fun GravityController.setPathType(value: IGravityController.PathType?) { this.pathType = value }
internal fun GravityController.setSavedMathUtil(value: MathUtil?) { this.savedMathUtil = value }
internal fun JumpController.getFirstExecute() = this.firstExecute
internal fun JumpController.getMathUtil() = this.mathUtil
internal fun JumpController.getPathType() = this.pathType
internal fun JumpController.setFirstExecute(value: Boolean) { this.firstExecute = value }
internal fun JumpController.setMathUtil(value: MathUtil) { this.mathUtil = value }
internal fun JumpController.setPathType(value: IGravityController.PathType?) { this.pathType = value }
internal fun MathUtil.getAngle() = this.angle
internal fun MathUtil.getAx() = this.ax
internal fun MathUtil.getAy() = this.ay
internal fun MathUtil.getDeltaTime() = this.deltaTime
internal fun MathUtil.getSpeedX() = this.speedX
internal fun MathUtil.getSpeedY() = this.speedY
internal fun MathUtil.getVx() = this.vx
internal fun MathUtil.getVy() = this.vy
internal fun MathUtil.setAx(value: Float) { this.ax = value }
internal fun MathUtil.setAy(value: Float) { this.ay = value }
internal fun MathUtil.setDeltaTime(value: Float) { this.deltaTime = value }
internal fun MathUtil.setVx(value: Float) { this.vx = value }
internal fun MathUtil.setVy(value: Float) { this.vy = value }
internal fun MovementAction.getActionListener() = this.actionListener
internal fun MovementAction.getActions() = this.actions
internal fun MovementAction.getController() = this.controller
internal fun MovementAction.getDidInitTimer() = this.didInitTimer
internal fun MovementAction.getMovementActionMemento() = this.movementActionMemento
internal fun MovementAction.getMovementInfoList() = this.movementInfoList
internal fun MovementAction.getName() = this.name
internal fun MovementAction.getStartMovementInfoList() = this.startMovementInfoList
internal fun MovementAction.getThread() = this.thread
internal fun MovementAction.isLoop() = this.isLoop
internal fun MovementAction.isRepeatSpriteActionIfMovementActionRepeat() = this.isRepeatSpriteActionIfMovementActionRepeat
internal fun MovementAction.isSigleThread() = this.isSigleThread
internal fun MovementAction.setActions(value: MutableList<MovementAction>) { this.actions = value }
internal fun MovementAction.setController(value: MovementAtionController?) { this.controller = value }
internal fun MovementAction.setDidInitTimer(value: Boolean) { this.didInitTimer = value }
internal fun MovementAction.setLoop(value: Boolean) { this.isLoop = value }
internal fun MovementAction.setMovementActionMemento(value: IMovementActionMemento?) { this.movementActionMemento = value }
internal fun MovementAction.setMovementInfoList(value: MutableList<MovementActionInfo?>) { this.movementInfoList = value }
internal fun MovementAction.setName(value: String) { this.name = value }
internal fun MovementAction.setRepeatSpriteActionIfMovementActionRepeat(value: Boolean) { this.isRepeatSpriteActionIfMovementActionRepeat = value }
internal fun MovementAction.setSigleThread(value: Boolean) { this.isSigleThread = value }
internal fun MovementAction.setThread(value: Thread?) { this.thread = value }
internal fun MovementActionFrameItem.getDx() = this.dx
internal fun MovementActionFrameItem.getDy() = this.dy
internal fun MovementActionFrameItem.getFrameTimes() = this.frameTimes
internal fun MovementActionFrameItem.getInfo() = this.info
internal fun MovementActionFrameItem.getMillisDelay() = this.millisDelay
internal fun MovementActionFrameItem.getMillisTotal() = this.millisTotal
internal fun MovementActionFrameItem.getResetTotal() = this.resetTotal
internal fun MovementActionFrameItem.getResumeFrameCount() = this.resumeFrameCount
internal fun MovementActionFrameItem.getResumeFrameIndex() = this.resumeFrameIndex
internal fun MovementActionFrameItem.getResumeTotal() = this.resumeTotal
internal fun MovementActionFrameItem.isStop() = this.isStop
internal fun MovementActionFrameItem.setDx(value: Float) { this.dx = value }
internal fun MovementActionFrameItem.setDy(value: Float) { this.dy = value }
internal fun MovementActionFrameItem.setFrameTimes(value: LongArray) { this.frameTimes = value }
internal fun MovementActionFrameItem.setInfo(value: MovementActionInfo) { this.info = value }
internal fun MovementActionFrameItem.setMillisDelay(value: Long) { this.millisDelay = value }
internal fun MovementActionFrameItem.setMillisTotal(value: Long) { this.millisTotal = value }
internal fun MovementActionFrameItem.setResetTotal(value: Long) { this.resetTotal = value }
internal fun MovementActionFrameItem.setResumeFrameCount(value: Int) { this.resumeFrameCount = value }
internal fun MovementActionFrameItem.setResumeFrameIndex(value: Int) { this.resumeFrameIndex = value }
internal fun MovementActionFrameItem.setResumeTotal(value: Long) { this.resumeTotal = value }
internal fun MovementActionFrameItem.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionGroup.getFinishCount() = this.finishCount
internal fun MovementActionGroup.getId() = this.id
internal fun MovementActionGroup.getMovementActions() = this.movementActions
internal fun MovementActionGroup.getStartCount() = this.startCount
internal fun MovementActionGroup.isAutoResetAfterLastFinish() = this.isAutoResetAfterLastFinish
internal fun MovementActionGroup.setAutoResetAfterLastFinish(value: Boolean) { this.isAutoResetAfterLastFinish = value }
internal fun MovementActionGroup.setFinishCount(value: Int) { this.finishCount = value }
internal fun MovementActionGroup.setId(value: String?) { this.id = value }
internal fun MovementActionGroup.setMovementActions(value: MutableList<MovementAction>) { this.movementActions = value }
internal fun MovementActionGroup.setStartCount(value: Int) { this.startCount = value }
internal fun MovementActionItem.getInfo() = this.info
internal fun MovementActionItem.getTriggerEnable() = this.triggerEnable
internal fun MovementActionItem.isFirstTime() = this.isFirstTime
internal fun MovementActionItem.isReset() = this.isReset
internal fun MovementActionItem.isStop() = this.isStop
internal fun MovementActionItem.setFirstTime(value: Boolean) { this.isFirstTime = value }
internal fun MovementActionItem.setInfo(value: MovementActionInfo?) { this.info = value ?: return }
internal fun MovementActionItem.setReset(value: Boolean) { this.isReset = value }
internal fun MovementActionItem.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionItem.setTriggerEnable(value: Boolean) { this.triggerEnable = value }
internal fun MovementActionItemAnimate.getFrameIdx() = this.frameIdx
internal fun MovementActionItemAnimate.getFrameTimes() = this.frameTimes
internal fun MovementActionItemAnimate.getInfo() = this.info
internal fun MovementActionItemAnimate.getMillisDelay() = this.millisDelay
internal fun MovementActionItemAnimate.getMillisTotal() = this.millisTotal
internal fun MovementActionItemAnimate.getMyTrigger() = this.myTrigger
internal fun MovementActionItemAnimate.getName() = this.name
internal fun MovementActionItemAnimate.getNextframeTrigger() = this.nextframeTrigger
internal fun MovementActionItemAnimate.getPauseFrameCounter() = this.pauseFrameCounter
internal fun MovementActionItemAnimate.getPauseFrameNum() = this.pauseFrameNum
internal fun MovementActionItemAnimate.getResetTotal() = this.resetTotal
internal fun MovementActionItemAnimate.getResumeFrameCount() = this.resumeFrameCount
internal fun MovementActionItemAnimate.getResumeFrameIndex() = this.resumeFrameIndex
internal fun MovementActionItemAnimate.getResumeTotal() = this.resumeTotal
internal fun MovementActionItemAnimate.getTriggerEnable() = this.triggerEnable
internal fun MovementActionItemAnimate.isCycleFinish() = this.isCycleFinish
internal fun MovementActionItemAnimate.isStop() = this.isStop
internal fun MovementActionItemAnimate.setCycleFinish(value: Boolean) { this.isCycleFinish = value }
internal fun MovementActionItemAnimate.setFrameIdx(value: Int) { this.frameIdx = value }
internal fun MovementActionItemAnimate.setFrameTimes(value: LongArray?) { this.frameTimes = value }
internal fun MovementActionItemAnimate.setInfo(value: MovementActionInfo) { this.info = value }
internal fun MovementActionItemAnimate.setMillisDelay(value: Long) { this.millisDelay = value }
internal fun MovementActionItemAnimate.setMillisTotal(value: Long) { this.millisTotal = value }
internal fun MovementActionItemAnimate.setName(value: String?) { this.name = value }
internal fun MovementActionItemAnimate.setPauseFrameCounter(value: Int) { this.pauseFrameCounter = value }
internal fun MovementActionItemAnimate.setPauseFrameNum(value: Long) { this.pauseFrameNum = value }
internal fun MovementActionItemAnimate.setResetTotal(value: Long) { this.resetTotal = value }
internal fun MovementActionItemAnimate.setResumeFrameCount(value: Int) { this.resumeFrameCount = value }
internal fun MovementActionItemAnimate.setResumeFrameIndex(value: Int) { this.resumeFrameIndex = value }
internal fun MovementActionItemAnimate.setResumeTotal(value: Long) { this.resumeTotal = value }
internal fun MovementActionItemAnimate.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionItemAnimate.setTriggerEnable(value: Boolean) { this.triggerEnable = value }
internal fun MovementActionItemAnimate2.getData() = this.data
internal fun MovementActionItemAnimate2.getMyTrigger() = this.myTrigger
internal fun MovementActionItemAnimate2.setData(value: MovementActionItemTrigger?) { this.data = value }
internal fun MovementActionItemCountDownTimer.getCountDownTimer() = this.countDownTimer
internal fun MovementActionItemCountDownTimer.getHandler() = this.handler
internal fun MovementActionItemCountDownTimer.setCountDownTimer(value: CountDownTimer?) { this.countDownTimer = value }
internal fun MovementActionItemCountDownTimer.setHandler(value: Handler) { this.handler = value }
internal fun MovementActionItemForMilliseconds.getDx() = this.dx
internal fun MovementActionItemForMilliseconds.getDy() = this.dy
internal fun MovementActionItemForMilliseconds.getFrameIdx() = this.frameIdx
internal fun MovementActionItemForMilliseconds.getMillisDelay() = this.millisDelay
internal fun MovementActionItemForMilliseconds.getMillisTotal() = this.millisTotal
internal fun MovementActionItemForMilliseconds.getName() = this.name
internal fun MovementActionItemForMilliseconds.getResetTotal() = this.resetTotal
internal fun MovementActionItemForMilliseconds.getResumeTotal() = this.resumeTotal
internal fun MovementActionItemForMilliseconds.isActionFinish() = this.isActionFinish
internal fun MovementActionItemForMilliseconds.isFirstTime() = this.isFirstTime
internal fun MovementActionItemForMilliseconds.isReset() = this.isReset
internal fun MovementActionItemForMilliseconds.isStop() = this.isStop
internal fun MovementActionItemForMilliseconds.setActionFinish(value: Boolean) { this.isActionFinish = value }
internal fun MovementActionItemForMilliseconds.setDx(value: Float) { this.dx = value }
internal fun MovementActionItemForMilliseconds.setDy(value: Float) { this.dy = value }
internal fun MovementActionItemForMilliseconds.setFirstTime(value: Boolean) { this.isFirstTime = value }
internal fun MovementActionItemForMilliseconds.setFrameIdx(value: Int) { this.frameIdx = value }
internal fun MovementActionItemForMilliseconds.setMillisDelay(value: Long) { this.millisDelay = value }
internal fun MovementActionItemForMilliseconds.setMillisTotal(value: Long) { this.millisTotal = value }
internal fun MovementActionItemForMilliseconds.setName(value: String?) { this.name = value }
internal fun MovementActionItemForMilliseconds.setReset(value: Boolean) { this.isReset = value }
internal fun MovementActionItemForMilliseconds.setResetTotal(value: Long) { this.resetTotal = value }
internal fun MovementActionItemForMilliseconds.setResumeTotal(value: Long) { this.resumeTotal = value }
internal fun MovementActionItemForMilliseconds.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionItemUpdate.getData() = this.data
internal fun MovementActionItemUpdate.getMyTrigger() = this.myTrigger
internal fun MovementActionItemUpdate.setData(value: MovementActionItemTrigger) { this.data = value }
internal fun MovementActionOwnerGroup.getFinishBlocks() = this.finishBlocks
internal fun MovementActionOwnerGroup.getFinishCount() = this.finishCount
internal fun MovementActionOwnerGroup.getId() = this.id
internal fun MovementActionOwnerGroup.getMovementActions() = this.movementActions
internal fun MovementActionOwnerGroup.getSprites() = this.sprites
internal fun MovementActionOwnerGroup.getStartBlocks() = this.startBlocks
internal fun MovementActionOwnerGroup.getStartCount() = this.startCount
internal fun MovementActionOwnerGroup.isAutoResetAfterLastFinish() = this.isAutoResetAfterLastFinish
internal fun MovementActionOwnerGroup.setAutoResetAfterLastFinish(value: Boolean) { this.isAutoResetAfterLastFinish = value }
internal fun MovementActionOwnerGroup.setFinishCount(value: Int) { this.finishCount = value }
internal fun MovementActionOwnerGroup.setId(value: String?) { this.id = value }
internal fun MovementActionOwnerGroup.setMovementActions(value: MutableList<MovementAction>) { this.movementActions = value }
internal fun MovementActionOwnerGroup.setSprites(value: MutableList<Sprite?>) { this.sprites = value }
internal fun MovementActionOwnerGroup.setStartCount(value: Int) { this.startCount = value }
internal fun MovementActionSetGroupWithOutThread.isStop() = this.isStop
internal fun MovementActionSetGroupWithOutThread.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionSetWithOutThread.isStop() = this.isStop
internal fun MovementActionSetWithOutThread.setStop(value: Boolean) { this.isStop = value }
internal fun MovementActionSetWithThreadPool.getFuture() = this.future
internal fun MovementActionSetWithThreadPool.isStop() = this.isStop
internal fun MovementActionSetWithThreadPool.setFuture(value: Future<*>?) { this.future = value }
internal fun MovementActionSetWithThreadPool.setStop(value: Boolean) { this.isStop = value }
internal fun MovementAtionController.getAction() = this.action
internal fun MovementAtionController.setAction(value: MovementAction) { this.action = value }
internal fun RotationCurveController.getFirstExecute() = this.firstExecute
internal fun RotationCurveController.getInitspeedX() = this.initspeedX
internal fun RotationCurveController.getOrigineDx() = this.origineDx
internal fun RotationCurveController.getOrigineDy() = this.origineDy
internal fun RotationCurveController.getRotation() = this.rotation
internal fun RotationCurveController.setFirstExecute(value: Boolean) { this.firstExecute = value }
internal fun RotationCurveController.setInitspeedX(value: Float) { this.initspeedX = value }
internal fun RotationCurveController.setOrigineDx(value: Float) { this.origineDx = value }
internal fun RotationCurveController.setOrigineDy(value: Float) { this.origineDy = value }
internal fun RotationCurveController.setRotation(value: Float) { this.rotation = value }
internal fun RotationOnceController.getFirstExecute() = this.firstExecute
internal fun RotationOnceController.getOrigineDx() = this.origineDx
internal fun RotationOnceController.getOrigineDy() = this.origineDy
internal fun RotationOnceController.getRotation() = this.rotation
internal fun RotationOnceController.setFirstExecute(value: Boolean) { this.firstExecute = value }
internal fun RotationOnceController.setOrigineDx(value: Float) { this.origineDx = value }
internal fun RotationOnceController.setOrigineDy(value: Float) { this.origineDy = value }
internal fun RotationOnceController.setRotation(value: Float) { this.rotation = value }
internal fun SimultaneouslyMovementActionSet.getSimultaneouslyLock() = this.SimultaneouslyLock
internal fun SimultaneouslyMovementActionSet.setSimultaneouslyLock(value: Any) { this.SimultaneouslyLock = value }

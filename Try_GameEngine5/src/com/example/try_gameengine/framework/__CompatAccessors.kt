@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.framework

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.example.try_gameengine.Camera.Camera
import com.example.try_gameengine.action.MAction
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementAtionController
import com.example.try_gameengine.action.Time
import com.example.try_gameengine.avg.GraphicsUtils
import com.example.try_gameengine.avg.NumberUtils
import com.example.try_gameengine.physics.PhysicsBody
import com.example.try_gameengine.scene.Scene
import com.example.try_gameengine.scene.SceneManager
import com.example.try_gameengine.scene.Scene.DestoryData
import com.example.try_gameengine.stage.Stage
import com.example.try_gameengine.stage.StageManager
import com.example.try_gameengine.utils.SpriteDetectAreaHandler
import java.util.Collections
import java.util.Hashtable
import java.util.Random
import java.util.Stack
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs
import kotlin.math.ceil
import org.loon.framework.android.game.physics.LWorld
import org.loon.framework.android.game.physics.RectBox

internal fun Stage.getCurrentActiveScene(): Scene? = getSceneManager().currentActiveScene
internal fun SceneManager.getCurrentActiveScene(): Scene? = currentActiveScene
internal fun Scene.getCamera() = camera

internal fun ALayer.LayerParam.getBindPositionX() = this.bindPositionX
internal fun ALayer.LayerParam.getBindPositionXY() = this.bindPositionXY
internal fun ALayer.LayerParam.getBindPositionY() = this.bindPositionY
internal fun ALayer.LayerParam.getPercentageH() = this.percentageH
internal fun ALayer.LayerParam.getPercentageW() = this.percentageW
internal fun ALayer.LayerParam.getPercentageX() = this.percentageX
internal fun ALayer.LayerParam.getPercentageY() = this.percentageY
internal fun ALayer.LayerParam.isEnabledBindPositionXY() = this.isEnabledBindPositionXY
internal fun ALayer.LayerParam.isEnabledPercentagePositionX() = this.isEnabledPercentagePositionX
internal fun ALayer.LayerParam.isEnabledPercentagePositionY() = this.isEnabledPercentagePositionY
internal fun ALayer.LayerParam.isEnabledPercentageSizeH() = this.isEnabledPercentageSizeH
internal fun ALayer.LayerParam.isEnabledPercentageSizeW() = this.isEnabledPercentageSizeW
internal fun ALayer.LayerParam.setEnabledBindPositionXY(value: Boolean) { this.isEnabledBindPositionXY = value }
internal fun ALayer.LayerParam.setEnabledPercentagePositionX(value: Boolean) { this.isEnabledPercentagePositionX = value }
internal fun ALayer.LayerParam.setEnabledPercentagePositionY(value: Boolean) { this.isEnabledPercentagePositionY = value }
internal fun ALayer.LayerParam.setEnabledPercentageSizeH(value: Boolean) { this.isEnabledPercentageSizeH = value }
internal fun ALayer.LayerParam.setEnabledPercentageSizeW(value: Boolean) { this.isEnabledPercentageSizeW = value }
internal fun ALayer.getALayer() = this.aLayer
internal fun ALayer.getAnchorPointXY() = this.anchorPointXY
internal fun ALayer.getSrc() = this.src
internal fun ALayer.isBitmapSacleToFitSize() = this.isBitmapSacleToFitSize
internal fun ALayer.isEnableMultiTouch() = this.isEnableMultiTouch
internal fun ALayer.isEnableTouchOnSlef() = this.isEnableTouchOnSlef
internal fun ALayer.isEnableTouchOnSlefAndChildren() = this.isEnableTouchOnSlefAndChildren
internal fun ALayer.setALayer(value: ALayer?) { this.aLayer = value }
internal fun ALayer.setBitmapSacleToFitSize(value: Boolean) { this.isBitmapSacleToFitSize = value }
internal fun ALayer.setEnableMultiTouch(value: Boolean) { this.isEnableMultiTouch = value }
internal fun ALayer.setEnableTouchOnSlef(value: Boolean) { this.isEnableTouchOnSlef = value }
internal fun ALayer.setEnableTouchOnSlefAndChildren(value: Boolean) { this.isEnableTouchOnSlefAndChildren = value }
internal fun ALayer.setSrc(value: Rect?) { this.src = value }
internal fun ALayerComponent.getName() = this.name
internal fun ALayerComponent.getPrice() = this.price
internal fun APlayerManager.getHandler() = this.handler
internal fun APlayerManager.getWinner() = this.winner
internal fun APlayerManager.isCanPutChessPoint() = this.isCanPutChessPoint
internal fun APlayerManager.isSomeOneWin() = this.isSomeOneWin
internal fun APlayerManager.setCanPutChessPoint(value: Boolean) { this.isCanPutChessPoint = value }
internal fun APlayerManager.setHandler(value: Handler) { this.handler = value }
internal fun BasePlayer.getCount() = this.count
internal fun BasePlayer.setCount(value: Int) { this.count = value }
internal fun ButtonLayer.getText() = this.text
internal fun ButtonLayer.isClickCancled() = this.isClickCancled
internal fun ButtonLayer.setClickCancled(value: Boolean) { this.isClickCancled = value }
internal fun ButtonLayer.setText(value: String?) { this.text = value }
internal fun ChessBoard.getMaxX() = this.maxX
internal fun ChessBoard.getMaxY() = this.maxY
internal fun ChessBoard.setMaxX(value: Int) { this.maxX = value }
internal fun ChessBoard.setMaxY(value: Int) { this.maxY = value }
internal fun ChessPoint.getChessPointBimap() = this.chessPointBimap
internal fun ChessPoint.setChessPointBimap(value: Bitmap?) { this.chessPointBimap = value }
internal fun ChessPointManager.getChessPointBimapResiource() = this.chessPointBimapResiource
internal fun ChessPointManager.getChessPointBimapResiourceUseable() = this.chessPointBimapResiourceUseable
internal fun ChessPointManager.setChessPointBimapResiource(value: IntArray) { this.chessPointBimapResiource = value }
internal fun ChessPointManager.setChessPointBimapResiourceUseable(value: BooleanArray) { this.chessPointBimapResiourceUseable = value }
internal fun CompositeIterator.getStack() = this.stack
internal fun CompositeIterator.setStack(value: Stack<*>) { this.stack = value as Stack<MutableIterator<*>?> }
internal fun DialogLayer.getLeftButton() = this.leftButton
internal fun DialogLayer.getMidButton() = this.midButton
internal fun DialogLayer.getRightButton() = this.rightButton
internal fun DialogLayer.setLeftButton(value: ButtonLayer?) { this.leftButton = value }
internal fun DialogLayer.setMidButton(value: ButtonLayer?) { this.midButton = value }
internal fun DialogLayer.setRightButton(value: ButtonLayer?) { this.rightButton = value }
internal fun GameController.BlockRunData.isBlock() = this.isBlock
internal fun GameController.BlockRunData.setBlock(value: Boolean) { this.isBlock = value }
internal fun GameModel.getCamera() = this.camera
internal fun GameModel.getCanvas() = this.canvas
internal fun GameModel.getEndTime() = this.endTime
internal fun GameModel.getFps() = this.fps
internal fun GameModel.getGameThread() = this.gameThread
internal fun GameModel.getInterval() = this.interval
internal fun GameModel.getPaint() = this.paint
internal fun GameModel.getStartTime() = this.startTime
internal fun GameModel.setCamera(value: Camera?) { this.camera = value }
internal fun GameModel.setCanvas(value: Canvas?) { this.canvas = value }
internal fun GameModel.setFps(value: Float) { this.fps = value }
internal fun GameModel.setGameThread(value: Thread) { this.gameThread = value }
internal fun GameModel.setPaint(value: Paint) { this.paint = value }
internal fun LayerManager.getLayerLevelList() = this.layerLevelList
internal fun LightImage.ClipInfo.getClipStartX() = this.clipStartX
internal fun LightImage.ClipInfo.getClipStartY() = this.clipStartY
internal fun LightImage.ClipInfo.getHeight() = this.height
internal fun LightImage.ClipInfo.getWidth() = this.width
internal fun LightImage.getClipIfno() = this.clipIfno
internal fun LightImage.getHeight() = this.height
internal fun LightImage.getWidth() = this.width
internal fun Line.getXStart() = this.xStart
internal fun Line.getXStop() = this.xStop
internal fun Line.getYStart() = this.yStart
internal fun Line.getYStop() = this.yStop
internal fun Line.setXStart(value: Float) { this.xStart = value }
internal fun Line.setXStop(value: Float) { this.xStop = value }
internal fun Line.setYStart(value: Float) { this.yStart = value }
internal fun Line.setYStop(value: Float) { this.yStop = value }
internal fun Logic.getClickPoint() = this.clickPoint
internal fun Logic.getWhoPlay() = this.whoPlay
internal fun Logic.setClickPoint(value: Point?) { this.clickPoint = value }
internal fun Logic.setWhoPlay(value: Int) { this.whoPlay = value }
internal fun Minimax.getBestMaxDeep() = this.bestMaxDeep
internal fun Minimax.getBestMinDeep() = this.bestMinDeep
internal fun Minimax.getBestMove() = this.bestMove
internal fun Minimax.setBestMaxDeep(value: Int) { this.bestMaxDeep = value }
internal fun Minimax.setBestMinDeep(value: Int) { this.bestMinDeep = value }
internal fun NormalWinLoseLogic.getWho() = this.who
internal fun NormalWinLoseLogic.setWho(value: Int) { this.who = value }
internal fun ShapeLayer.CircleShape.getRadius() = this.radius
internal fun ShapeLayer.CircleShape.setRadius(value: Float) { this.radius = value }
internal fun ShapeLayer.MaskShape.getPath() = this.path
internal fun ShapeLayer.MaskShape.getShape() = this.shape
internal fun ShapeLayer.MaskShape.setPath(value: Path) { this.path = value }
internal fun ShapeLayer.RectShape.getRectF() = this.rectF
internal fun ShapeLayer.RectShape.setRectF(value: RectF) { this.rectF = value }
internal fun ShapeLayer.Shape.getCenter() = this.center
internal fun ShapeLayer.Shape.getPaint() = this.paint
internal fun ShapeLayer.Shape.getPath() = this.path
internal fun ShapeLayer.Shape.getShapeBounds() = this.shapeBounds
internal fun ShapeLayer.Shape.setPaint(value: Paint) { this.paint = value }
internal fun ShapeLayer.Shape.setPath(value: Path) { this.path = value }
internal fun ShapeLayer.getPath() = this.path
internal fun ShapeLayer.getShape() = this.shape
internal fun ShapeLayer.getTmpSize() = this.tmpSize
internal fun ShapeLayer.isEnableShape() = this.isEnableShape
internal fun ShapeLayer.setEnableShape(value: Boolean) { this.isEnableShape = value }
internal fun ShapeLayer.setPath(value: Path) { this.path = value }
internal fun ShapeLayer.setTmpSize(value: Point) { this.tmpSize = value }
internal fun Sprite.SpriteAction.getActionListener() = this.actionListener
internal fun Sprite.SpriteAction.getBitmapFrames() = this.bitmapFrames
internal fun Sprite.SpriteAction.getFrameTime() = this.frameTime
internal fun Sprite.SpriteAction.getFrames() = this.frames
internal fun Sprite.SpriteAction.getName() = this.name
internal fun Sprite.SpriteAction.getScale() = this.scale
internal fun Sprite.SpriteAction.getUpdateByMovement() = this.updateByMovement
internal fun Sprite.SpriteAction.isLoop() = this.isLoop
internal fun Sprite.SpriteAction.setActionListener(value: IActionListener) { this.actionListener = value }
internal fun Sprite.SpriteAction.setBitmapFrames(value: Array<Bitmap?>) { this.bitmapFrames = value }
internal fun Sprite.SpriteAction.setFrameTime(value: IntArray) { this.frameTime = value }
internal fun Sprite.SpriteAction.setFrames(value: IntArray?) { this.frames = value }
internal fun Sprite.SpriteAction.setLoop(value: Boolean) { this.isLoop = value }
internal fun Sprite.SpriteAction.setName(value: String?) { this.name = value }
internal fun Sprite.SpriteAction.setScale(value: Float) { this.scale = value }
internal fun Sprite.SpriteAction.setUpdateByMovement(value: Boolean) { this.updateByMovement = value }
internal fun Sprite.getActionName() = this.actionName
internal fun Sprite.getActions() = this.actions
internal fun Sprite.getBitmapOrginalFrameHright() = this.bitmapOrginalFrameHright
internal fun Sprite.getBitmapOrginalFrameWidth() = this.bitmapOrginalFrameWidth
internal fun Sprite.getCanCollision() = this.canCollision
internal fun Sprite.getCurrentAction() = this.currentAction
internal fun Sprite.getCurrentFrame() = this.currentFrame
internal fun Sprite.getDrawOffsetX() = this.drawOffsetX
internal fun Sprite.getDrawRectF() = this.drawRectF
internal fun Sprite.getDrawWithoutClip() = this.drawWithoutClip
internal fun Sprite.getFrameHeight() = this.frameHeight
internal fun Sprite.getFrameIdx() = this.frameIdx
internal fun Sprite.getFrameWidth() = this.frameWidth
internal fun Sprite.getMoveRage() = this.moveRage
internal fun Sprite.getMovementAction() = this.movementAction
internal fun Sprite.getMovementActions() = this.movementActions
internal fun Sprite.getScale() = this.scale
internal fun Sprite.getSpriteMatrix() = this.spriteMatrix
internal fun Sprite.getXscale() = this.xscale
internal fun Sprite.getYscale() = this.yscale
internal fun Sprite.isCollisionRectFEnable() = this.isCollisionRectFEnable
internal fun Sprite.isNeedCreateNewInstance() = this.isNeedCreateNewInstance
internal fun Sprite.isNeedRemoveInstance() = this.isNeedRemoveInstance
internal fun Sprite.isStop() = this.isStop
internal fun Sprite.setCanCollision(value: Boolean) { this.canCollision = value }
internal fun Sprite.setCollisionRectFEnable(value: Boolean) { this.isCollisionRectFEnable = value }
internal fun Sprite.setCurrentFrame(value: Int) { this.currentFrame = value }
internal fun Sprite.setDrawOffsetX(value: Float) { this.drawOffsetX = value }
internal fun Sprite.setDrawRectF(value: RectF?) { this.drawRectF = value }
internal fun Sprite.setDrawWithoutClip(value: Boolean) { this.drawWithoutClip = value }
internal fun Sprite.setFrameHeight(value: Float) { this.frameHeight = value }
internal fun Sprite.setFrameIdx(value: Int) { this.frameIdx = value }
internal fun Sprite.setFrameWidth(value: Float) { this.frameWidth = value }
internal fun Sprite.setMoveRage(value: RectF?) { this.moveRage = value }
internal fun Sprite.setMovementAction(value: MovementAction?) { this.movementAction = value }
internal fun Sprite.setMovementActions(value: ConcurrentLinkedQueue<MovementAction>) { this.movementActions = value }
internal fun Sprite.setScale(value: Float) { this.scale = value }
internal fun Sprite.setSpriteMatrix(value: Matrix?) { this.spriteMatrix = value }
internal fun Sprite.setStop(value: Boolean) { this.isStop = value }
internal fun Sprite.setXscale(value: Float) { this.xscale = value }
internal fun Sprite.setYscale(value: Float) { this.yscale = value }
internal fun StatusBar.getColor() = this.color
internal fun StatusBar.getMaxValue() = this.maxValue
internal fun StatusBar.getMinValue() = this.minValue
internal fun StatusBar.getValue() = this.value
internal fun StatusBar.getXInScene() = this.xInScene
internal fun StatusBar.getYInScene() = this.yInScene
internal fun StatusBar.isHit() = this.isHit
internal fun StatusBar.isShowHP() = this.isShowHP
internal fun StatusBar.setColor(value: Int) { this.color = value }
internal fun StatusBar.setHit(value: Boolean) { this.isHit = value }
internal fun StatusBar.setMaxValue(value: Int) { this.maxValue = value }
internal fun StatusBar.setMinValue(value: Int) { this.minValue = value }
internal fun StatusBar.setShowHP(value: Boolean) { this.isShowHP = value }
internal fun StatusBar.setValue(value: Int) { this.value = value }
internal fun TouchDispatcher.getHasTouchableObjectConsumed() = this.hasTouchableObjectConsumed
internal fun TouchDispatcher.getStandardTouchHandlers() = this.standardTouchHandlers
internal fun TouchDispatcher.getTouchDispatcherConsumeFlag() = this.touchDispatcherConsumeFlag
internal fun TouchDispatcher.getTouchDispatcherEnableFlag() = this.touchDispatcherEnableFlag
internal fun TouchDispatcher.getTouchHandlers() = this.touchHandlers
internal fun TouchDispatcher.isEnabled() = this.isEnabled
internal fun TouchDispatcher.setHasTouchableObjectConsumed(value: Boolean) { this.hasTouchableObjectConsumed = value }
internal fun TouchDispatcher.setStandardTouchHandlers(value: MutableList<StandardTouchHandler>) { this.standardTouchHandlers = value }
internal fun TouchDispatcher.setTouchDispatcherConsumeFlag(value: Int) { this.touchDispatcherConsumeFlag = value }
internal fun TouchDispatcher.setTouchDispatcherEnableFlag(value: Int) { this.touchDispatcherEnableFlag = value }
internal fun TouchDispatcher.setTouchHandlers(value: MutableList<TargetTouchHandler>) { this.touchHandlers = value }
internal fun TouchEventManager.getEvent() = this.event
internal fun TouchEventManager.getEventList() = this.eventList
internal fun TouchEventManager.getMaxMoveEventCount() = this.maxMoveEventCount
internal fun TouchEventManager.getMoveEventCount() = this.moveEventCount
internal fun TouchEventManager.getMoveEventList() = this.moveEventList
internal fun TouchEventManager.setEventList(value: MutableList<MotionEvent?>) { this.eventList = value }
internal fun TouchEventManager.setMaxMoveEventCount(value: Int) { this.maxMoveEventCount = value }
internal fun TouchEventManager.setMoveEventCount(value: Int) { this.moveEventCount = value }
internal fun TouchEventManager.setMoveEventList(value: MutableList<MotionEvent?>) { this.moveEventList = value }

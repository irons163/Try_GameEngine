@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.scene

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.RectF
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.example.try_gameengine.Camera.Camera
import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.CommonUtil
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.Data
import com.example.try_gameengine.framework.GameController
import com.example.try_gameengine.framework.GameModel
import com.example.try_gameengine.framework.GameView
import com.example.try_gameengine.framework.IGameController
import com.example.try_gameengine.framework.IGameModel
import com.example.try_gameengine.framework.LayerManager
import com.example.try_gameengine.framework.ProcessBlock
import com.example.try_gameengine.framework.ProcessBlockManager
import com.example.try_gameengine.framework.TouchDispatcher
import com.example.try_gameengine.stage.Stage
import java.lang.reflect.InvocationTargetException
import org.loon.framework.android.game.physics.LBody
import org.loon.framework.android.game.physics.LWorld
import org.loon.framework.android.game.physics.LWorldListener
import org.loon.framework.android.game.physics.RectBox
import org.loon.framework.android.game.physics.WorldBox

internal fun DialogScene.getGameview() = this.gameview
internal fun DialogScene.isNeedToStopTheActiveScene() = this.isNeedToStopTheActiveScene
internal fun DialogScene.setGameview(value: GameView?) { this.gameview = value }
internal fun DialogScene.setNeedToStopTheActiveScene(value: Boolean) { this.isNeedToStopTheActiveScene = value }
internal fun EasyScene.getGravity() = this.gravity
internal fun EasyScene.getLock() = this.lock
internal fun EasyScene.getPaint() = this.paint
internal fun EasyScene.getPhysicsWorld() = this.physicsWorld
internal fun EasyScene.getWorld() = this.world
internal fun EasyScene.isEnablePhysical() = this.isEnablePhysical
internal fun EasyScene.setEnablePhysical(value: Boolean) { this.isEnablePhysical = value }
internal fun EasyScene.setGravity(value: Vector2?) { this.gravity = value }
internal fun EasyScene.setLock(value: ByteArray) { this.lock = value }
internal fun EasyScene.setPaint(value: Paint?) { this.paint = value }
internal fun EasyScene.setPhysicsWorld(value: PhysicsWorld?) { this.physicsWorld = value }
internal fun EasyScene.setWorld(value: LWorld?) { this.world = value }
internal fun MyRect.getAngle() = this.angle
internal fun MyRect.getHeight() = this.height
internal fun MyRect.getType() = this.type
internal fun MyRect.getWidth() = this.width
internal fun MyRect.getX() = this.x
internal fun MyRect.getY() = this.y
internal fun MyRect.setAngle(value: Float) { this.angle = value }
internal fun MyRect.setHeight(value: Float) { this.height = value }
internal fun MyRect.setType(value: EasyScene.Type?) { this.type = value }
internal fun MyRect.setWidth(value: Float) { this.width = value }
internal fun MyRect.setX(value: Float) { this.x = value }
internal fun MyRect.setY(value: Float) { this.y = value }
internal fun PhysicsWorld.getBodyCount() = this.bodyCount
internal fun PhysicsWorld.getBodyList() = this.bodyList
internal fun PhysicsWorld.getBox2DWorld() = this.box2DWorld
internal fun PhysicsWorld.getGravity() = this.gravity
internal fun PhysicsWorld.isAutoStep() = this.isAutoStep
internal fun PhysicsWorld.setGravity(value: Vector2?) { this.gravity = value }
internal fun SceneBuilder.getSceneIndex() = this.sceneIndex
internal fun SceneManager.SceneClassInfo.getContext() = this.context
internal fun SceneManager.SceneClassInfo.getId() = this.id
internal fun SceneManager.SceneClassInfo.getMode() = this.mode
internal fun SceneManager.SceneClassInfo.getSceneLayerLevel() = this.sceneLayerLevel
internal fun SceneManager.SceneClassInfo.setContext(value: Context?) { this.context = value }
internal fun SceneManager.SceneClassInfo.setId(value: String?) { this.id = value }
internal fun SceneManager.SceneClassInfo.setMode(value: Int) { this.mode = value }
internal fun SceneManager.SceneClassInfo.setSceneLayerLevel(value: Int) { this.sceneLayerLevel = value }
internal fun SceneManager.getCurrentActiveScene() = this.currentActiveScene
internal fun SceneManager.getScenes() = this.scenes
internal fun TransformSceneEffect.getCamera() = this.camera
internal fun TransformSceneEffect.setCamera(value: Camera?) { this.camera = value }

@file:Suppress("unused", "FunctionName")
package org.loon.framework.android.game.physics

import android.graphics.Point
import android.graphics.RectF
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.example.try_gameengine.scene.PhysicsWorld

internal fun LBody.getAngle() = this.angle
internal fun LBody.getAngularVelocity() = this.angularVelocity
internal fun LBody.getBox2DBody() = this.box2DBody
internal fun LBody.getLShape() = this.lShape
internal fun LBody.getPosition() = this.position
internal fun LBody.getRotation() = this.rotation
internal fun LBody.getUserData() = this.userData
internal fun LBody.getX() = this.x
internal fun LBody.getXVelocity() = this.xVelocity
internal fun LBody.getY() = this.y
internal fun LBody.getYVelocity() = this.yVelocity
internal fun LBody.isSleeping() = this.isSleeping
internal fun LBody.isStatic() = this.isStatic
internal fun LBody.setAngularVelocity(value: Float) { this.angularVelocity = value }
internal fun LBody.setRotation(value: Float) { this.rotation = value }
internal fun LBody.setUserData(value: Any?) { this.userData = value }
internal fun PolygonDef.getVertexArray() = this.vertexArray
internal fun PolygonDef.getVertexCount() = this.vertexCount
internal fun PolygonDef.getVertexList() = this.vertexList
internal fun PolygonDef.getVertexs() = this.vertexs
internal fun PolygonDef.setVertexList(value: MutableList<Vector2>) { this.vertexList = value }
internal fun RectBox.getArea() = this.area
internal fun RectBox.getCenterX() = this.centerX
internal fun RectBox.getCenterY() = this.centerY
internal fun RectBox.getHeight() = this.height
internal fun RectBox.getMaxX() = this.maxX
internal fun RectBox.getMaxY() = this.maxY
internal fun RectBox.getMiddleX() = this.middleX
internal fun RectBox.getMiddleY() = this.middleY
internal fun RectBox.getMinX() = this.minX
internal fun RectBox.getMinY() = this.minY
internal fun RectBox.getRect() = this.rect
internal fun RectBox.getRectangle2D() = this.rectangle2D
internal fun RectBox.getRight() = this.right
internal fun RectBox.getTop() = this.top
internal fun RectBox.getWidth() = this.width
internal fun RectBox.getX() = this.x
internal fun RectBox.getY() = this.y
internal fun RectBox.setHeight(value: Int) { this.height = value }
internal fun RectBox.setWidth(value: Int) { this.width = value }
internal fun RectBox.setX(value: Int) { this.x = value }
internal fun RectBox.setY(value: Int) { this.y = value }
internal fun WorldBox.getBox() = this.box
internal fun WorldBox.getDensity() = this.density
internal fun WorldBox.getFriction() = this.friction
internal fun WorldBox.getRestitution() = this.restitution
internal fun WorldBox.setBox(value: RectBox?) { this.box = value }
internal fun WorldBox.setDensity(value: Float) { this.density = value }
internal fun WorldBox.setFriction(value: Float) { this.friction = value }
internal fun WorldBox.setRestitution(value: Float) { this.restitution = value }

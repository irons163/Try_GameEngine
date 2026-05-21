@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import org.loon.framework.android.game.physics.LWorld

internal fun PhysicsBody.getBox2DBody() = this.box2DBody
internal fun PhysicsBody.getLShape() = this.lShape
internal fun PhysicsBody.getRotation() = this.rotation
internal fun PhysicsBody.getX() = this.x
internal fun PhysicsBody.getY() = this.y
internal fun PhysicsBody.isStatic() = this.isStatic
internal fun PhysicsBody.setRotation(value: Float) { this.rotation = value }

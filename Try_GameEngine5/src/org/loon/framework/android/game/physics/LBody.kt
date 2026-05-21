package org.loon.framework.android.game.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.example.try_gameengine.scene.PhysicsWorld

/**
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 // */
class LBody {
    var box2DBody: Body? = null
        private set

    private var jboxBodyDef: BodyDef? = null

    var isStatic: Boolean = false
        private set

    private val touching = ArrayList<Body?>()

    private var shape: LShape? = null

    var userData: Any? = null
        get() =//		return this.userData;
            box2DBody!!.getUserData()
        set(value) {
            field = value
        }

    constructor(body: Body?) {
        this.box2DBody = body
    }

    @JvmOverloads
    constructor(shape: LShape, x: Float, y: Float, staticBody: Boolean = false) {
        this.jboxBodyDef = BodyDef()
        this.jboxBodyDef!!.position.set(Vector2(x, y))
        this.isStatic = staticBody
        this.shape = shape
    }

    fun isTouching(other: Body?): Boolean {
        return this.touching.contains(other)
    }

    fun touchCount(other: Body?): Int {
        var count = 0
        for (i in this.touching.indices) {
            if (this.touching.get(i) == other) {
                count++
            }
        }
        return count
    }

    fun touch(other: Body?) {
        this.touching.add(other)
    }

    fun untouch(other: Body?) {
        this.touching.remove(other)
    }

    fun applyForce(x: Float, y: Float) {
        checkBody()
        this.box2DBody!!.applyForce(Vector2(x, y), Vector2(0.0f, 0.0f))
    }

    val x: Float
        get() {
            checkBody()
            return this.box2DBody!!.getPosition().x
        }

    val y: Float
        get() {
            checkBody()
            return this.box2DBody!!.getPosition().y
        }

    var rotation: Float
        get() {
            checkBody()
            return this.box2DBody!!.getAngle()
        }
        set(rotation) {
            checkBody()
            box2DBody!!.setTransform(box2DBody!!.getPosition(), this.box2DBody!!.getAngle())
        }

    val xVelocity: Float
        get() {
            checkBody()
            return this.box2DBody!!.getLinearVelocity().x
        }

    val yVelocity: Float
        get() {
            checkBody()
            return this.box2DBody!!.getLinearVelocity().y
        }

    var angularVelocity: Float
        get() {
            checkBody()
            return this.box2DBody!!.getAngularVelocity()
        }
        set(vel) {
            checkBody()
            this.box2DBody!!.setAngularVelocity(vel)
        }

    fun setRestitution(rest: Float) {
        this.shape!!.setRestitution(rest)
    }

    fun setFriction(f: Float) {
        this.shape!!.setFriction(f)
    }

    fun setDensity(den: Float) {
        this.shape!!.setDensity(den)
    }

    fun addToWorld(world: PhysicsWorld) {
        val jboxWorld = world.box2DWorld
        this.box2DBody = jboxWorld.createBody(this.jboxBodyDef!!)
        this.shape!!.createInBody(this)
        if (!this.isStatic) {
            this.box2DBody!!.setType(BodyType.StaticBody)
        } else {
            this.box2DBody!!.setType(BodyType.KinematicBody)
        }
    }

    fun removeFromWorld(world: LWorld) {
        val jboxWorld = world.box2DWorld
        jboxWorld.destroyBody(this.box2DBody!!)
    }

    fun addToWorld(world: LWorld) {
        val jboxWorld = world.box2DWorld
        this.box2DBody = jboxWorld.createBody(this.jboxBodyDef!!)
        this.shape!!.createInBody(this)
        if (!this.isStatic) {
            this.box2DBody!!.setType(BodyType.StaticBody)
        } else {
            this.box2DBody!!.setType(BodyType.KinematicBody)
        }
    }

    fun removeFromWorld(world: PhysicsWorld) {
        val jboxWorld = world.box2DWorld
        jboxWorld.destroyBody(this.box2DBody!!)
    }

    val lShape: LShape
        get() = this.shape!!

    fun setPosition(x: Float, y: Float) {
        checkBody()
        box2DBody!!.setTransform(Vector2(x, y), this.box2DBody!!.getAngle())
    }

    private fun checkBody() {
        if (this.box2DBody == null) throw RuntimeException("This Box2D-Body is NULL !")
    }

    val isSleeping: Boolean
        get() {
            checkBody()
            return this.box2DBody!!.isSleepingAllowed()
        }

    fun translate(x: Float, y: Float) {
        setPosition(this.x + x, this.y + y)
    }

    fun setDamping(damping: Float) {
        if (this.box2DBody == null) this.jboxBodyDef!!.linearDamping = damping
    }

    fun setVelocity(xVelocity: Float, yVelocity: Float) {
        checkBody()
        val vel = box2DBody!!.getLinearVelocity()
        vel.x = xVelocity
        vel.y = yVelocity
        this.box2DBody!!.setLinearVelocity(vel)
    }

    val position: Vector2
        get() = box2DBody!!.getPosition()

    val angle: Float
        get() = box2DBody!!.getAngle()
}

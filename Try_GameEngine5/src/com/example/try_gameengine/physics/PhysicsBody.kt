package com.example.try_gameengine.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import org.loon.framework.android.game.physics.LWorld

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
class PhysicsBody {
    var box2DBody: Body? = null
        private set

    private var jboxBodyDef: BodyDef? = null

    var isStatic: Boolean = false
        private set

    private val touching = ArrayList<Body?>()

    var lShape: PhysicsShape? = null
        private set

    private var userData: Any? = null

    private var fixtureDef: FixtureDef? = null

    private var fixture: Fixture? = null

    constructor(body: Body?) {
        this.box2DBody = body
    }

    @JvmOverloads
    constructor(shape: PhysicsShape?, x: Float, y: Float, staticBody: Boolean = false) {
        this.jboxBodyDef = BodyDef()
        this.jboxBodyDef!!.position.set(Vector2(x, y))
        this.isStatic = staticBody
        this.lShape = shape
    }

    constructor(bodyDef: BodyDef) {
        this.jboxBodyDef = bodyDef
        this.fixtureDef = FixtureDef()
    }

    constructor(fixtureDef: FixtureDef) {
        this.fixtureDef = fixtureDef
        this.jboxBodyDef = BodyDef()
    }

    fun getUserData(): Any? {
//		return this.userData;
        return box2DBody!!.getUserData()
    }

    fun setUserData(`object`: Any?) {
        if (this.box2DBody == null) {
            this.userData = `object`
        } else {
            this.box2DBody!!.setUserData(userData)
        }
    }

    fun isTouching(other: Body?): Boolean {
        return this.touching.contains(other)
    }

    fun touchCount(other: Body?): Int {
        var count = 0
        for (i in this.touching.indices) {
            if (this.touching.get(i) === other) {
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
        //	public void setPosition(float x, float y) {
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
            if (this.box2DBody == null) {
                jboxBodyDef!!.angularVelocity = vel
            } else {
                this.box2DBody!!.setAngularVelocity(vel)
            }
        }

    //	public void setRestitution(float rest) {
    //		this.shape.setRestitution(rest);
    //	}
    //
    //	public void setFriction(float f) {
    //		this.shape.setFriction(f);
    //	}
    //
    //	public void setDensity(float den) {
    //		this.shape.setDensity(den);
    //	}
    fun addToWorld(world: LWorld) {
        val jboxWorld = world.box2DWorld
        //		this.jboxBody = jboxWorld.createBody(this.jboxBodyDef);
        this.box2DBody = world.createBody(this.jboxBodyDef!!)
        //		this.shape.createInBody(this);
        this.box2DBody!!.createFixture(this.fixtureDef!!)
        this.box2DBody!!.setUserData(userData)
        if (!this.isStatic) {
            this.box2DBody!!.setType(BodyType.StaticBody)
        } else {
            this.box2DBody!!.setType(BodyType.KinematicBody)
        }
    }

    fun addToWorld(world: LWorld, x: Float, y: Float) {
        val jboxWorld = world.box2DWorld
        this.jboxBodyDef!!.position.set(Vector2(x, y))
        this.box2DBody = jboxWorld.createBody(this.jboxBodyDef!!)
        //		this.shape.createInBody(this);
        this.box2DBody!!.createFixture(this.fixtureDef!!)
        this.box2DBody!!.setUserData(userData)
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

    val position: Vector2?
        //	public void setVelocity(float xVelocity, float yVelocity) {
        get() = box2DBody!!.getPosition()

    val angle: Float
        get() = box2DBody!!.getAngle()

    fun setFixtureDef(fixtureDef: FixtureDef) {
        this.fixtureDef = fixtureDef
    }

    fun setDynamic(dynamic: Boolean) {
        if (this.box2DBody == null) {
            jboxBodyDef!!.type = if (dynamic) BodyType.DynamicBody else BodyType.StaticBody
        } else {
            box2DBody!!.setType(if (dynamic) BodyType.DynamicBody else BodyType.StaticBody)
        }
    }

    fun setFriction(friction: Float) {
        if (this.fixture == null) {
//			this.fixtureDef = new FixtureDef();
            this.fixtureDef!!.friction = friction
        } else {
            this.fixture!!.setFriction(friction)
        }
    }

    fun setRestitution(rest: Float) {
        if (this.fixture == null) {
            this.fixtureDef!!.restitution = rest
        } else {
            this.fixture!!.setRestitution(rest)
        }
    }

    fun setDensity(den: Float) {
        if (this.fixture == null) {
            this.fixtureDef!!.density = den
        } else {
            this.fixture!!.setDensity(den)
        }
    }

    fun setVelocity(xVelocity: Float, yVelocity: Float) {
        if (this.box2DBody == null) {
            jboxBodyDef!!.linearVelocity.x = xVelocity
            jboxBodyDef!!.linearVelocity.y = yVelocity
        } else {
            val vel = box2DBody!!.getLinearVelocity()
            vel.x = xVelocity
            vel.y = yVelocity
            this.box2DBody!!.setLinearVelocity(vel)
        }
    }

    fun setPosition(x: Float, y: Float) {
        if (this.box2DBody == null) {
            jboxBodyDef!!.position.x = x
            jboxBodyDef!!.position.y = y
        } else {
            box2DBody!!.setTransform(Vector2(x, y), this.box2DBody!!.getAngle())
        }
    }
}

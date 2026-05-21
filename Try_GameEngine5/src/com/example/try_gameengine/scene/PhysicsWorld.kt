package com.example.try_gameengine.scene

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import org.loon.framework.android.game.physics.LBody
import org.loon.framework.android.game.physics.LWorldListener
import org.loon.framework.android.game.physics.RectBox
import org.loon.framework.android.game.physics.WorldBox

class PhysicsWorld {
    private var jboxWorld: World? = null
    val bodyList: ArrayList<LBody?> = ArrayList<LBody?>()
    private val shapeMap = HashMap<FixtureDef?, LBody?>()
    private val listeners = ArrayList<LWorldListener?>()
    private var iterations = 0
    var gravity: Vector2? = null
    private var worldBox: WorldBox? = null

    constructor(scene: Scene?)

    constructor(
        gx: Float, gy: Float, width: Int, height: Int, doSleep: Boolean,
        iterations: Float
    ) {
        this.iterations = 10
        this.jboxWorld = World(Vector2(gx, gy).also { this.gravity = it }, true)
        this.worldBox = WorldBox(
            jboxWorld!!,
            RectBox(0, 0, width, height)
        )
    }

    val isAutoStep: Boolean
        get() =// TODO Auto-generated method stub
            false

    fun update(timeStep: Float) {
        this.jboxWorld!!.setContinuousPhysics(true)
        this.jboxWorld!!.setWarmStarting(true)
        this.jboxWorld!!.step(timeStep, this.iterations, this.iterations)
    }

    fun add(body: LBody) {
        body.addToWorld(this)
        val shapes = body.lShape.getBox2DFixtures() ?: return
        for (i in shapes.indices) {
            this.shapeMap.put(shapes.get(i), body)
        }
        this.bodyList.add(body)
    }

    fun remove(body: LBody) {
        val shapes = body.lShape.getBox2DFixtures() ?: return
        for (i in shapes.indices) {
            this.shapeMap.remove(shapes.get(i))
        }
        body.removeFromWorld(this)
        this.bodyList.remove(body)
    }

    val bodyCount: Int
        get() = this.bodyList.size

    fun getLBody(index: Int): LBody? {
        return this.bodyList.get(index)
    }

    fun setContactListener(listener: ContactListener?) {
        jboxWorld!!.setContactListener(listener)
    }

    fun destroyBody(body: Body) {
        jboxWorld!!.destroyBody(body)
    }

    fun destroyBody(body: LBody) {
        jboxWorld!!.destroyBody(body.box2DBody!!)
    }

    fun createBody(def: BodyDef): Body? {
        val body = jboxWorld!!.createBody(def)
        val lBody = LBody(body)
        bodyList.add(lBody)
        return body
    }

    fun addListener(listener: LWorldListener?) {
        this.listeners.add(listener)
    }

    fun removeListener(listener: LWorldListener?) {
        this.listeners.remove(listener)
    }

    val box2DWorld: World
        get() = this.jboxWorld!!

    fun getWorldBox(): RectBox? {
        return worldBox!!.box
    }

    fun setWorldBox(box: RectBox?) {
        this.worldBox!!.box = box
    }

    fun setWorldBox(w: Int, h: Int) {
        this.worldBox!!.box = RectBox(0, 0, w, h)
    }

    fun step(
        timeStep: Float, velocityIterations: Int,
        positionIterations: Int
    ) {
        jboxWorld!!.step(timeStep, velocityIterations, positionIterations)
    }
}

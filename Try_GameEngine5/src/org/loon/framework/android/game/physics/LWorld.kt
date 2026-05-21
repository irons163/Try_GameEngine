package org.loon.framework.android.game.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World

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
class LWorld(
    gx: Float, gy: Float, width: Int, height: Int, doSleep: Boolean,
    iterations: Float
) {
    val box2DWorld: World

    val bodyList: ArrayList<LBody?> = ArrayList<LBody?>()

    private val shapeMap = HashMap<FixtureDef?, LBody?>()

    private val listeners = ArrayList<LWorldListener?>()

    private val iterations = 10

    var gravity: Vector2? = null

    private val worldBox: WorldBox

    init {
        this.box2DWorld = World(Vector2(gx, gy).also { this.gravity = it }, true)
        this.worldBox = WorldBox(
            this.box2DWorld,
            RectBox(0, 0, width, height)
        )
    }

    fun add(body: LBody) {
        body.addToWorld(this)
        val shapes = body.getLShape().getBox2DFixtures()!!
        for (i in shapes.indices) {
            this.shapeMap.put(shapes.get(i), body)
        }
        this.bodyList.add(body)
    }

    fun remove(body: LBody) {
        val shapes = body.getLShape().getBox2DFixtures()!!
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

    fun update(timeStep: Float) {
        this.box2DWorld.setContinuousPhysics(true)
        this.box2DWorld.setWarmStarting(true)
        this.box2DWorld.step(timeStep, this.iterations, this.iterations)
    }

    fun setContactListener(listener: ContactListener?) {
        box2DWorld.setContactListener(listener)
    }

    fun destroyBody(body: Body) {
        box2DWorld.destroyBody(body)
    }

    fun destroyBody(body: LBody) {
        box2DWorld.destroyBody(body.getBox2DBody()!!)
    }

    fun createBody(def: BodyDef): Body {
        val body = box2DWorld.createBody(def)
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

    fun getWorldBox(): RectBox? {
        return worldBox.getBox()
    }

    fun setWorldBox(box: RectBox?) {
        this.worldBox.setBox(box)
    }

    fun setWorldBox(w: Int, h: Int) {
        this.worldBox.setBox(RectBox(0, 0, w, h))
    }

    fun step(
        timeStep: Float, velocityIterations: Int,
        positionIterations: Int
    ) {
        box2DWorld.step(timeStep, velocityIterations, positionIterations)
    }
}

package org.loon.framework.android.game.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
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
class WorldBox(private val world: World, box: RectBox) {
    private var northBody: Body? = null
    private var southBody: Body? = null
    private var eastBody: Body? = null
    private var westBody: Body? = null

    private var worldBox: RectBox? = null

    private val init: Boolean

    var density: Float = 0f
    var friction: Float
    var restitution: Float = 0f

    /**
     * 这是一个物理世界范围用类，用以取代JBox2D中的AABB(gdx未提供)
     * 
     * @param world
     * @param box
     // */
    init {
        this.worldBox = box
        this.init = true
        this.friction = 0.0f
    }

    @Synchronized
    fun remove() {
        if (init) {
            world.destroyBody(northBody!!)
            world.destroyBody(southBody!!)
            world.destroyBody(eastBody!!)
            world.destroyBody(westBody!!)
        }
    }

    var box: RectBox?
        get() = worldBox
        set(box) {
            if (init) {
                remove()
            }
            val eastWestShape =
                PolygonShape()
            eastWestShape.setAsBox(1.0f, box!!.getHeight().toFloat())

            val northSouthShape =
                PolygonShape()
            northSouthShape.setAsBox(box.getWidth().toFloat(), 0.0f)

            val northDef = BodyDef()
            northDef.type = BodyType.StaticBody
            northDef.position.set(Vector2(0f, 0f))
            northBody = world.createBody(northDef)
            val northFixture = FixtureDef()
            northFixture.shape = northSouthShape
            northFixture.density = density
            northFixture.friction = friction
            northFixture.restitution = restitution
            northBody!!.createFixture(northFixture)

            val southDef = BodyDef()
            southDef.type = BodyType.StaticBody
            southDef.position.set(Vector2(0f, box.getHeight().toFloat()))
            southBody = world.createBody(southDef)
            val southFixture = FixtureDef()
            southFixture.shape = northSouthShape
            southFixture.density = density
            southFixture.friction = friction
            southFixture.restitution = restitution
            southBody!!.createFixture(southFixture)

            val eastDef = BodyDef()
            eastDef.type = BodyType.StaticBody
            eastDef.position.set(Vector2(box.getWidth().toFloat(), 0f))
            eastBody = world.createBody(eastDef)
            val eastFixture = FixtureDef()
            eastFixture.shape = eastWestShape
            eastFixture.density = density
            eastFixture.friction = friction
            eastFixture.restitution = restitution
            eastBody!!.createFixture(eastFixture)

            val westDef = BodyDef()
            westDef.type = BodyType.StaticBody
            westDef.position.set(Vector2(0f, 0f))
            westBody = world.createBody(westDef)
            val westFixture = FixtureDef()
            westFixture.density = density
            westFixture.friction = friction
            westFixture.restitution = restitution
            westFixture.shape = eastWestShape
            westBody!!.createFixture(westFixture)

            eastWestShape.dispose()
            northSouthShape.dispose()

            this.worldBox = box
        }

    fun getEastBody(): Body {
        return eastBody!!
    }

    fun setEastBody(eastBody: Body) {
        this.eastBody = eastBody
    }

    fun getNorthBody(): Body {
        return northBody!!
    }

    fun setNorthBody(northBody: Body) {
        this.northBody = northBody
    }

    fun getSouthBody(): Body {
        return southBody!!
    }

    fun setSouthBody(southBody: Body) {
        this.southBody = southBody
    }

    fun getWestBody(): Body {
        return westBody!!
    }

    fun setWestBody(westBody: Body) {
        this.westBody = westBody
    }
}

package org.loon.framework.android.game.physics

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef

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
abstract class PrimitiveShape protected constructor(protected var def: PolygonDef) : LShape {
    protected var jboxFixtures: ArrayList<FixtureDef?> = ArrayList<FixtureDef?>()

    protected var jboxFixture: Fixture? = null

    protected var body: LBody? = null

    override fun createInBody(body: LBody?) {
        this.body = body
        this.jboxFixture = body!!.getBox2DBody()!!.createShape(this.def)
        this.jboxFixtures.add(this.def)
    }

    override fun getBox2DFixtures(): ArrayList<FixtureDef?> {
        return this.jboxFixtures
    }

    override fun getLBody(): LBody? {
        return this.body
    }

    override fun setDensity(density: Float) {
        if (this.jboxFixture == null) {
            this.def.density = density
        } else {
            this.jboxFixture!!.setDensity(density)
        }
    }

    override fun setFriction(friction: Float) {
        if (this.jboxFixture == null) {
            this.def.friction = friction
        } else {
            this.jboxFixture!!.setFriction(friction)
        }
    }

    override fun setRestitution(rest: Float) {
        if (this.jboxFixture == null) {
            this.def.restitution = rest
        } else {
            this.jboxFixture!!.setRestitution(rest)
        }
    }
}

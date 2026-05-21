package org.loon.framework.android.game.physics

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
class Rectangle constructor(
    width: Float, height: Float, density: Float = 0f,
    restitution: Float = 0f, friction: Float = 0f
) : PolygonBasedShape() {
    val width: Float

    val height: Float

    constructor(width: Float, height: Float, density: Float, resitution: Float) : this(
        width,
        height,
        density,
        resitution,
        0f
    )

    init {
        this.def.setAsBox(width / 2, height / 2)
        this.def.density = density
        this.def.restitution = restitution
        this.def.friction = friction
        this.width = width
        this.height = height
    }

    override fun applyOffset(x: Float, y: Float) {
        this.def.setAsBox(this.width / 2.0f, this.height / 2.0f)
    }
}

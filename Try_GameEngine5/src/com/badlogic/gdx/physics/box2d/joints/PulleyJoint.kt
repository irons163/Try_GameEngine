/*******************************************************************************
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 // */
package com.badlogic.gdx.physics.box2d.joints

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World

/**
 * The pulley joint is connected to two bodies and two fixed ground points. The pulley supports a ratio such that: length1 + ratio
 * * length2 <= constant Yes, the force transmitted is scaled by the ratio. The pulley also enforces a maximum length limit on
 * both sides. This is useful to prevent one side of the pulley hitting the top.
 // */
class PulleyJoint(world: World?, addr: Long) : Joint(world, addr) {
    /**
     * Get the first ground anchor.
     // */
    private val tmp = FloatArray(2)
    private val groundAnchorA = Vector2()

    fun getGroundAnchorA(): Vector2 {
        jniGetGroundAnchorA(addr, tmp)
        groundAnchorA.set(tmp[0], tmp[1])
        return groundAnchorA
    }

    private external fun jniGetGroundAnchorA(addr: Long, anchor: FloatArray?)

    /**
     * Get the second ground anchor.
     // */
    private val groundAnchorB = Vector2()

    fun getGroundAnchorB(): Vector2 {
        jniGetGroundAnchorB(addr, tmp)
        groundAnchorB.set(tmp[0], tmp[1])
        return groundAnchorB
    }

    private external fun jniGetGroundAnchorB(addr: Long, anchor: FloatArray?)

    /**
     * Get the current length of the segment attached to body1.
     // */
    fun getLength1(): Float {
        return jniGetLength1(addr)
    }

    private external fun jniGetLength1(addr: Long): Float

    /**
     * Get the current length of the segment attached to body2.
     // */
    fun getLength2(): Float {
        return jniGetLength2(addr)
    }

    private external fun jniGetLength2(addr: Long): Float

    /**
     * Get the pulley ratio.
     // */
    fun getRatio(): Float {
        return jniGetRatio(addr)
    }

    private external fun jniGetRatio(addr: Long): Float
}

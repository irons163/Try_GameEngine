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
 * A mouse joint is used to make a point on a body track a specified world point. This a soft constraint with a maximum force.
 * This allows the constraint to stretch and without applying huge forces. NOTE: this joint is not documented in the manual
 * because it was developed to be used in the testbed. If you want to learn how to use the mouse joint, look at the testbed.
 // */
class MouseJoint(world: World?, addr: Long) : Joint(world, addr) {
    /**
     * Use this to update the target point.
     // */
    fun setTarget(target: Vector2) {
        jniSetTarget(addr, target.x, target.y)
    }

    private external fun jniSetTarget(addr: Long, x: Float, y: Float)

    /**
     * Use this to update the target point.
     // */
    val tmp: FloatArray = FloatArray(2)
    private val target = Vector2()

    fun getTarget(): Vector2 {
        jniGetTarget(addr, tmp)
        target.x = tmp[0]
        target.y = tmp[1]
        return target
    }

    private external fun jniGetTarget(addr: Long, target: FloatArray?)

    /**
     * Set/get the maximum force in Newtons.
     // */
    fun setMaxForce(force: Float) {
        jniSetMaxForce(addr, force)
    }

    private external fun jniSetMaxForce(addr: Long, force: Float)

    /**
     * Set/get the maximum force in Newtons.
     // */
    fun getMaxForce(): Float {
        return jniGetMaxForce(addr)
    }

    private external fun jniGetMaxForce(addr: Long): Float

    /**
     * Set/get the frequency in Hertz.
     // */
    fun setFrequency(hz: Float) {
        jniSetFrequency(addr, hz)
    }

    private external fun jniSetFrequency(addr: Long, hz: Float)

    /**
     * Set/get the frequency in Hertz.
     // */
    fun getFrequency(): Float {
        return jniGetFrequency(addr)
    }

    private external fun jniGetFrequency(addr: Long): Float

    /**
     * Set/get the damping ratio (dimensionless).
     // */
    fun setDampingRatio(ratio: Float) {
        jniSetDampingRatio(addr, ratio)
    }

    private external fun jniSetDampingRatio(addr: Long, ratio: Float)

    /**
     * Set/get the damping ratio (dimensionless).
     // */
    fun getDampingRatio(): Float {
        return jniGetDampingRatio(addr)
    }

    private external fun jniGetDampingRatio(addr: Long): Float
}

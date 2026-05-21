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

import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World

/**
 * A distance joint constrains two points on two bodies to remain at a fixed distance from each other. You can view this as a
 * massless, rigid rod.
 // */
class DistanceJoint(world: World?, addr: Long) : Joint(world, addr) {
    /**
     * Set/get the natural length. Manipulating the length can lead to non-physical behavior when the frequency is zero.
     // */
    fun setLength(length: Float) {
        jniSetLength(addr, length)
    }

    private external fun jniSetLength(addr: Long, length: Float)

    /**
     * Set/get the natural length. Manipulating the length can lead to non-physical behavior when the frequency is zero.
     // */
    fun getLength(): Float {
        return jniGetLength(addr)
    }

    private external fun jniGetLength(addr: Long): Float

    /**
     * Set/get frequency in Hz.
     // */
    fun setFrequency(hz: Float) {
        jniSetFrequency(addr, hz)
    }

    private external fun jniSetFrequency(addr: Long, hz: Float)

    /**
     * Set/get frequency in Hz.
     // */
    fun getFrequency(): Float {
        return jniGetFrequency(addr)
    }

    private external fun jniGetFrequency(addr: Long): Float

    /**
     * Set/get damping ratio.
     // */
    fun setDampingRatio(ratio: Float) {
        jniSetDampingRatio(addr, ratio)
    }

    private external fun jniSetDampingRatio(addr: Long, ratio: Float)

    /**
     * Set/get damping ratio.
     // */
    fun getDampingRatio(): Float {
        return jniGetDampingRatio(addr)
    }

    private external fun jniGetDampingRatio(addr: Long): Float
}

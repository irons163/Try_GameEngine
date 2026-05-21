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
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.JointDef

/**
 * Friction joint definition.
 // */
class FrictionJointDef : JointDef() {
    /**
     * Initialize the bodies, anchors, axis, and reference angle using the world anchor and world axis.
     // */
    fun initialize(bodyA: Body, bodyB: Body, anchor: Vector2) {
        this.bodyA = bodyA
        this.bodyB = bodyB
        localAnchorA.set(bodyA.getLocalPoint(anchor))
        localAnchorB.set(bodyB.getLocalPoint(anchor))
    }

    /**
     * The local anchor point relative to bodyA's origin.
     // */
    val localAnchorA: Vector2 = Vector2()

    /**
     * The local anchor point relative to bodyB's origin.
     // */
    val localAnchorB: Vector2 = Vector2()

    /**
     * The maximum friction force in N.
     // */
    var maxForce: Float = 0f

    /**
     * The maximum friction torque in N-m.
     // */
    var maxTorque: Float = 0f

    init {
        type = JointType.FrictionJoint
    }
}

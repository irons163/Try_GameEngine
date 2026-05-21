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
 * Distance joint definition. This requires defining an anchor point on both bodies and the non-zero length of the distance joint.
 * The definition uses local anchor points so that the initial configuration can violate the constraint slightly. This helps when
 * saving and loading a game.
 * @warning Do not use a zero or short length.
 // */
class DistanceJointDef : JointDef() {
    /**
     * Initialize the bodies, anchors, and length using the world anchors.
     // */
    fun initialize(bodyA: Body, bodyB: Body, anchorA: Vector2, anchorB: Vector2) {
        this.bodyA = bodyA
        this.bodyB = bodyB
        this.localAnchorA.set(bodyA.getLocalPoint(anchorA))
        this.localAnchorB.set(bodyB.getLocalPoint(anchorB))
        this.length = anchorA.dst(anchorB)
    }

    /**
     * The local anchor point relative to body1's origin.
     // */
    val localAnchorA: Vector2 = Vector2()

    /**
     * The local anchor point relative to body2's origin.
     // */
    val localAnchorB: Vector2 = Vector2()

    /**
     * The natural length between the anchor points.
     // */
    var length: Float = 1f

    /**
     * The mass-spring-damper frequency in Hertz.
     // */
    var frequencyHz: Float = 0f

    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     // */
    var dampingRatio: Float = 0f

    init {
        type = JointType.DistanceJoint
    }
}

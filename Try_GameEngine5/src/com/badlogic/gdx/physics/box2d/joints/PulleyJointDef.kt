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
 * Pulley joint definition. This requires two ground anchors, two dynamic body anchor points, max lengths for each side, and a
 * pulley ratio.
 // */
class PulleyJointDef : JointDef() {
    /**
     * Initialize the bodies, anchors, lengths, max lengths, and ratio using the world anchors.
     // */
    fun initialize(
        bodyA: Body, bodyB: Body, groundAnchorA: Vector2, groundAnchorB: Vector2, anchorA: Vector2,
        anchorB: Vector2, ratio: Float
    ) {
        this.bodyA = bodyA
        this.bodyB = bodyB
        this.groundAnchorA.set(groundAnchorA)
        this.groundAnchorB.set(groundAnchorB)
        this.localAnchorA.set(bodyA.getLocalPoint(anchorA))
        this.localAnchorB.set(bodyB.getLocalPoint(anchorB))
        lengthA = anchorA.dst(groundAnchorA)
        lengthB = anchorB.dst(groundAnchorB)
        this.ratio = ratio
        val C = lengthA + ratio * lengthB
        maxLengthA = C - ratio * minPulleyLength
        maxLengthB = (C - minPulleyLength) / ratio
    }

    /**
     * The first ground anchor in world coordinates. This point never moves.
     // */
    val groundAnchorA: Vector2 = Vector2(-1f, 1f)

    /**
     * The second ground anchor in world coordinates. This point never moves.
     // */
    val groundAnchorB: Vector2 = Vector2(1f, 1f)

    /**
     * The local anchor point relative to bodyA's origin.
     // */
    val localAnchorA: Vector2 = Vector2(-1f, 0f)

    /**
     * The local anchor point relative to bodyB's origin.
     // */
    val localAnchorB: Vector2 = Vector2(1f, 0f)

    /**
     * The a reference length for the segment attached to bodyA.
     // */
    var lengthA: Float = 0f

    /**
     * The maximum length of the segment attached to bodyA.
     // */
    var maxLengthA: Float = 0f

    /**
     * The a reference length for the segment attached to bodyB.
     // */
    var lengthB: Float = 0f

    /**
     * The maximum length of the segment attached to bodyB.
     // */
    var maxLengthB: Float = 0f

    /**
     * The pulley ratio, used to simulate a block-and-tackle.
     // */
    var ratio: Float = 1f

    init {
        type = JointType.PulleyJoint
        collideConnected = true
    }

    companion object {
        private const val minPulleyLength = 2.0f
    }
}

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

class WeldJointDef : JointDef() {
    // / Initialize the bodies, anchors, and reference angle using a world
    // / anchor point.
    fun initialize(body1: Body, body2: Body, anchor: Vector2) {
        this.bodyA = body1
        this.bodyB = body2
        this.localAnchorA.set(body1.getLocalPoint(anchor))
        this.localAnchorB.set(body2.getLocalPoint(anchor))
        referenceAngle = body2.getAngle() - body1.getAngle()
    }

    // / The local anchor point relative to body1's origin.
    val localAnchorA: Vector2 = Vector2()

    // / The local anchor point relative to body2's origin.
    val localAnchorB: Vector2 = Vector2()

    // / The body2 angle minus body1 angle in the reference state (radians).
    var referenceAngle: Float = 0f

    init {
        type = JointType.WeldJoint
    }
}

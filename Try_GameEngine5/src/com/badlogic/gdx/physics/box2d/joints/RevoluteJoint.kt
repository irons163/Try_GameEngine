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
 * A revolute joint constrains two bodies to share a common point while they are free to rotate about the point. The relative
 * rotation about the shared point is the joint angle. You can limit the relative rotation with a joint limit that specifies a
 * lower and upper angle. You can use a motor to drive the relative rotation about the shared point. A maximum motor torque is
 * provided so that infinite forces are not generated.
 // */
class RevoluteJoint(world: World?, addr: Long) : Joint(world, addr) {
    /**
     * Get the current joint angle in radians.
     // */
    fun getJointAngle(): Float {
        return jniGetJointAngle(addr)
    }

    private external fun jniGetJointAngle(addr: Long): Float

    /**
     * Get the current joint angle speed in radians per second.
     // */
    fun getJointSpeed(): Float {
        return jniGetJointSpeed(addr)
    }

    private external fun jniGetJointSpeed(addr: Long): Float

    /**
     * Is the joint limit enabled?
     // */
    fun isLimitEnabled(): Boolean {
        return jniIsLimitEnabled(addr)
    }

    private external fun jniIsLimitEnabled(addr: Long): Boolean

    /**
     * Enable/disable the joint limit.
     // */
    fun enableLimit(flag: Boolean) {
        jniEnableLimit(addr, flag)
    }

    private external fun jniEnableLimit(addr: Long, flag: Boolean)

    /**
     * Get the lower joint limit in radians.
     // */
    fun getLowerLimit(): Float {
        return jniGetLowerLimit(addr)
    }

    private external fun jniGetLowerLimit(addr: Long): Float

    /**
     * Get the upper joint limit in radians.
     // */
    fun getUpperLimit(): Float {
        return jniGetUpperLimit(addr)
    }

    private external fun jniGetUpperLimit(addr: Long): Float

    /**
     * Set the joint limits in radians.
     * @param upper
     // */
    fun setLimits(lower: Float, upper: Float) {
        jniSetLimits(addr, lower, upper)
    }

    private external fun jniSetLimits(addr: Long, lower: Float, upper: Float)

    /**
     * Is the joint motor enabled?
     // */
    fun isMotorEnabled(): Boolean {
        return jniIsMotorEnabled(addr)
    }

    private external fun jniIsMotorEnabled(addr: Long): Boolean

    /**
     * Enable/disable the joint motor.
     // */
    fun enableMotor(flag: Boolean) {
        jniEnableMotor(addr, flag)
    }

    private external fun jniEnableMotor(addr: Long, flag: Boolean)

    /**
     * Set the motor speed in radians per second.
     // */
    fun setMotorSpeed(speed: Float) {
        jniSetMotorSpeed(addr, speed)
    }

    private external fun jniSetMotorSpeed(addr: Long, speed: Float)

    /**
     * Get the motor speed in radians per second.
     // */
    fun getMotorSpeed(): Float {
        return jniGetMotorSpeed(addr)
    }

    private external fun jniGetMotorSpeed(addr: Long): Float

    /**
     * Set the maximum motor torque, usually in N-m.
     // */
    fun setMaxMotorTorque(torque: Float) {
        jniSetMaxMotorTorque(addr, torque)
    }

    private external fun jniSetMaxMotorTorque(addr: Long, torque: Float)

    /**
     * Get the current motor torque, usually in N-m.
     // */
    fun getMotorTorque(): Float {
        return jniGetMotorTorque(addr)
    }

    private external fun jniGetMotorTorque(addr: Long): Float
}

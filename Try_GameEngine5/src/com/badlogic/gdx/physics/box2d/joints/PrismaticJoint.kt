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
 * A prismatic joint. This joint provides one degree of freedom: translation along an axis fixed in body1. Relative rotation is
 * prevented. You can use a joint limit to restrict the range of motion and a joint motor to drive the motion or to model joint
 * friction.
 // */
class PrismaticJoint(world: World?, addr: Long) : Joint(world, addr) {
    /**
     * Get the current joint translation, usually in meters.
     // */
    fun getJointTranslation(): Float {
        return jniGetJointTranslation(addr)
    }

    private external fun jniGetJointTranslation(addr: Long): Float

    /**
     * Get the current joint translation speed, usually in meters per second.
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
     * Get the lower joint limit, usually in meters.
     // */
    fun getLowerLimit(): Float {
        return jniGetLowerLimit(addr)
    }

    private external fun jniGetLowerLimit(addr: Long): Float

    /**
     * Get the upper joint limit, usually in meters.
     // */
    fun getUpperLimit(): Float {
        return jniGetUpperLimit(addr)
    }

    private external fun jniGetUpperLimit(addr: Long): Float

    /**
     * Set the joint limits, usually in meters.
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
     * Set the motor speed, usually in meters per second.
     // */
    fun setMotorSpeed(speed: Float) {
        jniSetMotorSpeed(addr, speed)
    }

    private external fun jniSetMotorSpeed(addr: Long, speed: Float)

    /**
     * Get the motor speed, usually in meters per second.
     // */
    fun getMotorSpeed(): Float {
        return jniGetMotorSpeed(addr)
    }

    private external fun jniGetMotorSpeed(addr: Long): Float

    /**
     * Set the maximum motor force, usually in N.
     // */
    fun setMaxMotorForce(force: Float) {
        jniSetMaxMotorForce(addr, force)
    }

    private external fun jniSetMaxMotorForce(addr: Long, force: Float)

    /**
     * Get the current motor force, usually in N.
     // */
    fun getMotorForce(): Float {
        return jniGetMotorForce(addr)
    }

    private external fun jniGetMotorForce(addr: Long): Float
}

package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

/**
 * A body definition holds all the data needed to construct a rigid body.
 * You can safely re-use body definitions. Shapes are added to a body after construction.
 * 
 * @author mzechner
 // */
open class BodyDef {
    /**
     * The body type.
     * static: zero mass, zero velocity, may be manually moved
     * kinematic: zero mass, non-zero velocity set by user, moved by solver
     * dynamic: positive mass, non-zero velocity determined by forces, moved by solver
     // */
    enum class BodyType
        (value: Int) {
        StaticBody(0),
        KinematicBody(1),
        DynamicBody(2);

        private val value: Int

        init {
            this.value = value
        }

        fun getValue(): Int {
            return value
        }
    }

    /** The body type: static, kinematic, or dynamic.
     * Note: if a dynamic body would have zero mass, the mass is set to one.  */
    var type: BodyType? = null

    /** The world position of the body. Avoid creating bodies at the origin
     * since this can lead to many overlapping shapes.  */
    val position: Vector2 = Vector2()

    /** The world angle of the body in radians.  */
    var angle: Float = 0f

    /** The linear velocity of the body's origin in world co-ordinates.  */
    val linearVelocity: Vector2 = Vector2()

    /** The angular velocity of the body.  */
    var angularVelocity: Float = 0f

    /** Linear damping is use to reduce the linear velocity. The damping parameter
     * can be larger than 1.0f but the damping effect becomes sensitive to the
     * time step when the damping parameter is large.  */
    var linearDamping: Float = 0f

    /** Angular damping is use to reduce the angular velocity. The damping parameter
     * can be larger than 1.0f but the damping effect becomes sensitive to the
     * time step when the damping parameter is large.  */
    var angularDamping: Float = 0f

    /** Set this flag to false if this body should never fall asleep. Note that
     * this increases CPU usage.  */
    var allowSleep: Boolean = true

    /** Is this body initially awake or sleeping?  */
    var awake: Boolean = true

    /** Should this body be prevented from rotating? Useful for characters.  */
    var fixedRotation: Boolean = false

    /** Is this a fast moving body that should be prevented from tunneling through
     * other moving bodies? Note that all bodies are prevented from tunneling through
     * kinematic and static bodies. This setting is only considered on dynamic bodies.
     * @warning You should use this flag sparingly since it increases processing time.
     // */
    var bullet: Boolean = false

    /** Does this body start out active?  */
    var active: Boolean = true

    /** Experimental: scales the inertia tensor.  */
    var inertiaScale: Float = 1f
}

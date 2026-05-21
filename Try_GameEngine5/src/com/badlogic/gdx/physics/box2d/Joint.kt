package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.JointDef.JointType

abstract class Joint
protected constructor(world: World?, addr: Long) {
    /** the address of the joint  */
    var addr: Long

    /** world  */
    private val world: World

    /** temporary float array  */
    private val tmp = FloatArray(2)

    /** joint edge a  */
    var jointEdgeA: JointEdge? = null

    /** joint edge b  */
    var jointEdgeB: JointEdge? = null

    /**
     * Get the type of the concrete joint.
     // */
    fun getType(): JointType? {
        val type = jniGetType(addr)
        for (i in JointType.entries.toTypedArray().indices) if (JointType.entries[i].getValue() == type) return JointType.entries[i]
        return JointType.Unknown
    }

    private external fun jniGetType(addr: Long): Int

    /**
     * Get the first body attached to this joint.
     // */
    fun getBodyA(): Body? {
        return world.bodies.get(jniGetBodyA(addr))
    }

    private external fun jniGetBodyA(addr: Long): Long

    /**
     * Get the second body attached to this joint.
     // */
    fun getBodyB(): Body? {
        return world.bodies.get(jniGetBodyB(addr))
    }

    private external fun jniGetBodyB(addr: Long): Long

    /**
     * Get the anchor point on bodyA in world coordinates.
     // */
    private val anchorA = Vector2()
    fun getAnchorA(): Vector2 {
        jniGetAnchorA(addr, tmp)
        anchorA.x = tmp[0]
        anchorA.y = tmp[1]
        return anchorA
    }

    private external fun jniGetAnchorA(addr: Long, anchorA: FloatArray?)

    /**
     * Get the anchor point on bodyB in world coordinates.
     // */
    private val anchorB = Vector2()
    fun getAnchorB(): Vector2 {
        jniGetAnchorB(addr, tmp)
        anchorB.x = tmp[0]
        anchorB.y = tmp[1]
        return anchorB
    }

    private external fun jniGetAnchorB(addr: Long, anchorB: FloatArray?)

    /**
     * Get the reaction force on body2 at the joint anchor in Newtons.
     // */
    private val reactionForce = Vector2()

    /**
     * Constructs a new joint
     * @param addr the address of the joint
     // */
    init {
        this.world = world!!
        this.addr = addr
    }

    fun getReactionForce(inv_dt: Float): Vector2 {
        jniGetReactionForce(addr, inv_dt, tmp)
        reactionForce.x = tmp[0]
        reactionForce.y = tmp[1]
        return reactionForce
    }

    private external fun jniGetReactionForce(addr: Long, inv_dt: Float, reactionForce: FloatArray?)

    /**
     * Get the reaction torque on body2 in N*m.
     // */
    fun getReactionTorque(inv_dt: Float): Float {
        return jniGetReactionTorque(addr, inv_dt)
    }

    private external fun jniGetReactionTorque(addr: Long, inv_dt: Float): Float

    //	/// Get the next joint the world joint list.
    //	b2Joint* GetNext();
    //
    //	/// Get the user data pointer.
    //	void* GetUserData() const;
    //
    //	/// Set the user data pointer.
    //	void SetUserData(void* data);
    /**
     * Short-cut function to determine if either body is inactive.
     // */
    fun isActive(): Boolean {
        return jniIsActive(addr)
    }

    private external fun jniIsActive(addr: Long): Boolean
}

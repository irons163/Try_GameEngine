package com.badlogic.gdx.physics.box2d

open class JointDef {
    enum class JointType
        (value: Int) {
        RevoluteJoint(0),
        PrismaticJoint(1),
        DistanceJoint(2),
        PulleyJoint(3),
        MouseJoint(4),
        GearJoint(5),
        LineJoint(6),
        WeldJoint(7),
        FrictionJoint(8),
        Unknown(9);

        private val value: Int

        init {
            this.value = value
        }

        fun getValue(): Int {
            return value
        }
    }

    /** The joint type is set automatically for concrete joint types.  */
    var type: JointType = JointType.Unknown

    /** The first attached body.  */
    var bodyA: Body? = null

    /** The second attached body  */
    var bodyB: Body? = null

    /** Set this flag to true if the attached bodies should collide.  */
    var collideConnected: Boolean = false
}

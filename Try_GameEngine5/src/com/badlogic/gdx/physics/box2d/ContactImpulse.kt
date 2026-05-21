package com.badlogic.gdx.physics.box2d

class ContactImpulse protected constructor(world: World?, addr: Long) {
    val world: World?
    var addr: Long
    var tmp: FloatArray = FloatArray(2)
    @JvmField
    val normalImpulses: FloatArray = FloatArray(2)
    @JvmField
    val tangentImpulses: FloatArray = FloatArray(2)

    init {
        this.world = world
        this.addr = addr
    }

    fun getNormalImpulses(): FloatArray {
        jniGetNormalImpulses(addr, normalImpulses)
        return normalImpulses
    }

    private external fun jniGetNormalImpulses(addr: Long, values: FloatArray?)

    fun getTangentImpulses(): FloatArray {
        jniGetTangentImpulses(addr, tangentImpulses)
        return tangentImpulses
    }

    private external fun jniGetTangentImpulses(addr: Long, values: FloatArray?)
}

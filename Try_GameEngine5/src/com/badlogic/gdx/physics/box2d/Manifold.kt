package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

class Manifold protected constructor(world: World?, addr: Long) {
    val world: World?
    var addr: Long
    @JvmField
    val points: Array<ManifoldPoint> = arrayOf<ManifoldPoint>(ManifoldPoint(), ManifoldPoint())
    @JvmField
    val localNormal: Vector2 = Vector2()
    @JvmField
    val localPoint: Vector2 = Vector2()

    val tmpInt: IntArray = IntArray(2)
    val tmpFloat: FloatArray = FloatArray(4)

    init {
        this.world = world
        this.addr = addr
    }

    fun getType(): ManifoldType {
        val type = jniGetType(addr)
        if (type == 0) return ManifoldType.Circle
        if (type == 1) return ManifoldType.FaceA
        if (type == 2) return ManifoldType.FaceB
        return ManifoldType.Circle
    }

    private external fun jniGetType(addr: Long): Int

    fun getPointCount(): Int {
        return jniGetPointCount(addr)
    }

    private external fun jniGetPointCount(addr: Long): Int

    fun getLocalNormal(): Vector2 {
        jniGetLocalNormal(addr, tmpFloat)
        localNormal.set(tmpFloat[0], tmpFloat[1])
        return localNormal
    }

    private external fun jniGetLocalNormal(addr: Long, values: FloatArray?)

    fun getLocalPoint(): Vector2 {
        jniGetLocalPoint(addr, tmpFloat)
        localPoint.set(tmpFloat[0], tmpFloat[1])
        return localPoint
    }

    private external fun jniGetLocalPoint(addr: Long, values: FloatArray?)

    fun getPoints(): Array<ManifoldPoint> {
        val count = jniGetPointCount(addr)

        for (i in 0..<count) {
            val contactID = jniGetPoint(addr, tmpFloat, i)
            val point = points[i]
            point.contactID = contactID
            point.localPoint.set(tmpFloat[0], tmpFloat[1])
            point.normalImpulse = tmpFloat[2]
            point.tangentImpulse = tmpFloat[3]
        }

        return points
    }

    private external fun jniGetPoint(addr: Long, values: FloatArray?, i: Int): Int

    inner class ManifoldPoint {
        val localPoint: Vector2 = Vector2()
        var normalImpulse: Float = 0f
        var tangentImpulse: Float = 0f
        var contactID: Int = 0

        override fun toString(): String {
            return "id: " + contactID + ", " + localPoint + ", " + normalImpulse + ", " + tangentImpulse
        }
    }

    enum class ManifoldType {
        Circle,
        FaceA,
        FaceB
    }
}

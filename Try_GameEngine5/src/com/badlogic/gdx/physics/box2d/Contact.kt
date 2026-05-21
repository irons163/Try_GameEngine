package com.badlogic.gdx.physics.box2d

/**
 * The class manages contact between two shapes. A contact exists for each overlapping
 * AABB in the broad-phase (except if filtered). Therefore a contact object may exist
 * that has no contact points.
 * @author mzechner
 // */
class Contact
    (world: World, addr: Long) {
    /** the address  */
    var addr: Long

    /** the world  */
    protected var world: World

    /** the world manifold  */
    protected val worldManifold: WorldManifold = WorldManifold()

    /**
     * Get the world manifold.
     // */
    private val tmp = FloatArray(6)

    init {
        this.addr = addr
        this.world = world
    }

    fun GetWorldManifold(): WorldManifold {
        val numContactPoints = jniGetWorldManifold(addr, tmp)

        worldManifold.numContactPoints = numContactPoints
        worldManifold.normal.set(tmp[0], tmp[1])
        for (i in 0..<numContactPoints) {
            val point = worldManifold.points[i]!!
            point.x = tmp[2 + i * 2]
            point.y = tmp[2 + i * 2 + 1]
        }

        return worldManifold
    }

    private external fun jniGetWorldManifold(addr: Long, manifold: FloatArray?): Int

    fun isTouching(): Boolean {
        return jniIsTouching(addr)
    }

    private external fun jniIsTouching(addr: Long): Boolean

    /**
     * Enable/disable this contact. This can be used inside the pre-solve
     * contact listener. The contact is only disabled for the current
     * time step (or sub-step in continuous collisions).
     // */
    fun setEnabled(flag: Boolean) {
        jniSetEnabled(addr, flag)
    }

    private external fun jniSetEnabled(addr: Long, flag: Boolean)

    /**
     * Has this contact been disabled?
     // */
    fun isEnabled(): Boolean {
        return jniIsEnabled(addr)
    }

    private external fun jniIsEnabled(addr: Long): Boolean

    /**
     * Get the first fixture in this contact.
     // */
    fun getFixtureA(): Fixture? {
        return world.fixtures.get(jniGetFixtureA(addr))
    }

    private external fun jniGetFixtureA(addr: Long): Long

    /**
     * Get the second fixture in this contact.
     // */
    fun getFixtureB(): Fixture? {
        return world.fixtures.get(jniGetFixtureB(addr))
    }

    private external fun jniGetFixtureB(addr: Long): Long
}

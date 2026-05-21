package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

/**
 * This is used to compute the current state of a contact manifold.
 // */
class WorldManifold {
    @JvmField
    val normal: Vector2 = Vector2()
    @JvmField
    val points: Array<Vector2?> = arrayOf<Vector2?>(Vector2(), Vector2())
    var numContactPoints: Int = 0

    /**
     * Returns the normal of this manifold
     // */
    fun getNormal(): Vector2 {
        return normal
    }

    /**
     * Returns the contact points of this manifold. Use getNumberOfContactPoints
     * to determine how many contact points there are (0,1 or 2)
     // */
    fun getPoints(): Array<Vector2?> {
        return points
    }

    /**
     * @return the number of contact points
     // */
    fun getNumberOfContactPoints(): Int {
        return numContactPoints
    }
}

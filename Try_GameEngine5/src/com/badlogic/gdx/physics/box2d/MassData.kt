package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

/**
 * This holds the mass data computed for a shape.
 * @author mzechner
 // */
class MassData {
    /** The mass of the shape, usually in kilograms.  */
    var mass: Float = 0f

    /** The position of the shape's centroid relative to the shape's origin.  */
    val center: Vector2 = Vector2()

    /** The rotational inertia of the shape about the local origin.  */
    var I: Float = 0f
}

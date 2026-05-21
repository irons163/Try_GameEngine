package com.badlogic.gdx.physics.box2d

/**
 * A fixture definition is used to create a fixture. This class defines an
 * abstract fixture definition. You can reuse fixture definitions safely.
 * @author mzechner
 // */
open class FixtureDef {
    /**
     * The shape, this must be set. The shape will be cloned, so you
     * can create the shape on the stack.
     // */
    var shape: Shape? = null

    /** The friction coefficient, usually in the range [0,1].  */
    var friction: Float = 0f

    /** The restitution (elasticity) usually in the range [0,1].  */
    var restitution: Float = 0f

    /** The density, usually in kg/m^2.  */
    var density: Float = 0f

    /**
     * A sensor shape collects contact information but never generates a collision
     * response.
     // */
    var isSensor: Boolean = false

    /** Contact filtering data.  */
    val filter: Filter = Filter()
}

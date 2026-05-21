package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

/**
 * A circle shape.
 * @author mzechner
 // */
class CircleShape : Shape() {
    private external fun newCircleShape(): Long

    /**
     * {@inheritDoc}
     // */
    override fun getType(): Type {
        return Type.Circle
    }

    /**
     * Returns the position of the shape
     // */
    private val tmp = FloatArray(2)
    private val position = Vector2()

    init {
        addr = newCircleShape()
    }

    fun getPosition(): Vector2 {
        jniGetPosition(addr, tmp)
        position.x = tmp[0]
        position.y = tmp[1]
        return position
    }

    private external fun jniGetPosition(addr: Long, position: FloatArray?)

    /**
     * Sets the position of the shape
     // */
    fun setPosition(position: Vector2) {
        jniSetPosition(addr, position.x, position.y)
    }

    private external fun jniSetPosition(addr: Long, positionX: Float, positionY: Float)
}

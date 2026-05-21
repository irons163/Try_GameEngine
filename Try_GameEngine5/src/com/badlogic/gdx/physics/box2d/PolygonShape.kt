package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

class PolygonShape : Shape() {
    /**
     * Constructs a new polygon
     // */
    init {
        addr = newPolygonShape()
    }

    private external fun newPolygonShape(): Long

    /**
     * {@inheritDoc}
     // */
    override fun getType(): Type {
        return Type.Polygon
    }

    /**
     * Copy vertices. This assumes the vertices define a convex polygon. It is
     * assumed that the exterior is the the right of each edge.
     // */
    fun set(vertices: Array<Vector2?>) {
        val vertice_size = vertices.size * 2
        val verts = FloatArray(vertice_size)
        var i = 0
        var j = 0
        while (i < vertice_size) {
            verts[i] = vertices[j]!!.x
            verts[i + 1] = vertices[j]!!.y
            i += 2
            j++
        }
        jniSet(addr, verts)
    }

    fun set(vertice: FloatArray?) {
        jniSet(addr, vertice)
    }

    private external fun jniSet(addr: Long, verts: FloatArray?)

    /**
     * Build vertices to represent an axis-aligned box.
     * 
     * @param hx
     * the half-width.
     * @param hy
     * the half-height.
     // */
    fun setAsBox(hx: Float, hy: Float) {
        jniSetAsBox(addr, hx, hy)
    }

    private external fun jniSetAsBox(addr: Long, hx: Float, hy: Float)

    /**
     * Build vertices to represent an oriented box.
     * 
     * @param hx
     * the half-width.
     * @param hy
     * the half-height.
     * @param center
     * the center of the box in local coordinates.
     * @param angle
     * the rotation of the box in local coordinates.
     // */
    fun setAsBox(hx: Float, hy: Float, center: Vector2, angle: Float) {
        jniSetAsBox(addr, hx, hy, center.x, center.y, angle)
    }

    private external fun jniSetAsBox(
        addr: Long, hx: Float, hy: Float,
        centerX: Float, centerY: Float, angle: Float
    )

    /**
     * Set this as a single edge.
     // */
    fun setAsEdge(v1: Vector2, v2: Vector2) {
        jniSetAsEdge(addr, v1.x, v1.y, v2.x, v2.y)
    }

    private external fun jniSetAsEdge(
        addr: Long, v1x: Float, v1y: Float,
        v2x: Float, v2y: Float
    )
}

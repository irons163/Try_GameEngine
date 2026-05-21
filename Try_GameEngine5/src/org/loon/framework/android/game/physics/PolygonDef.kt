package org.loon.framework.android.game.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.FixtureDef

/**
 * jbox
 // */
class PolygonDef : FixtureDef() {
    var vertexList: MutableList<Vector2>

    fun set(copyMe: PolygonDef) {
        this.density = copyMe.density
        this.friction = copyMe.friction
        this.isSensor = copyMe.isSensor
        this.restitution = copyMe.restitution
        this.filter.categoryBits = copyMe.filter.categoryBits
        this.filter.groupIndex = copyMe.filter.groupIndex
        this.filter.maskBits = copyMe.filter.maskBits
        this.friction = copyMe.friction
        this.vertexList = ArrayList<Vector2>()
        for (i in copyMe.vertexList.indices) {
            this.addVertex(copyMe.vertexList.get(i).clone())
        }
    }

    init {
        this.vertexList = ArrayList<Vector2>()
    }

    fun addVertex(v: Vector2?) {
        vertexList.add(v!!)
    }

    fun clearVertices() {
        vertexList.clear()
    }

    val vertexArray: Array<Vector2?>
        get() = vertexList.toTypedArray<Vector2?>()

    val vertexs: FloatArray
        get() {
            val vertice_size = vertexList.size * 2
            val verts = FloatArray(vertice_size)
            var i = 0
            var j = 0
            while (i < vertice_size) {
                val v = vertexList.get(j)
                verts[i] = v.x
                verts[i + 1] = v.y
                i += 2
                j++
            }
            return verts
        }


    fun setAsBox(hx: Float, hy: Float) {
        vertexList.clear()
        vertexList.add(Vector2(-hx, -hy))
        vertexList.add(Vector2(hx, -hy))
        vertexList.add(Vector2(hx, hy))
        vertexList.add(Vector2(-hx, hy))
    }

    val vertexCount: Int
        get() = vertexList.size
}

package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2

class Fixture
    (world: World?, body: Body?, addr: Long) {
    /** world  */
    private val world: World?

    /** body  */
    private val body: Body?

    /** the address of the fixture  */
    val addr: Long

    /**
     * Get the type of the child shape. You can use this to down cast to the concrete shape.
     * @return the shape type.
     // */
    fun getType(): Shape.Type {
        val type = jniGetType(addr)
        if (type == 0) return Shape.Type.Circle
        else return Shape.Type.Polygon
    }

    private external fun jniGetType(addr: Long): Int

    //	/// Get the child shape. You can modify the child shape, however you should not change the
    //	/// number of vertices because this will crash some collision caching mechanisms.
    //	/// Manipulating the shape may lead to non-physical behavior.
    //	b2Shape* GetShape();
    //	const b2Shape* GetShape() const;
    /**
     * Set if this fixture is a sensor.
     // */
    fun setSensor(sensor: Boolean) {
        jniSetSensor(addr, sensor)
    }

    private external fun jniSetSensor(addr: Long, sensor: Boolean)

    /**
     * Is this fixture a sensor (non-solid)?
     * @return the true if the shape is a sensor.
     // */
    fun isSensor(): Boolean {
        return jniIsSensor(addr)
    }

    private external fun jniIsSensor(addr: Long): Boolean

    /**
     * Set the contact filtering data. This will not update contacts until the next time
     * step when either parent body is active and awake.
     // */
    fun setFilterData(filter: Filter) {
        jniSetFilterData(addr, filter.categoryBits, filter.maskBits, filter.groupIndex)
    }

    private external fun jniSetFilterData(
        addr: Long,
        categoryBits: Short,
        maskBits: Short,
        groupIndex: Short
    )

    /**
     * Get the contact filtering data.
     // */
    private val tmp = ShortArray(3)
    private val filter = Filter()

    /**
     * Constructs a new fixture
     * @param addr the address of the fixture
     // */
    init {
        this.world = world
        this.body = body
        this.addr = addr
    }

    fun getFilterData(): Filter {
        jniGetFilterData(addr, tmp)
        filter.categoryBits = tmp[0]
        filter.maskBits = tmp[1]
        filter.groupIndex = tmp[2]
        return filter
    }

    private external fun jniGetFilterData(addr: Long, filter: ShortArray?)

    /**
     * Get the parent body of this fixture. This is NULL if the fixture is not attached.
     // */
    fun getBody(): Body? {
        return body
    }

    /**
     * Test a point for containment in this fixture.
     * @param p a point in world coordinates.
     // */
    fun testPoint(p: Vector2): Boolean {
        return jniTestPoint(addr, p.x, p.y)
    }

    private external fun jniTestPoint(addr: Long, x: Float, y: Float): Boolean

    //	const b2Body* GetBody() const;
    //
    //	/// Get the next fixture in the parent body's fixture list.
    //	/// @return the next shape.
    //	b2Fixture* GetNext();
    //	const b2Fixture* GetNext() const;
    //
    //	/// Get the user data that was assigned in the fixture definition. Use this to
    //	/// store your application specific data.
    //	void* GetUserData() const;
    //
    //	/// Set the user data. Use this to store your application specific data.
    //	void SetUserData(void* data);
    //
    //	/// Cast a ray against this shape.
    //	/// @param output the ray-cast results.
    //	/// @param input the ray-cast input parameters.
    //	bool RayCast(b2RayCastOutput* output, const b2RayCastInput& input) const;
    //
    //	/// Get the mass data for this fixture. The mass data is based on the density and
    //	/// the shape. The rotational inertia is about the shape's origin. This operation
    //	/// may be expensive.
    //	void GetMassData(b2MassData* massData) const;
    /**
     * Set the density of this fixture. This will _not_ automatically adjust the mass
     * of the body. You must call b2Body::ResetMassData to update the body's mass.
     // */
    fun setDensity(density: Float) {
        jniSetDensity(addr, density)
    }

    private external fun jniSetDensity(addr: Long, density: Float)

    /**
     * Get the density of this fixture.
     // */
    fun getDensity(): Float {
        return jniGetDensity(addr)
    }

    private external fun jniGetDensity(addr: Long): Float

    /**
     * Get the coefficient of friction.
     // */
    fun getFriction(): Float {
        return jniGetFriction(addr)
    }

    private external fun jniGetFriction(addr: Long): Float

    /**
     * Set the coefficient of friction.
     // */
    fun setFriction(friction: Float) {
        jniSetFriction(addr, friction)
    }

    private external fun jniSetFriction(addr: Long, friction: Float)

    /**
     * Get the coefficient of restitution.
     // */
    fun getRestitution(): Float {
        return jniGetRestitution(addr)
    }

    private external fun jniGetRestitution(addr: Long): Float

    /**
     * Set the coefficient of restitution.
     // */
    fun setRestitution(restitution: Float) {
        jniSetRestitution(addr, restitution)
    }

    private external fun jniSetRestitution(addr: Long, restitution: Float)

    fun getWorld(): World? {
        return world
    } //	/// Get the fixture's AABB. This AABB may be enlarge and/or stale.
    //	/// If you need a more accurate AABB, compute it using the shape and
    //	/// the body transform.
    //	const b2AABB& GetAABB() const;
}

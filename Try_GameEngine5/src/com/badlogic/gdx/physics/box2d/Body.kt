package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import org.loon.framework.android.game.physics.PolygonDef

/**
 * A rigid body. These are created via World.CreateBody.
 * 
 * @author mzechner
 // */
class Body(world: World, addr: Long) {
    /** the address of the body *  */
    val addr: Long

    /** temporary float array *  */
    private val tmp = FloatArray(4)

    /** World *  */
    private val world: World

    /** Fixtures of this body *  */
    private val fixtures = ArrayList<Fixture?>(2)

    /** Joints of this body *  */
    var joints: ArrayList<JointEdge?> = ArrayList<JointEdge?>(2)

    /** user data *  */
    private var userData: Any? = null

    /**
     * Creates a fixture and attach it to this body. Use this function if you
     * need to set some fixture parameters, like friction. Otherwise you can
     * create the fixture directly from a shape. If the density is non-zero,
     * this function automatically updates the mass of the body. Contacts are
     * not created until the next time step.
     * 
     * @param def
     * the fixture definition.
     * @warning This function is locked during callbacks.
     // */
    fun createFixture(def: FixtureDef): Fixture {
        val fixture = Fixture(
            world, this, jniCreateFixture(
                addr,
                def.shape!!.addr, def.friction, def.restitution, def.density,
                def.isSensor, def.filter.categoryBits, def.filter.maskBits,
                def.filter.groupIndex
            )
        )
        this.world.fixtures.put(fixture.addr, fixture)
        this.fixtures.add(fixture)
        return fixture
    }

    fun createShape(def: PolygonDef): Fixture {
        val polyShape = PolygonShape()
        polyShape.set(def.vertexs)
        def.shape = polyShape
        return createFixture(def)
    }

    private external fun jniCreateFixture(
        addr: Long, shapeAddr: Long,
        friction: Float, restitution: Float, density: Float, isSensor: Boolean,
        filterCategoryBits: Short, filterMaskBits: Short,
        filterGroupIndex: Short
    ): Long

    /**
     * Creates a fixture from a shape and attach it to this body. This is a
     * convenience function. Use b2FixtureDef if you need to set parameters like
     * friction, restitution, user data, or filtering. If the density is
     * non-zero, this function automatically updates the mass of the body.
     * 
     * @param shape
     * the shape to be cloned.
     * @param density
     * the shape density (set to zero for static bodies).
     * @warning This function is locked during callbacks.
     // */
    fun createFixture(shape: Shape, density: Float): Fixture {
        val fixture = Fixture(
            world, this, jniCreateFixture(
                addr,
                shape.addr, density
            )
        )
        this.world.fixtures.put(fixture.addr, fixture)
        this.fixtures.add(fixture)
        return fixture
    }

    private external fun jniCreateFixture(
        addr: Long, shapeAddr: Long,
        density: Float
    ): Long

    /**
     * Destroy a fixture. This removes the fixture from the broad-phase and
     * destroys all contacts associated with this fixture. This will
     * automatically adjust the mass of the body if the body is dynamic and the
     * fixture has positive density. All fixtures attached to a body are
     * implicitly destroyed when the body is destroyed.
     * 
     * @param fixture
     * the fixture to be removed.
     * @warning This function is locked during callbacks.
     // */
    fun destroyFixture(fixture: Fixture) {
        jniDestroyFixture(addr, fixture.addr)
        this.world.fixtures.remove(fixture.addr)
        this.fixtures.remove(fixture)
    }

    private external fun jniDestroyFixture(addr: Long, fixtureAddr: Long)

    /**
     * Set the position of the body's origin and rotation. This breaks any
     * contacts and wakes the other bodies. Manipulating a body's transform may
     * cause non-physical behavior.
     * 
     * @param position
     * the world position of the body's local origin.
     * @param angle
     * the world rotation in radians.
     // */
    fun setTransform(position: Vector2, angle: Float) {
        jniSetTransform(addr, position.x, position.y, angle)
    }

    private external fun jniSetTransform(
        addr: Long, positionX: Float,
        positionY: Float, angle: Float
    )

    /**
     // */
    private val transform = Transform()

    fun getTransform(): Transform {
        return transform
    }

    /**
     * Get the world body origin position.
     * 
     * @return the world position of the body's origin.
     // */
    private val position = Vector2()

    fun getPosition(): Vector2 {
        jniGetPosition(addr, tmp)
        position.x = tmp[0]
        position.y = tmp[1]
        return position
    }

    private external fun jniGetPosition(addr: Long, position: FloatArray?)

    /**
     * Get the angle in radians.
     * 
     * @return the current world rotation angle in radians.
     // */
    fun getAngle(): Float {
        return jniGetAngle(addr)
    }

    private external fun jniGetAngle(addr: Long): Float

    /**
     * Get the world position of the center of mass.
     // */
    private val worldCenter = Vector2()

    fun getWorldCenter(): Vector2 {
        jniGetWorldCenter(addr, tmp)
        worldCenter.x = tmp[0]
        worldCenter.y = tmp[1]
        return worldCenter
    }

    private external fun jniGetWorldCenter(addr: Long, worldCenter: FloatArray?)

    /**
     * Get the local position of the center of mass.
     // */
    private val localCenter = Vector2()

    fun getLocalCenter(): Vector2 {
        jniGetLocalCenter(addr, tmp)
        localCenter.x = tmp[0]
        localCenter.y = tmp[1]
        return localCenter
    }

    private external fun jniGetLocalCenter(addr: Long, localCenter: FloatArray?)

    /**
     * Set the linear velocity of the center of mass.
     // */
    fun setLinearVelocity(v: Vector2) {
        jniSetLinearVelocity(addr, v.x, v.y)
    }

    private external fun jniSetLinearVelocity(addr: Long, x: Float, y: Float)

    /**
     * Get the linear velocity of the center of mass.
     // */
    private val linearVelocity = Vector2()

    fun getLinearVelocity(): Vector2 {
        jniGetLinearVelocity(addr, tmp)
        linearVelocity.x = tmp[0]
        linearVelocity.y = tmp[1]
        return linearVelocity
    }

    private external fun jniGetLinearVelocity(
        addr: Long,
        tmpLinearVelocity: FloatArray?
    )

    /**
     * Set the angular velocity.
     // */
    fun setAngularVelocity(omega: Float) {
        jniSetAngularVelocity(addr, omega)
    }

    private external fun jniSetAngularVelocity(addr: Long, omega: Float)

    /**
     * Get the angular velocity.
     // */
    fun getAngularVelocity(): Float {
        return jniGetAngularVelocity(addr)
    }

    private external fun jniGetAngularVelocity(addr: Long): Float

    /**
     * Apply a force at a world point. If the force is not applied at the center
     * of mass, it will generate a torque and affect the angular velocity. This
     * wakes up the body.
     * 
     * @param force
     * the world force vector, usually in Newtons (N).
     * @param point
     * the world position of the point of application.
     // */
    fun applyForce(force: Vector2, point: Vector2) {
        jniApplyForce(addr, force.x, force.y, point.x, point.y)
    }

    private external fun jniApplyForce(
        addr: Long, forceX: Float, forceY: Float,
        pointX: Float, pointY: Float
    )

    /**
     * Apply a torque. This affects the angular velocity without affecting the
     * linear velocity of the center of mass. This wakes up the body.
     * 
     * @param torque
     * about the z-axis (out of the screen), usually in N-m.
     // */
    fun applyTorque(torque: Float) {
        jniApplyTorque(addr, torque)
    }

    private external fun jniApplyTorque(addr: Long, torque: Float)

    /**
     * Apply an impulse at a point. This immediately modifies the velocity. It
     * also modifies the angular velocity if the point of application is not at
     * the center of mass. This wakes up the body.
     * 
     * @param impulse
     * the world impulse vector, usually in N-seconds or kg-m/s.
     * @param point
     * the world position of the point of application.
     // */
    fun applyLinearImpulse(impulse: Vector2, point: Vector2) {
        jniApplyLinearImpulse(addr, impulse.x, impulse.y, point.x, point.y)
    }

    private external fun jniApplyLinearImpulse(
        addr: Long, impulseX: Float,
        impulseY: Float, pointX: Float, pointY: Float
    )

    /**
     * Apply an angular impulse.
     * 
     * @param impulse
     * the angular impulse in units of kg*m*m/s
     // */
    fun applyAngularImpulse(impulse: Float) {
        jniApplyAngularImpulse(addr, impulse)
    }

    private external fun jniApplyAngularImpulse(addr: Long, impulse: Float)

    /**
     * Get the total mass of the body.
     * 
     * @return the mass, usually in kilograms (kg).
     // */
    fun getMass(): Float {
        return jniGetMass(addr)
    }

    private external fun jniGetMass(addr: Long): Float

    /**
     * Get the rotational inertia of the body about the local origin.
     * 
     * @return the rotational inertia, usually in kg-m^2.
     // */
    fun getInertia(): Float {
        return jniGetInertia(addr)
    }

    private external fun jniGetInertia(addr: Long): Float

    /**
     * Get the mass data of the body.
     * 
     * @return a struct containing the mass, inertia and center of the body.
     // */
    private val massData = MassData()

    fun getMassData(): MassData? {
        jniGetMassData(addr, tmp)
        massData.mass = tmp[0]
        massData.center.x = tmp[1]
        massData.center.y = tmp[2]
        massData.I = tmp[3]
        return null
    }

    private external fun jniGetMassData(addr: Long, massData: FloatArray?)

    /**
     * Set the mass properties to override the mass properties of the fixtures.
     * Note that this changes the center of mass position. Note that creating or
     * destroying fixtures can also alter the mass. This function has no effect
     * if the body isn't dynamic.
     * 
     * @param massData
     * the mass properties.
     // */
    fun setMassData(data: MassData) {
        jniSetMassData(addr, data.mass, data.center.x, data.center.y, data.I)
    }

    private external fun jniSetMassData(
        addr: Long, mass: Float, centerX: Float,
        centerY: Float, I: Float
    )

    /**
     * This resets the mass properties to the sum of the mass properties of the
     * fixtures. This normally does not need to be called unless you called
     * SetMassData to override the mass and you later want to reset the mass.
     // */
    fun resetMassData() {
        jniResetMassData(addr)
    }

    private external fun jniResetMassData(addr: Long)

    /**
     * Get the world coordinates of a point given the local coordinates.
     * 
     * @param localPoint
     * a point on the body measured relative the the body's origin.
     * @return the same point expressed in world coordinates.
     // */
    private val localPoint = Vector2()

    fun getWorldPoint(localPoint: Vector2): Vector2 {
        jniGetWorldPoint(addr, localPoint.x, localPoint.y, tmp)
        this.localPoint.x = tmp[0]
        this.localPoint.y = tmp[1]
        return this.localPoint
    }

    private external fun jniGetWorldPoint(
        addr: Long, localPointX: Float,
        localPointY: Float, worldPoint: FloatArray?
    )

    /**
     * Get the world coordinates of a vector given the local coordinates.
     * 
     * @param localVector
     * a vector fixed in the body.
     * @return the same vector expressed in world coordinates.
     // */
    private val worldVector = Vector2()

    fun getWorldVector(localVector: Vector2): Vector2 {
        jniGetWorldVector(addr, localVector.x, localVector.y, tmp)
        worldVector.x = tmp[0]
        worldVector.y = tmp[1]
        return worldVector
    }

    private external fun jniGetWorldVector(
        addr: Long, localVectorX: Float,
        localVectorY: Float, worldVector: FloatArray?
    )

    /**
     * Gets a local point relative to the body's origin given a world point.
     * 
     * @param a
     * point in world coordinates.
     * @return the corresponding local point relative to the body's origin.
     // */
    val localPoint2: Vector2 = Vector2()

    fun getLocalPoint(worldPoint: Vector2): Vector2 {
        jniGetLocalPoint(addr, worldPoint.x, worldPoint.y, tmp)
        localPoint2.x = tmp[0]
        localPoint2.y = tmp[1]
        return localPoint2
    }

    private external fun jniGetLocalPoint(
        addr: Long, worldPointX: Float,
        worldPointY: Float, localPoint: FloatArray?
    )

    /**
     * Gets a local vector given a world vector.
     * 
     * @param a
     * vector in world coordinates.
     * @return the corresponding local vector.
     // */
    val localVector: Vector2 = Vector2()

    fun getLocalVector(worldVector: Vector2): Vector2 {
        jniGetLocalVector(addr, worldVector.x, worldVector.y, tmp)
        localVector.x = tmp[0]
        localVector.y = tmp[1]
        return localVector
    }

    private external fun jniGetLocalVector(
        addr: Long, worldVectorX: Float,
        worldVectorY: Float, worldVector: FloatArray?
    )

    /**
     * Get the world linear velocity of a world point attached to this body.
     * 
     * @param a
     * point in world coordinates.
     * @return the world velocity of a point.
     // */
    val linVelWorld: Vector2 = Vector2()

    fun getLinearVelocityFromWorldPoint(worldPoint: Vector2): Vector2 {
        jniGetLinearVelocityFromWorldPoint(
            addr, worldPoint.x, worldPoint.y,
            tmp
        )
        linVelWorld.x = tmp[0]
        linVelWorld.y = tmp[1]
        return linVelWorld
    }

    private external fun jniGetLinearVelocityFromWorldPoint(
        addr: Long,
        worldPointX: Float, worldPointY: Float, linVelWorld: FloatArray?
    )

    /**
     * Get the world velocity of a local point.
     * 
     * @param a
     * point in local coordinates.
     * @return the world velocity of a point.
     // */
    val linVelLoc: Vector2 = Vector2()

    /**
     * Constructs a new body with the given address
     * 
     * @param world
     * the world
     * @param addr
     * the address
     // */
    init {
        this.world = world
        this.addr = addr
    }

    fun getLinearVelocityFromLocalPoint(localPoint: Vector2): Vector2 {
        jniGetLinearVelocityFromLocalPoint(
            addr, localPoint.x, localPoint.y,
            tmp
        )
        linVelLoc.x = tmp[0]
        linVelLoc.y = tmp[1]
        return linVelLoc
    }

    private external fun jniGetLinearVelocityFromLocalPoint(
        addr: Long,
        localPointX: Float, localPointY: Float, linVelLoc: FloatArray?
    )

    /**
     * Get the linear damping of the body.
     // */
    fun getLinearDamping(): Float {
        return jniGetLinearDamping(addr)
    }

    private external fun jniGetLinearDamping(add: Long): Float

    /**
     * Set the linear damping of the body.
     // */
    fun setLinearDamping(linearDamping: Float) {
        jniSetLinearDamping(addr, linearDamping)
    }

    private external fun jniSetLinearDamping(addr: Long, linearDamping: Float)

    /**
     * Get the angular damping of the body.
     // */
    fun getAngularDamping(): Float {
        return jniGetAngularDamping(addr)
    }

    private external fun jniGetAngularDamping(addr: Long): Float

    /**
     * Set the angular damping of the body.
     // */
    fun setAngularDamping(angularDamping: Float) {
        jniSetAngularDamping(addr, angularDamping)
    }

    private external fun jniSetAngularDamping(addr: Long, angularDamping: Float)

    /**
     * Set the type of this body. This may alter the mass and velocity.
     // */
    fun setType(type: BodyType) {
        jniSetType(addr, type.getValue())
    }

    private external fun jniSetType(addr: Long, type: Int)

    /**
     * Get the type of this body.
     // */
    fun getType(): BodyType {
        val type = jniGetType(addr)
        if (type == 0) return BodyType.StaticBody
        if (type == 1) return BodyType.KinematicBody
        if (type == 2) return BodyType.DynamicBody
        return BodyType.StaticBody
    }

    private external fun jniGetType(addr: Long): Int

    /**
     * Should this body be treated like a bullet for continuous collision
     * detection?
     // */
    fun setBullet(flag: Boolean) {
        jniSetBullet(addr, flag)
    }

    private external fun jniSetBullet(addr: Long, flag: Boolean)

    /**
     * Is this body treated like a bullet for continuous collision detection?
     // */
    fun isBullet(): Boolean {
        return jniIsBullet(addr)
    }

    private external fun jniIsBullet(addr: Long): Boolean

    /**
     * You can disable sleeping on this body. If you disable sleeping, the
     // */
    fun setSleepingAllowed(flag: Boolean) {
        jniSetSleepingAllowed(addr, flag)
    }

    private external fun jniSetSleepingAllowed(addr: Long, flag: Boolean)

    /**
     * Is this body allowed to sleep
     // */
    fun isSleepingAllowed(): Boolean {
        return jniIsSleepingAllowed(addr)
    }

    private external fun jniIsSleepingAllowed(addr: Long): Boolean

    /**
     * Set the sleep state of the body. A sleeping body has very low CPU cost.
     * 
     * @param flag
     * set to true to put body to sleep, false to wake it.
     // */
    fun setAwake(flag: Boolean) {
        jniSetAwake(addr, flag)
    }

    private external fun jniSetAwake(addr: Long, flag: Boolean)

    /**
     * Get the sleeping state of this body.
     * 
     * @return true if the body is sleeping.
     // */
    fun isAwake(): Boolean {
        return jniIsAwake(addr)
    }

    private external fun jniIsAwake(addr: Long): Boolean

    /**
     * Set the active state of the body. An inactive body is not simulated and
     * cannot be collided with or woken up. If you pass a flag of true, all
     * fixtures will be added to the broad-phase. If you pass a flag of false,
     * all fixtures will be removed from the broad-phase and all contacts will
     * be destroyed. Fixtures and joints are otherwise unaffected. You may
     * continue to create/destroy fixtures and joints on inactive bodies.
     * Fixtures on an inactive body are implicitly inactive and will not
     * participate in collisions, ray-casts, or queries. Joints connected to an
     * inactive body are implicitly inactive. An inactive body is still owned by
     * a b2World object and remains in the body list.
     // */
    fun setActive(flag: Boolean) {
        jniSetActive(addr, flag)
    }

    private external fun jniSetActive(addr: Long, flag: Boolean)

    /**
     * Get the active state of the body.
     // */
    fun isActive(): Boolean {
        return jniIsActive(addr)
    }

    private external fun jniIsActive(addr: Long): Boolean

    /**
     * Set this body to have fixed rotation. This causes the mass to be reset.
     // */
    fun setFixedRotation(flag: Boolean) {
        jniSetFixedRotation(addr, flag)
    }

    private external fun jniSetFixedRotation(addr: Long, flag: Boolean)

    /**
     * Does this body have fixed rotation?
     // */
    fun isFixedRotation(): Boolean {
        return jniIsFixedRotation(addr)
    }

    private external fun jniIsFixedRotation(addr: Long): Boolean

    /**
     * Get the list of all fixtures attached to this body. Do not modify the
     * list!
     // */
    fun getFixtureList(): ArrayList<Fixture?> {
        return fixtures
    }

    /**
     * Get the list of all joints attached to this body. Do not modify the list!
     // */
    fun getJointList(): ArrayList<JointEdge?> {
        return joints
    }

    /**
     * Get the list of all contacts attached to this body.
     * 
     * @warning this list changes during the time step and you may miss some
     * collisions if you don't use b2ContactListener. Do not modify the
     * returned list!
     // */
    // ArrayList<ContactEdge> getContactList()
    // {
    // return contacts;
    // }
    /**
     * Get the parent world of this body.
     // */
    fun getWorld(): World {
        return world
    }

    /**
     * Get the user data
     // */
    fun getUserData(): Any? {
        return userData
    }

    /**
     * Set the user data
     // */
    fun setUserData(userData: Any?) {
        this.userData = userData
    }
}

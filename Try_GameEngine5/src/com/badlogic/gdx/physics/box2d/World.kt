package com.badlogic.gdx.physics.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.JointDef.JointType
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef
import com.badlogic.gdx.physics.box2d.joints.GearJoint
import com.badlogic.gdx.physics.box2d.joints.GearJointDef
import com.badlogic.gdx.physics.box2d.joints.LineJoint
import com.badlogic.gdx.physics.box2d.joints.LineJointDef
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef

/**
 * The world class manages all physics entities, dynamic simulation, and
 * asynchronous queries. The world also contains efficient memory management
 * facilities.
 * 
 * @author mzechner
 // */
class World(gravity: Vector2, doSleep: Boolean) {
    /** the address of the world instance *  */
    private val addr: Long

    /** all known bodies *  */
    val bodies: HashMap<Long?, Body?> = HashMap<Long?, Body?>()

    /** all known fixtures *  */
    val fixtures: HashMap<Long?, Fixture?> = HashMap<Long?, Fixture?>()

    /** all known joints *  */
    protected val joints: HashMap<Long?, Joint?> = HashMap<Long?, Joint?>()

    /** Contact filter *  */
    @JvmField
    protected var contactFilter: ContactFilter? = null

    /** Contact listener *  */
    @JvmField
    protected var contactListener: ContactListener? = null

    private external fun newWorld(gravityX: Float, gravityY: Float, doSleep: Boolean): Long

    /**
     * Register a destruction listener. The listener is owned by you and must
     * remain in scope.
     // */
    fun setDestructionListener(listener: DestructionListener?) {
    }

    /**
     * Register a contact filter to provide specific control over collision.
     * Otherwise the default filter is used (b2_defaultFilter). The listener is
     * owned by you and must remain in scope.
     // */
    fun setContactFilter(filter: ContactFilter?) {
        this.contactFilter = filter
    }

    /**
     * Register a contact event listener. The listener is owned by you and must
     * remain in scope.
     // */
    fun setContactListener(listener: ContactListener?) {
        this.contactListener = listener
    }

    /**
     * Create a rigid body given a definition. No reference to the definition is
     * retained.
     * 
     * @warning This function is locked during callbacks.
     // */
    fun createBody(def: BodyDef): Body {
        val bodyType = def.type ?: BodyType.DynamicBody
        val body = Body(
            this, jniCreateBody(
                addr,
                bodyType.getValue(), def.position.x, def.position.y, def.angle,
                def.linearVelocity.x, def.linearVelocity.y,
                def.angularVelocity, def.linearDamping, def.angularDamping,
                def.allowSleep, def.awake, def.fixedRotation, def.bullet,
                def.active, def.inertiaScale
            )
        )
        this.bodies.put(body.addr, body)
        return body
    }

    private external fun jniCreateBody(
        addr: Long, type: Int, positionX: Float,
        positionY: Float, angle: Float, linearVelocityX: Float,
        linearVelocityY: Float, angularVelocity: Float, linearDamping: Float,
        angularDamping: Float, allowSleep: Boolean, awake: Boolean,
        fixedRotation: Boolean, bullet: Boolean, active: Boolean,
        intertiaScale: Float
    ): Long

    /**
     * Destroy a rigid body given a definition. No reference to the definition
     * is retained. This function is locked during callbacks.
     * 
     * @warning This automatically deletes all associated shapes and joints.
     * @warning This function is locked during callbacks.
     // */
    fun destroyBody(body: Body) {
        this.bodies.remove(body.addr)
        for (i in body.getFixtureList().indices) this.fixtures.remove(
            body.getFixtureList().get(i)!!.addr
        )
        for (i in body.getJointList().indices) this.joints.remove(
            body.getJointList().get(i)!!.joint!!.addr
        )
        jniDestroyBody(addr, body.addr)
    }

    private external fun jniDestroyBody(addr: Long, bodyAddr: Long)

    /**
     * Create a joint to constrain bodies together. No reference to the
     * definition is retained. This may cause the connected bodies to cease
     * colliding.
     * 
     * @warning This function is locked during callbacks.
     // */
    fun createJoint(def: JointDef): Joint {
        val jointAddr = createProperJoint(def)
        var joint: Joint? = null
        if (def.type == JointType.DistanceJoint) joint = DistanceJoint(this, jointAddr)
        if (def.type == JointType.FrictionJoint) joint = FrictionJoint(this, jointAddr)
        if (def.type == JointType.GearJoint) joint = GearJoint(this, jointAddr)
        if (def.type == JointType.LineJoint) joint = LineJoint(this, jointAddr)
        if (def.type == JointType.MouseJoint) joint = MouseJoint(this, jointAddr)
        if (def.type == JointType.PrismaticJoint) joint = PrismaticJoint(this, jointAddr)
        if (def.type == JointType.PulleyJoint) joint = PulleyJoint(this, jointAddr)
        if (def.type == JointType.RevoluteJoint) joint = RevoluteJoint(this, jointAddr)
        if (def.type == JointType.WeldJoint) joint = WeldJoint(this, jointAddr)
        if (joint != null) joints.put(joint.addr, joint)
        val jointEdgeA = JointEdge(def.bodyB, joint)
        val jointEdgeB = JointEdge(def.bodyA, joint)
        joint!!.jointEdgeA = jointEdgeA
        joint.jointEdgeB = jointEdgeB
        def.bodyA!!.joints.add(jointEdgeA)
        def.bodyB!!.joints.add(jointEdgeB)
        return joint
    }

    private fun createProperJoint(def: JointDef): Long {
        if (def.type == JointType.DistanceJoint) {
            val d = def as DistanceJointDef
            return jniCreateDistanceJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.length,
                d.frequencyHz, d.dampingRatio
            )
        }
        if (def.type == JointType.FrictionJoint) {
            val d = def as FrictionJointDef
            return jniCreateFrictionJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.maxForce, d.maxTorque
            )
        }
        if (def.type == JointType.GearJoint) {
            val d = def as GearJointDef
            return jniCreateGearJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.joint1!!.addr, d.joint2!!.addr, d.ratio
            )
        }
        if (def.type == JointType.LineJoint) {
            val d = def as LineJointDef
            return jniCreateLineJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.localAxisA.x,
                d.localAxisA.y, d.enableLimit, d.lowerTranslation,
                d.upperTranslation, d.enableMotor, d.maxMotorForce,
                d.motorSpeed
            )
        }
        if (def.type == JointType.MouseJoint) {
            val d = def as MouseJointDef
            return jniCreateMouseJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.target.x, d.target.y, d.maxForce,
                d.frequencyHz, d.dampingRatio
            )
        }
        if (def.type == JointType.PrismaticJoint) {
            val d = def as PrismaticJointDef
            return jniCreatePrismaticJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.localAxis1.x,
                d.localAxis1.y, d.referenceAngle, d.enableLimit,
                d.lowerTranslation, d.upperTranslation, d.enableMotor,
                d.maxMotorForce, d.motorSpeed
            )
        }
        if (def.type == JointType.PulleyJoint) {
            val d = def as PulleyJointDef
            return jniCreatePulleyJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.groundAnchorA.x, d.groundAnchorA.y,
                d.groundAnchorB.x, d.groundAnchorB.y, d.localAnchorA.x,
                d.localAnchorA.y, d.localAnchorB.x, d.localAnchorB.y,
                d.lengthA, d.maxLengthA, d.lengthB, d.maxLengthB, d.ratio
            )
        }
        if (def.type == JointType.RevoluteJoint) {
            val d = def as RevoluteJointDef
            return jniCreateRevoluteJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.referenceAngle,
                d.enableLimit, d.lowerAngle, d.upperAngle, d.enableMotor,
                d.motorSpeed, d.maxMotorTorque
            )
        }
        if (def.type == JointType.WeldJoint) {
            val d = def as WeldJointDef
            return jniCreateWeldJoint(
                addr, d.bodyA!!.addr, d.bodyB!!.addr,
                d.collideConnected, d.localAnchorA.x, d.localAnchorA.y,
                d.localAnchorB.x, d.localAnchorB.y, d.referenceAngle
            )
        }

        return 0
    }

    private external fun jniCreateDistanceJoint(
        addr: Long, bodyA: Long,
        bodyB: Long, collideConnected: Boolean, localAnchorAX: Float,
        localAnchorAY: Float, localAnchorBX: Float, localAnchorBY: Float,
        length: Float, frequencyHz: Float, dampingRatio: Float
    ): Long

    private external fun jniCreateFrictionJoint(
        addr: Long, bodyA: Long,
        bodyB: Long, collideConnected: Boolean, localAnchorAX: Float,
        localAnchorAY: Float, localAnchorBX: Float, localAnchorBY: Float,
        maxForce: Float, maxTorque: Float
    ): Long

    private external fun jniCreateGearJoint(
        addr: Long, bodyA: Long, bodyB: Long,
        collideConnected: Boolean, joint1: Long, joint2: Long, ratio: Float
    ): Long

    private external fun jniCreateLineJoint(
        addr: Long, bodyA: Long, bodyB: Long,
        collideConnected: Boolean, localAnchorAX: Float, localAnchorAY: Float,
        localAnchorBX: Float, localAnchorBY: Float, localAxisAX: Float,
        localAxisAY: Float, enableLimit: Boolean, lowerTranslation: Float,
        upperTranslation: Float, enableMotor: Boolean, maxMotorForce: Float,
        motorSpeed: Float
    ): Long

    private external fun jniCreateMouseJoint(
        addr: Long, bodyA: Long, bodyB: Long,
        collideConnected: Boolean, targetX: Float, targetY: Float,
        maxForce: Float, frequencyHz: Float, dampingRatio: Float
    ): Long

    private external fun jniCreatePrismaticJoint(
        addr: Long, bodyA: Long,
        bodyB: Long, collideConnected: Boolean, localAnchorAX: Float,
        localAnchorAY: Float, localAnchorBX: Float, localAnchorBY: Float,
        localAxisAX: Float, localAxisAY: Float, referenceAngle: Float,
        enableLimit: Boolean, lowerTranslation: Float,
        upperTranslation: Float, enableMotor: Boolean, maxMotorForce: Float,
        motorSpeed: Float
    ): Long

    private external fun jniCreatePulleyJoint(
        addr: Long, bodyA: Long, bodyB: Long,
        collideConnected: Boolean, groundAnchorAX: Float,
        groundAnchorAY: Float, groundAnchorBX: Float, groundAnchorBY: Float,
        localAnchorAX: Float, localAnchorAY: Float, localAnchorBX: Float,
        localAnchorBY: Float, lengthA: Float, maxLengthA: Float,
        lengthB: Float, maxLengthB: Float, ratio: Float
    ): Long

    private external fun jniCreateRevoluteJoint(
        addr: Long, bodyA: Long,
        bodyB: Long, collideConnected: Boolean, localAnchorAX: Float,
        localAnchorAY: Float, localAnchorBX: Float, localAnchorBY: Float,
        referenceAngle: Float, enableLimit: Boolean, lowerAngle: Float,
        upperAngle: Float, enableMotor: Boolean, motorSpeed: Float,
        maxMotorTorque: Float
    ): Long

    private external fun jniCreateWeldJoint(
        addr: Long, bodyA: Long, bodyB: Long,
        collideConnected: Boolean, localAnchorAX: Float, localAnchorAY: Float,
        localAnchorBX: Float, localAnchorBY: Float, referenceAngle: Float
    ): Long

    /**
     * Destroy a joint. This may cause the connected bodies to begin colliding.
     * 
     * @warning This function is locked during callbacks.
     // */
    fun destroyJoint(joint: Joint) {
        joints.remove(joint.addr)
        joint.jointEdgeA!!.other!!.joints.remove(joint.jointEdgeB)
        joint.jointEdgeB!!.other!!.joints.remove(joint.jointEdgeA)
        jniDestroyJoint(addr, joint.addr)
    }

    private external fun jniDestroyJoint(addr: Long, jointAddr: Long)

    /**
     * Take a time step. This performs collision detection, integration, and
     * constraint solution.
     * 
     * @param timeStep
     * the amount of time to simulate, this should not vary.
     * @param velocityIterations
     * for the velocity constraint solver.
     * @param positionIterations
     * for the position constraint solver.
     // */
    fun step(
        timeStep: Float, velocityIterations: Int,
        positionIterations: Int
    ) {
        jniStep(addr, timeStep, velocityIterations, positionIterations)
    }

    private external fun jniStep(
        addr: Long, timeStep: Float,
        velocityIterations: Int, positionIterations: Int
    )

    /**
     * Call this after you are done with time steps to clear the forces. You
     * normally call this after each call to Step, unless you are performing
     * sub-steps. By default, forces will be automatically cleared, so you don't
     * need to call this function.
     * 
     * @see SetAutoClearForces
     // */
    fun clearForces() {
        jniClearForces(addr)
    }

    private external fun jniClearForces(addr: Long)

    /**
     * Enable/disable warm starting. For testing.
     // */
    fun setWarmStarting(flag: Boolean) {
        jniSetWarmStarting(addr, flag)
    }

    private external fun jniSetWarmStarting(addr: Long, flag: Boolean)

    /**
     * Enable/disable continuous physics. For testing.
     // */
    fun setContinuousPhysics(flag: Boolean) {
        jniSetContiousPhysics(addr, flag)
    }

    private external fun jniSetContiousPhysics(addr: Long, flag: Boolean)

    /**
     * Get the number of broad-phase proxies.
     // */
    fun getProxyCount(): Int {
        return jniGetProxyCount(addr)
    }

    private external fun jniGetProxyCount(addr: Long): Int

    /**
     * Get the number of bodies.
     // */
    fun getBodyCount(): Int {
        return jniGetBodyCount(addr)
    }

    private external fun jniGetBodyCount(addr: Long): Int

    /**
     * Get the number of joints.
     // */
    fun getJointCount(): Int {
        return jniGetJointcount(addr)
    }

    private external fun jniGetJointcount(addr: Long): Int

    /**
     * Get the number of contacts (each may have 0 or more contact points).
     // */
    fun getContactCount(): Int {
        return jniGetContactCount(addr)
    }

    private external fun jniGetContactCount(addr: Long): Int

    /**
     * Change the global gravity vector.
     // */
    fun setGravity(gravity: Vector2) {
        jniSetGravity(addr, gravity.x, gravity.y)
    }

    private external fun jniSetGravity(addr: Long, gravityX: Float, gravityY: Float)

    /**
     * Get the global gravity vector.
     // */
    val tmpGravity: FloatArray = FloatArray(2)

    @JvmField
    val gravity: Vector2 = Vector2()

    fun getGravity(): Vector2 {
        jniGetGravity(addr, tmpGravity)
        gravity.x = tmpGravity[0]
        gravity.y = tmpGravity[1]
        return gravity
    }

    private external fun jniGetGravity(addr: Long, gravity: FloatArray?)

    /**
     * Is the world locked (in the middle of a time step).
     // */
    fun isLocked(): Boolean {
        return jniIsLocked(addr)
    }

    private external fun jniIsLocked(addr: Long): Boolean

    /**
     * Set flag to control automatic clearing of forces after each time step.
     // */
    fun setAutoClearForces(flag: Boolean) {
        jniSetAutoClearForces(addr, flag)
    }

    private external fun jniSetAutoClearForces(addr: Long, flag: Boolean)

    /**
     * Get the flag that controls automatic clearing of forces after each time
     * step.
     // */
    fun getAutoClearForces(): Boolean {
        return jniGetAutoClearForces(addr)
    }

    private external fun jniGetAutoClearForces(addr: Long): Boolean

    /**
     * Query the world for all fixtures that potentially overlap the provided
     * AABB.
     * 
     * @param callback
     * a user implemented callback class.
     * @param aabb
     * the query box.
     // */
    fun QueryAABB(
        callback: QueryCallback?, lowerX: Float, lowerY: Float,
        upperX: Float, upperY: Float
    ) {
        queryCallback = callback
        jniQueryAABB(addr, lowerX, lowerY, upperX, upperY)
    }

    private var queryCallback: QueryCallback? = null

    private external fun jniQueryAABB(
        addr: Long, lowX: Float, lowY: Float,
        upX: Float, upY: Float
    )

    //
    // /// Ray-cast the world for all fixtures in the path of the ray. Your
    // callback
    // /// controls whether you get the closest point, any point, or n-points.
    // /// The ray-cast ignores shapes that contain the starting point.
    // /// @param callback a user implemented callback class.
    // /// @param point1 the ray starting point
    // /// @param point2 the ray ending point
    // void RayCast(b2RayCastCallback* callback, const b2Vec2& point1, const
    // b2Vec2& point2) const;
    //
    // /// Get the world contact list. With the returned contact, use
    // b2Contact::GetNext to get
    // /// the next contact in the world list. A NULL contact indicates the end
    // of the list.
    // /// @return the head of the world contact list.
    // /// @warning contacts are
    // b2Contact* GetContactList();
    private var contactAddrs = LongArray(200)

    private val contacts = ArrayList<Contact?>()

    private val freeContacts = ArrayList<Contact>()

    fun getContactList(): MutableList<Contact?> {
        val numContacts = getContactCount()
        if (numContacts > contactAddrs.size) contactAddrs = LongArray(numContacts)
        if (numContacts > freeContacts.size) {
            val freeConts = freeContacts.size
            for (i in 0..<numContacts - freeConts) freeContacts.add(Contact(this, 0))
        }
        jniGetContactList(addr, contactAddrs)

        contacts.clear()
        for (i in 0..<numContacts) {
            val contact = freeContacts.get(i)
            contact.addr = contactAddrs[i]
            contacts.add(contact)
        }

        return contacts
    }

    private external fun jniGetContactList(addr: Long, contacts: LongArray?)

    fun dispose() {
        jniDispose(addr)
    }

    private external fun jniDispose(addr: Long)

    /**
     * Internal method called from JNI in case a contact happens
     * 
     * @param fixtureA
     * @param fixtureB
     * @return
     // */
    fun contactFilter(fixtureA: Long, fixtureB: Long): Boolean {
        if (contactFilter != null) return contactFilter!!.shouldCollide(
            fixtures.get(fixtureA), fixtures
                .get(fixtureB)
        )
        else return true
    }

    private val contact = Contact(this, 0)

    /**
     * Construct a world object.
     * 
     * @param gravity
     * the world gravity vector.
     * @param doSleep
     * improve performance by not simulating inactive bodies.
     // */
    init {
        addr = newWorld(gravity.x, gravity.y, doSleep)

        for (i in 0..199) freeContacts.add(Contact(this, 0))
    }

    fun beginContact(contactAddr: Long) {
        contact.addr = contactAddr
        if (contactListener != null) contactListener!!.beginContact(contact)
    }

    fun endContact(contactAddr: Long) {
        contact.addr = contactAddr
        contact.GetWorldManifold()
        if (contactListener != null) contactListener!!.endContact(contact)
    }

    fun reportFixture(addr: Long): Boolean {
        if (queryCallback != null) return queryCallback!!.reportFixture(fixtures.get(addr))
        else return false
    }

    companion object {
        init {
            System.loadLibrary("jbox")
        }
    }
}

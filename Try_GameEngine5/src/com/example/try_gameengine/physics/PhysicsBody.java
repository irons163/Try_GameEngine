package com.example.try_gameengine.physics;

import java.util.ArrayList;

import org.loon.framework.android.game.physics.LShape;
import org.loon.framework.android.game.physics.LWorld;

import android.content.pm.FeatureInfo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 */
public class PhysicsBody {

	private Body jboxBody;

	private BodyDef jboxBodyDef;

	private boolean staticBody;

	private ArrayList<Body> touching = new ArrayList<Body>();

	private PhysicsShape shape;

	private Object userData;
	
	private FixtureDef fixtureDef;
	
	private Fixture fixture;

	public PhysicsBody(Body body){
		jboxBody = body;	
	}
	
	public PhysicsBody(PhysicsShape shape, float x, float y) {
		this(shape, x, y, false);
	}

	public PhysicsBody(PhysicsShape shape, float x, float y, boolean staticBody) {
		this.jboxBodyDef = new BodyDef();
		this.jboxBodyDef.position.set(new Vector2(x, y));
		this.staticBody = staticBody;
		this.shape = shape;
	}
	
	public PhysicsBody(BodyDef bodyDef) {
		this.jboxBodyDef = bodyDef;
		this.fixtureDef = new FixtureDef();
	}
	
	public PhysicsBody(FixtureDef fixtureDef) {
		this.fixtureDef = fixtureDef;
		this.jboxBodyDef = new BodyDef();
	}

	public boolean isStatic() {
		return this.staticBody;
	}

	public Object getUserData() {
//		return this.userData;
		return jboxBody.getUserData();
	}

	public void setUserData(Object object) {
		if(this.jboxBody==null){
			this.userData = object;
		}else{
			this.jboxBody.setUserData(userData);
		}
		
	}

	public boolean isTouching(Body other) {
		return this.touching.contains(other);
	}

	public int touchCount(Body other) {
		int count = 0;
		for (int i = 0; i < this.touching.size(); i++) {
			if (this.touching.get(i) == other) {
				count++;
			}
		}
		return count;
	}

	void touch(Body other) {
		this.touching.add(other);
	}

	void untouch(Body other) {
		this.touching.remove(other);
	}

	public void applyForce(float x, float y) {
		checkBody();
		this.jboxBody.applyForce(new Vector2(x, y), new Vector2(0.0F, 0.0F));
	}

	public float getX() {
		checkBody();
		return this.jboxBody.getPosition().x;
	}

	public float getY() {
		checkBody();
		return this.jboxBody.getPosition().y;
	}

	public float getRotation() {
		checkBody();
		return this.jboxBody.getAngle();
	}

	public float getXVelocity() {
		checkBody();
		return this.jboxBody.getLinearVelocity().x;
	}

	public float getYVelocity() {
		checkBody();
		return this.jboxBody.getLinearVelocity().y;
	}

	public float getAngularVelocity() {
		checkBody();
		return this.jboxBody.getAngularVelocity();
	}

//	public void setRestitution(float rest) {
//		this.shape.setRestitution(rest);
//	}
//
//	public void setFriction(float f) {
//		this.shape.setFriction(f);
//	}
//
//	public void setDensity(float den) {
//		this.shape.setDensity(den);
//	}

	public void addToWorld(LWorld world) {
		
		World jboxWorld = world.getBox2DWorld();
//		this.jboxBody = jboxWorld.createBody(this.jboxBodyDef);
		this.jboxBody= world.createBody(this.jboxBodyDef);
//		this.shape.createInBody(this);
		this.jboxBody.createFixture(this.fixtureDef);
		this.jboxBody.setUserData(userData);
		if (!this.staticBody) {
			this.jboxBody.setType(BodyType.StaticBody);
		} else {
			this.jboxBody.setType(BodyType.KinematicBody);
		}
	}

	public void addToWorld(LWorld world, float x, float y) {
		World jboxWorld = world.getBox2DWorld();
		this.jboxBodyDef.position.set(new Vector2(x, y));
		this.jboxBody = jboxWorld.createBody(this.jboxBodyDef);
//		this.shape.createInBody(this);
		this.jboxBody.createFixture(this.fixtureDef);
		this.jboxBody.setUserData(userData);
		if (!this.staticBody) {
			this.jboxBody.setType(BodyType.StaticBody);
		} else {
			this.jboxBody.setType(BodyType.KinematicBody);
		}
	}
	
	public void removeFromWorld(LWorld world) {
		World jboxWorld = world.getBox2DWorld();
		jboxWorld.destroyBody(this.jboxBody);
	}

	public Body getBox2DBody() {
		return this.jboxBody;
	}

	public PhysicsShape getLShape() {
		return this.shape;
	}

//	public void setPosition(float x, float y) {
//		checkBody();
//		jboxBody.setTransform(new Vector2(x, y), this.jboxBody.getAngle());
//	}

	public void setRotation(float rotation) {
		checkBody();
		jboxBody.setTransform(jboxBody.getPosition(), this.jboxBody.getAngle());
	}

	private void checkBody() {
		if (this.jboxBody == null)
			throw new RuntimeException("This Box2D-Body is NULL !");
	}

	public boolean isSleeping() {
		checkBody();
		return this.jboxBody.isSleepingAllowed();
	}

	public void translate(float x, float y) {
		setPosition(getX() + x, getY() + y);
	}

	public void setDamping(float damping) {
		if (this.jboxBody == null)
			this.jboxBodyDef.linearDamping = damping;
	}

//	public void setVelocity(float xVelocity, float yVelocity) {
//		checkBody();
//		Vector2 vel = jboxBody.getLinearVelocity();
//		vel.x = xVelocity;
//		vel.y = yVelocity;
//		this.jboxBody.setLinearVelocity(vel);
//	}
//
//	public void setAngularVelocity(float vel) {
//		checkBody();
//		this.jboxBody.setAngularVelocity(vel);
//	}
	
	public Vector2 getPosition() {
		return jboxBody.getPosition();
	}
	
	public float getAngle() {
		return jboxBody.getAngle();
	}
	
	public void setFixtureDef(FixtureDef fixtureDef){
		this.fixtureDef = fixtureDef;
	}
	
	public void setDynamic(boolean dynamic){
		if(jboxBody==null){
			jboxBodyDef.type = dynamic?BodyType.DynamicBody:BodyType.StaticBody;
		}else{
			jboxBody.setType(dynamic?BodyType.DynamicBody:BodyType.StaticBody);
		}
	}
	
	public void setFriction(float friction){
		if(this.fixture==null){
//			this.fixtureDef = new FixtureDef();
			this.fixtureDef.friction = friction;
		}else{
			this.fixture.setFriction(friction);
		}
	}
	
	public void setRestitution(float rest) {
		if(this.fixture==null){
			this.fixtureDef.restitution = rest;
		}else{
			this.fixture.setRestitution(rest);
		}
	}
	
	public void setDensity(float den) {
		if(this.fixture==null){
			this.fixtureDef.density = den;
		}else{
			this.fixture.setDensity(den);
		}
	}
	
	public void setVelocity(float xVelocity, float yVelocity) {
		if(jboxBody==null){
			jboxBodyDef.linearVelocity.x = xVelocity;
			jboxBodyDef.linearVelocity.y = yVelocity;
		}else{
			Vector2 vel = jboxBody.getLinearVelocity();
			vel.x = xVelocity;
			vel.y = yVelocity;
			this.jboxBody.setLinearVelocity(vel);
		}
	}

	public void setAngularVelocity(float vel) {
		if(jboxBody==null){
			jboxBodyDef.angularVelocity = vel;
		}else{
			this.jboxBody.setAngularVelocity(vel);
		}
	}
	
	public void setPosition(float x, float y) {
		if(jboxBody==null){
			jboxBodyDef.position.x = x;
			jboxBodyDef.position.y = y;
		}else{
			jboxBody.setTransform(new Vector2(x, y), this.jboxBody.getAngle());
		}
	}
}

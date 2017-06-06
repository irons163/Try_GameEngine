package com.example.try_gameengine.scene;

import java.util.ArrayList;
import java.util.HashMap;

import org.loon.framework.android.game.physics.LBody;
import org.loon.framework.android.game.physics.LWorld;
import org.loon.framework.android.game.physics.LWorldListener;
import org.loon.framework.android.game.physics.RectBox;
import org.loon.framework.android.game.physics.WorldBox;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsWorld {
	private World jboxWorld;
	private ArrayList<LBody> bodies = new ArrayList<LBody>();
	private HashMap<FixtureDef, LBody> shapeMap = new HashMap<FixtureDef, LBody>();
	private ArrayList<LWorldListener> listeners = new ArrayList<LWorldListener>();
	private int iterations;
	private Vector2 gravity;
	private WorldBox worldBox;
	
	public PhysicsWorld(Scene scene){
		
	}
	
	public PhysicsWorld(float gx, float gy, int width, int height, boolean doSleep,
			float iterations) {
		this.iterations = 10;
		this.jboxWorld = new World(this.gravity = new Vector2(gx, gy), true);
		this.worldBox = new WorldBox(jboxWorld,
				new RectBox(0, 0, width, height));
	}
	
	public boolean isAutoStep() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void update(float timeStep) {
		this.jboxWorld.setContinuousPhysics(true);
		this.jboxWorld.setWarmStarting(true);
		this.jboxWorld.step(timeStep, this.iterations, this.iterations);
	}

	public void add(LBody body) {
		body.addToWorld(this);
		ArrayList<FixtureDef> shapes = body.getLShape().getBox2DFixtures();
		for (int i = 0; i < shapes.size(); i++) {
			this.shapeMap.put(shapes.get(i), body);
		}
		this.bodies.add(body);
	}

	public void remove(LBody body) {
		ArrayList<FixtureDef> shapes = body.getLShape().getBox2DFixtures();
		for (int i = 0; i < shapes.size(); i++) {
			this.shapeMap.remove(shapes.get(i));
		}
		body.removeFromWorld(this);
		this.bodies.remove(body);
	}

	public int getBodyCount() {
		return this.bodies.size();
	}

	public LBody getLBody(int index) {
		return (LBody) this.bodies.get(index);
	}

	public void setContactListener(ContactListener listener) {
		jboxWorld.setContactListener(listener);
	}
	
	public void destroyBody(Body body) {
		jboxWorld.destroyBody(body);
	}
	
	public void destroyBody(LBody body) {
		jboxWorld.destroyBody(body.getBox2DBody());
	}
	
	public Body createBody(BodyDef def) {
		Body body = jboxWorld.createBody(def);
		LBody lBody = new LBody(body);
		bodies.add(lBody);
		return body;
	}
	
	public void addListener(LWorldListener listener) {
		this.listeners.add(listener);

	}

	public void removeListener(LWorldListener listener) {
		this.listeners.remove(listener);
	}

	public World getBox2DWorld() {
		return this.jboxWorld;
	}

	public RectBox getWorldBox() {
		return worldBox.getBox();
	}

	public void setWorldBox(RectBox box) {
		this.worldBox.setBox(box);
	}

	public void setWorldBox(int w, int h) {
		this.worldBox.setBox(new RectBox(0, 0, w, h));
	}

	public Vector2 getGravity() {
		return gravity;
	}

	public void setGravity(Vector2 gravity) {
		this.gravity = gravity;
	}
	
	public void step(float timeStep, int velocityIterations,
			int positionIterations) {
		jboxWorld.step(timeStep, velocityIterations, positionIterations);
	}
	
	public ArrayList<LBody> getBodyList() {
		return this.bodies;
	}
}

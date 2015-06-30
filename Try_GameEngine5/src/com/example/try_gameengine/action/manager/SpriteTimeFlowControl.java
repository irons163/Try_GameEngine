package com.example.try_gameengine.action.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.MovementActionFrameItem;
import com.example.try_gameengine.action.MovementActionFrameItem.FrameTrigger;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.enemy.Enemy;


public class SpriteTimeFlowControl {
	List<Enemy> enemies = new ArrayList<Enemy>();
	Enemy enemy;
	MovementActionFrameItem.FrameTrigger frameTrigger = new MovementActionFrameItem.FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			
		}
	};
	
	IActionListener actionListener = new IActionListener() {
		
		@Override
		public void beforeChangeFrame(int nextFrameId) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterChangeFrame(int periousFrameId) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void actionFinish() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionStart() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionCycleFinish() {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void addToFlowQueue(Enemy enemy){
//		enemy.getAction().settrigger;
//		enemy.;
		enemies.add(enemy);
	}
	
	public void flow(){
		frameTrigger.trigger();
//		enemy.trigger;
		enemy.getAction().start();
	}
	
	public void allFinishObjectRestart(){
//		if(enemy.getAction().isfinish)
//			enemy.getC().restart();
		
	}
	
	public void allFinishAndNotFinishObjectRestart(){
		enemy.getC().restart();
	}
	
}
